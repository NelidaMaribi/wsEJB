package pe.soapros.generacionccm.persistance.repository.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityTransaction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pe.soapros.generacionccm.persistance.domain.Detalle;
import pe.soapros.generacionccm.persistance.domain.Peticion;
import pe.soapros.generacionccm.persistance.repository.PeticionRepository;

@Stateless(name = "PeticionRepository")
//@TransactionManagement(TransactionManagementType.BEAN)
public class PeticionRepositoryImpl extends BaseJPARepository implements PeticionRepository {

	private Logger logger = LoggerFactory.getLogger(PeticionRepositoryImpl.class);
	
	@Override
	public Peticion getPeticion(String operacion) {
		return this.getEntityManager().createQuery("SELECT p FROM Peticion p WHERE p.numOperacion = (:operacion)", Peticion.class).setParameter("operacion", operacion).getSingleResult();
		//return null;
	}
	@Override
	public Peticion save(Peticion pet) {
		
		logger.debug("[PeticionRepository] INI save {}" + pet);
		//EntityTransaction transaction = this.getEntityManager().getTransaction();
		//transaction.begin();
		this.getEntityManager().persist(pet);
		//transaction.commit();
		logger.debug("[PeticionRepository] FIN save {}" + pet);
		return pet;
		
	}

	@Override
	public List<Detalle> detallesByOperacion(String operacion) {
		return this.getEntityManager().createQuery("SELECT d FROM Peticion p, Detalle d WHERE p.idPeticion = d.idPeticion AND p.numOperacion = (:operacion) ORDER BY d.idDetalle ", Detalle.class).setParameter("operacion", operacion).getResultList();
	}

	@Override
	public boolean saveTransaction(Peticion pet, Detalle det) {
		logger.debug("[PeticionRepository] INI saveTransaction {} \n {}" + pet, det);
		//EntityTransaction transaction = this.getEntityManager().getTransaction();
		//transaction.begin();
		this.getEntityManager().persist(pet);
		logger.debug("[PeticionRepository] pet.getIdPeticion() {}" + pet.getIdPeticion());
		//det.setIdPeticion(pet.getIdPeticion());
		this.getEntityManager().persist(det);
		logger.debug("[PeticionRepository] det.getIdDetalle() {}" + det.getIdDetalle());
		//transaction.commit();
		logger.debug("[PeticionRepository] FIN saveTransaction {} \n {}" + pet, det);
		return true;
	}

}
