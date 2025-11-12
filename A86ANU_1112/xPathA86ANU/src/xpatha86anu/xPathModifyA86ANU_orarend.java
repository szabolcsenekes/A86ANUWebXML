package xpatha86anu;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.xpath.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

public class xPathModifyA86ANU_orarend {
    public static void main(String[] args) throws Exception {
        File src = new File("A86ANU_orarend.xml");

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(src);
        doc.getDocumentElement().normalize();

        XPath xp = XPathFactory.newInstance().newXPath();

        Element o02 = (Element) xp.evaluate("/A86ANU_orarend/ora[@id='o02']",
                                            doc, XPathConstants.NODE);
        if (o02 != null) {
            o02.getElementsByTagName("helyszin").item(0).setTextContent("E5/201");
            printOra("#1 módosítva", o02);
        }

        Element o04 = (Element) xp.evaluate("/A86ANU_orarend/ora[@id='o04']",
                                            doc, XPathConstants.NODE);
        if (o04 != null) {
            o04.getElementsByTagName("oktato").item(0).setTextContent("Dr. Tamás Judit");
            printOra("#2 módosítva", o04);
        }

        Element o08 = (Element) xp.evaluate("/A86ANU_orarend/ora[@id='o08']",
                                            doc, XPathConstants.NODE);
        if (o08 != null) {
            Element idopont = (Element) o08.getElementsByTagName("idopont").item(0);
            idopont.getElementsByTagName("tol").item(0).setTextContent("17");
            idopont.getElementsByTagName("ig").item(0).setTextContent("19");
            o08.setAttribute("tipus", "konzultacio");
            printOra("#3 módosítva", o08);
        }

        Transformer tf = TransformerFactory.newInstance().newTransformer();
        tf.setOutputProperty(OutputKeys.INDENT, "yes");
        tf.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
        tf.transform(new DOMSource(doc), new StreamResult(new File("orarendNeptunkod2.xml")));

        System.out.println("Módosított dokumentum mentve: orarendNeptunkod2.xml");
    }

    private static void printOra(String title, Element ora){
        System.out.println(title + " -> id=" + ora.getAttribute("id")
                + ", tipus=" + ora.getAttribute("tipus"));
        System.out.println("  targy: " + text(ora,"targy"));
        System.out.println("  nap: " + ((Element)ora.getElementsByTagName("idopont").item(0))
                .getElementsByTagName("nap").item(0).getTextContent());
        System.out.println("  tol-ig: " + text(ora,"tol") + "-" + text(ora,"ig"));
        System.out.println("  helyszin: " + text(ora,"helyszin"));
        System.out.println("  oktato: " + text(ora,"oktato"));
    }
    private static String text(Element e, String tag){
        return ((Element)e.getElementsByTagName(tag).item(0)).getTextContent();
    }
}
