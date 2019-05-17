package pe.soapros.generacionccm.servlet;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pe.soapros.generacionccm.commons.ConfigurationProperties;
import pe.soapros.generacionccm.commons.Constants;

/**
 * Servlet implementation class InicioAppServlet
 */
@WebServlet(urlPatterns = "/InicioAppServlet", loadOnStartup = 1, asyncSupported = true)
public class InicioAppServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private Logger logger = LoggerFactory.getLogger(InicioAppServlet.class);

	public void init() throws ServletException {
		ServletContext servletContext = null;
		String rutaLog4j = null;
		try {
			rutaLog4j = System.getProperty(ConfigurationProperties.RUTA_ARCH_DEFAULT) + Constants.RUTA_LOG4J_PROPERTIES;
			PropertyConfigurator.configure(rutaLog4j);
			logger.info("[------------ INICIADO LOGS DEL API ---------]");
			servletContext = super.getServletContext();
			String ruta = servletContext.getRealPath("/");
			Constants.C_RUTA_WAR = ruta;
			logger.info("ruta --> " + ruta);
		} catch (Exception e) {
			logger.error("Error al iniciar parametros de la API ", e);
		}
		logger.info("INICIO PARAMETROS DE CONTEXTO FINALIZADO");
	}

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public InicioAppServlet() {
		super();
	}

	/**
	 * Destroy Instance Object
	 */
	public void destroy() {
		super.destroy();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * Metodo para procesar los para metros enviados desde el cliente
	 * 
	 * @param request
	 * @param response
	 */
	public void processRequest(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html");
	}

}
