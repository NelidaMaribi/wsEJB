package pe.soapros.generacionccm.beans;

import java.io.Serializable;

//import javax.validation.constraints.NotNull;

public class Entrada_Peticion implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//@NotNull(message="Origen es obligatorio")
	Origen origen;
	
	//@NotNull(message="Número de Operación es obligatorio")
	String numOperacion;
	
	public Origen getOrigen() {
		return origen;
	}
	public void setOrigen(Origen origen) {
		this.origen = origen;
	}
	public String getNumOperacion() {
		return numOperacion;
	}
	public void setNumOperacion(String numOperacion) {
		this.numOperacion = numOperacion;
	}
	
	
	
}
