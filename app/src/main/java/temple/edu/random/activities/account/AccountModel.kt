package temple.edu.random.activities.account

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import temple.edu.random.config.AuthConfig.ACCOUNT

data class AccountModel(val userName: String, val password: String)

class VerifyWithCredentials(val userName: String, val password: String, context: Context) {
    private val account: AccountModel = AccountModel(userName, password)

}


class SignInHelper(val activity: Activity) {

    private val auth = FirebaseAuth.getInstance()

    private val gso: GoogleSignInOptions =
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build()
    private val client: GoogleSignInClient = GoogleSignIn.getClient(activity, gso)
    private val gAccount = GoogleSignIn.getLastSignedInAccount(activity)

    fun alreadySignedIn() = gAccount == null
    inner class Email(val account: AccountModel) {
        fun emailSignIn(): FirebaseUser? {
            var user: FirebaseUser? = null
            auth.createUserWithEmailAndPassword(account.userName, account.password)
                .addOnCompleteListener(activity) { task ->
                    user = auth.currentUser
                }
            return user
        }

        fun emailRegister(): FirebaseUser? {
            var user: FirebaseUser? = null
            auth.createUserWithEmailAndPassword(account.userName, account.password)
                .addOnCompleteListener(activity) { task ->
                    user = auth.currentUser
                }
            return user
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

    inner class Google {
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

        fun firebaseAuthWithGoogle(id: String): FirebaseUser? {
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

        fun signInIntent() {
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                //     .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()

            var googleSignInClient = GoogleSignIn.getClient(activity, gso)
            val signInIntent = googleSignInClient.signInIntent
            activity.startActivityForResult(signInIntent, RC_SIGN_IN)
        }
    }


    companion object {
        const val RC_SIGN_IN = 123
        private const val TAG = "GOOGLE-SIGN-IN-HELPER"
    }
}


class AuthHelper {
    companion object {
        fun confirmHardPassword(password: String, password2: String) = password == password2
        fun countPasswordSize(password: String) = password.length
        fun isEmail(email: String) {
            // [anything num-letter]@[anything num-letter].com
            fun validEmailFormat(email: String) =
                email.matches(Regex("([a-zA-Z0-9])\\w+\\@([a-zA-Z0-9])\\w+.com"))

            // 5+ characters long
            // both numbers and characters
            fun validPasswordFormat(password: String) {

            }
        }
    }
}



