package pe.soapros.generacionccm.beans;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ResponseDetalleLocal {

	@JsonProperty("tipo")
	private String tipo;
	
	@JsonProperty("archivo")
	private String archivo;
	
	private String Key;
	
	@JsonProperty("status")
	private String status;

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getArchivo() {
		return archivo;
	}

	public void setArchivo(String archivo) {
		this.archivo = archivo;
	}

	public String getKey() {
		return Key;
	}

	public void setKey(String key) {
		Key = key;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "ResponseDetalleLocal [tipo=" + tipo + ", archivo=" + archivo + ", Key=" + Key + ", status=" + status
				+ "]";
	}
		
	

}
