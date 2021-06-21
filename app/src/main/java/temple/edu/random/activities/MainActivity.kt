package temple.edu.random.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import temple.edu.random.R

class MainActivity : AppCompatActivity() {
    var isSavedLogin = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (isSavedLogin) {
            //  auto load options activity
        }
        setContentView(R.layout.activity_login)
    }
}