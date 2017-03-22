package utilities;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public abstract class UtilTest {

	public static Map<String,Integer> beans;
	
	/**
	 * Reads the population context of the application and, assuming a sequential behaviour of
	 * ids when populating, maps each bean name read in the xml with its id in the database.
	 * @throws ParserConfigurationException 
	 * @throws IOException 
	 * @throws SAXException 
	 */
	public static void mapBeansToIds() throws SAXException, IOException, ParserConfigurationException {
		beans = new HashMap<String,Integer>();
		Document doc;

		doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new File("tmp/persistedIds.xml"));

		doc.getDocumentElement().normalize();
		
		NodeList a = doc.getElementsByTagName("entity");
		
		for (int i = 0; i < a.getLength(); i++){
			String bean = ((Element) a.item(i)).getAttribute("beanId");
			int id = Integer.parseInt(a.item(i).getTextContent().trim());
			beans.put(bean, id);
		}

	}
	
	/**
	 * Given the name of a bean, returns the id of such a bean in the database
	 * @param beanName The name of a certain bean
	 * @return The id that is associated to the bean passed as parameter
	 */
	public static int getIdFromBeanName(String beanName) {
		return beans.get(beanName);
	}
}
