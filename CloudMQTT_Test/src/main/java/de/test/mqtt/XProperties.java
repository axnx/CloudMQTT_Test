package de.test.mqtt;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class XProperties {
  
	public Properties read (String propertiesFile) {
	
		Properties prop = new Properties();
		InputStream input = null;
		
		try {

			input = new FileInputStream(propertiesFile);
			prop.load(input);
			if(prop.containsKey("externalfile")){
				String externalfile = prop.getProperty("externalfile");
				if(prop.getProperty("externalfile").length() > 0 ){
					return this.read(externalfile);
				}
			}
			
			return prop;

		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
  }
}