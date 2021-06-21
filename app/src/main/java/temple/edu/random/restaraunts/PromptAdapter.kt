package temple.edu.random.restaraunts

import android.content.Context
import android.graphics.Color
import android.widget.BaseAdapter
import android.view.ViewGroup
import android.widget.TextView
import android.view.Gravity
import android.view.View
import java.util.ArrayList

class PromptAdapter(private val context: Context) : BaseAdapter() {
    private val options: ArrayList<String>
    override fun getCount(): Int {
        return options.size
    }

    override fun getItem(i: Int): Any {
        return options[i]
    }

    override fun getItemId(i: Int): Long {
        return 0
    }

    override fun getView(i: Int, view: View, viewGroup: ViewGroup): View {
        val option: TextView
        option = if (view == null) {
            TextView(context)
        } else {
            view as TextView
        }
        option.setLines(5)
        option.gravity = Gravity.CENTER
        option.setBackgroundColor(Color.LTGRAY)
        option.text = options[i]
        return option
    }

    init {
        options = ArrayList()
        options.add("FEELING BOLD")
        options.add("LET ME CHOOSE")
    }
}