package pe.soapros.generacionccm.persistance.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "CCM_PETICION", schema = "OT_SUPPORT")
@SequenceGenerator(name = "id_Sequence", sequenceName = "OT_SUPPORT.ISEQ_PET_OTCCM", allocationSize = 1, initialValue = 1) // ,
																															// schema
																															// ="OT_SUPPORT"
																															// )
public class Peticion implements Serializable {

	private static final long serialVersionUID = 9043312177095494738L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_Sequence")
	@Column(name = "IDPETICION")
	private long idPeticion;

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

	@Column(name = "SISTEMA")
	private String sistema;

	@Column(name = "NOMFASE")
	private String nomfase;

	@Column(name = "NUMOPERACION")
	private String numOperacion;

	@Column(name = "INDERROR")
	private boolean indError;

	@Column(name = "DESCERROR")
	private String descerror;

	@OneToMany(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
    @JoinColumn(name="IDPETICION", nullable=false)
	private List<Detalle> detalles = new ArrayList<>();

	/*
	 * public void addDetalle(Detalle detalle) { this.detalles.add(detalle); }
	 */
	public long getIdPeticion() {
		return idPeticion;
	}

	public void setIdPeticion(long idPeticion) {
		this.idPeticion = idPeticion;
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

	public String getNumOperacion() {
		return numOperacion;
	}

	public void setNumOperacion(String numOperacion) {
		this.numOperacion = numOperacion;
	}

	public boolean isIndError() {
		return indError;
	}

	public void setIndError(boolean indError) {
		this.indError = indError;
	}

	public String getSistema() {
		return sistema;
	}

	public void setSistema(String sistema) {
		this.sistema = sistema;
	}

	public String getNomfase() {
		return nomfase;
	}

	public void setNomfase(String nomfase) {
		this.nomfase = nomfase;
	}

	public String getDescerror() {
		return descerror;
	}

	public void setDescerror(String descerror) {
		this.descerror = descerror;
	}

	public List<Detalle> getDetalles() {
		return detalles;
	}

	public void setDetalles(List<Detalle> detalles) {
		this.detalles = detalles;
	}

	@Override
	public String toString() {
		return "Peticion [idPeticion=" + idPeticion + ", usuCreacion=" + usuCreacion + ", fecCreacion=" + fecCreacion
				+ ", usuModif=" + usuModif + ", fecModif=" + fecModif + ", sistema=" + sistema + ", nomfase=" + nomfase
				+ ", numOperacion=" + numOperacion + ", indError=" + indError + ", descerror=" + descerror + "]";
	}

}
