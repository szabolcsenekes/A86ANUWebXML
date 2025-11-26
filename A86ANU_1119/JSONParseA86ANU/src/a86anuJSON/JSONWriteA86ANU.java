package a86anuJSON;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JSONWriteA86ANU {
	
	 public static void main(String[] args) {
	        JSONParser parser = new JSONParser();

	        try (FileReader reader = new FileReader("orarendA86ANU.json")) {

	            JSONObject root = (JSONObject) parser.parse(reader);

	            String topKey = (String) root.keySet().iterator().next();
	            JSONObject orarendObj = (JSONObject) root.get(topKey);

	            JSONArray orak = (JSONArray) orarendObj.get("ora");

	            int i = 1;
	            for (Object o : orak) {
	                JSONObject ora = (JSONObject) o;
	                System.out.println("===== " + i + ". óra =====");
	                System.out.println("targy: " + ora.get("targy"));
	                System.out.println("helyszin: " + ora.get("helyszin"));
	                System.out.println("oktato: " + ora.get("oktato"));
	                System.out.println("szak: " + ora.get("szak"));

	                JSONObject idopont = (JSONObject) ora.get("idopont");
	                System.out.println("nap: " + idopont.get("nap"));
	                System.out.println("tol: " + idopont.get("tol"));
	                System.out.println("ig: " + idopont.get("ig"));
	                System.out.println();
	                i++;
	            }

	            try (FileWriter writer = new FileWriter("orarendA86ANU1.json")) {
	                writer.write(root.toJSONString());
	                writer.flush();
	            }

	            System.out.println("Kész: orarendA86ANU1.json létrehozva.");

	        } catch (IOException | ParseException e) {
	            e.printStackTrace();
	        }
	    }

}
