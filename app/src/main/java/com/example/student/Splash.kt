package com.example.student

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieDrawable

class Splash : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val appname=findViewById<TextView>(R.id.appname)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        val background=findViewById<ImageView>(R.id.spash_image1)
        val animate=AnimationUtils.loadAnimation(this,R.anim.slidelogo)
        val animate2=AnimationUtils.loadAnimation(this,R.anim.slidetext)
//        setupAnim()
        background.startAnimation(animate)
        appname.startAnimation(animate2)

        Handler().postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        },1500)



        // we used the postDelayed(Runnable, time) method
        // to send a message with a delayed time.
        //Normal Handler is deprecated , so we have to change the code little bit



//        Handler(Looper.getMainLooper()).postDelayed({
//            val intent = Intent(this, MainActivity::class.java)
//            startActivity(intent)
//            finish()
//        }, 3000) // 3000 is the delayed time in milliseconds.
    }
//    private fun setupAnim() {
//        val animate= findViewById<LottieAnimationView>(R.id.lottiesplash)
//        animate.setAnimation(R.raw.splash_lottie)
//        animate.repeatCount = LottieDrawable.INFINITE
//        animate.playAnimation()
//    }

}