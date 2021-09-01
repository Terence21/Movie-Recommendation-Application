package temple.edu.random.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import temple.edu.random.R
import android.widget.AdapterView.OnItemClickListener
import android.util.Log
import android.widget.TextView
import android.view.View
import com.google.android.material.textfield.TextInputEditText
import android.widget.ImageButton
import android.widget.SeekBar
import android.widget.GridView
import temple.edu.random.restaraunts.CategoriesAdapter
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.fragment.app.Fragment
import java.lang.RuntimeException
import java.lang.StringBuilder
import java.util.ArrayList
import java.util.HashMap

/**
 * A simple [Fragment] subclass.
 * Use the [ChooseRestaurantsFormFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ChooseRestaurantsFormFragment : Fragment() {
    /**
     * qualifiers:
     * - distance (miles)
     * - price (slider)
     * - query (term)
     */
    var priceTextView: TextView? = null
    var foodSearchText: TextInputEditText? = null
    var addFoodButton: ImageButton? = null
    var searchChooseRestaurantsButton: ImageButton? = null
    var priceSeekBar: SeekBar? = null
    var categoriesGridView: GridView? = null
    var queriedFoodTypes: ArrayList<String>? = null
    var categories: ArrayList<String>? = null
    var selectors: HashMap<String, String>? = null
    var listener: LaunchChooseRestaurantsListener? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            queriedFoodTypes = ArrayList()
            selectors = HashMap()
            categories = requireArguments().getStringArrayList("categories")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_choose_restaurants, container, false)
        foodSearchText = v.findViewById(R.id._foodSearchText)
        addFoodButton = v.findViewById(R.id._addFoodButton)
        searchChooseRestaurantsButton = v.findViewById(R.id._searchChooseRestaurantsButton)
        priceSeekBar = v.findViewById(R.id._priceSeekBar)
        categoriesGridView = v.findViewById(R.id._categoriesGridView)
        priceTextView = v.findViewById(R.id._priceTextView)
        val imageButtonOCL = View.OnClickListener { view ->
            if (view == addFoodButton) {
                if (queriedFoodTypes!!.size == 0) {
                    queriedFoodTypes!!.add(foodSearchText?.getText().toString())
                    selectors!!["term"] = "Restaurants " + foodSearchText?.text.toString()
                } else {
                    queriedFoodTypes!!.removeAt(0)
                    queriedFoodTypes!!.add(foodSearchText?.text.toString())
                    selectors!!.remove("term")
                    selectors!!["term"] = "Restaurants " + foodSearchText?.text.toString()
                }
                Log.i("gridview", "gridView: " + foodSearchText?.text.toString())
                (categoriesGridView?.adapter as CategoriesAdapter).notifyDataSetChanged()
                // update grid view to use new arraylist.. notify dataset change
            } else if (view == searchChooseRestaurantsButton) {
                selectors!!["term"] = "Restaurants " + foodSearchText?.text
                listener!!.searchChooseRestaurants(selectors)
                // send selectors to fragment
            }
        }
        addFoodButton?.setOnClickListener(imageButtonOCL)
        searchChooseRestaurantsButton?.setOnClickListener(imageButtonOCL)
        priceSeekBar?.max = 4
        priceSeekBar?.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                val price = getDollarString(seekBar.max, i)
                priceTextView?.text = price
                if (priceTextView?.text.toString().contains("$")) {
                    selectors!!["price"] = i.toString()
                } else {
                    selectors!!.remove("price")
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) { // https://stackoverflow.com/questions/13797807/android-how-to-add-intervals-texts-in-a-seekbar
                /* int currentProgress = seekBar.getProgress();

                int lastSelected = Math.round(seekBar.getProgress() / currentProgress) * currentProgress;
                int nextDot = lastSelected + currentProgress;
                int mid = lastSelected + (currentProgress / 2);

                if (seekBar.getProgress() > mid){
                    seekBar.setProgress(nextDot);
                }else{
                    seekBar.setProgress(lastSelected);
                }*/
            }
        })
        categoriesGridView?.setColumnWidth(3)
        categoriesGridView?.setAdapter(categories?.let { context?.let { it1 -> CategoriesAdapter(it1, it) } })
        categoriesGridView?.setOnItemClickListener(OnItemClickListener { adapterView, view, i, l ->
            foodSearchText?.setText((view as TextView).text.toString())
            selectors!!["term"] = "Restaurants " + foodSearchText?.getText()
        })
        return v
    }

    /**
     * @param max highest item value on scale
     * @param selected the selected item on scale
     * @return for number selected, return same amount in '$'
     */
    private fun getDollarString(max: Int, selected: Int): String {
        val sb = StringBuilder()
        val quotient = selected % 10
        if (quotient == 0) {
            return "NO PRICE PREFERENCE"
        }
        for (i in 1..quotient) {
            sb.append('$')
        }
        return sb.toString()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = if (context is LaunchChooseRestaurantsListener) {
            context
        } else {
            throw RuntimeException("Calling Activity must implement LaunchChooseRestaurantsListener")
        }
    }

    interface LaunchChooseRestaurantsListener {
        fun searchChooseRestaurants(selectors: HashMap<String, String>?)
    }

    companion object {
        fun newInstance(categories: ArrayList<String>?): ChooseRestaurantsFormFragment {
            val fragment = ChooseRestaurantsFormFragment()
            val args = Bundle()
            args.putStringArrayList("categories", categories)
            fragment.arguments = args
            return fragment
        }
    }
}