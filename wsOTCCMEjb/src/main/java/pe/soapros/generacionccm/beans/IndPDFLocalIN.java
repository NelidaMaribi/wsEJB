package pe.soapros.generacionccm.beans;

public class IndPDFLocalIN {
	
	private String indLocalPDF;
	private String rutaDestinoPDF;
	public String getIndLocalPDF() {
		return indLocalPDF;
	}
	public void setIndLocalPDF(String indLocalPDF) {
		this.indLocalPDF = indLocalPDF;
	}
	public String getRutaDestinoPDF() {
		return rutaDestinoPDF;
	}
	public void setRutaDestinoPDF(String rutaDestinoPDF) {
		this.rutaDestinoPDF = rutaDestinoPDF;
	}
	@Override
	public String toString() {
		return "IndPDFLocalIN [indLocalPDF=" + indLocalPDF + ", rutaDestinoPDF=" + rutaDestinoPDF + "]";
	}
	
	

}
