package pe.soapros.generacionccm.beans;

import java.io.Serializable;

public class DetalleSMS implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String indSMS;
	private DetalleRespuesta numeroRespuesta;

	
	public DetalleRespuesta getNumeroRespuesta() {
		return numeroRespuesta;
	}

	public void setNumeroRespuesta(DetalleRespuesta numeroRespuesta) {
		this.numeroRespuesta = numeroRespuesta;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getIndSMS() {
		return indSMS;
	}

	public void setIndSMS(String indSMS) {
		this.indSMS = indSMS;
	}

}
