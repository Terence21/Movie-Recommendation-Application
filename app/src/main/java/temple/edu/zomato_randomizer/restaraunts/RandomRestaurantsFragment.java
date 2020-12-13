package temple.edu.zomato_randomizer.restaraunts;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.ListView;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import temple.edu.zomato_randomizer.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class RandomRestaurantsFragment extends Fragment {

    ListView listView_restaurants;
    ArrayList<Holder> restaurantsList;

    public RandomRestaurantsFragment() {
        // Required empty public constructor
    }

    public static RandomRestaurantsFragment newInstance() {
        RandomRestaurantsFragment fragment = new RandomRestaurantsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_random_restaurants, container, false);
        //String[] coordinate = getCoordinates();
        Map<String, String> selectors = new HashMap<>();
        selectors.put("latitude", "40.050900");
        selectors.put("longitude", "-75.087883");
        selectors.put("term", "restaurants+italian");
        selectors.put("radius", "16093");
        Thread thread = new Thread() {
            @Override
            public void run() {
                RestaurantsFinder restaurantsFinder = new RestaurantsFinder(0, selectors);
                restaurantsList = restaurantsFinder.getRandom();
            }
        };
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        listView_restaurants = (ListView) view.findViewById(R.id._restaurantsListView);
        listView_restaurants.setAdapter(new RandomRestaurantsAdapter(getContext(), restaurantsList));


        return view;
    }

    /**
     * may need to update so doesn't sending blocking if location isn't available
     * // https://stackoverflow.com/questions/2227292/how-to-get-latitude-and-longitude-of-the-mobile-device-in-android
     * @return
     */
    public String[] getCoordinates(){
        String []response = new String[2];
        LocationManager lm = (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);
        Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        response[0] = String.valueOf(location.getLatitude());
        response[1] =String.valueOf(location.getLongitude());
        return response;
    }
}