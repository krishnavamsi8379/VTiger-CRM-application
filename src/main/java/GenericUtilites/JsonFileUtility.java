package GenericUtilites;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * @author Vamsi
 * This class consists of Json File methods
 */

public class JsonFileUtility {
	/**
	 * This method is used to fetch the data from Json File
	 * @param key
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws ParseException
	 */
	
	public String fetchDataFromJsonFile(String key) throws FileNotFoundException, IOException, ParseException {
		JSONParser parser = new JSONParser();
		Object obj = parser.parse(new FileReader("./src/test/resources/Vitiger_JSON.json"));
		JSONObject js = (JSONObject)obj;
		 String data = js.get(key).toString();
		 return data;
		
	}

}
