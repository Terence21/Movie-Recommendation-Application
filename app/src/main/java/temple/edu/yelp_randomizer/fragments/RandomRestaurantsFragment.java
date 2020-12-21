package temple.edu.yelp_randomizer.fragments;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ListView;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import temple.edu.yelp_randomizer.R;
import temple.edu.yelp_randomizer.models.RestaurantModel;
import temple.edu.yelp_randomizer.restaraunts.RandomRestaurantsAdapter;
import temple.edu.yelp_randomizer.restaraunts.RestaurantsFinder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class RandomRestaurantsFragment extends Fragment {

    ListView listView_restaurants;
    ArrayList<RestaurantModel> restaurantsList;
    ArrayList<RestaurantModel> savedRestaurants;
    SavedRestaurantListener listener;


    public RandomRestaurantsFragment() {
        // Required empty public constructor
    }

    public static RandomRestaurantsFragment newInstance(ArrayList<RestaurantModel> savedRestaurants) {
        RandomRestaurantsFragment fragment = new RandomRestaurantsFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList("savedRestaurants", savedRestaurants);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            savedRestaurants = getArguments().getParcelableArrayList("savedRestaurants");
            requestRandomYelpNetworkOperation();
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_random_restaurants, container, false);
        //String[] coordinate = getCoordinates();

        listView_restaurants = (ListView) view.findViewById(R.id._searchedRestaurantsListView); //cant have view operations outside of main thread
        listView_restaurants.setAdapter(new RandomRestaurantsAdapter(getContext(), restaurantsList));
        listView_restaurants.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                listener.launchRandomContent(restaurantsList.get(i));
               // listener.updateSaveList();
            }
        });


        return view;
    }

    /**
     * fixed key value pairs to find list of random restaurants
     * run network operation away form main thread
     */
    public void requestRandomYelpNetworkOperation(){

        Map<String, String> selectors = new HashMap<>();
       selectors.put("latitude", "40.050900");
       selectors.put("longitude", "-75.087883");
        //selectors.put("latitude",listener.getLatitude());
       // selectors.put("longitude", listener.getLongitude());
        selectors.put("term", "restaurants+italian");
        selectors.put("radius", "16093");
        selectors.put("limit","5");

        Thread thread = new Thread() {
            @Override
            public void run() {
                RestaurantsFinder restaurantsFinder = new RestaurantsFinder(0, selectors);
                restaurantsList = restaurantsFinder.getRandom();
            }
        };
        thread.start();
        try {
            thread.join(); // allow for list to be updated on main thread
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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

    /**
     * @return current state of savedRestaurants
     */
    public ArrayList<RestaurantModel> getSavedRestaurants(){
        return savedRestaurants;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof SavedRestaurantListener){
            listener = (SavedRestaurantListener) context;
        }
        else{
            throw new RuntimeException("context not instance of SavedRestaurantListener.... calling activity must implement properly");
        }
    }

    public interface SavedRestaurantListener{
        public void updateSaveList();
        public void launchRandomContent(RestaurantModel restaurant);
        public String getLatitude();
        public String getLongitude();
    }
}