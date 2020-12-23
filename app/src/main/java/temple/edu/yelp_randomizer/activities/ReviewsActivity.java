package temple.edu.yelp_randomizer.activities;

import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import temple.edu.yelp_randomizer.R;
import temple.edu.yelp_randomizer.View.ReviewsView;
import temple.edu.yelp_randomizer.models.ReviewsModel;
import temple.edu.yelp_randomizer.restaraunts.RecycleAdapter;

import java.util.ArrayList;


/**
 * Learned:
 *  adjust pan in activity windowSoftInputMode
 *  styles... set parent
 *  set view constraint smaller... will adjust must reset constraints
 *  how to recyclerView
 */
public class ReviewsActivity extends AppCompatActivity {

    private ArrayList<ReviewsModel> reviews;
    private EditText personalReviewEditText;
    private RecyclerView reviewsRecyclerView;

    private RecyclerView.LayoutManager layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews);

        reviews = getIntent().getParcelableArrayListExtra("reviews");

        layoutManager = new LinearLayoutManager(this);
        reviewsRecyclerView= findViewById(R.id._reviewsRecyclerView);
        RecycleAdapter recycleAdapter = new RecycleAdapter(this, reviews);
        reviewsRecyclerView.setLayoutManager(layoutManager);
        reviewsRecyclerView.setAdapter(recycleAdapter);

        personalReviewEditText = findViewById(R.id._personalReviewEditText);


    }
}