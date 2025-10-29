package domA86ANU1029;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

public class DOMRead1 {
	
	public static void main(String[] argv) throws SAXException,
	IOException, ParserConfigurationException {
		
		File file = new File("A86ANU_orarend.xml");
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		
		DocumentBuilder dbuilder = factory.newDocumentBuilder();

		Document neptunkod = dbuilder.parse(file);
		
		neptunkod.getDocumentElement().normalize();
		
		System.out.println("Gyökér elem: " + neptunkod.getDocumentElement().getNodeName());
		
		NodeList nList = neptunkod.getElementsByTagName("ora");
		
		for (int i = 0; i < nList.getLength(); i++) {
			Node nNode = nList.item(i);
			
			System.out.println("\nAktuális elem: " + nNode.getNodeName());
			
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element elem = (Element) nNode;
				
				String hid = elem.getAttribute("id");
				
				Node node1 = elem.getElementsByTagName("targy").item(0);
				String tname = node1.getTextContent();
				
				Node node2 = elem.getElementsByTagName("nap").item(0);
				String nname = node2.getTextContent();
				
				Node node3 = elem.getElementsByTagName("tol").item(0);
				String tolname = node3.getTextContent();
				
				Node node4 = elem.getElementsByTagName("ig").item(0);
				String igname = node4.getTextContent();
				
				Node node5 = elem.getElementsByTagName("helyszin").item(0);
				String hname = node5.getTextContent();
				
				Node node6 = elem.getElementsByTagName("oktato").item(0);
				String oname = node6.getTextContent();
				
				Node node7 = elem.getElementsByTagName("szak").item(0);
				String szname = node7.getTextContent();
				
				System.out.println("Tárgy id: " + hid);
				System.out.println("Tárgy neve: " + tname);
				System.out.println("Időpont napja: " + nname);
				System.out.println("Időpont tól: " + tolname);
				System.out.println("Időpont ig: " + igname);
				System.out.println("Helyszín: " + hname);
				System.out.println("Oktató: " + oname);
				System.out.println("Szak: " + szname);

			}
		}
	}

}
