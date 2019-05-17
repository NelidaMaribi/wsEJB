package pe.soapros.generacionccm.beans;

import java.io.Serializable;

import com.fasterxml.jackson.databind.JsonNode;

public class IndPDFINDetalleS3 implements Serializable {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String indS3PDF;

    private String rutaURLDestinoPDF;
    
    private JsonNode metadata;
	
	public String getIndS3PDF() {
		return indS3PDF;
	}
	public void setIndS3PDF(String indS3PDF) {
		this.indS3PDF = indS3PDF;
	}
	public String getRutaURLDestinoPDF() {
		return rutaURLDestinoPDF;
	}
	public void setRutaURLDestinoPDF(String rutaURLDestinoPDF) {
		this.rutaURLDestinoPDF = rutaURLDestinoPDF;
	}	

	public JsonNode getMetadata() {
		return metadata;
	}
	public void setMetadata(JsonNode metadata) {
		this.metadata = metadata;
	}
	
	@Override
	public String toString() {
		return "IndPDFINDetalleS3 [indS3PDF=" + indS3PDF + ", rutaURLDestinoPDF=" + rutaURLDestinoPDF + "]";
	}
    
    
}
