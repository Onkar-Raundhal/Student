package com.example.student

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class Splash : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // Hide the status bar to display the splash screen in full screen
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        // Initialize views
        val appName = findViewById<TextView>(R.id.appname)
        val background = findViewById<ImageView>(R.id.spash_image1)

        // Apply animation to views
        val animateLogo = AnimationUtils.loadAnimation(this, R.anim.slidelogo)
        val animateText = AnimationUtils.loadAnimation(this, R.anim.slidetext)
        background.startAnimation(animateLogo)
        appName.startAnimation(animateText)

        // Delayed navigation to the main activity
        Handler().postDelayed({
            navigateToMainActivity()
        }, 1500)
    }

    private fun navigateToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}
