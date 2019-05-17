package pe.soapros.generacionccm.beans;

import java.io.Serializable;

//import javax.validation.constraints.NotNull;
//import javax.validation.constraints.Size;

public class DetallePDFIN implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//@Size(min = 1, max = 1, message = "detallePDF: El tamaño del indicador de PDF es sólo de un caracter")
	//@NotNull(message = "detallePDF: Identificador de PDF es obligatorio")	
	private String indPDF;

	//@NotNull(message = "detallePDF: El código de plantilla es obligatorio")
	//@Size(min = 0, max = 100, message = "detallePDF: El tamaño del código de plantilla es como máximo es de 100")
	private String codigoPlantilla;

	//@NotNull(message = "detallePDF: Identificador de Vizualización es obligatorio")
	//@Size(min = 0, max = 1, message = "detallePDF: El tamaño del indicador de visualización es sólo de un caracter")
	private String indVisualizacion;

	private String nombreDocumento;

	//@NotNull(message = "detallePDF: Identificador de Guardado es obligatorio")
	//@Size(min =01, max = 1, message = "detallePDF: El tamaño del indicador de guardado es sólo de un caracter")
	private String indGuardado;

	public String getIndPDF() {
		return indPDF;
	}

	public void setIndPDF(String indPDF) {
		this.indPDF = indPDF;
	}

	public String getCodigoPlantilla() {
		return codigoPlantilla;
	}

	public void setCodigoPlantilla(String codigoPlantilla) {
		this.codigoPlantilla = codigoPlantilla;
	}

	public String getIndVisualizacion() {
		return indVisualizacion;
	}

	public void setIndVisualizacion(String indVisualizacion) {
		this.indVisualizacion = indVisualizacion;
	}

	public String getNombreDocumento() {
		return nombreDocumento;
	}

	public void setNombreDocumento(String nombreDocumento) {
		this.nombreDocumento = nombreDocumento;
	}

	public String getIndGuardado() {
		return indGuardado;
	}

	public void setIndGuardado(String indGuardado) {
		this.indGuardado = indGuardado;
	}

	@Override
	public String toString() {
		return "DetallePDFIN [indPDF=" + indPDF + ", codigoPlantilla=" + codigoPlantilla + ", indVisualizacion="
				+ indVisualizacion + ", nombreDocumento=" + nombreDocumento + ", indGuardado=" + indGuardado + "]";
	}

	
}
