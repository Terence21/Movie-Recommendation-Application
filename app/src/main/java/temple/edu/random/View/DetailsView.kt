package temple.edu.random.View

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import android.widget.TextView
import com.squareup.picasso.Picasso
import android.view.LayoutInflater
import android.widget.ImageView
import temple.edu.random.R

class DetailsView( context: Context, attrs: AttributeSet?) : ConstraintLayout(context, attrs) {
    private val firstImageView: ImageView
    private val secondImageView: ImageView
    private val thirdImageView: ImageView
    private val sundayTextView: TextView
    private val mondayTextView: TextView
    private val tuesdayTextView: TextView
    private val wednesdayTextView: TextView
    private val thursdayTextView: TextView
    private val fridayTextView: TextView
    private val saturdayTextView: TextView
    fun setFirstImageView(imageURL: String) {
        if (imageURL.length > 1) {
            Picasso.with(context).load(imageURL).into(firstImageView)
        }
    }

    fun setSecondImageView(imageURL: String) {
        if (imageURL.length > 1) {
            Picasso.with(context).load(imageURL).into(secondImageView)
        }
    }

    fun setThirdImageView(imageURL: String) {
        if (imageURL.length > 1) {
            Picasso.with(context).load(imageURL).into(thirdImageView)
        }
    }

    fun setSundayTextView(text: String?) {
        sundayTextView.text = text
    }

    fun setMondayTextView(text: String?) {
        mondayTextView.text = text
    }

    fun setTuesdayTextView(text: String?) {
        tuesdayTextView.text = text
    }

    fun setWednesdayTextView(text: String?) {
        wednesdayTextView.text = text
    }

    fun setThursdayTextView(text: String?) {
        thursdayTextView.text = text
    }

    fun setFridayTextView(text: String?) {
        fridayTextView.text = text
    }

    fun setSaturdayTextView(text: String?) {
        saturdayTextView.text = text
    }

    init {
        val inflater = LayoutInflater.from(context)
        inflater.inflate(R.layout.details_view, this, true)
        firstImageView = findViewById(R.id._firstDetailsImageView)
        secondImageView = findViewById(R.id._secondDetailsImageView)
        thirdImageView = findViewById(R.id._thirdDetailsImageView)
        sundayTextView = findViewById(R.id._sundayTextView)
        mondayTextView = findViewById(R.id._mondayTextView)
        tuesdayTextView = findViewById(R.id._tuesdayTextView)
        wednesdayTextView = findViewById(R.id._wednesdayTextView)
        thursdayTextView = findViewById(R.id._thursdayTextView)
        fridayTextView = findViewById(R.id._fridayTextView)
        saturdayTextView = findViewById(R.id._saturdayTextView)
    }
}