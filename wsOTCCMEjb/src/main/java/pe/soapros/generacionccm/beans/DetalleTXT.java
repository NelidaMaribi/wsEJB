package pe.soapros.generacionccm.beans;

import java.io.Serializable;

public class DetalleTXT implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String indTXT,archivo,indExito ,codEstado ,msgEstado  ;

	public String getIndTXT() {
		return indTXT;
	}

	public void setIndTXT(String indTXT) {
		this.indTXT = indTXT;
	}

	public String getArchivo() {
		return archivo;
	}

	public void setArchivo(String archivo) {
		this.archivo = archivo;
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


	
}
