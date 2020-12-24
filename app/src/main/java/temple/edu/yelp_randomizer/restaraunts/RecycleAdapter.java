package temple.edu.yelp_randomizer.restaraunts;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import temple.edu.yelp_randomizer.R;
import temple.edu.yelp_randomizer.View.ReviewsView;
import temple.edu.yelp_randomizer.models.ReviewsModel;

import java.util.ArrayList;

public class RecycleAdapter extends RecyclerView.Adapter<ReviewsView> {
    private ArrayList<ReviewsModel> reviews;
    private Context context;

    public RecycleAdapter(Context context, ArrayList<ReviewsModel> reviews) {
        this.reviews = reviews;
        this.context = context;
    }

    @Override
    public ReviewsView onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reviews_view, parent, false);
        return new ReviewsView(view);
    }

    @Override
    public void onBindViewHolder(ReviewsView holder, int position) {
        ReviewsModel currentModel = reviews.get(position);
        holder.getReviewTextTextView().setText(currentModel.getText());
        holder.getReviewRatingTextView().setText(convertRating(currentModel.getRating()));
        holder.getReviewNameTextView().setText(currentModel.getUserName());
        if (currentModel.getProfileImageURL() != null) {
            Picasso.with(context).load(currentModel.getProfileImageURL()).into(holder.getProfilePictureImageView());
        }

    }


    @Override
    public int getItemCount() {
        return reviews.size();
    }

    private String convertRating(int rating){
        String rating_stars = "";
        for (int i = 0; i<= rating-1; i++){
            rating_stars += '⭐';
        }
        return rating_stars;
    }
}
