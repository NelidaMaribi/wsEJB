package pe.soapros.generacionccm.beans;

import java.io.Serializable;

public class DetalleHTML implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String indHTML,archivo,indExito ,codEstado ,msgEstado;

	public String getIndHTML() {
		return indHTML;
	}

	public void setIndHTML(String indHTML) {
		this.indHTML = indHTML;
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
