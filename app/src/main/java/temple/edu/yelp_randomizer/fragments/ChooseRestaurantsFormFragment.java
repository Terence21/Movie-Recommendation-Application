package temple.edu.yelp_randomizer.fragments;

import android.os.Bundle;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.material.textfield.TextInputEditText;
import temple.edu.yelp_randomizer.R;
import temple.edu.yelp_randomizer.restaraunts.SearchFoodGridViewAdapter;

import java.util.ArrayList;

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
    TextView priceTextView;
    TextInputEditText foodSearchText;
    ImageButton addFoodButton;
    ImageButton searchChooseRestaurantsButton;
    SeekBar priceSeekBar;
    GridView searchFoodGridView;
    ArrayList<String> queriedFoodTypes;


    LaunchChooseRestaurantsListener listener;
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
            queriedFoodTypes = new ArrayList<>();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_choose_restaurants, container, false);
        foodSearchText = v.findViewById(R.id._foodSearchText);
        addFoodButton = v.findViewById(R.id._addFoodButton);
        searchChooseRestaurantsButton = v.findViewById(R.id._searchChooseRestaurantsButton);
        priceSeekBar = v.findViewById(R.id._priceSeekBar);
        searchFoodGridView = v.findViewById(R.id._searchFoodGridView);
        priceTextView = v.findViewById(R.id._priceTextView);

        View.OnClickListener imageButtonOCL = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view == addFoodButton){
                    queriedFoodTypes.add(foodSearchText.getText().toString());
                    ((SearchFoodGridViewAdapter)searchFoodGridView.getAdapter()).notifyDataSetChanged();
                    // update grid view to use new arraylist.. notify dataset change
                }else if (view == searchChooseRestaurantsButton){
                    listener.searchChooseRestaurants(queriedFoodTypes, priceTextView.toString());
                }
            }
        };

        priceSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                priceTextView.setText(getDollarString(seekBar.getMax() , i));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { // https://stackoverflow.com/questions/13797807/android-how-to-add-intervals-texts-in-a-seekbar
                int currentProgress = seekBar.getMax() / 5;

                int lastSelected = Math.round(seekBar.getProgress() / currentProgress) * currentProgress;
                int nextDot = lastSelected + currentProgress;
                int mid = lastSelected + (currentProgress / 2);

                if (seekBar.getProgress() > mid){
                    seekBar.setProgress(nextDot);
                }else{
                    seekBar.setProgress(lastSelected);
                }
            }
        });

        searchFoodGridView.setAdapter(new SearchFoodGridViewAdapter(getContext(), queriedFoodTypes));


        return v;
    }

    private String getDollarString(int max, int selected){
        StringBuilder sb = new StringBuilder();
        int quotient = max / selected;
        for (int i = 0; i<= quotient; i++){
            sb.append('$');
        }
        return sb.toString();
    }





    public interface LaunchChooseRestaurantsListener{
        public void searchChooseRestaurants(ArrayList<String> selectors, String price);
    }
}