package temple.edu.random.View

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import android.widget.TextView
import com.squareup.picasso.Picasso
import android.view.LayoutInflater
import android.widget.ImageView
import temple.edu.random.R

class RestaurantView(context: Context, attributeSet: AttributeSet?) : ConstraintLayout(context, attributeSet) {
    private val textView: TextView
    private val imageView: ImageView
    fun setTextView(text: String?) {
        textView.text = text
    }

    fun setImageView(image_uri: String?) {
        if (image_uri != null && image_uri.length > 0) {
            Picasso.with(context).load(image_uri).into(imageView)
        }
    }

    /**
     * must implement context and attribute set in constructor to inflate view to layout
     * @param context
     * @param attributeSet
     */
    init {
        val inflater = LayoutInflater.from(context)
        inflater.inflate(R.layout.restaurant_view, this, true)
        textView = findViewById(R.id._descriptiontextView)
        imageView = findViewById(R.id._imageView)
    }
}