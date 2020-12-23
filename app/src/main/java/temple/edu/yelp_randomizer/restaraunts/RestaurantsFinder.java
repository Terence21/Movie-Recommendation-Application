package temple.edu.yelp_randomizer.restaraunts;

import android.util.Log;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.squareup.picasso.Picasso;
import temple.edu.yelp_randomizer.View.DetailsView;
import temple.edu.yelp_randomizer.models.DetailsModel;
import temple.edu.yelp_randomizer.models.RestaurantModel;
import temple.edu.yelp_randomizer.models.ReviewsModel;
import temple.edu.yelp_randomizer.storage.ApiKeyFinder;

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
    private String business_id;
    private static final String API_KEY = ApiKeyFinder.API_KEY;


    public RestaurantsFinder(int option_choice, Map<String, String> selectors){
        this.choice = option_choice;
        this.selectors = (HashMap<String, String>) selectors;
    }

    public RestaurantsFinder(){
    }

    public RestaurantsFinder(String business_id){
        this.business_id = business_id;
    }

    // ---------------------------- SEARCH ENDPOINT ----------------------------

    /**
     * @return names of restaurants as List
     */
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
     * @return RestaurantHolder list for randomFragment listView dataset
     */
    public ArrayList<RestaurantModel> getRandom(){
        ArrayList<RestaurantModel> restaurants = new ArrayList<>();
        String response = getQueriedResponse();
        Log.i("RESPONSE: ", "getQueriedResponse: " + response);
        if (response != null) {
            JsonObject businessObject = new JsonParser().parse(response).getAsJsonObject();
            JsonArray array_json = businessObject.getAsJsonArray("businesses");
            for (JsonElement element : array_json) {
                JsonObject object = element.getAsJsonObject();
                int rating = object.get("rating").getAsInt();
                String id = object.get("id").getAsString();
                String name = object.get("name").getAsString();
                String phone = object.get("phone").getAsString();
                String url = object.get("url").getAsString();
                String image = object.get("image_url").getAsString();
                JsonObject location_object = (JsonObject) object.get("location");
                String location = location_object.get("address1").getAsString() + ", " + location_object.get("zip_code").getAsString() + " " + location_object.get("city").getAsString() + " " + location_object.get("country").getAsString();
                restaurants.add(new RestaurantModel(rating, id, name, phone, image, location, url));
                // possible addition to add a web view to visit the restaurant from link? or intent action view to view link
            }
        }
        return restaurants;
    }

    public ArrayList<RestaurantModel> getQueried(){
        ArrayList<RestaurantModel> restaurants = new ArrayList<>();
        String response = getQueriedResponse();
        Log.i("RESPONSE: ", "getQueriedResponse: " + response);
        if (response != null) {
            JsonObject businessObject = new JsonParser().parse(response).getAsJsonObject();
            JsonArray array_json = businessObject.getAsJsonArray("businesses");
            for (JsonElement element : array_json) {
                JsonObject object = element.getAsJsonObject();
                int rating = object.get("rating").getAsInt();
                String id = object.get("id").getAsString();
                String name = object.get("name").getAsString();
                String phone = object.get("phone").getAsString();
                String url = object.get("url").getAsString();
                String image = object.get("image_url").getAsString();
                JsonObject location_object = (JsonObject) object.get("location");
                String location = location_object.get("address1").getAsString() + ", " + location_object.get("zip_code").getAsString() + " " + location_object.get("city").getAsString() + " " + location_object.get("country").getAsString();
                restaurants.add(new RestaurantModel(rating, id, name, phone, image, location,url));
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
            connection.setRequestProperty("Authorization", API_KEY);

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

    /**
     * update selectors
     * @param selectors
     */
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

    /**
     * get String representation of all names of categories
     * @return string of categories
     */
    public String getAll(){
        String total = "";
        ArrayList<String> categories = getCategoriesList();
        for (String item: categories){
            total += item + '\n';
        }
        return total;
    }

    /**
     * @return list of all categories
     */
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

    /**
     * @return response from yelp api category endpoint
     */
    public String getCategoriesResponse() {
        Log.i("Categories:", "getCategoriesResponse: Fetching categories endpoint for en_us locale");
        try {
            String base_url = "https://api.yelp.com/v3/categories";
            URL url = new URL(base_url);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Authorization", API_KEY);

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

    //  --------------------------------------- REVIEWS ENDPOINT ----------------------------------------------


    public ArrayList<ReviewsModel> getReviewsList(){
        ArrayList<ReviewsModel> reviews = new ArrayList<>();
        String response = getReviewsResponse();
        if (response != null){
            JsonObject reviewObject = new JsonParser().parse(response).getAsJsonObject();
            JsonArray review_json = reviewObject.getAsJsonArray("reviews");
            for (JsonElement element: review_json){
                JsonObject object = element.getAsJsonObject();
                int rating = object.get("rating").getAsInt();
                String reviewText = object.get("text").getAsString();
                Log.i("userReview:", "getReviewsList: " + reviewText);
                JsonObject userObject = (JsonObject) object.get("user");
                String userName = userObject.get("name").getAsString();
                JsonElement imageElement = userObject.get("image_url");
                String imageURL = null;
                try {
                    if (imageElement.getAsString() != null) {
                        imageURL = imageElement.getAsString();
                    }
                }catch(Exception ignored){

                }

                reviews.add(new ReviewsModel(rating, reviewText, imageURL, userName));
            }
        }
        return reviews;
    }
    public String getReviewsResponse(){
        if (business_id != null) {
            Log.i("Reviews", "getReviewsResponse: Fetching Reviews endpoint for restaurant");
            try {
                String base_url = "https://api.yelp.com/v3/businesses/" + business_id + "/reviews";
                URL url = new URL(base_url);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Authorization", API_KEY);

                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK){
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while((line = reader.readLine())!= null){
                        sb.append(line);
                    }
                    reader.close();
                    connection.disconnect();
                    return sb.toString();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return null;
    }

    // --------------------------------------- DETAILS ENDPOINT ----------------------------------------------

    public DetailsModel getDetails(){
        String response = getDetailsResponse();
        DetailsModel details = new DetailsModel();
        if (response != null){
            JsonObject detailsObject = new JsonParser().parse(response).getAsJsonObject();
            JsonArray hours_array = detailsObject.getAsJsonArray("hours");
            JsonArray photos_array = detailsObject.getAsJsonArray("photos");

            String image1 = "";
            String image2 = "";
            String image3 = "";
            if (photos_array.size() > 0) {
                image1 = photos_array.get(0).getAsString();
            }
            if (photos_array.size() > 1){
                image2 = photos_array.get(1).getAsString();
            }
            if (photos_array.size() > 3){
                image3 = photos_array.get(2).getAsString();
            }

            details.set_1_imageURL(image1);
            details.set_2_imageURL(image2);
            details.set_3_imageURL(image3);

            int[] nonAvailableDays = new int[7];
            if (hours_array != null) {
                JsonElement businessDetail = hours_array.get(0);
                JsonObject businessObject = businessDetail.getAsJsonObject();
                JsonArray open_array = businessObject.getAsJsonArray("open");

                if (open_array.size() > 0) {
                    // set hours open for each day

                    for (JsonElement element : open_array) {
                        JsonObject index = element.getAsJsonObject();
                        String start = index.get("start").getAsString();
                        String end = index.get("end").getAsString();
                        int day = index.get("day").getAsInt();
                        nonAvailableDays[day] = 1;
                        HashMap<String, String> hours = new HashMap<>();
                        hours.put("start", start);
                        hours.put("end", end);
                        setDetailsTime(details, day, hours);
                    }
                }

                for (int i = 0; i <= nonAvailableDays.length - 1; i++) {
                    if (nonAvailableDays[i] != 1) {
                        setDetailsTime(details, i, new HashMap<>());
                    }
                }
            }

        }
        return details;
    }

    private void setDetailsTime(DetailsModel details, int day, HashMap<String, String> hours){
        switch (day){
            case 0:
                details.setSundayText(hours);
                break;
            case 1:
                details.setMondayText(hours);
                break;
            case 2:
                details.setTuesdayText(hours);
                break;
            case 3:
                details.setWednesdayText(hours);
                break;
            case 4:
                details.setThursdayText(hours);
                break;
            case 5:
                details.setFridayText(hours);
                break;
            case 6:
                details.setSaturdayText(hours);
                break;
        }
    }

    private String getDetailsResponse(){
        if (business_id != null){
            Log.i("Details", "getDetailsResponse: Fetching Details endpoint for restaurant");
            try{
                String base_url = "https://api.yelp.com/v3/businesses/" + business_id;
                URL url  = new URL(base_url);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Authorization", API_KEY);

                int responseCode = connection.getResponseCode();

                if (responseCode == HttpURLConnection.HTTP_OK){
                    StringBuilder sb = new StringBuilder();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String line;
                    while ((line = reader.readLine()) != null){
                        sb.append(line);
                    }
                    reader.close();
                    connection.disconnect();
                    return sb.toString();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return null;
    }



}
