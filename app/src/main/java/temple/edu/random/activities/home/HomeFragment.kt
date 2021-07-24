package temple.edu.random.activities.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import temple.edu.random.R
import temple.edu.random.databinding.ActivityHomeBinding
import temple.edu.random.databinding.FragmentHomeBinding

class HomeFragment : Fragment(), View.OnClickListener {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var asParentController: NavController
    private lateinit var config: AppBarConfiguration

    init {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
        asParentController = binding.navHostFragment.findNavController()
        val config = AppBarConfiguration.Builder(asParentController.graph).build()
        // check that this casting is legal
        // maybe have another Activity like home activity that serves no purpose but to navHost?? (if this doesn't work)
        NavigationUI.setupActionBarWithNavController(activity as AppCompatActivity, asParentController, config)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)
        binding.fragmentHomeBottomNav.controller = asParentController

    }

    companion object {
        fun newInstance() =
            HomeFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }

    override fun onClick(v: View?) {

    }
}