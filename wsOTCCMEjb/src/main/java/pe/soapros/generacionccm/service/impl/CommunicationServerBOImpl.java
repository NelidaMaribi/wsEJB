package pe.soapros.generacionccm.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;
import java.util.Properties;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.net.ssl.HttpsURLConnection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import main.UtilTripleDes;
import pe.soapros.generacionccm.beans.Origen;
import pe.soapros.generacionccm.beans.ResponseFlujoCS;
import pe.soapros.generacionccm.beans.ResponseTokenCS;
import pe.soapros.generacionccm.beans.Respuesta;
import pe.soapros.generacionccm.commons.AppProperties;
import pe.soapros.generacionccm.commons.ConfigurationProperties;
import pe.soapros.generacionccm.commons.Constants;
import pe.soapros.generacionccm.persistance.domain.Documento;
import pe.soapros.generacionccm.service.CommunicationServerBO;
import pe.soapros.generacionccm.service.ValidatorBO;

@Stateless(name = "CommunicationServerBO")
@TransactionManagement(TransactionManagementType.BEAN)
public class CommunicationServerBOImpl implements CommunicationServerBO {

	private Logger logger = LoggerFactory.getLogger(CommunicationServerBOImpl.class);

	private ObjectMapper mapper = new ObjectMapper();

	@EJB(name = "ValidatorBO")
	private ValidatorBO validatorBO;

	@Override
	public byte[] callPreview(String strSolicitud) throws IOException, Exception {

		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);

		String appPropertiesPath = System.getProperty(ConfigurationProperties.RUTA_ARCH_DEFAULT)
				+ Constants.RUTA_APP_PROPERTIES;
		Properties appProperties = AppProperties.getExternalProperties(appPropertiesPath);

		String url = appProperties.getProperty("preview.url");
		String name = appProperties.getProperty("preview.name");
		String version = appProperties.getProperty("preview.version");
		String key = appProperties.getProperty("cs.key");
		String async = "false";

		String rpta = executeFlujoUnico(url, name, version, "POST", strSolicitud, key, async);

		ResponseFlujoCS response = mapper.readValue(rpta, ResponseFlujoCS.class);
		logger.debug("PREVIEW CLASS: " + response.toString());

		return response.getData().getResult()[0].getContent().getData();

	}

	@Override
	public Respuesta callFlujoUnico(String strSolicitud) throws IOException, Exception {

		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);

		String appPropertiesPath = System.getProperty(ConfigurationProperties.RUTA_ARCH_DEFAULT)
				+ Constants.RUTA_APP_PROPERTIES;
		Properties appProperties = AppProperties.getExternalProperties(appPropertiesPath);

		String url = appProperties.getProperty("flujo.url");
		String name = appProperties.getProperty("flujo.name");
		String version = appProperties.getProperty("flujo.version");
		String key = appProperties.getProperty("cs.key");
		String async = "true";

		String rpta = executeFlujoUnico(url, name, version, "POST", strSolicitud, key, async);

		ResponseFlujoCS response = mapper.readValue(rpta, ResponseFlujoCS.class);

		JsonNode jsonInput = mapper.readTree(strSolicitud);

		Respuesta respuesta = new Respuesta();
		Origen origen = mapper.readValue(jsonInput.get("origen").toString(), Origen.class);
		respuesta.setOrigen(origen);
		respuesta.setNumOperacion(response.getData().getId());

		return respuesta;
	}

	private String executeFlujoUnico(String urlFlujo, String name, String version, String method, String solicitud,
			String key, String async) throws Exception {

		urlFlujo += "?name=" + name + "&version=" + version + "&async=" + async;

		URL url = new URL(urlFlujo);
		HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();

		ResponseTokenCS rptaToken = getToken();

		conn.setRequestMethod(method);
		conn.setRequestProperty("Accept", "application/json");
		conn.setRequestProperty("otdsticket", rptaToken.getTicket());

		JsonNode jsonInput = mapper.readTree(solicitud);

		String sistema = jsonInput.get("origen").get("sistema").asText();
		String plantilla = "";
		plantilla = jsonInput.get("cabecera").get("detallePDF").get("codigoPlantilla").asText();
		if (plantilla == "" || plantilla == null || plantilla == "null") {
			String plantxt = jsonInput.get("cabecera").get("detalleTXT").get("codigoPlantilla").asText();
			plantilla = plantxt;
			if(plantxt == "" || plantxt == null || plantxt == "null")
			plantilla = jsonInput.get("cabecera").get("detalleHTML").get("codigoPlantilla").asText();
		}

		Documento dcto = validatorBO.getPlantilla(sistema, plantilla);

		byte[] arreglo = Base64.getEncoder().encode(solicitud.getBytes());

		String body = "{\"content\": {" + "\"contentType\": \"application/json\"," + "\"data\": \""
				+ new String(arreglo) + "\"" + "}," + "\"PubFileName\": \"" + dcto.getResourceid() + "\", "
				+ "\"Key\": \"" + key + "\"" + "}";

		logger.debug("TOKEN: " + rptaToken.getTicket());
		logger.debug("BODY Flujo: " + body);
		if (body != null) {
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setDoOutput(true);
			byte[] outputInBytes = body.getBytes("UTF-8");
			OutputStream os = conn.getOutputStream();
			os.write(outputInBytes);
			os.close();
		}
		logger.debug("Response: " + conn.getResponseCode());
		if (conn.getResponseCode() != 200) {
			logger.error(
					"Error al consultar servicio: " + urlFlujo + " - [HTTP ERROR CODE]: " + conn.getResponseCode());
			throw new Exception(
					"Error al consultar servicio: " + urlFlujo + " - [HTTP ERROR CODE]: " + conn.getResponseCode());
		}

		BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream()), "UTF-8"));
		String output;
		String response = "";
		while ((output = br.readLine()) != null) {
			response += output;
		}
		conn.disconnect();

		logger.debug("FLUJO UNICO: " + response);
		return response;

	}

	@Override
	public ResponseTokenCS getToken() throws Exception {
		String appPropertiesPath = System.getProperty(ConfigurationProperties.RUTA_ARCH_DEFAULT)
				+ Constants.RUTA_APP_PROPERTIES;
		Properties appProperties = AppProperties.getExternalProperties(appPropertiesPath);
		String token = appProperties.get("token.url").toString();
		String usuario = appProperties.get("token.usuario").toString();
		// String password = appProperties.get("token.password").toString();
		String passwordEncriptado = System.getProperty("VAR_PASS_OTADMIN");
		String password = String.valueOf(UtilTripleDes.decrypt(passwordEncriptado));

		String respuesta = executeToken(token, usuario, password, "POST");
		if (respuesta == null) {
			throw new Exception("No se ha recibido respuesta del Token");
		}
		ResponseTokenCS response = mapper.readValue(respuesta, ResponseTokenCS.class);
		return response;

	}

	private String executeToken(String urlToken, String usuario, String password, String method) throws Exception {
		URL url = new URL(urlToken);
		HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
		conn.setRequestMethod(method);
		conn.setRequestProperty("Accept", "application/json");

		String body = "{  \"user_name\": \"" + usuario + "\",  \"password\": \"" + password + "\"}";
		logger.debug("BODY TOKEN: " + body);

		if (body != null) {
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setDoOutput(true);
			byte[] outputInBytes = body.getBytes("UTF-8");
			OutputStream os = conn.getOutputStream();
			os.write(outputInBytes);
			os.close();
		}
		logger.debug("Response: " + conn.getResponseCode());
		if (conn.getResponseCode() != 200) {
			logger.error(
					"Error al consultar servicio: " + urlToken + " - [HTTP ERROR CODE]: " + conn.getResponseCode());
			throw new Exception(
					"Error al consultar servicio: " + urlToken + " - [HTTP ERROR CODE]: " + conn.getResponseCode());
		}

		BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream()), "UTF-8"));
		String output;
		String response = "";
		while ((output = br.readLine()) != null) {
			response += output;
		}
		conn.disconnect();
		return response;

	}

	@Override
	public Respuesta callOrquestador(String strSolicitud) throws IOException, Exception {
		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
		String appPropertiesPath = System.getProperty(ConfigurationProperties.RUTA_ARCH_DEFAULT)
				+ Constants.RUTA_APP_PROPERTIES;
		Properties appProperties = AppProperties.getExternalProperties(appPropertiesPath);
		String ws = appProperties.get("orquestador").toString();
		String respuesta = executeService(ws, "POST", strSolicitud);
		if (respuesta == null) {
			throw new Exception("No se ha recibido respuesta del orquestador");
		}
		Respuesta response = mapper.readValue(respuesta, Respuesta.class);

		return response;
	}

	private String executeService(String ws, String method, String body) throws Exception {
		URL url = new URL(ws);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod(method);
		conn.setRequestProperty("Accept", "application/json");
		if (body != null) {
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setDoOutput(true);
			byte[] outputInBytes = body.getBytes("UTF-8");
			OutputStream os = conn.getOutputStream();
			os.write(outputInBytes);
			os.close();
		}
		if (conn.getResponseCode() != 200) {
			logger.error("Error al consultar servicio: " + ws + " - [HTTP ERROR CODE]: " + conn.getResponseCode());
			throw new Exception(
					"Error al consultar servicio: " + ws + " - [HTTP ERROR CODE]: " + conn.getResponseCode());
		}

		BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream()), "UTF-8"));
		String output;
		String response = "";
		while ((output = br.readLine()) != null) {
			response += output;
		}
		conn.disconnect();
		return response;
	}

}
