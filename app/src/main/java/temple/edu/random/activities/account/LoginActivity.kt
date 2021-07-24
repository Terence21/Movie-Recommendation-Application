package temple.edu.random.activities.account

import androidx.appcompat.app.AppCompatActivity
import android.widget.EditText
import android.widget.TextView
import android.os.Bundle
import temple.edu.random.R
import android.content.Intent
import android.graphics.Color
import android.graphics.Paint
import android.view.View
import android.widget.Button
import temple.edu.random.activities.OptionsActivity

// restore reviewRecycleView
class LoginActivity : AppCompatActivity() {
    var username: EditText? = null
    var password: EditText? = null
    var register: TextView? = null
    var submitButton: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        username = findViewById(R.id._username)
        password = findViewById(R.id._password)
        register = findViewById(R.id.registerButton)
        register?.paintFlags = register!!.paintFlags or Paint.UNDERLINE_TEXT_FLAG
        register?.setTextColor(Color.BLUE)
        submitButton = findViewById(R.id.submitButton)
        submitButton?.setOnClickListener(View.OnClickListener { launchOptions() })
        register?.setOnClickListener(View.OnClickListener { launchRegister() })
    }

    /**
     * if register is clicked, launch register activity
     */
    private fun launchRegister() {
        val intent = Intent(this, RegisterActivity::class.java)
        val bundle = Bundle()
        intent.putExtra("Bundle", bundle)
        startActivity(intent)

        // how to kill login activity
    }

    /**
     * kill all activities loaded before opening OptionsActivity
     */
    fun launchOptions() {
        val intent = Intent(this, OptionsActivity::class.java)
        val bundle = Bundle()
        intent.putExtra("Bundle", bundle)
        startActivity(intent) // find way to kill login activity after register
        finishAffinity() // kills both login and register... if registered
    } // provisions to validate username or password
}