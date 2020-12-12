package temple.edu.zomato_randomizer.restaraunts;

import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import temple.edu.zomato_randomizer.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FindRestarauntsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FindRestarauntsFragment extends Fragment {

    private ListView optionsListView;
    private int currentOptionPosition;

    public FindRestarauntsFragment() {
        // Required empty public constructor
    }


    public static FindRestarauntsFragment newInstance() {
        FindRestarauntsFragment fragment = new FindRestarauntsFragment();
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
        View view = inflater.inflate(R.layout.fragment_find_restaraunts, container, false);

        optionsListView = view.findViewById(R.id._optionsListView);
        optionsListView.setAdapter(new PromptAdapter(getContext()));
        optionsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                currentOptionPosition = i; // if closed, this was last state selected
                displayUser(i); // Restaurants
            }
        });

        return view;
    }

    public void displayUser(int position){
        if (position == 0){ // bring up next fragment (random fragment)
            Toast.makeText(getContext(), "random chosen", Toast.LENGTH_LONG).show();
        }
        else if (position == 1){ // bring up next fragment (query fragment)
            Toast.makeText(getContext(), "query chosen", Toast.LENGTH_LONG).show();
        }
    }

    public int getCurrentOptionPosition(){
        return currentOptionPosition;
    }
}