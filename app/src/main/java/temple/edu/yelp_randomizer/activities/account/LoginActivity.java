package temple.edu.yelp_randomizer.activities.account;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.View;
import android.widget.Button; 
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import temple.edu.yelp_randomizer.activities.OptionsActivity;
import temple.edu.yelp_randomizer.R;

// restore reviewRecycleView
public class LoginActivity extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private TextView register;
    private Button submitButton;
    private FirebaseAuth mAuth;

    private SignInButton mGoogleSignInButton;
    private GoogleSignInClient mGoogleSignInClient;


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

        checkAlreadyLoggedIn();
        mAuth = FirebaseAuth.getInstance();

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (username.getText().toString().length() > 0 && password.getText().toString().length() > 0) {
                    mAuth.signInWithEmailAndPassword(username.getText().toString(), password.getText().toString()).addOnCompleteListener(LoginActivity.this,
                            new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        Bundle bundle = new Bundle();
                                        bundle.putParcelable("user", user);
                                        launchOptions(bundle);
                                    } else {
                                        Toast.makeText(LoginActivity.this, "Authentication Failed", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                } else{
                    Toast.makeText(LoginActivity.this, "Email or Password incomplete", Toast.LENGTH_SHORT).show();
                }


            }
        });
        

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchRegister();
            }
        });

        mGoogleSignInButton = findViewById(R.id._googleSignInButton);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        mGoogleSignInButton.setColorScheme(SignInButton.COLOR_DARK);
        mGoogleSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()){
                    case R.id._googleSignInButton:
                        googleSignIn();
                        break;
                }
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            Bundle bundle = new Bundle();
            bundle.putParcelable("user", currentUser);
            launchOptions(bundle);
        }

    }

    // checks last state with account
    private void checkAlreadyLoggedIn(){
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if (account != null){
            Bundle bundle = new Bundle();
            bundle.putParcelable("google", account);
            launchOptions(bundle);
        }
    }
    private void googleSignIn(){
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, 10);
    }

    /**
     * ---- HOW TO SET UP LOGIN WITH FIREBASE ----
     * set up support email for firebase console
     * get sha fingerprint from gradle => app => tasks => signingReport and add fingerprint to firebase
     * @param completedTask
     */
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask){
        try{
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            Bundle bundle = new Bundle();
            bundle.putParcelable("google", account);
            launchOptions(bundle);
        }catch(ApiException e){
            Log.w("handleSignInResultWarning", "handleSignInResult: " + e.getStatusCode() );
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 10){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    /**
     * if register is clicked, launch register activity
     */
    private void launchRegister() {
        Intent intent = new Intent(this, RegisterActivity.class);
        Bundle bundle = new Bundle();
        intent.putExtra("Bundle", bundle);
        startActivity(intent);

        // how to kill login activity
    }

    /**
     * kill all activities loaded before opening OptionsActivity
     */
    private void launchOptions(Bundle bundle){
        Intent intent = new Intent(this, OptionsActivity.class);
        intent.putExtra("Bundle", bundle);
        startActivity(intent); // find way to kill login activity after register
        finishAffinity(); // kills both login and register... if registered
    }


    // provisions to validate username or password


}