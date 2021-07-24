package temple.edu.random.activities.account

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import temple.edu.random.R

class HomeFragment : Fragment(), View.OnClickListener {
    private val controller by lazy { findNavController() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            HomeFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }

    override fun onClick(v: View?) {
    }
}