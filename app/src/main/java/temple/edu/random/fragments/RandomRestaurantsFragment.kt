package temple.edu.random.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import temple.edu.random.R
import android.widget.AdapterView.OnItemClickListener
import temple.edu.random.models.RestaurantModel
import temple.edu.random.restaraunts.RandomRestaurantsAdapter
import temple.edu.random.restaraunts.RestaurantsFinder
import android.location.LocationManager
import android.view.View
import android.widget.*
import androidx.fragment.app.Fragment
import java.lang.RuntimeException
import java.util.HashMap
import kotlin.collections.ArrayList

class RandomRestaurantsFragment : Fragment() {
    private var listviewRestaurants: ListView? = null
    var restaurantsList: ArrayList<RestaurantModel> = ArrayList()

    /**
     * @return current state of savedRestaurants
     */
    var savedRestaurants: ArrayList<RestaurantModel>? = null
    var listener: SavedRestaurantListener? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            savedRestaurants = requireArguments().getParcelableArrayList("savedRestaurants")
            requestRandomYelpNetworkOperation()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_random_restaurants, container, false)
        //String[] coordinate = getCoordinates();
        listviewRestaurants = view.findViewById<View>(R.id._searchedRestaurantsListView) as ListView //cant have view operations outside of main thread
        listviewRestaurants!!.adapter = context?.let { RandomRestaurantsAdapter(it, restaurantsList) }
        listviewRestaurants!!.onItemClickListener = OnItemClickListener { _, view, i, _ -> listener!!.launchRandomContent(restaurantsList[i]) }
        return view
    }

    /**
     * fixed key value pairs to find list of random restaurants
     * run network operation away form main thread
     */
    fun requestRandomYelpNetworkOperation() {
        val selectors: MutableMap<String, String> = HashMap()
        selectors["latitude"] = "40.050900"
        selectors["longitude"] = "-75.087883"
        //selectors.put("latitude",listener.getLatitude());
        // selectors.put("longitude", listener.getLongitude());
        selectors["term"] = "restaurants+italian"
        selectors["radius"] = "16093"
        selectors["limit"] = "5"
        val thread: Thread = object : Thread() {
            override fun run() {
                val restaurantsFinder = RestaurantsFinder(0, selectors)
                restaurantsList = restaurantsFinder.random
            }
        }
        thread.start()
        try {
            thread.join() // allow for list to be updated on main thread
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }

    /**
     * may need to update so doesn't sending blocking if location isn't available
     * // https://stackoverflow.com/questions/2227292/how-to-get-latitude-and-longitude-of-the-mobile-device-in-android
     * @return
     */
    val coordinates: Array<String?>
        get() {
            val response = arrayOfNulls<String>(2)
            val lm = activity!!.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            val location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            response[0] = location!!.latitude.toString()
            response[1] = location.longitude.toString()
            return response
        }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = if (context is SavedRestaurantListener) {
            context
        } else {
            throw RuntimeException("context not instance of SavedRestaurantListener.... calling activity must implement properly")
        }
    }

    interface SavedRestaurantListener {
        fun updateSaveList()
        fun launchRandomContent(restaurant: RestaurantModel?)
        val latitude: Double?
        val longitude: Double?
    }

    companion object {
        fun newInstance(savedRestaurants: java.util.ArrayList<RestaurantModel>?): RandomRestaurantsFragment {
            val fragment = RandomRestaurantsFragment()
            val args = Bundle()
            args.putParcelableArrayList("savedRestaurants", savedRestaurants)
            fragment.arguments = args
            return fragment
        }
    }
}