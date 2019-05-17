package pe.soapros.generacionccm.beans;

import java.io.Serializable;

public class indPDF_AlmcFilenet implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String infFilenetPDF,indExito,codEstado,msgEstado,objectid;

	public String getInfFilenetPDF() {
		return infFilenetPDF;
	}

	public void setInfFilenetPDF(String infFilenetPDF) {
		this.infFilenetPDF = infFilenetPDF;
	}

	public String getIndExito() {
		return indExito;
	}

	public void setIndExito(String indExito) {
		this.indExito = indExito;
	}



	public String getCodEstado() {
		return codEstado;
	}

	public void setCodEstado(String codEstado) {
		this.codEstado = codEstado;
	}

	public String getMsgEstado() {
		return msgEstado;
	}

	public void setMsgEstado(String msgEstado) {
		this.msgEstado = msgEstado;
	}

	public String getObjectid() {
		return objectid;
	}

	public void setObjectid(String objectid) {
		this.objectid = objectid;
	}
	
}
