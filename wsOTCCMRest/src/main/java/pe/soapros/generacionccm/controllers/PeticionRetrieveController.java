package pe.soapros.generacionccm.controllers;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pe.soapros.generacionccm.beans.Entrada_Peticion;
import pe.soapros.generacionccm.beans.PeticionOUT;
import pe.soapros.generacionccm.service.PeticionBO;
import pe.soapros.generacionccm.utils.ExceptionsUtils;

@Path("/consultar")
public class PeticionRetrieveController {
	
	private Logger logger = LoggerFactory.getLogger(RegistrarRespuestaController.class);

	
	@EJB(name = "PeticionBO")
	private PeticionBO peticionBO;

	private ExceptionsUtils exceptionsUtils = new ExceptionsUtils();
	
	@POST
	@Path("/pedido")     
    @Produces("application/json; charset=UTF-8")
	@Consumes(MediaType.APPLICATION_JSON)
	public PeticionOUT getTodasPeticiones(Entrada_Peticion peticion) {
		PeticionOUT rpta = null;
		try {
			logger.debug("getTodasPeticiones parametro: {}", peticion);
			rpta = peticionBO.consultarPeticion(peticion);
			if (rpta == null) {
				logger.error("Info getTodasPeticiones: No hay ningún pedido");
				return new PeticionOUT();
			} else {
				return rpta;
			}
		} catch (Exception e) {
			logger.error("ERROR getTodasPeticiones: {}", e.getMessage(), e);
			Response.ResponseBuilder resp_builder=Response.status(Response.Status.BAD_REQUEST);
			resp_builder.entity(exceptionsUtils.setException(e, "uri=/consultar/pedido"));
			throw new WebApplicationException(resp_builder.build());
		}
		
	}

}
