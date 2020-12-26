package temple.edu.yelp_randomizer.View;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import temple.edu.yelp_randomizer.R;

public class ReviewsView extends RecyclerView.ViewHolder {

    private TextView reviewNameTextView;
    private TextView reviewRatingTextView;
    private TextView reviewTextTextView;
    private ImageView profilePictureImageView;

    public ReviewsView(View itemView) {
        super(itemView);

        reviewNameTextView = itemView.findViewById(R.id._reviewNameTextView);
        reviewRatingTextView = itemView.findViewById(R.id._reviewRatingTextView);
        reviewTextTextView = itemView.findViewById(R.id._reviewTextTextView);
        profilePictureImageView = itemView.findViewById(R.id._reviewProfilePictureImageView);
    }

    public TextView getReviewNameTextView() {
        return reviewNameTextView;
    }

    public TextView getReviewRatingTextView() {
        return reviewRatingTextView;
    }

    public TextView getReviewTextTextView() {
        return reviewTextTextView;
    }

    public ImageView getProfilePictureImageView() {
        return profilePictureImageView;
    }


}
