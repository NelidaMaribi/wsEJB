package pe.soapros.generacionccm.beans;

import java.io.Serializable;
import java.util.Arrays;

//import javax.validation.constraints.NotNull;

/**
 * Bean que contiene los datos de origen
 * 
 * @author raul.talledo@soapros.pe
 *
 */

public class Origen implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//@NotNull(message = "Sistema es obligatorio")
	private String sistema;

	//@NotNull(message = "ambiente es obligatorio")
	private String ambiente;

	//@NotNull(message = "proceso es obligatorio")
	private String proceso;

	//@NotNull(message = "subproceso es obligatorio")
	private String subproceso;

	//@NotNull(message = "fechadeEnvio es obligatorio")
	private String fechadeEnvio;

	//@NotNull(message = "usuario es obligatorio")
	private String usuario;

	//@NotNull(message = "identificador es obligatorio")
	private String[] identificador;

	public String getSistema() {
		return sistema;
	}

	public void setSistema(String sistema) {
		this.sistema = sistema;
	}

	public String getProceso() {
		return proceso;
	}

	public void setProceso(String proceso) {
		this.proceso = proceso;
	}

	public String getSubproceso() {
		return subproceso;
	}

	public void setSubproceso(String subproceso) {
		this.subproceso = subproceso;
	}

	public String getFechadeEnvio() {
		return fechadeEnvio;
	}

	public void setFechadeEnvio(String fechadeEnvio) {
		this.fechadeEnvio = fechadeEnvio;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getAmbiente() {
		return ambiente;
	}

	public void setAmbiente(String ambiente) {
		this.ambiente = ambiente;
	}

	public String[] getIdentificador() {
		return identificador;
	}

	public void setIdentificador(String[] identificador) {
		this.identificador = identificador;
	}

	@Override
	public String toString() {
		return "Origen [sistema=" + sistema + ", ambiente=" + ambiente + ", proceso=" + proceso + ", subproceso="
				+ subproceso + ", fechadeEnvio=" + fechadeEnvio + ", usuario=" + usuario + ", identificador="
				+ Arrays.toString(identificador) + "]";
	}

}
