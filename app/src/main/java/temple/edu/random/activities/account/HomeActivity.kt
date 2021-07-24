package temple.edu.random.activities.account

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import temple.edu.random.R
import temple.edu.random.databinding.ActivityHomeBinding


class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var controller: NavController
    private lateinit var config: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val controller = binding.navHostFragment.findNavController()
        val config = AppBarConfiguration.Builder(controller.graph).build()
        NavigationUI.setupActionBarWithNavController(this, controller, config)
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(controller, config) || super.onSupportNavigateUp()
    }
}