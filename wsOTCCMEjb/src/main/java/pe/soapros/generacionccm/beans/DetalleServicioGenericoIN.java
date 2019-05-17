
package pe.soapros.generacionccm.beans;

import java.io.Serializable;

//import javax.validation.constraints.NotNull;
//import javax.validation.constraints.Size;

public class DetalleServicioGenericoIN implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//@NotNull(message = "detalleServicioGenerico: Identificador de Trazabilidad es obligatorio")
	//@Size(min = 1, max = 1, message = "detalleServicioGenerico: El tamaño del identificador de trazabilidad es sólo de un caracter")
	private String indServicioGenerico;
	
	//@NotNull(message = "detalleServicioGenerico: El datasource de trazabilidad es obligatorio")
	//@Size(min = 0, max = 100, message = "detalleServicioGenerico: El tamaño del datasource no puede superar los 100 caracteres")
	private String dataSource;

	public String getIndServicioGenerico() {
		return indServicioGenerico;
	}

	public void setIndServicioGenerico(String indServicioGenerico) {
		this.indServicioGenerico = indServicioGenerico;
	}

	public String getDataSource() {
		return dataSource;
	}

	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public String toString() {
		return "DetalleServicioGenericoIN [indServicioGenerico=" + indServicioGenerico + ", dataSource=" + dataSource
				+ "]";
	}

	
}
