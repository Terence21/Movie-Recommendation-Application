package temple.edu.random.activities

import androidx.appcompat.app.AppCompatActivity
import temple.edu.random.models.ReviewsModel
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import temple.edu.random.R
import androidx.recyclerview.widget.LinearLayoutManager
import temple.edu.random.restaraunts.RecycleAdapter
import java.util.*

/**
 * Learned:
 * adjust pan in activity windowSoftInputMode
 * styles... set parent
 * set view constraint smaller... will adjust must reset constraints
 * how to recyclerView
 * getSupportActionBar() vs. getActionBar
 * => setDisplayShowHomEnabled for showing home button on the left
 * styles xml for defining styles (app theme is general)
 */
class ReviewsActivity : AppCompatActivity() {
    private lateinit var reviews: ArrayList<ReviewsModel>
    private var personalReviewEditText: EditText? = null
    private lateinit var reviewsRecyclerView: RecyclerView
    private var layoutManager: RecyclerView.LayoutManager? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reviews)
        reviews = intent.getParcelableArrayListExtra("reviews")!!
        layoutManager = LinearLayoutManager(this)
        reviewsRecyclerView = findViewById(R.id._reviewsRecyclerView)
        val recycleAdapter = RecycleAdapter(this, reviews)
        reviewsRecyclerView.layoutManager = layoutManager
        reviewsRecyclerView.adapter = recycleAdapter
        personalReviewEditText = findViewById(R.id._personalReviewEditText)
        val toolbar = findViewById<Toolbar>(R.id._reivewToolbar)
        setSupportActionBar(toolbar)
        checkNotNull(supportActionBar).setDisplayHomeAsUpEnabled(true)

    }
}