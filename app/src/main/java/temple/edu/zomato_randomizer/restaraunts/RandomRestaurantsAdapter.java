package temple.edu.zomato_randomizer.restaraunts;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import temple.edu.zomato_randomizer.View.RestaurantView;


import java.util.ArrayList;

public class RandomRestaurantsAdapter extends BaseAdapter {
    private ArrayList<Holder> restaurants;
    Context context;

    public RandomRestaurantsAdapter(Context context, ArrayList<Holder> restaurants){
        this.restaurants = restaurants;
        this.context = context;
    }
    @Override
    public int getCount() {
        return restaurants.size();
    }

    @Override
    public Object getItem(int i) {
        return restaurants.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        TextView textView;
        RestaurantView restaurantView;

        if (view != null){
           // textView = (TextView) view;
            restaurantView = (RestaurantView) view;
        }else{
            //textView = new TextView(context);
            restaurantView = new RestaurantView(context);

        }
        String text = "RestaurantName: " + restaurants.get(i).getName() + "\n" +
                      "PhoneNumber: " + restaurants.get(i).getPhone() + "\n" +
                      "Location: " + restaurants.get(i).getLocation();
        //textView.setText(text);
        restaurantView.setTextView(text);
        restaurantView.setImageView(restaurants.get(i).getImage());
        restaurantView.setImageButton("https://lh3.googleusercontent.com/proxy/Pa10wXGse8T9gL4SnGuDj00vlRSJD_5P835sV4DGn-OBByF4_HEiULRZab0k_S3H15rRXUCn5ONO-LeK");
        return restaurantView;
    }


}
