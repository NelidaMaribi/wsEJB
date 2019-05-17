package pe.soapros.generacionccm.beans;

import java.io.Serializable;

public class DetalleTrazabilidad implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String indTrazabilidad,indExito ,codEstado ,msgEstado ,valorretorno ;

	
	public String getIndTrazabilidad() {
		return indTrazabilidad;
	}

	public void setIndTrazabilidad(String indTrazabilidad) {
		this.indTrazabilidad = indTrazabilidad;
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

	public String getValorretorno() {
		return valorretorno;
	}

	public void setValorretorno(String valorretorno) {
		this.valorretorno = valorretorno;
	}
	

}
