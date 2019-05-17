package pe.soapros.generacionccm.beans;

import java.io.Serializable;

//import javax.validation.constraints.NotNull;
//import javax.validation.constraints.Size;

public class DetalleTXTIN implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//@NotNull(message = "detalleTXT: Identificador de TXT es obligatorio")
	//@Size(min = 1, max = 1, message = "detalleTXT: El tamaño del indicador de TXT es sólo de un caracter")
	private String indTXT;

	//@NotNull(message = "detalleTXT: El código de plantilla es obligatorio")
	//@Size(min = 0, max = 100, message = "detalleTXT: El tamaño del código de plantilla es como máximo es de 100")
	private String codigoPlantilla;

	private String nombreDocumento;

	//@NotNull(message = "detalleTXT: Identificador de guardado es obligatorio")
	//@Size(min = 0, max = 1, message = "detalleTXT: El tamaño del indicador de guardado es sólo de un caracter")
	private String indGuardado;

	public String getIndTXT() {
		return indTXT;
	}

	public void setIndTXT(String indTXT) {
		this.indTXT = indTXT;
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
		return "DetalleTXTIN [indTXT=" + indTXT + ", codigoPlantilla=" + codigoPlantilla + ", nombreDocumento="
				+ nombreDocumento + ", indGuardado=" + indGuardado + "]";
	}
	
	

}
