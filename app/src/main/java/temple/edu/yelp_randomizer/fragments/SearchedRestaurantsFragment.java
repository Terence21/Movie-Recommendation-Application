package temple.edu.yelp_randomizer.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
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


/**
 * list view of fragments
 */
public class SearchedRestaurantsFragment extends Fragment {

    private ArrayList<RestaurantModel> savedRestaurants;
    private ArrayList<RestaurantModel> searchedRestaurants;
    private HashMap<String, String> selectors;

    private ListView searchedRestaurantsListView;
    private TextView alertTextView;
    private boolean availableRestaurants;
    private SavedChooseRestaurantListener listener;

    public SearchedRestaurantsFragment() {
        // Required empty public constructor
    }


    public static SearchedRestaurantsFragment newInstance(ArrayList<RestaurantModel> savedRestaurants, HashMap<String, String> selectors) {
        SearchedRestaurantsFragment fragment = new SearchedRestaurantsFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList("savedRestaurants", savedRestaurants);
        args.putSerializable("selectors", selectors);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            savedRestaurants = getArguments().getParcelableArrayList("savedRestaurants");
            selectors = (HashMap<String, String>) getArguments().getSerializable("selectors");
            requestSearchedRestaurantOperation(selectors);
            if (searchedRestaurants.size() == 0){
                availableRestaurants = false;
            } else{
                availableRestaurants = true;
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_searched_restaurants, container, false);
        if (availableRestaurants) {
            searchedRestaurantsListView = v.findViewById(R.id._searchedRestaurantsListView);
            searchedRestaurantsListView.setAdapter(new RandomRestaurantsAdapter(getContext(), searchedRestaurants));
            searchedRestaurantsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    listener.launchSearchedContent(searchedRestaurants.get(i));
                  //  listener.updateSaveListChoose_();
                }
            });
        }else{
            alertTextView = v.findViewById(R.id._alertUserTextVeiw);
            alertTextView.setText("Restaurant Search Unsuccessful, Possibly Too Specific For Your Given Area\nTry A Different Search");
            alertTextView.setGravity(Gravity.CENTER);
        }

        return v;
    }

    /**
     * add fixed key value pairs to the selectors for request
     * run network operation outside of main thread
     * @param selectors
     */
    public void requestSearchedRestaurantOperation(Map<String, String> selectors){
        selectors.put("latitude", "40.050900");
        selectors.put("longitude","-75.087883");
        selectors.put("limit","10");
        selectors.put("radius","16093"); // user should do this one

        Thread thread = new Thread(){
            @Override
            public void run() {
                RestaurantsFinder restaurantsFinder = new RestaurantsFinder(0, selectors);
                searchedRestaurants = restaurantsFinder.getQueried();
            }
        };
        thread.start();
        try {
            thread.join();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof  SavedChooseRestaurantListener){
            listener = (SavedChooseRestaurantListener) context;
        }else {
            throw new RuntimeException("Calling Activity must implement SavedChooseRestaurantListener");
        }
    }

    /**
     * @return current state of savedRestaurants list
     */
    public ArrayList<RestaurantModel> getSavedRestaurants(){
        return savedRestaurants;
    }

    public interface SavedChooseRestaurantListener{
        public void updateSaveListChoose_();
        public void launchSearchedContent(RestaurantModel restaurant);

    }




}