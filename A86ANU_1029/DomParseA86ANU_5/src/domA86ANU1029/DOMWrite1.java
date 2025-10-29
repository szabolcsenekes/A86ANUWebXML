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

public class DOMWrite1 {
	
	public static void main(String[] args) throws ParserConfigurationException,
	TransformerException {
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		
		Document doc = builder.newDocument();
		
		Element root = doc.createElementNS("DOMA86ANU", "A86ANU_orarend");
		doc.appendChild(root);
		
		root.appendChild(createUser(doc, "o01", "elmelet", "Web technológiák 1", "Hétfő", "10", "12", "E5", "Dr. Agárdi Anita", "Programtervező informatikus BSc"));
		root.appendChild(createUser(doc, "o02", "gyakorlat", "Web technológiák 1", "Hétfő", "14", "16", "L101", "Dr. Agárdi Anita", "Programtervező informatikus BSc"));
		root.appendChild(createUser(doc, "o03", "elmelet", "Mesterséges intelligencia alapok", "Kedd", "10", "12", "E32", "Dr. Tamás Judit", "Programtervező informatikus BSc"));
		root.appendChild(createUser(doc, "o04", "gyakorlat", "Mesterséges intelligencia alapok", "Kedd", "12", "14", "E32", "Fazekas Levente Áron", "Programtervező informatikus BSc"));
		root.appendChild(createUser(doc, "o05", "elmelet", "Adatkezelés XML-ben", "Szerda", "8", "10", "A1/310", "Dr. Kovács László József", "Programtervező informatikus BSc"));
		root.appendChild(createUser(doc, "o06", "gyakorlat", "Adatkezelés XML-ben", "Szerda", "10", "12", "L103", "Dr. Bednarik László", "Programtervező informatikus BSc"));
		root.appendChild(createUser(doc, "o07", "elmelet", "Vállalati információs rendszerek fejlesztése", "Szerda", "14", "16", "L103", "Dr. Sasvári Péter László", "Programtervező informatikus BSc"));
		root.appendChild(createUser(doc, "o08", "gyakorlat", "Vállalati információs rendszerek fejlesztése", "Szerda", "18", "20", "L103", "Dr. Sasvári Péter László", "Programtervező informatikus BSc"));
		
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transf = transformerFactory.newTransformer();
		
		transf.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		transf.setOutputProperty(OutputKeys.INDENT, "yes");
		transf.setOutputProperty("{https://xml.apache.org/xslt}indent-amount", "2");
		
		
		DOMSource source = new DOMSource(doc);
		
		File myFile = new File("orarend1A86ANU.xml");
		
		StreamResult console = new StreamResult(System.out);
		StreamResult file = new StreamResult(myFile);
		
		transf.transform(source, console);
		transf.transform(source, file);
	}
	
	private static Node createUser(Document doc, String id, String tipus,
			String targy, String nap, String tol, String ig, String helyszin,
			String oktato, String szak) {
		
		Element user = doc.createElement("ora");
		
		user.setAttribute("id", id);
		user.appendChild(createUserElement(doc, "típus", tipus));
		user.appendChild(createUserElement(doc, "tárgy", targy));
		user.appendChild(createUserElement(doc, "nap", nap));
		user.appendChild(createUserElement(doc, "tól", tol));
		user.appendChild(createUserElement(doc, "ig", ig));
		user.appendChild(createUserElement(doc, "helyszín", helyszin));
		user.appendChild(createUserElement(doc, "oktató", oktato));
		user.appendChild(createUserElement(doc, "szak", szak));
		
		return user;
	}
	
	private static Node createUserElement(Document doc, String name, String value) {
		
		Element node = doc.createElement(name);
		node.appendChild(doc.createTextNode(value));
		
		return node;
	}

}
