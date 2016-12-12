package hellpoet.dts;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.dom4j.*;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
/* add config like:
 * 
 * <servlet>
 * 	<servlet-name></servlet-name>
 *	<servlet-class></servlet-class>
 * </servlet>
 * <servlet-mapping>
 *	<servlet-name></servlet-name>
 *	<url-pattern></url-pattern>
 * </servlet-mapping>
 */
public class ConfigEditHandler {
	private static ConfigEditHandler instance;
	
	private ConfigEditHandler(){
		
	}
	
	public static ConfigEditHandler getInstance(){
		if(instance == null){
			instance = new ConfigEditHandler();
		}
		return instance;
	}
	
	public void WriteConfigFile(String configFilePath, String servletName, String servletClass, String urlPattern) throws DocumentException, IOException{
		File configfile = new File(configFilePath);
		if(!configfile.exists()){
			throw new FileNotFoundException(String.format("Cannot find the file, path: %s.", configFilePath));
		}
		
		SAXReader reader = new SAXReader();
		Document doc = reader.read(configfile);
		Element root = doc.getRootElement();
		Element servletEle = addNode(root, "servlet");
		addNode(servletEle, "servlet-name", servletName);
		addNode(servletEle, "servlet-class", servletClass);
		Element servletMappingEle = addNode(root, "servlet-mapping");
		addNode(servletMappingEle, "servlet-name", servletName);
		addNode(servletMappingEle, "url-pattern", urlPattern);
		OutputFormat format = OutputFormat.createPrettyPrint(); 
		XMLWriter writer = new XMLWriter(new FileOutputStream(configfile, false),format); 
		writer.write(doc);
		writer.close();
	}
	
	private Element addNode(Element parentElement, String name){
		Element newEle = parentElement.addElement(name);
		return newEle;
	}
	
	private Element addNode(Element parentElement, String name, String value){
		Element newEle = parentElement.addElement(name);
		newEle.setText(value);
		return newEle;
	}
}
