package temple.edu.random.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import temple.edu.random.R
import temple.edu.random.restaraunts.PromptAdapter
import android.widget.AdapterView.OnItemClickListener
import android.view.View
import android.widget.*
import androidx.fragment.app.Fragment
import java.lang.RuntimeException

/**
 * A simple [Fragment] subclass.
 * Use the [FindRestarauntsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FindRestarauntsFragment : Fragment() {
    private var optionsListView: ListView? = null

    /**
     * @return the current state of the option selected based on listView
     */
    var currentOptionPosition = 0
        private set
    private var chooserInterface: FindRestaurantsChooser? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_find_restaraunts, container, false)
        optionsListView = view.findViewById(R.id._optionsListView)
        optionsListView?.adapter = context?.let { PromptAdapter(it) }
        optionsListView?.onItemClickListener = OnItemClickListener { adapterView, view, i, l ->
            currentOptionPosition = i // if closed, this was last state selected
            displayUser(i) // display option selected in toast
        }
        return view
    }

    /**
     * display the correct fragment based on otpion selected
     * @param position position of option chosen in listView
     */
    fun displayUser(position: Int) {
        if (position == 0) { // bring up next fragment (random fragment)
            chooserInterface!!.findOptionFragment()
            Toast.makeText(context, "random chosen", Toast.LENGTH_LONG).show()
        } else if (position == 1) { // bring up next fragment (query fragment)
            chooserInterface!!.findOptionFragment()
            Toast.makeText(context, "query chosen", Toast.LENGTH_LONG).show()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        chooserInterface = if (context is FindRestaurantsChooser) {
            context
        } else {
            throw RuntimeException("context must have an instance of FindRestaurantsChooser implemented")
        }
    }

    // which options is selected (feeling bold? let me choose)
    interface FindRestaurantsChooser {
        fun findOptionFragment() //display the fragment of the selected choice
    }

    companion object {
        fun newInstance(): FindRestarauntsFragment {
            val fragment = FindRestarauntsFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}