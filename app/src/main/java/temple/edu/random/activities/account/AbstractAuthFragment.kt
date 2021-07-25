package temple.edu.random.activities.account

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import temple.edu.random.Global
import temple.edu.random.Global.Companion
import temple.edu.random.Global.Companion.safeNotNull

abstract class AbstractAuthFragment: Fragment() {

    protected lateinit var auth: FirebaseAuth
    protected lateinit var currentUser: FirebaseUser

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
}