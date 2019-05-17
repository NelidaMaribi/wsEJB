package pe.soapros.generacionccm.beans;

import java.io.Serializable;

public class IndPDF_AlmcS3 implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String indS3PDF,indExito,codEstado,msgEstado,rutaURLDestinoPDF;

	public String getIndS3PDF() {
		return indS3PDF;
	}
	public void setIndS3PDF(String indS3PDF) {
		this.indS3PDF = indS3PDF;
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
	public String getRutaURLDestinoPDF() {
		return rutaURLDestinoPDF;
	}
	public void setRutaURLDestinoPDF(String rutaURLDestinoPDF) {
		this.rutaURLDestinoPDF = rutaURLDestinoPDF;
	}
	
}
