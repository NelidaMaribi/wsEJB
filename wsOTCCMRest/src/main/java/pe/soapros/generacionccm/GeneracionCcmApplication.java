package pe.soapros.generacionccm;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import pe.soapros.generacionccm.controllers.PeticionRetrieveController;
import pe.soapros.generacionccm.controllers.RegistrarRespuestaController;

@ApplicationPath("/")
public class GeneracionCcmApplication extends Application {
	
	@Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> sets = new HashSet<Class<?>>();
        sets.add(PeticionRetrieveController.class);
        sets.add(RegistrarRespuestaController.class);
        return sets;
    }

}