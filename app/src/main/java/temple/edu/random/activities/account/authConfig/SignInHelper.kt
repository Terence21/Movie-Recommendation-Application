package temple.edu.random.activities.account.authConfig

import android.app.Activity
import android.content.Intent
import android.util.Log
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import temple.edu.random.R
import temple.edu.random.activities.account.authConfig.AuthConfig.ACCOUNT

class SignInHelper(val activity: Activity) {

    private val auth by lazy { FirebaseAuth.getInstance() }
    private val gso: GoogleSignInOptions =
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(activity.getString(R.string.client_id)).requestEmail().build()
    private val client: GoogleSignInClient = GoogleSignIn.getClient(activity, gso)

    init {

    }


    inner class EmailHelper(private val userName: String, private val password: String) {
        private val account = AccountModel(userName, password)
        private var user: FirebaseUser? = null

        fun emailSignIn(): FirebaseUser? {
            var user: FirebaseUser? = null
            auth.createUserWithEmailAndPassword(account.userName, account.password)
                .addOnCompleteListener(activity) {
                    user = auth.currentUser
                }
            return user
        }

        fun emailRegister(): FirebaseUser? {
            auth.createUserWithEmailAndPassword(account.userName, account.password)
                .addOnCompleteListener(activity) {
                    if (it.isSuccessful){
                    }else {
                        Log.i(TAG, "emailRegister: failed to create user ${it.exception}")
                    }
                }
            return auth.currentUser
        }

        private fun handleEmail(
            loginType: ACCOUNT.LOGIN_TYPE,
            verificationType: ACCOUNT.VERIFICATION_TYPE
        ) {
            var user: FirebaseUser?
            var isSuccessful: Boolean
            when (loginType) {
                ACCOUNT.LOGIN_TYPE.EMAIL -> {
                    when (verificationType) {
                        ACCOUNT.VERIFICATION_TYPE.LOGIN -> {
                            auth.signInWithEmailAndPassword(account.userName, account.password)
                                .addOnCompleteListener(activity) {
                                    isSuccessful = it.isSuccessful
                                    user = auth.currentUser
                                }
                        }
                        ACCOUNT.VERIFICATION_TYPE.REGISTER -> {
                            auth.createUserWithEmailAndPassword(account.userName, account.password)
                                .addOnCompleteListener(activity) {
                                    isSuccessful = it.isSuccessful
                                    user = auth.currentUser
                                }
                        }
                    }
                }
                ACCOUNT.LOGIN_TYPE.GOOGLE -> {
                    val intent = client.signInIntent
                    activity.startActivityForResult(intent, RC_SIGN_IN)
                }
                else -> {
                    isSuccessful = false
                }
            }
        }
    }

    /**
     * 1. GOOGLE SIGN IN INTENT LAUNCHER
     * 2. RESPONSE TO TASK RESULT OF INTENT RESULT CODE
     * 3. AUTHENTICATE WITH CREDENTIALS PROVIDED AFTER SIGN IN SUCCESS
     */
    inner class GoogleHelper {

        fun signInIntent() {
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                //     .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()

            val googleSignInClient = GoogleSignIn.getClient(activity, gso)
            val signInIntent = googleSignInClient.signInIntent
            activity.startActivityForResult(signInIntent, RC_SIGN_IN)
        }

        fun googleSignInHelper(requestCode: Int, resultCode: Int, data: Intent?): FirebaseUser? {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            var user: FirebaseUser? = null
            try {
                val account = task.getResult(ApiException::class.java)
                user = if (account != null) firebaseAuthWithGoogle(account.id!!) else null
            } catch (e: Exception) {
                Log.i(TAG, "googleSignInHelper: FAILED ${e.localizedMessage}")
            }
            return user
        }

        private fun firebaseAuthWithGoogle(id: String): FirebaseUser? {
            var user: FirebaseUser? = null
            val credential = GoogleAuthProvider.getCredential(id, null)
            auth.signInWithCredential(credential)
                .addOnCompleteListener(activity) {
                    if (it.isSuccessful) {
                        user = auth.currentUser
                    } else {
                        Log.i(TAG, "firebaseAuthWithGoogle: failure ${it.exception}")
                    }
                }
            return user
        }
    }


    companion object {
        const val RC_SIGN_IN = 123
        private const val TAG = "GOOGLE-SIGN-IN-HELPER"
    }
}