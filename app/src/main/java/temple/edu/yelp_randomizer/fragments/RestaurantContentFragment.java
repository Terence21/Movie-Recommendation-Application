package temple.edu.yelp_randomizer.fragments;

import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.button.MaterialButton;
import temple.edu.yelp_randomizer.R;
import temple.edu.yelp_randomizer.View.DetailsView;
import temple.edu.yelp_randomizer.View.RestaurantView;
import temple.edu.yelp_randomizer.models.DetailsModel;
import temple.edu.yelp_randomizer.models.RestaurantModel;
import temple.edu.yelp_randomizer.models.ReviewsModel;
import temple.edu.yelp_randomizer.restaraunts.RecycleAdapter;

import java.util.ArrayList;


public class RestaurantContentFragment extends Fragment {

    RestaurantView contentRestaurantView;
    DetailsView detailsView;
    MaterialButton reviewButton;

    RestaurantModel restaurant;
    DetailsModel details;
    ArrayList<RestaurantModel> savedRestaurants;
    RestaurantContentListener listener;

    String restaurantUrl;


    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<ReviewsModel> reviews;

    public RestaurantContentFragment() {
        // Required empty public constructor
    }

    public static RestaurantContentFragment newInstance(RestaurantModel restaurant, ArrayList<RestaurantModel> savedRestaurants, ArrayList<ReviewsModel> reviews, DetailsModel details) {
        RestaurantContentFragment fragment = new RestaurantContentFragment();
        Bundle args = new Bundle();
        args.putParcelable("restaurant", restaurant);
        args.putParcelableArrayList("savedRestaurants", savedRestaurants);
        args.putParcelableArrayList("reviews", reviews);
        args.putParcelable("details",details);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            restaurant = getArguments().getParcelable("restaurant");
            savedRestaurants = getArguments().getParcelableArrayList("savedRestaurants");
            restaurantUrl = restaurant.getUrl().replace("biz", "menu");
            reviews = getArguments().getParcelableArrayList("reviews");
            details = getArguments().getParcelable("details");

            /*restaurantUrl = restaurant.getUrl().replace("www", "m");
            restaurantUrl = restaurant.getUrl().replace("adjust_", "utm_");*/
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_restaurant_content, container, false);

        contentRestaurantView = v.findViewById(R.id._restaurantContentView);
        detailsView = v.findViewById(R.id._restaurantContentDetailsview);
        reviewButton = v.findViewById(R.id._reviewsButton);
     //   reviewRecycleView = v.findViewById(R.id._reviewRecycleView);

        contentRestaurantView.setImageView(restaurant.getImage());
        String text = restaurant.getName() + "\n\n" + restaurant.getLocation() + "\n" + restaurant.getPhone().replace("+","");
        contentRestaurantView.setTextView(text);

        layoutManager = new LinearLayoutManager(getActivity()); // create a LinearLayout for recycle view

        detailsView.setFirstImageView(details.get_1_imageURL());
        detailsView.setSecondImageView(details.get_2_imageURL());
        detailsView.setThirdImageView(details.get_3_imageURL());
        detailsView.setSundayTextView(details.getSundayText());
        detailsView.setMondayTextView(details.getMondayText());
        detailsView.setTuesdayTextView(details.getTuesdayText());
        detailsView.setWednesdayTextView(details.getWednesdayText());
        detailsView.setThursdayTextView(details.getThursdayText());
        detailsView.setFridayTextView(details.getFridayText());
        detailsView.setSaturdayTextView(details.getSaturdayText());

        reviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.launchReviewActivity(reviews);
            }
        });

        return v;
    }

    public RestaurantModel getCurrentRestaurant(){
        return restaurant;
    }

    public ArrayList<RestaurantModel> getSavedRestaurants(){
        return savedRestaurants;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof RestaurantContentListener){
            listener = (RestaurantContentListener) context;
        }else{
            throw new RuntimeException("Calling Activity must implement RestaurantContentListener");
        }
    }

    public interface RestaurantContentListener{
        public void launchReviewActivity(ArrayList<ReviewsModel> reviews);
    }
}