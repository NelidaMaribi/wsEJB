package pe.soapros.generacionccm.beans;

import java.io.Serializable;
import java.util.Arrays;

//import javax.validation.Valid;
//import javax.validation.constraints.NotNull;
//import javax.validation.constraints.Size;

import com.fasterxml.jackson.databind.JsonNode;

public class DetalleCorreoIN implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//@NotNull(message = "detalleCorreo: Identificador de Correo es obligatorio")
	//@Size(min = 1, max = 1, message = "detalleCorreo: El tamaño del indicador de correo es sólo de un caracter")
	private String indCorreo;

	//@NotNull(message = "detalleCorreo: Identificador para Adjuntar PDF es obligatorio")
	//@Size(min = 0, max = 1, message = "detalleCorreo: El tamaño del indificador para adjuntar un PDF es sólo de un caracter")
	private String indAdjuntarPDF;

	//@NotNull(message = "detalleCorreo: Identificador para Adjuntar TXT es obligatorio")
	//@Size(min = 0, max = 1, message = "detalleCorreo: El tamaño del indetificador para adjuntar un TXT es sólo de un caracter")
	private String indAdjuntarTXT;

	//@NotNull(message = "detalleCorreo: El origen del correo es obligatorio")
	//@Email(message = "Email debería ser válido")
	//@Size(min = 0, max = 1000, message = "detalleCorreo: El tamaño del correo no debería ser mayor a 1000")
	private String de;

	//@Size(min = 0, max = 4000, message = "detalleCorreo: El tamaño del alias no debe ser mayor a 4000")
	private String aliasDe;

	//@NotNull(message = "detalleCorreo: El destinatario del correo es obligatorio")
	//@NotEmpty(message = "detalleCorreo: La lista de destinatarios no debería estar vacía")
	private String[] para;

	private String[] conCopia;

	private String[] conCopiaOculta;

	//@Size(min = 0, max = 100, message = "detalleCorreo: El asunto de la plantilla no puede ser mayor a 100 aplica a Thunderhead")
	private String asuntoPlantilla;

	//@Size(min = 0, max = 4000, message = "detalleCorreo: El asunto no puede ser mayor a 4000")
	private String asunto;

	//@Valid
	private JsonNode adjuntosadicionales;

	public String getIndCorreo() {
		return indCorreo;
	}

	public void setIndCorreo(String indCorreo) {
		this.indCorreo = indCorreo;
	}

	public String getIndAdjuntarPDF() {
		return indAdjuntarPDF;
	}

	public void setIndAdjuntarPDF(String indAdjuntarPDF) {
		this.indAdjuntarPDF = indAdjuntarPDF;
	}

	public String getIndAdjuntarTXT() {
		return indAdjuntarTXT;
	}

	public void setIndAdjuntarTXT(String indAdjuntarTXT) {
		this.indAdjuntarTXT = indAdjuntarTXT;
	}

	public String getDe() {
		return de;
	}

	public void setDe(String de) {
		this.de = de;
	}

	public String getAliasDe() {
		return aliasDe;
	}

	public void setAliasDe(String aliasDe) {
		this.aliasDe = aliasDe;
	}

	public String[] getPara() {
		return para;
	}

	public void setPara(String[] para) {
		this.para = para;
	}

	public String[] getConCopia() {
		return conCopia;
	}

	public void setConCopia(String[] conCopia) {
		this.conCopia = conCopia;
	}

	public String[] getConCopiaOculta() {
		return conCopiaOculta;
	}

	public void setConCopiaOculta(String[] conCopiaOculta) {
		this.conCopiaOculta = conCopiaOculta;
	}

	public String getAsuntoPlantilla() {
		return asuntoPlantilla;
	}

	public void setAsuntoPlantilla(String asuntoPlantilla) {
		this.asuntoPlantilla = asuntoPlantilla;
	}

	public String getAsunto() {
		return asunto;
	}

	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}

	public JsonNode getAdjuntosadicionales() {
		return adjuntosadicionales;
	}

	public void setAdjuntosadicionales(JsonNode adjuntosadicionales) {
		this.adjuntosadicionales = adjuntosadicionales;
	}

	@Override
	public String toString() {
		return "DetalleCorreoIN [indCorreo=" + indCorreo + ", indAdjuntarPDF=" + indAdjuntarPDF + ", indAdjuntarTXT="
				+ indAdjuntarTXT + ", de=" + de + ", aliasDe=" + aliasDe + ", para=" + Arrays.toString(para)
				+ ", conCopia=" + Arrays.toString(conCopia) + ", conCopiaOculta=" + Arrays.toString(conCopiaOculta)
				+ ", asuntoPlantilla=" + asuntoPlantilla + ", asunto=" + asunto + ", adjuntosadicionales="
				+ adjuntosadicionales + "]";
	}


	

	
	

}
