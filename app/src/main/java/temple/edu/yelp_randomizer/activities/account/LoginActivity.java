package temple.edu.yelp_randomizer.activities.account;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;
import android.widget.Button; 
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import temple.edu.yelp_randomizer.activities.OptionsActivity;
import temple.edu.yelp_randomizer.R;

// restore reviewRecycleView
public class LoginActivity extends AppCompatActivity {

    EditText username;
    EditText password;
    TextView register;
    Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id._username);
        password = findViewById(R.id._password);
        register = findViewById(R.id._register);
        register.setPaintFlags(register.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        register.setTextColor(Color.BLUE);
        submitButton = findViewById(R.id._registerButton);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchOptions();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchRegister();
            }
        });
    }

    /**
     * if register is clicked, launch register activity
     */
    public void launchRegister() {
        Intent intent = new Intent(this, RegisterActivity.class);
        Bundle bundle = new Bundle();
        intent.putExtra("Bundle", bundle);
        startActivity(intent);

        // how to kill login activity
    }

    /**
     * kill all activities loaded before opening OptionsActivity
     */
    public void launchOptions(){
        Intent intent = new Intent(this, OptionsActivity.class);
        Bundle bundle = new Bundle();
        intent.putExtra("Bundle", bundle);
        startActivity(intent); // find way to kill login activity after register
        finishAffinity(); // kills both login and register... if registered
    }


    // provisions to validate username or password


}