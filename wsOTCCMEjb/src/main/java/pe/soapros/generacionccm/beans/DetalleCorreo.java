package pe.soapros.generacionccm.beans;

import java.io.Serializable;

public class DetalleCorreo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String indCorreo,indExito ,codEstado ,msgEstado  ;
	public String getIndCorreo() {
		return indCorreo;
	}
	public void setIndCorreo(String indCorreo) {
		this.indCorreo = indCorreo;
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
