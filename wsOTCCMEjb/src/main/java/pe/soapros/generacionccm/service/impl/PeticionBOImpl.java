package pe.soapros.generacionccm.service.impl;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import pe.soapros.generacionccm.beans.AlmacenamientoS3;
import pe.soapros.generacionccm.beans.Cabecera;
import pe.soapros.generacionccm.beans.CabeceraIN;
import pe.soapros.generacionccm.beans.DetallePDF;
import pe.soapros.generacionccm.beans.DetalleSMS;
import pe.soapros.generacionccm.beans.DetalleServicio;
import pe.soapros.generacionccm.beans.DetalleTXT;
import pe.soapros.generacionccm.beans.DetalleTrazabilidad;
import pe.soapros.generacionccm.beans.Entrada_Peticion;
import pe.soapros.generacionccm.beans.IndHTML_AlmcS3;
import pe.soapros.generacionccm.beans.IndPDF_AlmcS3;
import pe.soapros.generacionccm.beans.IndTXT_AlmcS3;
import pe.soapros.generacionccm.beans.PeticionOUT;
import pe.soapros.generacionccm.beans.ResponseS3;
import pe.soapros.generacionccm.beans.Respuesta;
import pe.soapros.generacionccm.beans.Solicitud;
import pe.soapros.generacionccm.beans.DetalleHTML;
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
		//det.setIdPeticion(p.getIdPeticion());
		//detalleRepository.save(det);
		
		//peticionRepository.saveTransaction(pet, det);
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
		//logger.debug("FIN [consultarPeticion]");
		
		logger.debug("consultarPeticion: {}", solicitud);
		logger.debug("numero operación: {}", solicitud.getNumOperacion());
		Peticion petDetalles = peticionRepository.getPeticion(solicitud.getNumOperacion());
		
		List<Detalle> detalles = petDetalles.getDetalles(); 
				
				//peticionRepository.detallesByOperacion(solicitud.getNumOperacion());
		Peticion peticion = peticionRepository.getPeticion(solicitud.getNumOperacion());
		
		if (detalles == null || detalles.size() == 0) {
			return null;
		}
		
		logger.debug("Detalles: {}", detalles.toString());


		HashMap<String, Detalle> hmap = new HashMap<String, Detalle>();

		for (Detalle det : detalles) {

			hmap.put(det.getNomFase(), det);

		}

		logger.debug("Input Solicitado: {}", hmap.get("Solicitado"));

		
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

		//boolean swSeguir = true;

		if (hmap.get("Generacion") != null) {
			
			logger.debug("DOCUMENTOS GENERADOS");

			if (cabIn.getDetallePDF().getIndPDF().equals("S")) {
				
				if(hmap.get("Generacion").isIndError()) {
					detPDF.setIndExito("N");
					detPDF.setCodEstado("-1");
					detPDF.setMsgEstado("DOCUMENTOS NO GENERADOS");
				}else {
					detPDF.setIndExito("S");
					detPDF.setCodEstado("0");
					detPDF.setMsgEstado("DOCUMENTOS GENERADOS");
				}
				
				
			}

			if (cabIn.getDetalleTXT().getIndTXT().equals("S")) {
				
				if(hmap.get("Generacion").isIndError()) {
					detTXT.setIndExito("N");
					detTXT.setCodEstado("-1");
					detTXT.setMsgEstado("DOCUMENTOS NO GENERADOS");
				}else {
					detTXT.setIndExito("S");
					detTXT.setCodEstado("0");
					detTXT.setMsgEstado("DOCUMENTOS GENERADOS");
				}
				
				
			}

			if (cabIn.getDetalleHTML().getIndHTML().equals("S")) {
				
				if(hmap.get("Generacion").isIndError()) {
					detHTML.setIndExito("N");
					detHTML.setCodEstado("-1");
					detHTML.setMsgEstado("DOCUMENTOS NO GENERADOS");
				}else {
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
				
				 
				logger.debug("JSON S3: {}", hmap.get("Documentos Subidos S3"));
				
				

				if (cabIn.getDetalleS3().getIndPDF().getIndS3PDF().equals("S")) {
					
					if(hmap.get("S3").isIndError()) {
						pdfS3.setIndExito("N");
						pdfS3.setCodEstado("-1");
						pdfS3.setMsgEstado(hmap.get("S3").getInputJson());
					}else {
						
						ResponseS3[] responseS3 = mapper.readValue(hmap.get("S3").getInputJson(), ResponseS3[].class);						
						logger.debug("Response S3[0] {}", responseS3[0].toString());
						
						pdfS3.setIndExito("S");
						pdfS3.setCodEstado("0");
						pdfS3.setMsgEstado("Documento Subido a S3");
						pdfS3.setRutaURLDestinoPDF(responseS3[0].getLocation());
					}
					
				}

				if (cabIn.getDetalleS3().getIndTXT().getIndS3TXT().equals("S")) {
					
					if(hmap.get("S3").isIndError()) {
						txtS3.setIndExito("N");
						txtS3.setCodEstado("-1");
						txtS3.setMsgEstado("Documento No Subido a S3");
						txtS3.setRutaURLDestinoTXT(hmap.get("S3").getInputJson());
					}else {
						ResponseS3[] responseS3 = mapper.readValue(hmap.get("S3").getInputJson(), ResponseS3[].class);						
						logger.debug("Response S3[0] {}", responseS3[0].toString());
						txtS3.setIndExito("S");
						txtS3.setCodEstado("0");
						txtS3.setMsgEstado("Documento Subido a S3");
						txtS3.setRutaURLDestinoTXT(responseS3[1].getLocation());
					}
					
				}

				if (cabIn.getDetalleS3().getIndHTML().getIndS3HTML().equals("S")) {
					
					if(hmap.get("S3").isIndError()) {
						htmlS3.setIndExito("N");
						htmlS3.setCodEstado("-1");
						htmlS3.setMsgEstado("Documento No Subido a S3");
						htmlS3.setRutaURLDestinoHTML(hmap.get("S3").getInputJson());
					}else {
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

		/**********************************************
		 * ENVIO A TRAZABILIDAD
		 *********************************************/

		DetalleTrazabilidad detTraz = new DetalleTrazabilidad();

		try {
			detTraz.setIndTrazabilidad(sol.getCabecera().getDetalleTrazabilidadCorreo().getIndTrazabilidad());
			logger.debug("INDTRAZABILIDAD: {}",
					sol.getCabecera().getDetalleTrazabilidadCorreo().getIndTrazabilidad());

			if (hmap.get("Trazabilidad") != null) {

				logger.debug("TRAZABILIDAD");

				if (cabIn.getDetalleTrazabilidadCorreo().getIndTrazabilidad().equals("S")) {
					
					if(hmap.get("Trazabilidad").isIndError()) {
						
						detTraz.setIndExito("N");
						detTraz.setCodEstado("-1");
						detTraz.setMsgEstado("Trazabilidad no realizada");
						detTraz.setValorretorno(hmap.get("Trazabilidad").getInputJson());
						
					}else {
					
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

		/***************************************************************
		 * DETALLE DEL SERVICIO GENERICO
		 *************************************************************/

		DetalleServicio detServ = new DetalleServicio();

		try {
			detServ.setIndServicio(sol.getCabecera().getDetalleServicioGenerico().getIndServicioGenerico());
			logger.debug("IND SERVICIO: {}",
					sol.getCabecera().getDetalleServicioGenerico().getIndServicioGenerico());

			if (hmap.get("ServicioGenerico") != null) {

				logger.debug("SERVICIO GENERICO");
				if (cabIn.getDetalleServicioGenerico().getIndServicioGenerico().equals("S")) {
					
					if(hmap.get("ServicioGenerico").isIndError()) {
						detServ.setIndExito("N");
						detServ.setCodEstado("-1");
						detServ.setMsgEstado("Servicio Genérico no realizado");
						detServ.setValorretorno(hmap.get("ServicioGenerico").getInputJson());
					}else {
						detServ.setIndExito("S");
						detServ.setCodEstado("0");
						detServ.setMsgEstado("Servicio Genérico realizado");
						detServ.setValorretorno(hmap.get("ServicioGenerico").getInputJson());
					}
					
				}

			} 
			
			cabecera.setDetalleServicio(detServ);
			logger.debug("DETSERV: {}", detServ);

		} catch (Exception e) {

			logger.error("ERROR SERVICIO GENERICO {}", e);

		}

		/***********************************
		 * + DETALLE DEL ENVIO A SMS
		 ***********************************/

		DetalleSMS detSMS = new DetalleSMS();

		try {
			if (hmap.get("Envio SMS") != null) {

				logger.debug("ENVIO SMS");
				detSMS.setIndSMS("S");

			} else if (hmap.get("Envio SMS") == null ) {

				logger.debug("ENVIO SMS NO");
				detSMS.setIndSMS("N");

			
			} 

			cabecera.setEnvioSMS(detSMS);
			logger.debug("ENVIO SMS: {}", detSMS);
		} catch (Exception e) {

			logger.error("ERROR SMS {}", e);
		}

		respuesta.setCabecera(cabecera);
		logger.debug("CABECERA: {}", cabecera);

		respuesta.setNumOperacion(solicitud.getNumOperacion());
		
		if(peticion.isIndError()) {
			respuesta.setEstado("Errores");
		}else {
			respuesta.setEstado(peticion.getNomfase());
		}
		
		logger.debug("Respuesta {}", respuesta);
		return respuesta;
	}

}
