package pe.soapros.generacionccm.beans;

import java.io.Serializable;

public class Cabecera implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DetallePDF detallePDF;
	private DetalleTXT detalleTXT;
	private DetalleHTML detalleHTML;
	private DetalleCorreo detalleCorreo;
	private DetalleSMS EnvioSMS;
	private DetalleTrazabilidad detalleTrazabilidad;
	private DetalleServicio detalleServicio;
	private AlmacenamientoS3 almacenamientoS3;
	private AlmacenamientoFilenet almacenamientoFilenet;
	private AlmacenamientoLocal almacenamientoLocal;
	public DetallePDF getDetallePDF() {
		return detallePDF;
	}
	public void setDetallePDF(DetallePDF detallePDF) {
		this.detallePDF = detallePDF;
	}
	public DetalleTXT getDetalleTXT() {
		return detalleTXT;
	}
	public void setDetalleTXT(DetalleTXT detalleTXT) {
		this.detalleTXT = detalleTXT;
	}
	public DetalleHTML getDetalleHTML() {
		return detalleHTML;
	}
	public void setDetalleHTML(DetalleHTML detalleHTML) {
		this.detalleHTML = detalleHTML;
	}

	public DetalleCorreo getDetalleCorreo() {
		return detalleCorreo;
	}
	public void setDetalleCorreo(DetalleCorreo detalleCorreo) {
		this.detalleCorreo = detalleCorreo;
	}
	public DetalleSMS getEnvioSMS() {
		return EnvioSMS;
	}
	public void setEnvioSMS(DetalleSMS envioSMS) {
		EnvioSMS = envioSMS;
	}
	public DetalleTrazabilidad getDetalleTrazabilidad() {
		return detalleTrazabilidad;
	}
	public void setDetalleTrazabilidad(DetalleTrazabilidad detalleTrazabilidad) {
		this.detalleTrazabilidad = detalleTrazabilidad;
	}
	public DetalleServicio getDetalleServicio() {
		return detalleServicio;
	}
	public void setDetalleServicio(DetalleServicio detalleServicio) {
		this.detalleServicio = detalleServicio;
	}
	public AlmacenamientoS3 getAlmacenamientoS3() {
		return almacenamientoS3;
	}
	public void setAlmacenamientoS3(AlmacenamientoS3 almacenamientoS3) {
		this.almacenamientoS3 = almacenamientoS3;
	}
	public AlmacenamientoFilenet getAlmacenamientoFilenet() {
		return almacenamientoFilenet;
	}
	public void setAlmacenamientoFilenet(AlmacenamientoFilenet almacenamientoFilenet) {
		this.almacenamientoFilenet = almacenamientoFilenet;
	}
	public AlmacenamientoLocal getAlmacenamientoLocal() {
		return almacenamientoLocal;
	}
	public void setAlmacenamientoLocal(AlmacenamientoLocal almacenamientoLocal) {
		this.almacenamientoLocal = almacenamientoLocal;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public String toString() {
		return "Cabecera [detallePDF=" + detallePDF + ", detalleTXT=" + detalleTXT + ", detalleHTML=" + detalleHTML
				+ ", detalleCorreo=" + detalleCorreo + ", EnvioSMS=" + EnvioSMS + ", detalleTrazabilidad="
				+ detalleTrazabilidad + ", detalleServicio=" + detalleServicio + ", almacenamientoS3="
				+ almacenamientoS3 + ", almacenamientoFilenet=" + almacenamientoFilenet + ", almacenamientoLocal="
				+ almacenamientoLocal + "]";
	}
	







}
