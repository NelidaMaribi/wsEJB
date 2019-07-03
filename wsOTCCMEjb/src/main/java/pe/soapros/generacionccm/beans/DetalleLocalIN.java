package pe.soapros.generacionccm.beans;

import java.io.Serializable;

public class DetalleLocalIN implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private IndPDFLocalIN indPDF;
	private IndTXTLocalIN indTXT;
	private IndHTMLLocalIN indHTML;
	public IndPDFLocalIN getIndPDF() {
		return indPDF;
	}
	public void setIndPDF(IndPDFLocalIN indPDF) {
		this.indPDF = indPDF;
	}
	public IndTXTLocalIN getIndTXT() {
		return indTXT;
	}
	public void setIndTXT(IndTXTLocalIN indTXT) {
		this.indTXT = indTXT;
	}
	public IndHTMLLocalIN getIndHTML() {
		return indHTML;
	}
	public void setIndHTML(IndHTMLLocalIN indHTML) {
		this.indHTML = indHTML;
	}
	@Override
	public String toString() {
		return "DetalleLocalIN [indPDF=" + indPDF + ", indTXT=" + indTXT + ", indHTML=" + indHTML + "]";
	}
	
	
}
