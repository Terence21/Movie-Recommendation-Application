package temple.edu.yelp_randomizer.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.*;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import temple.edu.yelp_randomizer.R;
import temple.edu.yelp_randomizer.View.ReviewsView;
import temple.edu.yelp_randomizer.models.RestaurantModel;
import temple.edu.yelp_randomizer.models.ReviewsModel;
import temple.edu.yelp_randomizer.restaraunts.RecycleAdapter;

import java.util.ArrayList;
import java.util.Objects;


/**
 * Learned:
 *  adjust pan in activity windowSoftInputMode
 *  styles... set parent
 *  set view constraint smaller... will adjust must reset constraints
 *  how to recyclerView
 *  getSupportActionBar() vs. getActionBar
 *      => setDisplayShowHomEnabled for showing home button on the left
 *  styles xml for defining styles (app theme is general)
 */

// NEED TO ADD SAVE PROPERLY IN MY RESTAURANTS FROM REVIEW BUTTON. its not saved to list, find proper callback
public class ReviewsActivity extends AppCompatActivity {

    private ArrayList<ReviewsModel> reviews;
    private EditText personalReviewEditText;
    private RecyclerView reviewsRecyclerView;
    private Button saveReviewButton;

    FirebaseFirestore fireStore;
    FirebaseUser currentUser;

    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews);

        fireStore = FirebaseFirestore.getInstance();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        reviews = getIntent().getParcelableArrayListExtra("reviews");

        layoutManager = new LinearLayoutManager(this);
        reviewsRecyclerView = findViewById(R.id._reviewsRecyclerView);
        RecycleAdapter recycleAdapter = new RecycleAdapter(this, reviews);
        reviewsRecyclerView.setLayoutManager(layoutManager);
        reviewsRecyclerView.setAdapter(recycleAdapter);

        personalReviewEditText = findViewById(R.id._personalReviewEditText);

        Toolbar toolbar = findViewById(R.id._reivewToolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        RestaurantModel restaurant = getIntent().getParcelableExtra("restaurant");
        if (restaurant.getReview().length() >0){
            personalReviewEditText.setText(restaurant.getReview());
        }

        ArrayList<RestaurantModel> savedRestaurants = getIntent().getParcelableArrayListExtra("savedRestaurants");
        saveReviewButton = findViewById(R.id._saveReviewButton);
        saveReviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                restaurant.setReview(personalReviewEditText.getText().toString());
                if (!OptionsActivity.containsRestaurant(savedRestaurants, restaurant.getId())) {
                    savedRestaurants.add(restaurant);

                } else {
                    int position  = getRestaurantPosition(savedRestaurants, restaurant.getId());
                    savedRestaurants.get(position).setReview(personalReviewEditText.getText().toString());
                }
                Intent responseIntent = new Intent();
                responseIntent.putExtra("savedRestaurants", savedRestaurants);
                setResult(Activity.RESULT_OK, responseIntent);

                fireStore.collection("users").document(currentUser.getUid()).collection("savedRestaurants")
                        .document(restaurant.getId()).set(restaurant, SetOptions.merge());

                // save restaurant
            }
        });
    }

        public int getRestaurantPosition(ArrayList<RestaurantModel> restaurants, String resId) {
            int counter = 0;
            for (RestaurantModel restaurant : restaurants) {
                if (restaurant.getId().equals(resId)) {
                    return counter;
                }
                counter++;
            }
            return -1;
        }

       // getSupportActionBar().setDisplayShowHomeEnabled(true);
        //getSupportActionBar().setDisplayShowTitleEnabled(false);








   /* @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id._reviewBackButton:
                finish();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }*/

    /*@Override  ==> don't need a back button menu because setDisplayHomeAsUpEnabled already has a dedicated item for going to parent activity
    public boolean onPrepareOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.refresh_menu, menu);
        return true;
    }*/
}