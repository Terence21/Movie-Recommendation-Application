package temple.edu.random.activities.account

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import temple.edu.random.R
import temple.edu.random.databinding.FragmentSignInSplashBinding

class SignInSplash : Fragment(), View.OnClickListener {

    private lateinit var binding: FragmentSignInSplashBinding
    private lateinit var controller: NavController
    private val signInHelper by lazy { this.activity?.let { SignInHelper(it).Google() } }

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
        val view = inflater.inflate(R.layout.fragment_sign_in_splash, container, false)
        controller = view.findNavController()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSignInSplashBinding.inflate(layoutInflater)
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
                var user = signInHelper?.googleSignInHelper(requestCode, resultCode, data)
                if (user != null) {
                    controller.navigate(R.id.action_signInSplash_to_homeFragment)
                }
            }
        }
    }
}
