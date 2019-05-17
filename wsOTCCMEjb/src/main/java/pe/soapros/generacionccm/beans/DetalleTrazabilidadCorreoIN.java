
package pe.soapros.generacionccm.beans;

import java.io.Serializable;

//import javax.validation.constraints.NotNull;
//import javax.validation.constraints.Size;

public class DetalleTrazabilidadCorreoIN implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//@NotNull(message = "Identificador de Trazabilidad es obligatorio")
	//@Size(min = 1, max = 1, message = "El tamaño es sólo de un caracter")
	private String indTrazabilidad;

	//@NotNull(message = "El datasource de trazabilidad es obligatorio")
	//@Size(min = 0, max = 100, message = "El tamaño del datasource no puede superar los 100 caracteres")
	private String dataSource;

	public String getIndTrazabilidad() {
		return indTrazabilidad;
	}

	public void setIndTrazabilidad(String indTrazabilidad) {
		this.indTrazabilidad = indTrazabilidad;
	}

	public String getDataSource() {
		return dataSource;
	}

	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public String toString() {
		return "DetalleTrazabilidadCorreoIN [indTrazabilidad=" + indTrazabilidad + ", dataSource=" + dataSource + "]";
	}
	
	

}
