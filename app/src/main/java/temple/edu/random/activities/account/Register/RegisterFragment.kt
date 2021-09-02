package temple.edu.random.activities.account.Register

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import temple.edu.random.globals.Global.Companion.TAG
import temple.edu.random.R
import temple.edu.random.activities.account.AbstractAuthFragment
import temple.edu.random.activities.account.authConfig.SignInHelper
import temple.edu.random.databinding.FragmentRegisterBinding

class RegisterFragment : AbstractAuthFragment(), View.OnClickListener {
    private lateinit var binding: FragmentRegisterBinding
    private lateinit var username: String
    private lateinit var password: String
    private lateinit var controller: NavController

    /*  private val signInHelper by lazy {
        this.activity?.let {
            SignInHelper(it).EmailHelper(
                username,
                password
            )
        }
    }*/

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
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        controller = findNavController()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.registerSubmitButton.setOnClickListener(this)
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
        username = binding.registerUsernameEditText.text.toString()
        password = binding.registerPasswordEditText.text.toString()
        if (v != null) {
            when (v.id) {
                R.id.register_submit_button -> {
                    val signInHelper =
                        activity?.let { SignInHelper(it).EmailHelper(username, password) }
                    Log.i(TAG, "onClick: register $username , $password")
                    val user = signInHelper?.emailRegister()
                    user?.let {
                        Log.i(TAG, "onClick: navigating to home")
                        controller.navigate(R.id.action_registerFragment_to_openFragment)
                    }

                }
            }
        }
    }
}