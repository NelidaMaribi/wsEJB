package pe.soapros.generacionccm.service;

import java.io.IOException;

import javax.ejb.Remote;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import pe.soapros.generacionccm.beans.Entrada_Peticion;
import pe.soapros.generacionccm.beans.PeticionOUT;
import pe.soapros.generacionccm.beans.Respuesta;
import pe.soapros.generacionccm.beans.Solicitud;

@Remote
public interface PeticionBO {
	
	public String procesarPeticion(String strRes, String strSol) throws JsonProcessingException, IOException;
	
	public PeticionOUT consultarPeticion(Entrada_Peticion solicitud) throws JsonParseException, JsonMappingException, IOException;
	
	

}
