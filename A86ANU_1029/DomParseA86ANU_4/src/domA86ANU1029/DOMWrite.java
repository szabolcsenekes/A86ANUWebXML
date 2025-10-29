package domA86ANU1029;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class DOMWrite {
	
	public static void main(String[] args) throws ParserConfigurationException,
	TransformerException {
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		
		Document doc = builder.newDocument();
		
		Element root = doc.createElementNS("DOMA86ANU", "hallgatok");
		doc.appendChild(root);
		
		root.appendChild(createUser(doc, "1", "Peter", "Nagy", "Web Developer"));
		root.appendChild(createUser(doc, "2", "Piroska", "Vigh", "Java programozo"));
		root.appendChild(createUser(doc, "3", "Ferenc", "Kiss", "associate professor"));
		
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transf = transformerFactory.newTransformer();
		
		transf.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		transf.setOutputProperty(OutputKeys.INDENT, "yes");
		transf.setOutputProperty("{https://xml.apache.org/xslt}indent-amount", "2");
		
		
		DOMSource source = new DOMSource(doc);
		
		File myFile = new File("hallgatok1.xml");
		
		StreamResult console = new StreamResult(System.out);
		StreamResult file = new StreamResult(myFile);
		
		transf.transform(source, console);
		transf.transform(source, file);
	}
	
	private static Node createUser(Document doc, String id, String firstName,
			String lastName, String profession) {
		
		Element user = doc.createElement("hallgato");
		
		user.setAttribute("id", id);
		user.appendChild(createUserElement(doc, "keresztnev", firstName));
		user.appendChild(createUserElement(doc, "vezeteknev", lastName));
		user.appendChild(createUserElement(doc, "foglalkozas", profession));
		
		return user;
	}
	
	private static Node createUserElement(Document doc, String name, String value) {
		
		Element node = doc.createElement(name);
		node.appendChild(doc.createTextNode(value));
		
		return node;
	}

}
