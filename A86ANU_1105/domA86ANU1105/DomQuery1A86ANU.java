package domA86ANU1105;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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

public class DomQuery1A86ANU {

    private static void prettyPrintToConsoleAndFile(Element elem, String outFile) throws Exception {
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer t = tf.newTransformer();
        t.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        t.setOutputProperty(OutputKeys.INDENT, "yes");
        t.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

        System.out.println("\n--- Strukturált kiírás ---");
        t.transform(new DOMSource(elem), new StreamResult(System.out));
        t.transform(new DOMSource(elem), new StreamResult(new File(outFile)));
        System.out.println("\n(Mentve: " + outFile + ")");
    }

    public static void main(String[] args) {

        File inputFile = new File("A86ANU_orarend.xml");

        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setIgnoringComments(true);
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(inputFile);
            doc.getDocumentElement().normalize();

            NodeList oras = doc.getElementsByTagName("ora");
            List<String> kurzusok = new ArrayList<>();
            for (int i = 0; i < oras.getLength(); i++) {
                Element ora = (Element) oras.item(i);
                NodeList targyNodes = ora.getElementsByTagName("targy");
                if (targyNodes.getLength() > 0) {
                    kurzusok.add(targyNodes.item(0).getTextContent().trim());
                }
            }
            System.out.println("Kurzusnév: " + kurzusok);

            if (oras.getLength() > 0) {
                Element elsoOra = (Element) oras.item(0);
                prettyPrintToConsoleAndFile(elsoOra, "orarendQueryFirstNeptunkod.xml");
            } else {
                System.out.println("Nincs <ora> elem a dokumentumban.");
            }

            List<String> oktatok = new ArrayList<>();
            for (int i = 0; i < oras.getLength(); i++) {
                Element ora = (Element) oras.item(i);
                NodeList oktatoNodes = ora.getElementsByTagName("oktato");
                if (oktatoNodes.getLength() > 0) {
                    oktatok.add(oktatoNodes.item(0).getTextContent().trim());
                }
            }
            System.out.println("Oktatók: " + oktatok);

            List<String> szerdaGyak = new ArrayList<>();
            for (int i = 0; i < oras.getLength(); i++) {
                Element ora = (Element) oras.item(i);
                String tipus = ora.getAttribute("tipus").trim();
                if (!"gyakorlat".equalsIgnoreCase(tipus)) continue;

                Element idopont = (Element) ora.getElementsByTagName("idopont").item(0);
                String nap = idopont.getElementsByTagName("nap").item(0).getTextContent().trim();
                if (!"Szerda".equalsIgnoreCase(nap)) continue;

                String id = ora.getAttribute("id");
                String targy = ora.getElementsByTagName("targy").item(0).getTextContent().trim();
                String tol = idopont.getElementsByTagName("tol").item(0).getTextContent().trim();
                String ig = idopont.getElementsByTagName("ig").item(0).getTextContent().trim();
                String hely = ora.getElementsByTagName("helyszin").item(0).getTextContent().trim();
                String okt = ora.getElementsByTagName("oktato").item(0).getTextContent().trim();

                szerdaGyak.add(id + " | " + targy + " | " + tol + "-" + ig + " | " + hely + " | " + okt);
            }

            System.out.println("Összetett lekérdezés – SZERDAI gyakorlatok:");
            if (szerdaGyak.isEmpty()) {
                System.out.println("  (nincs találat)");
            } else {
                for (String s : szerdaGyak) System.out.println("  " + s);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
