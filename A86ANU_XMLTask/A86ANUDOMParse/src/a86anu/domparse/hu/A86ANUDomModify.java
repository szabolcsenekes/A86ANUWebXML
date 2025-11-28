package a86anu.domparse.hu;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class A86ANUDomModify {
	
	public static void main(String[] args) {

        try {
            // 0. XML dokumentum betöltése DOM-ba
            Document doc = loadDocument("A86ANU_XML.xml");

            // 1. módosítás: új ügyfél felvétele
            addUjUgyfel(doc);

            // 2. módosítás: új autó felvétele
            addUjAuto(doc);

            // 3. módosítás: meglévő autó árának módosítása
            modifyAutoAr(doc, "A001", 17000);

            // 4. módosítás: egy bérlés törlése
            deleteBerles(doc, "BR002");

            System.out.println();
            System.out.println("Módosítások elkészültek");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ----------------------------------------------------
    //              XML DOKUMENTUM BETÖLTÉS
    // ----------------------------------------------------
    private static Document loadDocument(String fileName) throws Exception {

        File xmlFile = new File(fileName);

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        dbFactory.setNamespaceAware(true);

        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(xmlFile);

        // A DOM normalizálása – szövegcsomópontok rendezése
        doc.getDocumentElement().normalize();

        System.out.println("Betöltött dokumentum gyökéreleme: " +
                doc.getDocumentElement().getNodeName());
        System.out.println();

        return doc;
    }

    // ----------------------------------------------------
    // 1. MÓDOSÍTÁS – ÚJ ÜGYFÉL HOZZÁADÁSA
    // ----------------------------------------------------
    private static void addUjUgyfel(Document doc) {

        System.out.println("1. módosítás: új ügyfél hozzáadása");

        // Gyökérelem: <autokolcsonzo>
        Element gyoker = doc.getDocumentElement();

        // <ugyfelek> elem keresése
        Element ugyfelek = (Element) gyoker.getElementsByTagName("ugyfelek").item(0);

        // Új <ugyfel> elem létrehozása
        Element uj = doc.createElement("ugyfel");
        uj.setAttribute("ukod", "U005");  // új ügyfél azonosító

        // <nev> összetett elem
        Element nev = doc.createElement("nev");

        Element vez = doc.createElement("vezeteknev");
        vez.setTextContent("Szabó");

        Element ker = doc.createElement("keresztnev");
        ker.setTextContent("Péter");

        nev.appendChild(vez);
        nev.appendChild(ker);

        // Egyéb adatok
        Element szemelyi = doc.createElement("szemelyi_szam");
        szemelyi.setTextContent("555555EF");

        Element tel = doc.createElement("telefon");
        tel.setTextContent("06301234567");

        Element emailek = doc.createElement("emailek");
        Element email = doc.createElement("email");
        email.setTextContent("peter.szabo@pelda.hu");
        emailek.appendChild(email);

        // Az <ugyfel> felépítése
        uj.appendChild(nev);
        uj.appendChild(szemelyi);
        uj.appendChild(tel);
        uj.appendChild(emailek);

        // Hozzáadás az <ugyfelek> listához
        ugyfelek.appendChild(uj);

        System.out.println("   Új ügyfél felvéve: ukod=U005, név=Szabó Péter");
        System.out.println();
    }

    // ----------------------------------------------------
    // 2. MÓDOSÍTÁS – ÚJ AUTÓ HOZZÁADÁSA
    // ----------------------------------------------------
    private static void addUjAuto(Document doc) {

        System.out.println("2. módosítás: új autó hozzáadása");

        Element gyoker = doc.getDocumentElement();

        // <autok> elem keresése
        Element autok = (Element) gyoker.getElementsByTagName("autok").item(0);

        // Új <auto> elem létrehozása
        Element auto = doc.createElement("auto");
        auto.setAttribute("akod", "A006");
        auto.setAttribute("mkodref", "M003");  // létező márka
        auto.setAttribute("tkodref", "T005");  // létező telephely

        Element rendszam = doc.createElement("rendszam");
        rendszam.setTextContent("JKL-456");

        Element tipus = doc.createElement("tipus");
        tipus.setTextContent("szemelyauto");

        Element evjarat = doc.createElement("evjarat");
        evjarat.setTextContent("2021");

        Element ar = doc.createElement("ar_naponta");
        ar.setTextContent("16500");

        auto.appendChild(rendszam);
        auto.appendChild(tipus);
        auto.appendChild(evjarat);
        auto.appendChild(ar);

        autok.appendChild(auto);

        System.out.println("   Új autó felvéve: akod=A006, rendszám=JKL-456, ár=16500 Ft/nap");
        System.out.println();
    }

    // ----------------------------------------------------
    // 3. MÓDOSÍTÁS – AUTÓ ÁRÁNAK MÓDOSÍTÁSA
    // ----------------------------------------------------
    private static void modifyAutoAr(Document doc, String akod, int ujAr) {

        System.out.println("3. módosítás: autó napi árának frissítése (akod=" + akod + ")");

        NodeList autoList = doc.getElementsByTagName("auto");

        for (int i = 0; i < autoList.getLength(); i++) {

            Element auto = (Element) autoList.item(i);

            if (akod.equals(auto.getAttribute("akod"))) {

                Element arElem = (Element) auto.getElementsByTagName("ar_naponta").item(0);

                String regiErtek = arElem.getTextContent();
                arElem.setTextContent(Integer.toString(ujAr));

                String rendszam = auto.getElementsByTagName("rendszam").item(0).getTextContent();

                System.out.println("   Autó: " + rendszam +
                        " | régi ár: " + regiErtek +
                        " | új ár: " + ujAr);
                System.out.println();
                break;
            }
        }
    }

    // ----------------------------------------------------
    // 4. MÓDOSÍTÁS – BÉRLÉS TÖRLÉSE
    // ----------------------------------------------------
    private static void deleteBerles(Document doc, String brid) {

        System.out.println("4. módosítás: bérlés törlése (brid=" + brid + ")");

        NodeList berlesList = doc.getElementsByTagName("berles");
        boolean torolve = false;

        for (int i = 0; i < berlesList.getLength(); i++) {

            Element berles = (Element) berlesList.item(i);

            if (brid.equals(berles.getAttribute("brid"))) {

                // Kiírjuk, hogy mit törlünk
                String ukodref = berles.getAttribute("ukodref");
                String akodref = berles.getAttribute("akodref");
                String datum = berles.getElementsByTagName("datum").item(0).getTextContent();

                System.out.println("   Törölt bérlés: brid=" + brid +
                        ", ügyfél=" + ukodref +
                        ", autó=" + akodref +
                        ", dátum=" + datum);

                // A szülő elem (pl. <berlesek>) eltávolítja a gyerekét
                berles.getParentNode().removeChild(berles);
                torolve = true;
                break;
            }
        }

        if (!torolve) {
            System.out.println("   Figyelem: ilyen azonosítójú bérlés nem található.");
        }

        System.out.println();
    }

}
