package temple.edu.yelp_randomizer.View;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.squareup.picasso.Picasso;
import temple.edu.yelp_randomizer.R;

import java.util.HashMap;

public class DetailsView extends ConstraintLayout {
    private ImageView firstImageView;
    private ImageView secondImageView;
    private ImageView thirdImageView;
    private TextView sundayTextView;
    private TextView mondayTextView;
    private TextView tuesdayTextView;
    private TextView wednesdayTextView;
    private TextView thursdayTextView;
    private TextView fridayTextView;
    private TextView saturdayTextView;

    private Context context;

    public DetailsView( Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.details_view, this, true);

        this.firstImageView = findViewById(R.id._firstDetailsImageView);
        this.secondImageView = findViewById(R.id._secondDetailsImageView);
        this.thirdImageView = findViewById(R.id._thirdDetailsImageView);

        this.sundayTextView = findViewById(R.id._sundayTextView);
        this.mondayTextView = findViewById(R.id._mondayTextView);
        this.tuesdayTextView = findViewById(R.id._tuesdayTextView);
        this.wednesdayTextView = findViewById(R.id._wednesdayTextView);
        this.thursdayTextView = findViewById(R.id._thursdayTextView);
        this.fridayTextView = findViewById(R.id._fridayTextView);
        this.saturdayTextView = findViewById(R.id._saturdayTextView);
    }

    public void setFirstImageView(String imageURL) {
        if (imageURL.length() > 1) {
            Picasso.with(context).load(imageURL).into(firstImageView);
        }
    }

    public void setSecondImageView(String imageURL) {
        if (imageURL.length() > 1) {
            Picasso.with(context).load(imageURL).into(secondImageView);
        }
    }

    public void setThirdImageView(String imageURL) {
        if (imageURL.length() > 1) {
            Picasso.with(context).load(imageURL).into(thirdImageView);
        }
    }

    public void setSundayTextView(String text) {
        this.sundayTextView.setText(text);
    }

    public void setMondayTextView(String text) {
        this.mondayTextView.setText(text);
    }

    public void setTuesdayTextView(String text) {
        this.tuesdayTextView.setText(text);
    }

    public void setWednesdayTextView(String text) {
        this.wednesdayTextView.setText(text);
    }

    public void setThursdayTextView(String text) {
        this.thursdayTextView.setText(text);
    }

    public void setFridayTextView(String text) {
        this.fridayTextView.setText(text);
    }

    public void setSaturdayTextView(String text) {
        this.saturdayTextView.setText(text);
    }


}
