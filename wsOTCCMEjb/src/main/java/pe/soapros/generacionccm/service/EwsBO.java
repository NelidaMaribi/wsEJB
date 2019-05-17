package pe.soapros.generacionccm.service;

import java.io.IOException;

import javax.ejb.Local;

import com.fasterxml.jackson.core.JsonProcessingException;

import services.hpexstream.engine.EngineServiceException_Exception;

@Local
public interface EwsBO {

	public byte[] callEWS(String strSolicitud) throws JsonProcessingException, EngineServiceException_Exception, IOException;
	
}
