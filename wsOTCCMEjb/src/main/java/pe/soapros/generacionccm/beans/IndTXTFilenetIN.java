
package pe.soapros.generacionccm.beans;

import java.io.Serializable;

import com.fasterxml.jackson.databind.JsonNode;

public class IndTXTFilenetIN implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String indFilenetTXT;
	JsonNode propiedades;
	JsonNode contentStream;

    public String getIndFilenetTXT() {
        return indFilenetTXT;
    }

    public void setIndFilenetTXT(String indFilenetTXT) {
        this.indFilenetTXT = indFilenetTXT;
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
		return "IndTXTFilenetIN [indFilenetTXT=" + indFilenetTXT + ", propiedades=" + propiedades + ", contentStream="
				+ contentStream + "]";
	}

    
    
    
    
}
