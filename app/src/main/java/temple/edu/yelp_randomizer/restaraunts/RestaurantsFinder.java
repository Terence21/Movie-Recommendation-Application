package temple.edu.yelp_randomizer.restaraunts;

import android.util.Log;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import temple.edu.yelp_randomizer.models.RestaurantHolder;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 1. base url
 * 2. add selectors/query to url (automated fashion)
 * 3. get response as string from yelp
 * 4. return arraylist representation of select response values... change to Holder class
 */
public class RestaurantsFinder{
// Zomato api key: f8aa3518c1deaaeb0d6f8e54174e1356 (1000 calls per day)
    int choice;
    HashMap<String, String> selectors;
    private final String BASE_YELP_URL = "https://api.yelp.com/v3/businesses/search";


    public RestaurantsFinder(int option_choice, Map<String, String> selectors){
        this.choice = option_choice;
        this.selectors = (HashMap<String, String>) selectors;
    }

    public RestaurantsFinder(){

    }

    // ---------------------------- SEARCH ENDPOINT ----------------------------
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

    /**
     * populate model class "Holder" with appropriate members
     * @return
     */
    public ArrayList<RestaurantHolder> getRandom(){
        ArrayList<RestaurantHolder> restaurants = new ArrayList<>();
        String response = getQueriedResponse();
        Log.i("RESPONSE: ", "getQueriedResponse: " + response);
        if (response != null) {
            JsonObject businessObject = new JsonParser().parse(response).getAsJsonObject();
            JsonArray array_json = businessObject.getAsJsonArray("businesses");
            for (JsonElement element : array_json) {
                JsonObject object = element.getAsJsonObject();
                String id = object.get("id").getAsString();
                String name = object.get("name").getAsString();
                String phone = object.get("phone").getAsString();
                String image = object.get("image_url").getAsString();
                JsonObject location_object = (JsonObject) object.get("location");
                String location = location_object.get("address1").getAsString() + ", " + location_object.get("zip_code").getAsString() + " " + location_object.get("city").getAsString() + " " + location_object.get("country").getAsString();
                restaurants.add(new RestaurantHolder(id, name, phone, image, location));
                // possible addition to add a web view to visit the restaurant from link? or intent action view to view link
            }
        }
        return restaurants;
    }

    public ArrayList<RestaurantHolder> getQueried(){
        ArrayList<RestaurantHolder> restaurants = new ArrayList<>();
        String response = getQueriedResponse();
        Log.i("RESPONSE: ", "getQueriedResponse: " + response);
        if (response != null) {
            JsonObject businessObject = new JsonParser().parse(response).getAsJsonObject();
            JsonArray array_json = businessObject.getAsJsonArray("businesses");
            for (JsonElement element : array_json) {
                JsonObject object = element.getAsJsonObject();
                String id = object.get("id").getAsString();
                String name = object.get("name").getAsString();
                String phone = object.get("phone").getAsString();
                String image = object.get("image_url").getAsString();
                JsonObject location_object = (JsonObject) object.get("location");
                String location = location_object.get("address1").getAsString() + ", " + location_object.get("zip_code").getAsString() + " " + location_object.get("city").getAsString() + " " + location_object.get("country").getAsString();
                restaurants.add(new RestaurantHolder(id, name, phone, image, location));
                // possible addition to add a web view to visit the restaurant from link? or intent action view to view link
            }
        }
        return restaurants;
    }



    private String getQueriedResponse(){
        try {
            String finishedURL = getQueriedURL();
            Log.i("URL: ", "getQueriedResponse: " + finishedURL);
            URL search_request = new URL(finishedURL);
            HttpURLConnection connection = (HttpURLConnection) search_request.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Authorization", "Bearer MnFTntDNgS4Tc11ChaXiyOWro6tm2wNp8h8KctuqooZtkvVM3cW5v9s9Bu9OfWZiUvw2_-uvhKMh2AFiiYuztU6TRyk6KezRRIUG9fFF2VhrIiiO_2hIEvKKPfPTX3Yx");

            int responseCode = connection.getResponseCode();
            System.out.println("ResponseCode: " + responseCode);
            Log.i("ResponseCode:", "ResponseCode: " + responseCode);
            StringBuilder response = new StringBuilder();
            Log.i("CODE: ", "getQueriedResponse: " + responseCode);
            if (responseCode == 200){
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null){
                    response.append(line);
               }
                reader.close();

            }
            Log.i("READER: ", "getQueriedResponse: " + response);
            return response.toString();

        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public void setSelectors(Map<String, String> selectors){
        this.selectors = (HashMap<String, String>) selectors;
    }

    private String getQueriedURL() {

        String url = BASE_YELP_URL;
        int count = 0;
        for (Map.Entry<String, String> selector : selectors.entrySet()) {
            if (count == 0) {
                url = addQuery(true, url, selector.getKey(), selector.getValue());
                count++;
            } else {
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

    // ----------------------------- CATEGORIES ENDPOINT ---------------------------------------
    public String getAll(){
        String total = "";
        ArrayList<String> categories = getCategoriesList();
        for (String item: categories){
            total += item + '\n';
        }
        return total;
    }

    public ArrayList<String> getCategoriesList(){
        String response = getCategoriesResponse();
        if (response != null) {
            JsonObject categoryObject = new JsonParser().parse(response).getAsJsonObject();
            JsonArray array_json = categoryObject.getAsJsonArray("categories");
            ArrayList<String> categories = new ArrayList<>();
            for (JsonElement element : array_json) {
                JsonObject object = element.getAsJsonObject();
                JsonArray parent_aliases = object.getAsJsonArray("parent_aliases");
                if (parent_aliases.size() > 0){
                    if (parent_aliases.get(0).getAsString().equals("restaurants")) {
                        categories.add(object.get("alias").getAsString());
                    }
                }
            }
            return categories;
        }
        return null;
    }
    public String getCategoriesResponse() {

        try {
            String base_url = "https://api.yelp.com/v3/categories";
            URL url = new URL(base_url);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Authorization", "Bearer MnFTntDNgS4Tc11ChaXiyOWro6tm2wNp8h8KctuqooZtkvVM3cW5v9s9Bu9OfWZiUvw2_-uvhKMh2AFiiYuztU6TRyk6KezRRIUG9fFF2VhrIiiO_2hIEvKKPfPTX3Yx");

            int responseCode = connection.getResponseCode();
            StringBuilder sb = new StringBuilder();
            if (responseCode == HttpsURLConnection.HTTP_OK){
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                if((line = reader.readLine()) != null){
                    sb.append(line);
                }
                reader.close();
                return sb.toString();
            }
        } catch (
                Exception e) {
            e.printStackTrace();
        }
        return null;
    }



}
