package pe.soapros.generacionccm.controllers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import pe.soapros.generacionccm.beans.CabeceraIN;
import pe.soapros.generacionccm.beans.DetalleCorreoIN;
import pe.soapros.generacionccm.beans.DetalleFilenetIN;
import pe.soapros.generacionccm.beans.DetalleHTMLIN;
import pe.soapros.generacionccm.beans.DetalleLocalIN;
import pe.soapros.generacionccm.beans.DetallePDFIN;
import pe.soapros.generacionccm.beans.DetalleS3IN;
import pe.soapros.generacionccm.beans.DetalleSMSIN;
import pe.soapros.generacionccm.beans.DetalleServicioGenericoIN;
import pe.soapros.generacionccm.beans.DetalleTXTIN;
import pe.soapros.generacionccm.beans.DetalleTrazabilidadCorreoIN;
import pe.soapros.generacionccm.beans.IndHTMLFilenetIN;
import pe.soapros.generacionccm.beans.IndHTMLINDetalleS3;
import pe.soapros.generacionccm.beans.IndHTMLLocalIN;
import pe.soapros.generacionccm.beans.IndPDFFilenetIN;
import pe.soapros.generacionccm.beans.IndPDFINDetalleS3;
import pe.soapros.generacionccm.beans.IndPDFLocalIN;
import pe.soapros.generacionccm.beans.IndTXTFilenetIN;
import pe.soapros.generacionccm.beans.IndTXTINDetalleS3;
import pe.soapros.generacionccm.beans.IndTXTLocalIN;
import pe.soapros.generacionccm.beans.Origen;
import pe.soapros.generacionccm.beans.Respuesta;
import pe.soapros.generacionccm.beans.Solicitud;
import pe.soapros.generacionccm.service.CommunicationServerBO;
import pe.soapros.generacionccm.service.EwsBO;
import pe.soapros.generacionccm.service.PeticionBO;
import pe.soapros.generacionccm.service.ValidatorBO;
import pe.soapros.generacionccm.utils.ExceptionsUtils;

@Path("/registrar")
public class RegistrarRespuestaController {

	private Logger logger = LoggerFactory.getLogger(RegistrarRespuestaController.class);

	@EJB(name = "PeticionBO")
	private PeticionBO peticionBO;

	@EJB(name = "CommunicationServerBO")
	private CommunicationServerBO cs;

	// @EJB(name = "EwsBO")
	// private EwsBO ews;

	@EJB(name = "ValidatorBO")
	private ValidatorBO validatorBO;

	private ObjectMapper mapper = new ObjectMapper();

	private ExceptionsUtils exceptionsUtils = new ExceptionsUtils();

	@POST
	@Path("/pedido")
	@Produces("application/json; charset=UTF-8")
	@Consumes(MediaType.APPLICATION_JSON)
	public Respuesta respuestaSolicitud(String StrsolicitudIn) {
		try {
			logger.debug("respuestaSolicitud parametro: {}", StrsolicitudIn);
			mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
			mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
			// String strSolicitudValida = mapper.writeValueAsString(solicitud);
			String strSolicitudValida = deserialize(StrsolicitudIn);
			Solicitud solicitud = null;
			if (strSolicitudValida == null) {
				Response.ResponseBuilder resp_builder = Response.status(Response.Status.BAD_REQUEST);
				resp_builder.entity(exceptionsUtils.setException("Error al validar el objeto de Solicitud",
						"uri=/registrar/pedido"));
				throw new WebApplicationException(resp_builder.build());
			} else {
				solicitud = mapper.readValue(strSolicitudValida, Solicitud.class);
			}

			Respuesta respuesta = new Respuesta();
			// respuesta = cs.callOrquestador(strSolicitudValida);
			respuesta = cs.callFlujoUnico(strSolicitudValida);
			if (respuesta != null) {
				logger.debug("Orquestador: {}", respuesta);
				respuesta.setOrigen(solicitud.getOrigen());
				logger.debug("Respuesta con Origen: {}", respuesta);
				String strRespuesta = peticionBO.procesarPeticion(mapper.writeValueAsString(respuesta),
						mapper.writeValueAsString(solicitud));
				if (strRespuesta != null) {
					respuesta = mapper.readValue(strRespuesta, Respuesta.class);
				} else {

				}
				try {
					logger.debug("Indicador Vizualización",
							solicitud.getCabecera().getDetallePDF().getIndVisualizacion());
					if ("S".equals(solicitud.getCabecera().getDetallePDF().getIndVisualizacion())) {
						byte[] valor = cs.callPreview(strSolicitudValida);
						/*
						 * byte[] valor = ews.callEWS(mapper.writeValueAsString(solicitud));
						 */
						logger.debug("Retorno EWS: {}", valor);
						if (valor != null && valor.length > 0) {
							respuesta.setDocBase64(valor);
						}
					}
				} catch (Exception e) {
					logger.error("Error EWS {}", e);
				}
			} else {
				logger.error("No se encontró respuesta del orquestador");
			}

			logger.info("Respuesta: {}", respuesta);
			return respuesta;
		} catch (Exception e) {
			logger.error("ERROR respuestaSolicitud: {}", e.getMessage(), e);
			Response.ResponseBuilder resp_builder = Response.status(Response.Status.BAD_REQUEST);
			resp_builder.entity(exceptionsUtils.setException(e, "uri=/registrar/pedido"));
			throw new WebApplicationException(resp_builder.build());
		}

	}

	public String deserialize(String jsonString) throws JsonProcessingException {
		try {
			logger.debug("deserialize parametros {} {} ", jsonString);
			ObjectMapper mapper = new ObjectMapper();
			JsonNode node = mapper.readTree(jsonString);
			JsonNode origen = null;
			JsonNode cabecera = null;
			JsonNode detallePdf = null;
			JsonNode detalleTxt = null;
			JsonNode detalleHtml = null;
			JsonNode detalleCorreo = null;
			JsonNode detalleSms = null;
			JsonNode detalleTrazCor = null;
			JsonNode detServGen = null;
			JsonNode detS3 = null;
			JsonNode detLocal = null;
			JsonNode indPdf = null;
			JsonNode indTxt = null;
			JsonNode indHtml = null;
			JsonNode indPdfLocal = null;
			JsonNode indTxtLocal = null;
			JsonNode indHtmlLocal = null;
			JsonNode detFNet = null;
			JsonNode indPDFFilenet = null;
			JsonNode indTXTFilenet = null;
			JsonNode indHTMLFilenet = null;

			JsonNode PropiedadesFilenetPdf = null;
			JsonNode contentStreamFilenetPdf = null;

			JsonNode PropiedadesFilenetTxt = null;
			JsonNode contentStreamFileneTxt = null;

			JsonNode PropiedadesFilenetHtml = null;
			JsonNode contentStreamFilenetHtml = null;

			/*
			 * JsonNode metaDataPdf = null; JsonNode metaDataTxt = null; JsonNode
			 * metaDataHtml = null;
			 */
			JsonNode destinatario = null;
			JsonNode para = null;
			JsonNode conCopia = null;
			JsonNode conCopiaOculta = null;
			JsonNode adjuntosadicionales = null;

			JsonNode jsonData = null;

			Solicitud solicitudMod = new Solicitud();
			Origen o = new Origen();
			CabeceraIN cab = new CabeceraIN();
			// JsonDataIN json = new JsonDataIN();

			DetallePDFIN detPdf = new DetallePDFIN();
			DetalleTXTIN detTxt = new DetalleTXTIN();
			DetalleHTMLIN detHtml = new DetalleHTMLIN();
			DetalleCorreoIN detCorreo = new DetalleCorreoIN();
			DetalleSMSIN detSms = new DetalleSMSIN();
			DetalleTrazabilidadCorreoIN detTraz = new DetalleTrazabilidadCorreoIN();
			DetalleServicioGenericoIN detSGenerico = new DetalleServicioGenericoIN();
			DetalleS3IN detalS3 = new DetalleS3IN();
			IndPDFINDetalleS3 indPdfS3 = new IndPDFINDetalleS3();
			IndTXTINDetalleS3 indTxtS3 = new IndTXTINDetalleS3();
			IndHTMLINDetalleS3 indHtmlS3 = new IndHTMLINDetalleS3();

			IndPDFLocalIN indPdfLocalCom = new IndPDFLocalIN();
			IndTXTLocalIN indTxtLocalCom = new IndTXTLocalIN();
			IndHTMLLocalIN indHtmlLocalCom = new IndHTMLLocalIN();

			DetalleLocalIN detalLocal = new DetalleLocalIN();

			DetalleFilenetIN detFileNet = new DetalleFilenetIN();
			IndPDFFilenetIN fileNetPdf = new IndPDFFilenetIN();
			IndTXTFilenetIN fileNetTxt = new IndTXTFilenetIN();
			IndHTMLFilenetIN fileNetHtml = new IndHTMLFilenetIN();

			/**
			 * ********************************************************************************
			 * VALIDANDO LOS DATOS DEL ORIGEN
			 * *******************************************************************************
			 */
			if (node.has("origen")) {

				origen = node.get("origen");
				logger.debug("ORIGEN {}", origen.toString());

			} else {
				logger.debug("ERROR {}", "origen, La etiqueta no existe");
				throw new JsonProcessingException("origen, La etiqueta no existe") {
					private static final long serialVersionUID = 1L;

					@SuppressWarnings("unused")
					private void init() {
						this.initCause(this);
					}
				};

			}

			// sistema
			if (origen.has("sistema")) {

				o.setSistema(origen.get("sistema").asText());
				logger.debug("Sistema {}", origen.get("sistema").asText());

			} else {
				logger.debug("ERROR {}", "origen.sistema, La etiqueta no existe ");
				throw new JsonProcessingException("origen.sistema, La etiqueta no existe ") {

					private static final long serialVersionUID = 1L;

					@SuppressWarnings("unused")
					private void init() {
						this.initCause(this);
					}
				};

			}

			if ("".equals(o.getSistema()) || "null".equals(o.getSistema()) || o.getSistema() == null) {
				logger.debug("ERROR {}", "origen.sistema, La etiqueta no puede ser vacío o nula");
				throw new JsonProcessingException("origen.sistema, La etiqueta no puede ser vacío o nula") {

					private static final long serialVersionUID = 1L;

					@SuppressWarnings("unused")
					private void init() {
						this.initCause(this);
					}
				};

			} else

			{
				logger.debug("Validar DB");
				Boolean validado = validatorBO.validateSistema(o.getSistema());
				if (validado == null) {
					logger.debug("ERROR {}", "origen.sistema, No se encontró el sistema.");
					throw new JsonProcessingException("origen.sistema, No se encontró el sistema.") {

						private static final long serialVersionUID = 1L;

						@SuppressWarnings("unused")
						private void init() {
							this.initCause(this);
						}
					};
				}
				if (!validado) {
					logger.debug("ERROR {}",
							"origen.sistema, El Sistema ingresado no tiene permisos para hacer una solicitud");
					throw new JsonProcessingException(
							"origen.sistema, El Sistema ingresado no tiene permisos para hacer una solicitud") {

						private static final long serialVersionUID = 1L;

						@SuppressWarnings("unused")
						private void init() {
							this.initCause(this);
						}
					};
				}
			}

			// Ambiente
			if (origen.has("ambiente")) {

				o.setAmbiente(origen.get("ambiente").asText());
				logger.debug(origen.get("ambiente").asText());

			} else {

				throw new JsonProcessingException("origen.ambiente, La etiqueta no existe ") {

					private static final long serialVersionUID = 1L;

					@SuppressWarnings("unused")
					private void init() {
						this.initCause(this);
					}
				};
			}

			// PROCESO
			if (origen.has("proceso")) {

				if (!"null".equals(origen.get("proceso").asText())) {
					o.setProceso(origen.get("proceso").asText());
				}

				logger.debug(origen.get("proceso").asText());

			} else {

				throw new JsonProcessingException("origen.proceso, La etiqueta no existe ") {

					private static final long serialVersionUID = 1L;

					@SuppressWarnings("unused")
					private void init() {
						this.initCause(this);
					}
				};

			}

			// SUBPROCESO
			if (origen.has("subproceso")) {

				if (!"null".equals(origen.get("subproceso").asText())) {
					o.setSubproceso(origen.get("subproceso").asText());
				}

				logger.debug(origen.get("subproceso").asText());

			} else {

				throw new JsonProcessingException("origen.subproceso, La etiqueta no existe ") {
					private static final long serialVersionUID = 1L;

					@SuppressWarnings("unused")
					private void init() {
						this.initCause(this);
					}
				};
			}

			// FECHA DE ENVIO
			if (origen.has("fechadeEnvio")) {

				o.setFechadeEnvio(origen.get("fechadeEnvio").asText());
				logger.debug(origen.get("fechadeEnvio").asText());

			} else {

				throw new JsonProcessingException("origen.fechadeEnvio, La etiqueta no existe ") {
					private static final long serialVersionUID = 1L;

					@SuppressWarnings("unused")
					private void init() {
						this.initCause(this);
					}
				};
			}

			// USUARIO
			if (origen.has("usuario")) {

				o.setUsuario(origen.get("usuario").asText());
				logger.debug(origen.get("usuario").asText());

			} else {

				throw new JsonProcessingException("origen.usuario, La etiqueta no existe ") {

					private static final long serialVersionUID = 1L;

					@SuppressWarnings("unused")
					private void init() {
						this.initCause(this);
					}
				};
			}

			if ("".equals(o.getUsuario()) || "null".equals(o.getUsuario()) || o.getUsuario() == null) {

				throw new JsonProcessingException("origen.usuario, La etiqueta no puede ser vacío o nula") {

					private static final long serialVersionUID = 1L;

					@SuppressWarnings("unused")
					private void init() {
						this.initCause(this);
					}
				};
			}

			// IDENTIFICADOR
			if (origen.has("identificador")) {

				JsonNode identificador = origen.get("identificador");
				logger.debug(origen.get("identificador").toString());

				String[] identificadores = new String[identificador.size()];
				int ind = 0;
				for (JsonNode j : identificador) {
					identificadores[ind++] = j.asText();
				}

				o.setIdentificador(identificadores);

			} else {

				throw new JsonProcessingException("origen.identificador, La etiqueta no existe ") {

					private static final long serialVersionUID = 1L;

					@SuppressWarnings("unused")
					private void init() {
						this.initCause(this);
					}
				};
			}

			/**
			 * ********************************************************************************
			 * VALIDANDO LOS DATOS DE LA CABECERA
			 * *******************************************************************************
			 */
			// VALIDANDO LA ETIQUETA CABECERAIN
			if (node.has("cabecera")) {

				cabecera = node.get("cabecera");
				logger.debug(node.get("cabecera").toString());

			} else {
				throw new JsonProcessingException("cabecera, La etiqueta no existe ") {

					private static final long serialVersionUID = 1L;

					@SuppressWarnings("unused")
					private void init() {
						this.initCause(this);
					}
				};
			}
			/**
			 * ******************************************************************************
			 * DETALLEPDF
			 * *****************************************************************************
			 */
			if (cabecera.has("detallePDF")) {

				detallePdf = cabecera.get("detallePDF");
				logger.debug(cabecera.get("detallePDF").toString());

			} else {

				throw new JsonProcessingException("cabecera.detallePDF, La etiqueta no existe ") {

					private static final long serialVersionUID = 1L;

					@SuppressWarnings("unused")
					private void init() {
						this.initCause(this);
					}
				};
			}

			// VALIDACION DE LOS CAMPOS DETALLEPDF
			if (detallePdf.has("indPDF")) {

				detPdf.setIndPDF(detallePdf.get("indPDF").asText());
				logger.debug(detallePdf.get("indPDF").asText());

			} else {

				throw new JsonProcessingException("cabecera.detallePDF.indPDF, La etiqueta no existe ") {

					private static final long serialVersionUID = 1L;

					@SuppressWarnings("unused")
					private void init() {
						this.initCause(this);
					}
				};
			}

			// VALIDAMOS QUE EL CAMPO INDPDF SEA = S
			if (detPdf.getIndPDF() != null && detPdf.getIndPDF().equals("S")) {

				// 1: CODIGOPLANTILLA
				if (detallePdf.has("codigoPlantilla")) {

					detPdf.setCodigoPlantilla(detallePdf.get("codigoPlantilla").asText());
					logger.debug("CodigoPlantilla", detallePdf.get("codigoPlantilla").asText());

				} else {

					throw new JsonProcessingException("cabecera.detallePdf.codigoPlantilla, La etiqueta no existe ") {

						private static final long serialVersionUID = 1L;

						@SuppressWarnings("unused")
						private void init() {
							this.initCause(this);
						}
					};
				}

				if (detPdf.getCodigoPlantilla().equals("") || detPdf.getCodigoPlantilla().equals("null")
						|| detPdf.getCodigoPlantilla().equals(" ")) {
					// validar que no este vacio el indPDF

					throw new JsonProcessingException("cabecera.detallePdf.codigoPlantilla No puede ser nulo ") {

						private static final long serialVersionUID = 1L;

						@SuppressWarnings("unused")
						private void init() {
							this.initCause(this);
						}
					};
				} else {

					if (detPdf.getIndPDF().equals("S")) {
						logger.debug("IndPDF Devolver" + detPdf.getIndPDF());

						Boolean validado = validatorBO.validatePlantilla(o.getSistema(), detPdf.getCodigoPlantilla());

						if (validado == null) {

							throw new JsonProcessingException(
									"cabecera.detallePdf.codigoPlantilla. No se encontró la plantilla null"
											+ detPdf.getIndPDF() + validado) {

								private static final long serialVersionUID = 1L;

								@SuppressWarnings("unused")
								private void init() {
									this.initCause(this);
								}
							};
						}

						if (!validado) {

							throw new JsonProcessingException(
									"cabecera.detallePdf.codigoPlantilla Esta plantilla no está registrada para diferente null: "
											+ o.getSistema()) {

								private static final long serialVersionUID = 1L;

								@SuppressWarnings("unused")
								private void init() {
									this.initCause(this);
								}
							};
						} else {
							detPdf.setCodigoPlantilla(detallePdf.get("codigoPlantilla").asText());
						}

					}
				}

				// 2: NOMBREDOCUMENTO
				if (detallePdf.has("nombreDocumento")) {

					detPdf.setNombreDocumento(detallePdf.get("nombreDocumento").asText());
					logger.debug("NOMBREDOCUMENTO", detallePdf.get("nombreDocumento").asText());

				} else {

					throw new JsonProcessingException("cabecera.detallePdf.nombreDocumento, La etiqueta no existe ") {

						private static final long serialVersionUID = 1L;

						@SuppressWarnings("unused")
						private void init() {
							this.initCause(this);
						}
					};
				}

				if (detPdf.getNombreDocumento().equals("") || detPdf.getNombreDocumento().equals("null")
						|| detPdf.getNombreDocumento().equals(" ")) {

					throw new JsonProcessingException("cabecera.detallePdf.nombreDocumento No puede ser nulo ") {

						private static final long serialVersionUID = 1L;

						@SuppressWarnings("unused")
						private void init() {
							this.initCause(this);
						}
					};

				} else {
					detTxt.setNombreDocumento(detallePdf.get("nombreDocumento").asText());
				}

				// 3: INDGUARDADO
				if (detallePdf.has("indGuardado")) {

					detPdf.setIndGuardado(detallePdf.get("indGuardado").asText());
					logger.debug("INDGUARDADO", detallePdf.get("indGuardado").asText());

				} else {

					throw new JsonProcessingException("cabecera.detallePdf.indGuardado, La etiqueta no existe ") {

						private static final long serialVersionUID = 1L;

						@SuppressWarnings("unused")
						private void init() {
							this.initCause(this);
						}
					};
				}

				if (detPdf.getIndGuardado().equals("") || detPdf.getIndGuardado().equals("null")
						|| detPdf.getIndGuardado().equals(" ")) {

					throw new JsonProcessingException("cabecera.detallePdf.indGuardado No puede ser nulo ") {

						private static final long serialVersionUID = 1L;

						@SuppressWarnings("unused")
						private void init() {
							this.initCause(this);
						}
					};
				} else {
					detPdf.setIndGuardado(detallePdf.get("indGuardado").asText());
				}

				cab.setDetallePDF(detPdf);

			} else if (detPdf.getIndPDF().equals("N")) {

				detPdf.setIndPDF(detallePdf.get("indPDF").asText());
				logger.debug("indPDF", detallePdf.get("indPDF").asText());

				cab.setDetallePDF(detPdf);

			} else {

				throw new JsonProcessingException("cabecera.detallePDF.indPDF, No puede ser nulo ") {

					private static final long serialVersionUID = 1L;

					@SuppressWarnings("unused")
					private void init() {
						this.initCause(this);
					}
				};
			}

			/**
			 * ******************************************************************************
			 * DETALLETXT
			 * *****************************************************************************
			 */
			if (cabecera.has("detalleTXT")) {

				detalleTxt = cabecera.get("detalleTXT");
				logger.debug(cabecera.get("detalleTXT").toString());

			} else {

				throw new JsonProcessingException("cabecera.detalleTXT, La etiqueta no existe ") {

					private static final long serialVersionUID = 1L;

					@SuppressWarnings("unused")
					private void init() {
						this.initCause(this);
					}
				};
			}

			// VALIDACION DE LAS ETIQUETAS DE DETALLETXT
			if (detalleTxt.has("indTXT")) {

				detTxt.setIndTXT(detalleTxt.get("indTXT").asText());
				logger.debug("INDTXT", detalleTxt.get("indTXT").asText());

			} else {

				throw new JsonProcessingException("cabecera.detalleTXT.indTXT, La etiqueta no existe ") {

					private static final long serialVersionUID = 1L;

					@SuppressWarnings("unused")
					private void init() {
						this.initCause(this);
					}
				};
			}

			// VALIDAMOS QUE EL CAMPO INDTXT SEA = S
			if (detTxt.getIndTXT() != null && detTxt.getIndTXT().equals("S")) {

				// 1: CODIGOPLANTILLA
				if (detalleTxt.has("codigoPlantilla")) {

					detTxt.setCodigoPlantilla(detalleTxt.get("codigoPlantilla").asText());
					logger.debug("CodigoPlantilla", detalleTxt.get("codigoPlantilla").asText());

				} else {

					throw new JsonProcessingException("cabecera.detalleTXT.codigoPlantilla, La etiqueta no existe ") {

						private static final long serialVersionUID = 1L;

						@SuppressWarnings("unused")
						private void init() {
							this.initCause(this);
						}
					};
				}

				if (detTxt.getCodigoPlantilla().equals("") || detTxt.getCodigoPlantilla().equals("null")
						|| detTxt.getCodigoPlantilla().equals(" ")) {
					// validar que no este vacio el indTXT

					throw new JsonProcessingException("cabecera.detalleTXT.codigoPlantilla No puede ser nulo ") {

						private static final long serialVersionUID = 1L;

						@SuppressWarnings("unused")
						private void init() {
							this.initCause(this);
						}
					};
				} else {

					if (detTxt.getIndTXT().equals("S")) {

						Boolean validado = validatorBO.validatePlantilla(o.getSistema(), detTxt.getCodigoPlantilla());

						if (validado == null) {

							throw new JsonProcessingException(
									"cabecera.detalleTXT.codigoPlantilla. No se encontró la plantilla") {

								private static final long serialVersionUID = 1L;

								@SuppressWarnings("unused")
								private void init() {
									this.initCause(this);
								}
							};
						}

						if (!validado) {

							throw new JsonProcessingException(
									"cabecera.detalleTXT.codigoPlantilla Esta plantilla no está registrada para: "
											+ o.getSistema()) {

								private static final long serialVersionUID = 1L;

								@SuppressWarnings("unused")
								private void init() {
									this.initCause(this);
								}
							};
						} else {
							detTxt.setCodigoPlantilla(detalleTxt.get("codigoPlantilla").asText());
						}

					}
				}

				// 2: NOMBREDOCUMENTO
				if (detalleTxt.has("nombreDocumento")) {

					detTxt.setNombreDocumento(detalleTxt.get("nombreDocumento").asText());
					logger.debug("NOMBREDOCUMENTO", detalleTxt.get("nombreDocumento").asText());

				} else {

					throw new JsonProcessingException("cabecera.detalleTXT.nombreDocumento, La etiqueta no existe ") {

						private static final long serialVersionUID = 1L;

						@SuppressWarnings("unused")
						private void init() {
							this.initCause(this);
						}
					};
				}

				if (detTxt.getNombreDocumento().equals("") || detTxt.getNombreDocumento().equals("null")
						|| detTxt.getNombreDocumento().equals(" ")) {

					throw new JsonProcessingException("cabecera.detalleTXT.nombreDocumento No puede ser nulo ") {

						private static final long serialVersionUID = 1L;

						@SuppressWarnings("unused")
						private void init() {
							this.initCause(this);
						}
					};

				} else {
					detTxt.setNombreDocumento(detalleTxt.get("nombreDocumento").asText());
				}

				// 3: INDGUARDADO
				if (detalleTxt.has("indGuardado")) {

					detTxt.setIndGuardado(detalleTxt.get("indGuardado").asText());
					logger.debug("INDGUARDADO", detalleTxt.get("indGuardado").asText());

				} else {

					throw new JsonProcessingException("cabecera.detalleTXT.indGuardado, La etiqueta no existe ") {

						private static final long serialVersionUID = 1L;

						@SuppressWarnings("unused")
						private void init() {
							this.initCause(this);
						}
					};
				}

				if (detTxt.getIndGuardado().equals("") || detTxt.getIndGuardado().equals("null")
						|| detTxt.getIndGuardado().equals(" ")) {

					throw new JsonProcessingException("cabecera.detalleTXT.indGuardado No puede ser nulo ") {

						private static final long serialVersionUID = 1L;

						@SuppressWarnings("unused")
						private void init() {
							this.initCause(this);
						}
					};
				} else {
					detTxt.setIndGuardado(detalleTxt.get("indGuardado").asText());
				}

				cab.setDetalleTXT(detTxt);

			} else if (detTxt.getIndTXT().equals("N")) {

				detTxt.setIndTXT(detalleTxt.get("indTXT").asText());
				logger.debug("INDTXT", detalleTxt.get("indTXT").asText());

				cab.setDetalleTXT(detTxt);

			} else {

				throw new JsonProcessingException("cabecera.detallePDF.indTXT, No puede ser nulo ") {

					private static final long serialVersionUID = 1L;

					@SuppressWarnings("unused")
					private void init() {
						this.initCause(this);
					}
				};
			}

			/**
			 * ******************************************************************************
			 * DETALLEHTML
			 * *****************************************************************************
			 */
			if (cabecera.has("detalleHTML")) {

				detalleHtml = cabecera.get("detalleHTML");
				logger.debug("DETALLEHTML", cabecera.get("detalleHTML"));

			} else {

				throw new JsonProcessingException("cabecera.detalleHTML, La etiqueta no existe ") {

					private static final long serialVersionUID = 1L;

					@SuppressWarnings("unused")
					private void init() {
						this.initCause(this);
					}
				};

			}

			// VALIDANDO LAS ETIQUETAS DE DETALLEHTML
			if (detalleHtml.has("indHTML")) {

				detHtml.setIndHTML(detalleHtml.get("indHTML").asText());
				logger.debug("INDHTML", detalleHtml.get("indHTML").asText());

			} else {

				throw new JsonProcessingException("cabecera.detalleHTML.indHTML, La etiqueta no existe ") {

					private static final long serialVersionUID = 1L;

					@SuppressWarnings("unused")
					private void init() {
						this.initCause(this);
					}
				};

			}

			// VALIDAMOS QUE EL CAMPO INDHTML SEA = S
			if (detHtml.getIndHTML() != null && detHtml.getIndHTML().equals("S")) {

				// 1: CODIGOPLANTILLA
				if (detalleHtml.has("codigoPlantilla")) {

					detHtml.setCodigoPlantilla(detalleHtml.get("codigoPlantilla").asText());
					logger.debug("CODIGOPLANTILLA", detalleHtml.get("codigoPlantilla").asText());

				} else {

					throw new JsonProcessingException("cabecera.detalleHTML.codigoPlantilla, La etiqueta no existe ") {

						private static final long serialVersionUID = 1L;

						@SuppressWarnings("unused")
						private void init() {
							this.initCause(this);
						}
					};
				}

				if (detHtml.getCodigoPlantilla().equals("") || detHtml.getCodigoPlantilla().equals(" ")
						|| detHtml.getCodigoPlantilla().equals("null")) {

					throw new JsonProcessingException("cabecera.detalleHTML.codigoPlantilla No puede ser nulo ") {
						/**
						 *
						 */
						private static final long serialVersionUID = 1L;

						@SuppressWarnings("unused")
						private void init() {
							this.initCause(this);
						}
					};

				} else {

					if (detHtml.getIndHTML().equals("S")) {
						Boolean validado = validatorBO.validatePlantilla(o.getSistema(), detHtml.getCodigoPlantilla());

						if (validado == null) {

							throw new JsonProcessingException(
									"cabecera.detalleHTML.codigoPlantilla. No se encontró la plantilla") {

								private static final long serialVersionUID = 1L;

								@SuppressWarnings("unused")
								private void init() {
									this.initCause(this);
								}
							};
						}

						if (!validado) {

							throw new JsonProcessingException(
									"cabecera.detalleHTML.codigoPlantilla Esta plantilla no está registrada para: "
											+ o.getSistema()) {

								private static final long serialVersionUID = 1L;

								@SuppressWarnings("unused")
								private void init() {
									this.initCause(this);
								}
							};
						}

						detHtml.setCodigoPlantilla(detalleHtml.get("codigoPlantilla").asText());

					}
				}

				// 2: NOMBREDOCUMENTO
				if (detalleHtml.has("nombreDocumento")) {

					detHtml.setNombreDocumento(detalleHtml.get("nombreDocumento").asText());
					logger.debug("NOMBREDOCUMENTO", detalleHtml.get("nombreDocumento").asText());

				} else {

					throw new JsonProcessingException("cabecera.detalleHTML.nombreDocumento, La etiqueta no existe ") {
						/**
						 *
						 */
						private static final long serialVersionUID = 1L;

						@SuppressWarnings("unused")
						private void init() {
							this.initCause(this);
						}
					};

				}

				if (detHtml.getNombreDocumento().equals("") || detHtml.getNombreDocumento().equals(" ")
						|| detHtml.getNombreDocumento().equals("null")) {

					throw new JsonProcessingException("cabecera.detalleHTML.nombreDocumento No puede ser nulo ") {
						/**
						 *
						 */
						private static final long serialVersionUID = 1L;

						@SuppressWarnings("unused")
						private void init() {
							this.initCause(this);
						}
					};
				} else {
					detHtml.setNombreDocumento(detalleHtml.get("nombreDocumento").asText());
				}

				// 3: INDGUARDADO
				if (detalleHtml.has("indGuardado")) {

					detHtml.setIndGuardado(detalleHtml.get("indGuardado").asText());
					logger.debug("INDGUARDADO", detalleHtml.get("indGuardado").asText());

				} else {

					throw new JsonProcessingException("cabecera.detalleHTML.indGuardado, La etiqueta no existe ") {
						/**
						 *
						 */
						private static final long serialVersionUID = 1L;

						@SuppressWarnings("unused")
						private void init() {
							this.initCause(this);
						}
					};
				}

				if (detHtml.getIndGuardado().equals("") || detHtml.getIndGuardado().equals(" ")
						|| detHtml.getIndGuardado().equals("null")) {

					throw new JsonProcessingException("cabecera.detalleHTML.indGuardado, No puede ser nulo ") {
						/**
						 *
						 */
						private static final long serialVersionUID = 1L;

						@SuppressWarnings("unused")
						private void init() {
							this.initCause(this);
						}
					};

				} else {
					detHtml.setIndGuardado(detalleHtml.get("indGuardado").asText());
				}

				cab.setDetalleHTML(detHtml);

			} else if (detHtml.getIndHTML().equals("N")) {

				detHtml.setIndHTML(detalleHtml.get("indHTML").asText());
				cab.setDetalleHTML(detHtml);

			} else {

				throw new JsonProcessingException("cabecera.detalleHTML.indHTML, No puede ser nulo ") {
					/**
					 *
					 */
					private static final long serialVersionUID = 1L;

					@SuppressWarnings("unused")
					private void init() {
						this.initCause(this);
					}
				};
			}

			/**
			 * ******************************************************************************
			 * DETALLECORREO
			 * *****************************************************************************
			 */
			if (cabecera.has("detalleCorreo")) {

				detalleCorreo = cabecera.get("detalleCorreo");
				logger.debug("DETALLECORREO", cabecera.get("detalleCorreo"));

			} else {

				throw new JsonProcessingException("cabecera.detalleCorreo, La etiqueta no existe ") {
					/**
					 *
					 */
					private static final long serialVersionUID = 1L;

					@SuppressWarnings("unused")
					private void init() {
						this.initCause(this);
					}
				};

			}

			// VALIDANDO LAS ETIQUETAS DE DETALLECORREO
			if (detalleCorreo.has("indCorreo")) {

				detCorreo.setIndCorreo(detalleCorreo.get("indCorreo").asText());
				logger.debug("INDCORREO", detalleCorreo.get("indCorreo").asText());

			} else {

				throw new JsonProcessingException("cabecera.detalleCorreo.indCorreo, La etiqueta no existe ") {
					/**
					 *
					 */
					private static final long serialVersionUID = 1L;

					@SuppressWarnings("unused")
					private void init() {
						this.initCause(this);
					}
				};

			}

			// VALIDAMOS QUE INDCORREO = S
			if (detCorreo.getIndCorreo() != null && detCorreo.getIndCorreo().equals("S")) {

				// 1: DE
				if (detalleCorreo.has("de")) {

					detCorreo.setDe(detalleCorreo.get("de").asText());
					logger.debug("DE", detalleCorreo.get("de").asText());

				} else {

					throw new JsonProcessingException("cabecera.detalleCorreo.de, La etiqueta no existe ") {
						/**
						 *
						 */
						private static final long serialVersionUID = 1L;

						@SuppressWarnings("unused")
						private void init() {
							this.initCause(this);
						}
					};

				}

				if (detCorreo.getDe().equals("") || detCorreo.getDe().equals(" ") || detCorreo.getDe().equals("null")) {
					throw new JsonProcessingException("cabecera.detalleCorreo.de, No puede ser nulo ") {
						/**
						 *
						 */
						private static final long serialVersionUID = 1L;

						@SuppressWarnings("unused")
						private void init() {
							this.initCause(this);
						}
					};
				} else {
					String de = detCorreo.getDe();
					Pattern pattern = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
							Pattern.CASE_INSENSITIVE);
					Matcher matcher = pattern.matcher(de);

					if (matcher.find() == false) {

						throw new JsonProcessingException("cabecera.detalleCorreo.de, el correo es inválido") {

							private static final long serialVersionUID = 1L;

							@SuppressWarnings("unused")
							private void init() {
								this.initCause(this);
							}
						};
					} else {
						detCorreo.setDe(detalleCorreo.get("de").asText());
					}
				}
				// 2: ALIAS
				if (detalleCorreo.has("aliasDe")) {
					detCorreo.setAliasDe(detalleCorreo.get("aliasDe").asText());
					logger.debug("ALIAS", detalleCorreo.get("aliasDe").asText());

				} else {

					throw new JsonProcessingException("cabecera.detalleCorreo.aliasDe, La etiqueta no existe ") {
						/**
						 *
						 */
						private static final long serialVersionUID = 1L;

						@SuppressWarnings("unused")
						private void init() {
							this.initCause(this);
						}
					};
				}
				if (detCorreo.getAliasDe().equals("") || detCorreo.getAliasDe().equals(" ")
						|| detCorreo.getAliasDe().equals("null")) {

					throw new JsonProcessingException("cabecera.detalleCorreo.aliasDe, No puede ser nulo ") {
						/**
						 *
						 */
						private static final long serialVersionUID = 1L;

						@SuppressWarnings("unused")
						private void init() {
							this.initCause(this);
						}
					};
				} else {
					detCorreo.setAliasDe(detalleCorreo.get("aliasDe").asText());
				}

				// 3: PARA
				if (detalleCorreo.has("para")) {

					para = detalleCorreo.get("para");
					logger.debug("PARA", detalleCorreo.get("para"));

					String[] corPara = new String[5];
					int ind = 0;
					for (JsonNode j : para) {

						corPara[ind++] = j.asText();
					}
					detCorreo.setPara(corPara);

				} else {

					throw new JsonProcessingException("cabecera.detalleCorreo.para, La etiqueta no existe ") {
						/**
						 *
						 */
						private static final long serialVersionUID = 1L;

						@SuppressWarnings("unused")
						private void init() {
							this.initCause(this);
						}
					};
				}

				if ((detCorreo.getPara()[0] == null) || (detCorreo.getPara() == null)) {

					throw new JsonProcessingException("cabecera.detalleCorreo.para, No puede ser nulo ") {
						/**
						 *
						 */
						private static final long serialVersionUID = 1L;

						@SuppressWarnings("unused")
						private void init() {
							this.initCause(this);
						}
					};

				} else {
					for (String cpara : detCorreo.getPara()) {

						if (cpara != null) {
							logger.debug(cpara);
							Pattern pattern = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
									Pattern.CASE_INSENSITIVE);

							Matcher matcher = pattern.matcher(cpara);

							if (!matcher.find()) {

								throw new JsonProcessingException(
										"cabecera.detalleCorreo.para, Uno de los correo es inválido") {
									/**
									 *
									 */
									private static final long serialVersionUID = 1L;

									@SuppressWarnings("unused")
									private void init() {
										this.initCause(this);
									}
								};
							}
						}
					}
				}

				// 4: ASUNTOPLANTILLA
				if (detalleCorreo.has("asuntoPlantilla")) {

					detCorreo.setAsuntoPlantilla(detalleCorreo.get("asuntoPlantilla").asText());
					logger.debug("ASUNTO PLANTILLA", detalleCorreo.get("asuntoPlantilla").asText());

				} else {

					throw new JsonProcessingException(
							"cabecera.detalleCorreo.asuntoPlantilla, La etiqueta no existe ") {
						/**
						 *
						 */
						private static final long serialVersionUID = 1L;

						@SuppressWarnings("unused")
						private void init() {
							this.initCause(this);
						}
					};
				}
				if (detCorreo.getAsuntoPlantilla().equals("") || detCorreo.getAsuntoPlantilla().equals("null")
						|| detCorreo.getAsuntoPlantilla().equals(" ")) {

					throw new JsonProcessingException("cabecera.detalleCorreo.asuntoPlantilla No puede ser nulo ") {
						/**
						 *
						 */
						private static final long serialVersionUID = 1L;

						@SuppressWarnings("unused")
						private void init() {
							this.initCause(this);
						}
					};
				} else {
					detCorreo.setAsuntoPlantilla(detalleCorreo.get("asuntoPlantilla").asText());

				}

				// 5: ASUNTO
				if (detalleCorreo.has("asunto")) {

					detCorreo.setAsunto(detalleCorreo.get("asunto").asText());
					logger.debug("ASUNTO", detalleCorreo.get("asunto").asText());

				} else {

					throw new JsonProcessingException("cabecera.detalleCorreo.asunto, La etiqueta no existe ") {
						/**
						 *
						 */
						private static final long serialVersionUID = 1L;

						@SuppressWarnings("unused")
						private void init() {
							this.initCause(this);
						}
					};
				}

				if (detCorreo.getAsunto().equals("") || detCorreo.getAsunto().equals("null")
						|| detCorreo.getAsunto().equals(" ")) {

					throw new JsonProcessingException("cabecera.detalleCorreo.asunto, No puede ser nulo") {
						/**
						 *
						 */
						private static final long serialVersionUID = 1L;

						@SuppressWarnings("unused")
						private void init() {
							this.initCause(this);
						}
					};

				} else {
					detCorreo.setAsunto(detalleCorreo.get("asunto").asText());
					logger.debug("ASUNTO", detalleCorreo.get("asunto").asText());

				}

				if (detalleCorreo.has("indAdjuntarPDF")) {
					detCorreo.setIndAdjuntarPDF(detalleCorreo.get("indAdjuntarPDF").asText());
				}

				if (detalleCorreo.has("indAdjuntarTXT")) {
					detCorreo.setIndAdjuntarTXT(detalleCorreo.get("indAdjuntarTXT").asText());
				}

				if (detalleCorreo.has("conCopia")) {
					conCopia = detalleCorreo.get("conCopia");
					logger.debug("CON COPIA", detalleCorreo.get("conCopia"));
					String[] conCop = new String[5];
					int ind = 0;
					for (JsonNode j : conCopia) {

						conCop[ind++] = j.asText();
					}
					detCorreo.setConCopia(conCop);

				} else {

					throw new JsonProcessingException("cabecera.detalleCorreo.conCopia, La etiqueta no existe ") {
						/**
						 *
						 */
						private static final long serialVersionUID = 1L;

						@SuppressWarnings("unused")
						private void init() {
							this.initCause(this);
						}
					};
				}

				if (detalleCorreo.has("conCopiaOculta")) {
					conCopiaOculta = detalleCorreo.get("conCopiaOculta");
					logger.debug("CON COPIA OCULTA", detalleCorreo.get("conCopiaOculta"));
					String[] conCopOcul = new String[5];
					int ind = 0;
					for (JsonNode j : conCopiaOculta) {

						conCopOcul[ind++] = j.asText();
					}
					detCorreo.setConCopiaOculta(conCopOcul);
					logger.debug("finalizando CON COPIA OCULTA");
				} else {

					throw new JsonProcessingException("cabecera.detalleCorreo.conCopiaOculta, La etiqueta no existe ") {
						/**
						 *
						 */
						private static final long serialVersionUID = 1L;

						@SuppressWarnings("unused")
						private void init() {
							this.initCause(this);
						}
					};
				}
				logger.debug("iniciando ADJUNTOS ADICIONALES");
				if (detalleCorreo.has("adjuntosadicionales")) {
					logger.debug("Tiene ADJUNTOS ADICIONALES");
					adjuntosadicionales = detalleCorreo.get("adjuntosadicionales");
					logger.debug("ADJUNTO ADICIONAL", detalleCorreo.get("adjuntosadicionales"));

					detCorreo.setAdjuntosadicionales(adjuntosadicionales);

				} else {

					throw new JsonProcessingException(
							"cabecera.detalleCorreo.adjuntosadicionales, La etiqueta no existe ") {
						private static final long serialVersionUID = 1L;

						@SuppressWarnings("unused")
						private void init() {
							this.initCause(this);
						}
					};
				}
				logger.debug("finalizando ADJUNTOS ADICIONALES");
				cab.setDetalleCorreo(detCorreo);

			} else if (detCorreo.getIndCorreo().equals("N")) {
				detCorreo.setIndCorreo(detalleCorreo.get("indCorreo").asText());
				logger.debug("INDCORREO", detalleCorreo.get("indCorreo").asText());

				cab.setDetalleCorreo(detCorreo);

			} else {

				throw new JsonProcessingException("cabecera.detalleCorreo.indCorreo, No puede ser nulo") {
					/**
					 *
					 */
					private static final long serialVersionUID = 1L;

					@SuppressWarnings("unused")
					private void init() {
						this.initCause(this);
					}
				};
			}

			/**
			 * ******************************************************************************
			 * DETALLESMS
			 * *****************************************************************************
			 */
			logger.debug("iniciando DETALLE SMS");

			if (cabecera.has("detalleSMS")) {
				detalleSms = cabecera.get("detalleSMS");
				logger.debug("detalleSMS", cabecera.get("detalleSMS"));

			} else {

				throw new JsonProcessingException("cabecera.detalleSMS, La etiqueta no existe ") {

					private static final long serialVersionUID = 1L;

					@SuppressWarnings("unused")
					private void init() {
						this.initCause(this);
					}
				};
			}

			if (cabecera.has("detalleSMS")) {

				detalleSms = cabecera.get("detalleSMS");
				logger.debug("DETALLESMS", cabecera.get("detalleSMS"));

			} else {

				throw new JsonProcessingException("cabecera.detalleSMS, La etiqueta no existe ") {
					/**
					 *
					 */
					private static final long serialVersionUID = 1L;

					@SuppressWarnings("unused")
					private void init() {
						this.initCause(this);
					}
				};
			}

			// VALIDANDO LAS ETIQUETAS DE DETALLESMS
			if (detalleSms.has("indSMS")) {
				detSms.setIndSMS(detalleSms.get("indSMS").asText());
				logger.debug("INDSMS", detalleSms.get("indSMS").asText());
			} else {

				throw new JsonProcessingException("cabecera.DetalleSMS.indSMS, La etiqueta no existe ") {
					/**
					 *
					 */
					private static final long serialVersionUID = 1L;

					@SuppressWarnings("unused")
					private void init() {
						this.initCause(this);
					}
				};
			}

			// VALIDAMOS QUE INDSMS SEA = S
			if (detSms.getIndSMS() != null && detSms.getIndSMS().equals("S")) {

				// 1: DATASOURCE
				if (detalleSms.has("dataSource")) {
					detSms.setDataSource(detalleSms.get("dataSource").asText());
					logger.debug("DATASOURCE", detalleSms.get("dataSource").asText());
				} else {

					throw new JsonProcessingException("cabecera.DetalleSMS.dataSource, La etiqueta no existe ") {
						/**
						 *
						 */
						private static final long serialVersionUID = 1L;

						@SuppressWarnings("unused")
						private void init() {
							this.initCause(this);
						}
					};
				}

				if (detSms.getDataSource().equals("") || detSms.getDataSource().equals("null")
						|| detSms.getDataSource().equals(" ")) {

					throw new JsonProcessingException("cabecera.detalleSMS.dataSource, No puede ser nulo ") {
						/**
						 *
						 */
						private static final long serialVersionUID = 1L;

						@SuppressWarnings("unused")
						private void init() {
							this.initCause(this);
						}
					};
				} else {
					detSms.setDataSource(detalleSms.get("dataSource").asText());
				}

				// 2: DESTINATARIO
				if (detalleSms.has("destinatario")) {
					destinatario = detalleSms.get("destinatario");
					logger.debug("DESTINATARIO", detalleSms.get("destinatario"));

					String[] destinatarios = new String[3];
					int ind = 0;

					for (JsonNode j : destinatario) {
						destinatarios[ind++] = j.asText();
					}
					detSms.setDestinatario(destinatarios);

				} else {

					throw new JsonProcessingException("cabecera.DetalleSMS.destinatario, La etiqueta no existe ") {
						/**
						 *
						 */
						private static final long serialVersionUID = 1L;

						@SuppressWarnings("unused")
						private void init() {
							this.initCause(this);
						}
					};
				}
				if (detSms.getDestinatario()[0] == null || detSms.getDestinatario() == null) {

					throw new JsonProcessingException("cabecera.detalleSMS.destinatario, No puede ser nulo ") {
						/**
						 *
						 */
						private static final long serialVersionUID = 1L;

						@SuppressWarnings("unused")
						private void init() {
							this.initCause(this);
						}
					};
				} else {
					detSms.setIndSMS(detalleSms.get("indSMS").asText());
				}
			} else if (detSms.getIndSMS().equals("N")) {
				cab.setDetalleSMS(detSms);
			} else {

				throw new JsonProcessingException("cabecera.detalleSMS.indSMS, No puede ser nulo ") {
					/**
					 *
					 */
					private static final long serialVersionUID = 1L;

					@SuppressWarnings("unused")
					private void init() {
						this.initCause(this);
					}
				};
			}
			cab.setDetalleSMS(detSms);

			/**
			 * ******************************************************************************
			 * TRAZABILIDAD
			 * *****************************************************************************
			 */
			if (cabecera.has("detalleTrazabilidadCorreo")) {
				detalleTrazCor = cabecera.get("detalleTrazabilidadCorreo");
				logger.debug("TRAZABILIDAD", cabecera.get("detalleTrazabilidadCorreo"));

			} else {

				throw new JsonProcessingException("cabecera.detalleTrazabilidadCorreo, La etiqueta no existe ") {
					/**
					 *
					 */
					private static final long serialVersionUID = 1L;

					@SuppressWarnings("unused")
					private void init() {
						this.initCause(this);
					}
				};
			}

			// VALIDANDO LAS ETIQUETAS TRAZABILIDAD
			if (detalleTrazCor.has("indTrazabilidad")) {
				detTraz.setIndTrazabilidad(detalleTrazCor.get("indTrazabilidad").asText());
				logger.debug("INDTRAZABILIDAD", detalleTrazCor.get("indTrazabilidad").asText());
			} else {

				throw new JsonProcessingException(
						"cabecera.detalleTrazabilidadCorreo.indTrazabilidad, La etiqueta no existe ") {
					/**
					 *
					 */
					private static final long serialVersionUID = 1L;

					@SuppressWarnings("unused")
					private void init() {
						this.initCause(this);
					}
				};
			}

			// VALIDAMOS QUE INDTRAZABILIDAD SEA = S
			if (detTraz.getIndTrazabilidad() != null && detTraz.getIndTrazabilidad().equals("S")) {
				if (detalleTrazCor.has("dataSource")) {
					detTraz.setDataSource(detalleTrazCor.get("dataSource").asText());
					logger.debug("DATASOURCE {}", detalleTrazCor.get("dataSource").asText());
				} else {

					throw new JsonProcessingException(
							"cabecera.detalleTrazabilidadCorreo.dataSource, La etiqueta no existe ") {
						/**
						 *
						 */
						private static final long serialVersionUID = 1L;

						@SuppressWarnings("unused")
						private void init() {
							this.initCause(this);
						}
					};
				}

				// VALIDAMOS QUE LOS CAMPOS NO ESTEN VACIAS
				if (detTraz.getDataSource().equals("") || detTraz.getDataSource().equals("null")
						|| detTraz.getDataSource().equals(" ")) {

					throw new JsonProcessingException("cabecera.detalleTrazabilidad.dataSource, No puede ser nulo ") {
						/**
						 *
						 */
						private static final long serialVersionUID = 1L;

						@SuppressWarnings("unused")
						private void init() {
							this.initCause(this);
						}
					};
				} else {
					detTraz.setDataSource(detalleTrazCor.get("dataSource").asText());
				}
			} else if (detTraz.getIndTrazabilidad().equals("N")) {

				// detTraz.setIndTrazabilidad(detalleTrazCor.get("indTrazabilidad").asText());
				cab.setDetalleTrazabilidadCorreo(detTraz);

			} else {

				throw new JsonProcessingException("cabecera.detalleTrazabilidad.indTrazabilidad, No puede ser nulo ") {
					/**
					 *
					 */
					private static final long serialVersionUID = 1L;

					@SuppressWarnings("unused")
					private void init() {
						this.initCause(this);
					}
				};
			}

			cab.setDetalleTrazabilidadCorreo(detTraz);

			/**
			 * ******************************************************************************
			 * DETALLEGENERICO
			 * *****************************************************************************
			 */
			if (cabecera.has("detalleServicioGenerico")) {

				detServGen = cabecera.get("detalleServicioGenerico");
				logger.debug("DETALLESERVICIOGENERICO {}", cabecera.get("detalleServicioGenerico"));

			} else {

				throw new JsonProcessingException("cabecera.detalleServicioGenerico, La etiqueta no existe ") {
					/**
					 *
					 */
					private static final long serialVersionUID = 1L;

					@SuppressWarnings("unused")
					private void init() {
						this.initCause(this);
					}
				};

			}

			// VALIDANDO LAS ETIQUETAS DETALLEGENERICO
			if (detServGen.has("indServicioGenerico")) {
				detSGenerico.setIndServicioGenerico(detServGen.get("indServicioGenerico").asText());
				logger.debug("INDSERVICIOGENERICO", detServGen.get("indServicioGenerico").asText());

			} else {

				throw new JsonProcessingException(
						"cabecera.detalleServicioGenerico.indServicioGenerico, La etiqueta no existe ") {
					/**
					 *
					 */
					private static final long serialVersionUID = 1L;

					@SuppressWarnings("unused")
					private void init() {
						this.initCause(this);
					}
				};
			}

			// VALIDAMOS QUE INDSERVICIOGENERICO SEA = S
			if (detSGenerico.getIndServicioGenerico() != null && detSGenerico.getIndServicioGenerico().equals("S")) {
				if (detServGen.has("dataSource")) {
					detSGenerico.setDataSource(detServGen.get("dataSource").asText());
					logger.debug("DATASOURCE{}", detServGen.get("dataSource").asText());
				} else {

					throw new JsonProcessingException(
							"cabecera.detalleServicioGenerico.dataSource, La etiqueta no existe ") {
						/**
						 *
						 */
						private static final long serialVersionUID = 1L;

						@SuppressWarnings("unused")
						private void init() {
							this.initCause(this);
						}
					};
				}
				// VALIDAMOS QUE LOS CAMPOS NO ESTEN VACIOS
				if (detSGenerico.getDataSource().equals("") || detSGenerico.getDataSource().equals("null")
						|| detSGenerico.getDataSource().equals(" ")) {

					throw new JsonProcessingException(
							"cabecera.detalleServicioGenerico.dataSource, No puede ser nulo ") {
						/**
						 *
						 */
						private static final long serialVersionUID = 1L;

						@SuppressWarnings("unused")
						private void init() {
							this.initCause(this);
						}
					};
				} else {
					detSGenerico.setDataSource(detServGen.get("dataSource").asText());
				}
				cab.setDetalleServicioGenerico(detSGenerico);
			} else if (detSGenerico.getIndServicioGenerico().equals("N")) {
				detSGenerico.setIndServicioGenerico(detServGen.get("indServicioGenerico").asText());
				cab.setDetalleServicioGenerico(detSGenerico);

			} else {

				throw new JsonProcessingException(
						"cabecera.detalleServicioGenerico.indServicioGenerico, No puede ser nulo ") {
					/**
					 *
					 */
					private static final long serialVersionUID = 1L;

					@SuppressWarnings("unused")
					private void init() {
						this.initCause(this);
					}
				};
			}

			/****************************************************************************
			 * DETALLE LOCAL
			 **************************************************************************/
			if (cabecera.has("detalleLocal")) {
				detLocal = cabecera.get("detalleLocal");
				logger.debug("DETALLE Local" + cabecera.get("detalleLocal"));
			} else {
				/*
				 * throw new
				 * JsonProcessingException("cabecera.detalleLocal, La etiqueta no existe ") {
				 * 
				 * private static final long serialVersionUID = 1L;
				 * 
				 * @SuppressWarnings("unused") private void init() { this.initCause(this); } };
				 */
				detLocal = cabecera.with("detalleLocal");// at("detalleLocal");
			}

			// VALIDAR LA ETIQUETAS indPDF de DETALLE Local
			if (detLocal.has("indPDF")) {
				indPdfLocal = detLocal.get("indPDF");
				logger.debug("DETALLELOCAL.indPDF " + detLocal.get("indPDF"));
			} else {

//				throw new JsonProcessingException("cabecera.detalleLocal.indPDF, La etiqueta no existe ") {
//									private static final long serialVersionUID = 1L;
//
//					@SuppressWarnings("unused")
//					private void init() {
//						this.initCause(this);
//					}
//				};
				indPdfLocal = detLocal.with("indPDF"); // at("indPDF");
			}

			if (detLocal.has("indTXT")) {
				indTxtLocal = detLocal.get("indTXT");
				logger.debug("DETALLELOCAL.indTXT " + detLocal.get("indTXT"));
			} else {
				indTxtLocal = detLocal.with("indTXT");// at("indTXT");
//				throw new JsonProcessingException("cabecera.detalleLocal.indTXT, La etiqueta no existe ") {
//					/**
//					 *
//					 */
//					private static final long serialVersionUID = 1L;
//
//					@SuppressWarnings("unused")
//					private void init() {
//						this.initCause(this);
//					}
//				};
			}

			if (detLocal.has("indHTML")) {
				indHtmlLocal = detLocal.get("indHTML");
				logger.debug("DETALLELOCAL.indHTML " + detLocal.get("indHTML"));
			} else {

				indHtmlLocal = detLocal.with("indHTML");// at("indHTML");
//				throw new JsonProcessingException("cabecera.detalleLocal.indHTML, La etiqueta no existe ") {
//					/**
//					 *
//					 */
//					private static final long serialVersionUID = 1L;

//					@SuppressWarnings("unused")
//					private void init() {
//						this.initCause(this);
//					}
//				};
			}

			if (indPdfLocal.has("indLocalPDF")) {
				indPdfLocalCom.setIndLocalPDF(indPdfLocal.get("indLocalPDF").asText());
				indPdfLocalCom.setRutaDestinoPDF(indPdfLocal.get("rutaDestinoPDF").asText());
				logger.debug("DETALLELocal.indPDF.indLocalPDF " + indPdfLocal.get("indLocalPDF"));
			} else {
				indPdfLocalCom.setIndLocalPDF("N");
				indPdfLocalCom.setRutaDestinoPDF(null);
//				throw new JsonProcessingException("cabecera.detalleLocal.indPDF.indLocalPDF, La etiqueta no existe ") {
//					/**
//					 *
//					 */
//					private static final long serialVersionUID = 1L;
//
//					@SuppressWarnings("unused")
//					private void init() {
//						this.initCause(this);
//					}
//				};

			}

			if (indTxtLocal.has("indLocalTXT")) {
				indTxtLocalCom.setIndLocalTXT(indTxtLocal.get("indLocalTXT").asText());
				indTxtLocalCom.setRutaDestinoTXT(indTxtLocal.get("rutaDestinoTXT").asText());
				logger.debug("DETALLELocal.indTXT.indLocalTXT " + indTxtLocal.get("indLocalTXT"));
			} else {
				indTxtLocalCom.setIndLocalTXT("N");
				indTxtLocalCom.setRutaDestinoTXT(null);
//				throw new JsonProcessingException("cabecera.detalleLocal.indTXT.indLocalTXT, La etiqueta no existe ") {
//					/**
//					 *
//					 */
//					private static final long serialVersionUID = 1L;
//
//					@SuppressWarnings("unused")
//					private void init() {
//						this.initCause(this);
//					}
//				};
			}

			if (indHtmlLocal.has("indLocalHTML")) {
				indHtmlLocalCom.setIndLocalHTML(indHtmlLocal.get("indLocalHTML").asText());
				indHtmlLocalCom.setRutaDestinoHTML(indHtmlLocal.get("rutaDestinoHTML").asText());
				logger.debug("DETALLELocal.indHTML.indLocalHTML " + indHtmlLocal.get("indLocalHTML"));
			} else {
				indHtmlLocalCom.setIndLocalHTML("N");
				indHtmlLocalCom.setRutaDestinoHTML(null);
//				throw new JsonProcessingException(
//						"cabecera.detalleLocal.indHTML.indLocalHTML, La etiqueta no existe ") {
//					/**
//					 *
//					 */
//					private static final long serialVersionUID = 1L;
//
//					@SuppressWarnings("unused")
//					private void init() {
//						this.initCause(this);
//					}
//				};
			}

			if (detPdf.getIndPDF().equals("N") && indPdfLocalCom.getIndLocalPDF().equals("S")) {

				throw new JsonProcessingException(
						"cabecera.detalleLocal.indPDF.indLocalPDF, No puede guardar documento que no existe") {

					private static final long serialVersionUID = 1L;

					@SuppressWarnings("unused")
					private void init() {
						this.initCause(this);
					}
				};
			}

			if (detTxt.getIndTXT().equals("N") && indTxtLocalCom.getIndLocalTXT().equals("S")) {

				throw new JsonProcessingException(
						"cabecera.detalleLocal.indTXT.indLocalTXT, No puede guardar documento que no existe") {

					private static final long serialVersionUID = 1L;

					@SuppressWarnings("unused")
					private void init() {
						this.initCause(this);
					}
				};
			}

			if (detHtml.getIndHTML().equals("N") && indHtmlLocalCom.getIndLocalHTML().equals("S")) {

				throw new JsonProcessingException(
						"cabecera.detalleLocal.indHTML.indLocalHTML, No puede guardar documento que no existe") {

					private static final long serialVersionUID = 1L;

					@SuppressWarnings("unused")
					private void init() {
						this.initCause(this);
					}
				};
			}

			detalLocal.setIndPDF(indPdfLocalCom);
			detalLocal.setIndTXT(indTxtLocalCom);
			detalLocal.setIndHTML(indHtmlLocalCom);

			cab.setDetalleLocal(detalLocal);

			/**
			 * ******************************************************************************
			 * DETALLES3
			 * *****************************************************************************
			 */
			if (cabecera.has("detalleS3")) {
				detS3 = cabecera.get("detalleS3");
				logger.debug("DETALLES3", cabecera.get("detalleS3"));
			} else {

				throw new JsonProcessingException("cabecera.detalleS3, La etiqueta no existe ") {
					/**
					 *
					 */
					private static final long serialVersionUID = 1L;

					@SuppressWarnings("unused")
					private void init() {
						this.initCause(this);
					}
				};
			}

			// VALIDAR LA ETIQUETAS indPDF de DETALLES3
			if (detS3.has("indPDF")) {
				indPdf = detS3.get("indPDF");
				logger.debug("DETALLES3.indPDF", detS3.get("indPDF"));
			} else {

				throw new JsonProcessingException("cabecera.detalleS3.indPDF, La etiqueta no existe ") {
					/**
					 *
					 */
					private static final long serialVersionUID = 1L;

					@SuppressWarnings("unused")
					private void init() {
						this.initCause(this);
					}
				};
			}

			// VALIDAR LAS ETIQUETAS indPDF de DETALLES3
			if (indPdf.has("indS3PDF")) {
				indPdfS3.setIndS3PDF(indPdf.get("indS3PDF").asText());
				logger.debug("DETALLES3.indPDF.indS3PDF", indPdf.get("indS3PDF"));
			} else {

				throw new JsonProcessingException("cabecera.detalleS3.indPDF.indS3PDF, La etiqueta no existe ") {
					/**
					 *
					 */
					private static final long serialVersionUID = 1L;

					@SuppressWarnings("unused")
					private void init() {
						this.initCause(this);
					}
				};
			}
			// VALIDAMOS QUE EL CAMPO DE LA ETIQUETA IndPDF SEA ="S", YA QUE DEPENDE SI ESTE
			// CAMPO SI ES = S

			if (detPdf.getIndPDF().equals("N") && indPdfS3.getIndS3PDF().equals("S")) {

				throw new JsonProcessingException(
						"cabecera.detalleS3.indPDF.indS3PDF, No puede guardar documento que no existe") {

					private static final long serialVersionUID = 1L;

					@SuppressWarnings("unused")
					private void init() {
						this.initCause(this);
					}
				};
			}

			// VALIDAMOS QUE INDS3PDF SEA = S
			if (indPdfS3.getIndS3PDF() != null && indPdfS3.getIndS3PDF().equals("S")) {
				// VALIDAMOS QUE LAS ETIQUETAS EXISTAN
				if (indPdf.has("metadata")) {

					indPdfS3.setMetadata(indPdf.get("metadata"));
					// metaDataPdf = indPdf.get("metadata");
					logger.debug("METADATA {}", indPdf.get("metadata"));
				} else {

					throw new JsonProcessingException("cabecera.detalleS3.indPdf.metadata, La etiqueta no existe ") {
						/**
						 *
						 */
						private static final long serialVersionUID = 1L;

						@SuppressWarnings("unused")
						private void init() {
							this.initCause(this);
						}
					};
				}
				if (indPdf.has("rutaURLDestinoPDF")) {
					indPdfS3.setRutaURLDestinoPDF(indPdf.get("rutaURLDestinoPDF").asText());
					logger.debug("RUTAURLDestinoPDF {}", indPdf.get("rutaURLDestinoPDF").asText());
				} else {

					throw new JsonProcessingException(
							"cabecera.detalleS3.indPDF.rutaURLDestinoPDF, La etiqueta no existe ") {
						/**
						 *
						 */
						private static final long serialVersionUID = 1L;

						@SuppressWarnings("unused")
						private void init() {
							this.initCause(this);
						}
					};
				}
				// VALIDACION DE CAMPOS NO ESTEN VACIO
				if (indPdfS3.getRutaURLDestinoPDF().equals("") || indPdfS3.getRutaURLDestinoPDF().equals("null")
						|| indPdfS3.getRutaURLDestinoPDF().equals(" ")) {

					throw new JsonProcessingException(
							"cabecera.detalleS3.indPDF.rutaURLDestinoPDF, No puede ser nulo ") {
						/**
						 *
						 */
						private static final long serialVersionUID = 1L;

						@SuppressWarnings("unused")
						private void init() {
							this.initCause(this);
						}
					};
				} else {
					indPdfS3.setRutaURLDestinoPDF(indPdf.get("rutaURLDestinoPDF").asText());
				}
				detalS3.setIndPDF(indPdfS3);

			} else if (indPdfS3.getIndS3PDF().equals("N")) {
				indPdfS3.setIndS3PDF(indPdf.get("indS3PDF").asText());
				detalS3.setIndPDF(indPdfS3);
			} else {

				throw new JsonProcessingException("cabecera.detalleS3.indPDF.indS3PDF, No puede ser nulo ") {
					/**
					 *
					 */
					private static final long serialVersionUID = 1L;

					@SuppressWarnings("unused")
					private void init() {
						this.initCause(this);
					}
				};
			}

			// VALIDAR LA ETIQUETA DETALLES3TXT
			if (detS3.has("indTXT")) {
				indTxt = detS3.get("indTXT");
				logger.debug("DETALLES3TXT.indTXT {}", detS3.get("indTXT"));
			} else {

				throw new JsonProcessingException("cabecera.detalleS3.indTXT, La etiqueta no existe ") {
					/**
					 *
					 */
					private static final long serialVersionUID = 1L;

					@SuppressWarnings("unused")
					private void init() {
						this.initCause(this);
					}
				};
			}

			// VALIDAMOS LAS ETIQUETA DE INDTXT
			if (indTxt.has("indS3TXT")) {
				indTxtS3.setIndS3TXT(indTxt.get("indS3TXT").asText());
				logger.debug("DETALLES3TXT.indTXT.indS3TXT {}", indTxt.get("indS3TXT").asText());

			} else {

				throw new JsonProcessingException("cabecera.detalleS3.indTXT.indS3TXT, La etiqueta no existe ") {
					/**
					 *
					 */
					private static final long serialVersionUID = 1L;

					@SuppressWarnings("unused")
					private void init() {
						this.initCause(this);
					}
				};
			}

			// VALIDAMOS QUE EL CAMPO DE LA ETIQUETA indTXT SEA ="S", YA QUE DEPENDE SI ESTE
			// CAMPO SI ES = S
			if (detTxt.getIndTXT().equals("N") && indTxtS3.getIndS3TXT().equals("S")) {

				throw new JsonProcessingException(
						"cabecera.detalleS3.indTXT.indS3TXT, No puede guardar documento que no existe") {

					private static final long serialVersionUID = 1L;

					@SuppressWarnings("unused")
					private void init() {
						this.initCause(this);
					}
				};
			}

			// VALIDAMOS QUE INDTXT SEA = S
			if (indTxtS3.getIndS3TXT() != null && indTxtS3.getIndS3TXT().equals("S")) {

				if (indTxt.has("metadata")) {
					indTxtS3.setMetadata(indTxt.get("metadata"));
					// metaDataHtml = indTxt.get("metadata");
					logger.debug("METADATA {}", indTxt.get("metadata"));
				} else {

					throw new JsonProcessingException("cabecera.detalleS3.indTXT.metadata, La etiqueta no existe ") {
						/**
						 *
						 */
						private static final long serialVersionUID = 1L;

						@SuppressWarnings("unused")
						private void init() {
							this.initCause(this);
						}
					};
				}

				if (indTxt.has("rutaURLDestinoTXT")) {
					indTxtS3.setRutaURLDestinoTXT(indTxt.get("rutaURLDestinoTXT").asText());
					logger.debug("RUTA DESTINO {}", indTxt.get("rutaURLDestinoTXT").asText());
				} else {

					throw new JsonProcessingException(
							"cabecera.detalleS3.indTXT.rutaURLDestinoTXT, La etiqueta no existe ") {
						/**
						 *
						 */
						private static final long serialVersionUID = 1L;

						@SuppressWarnings("unused")
						private void init() {
							this.initCause(this);
						}
					};
				}

				// VALIDAR QUE LOS CAMPOS NO ESTEN VACIOS
				if (indTxtS3.getRutaURLDestinoTXT().equals("") || indTxtS3.getRutaURLDestinoTXT().equals("null")
						|| indTxtS3.getRutaURLDestinoTXT().equals(" ")) {

					throw new JsonProcessingException(
							"cabecera.detalleS3.indTXT.rutaURLDestinoTXT, No puede ser nulo ") {
						/**
						 *
						 */
						private static final long serialVersionUID = 1L;

						@SuppressWarnings("unused")
						private void init() {
							this.initCause(this);
						}
					};
				} else {
					indTxtS3.setRutaURLDestinoTXT(indTxt.get("rutaURLDestinoTXT").asText());
				}
				detalS3.setIndTXT(indTxtS3);
			} else if (indTxtS3.getIndS3TXT().equals("N")) {
				indTxtS3.setIndS3TXT(indTxt.get("indS3TXT").asText());
				detalS3.setIndTXT(indTxtS3);
			} else {

				throw new JsonProcessingException("cabecera.detalleS3.indTXT.indS3TXT, No puede ser nulo ") {
					/**
					 *
					 */
					private static final long serialVersionUID = 1L;

					@SuppressWarnings("unused")
					private void init() {
						this.initCause(this);
					}
				};
			}

			// VALIDANDO ETIQUETA INDHTML DE DETALLES3
			if (detS3.has("indHTML")) {
				indHtml = detS3.get("indHTML");
				logger.debug("IND HTML{}", detS3.get("indHTML"));

			} else {

				throw new JsonProcessingException("cabecera.detalleS3.indHTML, La etiqueta no existe ") {
					/**
					 *
					 */
					private static final long serialVersionUID = 1L;

					@SuppressWarnings("unused")
					private void init() {
						this.initCause(this);
					}
				};
			}

			// VALIDANDO LAS ETIQUETAS indHTML DETALLES3
			if (indHtml.has("indS3HTML")) {
				indHtmlS3.setIndS3HTML(indHtml.get("indS3HTML").asText());
				logger.debug("INS3HTML{}", indHtml.get("indS3HTML").asText());
			} else {

				throw new JsonProcessingException("cabecera.detalleS3.indHTML.indS3HTML, La etiqueta no existe ") {
					/**
					 *
					 */
					private static final long serialVersionUID = 1L;

					@SuppressWarnings("unused")
					private void init() {
						this.initCause(this);
					}
				};
			}

			// VALIDAMOS QUE EL CAMPO DE LA ETIQUETA indTXT SEA ="S", YA QUE DEPENDE SI ESTE
			// CAMPO SI ES = S
			if (detHtml.getIndHTML().equals("N") && indHtmlS3.getIndS3HTML().equals("S")) {

				throw new JsonProcessingException(
						"cabecera.detalleS3.indHTML.indS3HTML, No puede guardar documento que no existe") {

					private static final long serialVersionUID = 1L;

					@SuppressWarnings("unused")
					private void init() {
						this.initCause(this);
					}
				};
			}
			// VALIDAMOS QUE INDHTML SEA = S
			if (indHtmlS3.getIndS3HTML() != null && indHtmlS3.getIndS3HTML().equals("S")) {
				// VALISAMOS QUE EXISTA LAS ETIQUETAS
				if (indHtml.has("metadata")) {
					indHtmlS3.setMetadata(indHtml.get("metadata"));
					// metaDataHtml = indHtml.get("metadata");
					logger.debug("METADATA{}", indHtml.get("metadata"));
				} else {

					throw new JsonProcessingException("cabecera.detalleS3.indHTML.metadata, La etiqueta no existe ") {
						/**
						 *
						 */
						private static final long serialVersionUID = 1L;

						@SuppressWarnings("unused")
						private void init() {
							this.initCause(this);
						}
					};
				}
				if (indHtml.has("rutaURLDestinoHTML")) {
					indHtmlS3.setRutaURLDestinoHTML(indHtml.get("rutaURLDestinoHTML").asText());
					logger.debug("RUTA URL DESTINO HTML{}", indHtml.get("rutaURLDestinoHTML").asText());
				} else {

					throw new JsonProcessingException(
							"cabecera.detalleS3.indHTML.rutaURLDestinoHTML, La etiqueta no existe ") {
						/**
						 *
						 */
						private static final long serialVersionUID = 1L;

						@SuppressWarnings("unused")
						private void init() {
							this.initCause(this);
						}
					};
				}
				// VALIDAR QUE LOS CAMPOS NO ESTEN VACIO
				String ruta = indHtmlS3.getRutaURLDestinoHTML();
				if (ruta.equals("") || ruta.equals("null")) {

					throw new JsonProcessingException(
							"cabecera.detalleS3.indHTML.rutaURLDestinoHTML, No puede ser nulo ") {
						/**
						 *
						 */
						private static final long serialVersionUID = 1L;

						@SuppressWarnings("unused")
						private void init() {
							this.initCause(this);
						}
					};
				} else {
					indHtmlS3.setRutaURLDestinoHTML(indHtml.get("rutaURLDestinoHTML").asText());
				}

				detalS3.setIndHTML(indHtmlS3);
				//
			} else if (indHtmlS3.getIndS3HTML().equals("N")) {
				indHtmlS3.setIndS3HTML(indHtml.get("indS3HTML").asText());
				detalS3.setIndHTML(indHtmlS3);
			} else {

				throw new JsonProcessingException("cabecera.detalleS3.indHTML.indS3HTML, No puede ser nulo ") {
					/**
					 *
					 */
					private static final long serialVersionUID = 1L;

					@SuppressWarnings("unused")
					private void init() {
						this.initCause(this);
					}
				};
			}
			cab.setDetalleS3(detalS3);

			/**
			 * ******************************************************************************
			 * DETALLEFILENET
			 * *****************************************************************************
			 */
			if (cabecera.has("detalleFilenet")) {
				detFNet = cabecera.get("detalleFilenet");
			} else {

				throw new JsonProcessingException("cabecera.detalleFilenet, La etiqueta no existe ") {
					/**
					 *
					 */
					private static final long serialVersionUID = 1L;

					@SuppressWarnings("unused")
					private void init() {
						this.initCause(this);
					}
				};
			}

			// VALIDAMOS LAS ETIQUETA DE DETALLEFILENET
			if (detFNet.has("repositoryId")) {
				detFileNet.setRepositoryId(detFNet.get("repositoryId").asText());
				logger.debug("REPOSITORY ID{}", detFNet.get("repositoryId").asText());
			} else {

				throw new JsonProcessingException("cabecera.detalleFilenet.repositoryId, La etiqueta no existe ") {
					/**
					 *
					 */
					private static final long serialVersionUID = 1L;

					@SuppressWarnings("unused")
					private void init() {
						this.initCause(this);
					}
				};
			}
			// VALIDAR ETIQUETA INDPDF DETALLEFINET
			if (detFNet.has("indPDF")) {
				indPDFFilenet = detFNet.get("indPDF");
				logger.debug("INDPDF{}", detFNet.get("indPDF"));
			} else {

				throw new JsonProcessingException("detalleFilenet.indPDF, La etiqueta no existe ") {
					/**
					 *
					 */
					private static final long serialVersionUID = 1L;

					@SuppressWarnings("unused")
					private void init() {
						this.initCause(this);
					}
				};
			}

			// VALIDAR LAS ETIQUETAS DE INDPDF DE DETALLEFINET
			if (indPDFFilenet.has("indFilenetPDF")) {
				fileNetPdf.setIndFilenetPDF(indPDFFilenet.get("indFilenetPDF").asText());

				logger.debug("IND FILENET PDF{}", indPDFFilenet.get("indFilenetPDF").asText());
			} else {

				throw new JsonProcessingException("detalleFilenet.indPDF.indFilenetPDF, La etiqueta no existe ") {
					/**
					 *
					 */
					private static final long serialVersionUID = 1L;

					@SuppressWarnings("unused")
					private void init() {
						this.initCause(this);
					}
				};
			}

			// VALIDAMOS QUE INDFILENETPDF SEA = S
			if (fileNetPdf.getIndFilenetPDF() != null && fileNetPdf.getIndFilenetPDF().equals("S")) {
				// VALIDAR QUE LAS ETIQUETAS EXITAN

				if (indPDFFilenet.has("propiedades")) {

					PropiedadesFilenetPdf = indPDFFilenet.get("propiedades");

					fileNetPdf.setPropiedades(PropiedadesFilenetPdf);

					logger.debug("PROPIEDADESOBTENIDASFILENETTXT{}", indPDFFilenet.get("propiedades"));

				} else {

					throw new JsonProcessingException("detalleFilenet.indPDF.propiedades, La etiqueta no existe ") {
						/**
						 *
						 */
						private static final long serialVersionUID = 1L;

						@SuppressWarnings("unused")
						private void init() {
							this.initCause(this);
						}
					};
				}
				if (indPDFFilenet.has("contentStream")) {
					contentStreamFilenetPdf = indPDFFilenet.get("contentStream");
					fileNetPdf.setContentStream(contentStreamFilenetPdf);
					logger.debug("CONTENTSTREAM{}", indPDFFilenet.get("contentStream"));
				} else {

					throw new JsonProcessingException("detalleFilenet.indPDF.contentStream, La etiqueta no existe ") {
						/**
						 *
						 */
						private static final long serialVersionUID = 1L;

						@SuppressWarnings("unused")
						private void init() {
							this.initCause(this);
						}
					};
				}
				// VALIDAR QUE LOS CAMPOS NO ESTEN VACIOS
				PropiedadesFilenetPdf = indPDFFilenet.get("propiedades");
				if (PropiedadesFilenetPdf == null || PropiedadesFilenetPdf.size() == 0) {

					throw new JsonProcessingException(
							"cabecera.detalleFilenet.indPDF.propiedades, No puede ser nulo ") {
						/**
						 *
						 */
						private static final long serialVersionUID = 1L;

						@SuppressWarnings("unused")
						private void init() {
							this.initCause(this);
						}
					};
				}
				contentStreamFilenetPdf = indPDFFilenet.get("contentStream");
				if (contentStreamFilenetPdf == null || contentStreamFilenetPdf.size() == 0) {

					throw new JsonProcessingException(
							"cabecera.detalleFilenet.indPDF.contentStream, No puede ser nulo ") {
						/**
						 *
						 */
						private static final long serialVersionUID = 1L;

						@SuppressWarnings("unused")
						private void init() {
							this.initCause(this);
						}
					};
				}
				detFileNet.setIndPDF(fileNetPdf);
			} else if (fileNetPdf.getIndFilenetPDF().equals("N")) {
				fileNetPdf.setIndFilenetPDF(indPDFFilenet.get("indFilenetPDF").asText());
				detFileNet.setIndPDF(fileNetPdf);

			} else {

				throw new JsonProcessingException("cabecera.detalleFilenet.indTXT.indFilenetPDF, No puede ser nulo") {
					/**
					 *
					 */
					private static final long serialVersionUID = 1L;

					@SuppressWarnings("unused")
					private void init() {
						this.initCause(this);
					}
				};
			}

			// VALIDAR ETIQUETA INDTXT DETALLEFINET
			if (detFNet.has("indTXT")) {
				indTXTFilenet = detFNet.get("indTXT");
				logger.debug("INDTXT{}", detFNet.get("indTXT"));
			} else {

				throw new JsonProcessingException("detalleFilenet.indTXT, La etiqueta no existe ") {
					/**
					 *
					 */
					private static final long serialVersionUID = 1L;

					@SuppressWarnings("unused")
					private void init() {
						this.initCause(this);
					}
				};
			}
			// VALIDAR LAS ETIQUETAS DE INDTXT DE DETALLEFINET

			if (indTXTFilenet.has("indFilenetTXT")) {
				fileNetTxt.setIndFilenetTXT(indTXTFilenet.get("indFilenetTXT").asText());
				logger.debug("INDFILENET {}", indTXTFilenet.get("indFilenetTXT").asText());
			} else {

				throw new JsonProcessingException("detalleFilenet.indTXT.indTXTFilenet, La etiqueta no existe ") {
					/**
					 *
					 */
					private static final long serialVersionUID = 1L;

					@SuppressWarnings("unused")
					private void init() {
						this.initCause(this);
					}
				};
			}
			// VALIDAMOS QUE INDFILENETTXT SEA = S

			if (fileNetTxt.getIndFilenetTXT() != null && fileNetTxt.getIndFilenetTXT().equals("S")) {
				// VALIDAR QUE EXISTA LAS ETIQUETAS
				if (indTXTFilenet.has("propiedades")) {

					PropiedadesFilenetTxt = indTXTFilenet.get("propiedades");

					fileNetTxt.setPropiedades(PropiedadesFilenetTxt);

					logger.debug("PROPIEDADESOBTENIDASFILENETTXT{}", indTXTFilenet.get("propiedades"));

				} else {

					throw new JsonProcessingException("detalleFilenet.indTXT.propiedades, La etiqueta no existe ") {
						/**
						 *
						 */
						private static final long serialVersionUID = 1L;

						@SuppressWarnings("unused")
						private void init() {
							this.initCause(this);
						}
					};
				}
				if (indTXTFilenet.has("contentStream")) {
					contentStreamFileneTxt = indTXTFilenet.get("contentStream");
					fileNetTxt.setContentStream(contentStreamFileneTxt);
					logger.debug("CONTENTSTREAM{}", indTXTFilenet.get("contentStream"));
				} else {

					throw new JsonProcessingException("detalleFilenet.indTXT.contentStream, La etiqueta no existe ") {
						/**
						 *
						 */
						private static final long serialVersionUID = 1L;

						@SuppressWarnings("unused")
						private void init() {
							this.initCause(this);
						}
					};
				}
				// VALIDAR QUE EL CAMPO NO ESTE VACIO

				PropiedadesFilenetTxt = indTXTFilenet.get("propiedades");
				if (PropiedadesFilenetTxt == null || PropiedadesFilenetTxt.size() == 0) {

					throw new JsonProcessingException("cabecera.detalleFilenet.indTXT.propiedades, No puede ser nulo") {
						/**
						 *
						 */
						private static final long serialVersionUID = 1L;

						@SuppressWarnings("unused")
						private void init() {
							this.initCause(this);
						}
					};
				}
				contentStreamFileneTxt = indTXTFilenet.get("contentStream");
				if (contentStreamFileneTxt == null || contentStreamFileneTxt.size() == 0) {

					throw new JsonProcessingException(
							"cabecera.detalleFilenet.indTXT.contentStream, No puede ser nulo") {
						/**
						 *
						 */
						private static final long serialVersionUID = 1L;

						@SuppressWarnings("unused")
						private void init() {
							this.initCause(this);
						}
					};
				}
				detFileNet.setIndTXT(fileNetTxt);

			} else if (fileNetTxt.getIndFilenetTXT().equals("N")) {
				fileNetTxt.setIndFilenetTXT(indTXTFilenet.get("indFilenetTXT").asText());
				detFileNet.setIndTXT(fileNetTxt);
			} else {

				throw new JsonProcessingException("cabecera.detalleFilenet.indTXT.indFilenetTXT, No puede ser nulo") {
					/**
					 *
					 */
					private static final long serialVersionUID = 1L;

					@SuppressWarnings("unused")
					private void init() {
						this.initCause(this);
					}
				};
			}

			// VALIDAR ETIQUETA INDHTML DETALLEFINET
			if (detFNet.has("indHTML")) {
				indHTMLFilenet = detFNet.get("indHTML");
				logger.debug("INDHTML{}", indHTMLFilenet = detFNet.get("indHTML"));
			} else {

				throw new JsonProcessingException("detalleFilenet.indHTML, La etiqueta no existe ") {
					/**
					 *
					 */
					private static final long serialVersionUID = 1L;

					@SuppressWarnings("unused")
					private void init() {
						this.initCause(this);
					}
				};
			}
			// VALIDAR LAS ETIQUETAS DE INDHTML DE DETALLEFINET

			if (indHTMLFilenet.has("indFilenetHTML")) {
				fileNetHtml.setIndFilenetHTML(indHTMLFilenet.get("indFilenetHTML").asText());
				logger.debug("IND FILENET HTML{}", indHTMLFilenet.get("indFilenetHTML").asText());
			}

			else {

				throw new JsonProcessingException("detalleFilenet.indHTML.indFilenetHTML, La etiqueta no existe ") {
					/**
					 *
					 */
					private static final long serialVersionUID = 1L;

					@SuppressWarnings("unused")
					private void init() {
						this.initCause(this);
					}
				};
			}
			// VALIDAMOS QUE INDHTML SEA =S
			if (fileNetHtml.getIndFilenetHTML() != null && fileNetHtml.getIndFilenetHTML().equals("S")) {
				// VALIDAR QUE LAS ETIQUETAS EXISTAN

				if (indHTMLFilenet.has("propiedades")) {

					PropiedadesFilenetHtml = indHTMLFilenet.get("propiedades");

					fileNetHtml.setPropiedades(PropiedadesFilenetHtml);

					logger.debug("PROPIEDADESOBTENIDASFILENETTXT{}", indHTMLFilenet.get("propiedades"));
				} else {

					throw new JsonProcessingException("detalleFilenet.indHTML.propiedades, La etiqueta no existe ") {
						/**
						 *
						 */
						private static final long serialVersionUID = 1L;

						@SuppressWarnings("unused")
						private void init() {
							this.initCause(this);
						}
					};
				}
				if (indHTMLFilenet.has("contentStream")) {
					contentStreamFilenetHtml = indHTMLFilenet.get("contentStream");
					fileNetHtml.setContentStream(contentStreamFilenetHtml);
					logger.debug("CONTENTSTREAM{}", indHTMLFilenet.get("contentStream"));
				} else {

					throw new JsonProcessingException("detalleFilenet.indHTML.contentStream, La etiqueta no existe ") {
						/**
						 *
						 */
						private static final long serialVersionUID = 1L;

						@SuppressWarnings("unused")
						private void init() {
							this.initCause(this);
						}
					};
				}
				// VALIDAR QUE LOS CAMPOS NO ESTEN VACIOS
				PropiedadesFilenetHtml = indHTMLFilenet.get("propiedades");
				if (PropiedadesFilenetHtml == null || PropiedadesFilenetHtml.size() == 0) {

					throw new JsonProcessingException(
							"cabecera.detalleFilenet.indHTML.propiedades, No puede ser nulo") {
						/**
						 *
						 */
						private static final long serialVersionUID = 1L;

						@SuppressWarnings("unused")
						private void init() {
							this.initCause(this);
						}
					};
				}
				contentStreamFilenetHtml = indHTMLFilenet.get("contentStream");
				if (contentStreamFilenetHtml == null || contentStreamFilenetHtml.size() == 0) {

					throw new JsonProcessingException(
							"cabecera.detalleFilenet.indHTML.contentStream, No puede ser nulo") {
						/**
						 *
						 */
						private static final long serialVersionUID = 1L;

						@SuppressWarnings("unused")
						private void init() {
							this.initCause(this);
						}
					};
				}
				detFileNet.setIndHTML(fileNetHtml);
			} else if (fileNetHtml.getIndFilenetHTML().equals("N")) {
				fileNetHtml.setIndFilenetHTML(indHTMLFilenet.get("indFilenetHTML").asText());
				detFileNet.setIndHTML(fileNetHtml);
			} else {

				throw new JsonProcessingException("cabecera.detalleFilenet.indHTML.contentStream, No puede ser nulo") {
					/**
					 *
					 */
					private static final long serialVersionUID = 1L;

					@SuppressWarnings("unused")
					private void init() {
						this.initCause(this);
					}
				};
			}
			cab.setDetalleFilenet(detFileNet);

			// VALIDANDO JSON
			if (node.has("jsonData")) {
				jsonData = node.get("jsonData");
			} else {

				throw new JsonProcessingException("jsonData, La etiqueta no existe ") {
					/**
					 *
					 */
					private static final long serialVersionUID = 1L;

					@SuppressWarnings("unused")
					private void init() {
						this.initCause(this);
					}
				};
			}

			solicitudMod.setOrigen(o);

			solicitudMod.setCabecera(cab);

			solicitudMod.setJsonData(jsonData);

			logger.debug(solicitudMod.toString());

			return mapper.writeValueAsString(solicitudMod);
		} catch (Exception e) {
			logger.debug("ERROR {}", e.getMessage());
			throw new JsonProcessingException("[Validación] " + e.getMessage()) {
				private static final long serialVersionUID = 1L;

				@SuppressWarnings("unused")
				private void init() {
					this.initCause(this);
				}
			};
		}
	}

}
