package temple.edu.random.activities.account.Register

import androidx.appcompat.app.AppCompatActivity
import android.widget.EditText
import android.os.Bundle
import temple.edu.random.R
import android.content.Intent
import android.view.View
import android.widget.Button
import temple.edu.random.activities.OptionsActivity

class RegisterActivity : AppCompatActivity() {
    var username: EditText? = null
    var password: EditText? = null
    var registerButton: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        username = findViewById(R.id.register_username_edit_text)
        password = findViewById(R.id._password)
        registerButton = findViewById(R.id.register_submit_button)
        registerButton?.setOnClickListener(View.OnClickListener { launchOptions() })
    }

    fun launchOptions() {
        val intent = Intent(this, OptionsActivity::class.java)
        val bundle = Bundle()
        intent.putExtra("Bundle", bundle)
        startActivity(intent) // find way to kill login activity after register
        finishAffinity() // kills both login and register... if registered
    }
}