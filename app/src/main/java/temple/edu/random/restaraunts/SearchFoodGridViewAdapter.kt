package temple.edu.random.restaraunts

import android.content.Context
import android.widget.BaseAdapter
import android.view.ViewGroup
import android.widget.TextView
import android.view.View
import java.util.ArrayList

class SearchFoodGridViewAdapter(var context: Context, var selectors: ArrayList<String>) : BaseAdapter() {
    override fun getCount(): Int {
        return selectors.size
    }

    override fun getItem(i: Int): Any {
        return selectors[i]
    }

    override fun getItemId(i: Int): Long {
        return 0
    }

    override fun getView(i: Int, view: View, viewGroup: ViewGroup): View {
        val textView: TextView
        textView = if (view != null) {
            view as TextView
        } else {
            TextView(context)
        }
        textView.setLines(5)
        textView.text = selectors[i].toUpperCase()
        return textView
    }
}