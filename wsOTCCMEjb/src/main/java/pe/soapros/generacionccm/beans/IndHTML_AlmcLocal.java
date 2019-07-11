package pe.soapros.generacionccm.beans;

import java.io.Serializable;

public class IndHTML_AlmcLocal implements Serializable{

	private static final long serialVersionUID = 1L;
	 String indLocalHTML;
	 String indEstado;
	 String codEstado;
	 String msgEstado;
	 String rutaDestinoHTML;
	public String getIndLocalHTML() {
		return indLocalHTML;
	}
	public void setIndLocalHTML(String indLocalHTML) {
		this.indLocalHTML = indLocalHTML;
	}
	public String getIndEstado() {
		return indEstado;
	}
	public void setIndEstado(String indEstado) {
		this.indEstado = indEstado;
	}
	public String getCodEstado() {
		return codEstado;
	}
	public void setCodEstado(String codEstado) {
		this.codEstado = codEstado;
	}
	public String getMsgEstado() {
		return msgEstado;
	}
	public void setMsgEstado(String msgEstado) {
		this.msgEstado = msgEstado;
	}
	public String getRutaDestinoHTML() {
		return rutaDestinoHTML;
	}
	public void setRutaDestinoHTML(String rutaDestinoHTML) {
		this.rutaDestinoHTML = rutaDestinoHTML;
	}
	
	
	
}
