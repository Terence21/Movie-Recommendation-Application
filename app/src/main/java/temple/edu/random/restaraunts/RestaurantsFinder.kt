package temple.edu.random.restaraunts

import android.util.Log
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import temple.edu.random.models.RestaurantModel
import temple.edu.random.models.ReviewsModel
import temple.edu.random.models.DetailsModel
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.Exception
import java.lang.StringBuilder
import java.net.HttpURLConnection
import java.net.URL
import java.util.ArrayList
import java.util.HashMap
import javax.net.ssl.HttpsURLConnection

/**
 * 1. base url
 * 2. add selectors/query to url (automated fashion)
 * 3. get response as string from yelp
 * 4. return arraylist representation of select response values... change to Holder class
 */
class RestaurantsFinder {
    // Zomato api key: f8aa3518c1deaaeb0d6f8e54174e1356 (1000 calls per day)
    var choice = 0
    var selectors: HashMap<String, String>? = null
    private val BASE_YELP_URL = "https://api.yelp.com/v3/businesses/search"
    private var business_id: String? = null
    private val API_KEY = "MnFTntDNgS4Tc11ChaXiyOWro6tm2wNp8h8KctuqooZtkvVM3cW5v9s9Bu9OfWZiUvw2_-uvhKMh2AFiiYuztU6TRyk6KezRRIUG9fFF2VhrIiiO_2hIEvKKPfPTX3Yx"

    constructor(option_choice: Int, selectors: Map<String, String>?) {
        choice = option_choice
        this.selectors = selectors as HashMap<String, String>?
    }

    constructor() {}
    constructor(business_id: String?) {
        this.business_id = business_id
    }
    // ---------------------------- SEARCH ENDPOINT ----------------------------
    /**
     * @return names of restaurants as List
     */
    val names: ArrayList<String>
        get() {
            val names = ArrayList<String>()
            val response = queriedResponse
            val businesses_json = JsonParser().parse(response).asJsonObject
            val array_json = businesses_json.getAsJsonArray("businesses")
            for (element in array_json) {
                val `object` = element.asJsonObject
                names.add(`object`["name"].asString)
                println(`object`["name"])
            }
            return names
        }// possible addition to add a web view to visit the restaurant from link? or intent action view to view link

    /**
     * populate model class "Holder" with appropriate members
     * @return RestaurantHolder list for randomFragment listView dataset
     */
    val random: ArrayList<RestaurantModel>
        get() {
            val restaurants = ArrayList<RestaurantModel>()
            val response = queriedResponse
            Log.i("RESPONSE: ", "getQueriedResponse: $response")
            if (response != null) {
                val businessObject = JsonParser().parse(response).asJsonObject
                val array_json = businessObject.getAsJsonArray("businesses")
                for (element in array_json) {
                    val `object` = element.asJsonObject
                    val rating = `object`["rating"].asInt
                    val id = `object`["id"].asString
                    val name = `object`["name"].asString
                    val phone = `object`["phone"].asString
                    val url = `object`["url"].asString
                    val image = `object`["image_url"].asString
                    val location_object = `object`["location"] as JsonObject
                    val location = location_object["address1"].asString + ", " + location_object["zip_code"].asString + " " + location_object["city"].asString + " " + location_object["country"].asString
                    restaurants.add(RestaurantModel(rating, id, name, phone, image, location, url))
                    // possible addition to add a web view to visit the restaurant from link? or intent action view to view link
                }
            }
            return restaurants
        }

    // possible addition to add a web view to visit the restaurant from link? or intent action view to view link
    val queried: ArrayList<RestaurantModel>
        get() {
            val restaurants = ArrayList<RestaurantModel>()
            val response = queriedResponse
            Log.i("RESPONSE: ", "getQueriedResponse: $response")
            if (response != null) {
                val businessObject = JsonParser().parse(response).asJsonObject
                val array_json = businessObject.getAsJsonArray("businesses")
                for (element in array_json) {
                    val `object` = element.asJsonObject
                    val rating = `object`["rating"].asInt
                    val id = `object`["id"].asString
                    val name = `object`["name"].asString
                    val phone = `object`["phone"].asString
                    val url = `object`["url"].asString
                    val image = `object`["image_url"].asString
                    val location_object = `object`["location"] as JsonObject
                    val location = location_object["address1"].asString + ", " + location_object["zip_code"].asString + " " + location_object["city"].asString + " " + location_object["country"].asString
                    restaurants.add(RestaurantModel(rating, id, name, phone, image, location, url))
                    // possible addition to add a web view to visit the restaurant from link? or intent action view to view link
                }
            }
            return restaurants
        }
    private val queriedResponse: String?
        private get() {
            try {
                val finishedURL = queriedURL
                Log.i("URL: ", "getQueriedResponse: $finishedURL")
                val search_request = URL(finishedURL)
                val connection = search_request.openConnection() as HttpURLConnection
                connection.requestMethod = "GET"
                connection.setRequestProperty("Authorization", API_KEY)
                val responseCode = connection.responseCode
                println("ResponseCode: $responseCode")
                Log.i("ResponseCode:", "ResponseCode: $responseCode")
                val response = StringBuilder()
                Log.i("CODE: ", "getQueriedResponse: $responseCode")
                if (responseCode == 200) {
                    val reader = BufferedReader(InputStreamReader(connection.inputStream))
                    var line: String?
                    while (reader.readLine().also { line = it } != null) {
                        response.append(line)
                    }
                    reader.close()
                }
                Log.i("READER: ", "getQueriedResponse: $response")
                return response.toString()
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return null
        }

    /**
     * update selectors
     * @param selectors
     */
    fun setSelectors(selectors: Map<String, String>?) {
        this.selectors = selectors as HashMap<String, String>?
    }

    private val queriedURL: String
        private get() {
            var url = BASE_YELP_URL
            var count = 0
            for ((key, value) in selectors!!) {
                if (count == 0) {
                    url = addQuery(true, url, key, value)
                    count++
                } else {
                    url = addQuery(false, url, key, value)
                }
            }
            return url
        }

    private fun addQuery(isFirstQuery: Boolean, before: String, key: String, value: String): String {
        return if (isFirstQuery) {
            "$before?$key=$value"
        } else {
            "$before&$key=$value"
        }
    }
    // ----------------------------- CATEGORIES ENDPOINT ---------------------------------------
    /**
     * get String representation of all names of categories
     * @return string of categories
     */
    val all: String
        get() {
            var total = ""
            val categories = categoriesList
            for (item in categories!!) {
                total += """
                $item
                
                """.trimIndent()
            }
            return total
        }

    /**
     * @return list of all categories
     */
    val categoriesList: ArrayList<String>?
        get() {
            val response = categoriesResponse
            if (response != null) {
                val categoryObject = JsonParser().parse(response).asJsonObject
                val array_json = categoryObject.getAsJsonArray("categories")
                val categories = ArrayList<String>()
                for (element in array_json) {
                    val `object` = element.asJsonObject
                    val parent_aliases = `object`.getAsJsonArray("parent_aliases")
                    if (parent_aliases.size() > 0) {
                        if (parent_aliases[0].asString == "restaurants") {
                            categories.add(`object`["alias"].asString)
                        }
                    }
                }
                return categories
            }
            return null
        }

    /**
     * @return response from yelp api category endpoint
     */
    val categoriesResponse: String?
        get() {
            Log.i("Categories:", "getCategoriesResponse: Fetching categories endpoint for en_us locale")
            try {
                val base_url = "https://api.yelp.com/v3/categories"
                val url = URL(base_url)
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "GET"
                connection.setRequestProperty("Authorization", API_KEY)
                val responseCode = connection.responseCode
                val sb = StringBuilder()
                if (responseCode == HttpsURLConnection.HTTP_OK) {
                    val reader = BufferedReader(InputStreamReader(connection.inputStream))
                    var line: String?
                    if (reader.readLine().also { line = it } != null) {
                        sb.append(line)
                    }
                    reader.close()
                    return sb.toString()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return null
        }

    //  --------------------------------------- REVIEWS ENDPOINT ----------------------------------------------
    val reviewsList: ArrayList<ReviewsModel>
        get() {
            val reviews = ArrayList<ReviewsModel>()
            val response = reviewsResponse
            if (response != null) {
                val reviewObject = JsonParser().parse(response).asJsonObject
                val review_json = reviewObject.getAsJsonArray("reviews")
                for (element in review_json) {
                    val `object` = element.asJsonObject
                    val rating = `object`["rating"].asInt
                    val reviewText = `object`["text"].asString
                    Log.i("userReview:", "getReviewsList: $reviewText")
                    val userObject = `object`["user"] as JsonObject
                    val userName = userObject["name"].asString
                    val imageElement = userObject["image_url"]
                    var imageURL: String? = null
                    try {
                        if (imageElement.asString != null) {
                            imageURL = imageElement.asString
                        }
                    } catch (ignored: Exception) {
                    }
                    reviews.add(ReviewsModel(rating, reviewText, imageURL, userName))
                }
            }
            return reviews
        }
    val reviewsResponse: String?
        get() {
            if (business_id != null) {
                Log.i("Reviews", "getReviewsResponse: Fetching Reviews endpoint for restaurant")
                try {
                    val base_url = "https://api.yelp.com/v3/businesses/$business_id/reviews"
                    val url = URL(base_url)
                    val connection = url.openConnection() as HttpURLConnection
                    connection.requestMethod = "GET"
                    connection.setRequestProperty("Authorization", API_KEY)
                    val responseCode = connection.responseCode
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        val reader = BufferedReader(InputStreamReader(connection.inputStream))
                        val sb = StringBuilder()
                        var line: String?
                        while (reader.readLine().also { line = it } != null) {
                            sb.append(line)
                        }
                        reader.close()
                        connection.disconnect()
                        return sb.toString()
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            return null
        }// set hours open for each day

    // --------------------------------------- DETAILS ENDPOINT ----------------------------------------------
    val details: DetailsModel
        get() {
            val response = detailsResponse
            val details = DetailsModel()
            if (response != null) {
                val detailsObject = JsonParser().parse(response).asJsonObject
                val hours_array = detailsObject.getAsJsonArray("hours")
                val photos_array = detailsObject.getAsJsonArray("photos")
                var image1: String? = ""
                var image2: String? = ""
                var image3: String? = ""
                if (photos_array.size() > 0) {
                    image1 = photos_array[0].asString
                }
                if (photos_array.size() > 1) {
                    image2 = photos_array[1].asString
                }
                if (photos_array.size() > 3) {
                    image3 = photos_array[2].asString
                }
                details._1_imageURL = image1
                details._2_imageURL = image2
                details._3_imageURL = image3
                val nonAvailableDays = IntArray(7)
                if (hours_array != null) {
                    val businessDetail = hours_array[0]
                    val businessObject = businessDetail.asJsonObject
                    val open_array = businessObject.getAsJsonArray("open")
                    if (open_array.size() > 0) {
                        // set hours open for each day
                        for (element in open_array) {
                            val index = element.asJsonObject
                            val start = index["start"].asString
                            val end = index["end"].asString
                            val day = index["day"].asInt
                            nonAvailableDays[day] = 1
                            val hours = HashMap<String, String>()
                            hours["start"] = start
                            hours["end"] = end
                            setDetailsTime(details, day, hours)
                        }
                    }
                }
                for (i in 0..nonAvailableDays.size - 1) {
                    if (nonAvailableDays[i] != 1) {
                        setDetailsTime(details, i, HashMap())
                    }
                }
            }
            return details
        }

    private fun setDetailsTime(details: DetailsModel, day: Int, hours: HashMap<String, String>) {
        when (day) {
            0 -> details.setSundayText(hours)
            1 -> details.setMondayText(hours)
            2 -> details.setTuesdayText(hours)
            3 -> details.setWednesdayText(hours)
            4 -> details.setThursdayText(hours)
            5 -> details.setFridayText(hours)
            6 -> details.setSaturdayText(hours)
        }
    }

    private val detailsResponse: String?
        private get() {
            if (business_id != null) {
                Log.i("Details", "getDetailsResponse: Fetching Details endpoint for restaurant")
                try {
                    val base_url = "https://api.yelp.com/v3/businesses/$business_id"
                    val url = URL(base_url)
                    val connection = url.openConnection() as HttpURLConnection
                    connection.requestMethod = "GET"
                    connection.setRequestProperty("Authorization", API_KEY)
                    val responseCode = connection.responseCode
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        val sb = StringBuilder()
                        val reader = BufferedReader(InputStreamReader(connection.inputStream))
                        var line: String?
                        while (reader.readLine().also { line = it } != null) {
                            sb.append(line)
                        }
                        reader.close()
                        connection.disconnect()
                        return sb.toString()
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            return null
        }
}