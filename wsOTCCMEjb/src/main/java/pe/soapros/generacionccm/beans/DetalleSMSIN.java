
package pe.soapros.generacionccm.beans;

import java.io.Serializable;
import java.util.Arrays;

//import javax.validation.constraints.NotNull;
//import javax.validation.constraints.Size;

public class DetalleSMSIN implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//@NotNull(message = "detalleSMS: Identificador de SMS es obligatorio")
	//@Size(min = 1, max = 1, message = "detalleSMS: El tamaño del indentificador SMS es sólo de un caracter")
	private String indSMS;

	//@NotNull(message = "detalleSMS: Datasource es obligatorio")
	//@Size(min = 1, max = 100, message = "detalleSMS: El tamaño del datasource no puede superar los 100 caracteres")
	private String dataSource;

	//@NotEmpty(message = "detalleSMS: Los destinatarios no pueden ser vacíos")
	private String[] destinatario;

	public String getIndSMS() {
		return indSMS;
	}

	public void setIndSMS(String indSMS) {
		this.indSMS = indSMS;
	}

	public String getDataSource() {
		return dataSource;
	}

	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}

	public String[] getDestinatario() {
		return destinatario;
	}

	public void setDestinatario(String[] destinatario) {
		this.destinatario = destinatario;
	}

	@Override
	public String toString() {
		return "DetalleSMSIN [indSMS=" + indSMS + ", dataSource=" + dataSource + ", destinatario="
				+ Arrays.toString(destinatario) + "]";
	}
	
	

}
