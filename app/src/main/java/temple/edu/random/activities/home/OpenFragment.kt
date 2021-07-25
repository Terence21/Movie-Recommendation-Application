package temple.edu.random.activities.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import temple.edu.random.R
import temple.edu.random.databinding.FragmentOpenBinding


class OpenFragment : Fragment(), View.OnClickListener {
    private lateinit var binding: FragmentOpenBinding
    private lateinit var asParentController: NavController
    private lateinit var config: AppBarConfiguration
    private lateinit var controller: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
        // asParentController = binding.navHostFragment.findNavController()

/*        val navHostFragment = checkNotNull(fragmentManager?.findFragmentById(R.id.nav_host_fragment) as NavHostFragment)
        asParentController = navHostFragment.navController
        config = AppBarConfiguration.Builder(asParentController.graph).build()*/
        // check that this casting is legal
        // maybe have another Activity like home activity that serves no purpose but to navHost?? (if this doesn't work)
        // NavigationUI.setupActionBarWithNavController(activity as AppCompatActivity, asParentController, config)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentOpenBinding.inflate(inflater, container, false)
        controller = findNavController()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.fragmentHomeBottomNav.controller = binding.navHostFragment.findNavController()
     //   binding.fragmentHomeBottomNav.controller = asParentController
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            OpenFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }

    override fun onClick(v: View?) {

    }

}