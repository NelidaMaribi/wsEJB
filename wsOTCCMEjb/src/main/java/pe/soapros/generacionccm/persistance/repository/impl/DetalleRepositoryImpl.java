package pe.soapros.generacionccm.persistance.repository.impl;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityTransaction;

import pe.soapros.generacionccm.persistance.domain.Detalle;
import pe.soapros.generacionccm.persistance.repository.DetalleRepository;

@Stateless(name = "DetalleRepository")
@TransactionManagement(TransactionManagementType.BEAN)
public class DetalleRepositoryImpl extends BaseJPARepository implements DetalleRepository {

	@Override
	public Detalle save(Detalle detalle) {
		EntityTransaction transaction = this.getEntityManager().getTransaction();
		transaction.begin();
		this.getEntityManager().persist(detalle);
		transaction.commit();
		return detalle;
	}
	

}
