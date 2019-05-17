package pe.soapros.generacionccm.beans;

import java.io.Serializable;

import com.fasterxml.jackson.databind.JsonNode;


public class IndPDFFilenetIN implements Serializable {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String indFilenetPDF;
	JsonNode propiedades;
	JsonNode contentStream;

    public String getIndFilenetPDF() {
        return indFilenetPDF;
    }

    public void setIndFilenetPDF(String indFilenetPDF) {
        this.indFilenetPDF = indFilenetPDF;
    }

	public JsonNode getPropiedades() {
		return propiedades;
	}

	public void setPropiedades(JsonNode propiedades) {
		this.propiedades = propiedades;
	}

	public JsonNode getContentStream() {
		return contentStream;
	}

	public void setContentStream(JsonNode contentStream) {
		this.contentStream = contentStream;
	}

	@Override
	public String toString() {
		return "IndPDFFilenetIN [indFilenetPDF=" + indFilenetPDF + ", propiedades=" + propiedades + ", contentStream="
				+ contentStream + "]";
	}

   


    
    
}
