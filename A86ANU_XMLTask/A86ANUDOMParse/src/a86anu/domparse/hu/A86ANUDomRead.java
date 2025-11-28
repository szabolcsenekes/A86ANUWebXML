package a86anu.domparse.hu;

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

public class A86ANUDomRead {

    public static void main(String[] args) {

        try {
            // ----------------------------------------------------
            // XML FÁJL BETÖLTÉSE DOM-MODELBE
            // ----------------------------------------------------
            // A File objektum csak hivatkozik az XML fájlra.
            File xmlFile = new File("A86ANU_XML.xml");

            // DocumentBuilderFactory létrehozása – ez készíti elő a DOM parsert.
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            dbFactory.setNamespaceAware(true);  // névterek felismerésének engedélyezése

            // A tényleges DOM parser példány
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

            // A dokumentum beolvasása és DOM (fa-struktúra) létrehozása
            Document doc = dBuilder.parse(xmlFile);

            // Normalizálás: whitespace-ek, szövegtöredékek egyesítése
            doc.getDocumentElement().normalize();

            // Gyökérelem kiírása (autokolcsonzo)
            System.out.println("Gyökérelem: " + doc.getDocumentElement().getNodeName());
            System.out.println();


            // ----------------------------------------------------
            // A TELJES DOKUMENTUM FELDOLGOZÁSA BLOKKOKBAN
            // ----------------------------------------------------
            printUgyfelek(doc);
            printBerletkartyak(doc);
            printMarkak(doc);
            printTelephelyek(doc);
            printAutok(doc);
            printBerlesek(doc);


            // ----------------------------------------------------
            // A DOM FÁJLBA ÍRÁSA
            // ----------------------------------------------------
            // A DOM jelenlegi állapotát kiírjuk egy új XML fájlba.
            saveDocument(doc, "A86ANU_XML_mentett.xml");
            System.out.println("Dokumentum elmentve: A86ANU_XML_mentett.xml");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ====================================================
    // ÜGYFELEK BLOKK – <ugyfel> elemek feldolgozása
    // ====================================================
    private static void printUgyfelek(Document doc) {
        System.out.println("===== ÜGYFELEK =====");

        // Összes <ugyfel> elem lekérése NodeList formában
        NodeList ugyfelList = doc.getElementsByTagName("ugyfel");
        System.out.println("Ügyfelek száma: " + ugyfelList.getLength());
        System.out.println("------------------------------");

        // Bejárjuk az összes ügyfelet
        for (int i = 0; i < ugyfelList.getLength(); i++) {

            // Az aktuális <ugyfel> elemre hivatkozás
            Element ugyfel = (Element) ugyfelList.item(i);

            // Attribútum lekérése (ukod)
            String ukod = ugyfel.getAttribute("ukod");

            // Név beágyazott elemének feldolgozása
            Element nevElem = (Element) ugyfel.getElementsByTagName("nev").item(0);
            String vezetek = nevElem.getElementsByTagName("vezeteknev").item(0).getTextContent();
            String kereszt  = nevElem.getElementsByTagName("keresztnev").item(0).getTextContent();

            // Személyi szám, telefon
            String szemelyi = ugyfel.getElementsByTagName("szemelyi_szam").item(0).getTextContent();
            String telefon  = ugyfel.getElementsByTagName("telefon").item(0).getTextContent();

            // Konzolos kiírás blokk formában
            System.out.println("Ügyfél [" + ukod + "]");
            System.out.println("  Név       : " + vezetek + " " + kereszt);
            System.out.println("  Szem. sz. : " + szemelyi);
            System.out.println("  Telefon   : " + telefon);

            // Email lista feldolgozása (több <email> elem is lehet)
            NodeList emailLista = ugyfel.getElementsByTagName("email");
            for (int j = 0; j < emailLista.getLength(); j++) {
                String email = emailLista.item(j).getTextContent();
                System.out.println("  Email     : " + email);
            }

            System.out.println();
        }

        System.out.println();
    }


    // ====================================================
    // BÉRLETKÁRTYÁK BLOKK
    // ====================================================
    private static void printBerletkartyak(Document doc) {
        System.out.println("===== BÉRLETKÁRTYÁK =====");

        // Összes <berletkartya> elem összegyűjtése
        NodeList kartyaList = doc.getElementsByTagName("berletkartya");
        System.out.println("Kártyák száma: " + kartyaList.getLength());
        System.out.println("------------------------------");

        for (int i = 0; i < kartyaList.getLength(); i++) {

            Element kartya = (Element) kartyaList.item(i);

            // Azonosítók attribútumokból
            String bkod    = kartya.getAttribute("bkod");
            String ukodref = kartya.getAttribute("ukodref");

            // Gyerek elemek kiolvasása
            String kartyaszam     = kartya.getElementsByTagName("kartyaszam").item(0).getTextContent();
            String kiadas         = kartya.getElementsByTagName("kiadas_datuma").item(0).getTextContent();
            String ervenyesEddig  = kartya.getElementsByTagName("ervenyes_eddig").item(0).getTextContent();
            String allapot        = kartya.getElementsByTagName("allapot").item(0).getTextContent();

            System.out.println("Bérletkártya [" + bkod + "]");
            System.out.println("  Ügyfél ref : " + ukodref);
            System.out.println("  Kártyaszám : " + kartyaszam);
            System.out.println("  Kiadás     : " + kiadas);
            System.out.println("  Érvényes   : " + ervenyesEddig);
            System.out.println("  Állapot    : " + allapot);
            System.out.println();
        }

        System.out.println();
    }


    // ====================================================
    // MÁRKÁK BLOKK
    // ====================================================
    private static void printMarkak(Document doc) {
        System.out.println("===== MÁRKÁK =====");

        NodeList markaList = doc.getElementsByTagName("marka");
        System.out.println("Márkák száma: " + markaList.getLength());
        System.out.println("------------------------------");

        for (int i = 0; i < markaList.getLength(); i++) {

            Element marka = (Element) markaList.item(i);

            // Attribútum
            String mkod = marka.getAttribute("mkod");

            // Al-elemek
            String nev    = marka.getElementsByTagName("nev").item(0).getTextContent();
            String modell = marka.getElementsByTagName("modell").item(0).getTextContent();
            String kat    = marka.getElementsByTagName("kategoria").item(0).getTextContent();
            String garEv  = marka.getElementsByTagName("garancia_ev").item(0).getTextContent();
            String orszag = marka.getElementsByTagName("szarmazasi_orszag").item(0).getTextContent();

            System.out.println("Márka [" + mkod + "]");
            System.out.println("  Név        : " + nev);
            System.out.println("  Modell     : " + modell);
            System.out.println("  Kategória  : " + kat);
            System.out.println("  Garancia   : " + garEv + " év");
            System.out.println("  Ország     : " + orszag);
            System.out.println();
        }

        System.out.println();
    }


    // ====================================================
    // TELEPHELYEK BLOKK
    // ====================================================
    private static void printTelephelyek(Document doc) {
        System.out.println("===== TELEPHELYEK =====");

        NodeList telephelyList = doc.getElementsByTagName("telephely");
        System.out.println("Telephelyek száma: " + telephelyList.getLength());
        System.out.println("------------------------------");

        for (int i = 0; i < telephelyList.getLength(); i++) {

            Element telephely = (Element) telephelyList.item(i);

            String tkod    = telephely.getAttribute("tkod");
            String nev     = telephely.getElementsByTagName("nev").item(0).getTextContent();
            String telefon = telephely.getElementsByTagName("telefon").item(0).getTextContent();

            // Cím beágyazott struktúrában
            Element cim = (Element) telephely.getElementsByTagName("cim").item(0);
            String varos   = cim.getElementsByTagName("varos").item(0).getTextContent();
            String utca    = cim.getElementsByTagName("utca").item(0).getTextContent();
            String hazszam = cim.getElementsByTagName("hazszam").item(0).getTextContent();

            System.out.println("Telephely [" + tkod + "]");
            System.out.println("  Név     : " + nev);
            System.out.println("  Telefon : " + telefon);
            System.out.println("  Cím     : " + varos + ", " + utca + " " + hazszam);
            System.out.println();
        }

        System.out.println();
    }


    // ====================================================
    // AUTÓK BLOKK
    // ====================================================
    private static void printAutok(Document doc) {
        System.out.println("===== AUTÓK =====");

        NodeList autoList = doc.getElementsByTagName("auto");
        System.out.println("Autók száma: " + autoList.getLength());
        System.out.println("------------------------------");

        for (int i = 0; i < autoList.getLength(); i++) {

            Element auto = (Element) autoList.item(i);

            // Attribútumok
            String akod   = auto.getAttribute("akod");
            String mkodref= auto.getAttribute("mkodref");
            String tkodref= auto.getAttribute("tkodref");

            // Gyerek elemek
            String rendszam  = auto.getElementsByTagName("rendszam").item(0).getTextContent();
            String tipus     = auto.getElementsByTagName("tipus").item(0).getTextContent();
            String evjarat   = auto.getElementsByTagName("evjarat").item(0).getTextContent();
            String arNaponta = auto.getElementsByTagName("ar_naponta").item(0).getTextContent();

            System.out.println("Autó [" + akod + "]");
            System.out.println("  Rendszám : " + rendszam);
            System.out.println("  Típus    : " + tipus);
            System.out.println("  Évjárat  : " + evjarat);
            System.out.println("  Ár/nap   : " + arNaponta + " Ft");
            System.out.println("  Márka ref: " + mkodref);
            System.out.println("  Telephely ref: " + tkodref);
            System.out.println();
        }

        System.out.println();
    }


    // ====================================================
    // BÉRLÉSEK BLOKK
    // ====================================================
    private static void printBerlesek(Document doc) {
        System.out.println("===== BÉRLÉSEK =====");

        NodeList berlesList = doc.getElementsByTagName("berles");
        System.out.println("Bérlések száma: " + berlesList.getLength());
        System.out.println("------------------------------");

        for (int i = 0; i < berlesList.getLength(); i++) {

            Element berles = (Element) berlesList.item(i);

            // Attribútumok
            String brid    = berles.getAttribute("brid");
            String ukodref = berles.getAttribute("ukodref");
            String akodref = berles.getAttribute("akodref");

            // Dátum összetett elem (tol, ig)
            Element datumElem = (Element) berles.getElementsByTagName("datum").item(0);
            String tol = datumElem.getElementsByTagName("tol").item(0).getTextContent();
            String ig  = datumElem.getElementsByTagName("ig").item(0).getTextContent();

            String osszeg = berles.getElementsByTagName("osszeg").item(0).getTextContent();
            String bizt   = berles.getElementsByTagName("biztositas_tipus").item(0).getTextContent();

            System.out.println("Bérlés [" + brid + "]");
            System.out.println("  Ügyfél ref : " + ukodref);
            System.out.println("  Autó ref   : " + akodref);
            System.out.println("  Dátum      : " + tol + " → " + ig);
            System.out.println("  Összeg     : " + osszeg + " Ft");
            System.out.println("  Biztosítás : " + bizt);
            System.out.println();
        }

        System.out.println();
    }


    // ====================================================
    // XML DOKUMENTUM MENTÉSE
    // ====================================================
    private static void saveDocument(Document doc, String fileName) throws Exception {

        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer t = tf.newTransformer();

        // Kimeneti formázás beállítása
        t.setOutputProperty(OutputKeys.INDENT, "yes"); // behúzás
        t.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
        t.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File(fileName));

        // A DOM → XML fájl generálása
        t.transform(source, result);
    }
}
