package pe.soapros.generacionccm.beans;

import java.io.Serializable;

public class IndPDF_AlmcLocal implements Serializable {
	private static final long serialVersionUID = 1L;
	 String indLocalPDF;
	 String indExito;
	 String codEstado;
	 String msgEstado;
	 String rutaDestinoPDF;
	public String getIndLocalPDF() {
		return indLocalPDF;
	}
	public void setIndLocalPDF(String indLocalPDF) {
		this.indLocalPDF = indLocalPDF;
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
	public String getRutaDestinoPDF() {
		return rutaDestinoPDF;
	}
	public void setRutaDestinoPDF(String rutaDestinoPDF) {
		this.rutaDestinoPDF = rutaDestinoPDF;
	}
	

	
	
}
