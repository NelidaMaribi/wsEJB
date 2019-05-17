package pe.soapros.generacionccm.beans;

import java.io.Serializable;

import com.fasterxml.jackson.databind.JsonNode;

public class IndHTMLINDetalleS3 implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String indS3HTML;
	JsonNode metadata;
    String rutaURLDestinoHTML;

    public String getIndS3HTML() {
        return indS3HTML;
    }

    public void setIndS3HTML(String indS3HTML) {
        this.indS3HTML = indS3HTML;
    }
   
    
    
    public JsonNode getMetadata() {
		return metadata;
	}

	public void setMetadata(JsonNode metadata) {
		this.metadata = metadata;
	}

	public String getRutaURLDestinoHTML() {
        return rutaURLDestinoHTML;
    }

    public void setRutaURLDestinoHTML(String rutaURLDestinoHTML) {
        this.rutaURLDestinoHTML = rutaURLDestinoHTML;
    }

	@Override
	public String toString() {
		return "IndHTMLINDetalleS3 [indS3HTML=" + indS3HTML + ", rutaURLDestinoHTML=" + rutaURLDestinoHTML + "]";
	}
    
    
    
}
