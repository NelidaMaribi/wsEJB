package pe.soapros.generacionccm.beans;

import java.io.Serializable;

public class AlmacenamientoS3 implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private IndPDF_AlmcS3 indPDF;
	private IndTXT_AlmcS3 indTXT;
	private IndHTML_AlmcS3 indHTML;

	public IndPDF_AlmcS3 getIndPDF() {
		return indPDF;
	}

	public void setIndPDF(IndPDF_AlmcS3 indPDF) {
		this.indPDF = indPDF;
	}

	public IndTXT_AlmcS3 getIndTXT() {
		return indTXT;
	}

	public void setIndTXT(IndTXT_AlmcS3 indTXT) {
		this.indTXT = indTXT;
	}

	public IndHTML_AlmcS3 getIndHTML() {
		return indHTML;
	}

	public void setIndHTML(IndHTML_AlmcS3 indHTML) {
		this.indHTML = indHTML;
	}
	
	
}
