package xpatha86anu;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class xPathQueryA86ANU_orarend {
	 public static void main(String[] args) throws Exception {
	        File src = new File("A86ANU_orarend.xml");

	        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	        DocumentBuilder db = dbf.newDocumentBuilder();
	        Document doc = db.parse(src);
	        doc.getDocumentElement().normalize();

	        XPath xp = XPathFactory.newInstance().newXPath();

	        Double elmelet = (Double) xp.evaluate("count(/A86ANU_orarend/ora[@tipus='elmelet'])",
	                                              doc, XPathConstants.NUMBER);
	        Double gyakorlat = (Double) xp.evaluate("count(/A86ANU_orarend/ora[@tipus='gyakorlat'])",
	                                                doc, XPathConstants.NUMBER);

	        System.out.printf("#1: Előadások: %.0f, Gyakorlatok: %.0f%n", elmelet, gyakorlat);

	        NodeList szerda = (NodeList) xp.evaluate(
	                "/A86ANU_orarend/ora[idopont/nap='Szerda']",
	                doc, XPathConstants.NODESET);
	        System.out.println("#2: Szerda órái:");
	        for (int i = 0; i < szerda.getLength(); i++) {
	            Element e = (Element) szerda.item(i);
	            String id = e.getAttribute("id");
	            String targy = e.getElementsByTagName("targy").item(0).getTextContent();
	            String tol = e.getElementsByTagName("tol").item(0).getTextContent();
	            String ig  = e.getElementsByTagName("ig").item(0).getTextContent();
	            String hely= e.getElementsByTagName("helyszin").item(0).getTextContent();
	            System.out.printf("  %s | %s | %s-%s | %s%n", id, targy, tol, ig, hely);
	        }

	        NodeList sasvari = (NodeList) xp.evaluate(
	                "/A86ANU_orarend/ora[oktato='Dr. Sasvári Péter László']",
	                doc, XPathConstants.NODESET);
	        System.out.println("#3: Dr. Sasvári órái:");
	        for (int i = 0; i < sasvari.getLength(); i++) {
	            Element e = (Element) sasvari.item(i);
	            System.out.printf("  %s | %s | %s%n",
	                    e.getAttribute("id"),
	                    e.getElementsByTagName("helyszin").item(0).getTextContent(),
	                    ((Element)e.getElementsByTagName("idopont").item(0))
	                        .getElementsByTagName("nap").item(0).getTextContent());
	        }

	        NodeList webtech = (NodeList) xp.evaluate(
	                "/A86ANU_orarend/ora[targy='Web technológiák 1']/idopont",
	                doc, XPathConstants.NODESET);
	        System.out.println("#4: Web technológiák 1 időpontok:");
	        for (int i = 0; i < webtech.getLength(); i++) {
	            Element idopont = (Element) webtech.item(i);
	            String nap = idopont.getElementsByTagName("nap").item(0).getTextContent();
	            String tol = idopont.getElementsByTagName("tol").item(0).getTextContent();
	            String ig  = idopont.getElementsByTagName("ig").item(0).getTextContent();
	            System.out.printf("  %s %s-%s%n", nap, tol, ig);
	        }

	        Document report = db.newDocument();
	        Element root = report.createElement("report");
	        report.appendChild(root);

	        Element q1 = report.createElement("query");
	        q1.setAttribute("id", "1");
	        q1.appendChild(elem(report, "elmelet", String.valueOf(elmelet.intValue())));
	        q1.appendChild(elem(report, "gyakorlat", String.valueOf(gyakorlat.intValue())));
	        root.appendChild(q1);

	        Element q2 = report.createElement("query");
	        q2.setAttribute("id", "2");
	        for (int i = 0; i < szerda.getLength(); i++) {
	            Element e = (Element) szerda.item(i);
	            Element item = report.createElement("ora");
	            item.setAttribute("id", e.getAttribute("id"));
	            item.appendChild(elem(report, "targy", text(e,"targy")));
	            item.appendChild(elem(report, "tol",  text(e,"tol")));
	            item.appendChild(elem(report, "ig",   text(e,"ig")));
	            item.appendChild(elem(report, "helyszin", text(e,"helyszin")));
	            q2.appendChild(item);
	        }
	        root.appendChild(q2);

	        Element q3 = report.createElement("query");
	        q3.setAttribute("id", "3");
	        for (int i = 0; i < sasvari.getLength(); i++) {
	            Element e = (Element) sasvari.item(i);
	            Element item = report.createElement("ora");
	            item.setAttribute("id", e.getAttribute("id"));
	            item.appendChild(elem(report, "helyszin", text(e,"helyszin")));
	            item.appendChild(elem(report, "nap",
	                    ((Element)e.getElementsByTagName("idopont").item(0))
	                    .getElementsByTagName("nap").item(0).getTextContent()));
	            q3.appendChild(item);
	        }
	        root.appendChild(q3);

	        Transformer tf = TransformerFactory.newInstance().newTransformer();
	        tf.setOutputProperty(OutputKeys.INDENT, "yes");
	        tf.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
	        tf.transform(new DOMSource(report), new StreamResult(new File("orarendNeptunkod1.xml")));

	        System.out.println("Riport mentve: orarendNeptunkod1.xml");
	    }

	    private static Element elem(Document d, String name, String val){
	        Element e = d.createElement(name);
	        e.setTextContent(val);
	        return e;
	    }
	    private static String text(Element e, String tag){
	        return e.getElementsByTagName(tag).item(0).getTextContent();
	    }
	}