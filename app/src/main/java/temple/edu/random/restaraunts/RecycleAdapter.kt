package temple.edu.random.restaraunts

import android.content.Context
import android.view.ViewGroup
import temple.edu.random.models.ReviewsModel
import androidx.recyclerview.widget.RecyclerView
import temple.edu.random.View.ReviewsView
import android.view.LayoutInflater
import temple.edu.random.R
import com.squareup.picasso.Picasso
import java.util.ArrayList

class RecycleAdapter(private val context: Context, private val reviews: ArrayList<ReviewsModel>) : RecyclerView.Adapter<ReviewsView>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewsView {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.reviews_view, parent, false)
        return ReviewsView(view)
    }

    override fun onBindViewHolder(holder: ReviewsView, position: Int) {
        val currentModel = reviews[position]
        holder.reviewTextTextView.text = currentModel.text
        holder.reviewRatingTextView.text = convertRating(currentModel.rating)
        holder.reviewNameTextView.text = currentModel.userName
        if (currentModel.profileImageURL != null) {
            Picasso.with(context).load(currentModel.profileImageURL).into(holder.profilePictureImageView)
        }
    }

    override fun getItemCount(): Int {
        return reviews.size
    }

    private fun convertRating(rating: Int): String {
        var rating_stars = ""
        for (i in 0..rating - 1) {
            rating_stars += '⭐'
        }
        return rating_stars
    }
}