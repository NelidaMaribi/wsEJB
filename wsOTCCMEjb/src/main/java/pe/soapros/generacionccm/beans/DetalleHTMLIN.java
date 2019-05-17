package pe.soapros.generacionccm.beans;

import java.io.Serializable;

//import javax.validation.constraints.NotNull;
//import javax.validation.constraints.Size;

public class DetalleHTMLIN implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//@NotNull(message = "detalleHTML: Identificador de HTML es obligatorio")
	//@Size(min = 1, max = 1, message = "detalleHTML: El tamaño del indicador de HTML es sólo de un caracter")
	private String indHTML;

	//@NotNull(message = "detalleHTML: El código de plantilla es obligatorio")
	//@Size(min = 0, max = 100, message = "detalleHTML: El tamaño del código de plantilla es como máximo es de 100")
	private String codigoPlantilla;

	private String nombreDocumento;

	//@NotNull(message = "detalleHTML: Identificador de guardado es obligatorio")
	//@Size(min = 0, max = 1, message = "detalleHTML: El tamaño del indicador de guardado es sólo de un caracter")
	private String indGuardado;

	public String getIndHTML() {
		return indHTML;
	}

	public void setIndHTML(String indHTML) {
		this.indHTML = indHTML;
	}

	public String getCodigoPlantilla() {
		return codigoPlantilla;
	}

	public void setCodigoPlantilla(String codigoPlantilla) {
		this.codigoPlantilla = codigoPlantilla;
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
		return "DetalleHTMLIN [indHTML=" + indHTML + ", codigoPlantilla=" + codigoPlantilla + ", nombreDocumento="
				+ nombreDocumento + ", indGuardado=" + indGuardado + "]";
	}
	
	

}
