package temple.edu.yelp_randomizer.activities.account;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import temple.edu.yelp_randomizer.activities.OptionsActivity;
import temple.edu.yelp_randomizer.R;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {


    EditText username;
    EditText password;
    Button registerButton;

    private FirebaseAuth mAuth;
    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        username = findViewById(R.id._username);
        password = findViewById(R.id._password);
        registerButton = findViewById(R.id._registerButton);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.createUserWithEmailAndPassword(username.getText().toString(), password.getText().toString()).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    assert user != null;
                                    fireCreateUserObject(user);
                                    Bundle bundle = new Bundle();
                                    bundle.putParcelable("user", user);
                                    launchOptions(bundle);
                                }
                                else{
                                    Log.w("Fail", "registerFail: " + task.getException().getMessage() );
                                    Toast.makeText(RegisterActivity.this, "Authentication Failed", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }
        });
    }

    private void fireCreateUserObject(FirebaseUser user){
        firestore = FirebaseFirestore.getInstance();
        Map<String, String> map = new HashMap<>();

      /*  firestore.collection("users")
                .document(user.getUid())
                .collection("savedRestaurants");*/
        firestore.collection("users")
                .document(user.getUid()).set(map);
    }

    public void launchOptions(Bundle bundle){
        Intent intent = new Intent(this, OptionsActivity.class);
        intent.putExtra("Bundle", bundle);
        startActivity(intent); // find way to kill login activity after register
        finishAffinity(); // kills both login and register... if registered
    }


}