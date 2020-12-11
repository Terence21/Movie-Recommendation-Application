package temple.edu.zomato_randomizer.account;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import temple.edu.zomato_randomizer.R;

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

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchOptions();
            }
        });
    }

    public void launchOptions() {
        Intent intent = new Intent(this, RegisterActivity.class);
        Bundle bundle = new Bundle();
        intent.putExtra("Bundle", bundle);
        startActivity(intent);
        // how to kill login activity

    }


    // provisions to validate username or password


}