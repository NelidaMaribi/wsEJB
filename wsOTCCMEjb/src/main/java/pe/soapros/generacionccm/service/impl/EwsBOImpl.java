package pe.soapros.generacionccm.service.impl;

import java.io.IOException;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

import pe.soapros.generacionccm.beans.Solicitud;
import pe.soapros.generacionccm.service.EwsBO;
import services.hpexstream.engine.DriverFile;
import services.hpexstream.engine.EngineService;
import services.hpexstream.engine.EngineServiceException_Exception;
import services.hpexstream.engine.EngineWebService;
import services.hpexstream.engine.EwsComposeRequest;
import services.hpexstream.engine.EwsComposeResponse;

@Stateless(name = "EwsBO")
@TransactionManagement(TransactionManagementType.BEAN)
public class EwsBOImpl implements EwsBO{

	private Logger logger = LoggerFactory.getLogger(PeticionBOImpl.class);
	private ObjectMapper mapper = new ObjectMapper();
	
	@Override
	public byte[] callEWS(String strSolicitud) throws EngineServiceException_Exception, IOException {
		Solicitud solicitud = mapper.readValue(strSolicitud, Solicitud.class);
		
		
		logger.debug("callEWS parametro {}", strSolicitud);

		//String jsonInput = mapper.writeValueAsString(solicitud);
		String jsonInput = strSolicitud;
		logger.debug("JSON Input: {}", jsonInput);

		EngineService test = new EngineService();

		EngineWebService test1 = test.getEngineServicePort();
		logger.debug("EngineWebService: {}", test1);

		EwsComposeRequest ewsComposeRequest = new EwsComposeRequest();

		DriverFile value = new DriverFile();
		value.setDriver(jsonInput.getBytes());
		value.setFileName("dd:input");
		logger.debug("Driver: {} ", value);

		logger.debug("PUB: " + solicitud.getCabecera().getDetallePDF().getCodigoPlantilla() + ".pub");

		ewsComposeRequest.setDriver(value);
		ewsComposeRequest.setPubFile(solicitud.getCabecera().getDetallePDF().getCodigoPlantilla() + ".pub");

		EwsComposeResponse response = test1.compose(ewsComposeRequest);
		logger.debug("Response: {}", response.getStatusMessage());

		byte[] pdf = null;

		try {
			if ((response != null) && (response.getFiles().get(0) != null)) {
				pdf = response.getFiles().get(0).getFileOutput();
			}
		} catch (Exception e) {
			logger.error("Error: {}", e);
		}

		return pdf;
	}

}
