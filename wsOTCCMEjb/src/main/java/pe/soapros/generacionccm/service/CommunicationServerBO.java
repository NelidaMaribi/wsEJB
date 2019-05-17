package pe.soapros.generacionccm.service;

import java.io.IOException;

import javax.ejb.Local;

import pe.soapros.generacionccm.beans.ResponseFlujoCS;
import pe.soapros.generacionccm.beans.ResponseTokenCS;
import pe.soapros.generacionccm.beans.Respuesta;

@Local
public interface CommunicationServerBO {

	public ResponseTokenCS getToken() throws IOException, Exception;
	
	public Respuesta callFlujoUnico(String strSolicitud) throws IOException, Exception;
	
	public byte[] callPreview(String strSolicitud) throws IOException, Exception;
	
	public Respuesta callOrquestador(String strSolicitud) throws IOException, Exception;
}
