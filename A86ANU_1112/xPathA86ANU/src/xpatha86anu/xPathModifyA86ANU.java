package xpatha86anu;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class xPathModifyA86ANU {
	
	public static void main(String[] args) {
		
		try {
			DocumentBuilderFactory documentBF = DocumentBuilderFactory.newInstance();
			
			DocumentBuilder documentB = documentBF.newDocumentBuilder();
			
			Document document = documentB.parse("studentA86ANU.xml");
			
			document.getDocumentElement().normalize();
			
			XPath xPath = XPathFactory.newInstance().newXPath();
			
			String expression = "/class/student[@id='1']";
			Node studentNode =  (Node) xPath.evaluate(expression, document, XPathConstants.NODE);
			
			if (studentNode != null) {
				Element studentElement = (Element) studentNode;
				Node keresztnevNode = studentElement.getElementsByTagName("keresztnev").item(0);
				if (keresztnevNode != null) {
					keresztnevNode.setTextContent("Gergely Tamás");
				}
				
				System.out.println("Módosított pédány: ");
				NodeList childNodes = studentElement.getChildNodes();
				for(int i = 0; i < childNodes.getLength(); i++) {
					Node n = childNodes.item(i);
					if(n.getNodeType() == Node.ELEMENT_NODE) {
						System.out.println(n.getNodeName() + ": " + n.getTextContent());
					}
				}
			}
			
			
		} catch (IOException | ParserConfigurationException | SAXException | XPathExpressionException e) {
			e.printStackTrace();
		}
	}

}
