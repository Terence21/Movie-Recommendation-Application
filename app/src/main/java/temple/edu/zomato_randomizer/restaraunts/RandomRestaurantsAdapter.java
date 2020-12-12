package temple.edu.zomato_randomizer.restaraunts;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


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

        if (view != null){
            textView = (TextView) view;
        }else{
            textView = new TextView(context);
        }
        textView.setLines(5);
        textView.setText("RestaurantName: " + restaurants.get(i).getName());
        textView.setText("PhoneNumber: " + restaurants.get(i).getPhone());
        textView.setText("Location: " + restaurants.get(i).getLocation());

        return textView;
    }
}
