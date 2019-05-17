package com.rimac.api.validarventaveh.dao;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Vector;

public class PersistenceHelper {
    private static final EntityManager entityManager;
    static {
        wrapClassLoader();
        entityManager = Persistence.createEntityManagerFactory("DS_ASESOR_TEST").
                createEntityManager();
    }
    public static EntityManager getEntityManager() {
        return entityManager;
    };

    private static void wrapClassLoader() {
        ClassLoader current = Thread.currentThread().getContextClassLoader();
        Thread.currentThread().setContextClassLoader(new ClassLoader(current) {
            public Enumeration<URL> getResources(String name) throws IOException {
                if ("META-INF/persistence.xml".equals(name)) {
                    Enumeration<URL> urls = super.getResources(name);
                    Vector<URL> vector = new Vector<URL>();
                    if (urls.hasMoreElements()) {
                        String str = urls.nextElement().toExternalForm();
                        str = str.replaceAll("build/classes", "jpa_unittest");
                        // replaceAll solution works for eclipse, need additional replaceAll for ANT/other.
                        vector.add(new URL(str));
                    }
                    return vector.elements();
                } else {
                    return super.getResources(name);
                }
            };
        });
    }
}
