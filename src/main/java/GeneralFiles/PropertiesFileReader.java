package GeneralFiles;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesFileReader {
	
	public Properties getProperty() throws IOException {
		
		Properties properties= new Properties();
		
		try {
			properties.load(new FileInputStream("src/main/resources/config.properties"));
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		
		return properties;
	}

}
