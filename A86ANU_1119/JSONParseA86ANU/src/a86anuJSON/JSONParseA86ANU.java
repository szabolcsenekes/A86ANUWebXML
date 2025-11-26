package a86anuJSON;

import java.io.FileReader;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class JSONParseA86ANU {
	
	 public static void main(String[] args) {
	        JSONParser parser = new JSONParser();

	        try {
	            JSONObject root = (JSONObject) parser.parse(
	                    new FileReader("orarendA86ANU.json")
	            );

	            String key = (String) root.keySet().iterator().next();
	            JSONObject orarendObj = (JSONObject) root.get(key);

	            JSONArray orak = (JSONArray) orarendObj.get("ora");

	            for (Object o : orak) {
	                JSONObject ora = (JSONObject) o;

	                System.out.println("--------- Óra ---------");

	                System.out.println("targy: " + ora.get("targy"));
	                System.out.println("helyszin: " + ora.get("helyszin"));
	                System.out.println("oktato: " + ora.get("oktato"));
	                System.out.println("szak: " + ora.get("szak"));

	                JSONObject ido = (JSONObject) ora.get("idopont");
	                System.out.println("idopont.nap: " + ido.get("nap"));
	                System.out.println("idopont.tol: " + ido.get("tol"));
	                System.out.println("idopont.ig: " + ido.get("ig"));

	                System.out.println();
	            }

	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }

}
