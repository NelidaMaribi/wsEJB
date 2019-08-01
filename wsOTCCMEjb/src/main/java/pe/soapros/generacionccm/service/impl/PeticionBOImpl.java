package pe.soapros.generacionccm.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.xml.bind.ParseConversionEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;

import pe.soapros.generacionccm.beans.AlmacenamientoFilenet;
import pe.soapros.generacionccm.beans.AlmacenamientoLocal;
import pe.soapros.generacionccm.beans.AlmacenamientoS3;
import pe.soapros.generacionccm.beans.Cabecera;
import pe.soapros.generacionccm.beans.CabeceraIN;
import pe.soapros.generacionccm.beans.DetalleCorreo;
import pe.soapros.generacionccm.beans.DetalleCorreoIN;
import pe.soapros.generacionccm.beans.DetallePDF;
import pe.soapros.generacionccm.beans.DetalleRespuesta;
import pe.soapros.generacionccm.beans.DetalleSMS;
import pe.soapros.generacionccm.beans.DetalleServicio;
import pe.soapros.generacionccm.beans.DetalleServicioGenericoIN;
import pe.soapros.generacionccm.beans.DetalleTXT;
import pe.soapros.generacionccm.beans.DetalleTrazabilidad;
import pe.soapros.generacionccm.beans.Entrada_Peticion;
import pe.soapros.generacionccm.beans.IndHTML_AlmcLocal;
import pe.soapros.generacionccm.beans.IndHTML_AlmcS3;
import pe.soapros.generacionccm.beans.IndPDF_AlmcLocal;
import pe.soapros.generacionccm.beans.IndPDF_AlmcS3;
import pe.soapros.generacionccm.beans.IndTXT_AlmcLocal;
import pe.soapros.generacionccm.beans.IndTXT_AlmcS3;
import pe.soapros.generacionccm.beans.PeticionOUT;
import pe.soapros.generacionccm.beans.ResponseDetalleLocal;
import pe.soapros.generacionccm.beans.ResponseS3;
import pe.soapros.generacionccm.beans.Respuesta;
import pe.soapros.generacionccm.beans.Solicitud;
import pe.soapros.generacionccm.beans.indHTML_AlmcFilenet3;
import pe.soapros.generacionccm.beans.indPDF_AlmcFilenet;
import pe.soapros.generacionccm.beans.indTXT_AlmcFilenet2;
import pe.soapros.generacionccm.beans.DetalleHTML;
import pe.soapros.generacionccm.beans.DetalleHTMLIN;
import pe.soapros.generacionccm.persistance.domain.Detalle;
import pe.soapros.generacionccm.persistance.domain.Peticion;
import pe.soapros.generacionccm.persistance.repository.DetalleRepository;
import pe.soapros.generacionccm.persistance.repository.PeticionRepository;
import pe.soapros.generacionccm.service.PeticionBO;

@Stateless(name = "PeticionBO")
@TransactionManagement(TransactionManagementType.BEAN)
public class PeticionBOImpl implements PeticionBO {

	private Logger logger = LoggerFactory.getLogger(PeticionBOImpl.class);

	@EJB(name = "PeticionRepository")
	private PeticionRepository peticionRepository;

	@EJB(name = "DetalleRepository")
	private DetalleRepository detalleRepository;

	private ObjectMapper mapper = new ObjectMapper();

	@Override
	public String procesarPeticion(String strRes, String strSol) throws JsonProcessingException, IOException {
		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);

		Solicitud sol;
		Respuesta res;

		sol = mapper.readValue(strSol, Solicitud.class);
		res = mapper.readValue(strRes, Respuesta.class);

		logger.debug("INI [procesarPeticion]");
		logger.debug("procesarPeticion parametros; {} - {}", res, sol);

		Peticion pet = new Peticion();

		pet.setUsuCreacion(res.getOrigen().getUsuario());
		pet.setFecCreacion(new Date());
		pet.setNumOperacion(res.getNumOperacion());
		pet.setSistema(res.getOrigen().getSistema());
		pet.setNomfase("Solicitado");
		pet.setIndError(false);

		Detalle det = new Detalle();
		det.setUsuCreacion(res.getOrigen().getUsuario());
		det.setFecCreacion(new Date());
		det.setNomFase("Solicitado");

		ObjectMapper mapper = new ObjectMapper();
		String jsonInput = mapper.writeValueAsString(sol);
		logger.debug("JSON: {}", jsonInput);

		det.setInputJson(jsonInput);
		det.setIndError(false);

		pet.getDetalles().add(det);

		Peticion p = peticionRepository.save(pet);
		// det.setIdPeticion(p.getIdPeticion());
		// detalleRepository.save(det);

		// peticionRepository.saveTransaction(pet, det);
		logger.debug("JPA Guardado");

		String respuesta = mapper.writeValueAsString(res);
		logger.debug("Respuesta: {}" + res);
		logger.debug("FIN [procesarPeticion]");
		return respuesta;
	}

	@Override
	public PeticionOUT consultarPeticion(Entrada_Peticion solicitud)
			throws JsonParseException, JsonMappingException, IOException {
		logger.debug("INI [consultarPeticion]");
		// logger.debug("FIN [consultarPeticion]");

		logger.debug("consultarPeticion: {}", solicitud);
		logger.debug("numero operación: {}", solicitud.getNumOperacion());
		Peticion petDetalles = peticionRepository.getPeticion(solicitud.getNumOperacion());

		List<Detalle> detalles = petDetalles.getDetalles();

		// peticionRepository.detallesByOperacion(solicitud.getNumOperacion());
		Peticion peticion = peticionRepository.getPeticion(solicitud.getNumOperacion());

		if (detalles == null || detalles.size() == 0) {
			return null;
		}

		logger.debug("DetallesOBTENIDOSJSON: {}", detalles.toString());

		HashMap<String, Detalle> hmap = new HashMap<String, Detalle>();

		for (Detalle det : detalles) {

			hmap.put(det.getNomFase(), det);

		}

		logger.debug("InputSOLICITADOHMAP: {}", hmap);

		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);

		Solicitud sol = mapper.readValue(hmap.get("Solicitado").getInputJson(), Solicitud.class);
		logger.debug("JSON: {}", sol);

		PeticionOUT respuesta = new PeticionOUT();

		respuesta.setOrigen(sol.getOrigen());
		logger.debug("ORIGEN: {}", sol.getOrigen());

		Cabecera cabecera = new Cabecera();

		/*****************************************************************
		 * GENERAR DOCUMENTOS
		 ****************************************************************/

		DetallePDF detPDF = new DetallePDF();
		DetalleTXT detTXT = new DetalleTXT();
		DetalleHTML detHTML = new DetalleHTML();

		detPDF.setIndPDF(sol.getCabecera().getDetallePDF().getIndPDF());
		logger.debug("INDPDF: {}", sol.getCabecera().getDetallePDF().getIndPDF());

		detTXT.setIndTXT(sol.getCabecera().getDetalleTXT().getIndTXT());
		logger.debug("INDTXT: {}", sol.getCabecera().getDetalleTXT().getIndTXT());

		detHTML.setIndHTML(sol.getCabecera().getDetalleHTML().getIndHTML());
		logger.debug("INDHTML: {}", sol.getCabecera().getDetalleHTML().getIndHTML());

		CabeceraIN cabIn = sol.getCabecera();

		// boolean swSeguir = true;

		if (hmap.get("Solicitado") != null) {

			logger.debug("DOCUMENTOS GENERADOS");

			if (cabIn.getDetallePDF().getIndPDF().equals("S")) {

				if (hmap.get("Solicitado").isIndError()) {
					detPDF.setIndExito("N");
					detPDF.setCodEstado("-1");
					detPDF.setMsgEstado("DOCUMENTOS NO GENERADOS");
				} else {
					detPDF.setIndExito("S");
					detPDF.setCodEstado("0");
					detPDF.setMsgEstado("DOCUMENTOS GENERADOS");
				}

			}

			if (cabIn.getDetalleTXT().getIndTXT().equals("S")) {

				if (hmap.get("Solicitado").isIndError()) {
					detTXT.setIndExito("N");
					detTXT.setCodEstado("-1");
					detTXT.setMsgEstado("DOCUMENTOS NO GENERADOS");
				} else {
					detTXT.setIndExito("S");
					detTXT.setCodEstado("0");
					detTXT.setMsgEstado("DOCUMENTOS GENERADOS");
				}

			}

			if (cabIn.getDetalleHTML().getIndHTML().equals("S")) {

				if (hmap.get("Solicitado").isIndError()) {
					detHTML.setIndExito("N");
					detHTML.setCodEstado("-1");
					detHTML.setMsgEstado("DOCUMENTOS NO GENERADOS");
				} else {
					detHTML.setIndExito("S");
					detHTML.setCodEstado("0");
					detHTML.setMsgEstado("DOCUMENTOS GENERADOS");
				}

			}

		}

		cabecera.setDetallePDF(detPDF);
		logger.debug("DETPDF: {}", detPDF);

		cabecera.setDetalleTXT(detTXT);
		logger.debug("DETTXT: {}", detTXT);

		cabecera.setDetalleHTML(detHTML);
		logger.debug("DETHTML: {}", detHTML);

		/***********************************
		 * + DETALLE DEL ENVIO A SMS
		 ***********************************/
		DetalleSMS detSMS = new DetalleSMS();
		DetalleRespuesta detresp = new DetalleRespuesta();

		try {

			detSMS.setIndSMS(sol.getCabecera().getDetalleSMS().getIndSMS());
			logger.debug("IND SMS: {}", sol.getCabecera().getDetalleSMS().getIndSMS());

			if (hmap.get("Solicitado") != null) {

				logger.debug("ENVIO SMS");

				if (cabIn.getDetalleSMS().getIndSMS().equals("S")) {

					if (hmap.get("Solicitado").isIndError()) {

						detresp.setIndExito("N");
						detresp.setCodEstado("-1");
						detresp.setMsgEstado("DOCUMENTOS NO GENERADOS");

					} else {

						detresp.setIndExito("S");
						detresp.setCodEstado("0");
						detresp.setMsgEstado("DOCUMENTOS GENERADOS");
					}

				}

			}

			detSMS.setNumeroRespuesta(detresp);
			cabecera.setEnvioSMS(detSMS);
			logger.debug("ENVIO SMS: {}", detSMS);

		} catch (Exception e) {

			logger.error("ERROR SMS {}", e);
		}

		/**********************************************
		 * ENVIO A TRAZABILIDAD
		 *********************************************/

		DetalleTrazabilidad detTraz = new DetalleTrazabilidad();

		try {
			detTraz.setIndTrazabilidad(sol.getCabecera().getDetalleTrazabilidadCorreo().getIndTrazabilidad());
			logger.debug("INDTRAZABILIDAD: {}", sol.getCabecera().getDetalleTrazabilidadCorreo().getIndTrazabilidad());

			if (hmap.get("Trazabilidad") != null) {

				logger.debug("TRAZABILIDAD");

				if (cabIn.getDetalleTrazabilidadCorreo().getIndTrazabilidad().equals("S")) {

					if (hmap.get("Trazabilidad").isIndError()) {

						detTraz.setIndExito("N");
						detTraz.setCodEstado("-1");
						detTraz.setMsgEstado("Trazabilidad no realizada");
						detTraz.setValorretorno(hmap.get("Trazabilidad").getInputJson());

					} else {

						detTraz.setIndExito("S");
						detTraz.setCodEstado("0");
						detTraz.setMsgEstado("Trazabilidad realizada");
						detTraz.setValorretorno(hmap.get("Trazabilidad").getInputJson());
					}

				}

			}

			cabecera.setDetalleTrazabilidad(detTraz);

		} catch (Exception e) {

			logger.error("ERROR TRAZABILIDAD {}", e);

		}

		/***********************************
		 * + DETALLE SERVICIO GENERICO
		 ***********************************/
		DetalleServicio dets_g = new DetalleServicio();

		try {

			dets_g.setIndServicio(sol.getCabecera().getDetalleServicioGenerico().getIndServicioGenerico());
			logger.debug("IND SERVICIO: {}", sol.getCabecera().getDetalleServicioGenerico().getIndServicioGenerico());
			if (cabIn.getDetalleServicioGenerico().getIndServicioGenerico().equals("S")) {

				if (hmap.get("ServicioGenerico").isIndError()) {
					dets_g.setIndExito("N");
					dets_g.setCodEstado("-1");
					dets_g.setMsgEstado("DOCUMENTOS NO GENERADOS");

				} else {
					dets_g.setIndExito("S");
					dets_g.setCodEstado("0");
					dets_g.setMsgEstado("DOCUMENTOS GENERADOS");

				}

			}
			cabecera.setDetalleServicio(dets_g);
			logger.debug("SERVICIO Generico: {}", dets_g);

		} catch (Exception e) {

			logger.error("ERROR SERV. GENERICO {}", e);
		}

		/**************************************************
		 * ALMACENAMIENTO EN S3
		 ************************************************/

		AlmacenamientoS3 almS3 = new AlmacenamientoS3();

		IndPDF_AlmcS3 pdfS3 = new IndPDF_AlmcS3();
		IndTXT_AlmcS3 txtS3 = new IndTXT_AlmcS3();
		IndHTML_AlmcS3 htmlS3 = new IndHTML_AlmcS3();

		try {
			pdfS3.setIndS3PDF(sol.getCabecera().getDetalleS3().getIndPDF().getIndS3PDF());
			logger.debug("INDS3PDF", sol.getCabecera().getDetalleS3().getIndPDF().getIndS3PDF());

			txtS3.setIndS3TXT(sol.getCabecera().getDetalleS3().getIndTXT().getIndS3TXT());
			logger.debug("INDS3TXT: {}", sol.getCabecera().getDetalleS3().getIndTXT().getIndS3TXT());

			htmlS3.setIndS3HTML(sol.getCabecera().getDetalleS3().getIndHTML().getIndS3HTML());
			logger.debug("INDS3HTML: {}", sol.getCabecera().getDetalleS3().getIndHTML().getIndS3HTML());

			if (hmap.get("S3") != null) {

				logger.debug("JSON S3: {}", hmap.get("S3"));

				if (cabIn.getDetalleS3().getIndPDF().getIndS3PDF().equals("S")) {

					if (hmap.get("S3").isIndError()) {
						pdfS3.setIndExito("N");
						pdfS3.setCodEstado("-1");
						pdfS3.setMsgEstado(hmap.get("S3").getInputJson());
					} else {

						ResponseS3[] responseS3 = mapper.readValue(hmap.get("S3").getInputJson(), ResponseS3[].class);
						logger.debug("Response S3[0] {}", responseS3[0].toString());

						pdfS3.setIndExito("S");
						pdfS3.setCodEstado("0");
						pdfS3.setMsgEstado("Documento Subido a S3");
						pdfS3.setRutaURLDestinoPDF(responseS3[0].getLocation());
					}

				}

				if (cabIn.getDetalleS3().getIndTXT().getIndS3TXT().equals("S")) {

					if (hmap.get("S3").isIndError()) {
						txtS3.setIndExito("N");
						txtS3.setCodEstado("-1");
						txtS3.setMsgEstado("Documento No Subido a S3");
						txtS3.setRutaURLDestinoTXT(hmap.get("S3").getInputJson());
					} else {
						ResponseS3[] responseS3 = mapper.readValue(hmap.get("S3").getInputJson(), ResponseS3[].class);
						logger.debug("Response S3[0] {}", responseS3[0].toString());
						txtS3.setIndExito("S");
						txtS3.setCodEstado("0");
						txtS3.setMsgEstado("Documento Subido a S3");
						txtS3.setRutaURLDestinoTXT(responseS3[1].getLocation());
					}

				}

				if (cabIn.getDetalleS3().getIndHTML().getIndS3HTML().equals("S")) {

					if (hmap.get("S3").isIndError()) {
						htmlS3.setIndExito("N");
						htmlS3.setCodEstado("-1");
						htmlS3.setMsgEstado("Documento No Subido a S3");
						htmlS3.setRutaURLDestinoHTML(hmap.get("S3").getInputJson());
					} else {
						ResponseS3[] responseS3 = mapper.readValue(hmap.get("S3").getInputJson(), ResponseS3[].class);
						logger.debug("Response S3[0] {}", responseS3[0].toString());
						htmlS3.setIndExito("S");
						htmlS3.setCodEstado("0");
						htmlS3.setMsgEstado("Documento Subido a S3");
						htmlS3.setRutaURLDestinoHTML(responseS3[2].getLocation());
					}

				}

			}

			almS3.setIndPDF(pdfS3);
			logger.debug("INDPDFS3: {}", pdfS3);

			almS3.setIndTXT(txtS3);
			logger.debug("INDTXTS3: {}", txtS3);

			almS3.setIndHTML(htmlS3);
			logger.debug("INDHTMLS3: {}", htmlS3);

			cabecera.setAlmacenamientoS3(almS3);
			logger.debug("ALMACENAMIENTO S3 {}", almS3);

		} catch (Exception e) {
			logger.error("ERROR S3 {}", e);
		}

		/****************************************************
		 * ENVIO FILENET
		 ***************************************************/
		AlmacenamientoFilenet almFilenet = new AlmacenamientoFilenet();

		indPDF_AlmcFilenet pdfFilenet = new indPDF_AlmcFilenet();
		indTXT_AlmcFilenet2 txtFilenet = new indTXT_AlmcFilenet2();
		indHTML_AlmcFilenet3 htmlFilenet = new indHTML_AlmcFilenet3();

		try {

			pdfFilenet.setInfFilenetPDF(sol.getCabecera().getDetalleFilenet().getIndPDF().getIndFilenetPDF());
			logger.debug("pdfFilenet", sol.getCabecera().getDetalleFilenet().getIndPDF().getIndFilenetPDF());

			txtFilenet.setInfFilenetTXT(sol.getCabecera().getDetalleFilenet().getIndTXT().getIndFilenetTXT());
			logger.debug("txtFilenet", sol.getCabecera().getDetalleFilenet().getIndTXT().getIndFilenetTXT());

			htmlFilenet.setInfFilenetHTML(sol.getCabecera().getDetalleFilenet().getIndHTML().getIndFilenetHTML());
			logger.debug("htmlFilenet", sol.getCabecera().getDetalleFilenet().getIndHTML().getIndFilenetHTML());

			if (hmap.get("Filenet") != null) {
				logger.debug("JSON Filenet: {}", hmap.get("Filenet"));

				if (cabIn.getDetalleFilenet().getIndPDF().getIndFilenetPDF().equals("S")) {

					if (hmap.get("Filenet").isIndError()) {
						pdfFilenet.setIndExito("N");
						pdfFilenet.setCodEstado("-1");
						pdfFilenet.setMsgEstado(hmap.get("Filenet").getInputJson());
					} else {

						pdfFilenet.setIndExito("S");
						pdfFilenet.setCodEstado("0");
						pdfFilenet.setMsgEstado("Documento Subido a Filenet");
					}

				}

				if (cabIn.getDetalleFilenet().getIndTXT().getIndFilenetTXT().equals("S")) {

					if (hmap.get("Filenet").isIndError()) {
						txtFilenet.setIndExito("N");
						txtFilenet.setCodEstado("-1");
						txtFilenet.setMsgEstado("Documento No Subido a Filenet");
					} else {
						txtFilenet.setIndExito("S");
						txtFilenet.setCodEstado("0");
						txtFilenet.setMsgEstado("Documento Subido a Filenet");
					}

				}

				if (cabIn.getDetalleFilenet().getIndHTML().getIndFilenetHTML().equals("S")) {

					if (hmap.get("Filenet").isIndError()) {
						htmlFilenet.setIndExito("N");
						htmlFilenet.setCodEstado("-1");
						htmlFilenet.setMsgEstado("Documento No Subido a Filenet");
					} else {
						htmlFilenet.setIndExito("S");
						htmlFilenet.setCodEstado("0");
						htmlFilenet.setMsgEstado("Documento Subido a Filenet");
					}

				}

				almFilenet.setIndPDF(pdfFilenet);
				logger.debug("pdfFilenet: {}", pdfFilenet);

				almFilenet.setIndTXT(txtFilenet);
				logger.debug("txtFilenet: {}", txtFilenet);

				almFilenet.setIndHTML(htmlFilenet);
				logger.debug("htmlFilenet: {}", htmlFilenet);

				cabecera.setAlmacenamientoFilenet(almFilenet);
				logger.debug("ALMACENAMIENTO Filenet {}", almFilenet);

			}

		} catch (Exception e) {
			logger.error("ERROR FileNet {}", e);
		}
		/**************************************************
		 * DETALLE SERVICIO LOCAL
		 ************************************************/

		AlmacenamientoLocal almLocal = new AlmacenamientoLocal();

		IndPDF_AlmcLocal pdfLocal = new IndPDF_AlmcLocal();
		IndTXT_AlmcLocal txtLocal = new IndTXT_AlmcLocal();
		IndHTML_AlmcLocal htmlLocal = new IndHTML_AlmcLocal();
		try {

			pdfLocal.setIndLocalPDF(sol.getCabecera().getDetalleLocal().getIndPDF().getIndLocalPDF());
			logger.debug("PDF LOCAL", sol.getCabecera().getDetalleLocal().getIndPDF().getIndLocalPDF());

			txtLocal.setIndLocalTXT(sol.getCabecera().getDetalleLocal().getIndTXT().getIndLocalTXT());
			logger.debug("TXT LOCAL", sol.getCabecera().getDetalleLocal().getIndTXT().getIndLocalTXT());

			htmlLocal.setIndLocalHTML(sol.getCabecera().getDetalleLocal().getIndHTML().getIndLocalHTML());
			logger.debug("HTML LOCAL", sol.getCabecera().getDetalleLocal().getIndHTML().getIndLocalHTML());

			if (hmap.get("CopiaLocal") != null) {
				logger.debug("JSON Loal: {}", hmap.get("Local"));

				if (cabIn.getDetalleLocal().getIndPDF().getIndLocalPDF().equals("S")) {

					if (hmap.get("CopiaLocal").isIndError()) {
						pdfLocal.setIndExito("N");
						pdfLocal.setCodEstado("-1");
						pdfLocal.setMsgEstado(hmap.get("CopiaLocal").getInputJson());
					} else {

						ResponseDetalleLocal[] responseLC = mapper.readValue(hmap.get("CopiaLocal").getInputJson(), ResponseDetalleLocal[].class);
						logger.debug("Response Local[0] {}", responseLC[0].toString());

					
						pdfLocal.setIndExito("S");
						pdfLocal.setCodEstado("0");
						pdfLocal.setMsgEstado("Se guardo correctamente");
						pdfLocal.setRutaDestinoPDF(responseLC[0].getArchivo());
						
					}

				}

				if (cabIn.getDetalleLocal().getIndTXT().getIndLocalTXT().equals("S")) {

					if (hmap.get("CopiaLocal").isIndError()) {
						txtLocal.setIndExito("N");
						txtLocal.setCodEstado("-1");
						txtLocal.setMsgEstado("Documento TXT No guardado en Local");
					} else {
						ResponseDetalleLocal[] responseLC = mapper.readValue(hmap.get("CopiaLocal").getInputJson(), ResponseDetalleLocal[].class);
						logger.debug("Response Local[0] {}", responseLC[0].toString());
						
						txtLocal.setIndExito("S");
						txtLocal.setCodEstado("0");
						txtLocal.setMsgEstado("Se guardo correctamente");
						txtLocal.setRutaDestinoTxt(responseLC[1].getArchivo());
					}

				}

				if (cabIn.getDetalleLocal().getIndHTML().getIndLocalHTML().equals("S")) {

					if (hmap.get("CopiaLocal").isIndError()) {
						htmlLocal.setIndEstado("N");
						htmlLocal.setCodEstado("-1");
						htmlLocal.setMsgEstado("Documento HTML No guardado en Local");
					} else {
						ResponseDetalleLocal[] responseLC = mapper.readValue(hmap.get("CopiaLocal").getInputJson(), ResponseDetalleLocal[].class);
						logger.debug("Response Local[0] {}", responseLC[0].toString());
						
						htmlLocal.setIndEstado("S");
						htmlLocal.setCodEstado("0");
						htmlLocal.setMsgEstado("Se guardo correctamente");
						htmlLocal.setRutaDestinoHTML(responseLC[2].getArchivo());
					}

				}

				almLocal.setIndPDF(pdfLocal);
				logger.debug("PDF_LOCAL: {}", txtLocal);

				almLocal.setIndTXT(txtLocal);
				logger.debug("TXT_LOCAL: {}", txtLocal);

				almLocal.setIndHTML(htmlLocal);
				logger.debug("HTML_LOCAL: {}", txtLocal);

				cabecera.setAlmacenamientoLocal(almLocal);
				logger.debug("ALMACENAMIENTO LOCAL {}", almLocal);

			}

		} catch (Exception e) {
			logger.error("ERROR ALMACENAMIENTO LOCAL {}", e);
		}

		/***************************************************************
		 * DETALLE DEL SERVICIO correo
		 *************************************************************/

		DetalleCorreo detCorreo = new DetalleCorreo();

		try {
			detCorreo.setIndCorreo((sol.getCabecera().getDetalleCorreo().getIndCorreo()));
			logger.debug("IND CORREO: {}", sol.getCabecera().getDetalleServicioGenerico().getIndServicioGenerico());

			if (hmap.get("Solicitado") != null) {
				logger.debug("JSON DetalleCorreo: {}", hmap.get("Solicitado"));

				if (cabIn.getDetalleCorreo().getIndCorreo().equals("S")) {

					if (hmap.get("Solicitado").isIndError()) {
						detCorreo.setIndExito("N");
						detCorreo.setCodEstado("-1");
						detCorreo.setMsgEstado("NO SE ENVIO EL CORREO");
					} else {

						detCorreo.setIndExito("S");
						detCorreo.setCodEstado("0");
						detCorreo.setMsgEstado("CORREO ENVIADO CON EXITO");
					}

				}
			}

			cabecera.setDetalleCorreo(detCorreo);
			logger.debug("DETALLE CORREO: {}", detCorreo);

		} catch (Exception e) {

			logger.error("ERROR SERVICIO Correo {}", e);

		}
		/***********************************
		************************************/
		respuesta.setCabecera(cabecera);
		logger.debug("CABECERA: {}", cabecera);
		logger.debug("SEESPERARDERESPUESTA : ", respuesta.getCabecera());
		respuesta.setNumOperacion(solicitud.getNumOperacion());

		if (peticion.isIndError()) {
			respuesta.setEstado("Errores");
		} else {
			respuesta.setEstado(peticion.getNomfase());
		}

		logger.debug("RespuestaFINAL {}", respuesta);
		return respuesta;
	}

}
