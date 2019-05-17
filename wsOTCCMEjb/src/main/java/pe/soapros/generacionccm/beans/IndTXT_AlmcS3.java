package pe.soapros.generacionccm.beans;

import java.io.Serializable;

public class IndTXT_AlmcS3 implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String indS3TXT,indExito,codEstado,msgEstado,rutaURLDestinoTXT;

	public String getIndS3TXT() {
		return indS3TXT;
	}

	public void setIndS3TXT(String indS3TXT) {
		this.indS3TXT = indS3TXT;
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

	public String getRutaURLDestinoTXT() {
		return rutaURLDestinoTXT;
	}

	public void setRutaURLDestinoTXT(String rutaURLDestinoTXT) {
		this.rutaURLDestinoTXT = rutaURLDestinoTXT;
	}


}
