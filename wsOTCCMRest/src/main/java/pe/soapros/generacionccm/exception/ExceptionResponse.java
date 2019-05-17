package pe.soapros.generacionccm.exception;

import java.util.List;

import pe.soapros.generacionccm.beans.Origen;

public class ExceptionResponse {

	private Origen origen;
	private String numOperacion;
	private String timestamp;
	private String error;
	private List<String> details;

	public ExceptionResponse(String timestamp, String message, List<String> details) {
		super();
		this.origen = null;
		this.numOperacion = null;
		this.timestamp = timestamp;
		this.error = message;
		this.details = details;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public String getError() {
		return error;
	}

	public List<String> getDetails() {
		return details;
	}

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
