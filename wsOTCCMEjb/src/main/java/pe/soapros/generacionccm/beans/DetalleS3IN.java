
package pe.soapros.generacionccm.beans;

import java.io.Serializable;

public class DetalleS3IN implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//@NotNull(message = "detalleS3: Identificador de PDF en S3 es obligatorio")
	//@Size(min = 1, max = 1, message = "detalleS3: El tamaño del identificador PDF en S3 es sólo de un caracter")
    private IndPDFINDetalleS3 indPDF;
	
	//@NotNull(message = "detalleS3: Identificador de TXT en S3 es obligatorio")
	//@Size(min = 0, max = 1, message = "detalleS3: El tamaño del identificador TXT en S3 es sólo de un caracter")
    private IndTXTINDetalleS3 indTXT;
    
	//@NotNull(message = "detalleS3: Identificador de HTML en S3 es obligatorio")
	//@Size(min = 0, max = 1, message = "detalleS3: El tamaño del identificador HTML en S3 es sólo de un caracter")
    private IndHTMLINDetalleS3 indHTML;

	public IndPDFINDetalleS3 getIndPDF() {
		return indPDF;
	}

	public void setIndPDF(IndPDFINDetalleS3 indPDF) {
		this.indPDF = indPDF;
	}

	public IndTXTINDetalleS3 getIndTXT() {
		return indTXT;
	}

	public void setIndTXT(IndTXTINDetalleS3 indTXT) {
		this.indTXT = indTXT;
	}

	public IndHTMLINDetalleS3 getIndHTML() {
		return indHTML;
	}

	public void setIndHTML(IndHTMLINDetalleS3 indHTML) {
		this.indHTML = indHTML;
	}

	@Override
	public String toString() {
		return "DetalleS3IN [indPDF=" + indPDF + ", indTXT=" + indTXT + ", indHTML=" + indHTML + "]";
	}

    

  
}
