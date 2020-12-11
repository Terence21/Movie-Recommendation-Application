package temple.edu.zomato_randomizer.account;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import temple.edu.zomato_randomizer.OptionsActivity;
import temple.edu.zomato_randomizer.R;

public class RegisterActivity extends AppCompatActivity {


    EditText username;
    EditText password;
    Button registerButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username = findViewById(R.id._username);
        password = findViewById(R.id._password);
        registerButton = findViewById(R.id._registerButton);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchOptions();
            }
        });
    }

    public void launchOptions(){
        Intent intent = new Intent(this, OptionsActivity.class);
        Bundle bundle = new Bundle();
        intent.putExtra("Bundle", bundle);
        startActivity(intent); // find way to kill login activity after register
        finish();
    }
}