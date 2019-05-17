package pe.soapros.generacionccm.beans;

import java.io.Serializable;

public class DetalleRespuesta implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String numero;
	String archivo;
	String indExito;
	String codEstado;
	String msgEstado;
	String valorRetorno;
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
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
	public String getValorRetorno() {
		return valorRetorno;
	}
	public void setValorRetorno(String valorRetorno) {
		this.valorRetorno = valorRetorno;
	}
	
	
}
