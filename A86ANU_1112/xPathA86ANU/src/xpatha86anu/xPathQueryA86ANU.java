package xpatha86anu;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class xPathQueryA86ANU {
	public static void main(String[] args) {
		try {
			
			DocumentBuilderFactory documentBF = DocumentBuilderFactory.newInstance();
			
			DocumentBuilder documentB = documentBF.newDocumentBuilder();
			
			Document document = documentB.parse("studentA86ANU.xml");
			
			document.getDocumentElement().normalize();
			
			XPath xPath = XPathFactory.newInstance().newXPath();
			
			//Q1
			XPathExpression expr = xPath.compile("//class/student");
			
			//Q2
			//XPathExpression expr = xPath.compile("//student[@id='2']");
			
			//Q3
			//XPathExpression expr = xPath.compile("//student");
			
			//Q4
			//XPathExpression expr = xPath.compile("/class/student[2]");
			
			//Q5
			//XPathExpression expr = xPath.compile("/class/student[last()]");
			
			//Q6
			//XPathExpression expr = xPath.compile("/class/student[last()-1]");
			
			//Q7
			//XPathExpression expr = xPath.compile("/class/student[position() <= 2]");
			
			//Q8
			//XPathExpression expr = xPath.compile("/class/*");
			
			//Q9
			//XPathExpression expr = xPath.compile("//student[@*]");
			
			//Q10
			//XPathExpression expr = xPath.compile("//*");
			
			//Q11
			//XPathExpression expr = xPath.compile("/class/student[kor > 20]");
			
			//Q12
			//XPathExpression expr = xPath.compile("//student/keresztnev | //student/vezeteknev");
	
			NodeList studentNodes = (NodeList) expr.evaluate(document, XPathConstants.NODESET);
			
			for (int i = 0; i < studentNodes.getLength(); i++) {
				Element student = (Element) studentNodes.item(i);
				
				String id = student.getAttribute("id");
				String keresztnev = student.getElementsByTagName("keresztnev").item(0).getTextContent().trim();
				String vezeteknev = student.getElementsByTagName("vezeteknev").item(0).getTextContent().trim();
				String becenev = student.getElementsByTagName("becenev").item(0).getTextContent().trim();
				String kor = student.getElementsByTagName("kor").item(0).getTextContent().trim();
				
				System.out.println("Hallgató ID: " + id);
				System.out.println("Keresztnév: " + keresztnev);
				System.out.println("Vezetéknév: " + vezeteknev);
				System.out.println("Becenév: " + becenev);
				System.out.println("Kor: " + kor);
			}
			
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
	} 

}
