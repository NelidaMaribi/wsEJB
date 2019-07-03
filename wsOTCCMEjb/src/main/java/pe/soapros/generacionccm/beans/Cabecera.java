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
	private AlmacenamientoS3 almacenamientoS3;
	private DetalleTrazabilidad detalleTrazabilidad;
	private DetalleSMS EnvioSMS;	
	private AlmacenamientoFilenet almacenamientoFilenet;
	private AlmacenamientoLocal almacenamientoLocal;
	private DetalleServicio detalleServicio;
	
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

	public DetallePDF getDetallePDF() {
		return detallePDF;
	}
	public void setDetallePDF(DetallePDF detallePDF) {
		this.detallePDF = detallePDF;
	}
	
	public DetalleHTML getDetalleHTML() {
		return detalleHTML;
	}
	public void setDetalleHTML(DetalleHTML detalleHTML) {
		this.detalleHTML = detalleHTML;
	}
	
	public DetalleSMS getEnvioSMS() {
		return EnvioSMS;
	}
	public void setEnvioSMS(DetalleSMS envioSMS) {
		EnvioSMS = envioSMS;
	}
	public DetalleServicio getDetalleServicio() {
		return detalleServicio;
	}
	public void setDetalleServicio(DetalleServicio detalleServicio) {
		this.detalleServicio = detalleServicio;
	}
	public DetalleTXT getDetalleTXT() {
		return detalleTXT;
	}
	public void setDetalleTXT(DetalleTXT detalleTXT) {
		this.detalleTXT = detalleTXT;
	}
	public DetalleTrazabilidad getDetalleTrazabilidad() {
		return detalleTrazabilidad;
	}
	public void setDetalleTrazabilidad(DetalleTrazabilidad detalleTrazabilidad) {
		this.detalleTrazabilidad = detalleTrazabilidad;
	}	
	
	
	public AlmacenamientoLocal getAlmacenamientoLocal() {
		return almacenamientoLocal;
	}
	public void setAlmacenamientoLocal(AlmacenamientoLocal almacenamientoLocal) {
		this.almacenamientoLocal = almacenamientoLocal;
	}
	@Override
	public String toString() {
		return "Cabecera [detallePDF=" + detallePDF + ", detalleTXT=" + detalleTXT + ", detalleHTML=" + detalleHTML
				+ ", almacenamientoS3=" + almacenamientoS3 + ", detalleTrazabilidad=" + detalleTrazabilidad
				+ ", EnvioSMS=" + EnvioSMS + ", almacenamientoFilenet=" + almacenamientoFilenet
				+ ", almacenamientoLocal=" + almacenamientoLocal + ", detalleServicio=" + detalleServicio + "]";
	}

	
		
	
}
