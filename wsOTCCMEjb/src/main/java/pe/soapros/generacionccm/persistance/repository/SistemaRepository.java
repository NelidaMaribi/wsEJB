package pe.soapros.generacionccm.persistance.repository;

import javax.ejb.Local;

import pe.soapros.generacionccm.persistance.domain.Documento;
import pe.soapros.generacionccm.persistance.domain.Sistema;

@Local
public interface SistemaRepository {

	Sistema getByNombre(String nombre) throws Exception;
	
	Documento getDocumentoSistemaByPlantilla(String idSistema, String codigo) throws Exception;
}
