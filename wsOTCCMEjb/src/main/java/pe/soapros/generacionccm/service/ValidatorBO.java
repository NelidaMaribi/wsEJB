package pe.soapros.generacionccm.service;

import javax.ejb.Remote;

import pe.soapros.generacionccm.persistance.domain.Documento;

@Remote
public interface ValidatorBO {

	public Boolean validateSistema(String sistema) throws Exception;
	public Boolean validatePlantilla(String sistema, String codigo) throws Exception;	
	public Documento getPlantilla(String sistema, String codigo) throws Exception;
}
