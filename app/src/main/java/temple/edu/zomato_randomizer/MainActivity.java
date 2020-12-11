package temple.edu.zomato_randomizer;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

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