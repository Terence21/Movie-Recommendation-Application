package temple.edu.random.activities.account.Login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import temple.edu.random.globals.Global.Companion.safeNotNull
import temple.edu.random.R
import temple.edu.random.activities.account.AbstractAuthFragment
import temple.edu.random.activities.account.authConfig.SignInHelper
import temple.edu.random.databinding.FragmentLoginBinding


class LoginFragment : AbstractAuthFragment(), View.OnClickListener {
    private lateinit var binding: FragmentLoginBinding
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
        auth = FirebaseAuth.getInstance()
    }

    override fun onStart() {
        super.onStart()
        safeNotNull(auth) {
            currentUser = auth.currentUser!!
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       /* val navHostFragment =
            activity?.supportFragmentManager?.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        controller = navHostFragment.navController */
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        controller = findNavController()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.loginSubmitButton.setOnClickListener(this)
        binding.registerButton.setOnClickListener(this)

    }

    companion object {
        @JvmStatic
        fun newInstance() =
            LoginFragment().apply {
                arguments = Bundle().apply {

                }
            }

        const val RC_SIGN_IN = 123

    }

    override fun onClick(v: View?) {
        if (v != null) {
            /*username = binding.username.text.toString()
            password = binding.password.text.toString()*/
            when (v.id) {
                R.id.EmailSignInButton -> {
                    val user = signInHelper?.emailSignIn()
                    user?.let {
                        controller.navigate(R.id.action_loginFragment_to_openFragment)
                    }
                }
                R.id.splashRegisterButton -> {
                    controller.navigate(R.id.action_loginFragment_to_registerFragment)
                }

            }
        }
    }
}