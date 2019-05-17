package pe.soapros.generacionccm.beans;

import java.io.Serializable;

import com.fasterxml.jackson.databind.JsonNode;

public class IndTXTINDetalleS3 implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String indS3TXT;
	JsonNode metadata;
    String rutaURLDestinoTXT;

    public String getIndS3TXT() {
        return indS3TXT;
    }

    public void setIndS3TXT(String indS3TXT) {
        this.indS3TXT = indS3TXT;
    }

 
    
    public JsonNode getMetadata() {
		return metadata;
	}

	public void setMetadata(JsonNode metadata) {
		this.metadata = metadata;
	}

	public String getRutaURLDestinoTXT() {
        return rutaURLDestinoTXT;
    }

    public void setRutaURLDestinoTXT(String rutaURLDestinoTXT) {
        this.rutaURLDestinoTXT = rutaURLDestinoTXT;
    }

	@Override
	public String toString() {
		return "IndTXTINDetalleS3 [indS3TXT=" + indS3TXT + ", rutaURLDestinoTXT=" + rutaURLDestinoTXT + "]";
	}
    
    
    
}
