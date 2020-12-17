package temple.edu.yelp_randomizer.fragments;

import android.content.Context;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import temple.edu.yelp_randomizer.R;
import temple.edu.yelp_randomizer.View.RestaurantView;
import temple.edu.yelp_randomizer.models.RestaurantHolder;

import java.util.ArrayList;


public class RestaurantContentFragment extends Fragment {

    ImageButton saveRestaurantButton;
    WebView restaurantWebView;
    RestaurantView contentRestaurantView;

    RestaurantHolder restaurant;
    ArrayList<RestaurantHolder> savedRestaurants;
    RestaurantContentListener listener;

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
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_restaurant_content, container, false);
        saveRestaurantButton = v.findViewById(R.id._saveRestaurantButton);
        restaurantWebView = v.findViewById(R.id._restaurantWebView);

        contentRestaurantView = v.findViewById(R.id._contentRestaurantView);
        String text = restaurant.getName() + "\n\n" + restaurant.getPhone() + "\n" + restaurant.getLocation();
        contentRestaurantView.setTextView(text);
        contentRestaurantView.setImageView(restaurant.getImage());

        restaurantWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                restaurantWebView.loadUrl(restaurant.getUrl());
                return true;
            }
        });


        saveRestaurantButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                savedRestaurants.add(restaurant);
                listener.updateContentSavedRestaurants();
            }
        });
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