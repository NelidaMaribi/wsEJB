package pe.soapros.generacionccm.beans;

public class IndTXTLocalIN {
	private String indLocalTXT;
	private String rutaDestinoTXT;
	public String getIndLocalTXT() {
		return indLocalTXT;
	}
	public void setIndLocalTXT(String indLocalTXT) {
		this.indLocalTXT = indLocalTXT;
	}
	public String getRutaDestinoTXT() {
		return rutaDestinoTXT;
	}
	public void setRutaDestinoTXT(String rutaDestinoTXT) {
		this.rutaDestinoTXT = rutaDestinoTXT;
	}
	@Override
	public String toString() {
		return "IndTXTLocalIN [indLocalTXT=" + indLocalTXT + ", rutaDestinoTXT=" + rutaDestinoTXT + "]";
	}
	
	
}
