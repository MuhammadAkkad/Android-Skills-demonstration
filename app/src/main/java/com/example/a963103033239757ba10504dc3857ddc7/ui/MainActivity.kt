package com.example.a963103033239757ba10504dc3857ddc7.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.room.Room
import com.example.a963103033239757ba10504dc3857ddc7.R
import com.example.a963103033239757ba10504dc3857ddc7.data.db.StationDatabase
import com.example.a963103033239757ba10504dc3857ddc7.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_stations, R.id.navigation_favorite
            )
        )
        val navController = findNavController(R.id.nav_host_fragment)
        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.navView.setupWithNavController(navController)
    }
}


