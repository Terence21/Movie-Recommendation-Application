package temple.edu.yelp_randomizer.fragments;

import android.content.Context;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import temple.edu.yelp_randomizer.R;
import temple.edu.yelp_randomizer.models.RestaurantHolder;
import temple.edu.yelp_randomizer.restaraunts.RandomRestaurantsAdapter;
import temple.edu.yelp_randomizer.restaraunts.RestaurantsFinder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * list view of fragments
 */
public class SearchedRestaurantsFragment extends Fragment {

    ArrayList<RestaurantHolder> savedRestaurants;
    ArrayList<RestaurantHolder> searchedRestaurants;
    HashMap<String, String> selectors;

    ListView searchedRestaurantsListView;

    SavedChooseRestaurantListener listener;

    public SearchedRestaurantsFragment() {
        // Required empty public constructor
    }


    public static SearchedRestaurantsFragment newInstance(ArrayList<RestaurantHolder> savedRestaurants, HashMap<String, String> selectors) {
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
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_searched_restaurants, container, false);
        searchedRestaurantsListView = v.findViewById(R.id._searchedRestaurantsListView);
        searchedRestaurantsListView.setAdapter(new RandomRestaurantsAdapter(getContext(), searchedRestaurants));
        searchedRestaurantsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                savedRestaurants.add(((RestaurantHolder)searchedRestaurantsListView.getAdapter().getItem(i)));
                listener.updateSaveListChoose_();
            }
        });

        return v;
    }

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

    public ArrayList<RestaurantHolder> getSavedRestaurants(){
        return savedRestaurants;
    }

    public interface SavedChooseRestaurantListener{
        public void updateSaveListChoose_();
    }




}