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
import android.view.Gravity
import android.view.View
import android.widget.*
import androidx.fragment.app.Fragment
import java.lang.Exception
import java.lang.RuntimeException
import java.util.ArrayList
import java.util.HashMap

/**
 * list view of fragments
 */
class SearchedRestaurantsFragment : Fragment() {
    /**
     * @return current state of savedRestaurants list
     */
    var savedRestaurants: ArrayList<RestaurantModel>? = null
        private set
    private var searchedRestaurants: ArrayList<RestaurantModel>? = null
    private var selectors: HashMap<String, String>? = null
    private var searchedRestaurantsListView: ListView? = null
    private var alertTextView: TextView? = null
    private var availableRestaurants = false
    private var listener: SavedChooseRestaurantListener? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            savedRestaurants = arguments?.getParcelableArrayList("savedRestaurants")
            selectors = arguments?.getSerializable("selectors") as HashMap<String, String>?
            requestSearchedRestaurantOperation(selectors)
            availableRestaurants = searchedRestaurants!!.size != 0
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_searched_restaurants, container, false)
        if (availableRestaurants) {
            searchedRestaurantsListView = v.findViewById(R.id._searchedRestaurantsListView)
            searchedRestaurantsListView?.adapter = context?.let { searchedRestaurants?.let { it1 -> RandomRestaurantsAdapter(it, it1) } }
            searchedRestaurantsListView?.onItemClickListener = OnItemClickListener { adapterView, view, i, l ->
                listener!!.launchSearchedContent(searchedRestaurants!![i])
                //  listener.updateSaveListChoose_();
            }
        } else {
            alertTextView = v.findViewById(R.id._alertUserTextVeiw)
            alertTextView?.text = "Restaurant Search Unsuccessful, Possibly Too Specific For Your Given Area\nTry A Different Search"
            alertTextView?.gravity = Gravity.CENTER
        }
        return v
    }

    /**
     * add fixed key value pairs to the selectors for request
     * run network operation outside of main thread
     * @param selectors
     */
    fun requestSearchedRestaurantOperation(selectors: MutableMap<String, String>?) {
        selectors!!["latitude"] = "40.050900"
        selectors["longitude"] = "-75.087883"
        selectors["limit"] = "10"
        selectors["radius"] = "16093" // user should do this one
        val thread: Thread = object : Thread() {
            override fun run() {
                val restaurantsFinder = RestaurantsFinder(0, selectors)
                searchedRestaurants = restaurantsFinder.queried
            }
        }
        thread.start()
        try {
            thread.join()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = if (context is SavedChooseRestaurantListener) {
            context
        } else {
            throw RuntimeException("Calling Activity must implement SavedChooseRestaurantListener")
        }
    }

    interface SavedChooseRestaurantListener {
        fun updateSaveListChoose_()
        fun launchSearchedContent(restaurant: RestaurantModel?)
    }

    companion object {
        fun newInstance(savedRestaurants: ArrayList<RestaurantModel>?, selectors: HashMap<String, String>?): SearchedRestaurantsFragment {
            val fragment = SearchedRestaurantsFragment()
            val args = Bundle()
            args.putParcelableArrayList("savedRestaurants", savedRestaurants)
            args.putSerializable("selectors", selectors)
            fragment.arguments = args
            return fragment
        }
    }
}