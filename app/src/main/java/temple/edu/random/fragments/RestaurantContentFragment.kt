package temple.edu.random.fragments

import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.ViewGroup
import temple.edu.random.R
import temple.edu.random.models.RestaurantModel
import temple.edu.random.View.RestaurantView
import temple.edu.random.View.DetailsView
import com.google.android.material.button.MaterialButton
import temple.edu.random.models.DetailsModel
import androidx.recyclerview.widget.RecyclerView
import temple.edu.random.models.ReviewsModel
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.View
import androidx.fragment.app.Fragment
import java.lang.RuntimeException
import java.util.ArrayList

class RestaurantContentFragment : Fragment() {
    var contentRestaurantView: RestaurantView? = null
    var detailsView: DetailsView? = null
    var reviewButton: MaterialButton? = null
    var currentRestaurant: RestaurantModel? = null
    var details: DetailsModel? = null
    var savedRestaurants: ArrayList<RestaurantModel>? = null
    var listener: RestaurantContentListener? = null
    var restaurantUrl: String? = null
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var reviews: ArrayList<ReviewsModel?>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            currentRestaurant = requireArguments().getParcelable("restaurant")
            savedRestaurants = requireArguments().getParcelableArrayList("savedRestaurants")
            restaurantUrl = currentRestaurant!!.url?.replace("biz", "menu")
            reviews = requireArguments().getParcelableArrayList("reviews")
            details = requireArguments().getParcelable("details")

            /*restaurantUrl = restaurant.getUrl().replace("www", "m");
            restaurantUrl = restaurant.getUrl().replace("adjust_", "utm_");*/
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_restaurant_content, container, false)
        contentRestaurantView = v.findViewById(R.id._restaurantContentView)
        detailsView = v.findViewById(R.id._restaurantContentDetailsview)
        reviewButton = v.findViewById(R.id._reviewsButton)
        //   reviewRecycleView = v.findViewById(R.id._reviewRecycleView);
        contentRestaurantView?.setImageView(currentRestaurant!!.image)
        val text = """
            ${currentRestaurant!!.name}
            
            ${currentRestaurant!!.location}
            ${currentRestaurant!!.phone?.replace("+", "")}
            """.trimIndent()
        contentRestaurantView?.setTextView(text)
        layoutManager = LinearLayoutManager(activity) // create a LinearLayout for recycle view
        details!!._1_imageURL?.let { detailsView?.setFirstImageView(it) }
        details!!._2_imageURL?.let { detailsView?.setSecondImageView(it) }
        details!!._3_imageURL?.let { detailsView?.setThirdImageView(it) }
        detailsView?.setSundayTextView(details!!.sundayText)
        detailsView?.setMondayTextView(details!!.mondayText)
        detailsView?.setTuesdayTextView(details!!.tuesdayText)
        detailsView?.setWednesdayTextView(details!!.wednesdayText)
        detailsView?.setThursdayTextView(details!!.thursdayText)
        detailsView?.setFridayTextView(details!!.fridayText)
        detailsView?.setSaturdayTextView(details!!.saturdayText)
        reviewButton?.setOnClickListener(View.OnClickListener { listener!!.launchReviewActivity(reviews) })
        return v
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = if (context is RestaurantContentListener) {
            context
        } else {
            throw RuntimeException("Calling Activity must implement RestaurantContentListener")
        }
    }

    interface RestaurantContentListener {
        fun launchReviewActivity(reviews: ArrayList<ReviewsModel?>?)
    }

    companion object {
        fun newInstance(restaurant: RestaurantModel?, savedRestaurants: ArrayList<RestaurantModel>?, reviews: ArrayList<out Parcelable>, details: DetailsModel?): RestaurantContentFragment {
            val fragment = RestaurantContentFragment()
            val args = Bundle()
            args.putParcelable("restaurant", restaurant)
            args.putParcelableArrayList("savedRestaurants", savedRestaurants)
            args.putParcelableArrayList("reviews", reviews)
            args.putParcelable("details", details)
            fragment.arguments = args
            return fragment
        }
    }
}