package temple.edu.yelp_randomizer.fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import temple.edu.yelp_randomizer.R;
import temple.edu.yelp_randomizer.View.RestaurantView;
import temple.edu.yelp_randomizer.models.RestaurantHolder;

import java.util.ArrayList;


public class RestaurantContentFragment extends Fragment {

    RestaurantView contentRestaurantView;
    RecyclerView additionalImageRecycleView;
    RecyclerView reviewRecycleView;

    RestaurantHolder restaurant;
    ArrayList<RestaurantHolder> savedRestaurants;
    RestaurantContentListener listener;

    String restaurantUrl;

    public RestaurantContentFragment() {
        // Required empty public constructor
    }

    public static RestaurantContentFragment newInstance(RestaurantHolder restaurant, ArrayList<RestaurantHolder> savedRestaurants) {
        RestaurantContentFragment fragment = new RestaurantContentFragment();
        Bundle args = new Bundle();
        args.putParcelable("restaurant", restaurant);
        args.putParcelableArrayList("savedRestaurants", savedRestaurants);
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
        additionalImageRecycleView = v.findViewById(R.id._additionalImageRecycleView);
        reviewRecycleView = v.findViewById(R.id._reviewRecycleView);

        contentRestaurantView.setImageView(restaurant.getImage());
        String text = restaurant.getName() + "\n\n" + restaurant.getLocation() + "\n" + restaurant.getPhone().replace("+","");
        contentRestaurantView.setTextView(text);



        return v;
    }

    public ArrayList<RestaurantHolder> getSavedRestaurants(){
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
        public void updateContentSavedRestaurants();
    }
}