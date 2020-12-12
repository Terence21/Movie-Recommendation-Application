package temple.edu.zomato_randomizer.restaraunts;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 1. base url
 * 2. add selectors/query to url (automated fashion)
 * 3. get response as string from yelp
 * 4. return arraylist representation of select response values... change to Holder class
 */
public class RestaurantsFinder {
// Zomato api key: f8aa3518c1deaaeb0d6f8e54174e1356 (1000 calls per day)
    int choice;
    HashMap<String, String> selectors;
    private final String BASE_YELP_URL = "https://api.yelp.com/v3/buisnesses/search";


    public RestaurantsFinder(int option_choice, Map<String, String> selectors){
        this.choice = option_choice;
        this.selectors = (HashMap<String, String>) selectors;
    }

    public ArrayList<String> getNames(){
        ArrayList<String> names = new ArrayList<>();
        String response = getQueriedResponse();
        JsonObject businesses_json= new JsonParser().parse(response).getAsJsonObject();
        JsonArray array_json = businesses_json.getAsJsonArray("businesses");
        for (JsonElement element : array_json){
            JsonObject object = element.getAsJsonObject();
            names.add(object.get("name").getAsString());
            System.out.println(object.get("name"));

        }
        return names;
    }

    private String getQueriedResponse(){
        try {
            String finishedURL = getQueriedURL();
            URL search_request = new URL(finishedURL);
            HttpURLConnection connection = (HttpURLConnection) search_request.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Authorization", "Bearer MnFTntDNgS4Tc11ChaXiyOWro6tm2wNp8h8KctuqooZtkvVM3cW5v9s9Bu9OfWZiUvw2_-uvhKMh2AFiiYuztU6TRyk6KezRRIUG9fFF2VhrIiiO_2hIEvKKPfPTX3Yx");

            int responseCode = connection.getResponseCode();
            StringBuilder response = new StringBuilder();
            if (responseCode == 200){
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null){
                    response.append(line);
                }
                reader.close();
            }

            return response.toString();

        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public void setSelectors(Map<String, String> selectors){
        this.selectors = (HashMap<String, String>) selectors;
    }

    private String getQueriedURL(){
        String url = BASE_YELP_URL;
        int count = 0;
        for (Map.Entry<String, String> selector: selectors.entrySet()){
            if (count == 0) {
                url = addQuery(true, url, selector.getKey(), selector.getValue());
                count++;
            }else{
                url = addQuery(false, url, selector.getKey(), selector.getValue());
            }
        }
        return url;
    }

    private String addQuery(boolean isFirstQuery, String before, String key, String value){
        if (isFirstQuery) {
            return before + "?" + key + "=" + value;
        } else{
            return before + "&" + key + "=" + value;
        }
    }
    public void populateQuery(){

    }



}
