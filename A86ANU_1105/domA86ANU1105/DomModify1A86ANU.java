package domA86ANU1105;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class DomModify1A86ANU {

    public static void main(String[] argv) {
        try {
            File inputFile = new File("A86ANU_orarend.xml");

            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();

            Element firstOra = (Element) doc.getElementsByTagName("ora").item(0);
            firstOra.setAttribute("id", "o01");

            Element oraado = doc.createElement("óraadó");
            oraado.setTextContent("Dr. Bednarik László");
            firstOra.appendChild(oraado);

            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer t = tf.newTransformer();
            t.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            t.setOutputProperty(OutputKeys.INDENT, "yes");
            t.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

            System.out.println("---1. módosítás---");
            t.transform(new DOMSource(doc), new StreamResult(System.out));
            t.transform(new DOMSource(doc), new StreamResult(new File("orarendModify1A86ANU.xml")));

            NodeList orak = doc.getElementsByTagName("ora");
            for (int i = 0; i < orak.getLength(); i++) {
                Element eOra = (Element) orak.item(i);
                String tipus = eOra.getAttribute("tipus").trim();
                if ("gyakorlat".equalsIgnoreCase(tipus)) {
                    eOra.setAttribute("tipus", "előadás");
                }
            }

            System.out.println("\n---2. módosítás---");
            t.transform(new DOMSource(doc), new StreamResult(System.out));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
