package pe.soapros.generacionccm.beans;

import java.io.Serializable;

public class IndTXT_AlmcLocal implements Serializable{

	private static final long serialVersionUID = 1L;
	 String indLocalTXT;
	 String indExito;
	 String codEstado;
	 String msgEstado;
	 String rutaDestinoTxt;
	public String getIndLocalTXT() {
		return indLocalTXT;
	}
	public void setIndLocalTXT(String indLocalTXT) {
		this.indLocalTXT = indLocalTXT;
	}
	public String getIndExito() {
		return indExito;
	}
	public void setIndExito(String indExito) {
		this.indExito = indExito;
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
	public String getRutaDestinoTxt() {
		return rutaDestinoTxt;
	}
	public void setRutaDestinoTxt(String rutaDestinoTxt) {
		this.rutaDestinoTxt = rutaDestinoTxt;
	}
	
	
	
}
