package a86anu.domparse.hu;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class A86ANUDomQuery {
    
    public static void main(String[] args) {
        try {
            // ----------------------------------------------------
            // XML DOKUMENTUM BETÖLTÉSE DOM-MODELBE
            // ----------------------------------------------------
            Document doc = loadDocument("A86ANU_XML.xml");

            // 1. lekérdezés: drága autók (min. ár alapján)
            queryDragaAutok(doc, 16000);
            System.out.println("========================================");

            // 2. lekérdezés: egy adott ügyfél összes bérlése
            queryBerlesekUgyfelSzerint(doc, "U001");
            System.out.println("========================================");

            // 3. lekérdezés: autók egy megadott telephelyen
            queryAutokTelephelySzerint(doc, "T001");
            System.out.println("========================================");

            // 4. lekérdezés: azon ügyfelek, akiknek van bérletkártyájuk
            queryUgyfelekBerlettel(doc);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ----------------------------------------------------
    // XML dokumentum betöltése – közös segédmetódus
    // ----------------------------------------------------
    private static Document loadDocument(String fileName) throws Exception {
        // File objektum létrehozása a megadott fájlnévre
        File xmlFile = new File(fileName);

        // DOM parserhez szükséges gyár objektum
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        dbFactory.setNamespaceAware(true);   // névterek (xmlns) támogatása

        // DOM parser példány
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

        // XML feldolgozása, DOM fa felépítése
        Document doc = dBuilder.parse(xmlFile);
        doc.getDocumentElement().normalize();   // normalizálás (whitespace stb.)

        return doc;
    }

    // ----------------------------------------------------
    // 1. LEKÉRDEZÉS
    // Mely autók ára >= megadott minimum ár?
    // ----------------------------------------------------
    private static void queryDragaAutok(Document doc, int minAr) {

        System.out.println("1. lekérdezés: Autók, amelyek ára >= " + minAr + " Ft/nap");
        System.out.println();

        // Az összes <auto> elem lekérése
        NodeList autoList = doc.getElementsByTagName("auto");

        // Végigmegyünk az összes autón
        for (int i = 0; i < autoList.getLength(); i++) {
            Element auto = (Element) autoList.item(i);

            // Az <ar_naponta> elem szövegét int típusra alakítjuk
            int ar = Integer.parseInt(
                    auto.getElementsByTagName("ar_naponta").item(0).getTextContent()
            );

            // Csak azokat írjuk ki, amelyek ára eléri a megadott minimumot
            if (ar >= minAr) {
                String akod = auto.getAttribute("akod");
                String rendszam = auto.getElementsByTagName("rendszam").item(0).getTextContent();
                System.out.println("  " + akod + " (" + rendszam + ") - " + ar + " Ft/nap");
            }
        }
    }

    // ----------------------------------------------------
    // 2. LEKÉRDEZÉS
    // Egy adott ügyfél (ukod) összes bérlése
    // ----------------------------------------------------
    private static void queryBerlesekUgyfelSzerint(Document doc, String ukod) {

        System.out.println("2. lekérdezés: Bérlések az ügyfélhez: " + ukod);
        System.out.println();

        // Minden <berles> elem lekérése
        NodeList berlesList = doc.getElementsByTagName("berles");

        // Végigmegyünk az összes bérlésen
        for (int i = 0; i < berlesList.getLength(); i++) {
            Element berles = (Element) berlesList.item(i);

            // Csak azokat a bérléseket keressük, ahol az ukodref megegyezik
            if (ukod.equals(berles.getAttribute("ukodref"))) {
                String brid = berles.getAttribute("brid");
                // A <datum> elem teljes szövegét olvassuk ki (benne tol + ig)
                String datum = berles.getElementsByTagName("datum").item(0).getTextContent();
                String akodref = berles.getAttribute("akodref");
                String osszeg = berles.getElementsByTagName("osszeg").item(0).getTextContent();

                System.out.println("  Bérlés " + brid +
                        " | dátum: " + datum +
                        " | autó: " + akodref +
                        " | összeg: " + osszeg + " Ft");
            }
        }
    }

    // ----------------------------------------------------
    // 3. LEKÉRDEZÉS
    // Egy adott telephelyen lévő autók kilistázása
    // ----------------------------------------------------
    private static void queryAutokTelephelySzerint(Document doc, String tkod) {

        System.out.println("3. lekérdezés: Autók a(z) " + tkod + " telephelyen");
        System.out.println();

        // Összes <auto> elem lekérése
        NodeList autoList = doc.getElementsByTagName("auto");

        for (int i = 0; i < autoList.getLength(); i++) {
            Element auto = (Element) autoList.item(i);

            // Az <auto> elem tkodref attribútuma mutatja, melyik telephelyhez tartozik
            if (tkod.equals(auto.getAttribute("tkodref"))) {
                String akod = auto.getAttribute("akod");
                String rendszam = auto.getElementsByTagName("rendszam").item(0).getTextContent();
                String tipus = auto.getElementsByTagName("tipus").item(0).getTextContent();

                System.out.println("  " + akod + " (" + rendszam + "), típus: " + tipus);
            }
        }
    }

    // ----------------------------------------------------
    // 4. LEKÉRDEZÉS
    // Azon ügyfelek listázása, akikhez tartozik legalább egy bérletkártya
    // ----------------------------------------------------
    private static void queryUgyfelekBerlettel(Document doc) {

        System.out.println("4. lekérdezés: Ügyfelek, akiknek van bérletkártyájuk");
        System.out.println();

        // 1. lépés: összegyűjtjük a bérletkártyákhoz tartozó ukodref értékeket
        NodeList kartyaList = doc.getElementsByTagName("berletkartya");
        Set<String> ugyfelekKodjai = new HashSet<>();

        for (int i = 0; i < kartyaList.getLength(); i++) {
            Element kartya = (Element) kartyaList.item(i);

            // A bérletkártya ukodref attribútuma mutat az ügyfélre
            String ukodref = kartya.getAttribute("ukodref");
            ugyfelekKodjai.add(ukodref);   // halmazba tesszük (duplikációk kiszűrése)
        }

        // 2. lépés: végigmegyünk az összes ügyfélen, és csak azokat írjuk ki,
        // akik szerepelnek a fenti halmazban (tehát van bérletkártyájuk)
        NodeList ugyfelList = doc.getElementsByTagName("ugyfel");

        for (int i = 0; i < ugyfelList.getLength(); i++) {
            Element ugyfel = (Element) ugyfelList.item(i);
            String ukod = ugyfel.getAttribute("ukod");

            if (ugyfelekKodjai.contains(ukod)) {
                // Név kiolvasása
                Element nev = (Element) ugyfel.getElementsByTagName("nev").item(0);
                String vez = nev.getElementsByTagName("vezeteknev").item(0).getTextContent();
                String ker = nev.getElementsByTagName("keresztnev").item(0).getTextContent();

                System.out.println("  " + ukod + " - " + vez + " " + ker);
            }
        }
    }
}
