package pe.soapros.generacionccm.beans;

import java.io.Serializable;

public class AlmacenamientoFilenet implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	indPDF_AlmcFilenet indPDF;
	indTXT_AlmcFilenet2 indTXT;
	indHTML_AlmcFilenet3 indHTML;
	
	public indPDF_AlmcFilenet getIndPDF() {
		return indPDF;
	}
	public void setIndPDF(indPDF_AlmcFilenet indPDF) {
		this.indPDF = indPDF;
	}
	public indTXT_AlmcFilenet2 getIndTXT() {
		return indTXT;
	}
	public void setIndTXT(indTXT_AlmcFilenet2 indTXT) {
		this.indTXT = indTXT;
	}
	public indHTML_AlmcFilenet3 getIndHTML() {
		return indHTML;
	}
	public void setIndHTML(indHTML_AlmcFilenet3 indHTML) {
		this.indHTML = indHTML;
	}


	
}
