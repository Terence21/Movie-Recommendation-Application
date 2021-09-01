package temple.edu.random.View

import android.widget.TextView
import android.view.View
import android.widget.ImageView
import temple.edu.random.R
import androidx.recyclerview.widget.RecyclerView

class ReviewsView(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val reviewNameTextView: TextView
    val reviewRatingTextView: TextView
    val reviewTextTextView: TextView
    val profilePictureImageView: ImageView

    init {
        reviewNameTextView = itemView.findViewById(R.id._reviewNameTextView)
        reviewRatingTextView = itemView.findViewById(R.id._reviewRatingTextView)
        reviewTextTextView = itemView.findViewById(R.id._reviewTextTextView)
        profilePictureImageView = itemView.findViewById(R.id._reviewProfilePictureImageView)
    }
}