package pe.soapros.generacionccm.beans;

import java.io.Serializable;

public class AlmacenamientoLocal  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private IndPDF_AlmcLocal indPDF;
	private IndTXT_AlmcLocal indTXT;
	private IndHTML_AlmcLocal indHTML;
	public IndPDF_AlmcLocal getIndPDF() {
		return indPDF;
	}
	public void setIndPDF(IndPDF_AlmcLocal indPDF) {
		this.indPDF = indPDF;
	}
	public IndTXT_AlmcLocal getIndTXT() {
		return indTXT;
	}
	public void setIndTXT(IndTXT_AlmcLocal indTXT) {
		this.indTXT = indTXT;
	}
	public IndHTML_AlmcLocal getIndHTML() {
		return indHTML;
	}
	public void setIndHTML(IndHTML_AlmcLocal indHTML) {
		this.indHTML = indHTML;
	}

	
	
	
	
}
