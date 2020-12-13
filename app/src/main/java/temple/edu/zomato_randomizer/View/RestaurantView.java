package temple.edu.zomato_randomizer.View;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.squareup.picasso.Picasso;
import org.w3c.dom.Text;
import temple.edu.zomato_randomizer.R;

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
        Picasso.with(context).load(image_uri).into(imageView);
    }
}
