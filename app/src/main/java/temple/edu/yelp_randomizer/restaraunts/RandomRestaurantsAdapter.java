package temple.edu.yelp_randomizer.restaraunts;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import temple.edu.yelp_randomizer.View.RestaurantView;
import temple.edu.yelp_randomizer.models.RestaurantModel;


import java.util.ArrayList;

public class RandomRestaurantsAdapter extends BaseAdapter {
    private ArrayList<RestaurantModel> restaurants;
    Context context;

    public RandomRestaurantsAdapter(Context context, ArrayList<RestaurantModel> restaurants){
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
            restaurantView = new RestaurantView(context, null);

        }
        String text = restaurants.get(i).getName() + "\n\n" +
                      restaurants.get(i).getPhone() + "\n" +
                       restaurants.get(i).getLocation();
        //textView.setText(text);
        restaurantView.setTextView(text);
        restaurantView.setImageView(restaurants.get(i).getImage());

        return restaurantView;
    }


}
