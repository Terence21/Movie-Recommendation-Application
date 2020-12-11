package temple.edu.zomato_randomizer.restaraunts;

import android.os.Bundle;
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
        return inflater.inflate(R.layout.fragment_find_restaraunts, container, false);
    }
}