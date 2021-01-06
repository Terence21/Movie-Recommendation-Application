package temple.edu.yelp_randomizer.fragments;

import android.content.Context;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import temple.edu.yelp_randomizer.R;
import temple.edu.yelp_randomizer.models.RestaurantModel;
import temple.edu.yelp_randomizer.restaraunts.RandomRestaurantsAdapter;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserRestarauntsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserRestarauntsFragment extends Fragment {

    ListView userRestaurantList;
    ArrayList<RestaurantModel> savedRestaurants;
    private FragmentContentListener listener;


    public UserRestarauntsFragment() {
        // Required empty public constructor
    }

    public static UserRestarauntsFragment newInstance(ArrayList<RestaurantModel> savedRestaurants) {
        UserRestarauntsFragment fragment = new UserRestarauntsFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList("restaurants", savedRestaurants);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            savedRestaurants = getArguments().getParcelableArrayList("restaurants");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_user_restaraunts, container, false);
        userRestaurantList = (ListView) v.findViewById(R.id._myRestaurantsListView);
        userRestaurantList.setAdapter(new RandomRestaurantsAdapter(getContext(), savedRestaurants));
        userRestaurantList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                listener.launchRandomContent(savedRestaurants.get(i));
            }
        });

        return v;
    }

    public void notifyDataSetChanged(){
        ((BaseAdapter) userRestaurantList.getAdapter()).notifyDataSetChanged();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof  FragmentContentListener){
            listener = (FragmentContentListener) context;
        } else{
            throw new RuntimeException("Calling Activity must implement FragmentChangeListener");
        }
    }

    public interface FragmentContentListener extends RandomRestaurantsFragment.SavedRestaurantListener {

    }


}