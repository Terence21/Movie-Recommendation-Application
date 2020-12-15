package temple.edu.yelp_randomizer.restaraunts;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class PromptAdapter extends BaseAdapter {

    private ArrayList<String> options;
    private Context context;
    public PromptAdapter(Context context){
        this.context = context;
        options = new ArrayList<>();
        options.add("FEELING BOLD");
        options.add("LET ME CHOOSE");
    }
    @Override
    public int getCount() {
        return options.size();
    }

    @Override
    public Object getItem(int i) {
        return options.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        TextView option;

        if( view == null){
            option = new TextView(this.context);
        }
        else{
            option = (TextView) view;
        }

        option.setLines(5);
        option.setGravity(Gravity.CENTER);
        option.setBackgroundColor(Color.LTGRAY);
        option.setText(options.get(i));

        return option;
    }
}
