package pe.soapros.generacionccm.commons;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class AppProperties {

	
	public static final Properties getExternalProperties(String fileName) {
		Properties properties = null;
		FileInputStream fis = null;
		File file = null;
		
		try {
			file = new File(fileName);
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
