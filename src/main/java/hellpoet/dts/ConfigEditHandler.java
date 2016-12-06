package hellpoet.dts;

import java.io.File;
import java.io.FileNotFoundException;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

public class ConfigEditHandler {
	private static final String servletConfig = "\t<servlet>\n"
												+"\t\t<servlet-name></servlet-name>\n"
												+"\t\t<servlet-class></servlet-class>\n"
												+"\t</servlet>\n"
												+"\t<servlet-mapping>\n"
												+"\t\t<servlet-name></servlet-name>\n"
												+"\t\t<url-pattern></url-pattern>\n"
												+"\t</servlet-mapping>\n";
	private static ConfigEditHandler instance;
	
	private ConfigEditHandler(){
		
	}
	
	public static ConfigEditHandler getInstance(){
		if(instance == null){
			instance = new ConfigEditHandler();
		}
		return instance;
	}
	
	public void WriteConfigFile(String configFilePath, String servletName, String servletClass, String urlPattern) throws FileNotFoundException, DocumentException{
		File configfile = new File(configFilePath);
		if(!configfile.exists()){
			throw new FileNotFoundException(String.format("File path: %s.", configFilePath));
		}
		
		SAXReader reader = new SAXReader();
		Document   document = reader.read(configfile); 
	}
}
