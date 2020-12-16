package temple.edu.yelp_randomizer.restaraunts;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CategoriesAdapter extends BaseAdapter {

    Context context;
    ArrayList<String> categories;

    public CategoriesAdapter(Context context, ArrayList<String> categories){
        this.context = context;
        this.categories = categories;
    }
    @Override
    public int getCount() {
        return categories.size();
    }

    @Override
    public Object getItem(int i) {
        return categories.get(i);
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
        }
        else{
            textView = new TextView(context);
        }
        textView.setLines(4);
        textView.setGravity(Gravity.CENTER);
        textView.setText(categories.get(i).toUpperCase());

        return textView;
    }
}
