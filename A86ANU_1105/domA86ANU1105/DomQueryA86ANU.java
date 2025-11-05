package domA86ANU1105;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DomQueryA86ANU {

    public static void main(String[] args) {

        File inputFile = new File("A86ANUhallgato.xml");

        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setIgnoringComments(true);
            dbf.setNamespaceAware(false);

            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(inputFile);
            doc.getDocumentElement().normalize();

            System.out.println("Gyökér elem: " + doc.getDocumentElement().getNodeName());
            System.out.println("----------------------------");

            NodeList hallgatok = doc.getElementsByTagName("hallgato");
            for (int i = 0; i < hallgatok.getLength(); i++) {
                Node n = hallgatok.item(i);
                if (n.getNodeType() != Node.ELEMENT_NODE) continue;

                Element hallgato = (Element) n;

                System.out.println("Aktuális elem: " + hallgato.getTagName());

                NodeList vezeteknevNodes = hallgato.getElementsByTagName("vezeteknev");
                String vezeteknev = (vezeteknevNodes.getLength() > 0)
                        ? vezeteknevNodes.item(0).getTextContent().trim()
                        : "";

                System.out.println("vezeteknev: " + vezeteknev);
                System.out.println();
            }

        } catch (Exception e) {
            System.err.println("Hiba az XML feldolgozása közben: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
