package pe.soapros.generacionccm.commons;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class ConfigurationProperties {

	public static final String RUTA_ARCH_DEFAULT = "RUTA_ARCH_CONF";
	
	/**
	 * Método que crea un objeto Properties con los parametros configurados
	 * en un archivo externo en el servidor de aplicaciones. Toma como ruta
	 * la que se configura en la variable de sistema: "RUTA_ARCH_CONF",
	 * en caso no se configure esta variable en el servidor usar el método al
	 * que se le pasa la ruta y nombre del archivo como parámetros.
	 * @param fileName Nombre del archivo properties
	 * (sin la extensión .properties)
	 * @return Intancia con los parametros cargados desde el archivo
	 */
	public static final Properties getExternalProperties(String fileName) {
		String ruta = null;
		
		ruta = System.getProperty(RUTA_ARCH_DEFAULT)
					+ Constants.RUTA_API_CONFIG_PROPERTIES;
		
		return getExternalProperties(ruta, fileName);
	}
	
	/**
	 * Método que crea un objeto Properties con los parametros configurados
	 * en un archivo externo en el servidor de aplicaciones
	 * @param ruta Ruta donde se encuetra el archivo propeties
	 * @param fileName Nombre del archivo properties si la extensión
	 * @return Intancia con los parametros cargados desde el archivo
	 */
	public static final Properties getExternalProperties(String ruta,
			String fileName) {
		Properties properties = null;
		FileInputStream fis = null;
		File file = null;
		
		try {
			file = new File(ruta + System.getProperty("file.separator")
					+ fileName + ".properties");
			fis = new FileInputStream(file);
			
			properties = new Properties();
			properties.load(fis);
			
			fis.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return properties;
	}

}
