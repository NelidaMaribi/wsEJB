package pe.soapros.generacionccm.persistance.repository;

import java.util.List;

import javax.ejb.Local;

import pe.soapros.generacionccm.persistance.domain.Detalle;
import pe.soapros.generacionccm.persistance.domain.Peticion;

@Local
public interface PeticionRepository {

	Peticion save(Peticion pet);
	boolean saveTransaction(Peticion pet, Detalle det);
	List<Detalle> detallesByOperacion(String operacion);
	Peticion getPeticion (String operacion);
	
}
