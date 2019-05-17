package pe.soapros.generacionccm.beans;

import java.io.Serializable;

public class DetalleFilenetIN implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String repositoryId;
	private IndPDFFilenetIN indPDF;
	private IndTXTFilenetIN indTXT;
	private IndHTMLFilenetIN indHTML;
	
	
	public String getRepositoryId() {
		return repositoryId;
	}
	public void setRepositoryId(String repositoryId) {
		this.repositoryId = repositoryId;
	}
	public IndPDFFilenetIN getIndPDF() {
		return indPDF;
	}
	public void setIndPDF(IndPDFFilenetIN indPDF) {
		this.indPDF = indPDF;
	}
	public IndTXTFilenetIN getIndTXT() {
		return indTXT;
	}
	public void setIndTXT(IndTXTFilenetIN indTXT) {
		this.indTXT = indTXT;
	}
	public IndHTMLFilenetIN getIndHTML() {
		return indHTML;
	}
	public void setIndHTML(IndHTMLFilenetIN indHTML) {
		this.indHTML = indHTML;
	}
	
	
	@Override
	public String toString() {
		return "DetalleFilenetIN [repositoryId=" + repositoryId + ", indPDF=" + indPDF + ", indTXT=" + indTXT
				+ ", indHTML=" + indHTML + "]";
	}

	

}
