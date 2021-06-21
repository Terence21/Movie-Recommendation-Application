package temple.edu.random.restaraunts

import android.content.Context
import android.widget.BaseAdapter
import android.view.ViewGroup
import android.view.View
import temple.edu.random.models.RestaurantModel
import temple.edu.random.View.RestaurantView
import java.util.ArrayList

class RandomRestaurantsAdapter(var context: Context, private val restaurants: ArrayList<RestaurantModel>) : BaseAdapter() {
    override fun getCount(): Int {
        return restaurants.size
    }

    override fun getItem(i: Int): Any {
        return restaurants[i]
    }

    override fun getItemId(i: Int): Long {
        return 0
    }

    override fun getView(i: Int, view: View, viewGroup: ViewGroup): View {
        val restaurantView: RestaurantView
        restaurantView = if (view != null) {
            view as RestaurantView
        } else {
            RestaurantView(context, null)
        }
        val text = """
            ${restaurants[i].name}
            ${convertRating(restaurants[i].rating)}
            
            ${restaurants[i].phone}
            
            ${restaurants[i].location}
            """.trimIndent()
        restaurantView.setTextView(text)
        restaurantView.setImageView(restaurants[i].image)
        return restaurantView
    }

    private fun convertRating(rating: Int): String {
        var rating_stars = ""
        for (i in 0..rating - 1) {
            rating_stars += '⭐'
        }
        return rating_stars
    }
}