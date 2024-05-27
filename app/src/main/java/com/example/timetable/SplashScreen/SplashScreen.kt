package com.example.timetable.SplashScreen

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import com.example.timetable.MainActivity
import com.example.timetable.R
import com.example.timetable.data.UserPreferences
import com.example.timetable.ui.login.AuthActivity
import com.example.timetable.ui.startNewActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        lifecycleScope.launch {
            delay(5000) // Delay for 5 seconds
            navigateToNextScreen()
        }
    }

    private fun navigateToNextScreen() {
        val userPreferences = UserPreferences.getInstance(this)
        userPreferences.authToken.asLiveData().observe(this) { authToken ->
            val activity = if (authToken == null) AuthActivity::class.java else MainActivity::class.java
            startNewActivity(activity)
            finish() // Finish this activity after starting the new one
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // Remove observers to prevent memory leaks
        UserPreferences(this).authToken.asLiveData().removeObservers(this)
    }
}
