package pe.soapros.generacionccm.persistance.repository;

import javax.ejb.Local;

import pe.soapros.generacionccm.persistance.domain.Detalle;

@Local
public interface DetalleRepository {
	
	Detalle save(Detalle detalle);

}
