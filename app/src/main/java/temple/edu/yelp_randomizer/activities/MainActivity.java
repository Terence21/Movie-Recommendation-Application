package temple.edu.yelp_randomizer.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import temple.edu.yelp_randomizer.R;

public class MainActivity extends AppCompatActivity {

    boolean isSavedLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (isSavedLogin){
            //  auto load options activity
        }

        setContentView(R.layout.activity_login);


    }
}