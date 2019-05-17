package pe.soapros.generacionccm.persistance.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "CCM_SISTEMA", schema ="OT_SUPPORT")
public class Sistema implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "IDSISTEMA")
	private long idSistema;
	
	@Column(name = "USUCREACION")
	private String usuCreacion;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECCREACION")
	private Date fecCreacion;
	
	@Column(name = "USUMODIF")
	private String usuModif;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECMODIF")
	private Date fecModif;
	
	@Column(name = "NOMBRE")
	private String nombre;
	
	@Column(name = "INDACTIVO")
	private int indActivo;

	public long getIdSistema() {
		return idSistema;
	}

	public void setIdSistema(long idSistema) {
		this.idSistema = idSistema;
	}

	public String getUsuCreacion() {
		return usuCreacion;
	}

	public void setUsuCreacion(String usuCreacion) {
		this.usuCreacion = usuCreacion;
	}

	public Date getFecCreacion() {
		return fecCreacion;
	}

	public void setFecCreacion(Date fecCreacion) {
		this.fecCreacion = fecCreacion;
	}

	public String getUsuModif() {
		return usuModif;
	}

	public void setUsuModif(String usuModif) {
		this.usuModif = usuModif;
	}

	public Date getFecModif() {
		return fecModif;
	}

	public void setFecModif(Date fecModif) {
		this.fecModif = fecModif;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getIndActivo() {
		return indActivo;
	}

	public void setIndActivo(int indActivo) {
		this.indActivo = indActivo;
	}

	@Override
	public String toString() {
		return "Sistema [idSistema=" + idSistema + ", usuCreacion=" + usuCreacion + ", fecCreacion=" + fecCreacion
				+ ", usuModif=" + usuModif + ", fecModif=" + fecModif + ", nombre=" + nombre + ", indActivo="
				+ indActivo + "]";
	}
	
	
	
	
}
