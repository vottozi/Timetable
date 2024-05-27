package com.example.timetable

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.timetable.data.UserPreferences
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {
    private lateinit var userPreferences: UserPreferences
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        userPreferences = UserPreferences.getInstance(this)

        // Setup NavController
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController

        val bottomnav = findViewById<BottomNavigationView>(R.id.bottomNavigationView)

        lifecycleScope.launch {
            val role = userPreferences.role.first()
            val navGraph = navController.navInflater.inflate(R.navigation.navigation)
            navGraph.setStartDestination(if (role == "admin") R.id.adminFragment else R.id.scheduleFragment)
            navController.graph = navGraph

            // Set up BottomNavigationView
            bottomnav.setOnNavigationItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.scheduleFragment -> {
                        if (role == "admin") {
                            navController.navigate(R.id.adminFragment)
                        } else {
                            navController.navigate(R.id.scheduleFragment)
                        }
                        true
                    }
                    R.id.profileFragment -> {
                        navController.navigate(R.id.profileFragment)
                        true
                    }
                    else -> false
                }
            }
        }
    }
}
