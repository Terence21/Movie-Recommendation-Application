package temple.edu.random.activities.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import temple.edu.random.R
import temple.edu.random.databinding.FragmentOpenBinding


class OpenFragment : Fragment(), View.OnClickListener {

    private lateinit var binding: FragmentOpenBinding
    private lateinit var controller: NavController

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
        binding = FragmentOpenBinding.inflate(inflater, container, false)
        controller = findNavController()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val nestedNavHostFragment =
            childFragmentManager.findFragmentById(R.id.nested_nav_host) as? NavHostFragment
        nestedNavHostFragment?.navController?.let {
            controller = it
            binding.fragmentHomeBottomNav.controller = controller
            binding.fragmentHomeBottomNav.setCurrentMenuItem(R.id.bottom_nav_menu_landing)
        }
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