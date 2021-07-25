package temple.edu.random.activities.account.Register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import temple.edu.random.R
import temple.edu.random.activities.account.AbstractAuthFragment
import temple.edu.random.activities.account.authConfig.SignInHelper
import temple.edu.random.databinding.FragmentRegisterBinding

class RegisterFragment : AbstractAuthFragment(), View.OnClickListener {
    private lateinit var binding: FragmentRegisterBinding
    private lateinit var username: String
    private lateinit var password: String
    private lateinit var controller: NavController

    private val signInHelper by lazy {
        this.activity?.let {
            SignInHelper(it).EmailHelper(
                username,
                password
            )
        }
    }

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
       /* val navHostFragment =
            activity?.supportFragmentManager?.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        controller = navHostFragment.navController*/
        controller = findNavController()
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRegisterBinding.inflate(layoutInflater)
        binding.submitButton.setOnClickListener(this)
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            RegisterFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }

    override fun onClick(v: View?) {
       /* username = binding.username.text.toString()
        password = binding.password.text.toString()*/
        if (v != null) {
            when (v.id) {
                R.id.submitButton -> {
                    val user = signInHelper?.emailRegister()
                    user?.let { controller.navigate(R.id.action_registerFragment_to_homeFragment) }
                }
            }
        }
    }
}