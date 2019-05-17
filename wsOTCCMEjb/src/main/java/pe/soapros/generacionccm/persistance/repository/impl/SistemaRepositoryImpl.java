package pe.soapros.generacionccm.persistance.repository.impl;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import pe.soapros.generacionccm.persistance.domain.Documento;
import pe.soapros.generacionccm.persistance.domain.Sistema;
import pe.soapros.generacionccm.persistance.repository.SistemaRepository;

@Stateless(name = "SistemaRepository")
@TransactionManagement(TransactionManagementType.BEAN)
public class SistemaRepositoryImpl extends BaseJPARepository implements SistemaRepository {

	@Override
	public Sistema getByNombre(String nombre) throws Exception {
		return this.getEntityManager().createQuery("SELECT s FROM Sistema s WHERE s.nombre = (:nombre)", Sistema.class).setParameter("nombre", nombre).getSingleResult();
	}

	@Override
	public Documento getDocumentoSistemaByPlantilla(String idSistema, String codigo) throws Exception {
		return this.getEntityManager().createQuery("SELECT d FROM Sistema s, Documento d WHERE s.idSistema = d.idSistema AND s.nombre = (:sistema) AND d.codigo = (:codigo)", Documento.class).setParameter("sistema", idSistema).setParameter("codigo", codigo).getSingleResult();
	}

}
