package temple.edu.random.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import temple.edu.random.R
import temple.edu.random.models.RestaurantModel
import temple.edu.random.restaraunts.RandomRestaurantsAdapter
import android.view.View
import android.widget.*
import androidx.fragment.app.Fragment
import java.util.ArrayList

/**
 * A simple [Fragment] subclass.
 * Use the [UserRestarauntsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class UserRestarauntsFragment : Fragment() {
    var userRestaurantList: ListView? = null
    var savedRestaurants: ArrayList<RestaurantModel>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            savedRestaurants = arguments?.getParcelableArrayList("restaurants")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_user_restaraunts, container, false)
        userRestaurantList = v.findViewById<View>(R.id._myRestaurantsListView) as ListView
        userRestaurantList!!.adapter = savedRestaurants?.let { context?.let { it1 -> RandomRestaurantsAdapter(it1, it) } }
        return v
    }

    companion object {
        fun newInstance(savedRestaurants: ArrayList<RestaurantModel>?): UserRestarauntsFragment {
            val fragment = UserRestarauntsFragment()
            val args = Bundle()
            args.putParcelableArrayList("restaurants", savedRestaurants)
            fragment.arguments = args
            return fragment
        }
    }
}