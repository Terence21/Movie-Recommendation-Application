package temple.edu.yelp_randomizer.restaraunts;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class SearchFoodGridViewAdapter extends BaseAdapter {
    ArrayList<String> selectors;
    Context context;
    public SearchFoodGridViewAdapter(Context context, ArrayList<String> selectors){
        this.context = context;
        this.selectors = selectors;
    }

    @Override
    public int getCount() {
        return selectors.size();
    }

    @Override
    public Object getItem(int i) {
        return selectors.get(i);
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
        textView.setText(selectors.get(i).toUpperCase());
        return textView;
    }
}
