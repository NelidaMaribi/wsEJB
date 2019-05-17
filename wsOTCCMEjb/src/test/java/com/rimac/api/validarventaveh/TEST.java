package com.rimac.api.validarventaveh;

import com.rimac.api.validarventaveh.dao.PersistenceHelper;

import javax.persistence.EntityManager;

import org.junit.Ignore;
@Ignore
public class TEST {

    public EntityManager entityManager;

    public TEST() {
        this.entityManager = PersistenceHelper.getEntityManager();
    }

}
