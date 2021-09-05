package temple.edu.random.activities.account.SignInSplash

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import temple.edu.random.globals.Global.Companion.TAG
import temple.edu.random.R
import temple.edu.random.activities.account.authConfig.SignInHelper
import temple.edu.random.databinding.FragmentSignInSplashBinding

class SignInSplash : Fragment(), View.OnClickListener {

    private lateinit var binding: FragmentSignInSplashBinding
    private lateinit var controller: NavController
    private val signInHelper by lazy { this.activity?.let { SignInHelper(it).GoogleHelper() } }

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
        /* binding = FragmentSignInSplashBinding.inflate(inflater, container, false)
         val navHostFragment =
             activity?.supportFragmentManager?.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
         controller = navHostFragment.navController*/
        binding = FragmentSignInSplashBinding.inflate(inflater, container, false)
        controller = findNavController()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val auth = FirebaseAuth.getInstance()
        auth.let { controller.navigate(R.id.action_signInSplash_to_openFragment) }
        binding.EmailSignInButton.setOnClickListener(this)
        binding.GoogleSignInButton.setOnClickListener(this)
        binding.splashRegisterButton.setOnClickListener(this)
    }

    companion object {
        fun newInstance() =
            SignInSplash().apply {
                arguments = Bundle().apply {

                }
            }
    }

    override fun onClick(v: View?) {

        v?.let {
            when (v.id) {
                R.id.EmailSignInButton -> {
                    controller.navigate(R.id.action_signInSplash_to_loginFragment)
                }
                R.id.GoogleSignInButton -> {
                    signInHelper?.signInIntent()
                }
                R.id.splashRegisterButton -> {
                    controller.navigate(R.id.action_signInSplash_to_registerFragment)
                }
                else -> Toast.makeText(this.context, "FAILED TO LOAD", Toast.LENGTH_SHORT)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            SignInHelper.RC_SIGN_IN -> {
                val user = signInHelper?.googleSignInHelper(requestCode, resultCode, data)
                Log.i(TAG, "onActivityResult: $user")
                if (user != null) {
                    controller.navigate(R.id.action_signInSplash_to_openFragment)
                }
            }
        }
    }
}
