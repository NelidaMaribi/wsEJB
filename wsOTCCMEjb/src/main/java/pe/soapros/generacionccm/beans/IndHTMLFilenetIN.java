package pe.soapros.generacionccm.beans;

import java.io.Serializable;

import com.fasterxml.jackson.databind.JsonNode;

public class IndHTMLFilenetIN implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String indFilenetHTML;
	JsonNode propiedades;
	JsonNode contentStream;

    public String getIndFilenetHTML() {
        return indFilenetHTML;
    }

    public void setIndFilenetHTML(String indFilenetHTML) {
        this.indFilenetHTML = indFilenetHTML;
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
		return "IndHTMLFilenetIN [indFilenetHTML=" + indFilenetHTML + ", propiedades=" + propiedades
				+ ", contentStream=" + contentStream + "]";
	}

   
    
    
    
}
