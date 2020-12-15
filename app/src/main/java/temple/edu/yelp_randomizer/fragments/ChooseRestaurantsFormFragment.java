package temple.edu.yelp_randomizer.fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import temple.edu.yelp_randomizer.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChooseRestaurantsFormFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChooseRestaurantsFormFragment extends Fragment {

    /**
     * qualifiers:
     *      - distance (miles)
     *      - price (slider)
     *      - open (checkbox)
     *      - query (term)
     */
    public ChooseRestaurantsFormFragment() {
        // Required empty public constructor
    }

    public static ChooseRestaurantsFormFragment newInstance() {
        ChooseRestaurantsFormFragment fragment = new ChooseRestaurantsFormFragment();
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
        return inflater.inflate(R.layout.fragment_choose_restaurants, container, false);
    }
}