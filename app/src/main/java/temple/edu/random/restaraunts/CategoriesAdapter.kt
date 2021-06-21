package temple.edu.random.restaraunts

import android.content.Context
import android.widget.BaseAdapter
import android.view.ViewGroup
import android.widget.TextView
import android.view.Gravity
import android.view.View
import java.util.ArrayList

class CategoriesAdapter(var context: Context, var categories: ArrayList<String>) : BaseAdapter() {
    override fun getCount(): Int {
        return categories.size
    }

    override fun getItem(i: Int): Any {
        return categories[i]
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
        textView.setLines(4)
        textView.gravity = Gravity.CENTER
        textView.text = categories[i].toUpperCase()
        return textView
    }
}