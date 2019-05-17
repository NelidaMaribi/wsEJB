package pe.soapros.generacionccm.beans;

import java.io.Serializable;

//import javax.validation.Valid;

public class CabeceraIN implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//@Valid
	private DetallePDFIN detallePDF;

	//@Valid
	private DetalleTXTIN detalleTXT;

	//@Valid
	private DetalleHTMLIN detalleHTML;

	//@Valid
	private DetalleCorreoIN detalleCorreo;

	//@Valid
	private DetalleSMSIN detalleSMS;

	//@Valid
	private DetalleTrazabilidadCorreoIN detalleTrazabilidadCorreo;

	//@Valid
	private DetalleServicioGenericoIN detalleServicioGenerico;

	//@Valid
	private DetalleS3IN detalleS3;

	private DetalleFilenetIN detalleFilenet;

	public DetallePDFIN getDetallePDF() {
		return detallePDF;
	}

	public void setDetallePDF(DetallePDFIN detallePDF) {
		this.detallePDF = detallePDF;
	}

	public DetalleTXTIN getDetalleTXT() {
		return detalleTXT;
	}

	public void setDetalleTXT(DetalleTXTIN detalleTXT) {
		this.detalleTXT = detalleTXT;
	}

	public DetalleHTMLIN getDetalleHTML() {
		return detalleHTML;
	}

	public void setDetalleHTML(DetalleHTMLIN detalleHTML) {
		this.detalleHTML = detalleHTML;
	}

	public DetalleCorreoIN getDetalleCorreo() {
		return detalleCorreo;
	}

	public void setDetalleCorreo(DetalleCorreoIN detalleCorreo) {
		this.detalleCorreo = detalleCorreo;
	}

	public DetalleSMSIN getDetalleSMS() {
		return detalleSMS;
	}

	public void setDetalleSMS(DetalleSMSIN detalleSMS) {
		this.detalleSMS = detalleSMS;
	}

	public DetalleServicioGenericoIN getDetalleServicioGenerico() {
		return detalleServicioGenerico;
	}

	public void setDetalleServicioGenerico(DetalleServicioGenericoIN detalleServicioGenerico) {
		this.detalleServicioGenerico = detalleServicioGenerico;
	}

	public DetalleS3IN getDetalleS3() {
		return detalleS3;
	}

	public void setDetalleS3(DetalleS3IN detalleS3) {
		this.detalleS3 = detalleS3;
	}

	public DetalleFilenetIN getDetalleFilenet() {
		return detalleFilenet;
	}

	public void setDetalleFilenet(DetalleFilenetIN detalleFilenet) {
		this.detalleFilenet = detalleFilenet;
	}

	public DetalleTrazabilidadCorreoIN getDetalleTrazabilidadCorreo() {
		return detalleTrazabilidadCorreo;
	}

	public void setDetalleTrazabilidadCorreo(DetalleTrazabilidadCorreoIN detalleTrazabilidadCorreo) {
		this.detalleTrazabilidadCorreo = detalleTrazabilidadCorreo;
	}

	@Override
	public String toString() {
		return "CabeceraIN [detallePDF=" + detallePDF + ", detalleTXT=" + detalleTXT + ", detalleHTML=" + detalleHTML
				+ ", detalleCorreo=" + detalleCorreo + ", detalleSMS=" + detalleSMS + ", detalleTrazabilidadCorreo="
				+ detalleTrazabilidadCorreo + ", detalleServicioGenerico=" + detalleServicioGenerico + ", detalleS3="
				+ detalleS3 + ", detalleFilenet=" + detalleFilenet + "]";
	}
	
	

}
