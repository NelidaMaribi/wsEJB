package pe.soapros.generacionccm.persistance.repository.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class BaseJPARepository {
	
	protected EntityManager entityManager;

	public EntityManager getEntityManager() {
		return entityManager;
	}

	@PersistenceContext(unitName = "DS_JOB_OT")
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
}