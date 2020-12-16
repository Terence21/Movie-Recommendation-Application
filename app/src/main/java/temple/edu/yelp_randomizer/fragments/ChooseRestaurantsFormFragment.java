package temple.edu.yelp_randomizer.fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.material.textfield.TextInputEditText;
import temple.edu.yelp_randomizer.R;
import temple.edu.yelp_randomizer.restaraunts.SearchFoodGridViewAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
     *      - query (term)
     */
    TextView priceTextView;
    TextInputEditText foodSearchText;
    ImageButton addFoodButton;
    ImageButton searchChooseRestaurantsButton;
    SeekBar priceSeekBar;
    GridView searchFoodGridView;

    ArrayList<String> queriedFoodTypes;

    HashMap<String, String> selectors;

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
            selectors = new HashMap<>();
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
                if (view.equals(addFoodButton)){
                    if (queriedFoodTypes.size() == 0) {
                        queriedFoodTypes.add(foodSearchText.getText().toString());
                        selectors.put("term",foodSearchText.getText().toString());
                    } else{
                        queriedFoodTypes.remove(0);
                        queriedFoodTypes.add(foodSearchText.getText().toString());
                        selectors.remove("term");
                        selectors.put("term",foodSearchText.getText().toString());
                    }

                    Log.i("gridview", "gridView: " + foodSearchText.getText().toString());
                    ((SearchFoodGridViewAdapter)searchFoodGridView.getAdapter()).notifyDataSetChanged();
                    // update grid view to use new arraylist.. notify dataset change
                }else if (view.equals(searchChooseRestaurantsButton)){
                    listener.searchChooseRestaurants(selectors);
                    // send selectors to fragment
                }
            }
        };

        addFoodButton.setOnClickListener(imageButtonOCL);
        searchChooseRestaurantsButton.setOnClickListener(imageButtonOCL);

        priceSeekBar.setMax(4);
        priceSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                String price = getDollarString(seekBar.getMax(), i);
                priceTextView.setText(price);
                if (priceTextView.getText().toString().contains("$")) {
                    selectors.put("price", getDollarString(seekBar.getMax(), i));
                } else{
                    selectors.remove("price");
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { // https://stackoverflow.com/questions/13797807/android-how-to-add-intervals-texts-in-a-seekbar
               /* int currentProgress = seekBar.getProgress();

                int lastSelected = Math.round(seekBar.getProgress() / currentProgress) * currentProgress;
                int nextDot = lastSelected + currentProgress;
                int mid = lastSelected + (currentProgress / 2);

                if (seekBar.getProgress() > mid){
                    seekBar.setProgress(nextDot);
                }else{
                    seekBar.setProgress(lastSelected);
                }*/
            }
        });

        searchFoodGridView.setColumnWidth(3);
        searchFoodGridView.setAdapter(new SearchFoodGridViewAdapter(getContext(), queriedFoodTypes));


        return v;
    }

    private String getDollarString(int max, int selected){
        StringBuilder sb = new StringBuilder();
        int quotient = selected % 5;
        if (quotient == 0){
            return "NO PRICE PREFERENCE";
        }
        for (int i = 1; i<= quotient; i++){
            sb.append('$');
        }
        return sb.toString();
    }

    public HashMap<String, String> getSelectors(){
        return selectors;
    }

    @Override
    public void onAttach( Context context) {
        super.onAttach(context);
        if (context instanceof LaunchChooseRestaurantsListener){
            listener = (LaunchChooseRestaurantsListener) context;
        }else {
            throw new RuntimeException("Calling Activity must implement LaunchChooseRestaurantsListener");
        }
    }

    public interface LaunchChooseRestaurantsListener{
        public void searchChooseRestaurants(HashMap<String, String> selectors);
    }
}