package com.example.a963103033239757ba10504dc3857ddc7.ui.station

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.a963103033239757ba10504dc3857ddc7.R
import com.example.a963103033239757ba10504dc3857ddc7.databinding.ActivityStationBinding

class StationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStationBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setupNavController()
    }

    private fun setupNavController() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        binding.navView.setupWithNavController(navController)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            // hide bottom nav bar on shipFragment
            if (destination.id == R.id.shipFragment) {
                binding.navView.visibility = View.INVISIBLE
            } else {
                binding.navView.visibility = View.VISIBLE
            }
        }
        /*val appBarConfiguration = AppBarConfiguration(
    setOf(
        R.id.navigation_stations, R.id.navigation_favorite
    )
)
setupActionBarWithNavController(navController, appBarConfiguration)*/
    }
}


