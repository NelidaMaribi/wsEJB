package pe.soapros.generacionccm.beans;

import java.io.Serializable;

public class PeticionOUT implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Origen origen;
	Cabecera cabecera;
	String numOperacion;
	String estado;
	
	public Origen getOrigen() {
		return origen;
	}
	public void setOrigen(Origen origen) {
		this.origen = origen;
	}	
	
	public Cabecera getCabecera() {
		return cabecera;
	}
	public void setCabecera(Cabecera cabecera) {
		this.cabecera = cabecera;
	}
	public String getNumOperacion() {
		return numOperacion;
	}
	public void setNumOperacion(String numOperacion) {
		this.numOperacion = numOperacion;
	}
	
	
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	@Override
	public String toString() {
		return "PeticionOUT [origen=" + origen + ", cabecera=" + cabecera + ", numOperacion=" + numOperacion
				+  "]";
	}
	

	
	
}	