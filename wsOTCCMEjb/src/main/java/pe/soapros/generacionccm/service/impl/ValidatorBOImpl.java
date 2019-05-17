package pe.soapros.generacionccm.service.impl;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pe.soapros.generacionccm.persistance.domain.Documento;
import pe.soapros.generacionccm.persistance.domain.Sistema;
import pe.soapros.generacionccm.persistance.repository.SistemaRepository;
import pe.soapros.generacionccm.service.ValidatorBO;

@Stateless(name = "ValidatorBO")
@TransactionManagement(TransactionManagementType.BEAN)
public class ValidatorBOImpl implements ValidatorBO{

	@EJB(name = "SistemaRepository")
	private SistemaRepository sistemaRepository;
	
	private Logger logger = LoggerFactory.getLogger(ValidatorBOImpl.class);
	
	public Boolean validateSistema(String sistema) throws Exception {
		logger.debug("validateSistema: {}",sistema);
		boolean rpta = false;
		try {
			Sistema sist = new Sistema();
			sist = sistemaRepository.getByNombre(sistema);
					
			if(sist!= null && sist.getNombre().equals(sistema)) {
				logger.debug("Validado");
				logger.debug("Query: " + sist.toString());
				rpta = true;
			}
		} catch (Exception e) {
			throw new Exception(
                    "[validateSistema] No se encontró el sistema: " + sistema);
		}
		return rpta;
	}
	
	public Boolean validatePlantilla(String sistema, String codigo) throws Exception {
		logger.debug("validatePlantilla Sistema: {} Codigo: {}",sistema, codigo);
		boolean rpta = false;
		try {
			Documento doc = sistemaRepository.getDocumentoSistemaByPlantilla(sistema, codigo);
			logger.debug("Documento recuperado {}", doc);
			
			if(doc != null) {
				logger.debug("Validado");
				logger.debug("Query: " + doc.toString());
				rpta = true;
			}
		} catch (Exception e) {
			throw new Exception(
	                "[validateSistema] No se encontró el la plantilla: Sistema {" + sistema + "} Codigo {" + codigo + "}");
		}
		return rpta;
	}
	
	public Documento getPlantilla(String sistema, String codigo) throws Exception {
		
		logger.debug("validatePlantilla Sistema: {} Codigo: {}",sistema, codigo);
		Documento doc = null;
		
		try {
			doc = sistemaRepository.getDocumentoSistemaByPlantilla(sistema, codigo);
			logger.debug("Documento recuperado {}", doc);		
			
		} catch (Exception e) {
			throw new Exception(
	                "[validateSistema] No se encontró el la plantilla: Sistema {" + sistema + "} Codigo {" + codigo + "}");
		}
		return doc;
		
	}
}
