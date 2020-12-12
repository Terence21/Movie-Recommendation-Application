package temple.edu.zomato_randomizer.restaraunts;

import android.os.Bundle;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import temple.edu.zomato_randomizer.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserRestarauntsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserRestarauntsFragment extends Fragment {

    TextView findTextView;


    public UserRestarauntsFragment() {
        // Required empty public constructor
    }

    public static UserRestarauntsFragment newInstance() {
        UserRestarauntsFragment fragment = new UserRestarauntsFragment();
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

        View v = inflater.inflate(R.layout.fragment_user_restaraunts, container, false);
        findTextView = v.findViewById(R.id._prevTextView);

        return v;
    }
}