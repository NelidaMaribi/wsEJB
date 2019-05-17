package pe.soapros.generacionccm.beans;

import java.io.Serializable;

public class IndHTML_AlmcS3 implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String indS3HTML,indExito,codEstado,msgEstado,rutaURLDestinoHTML;

	public String getIndS3HTML() {
		return indS3HTML;
	}

	public void setIndS3HTML(String indS3HTML) {
		this.indS3HTML = indS3HTML;
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

	public String getRutaURLDestinoHTML() {
		return rutaURLDestinoHTML;
	}

	public void setRutaURLDestinoHTML(String rutaURLDestinoHTML) {
		this.rutaURLDestinoHTML = rutaURLDestinoHTML;
	}
	
}
