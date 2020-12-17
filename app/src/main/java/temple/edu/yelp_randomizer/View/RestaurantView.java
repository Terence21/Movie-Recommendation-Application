package temple.edu.yelp_randomizer.View;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.squareup.picasso.Picasso;
import temple.edu.yelp_randomizer.R;

public class RestaurantView extends ConstraintLayout {

    private final TextView textView;
    private final ImageView imageView;

    Context context;

    public RestaurantView(Context context) {
        super(context);
        this.context = context;
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.restaurant_view,this,true);

        textView = findViewById(R.id._descriptiontextView);
        imageView = findViewById(R.id._imageView);


    }

    public void setTextView(String text){
        textView.setText(text);
    }

    public void setImageView(String image_uri){
        if (image_uri != null && image_uri.length() > 0) {
            Picasso.with(context).load(image_uri).into(imageView);
        }
    }
}
