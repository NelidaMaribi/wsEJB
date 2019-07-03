package pe.soapros.generacionccm.beans;

public class IndPDF_AlmcLocal {

	private String indLocalPDF;
	private String indExito;
	private String codEstado;
	private String msgEstado;
	private String rutaDestinoPDF;
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
	@Override
	public String toString() {
		return "IndPDF_AlmcLocal [indLocalPDF=" + indLocalPDF + ", indExito=" + indExito + ", codEstado=" + codEstado
				+ ", msgEstado=" + msgEstado + ", rutaDestinoPDF=" + rutaDestinoPDF + "]";
	}
	
	
}
