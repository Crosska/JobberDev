package com.crosska.jobberdev

import android.annotation.SuppressLint
import android.content.Intent
import android.media.Image
import android.os.Bundle
import android.os.CountDownTimer
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.findViewTreeViewModelStoreOwner
import java.util.Timer

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    var exiting = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        loadingAnimation()
        val countdown = object : CountDownTimer(1200, 100) {
            override fun onTick(millisUntilFinished: Long) {
                if (exiting) {
                    cancel()
                }
            }

            override fun onFinish() {
                toastTest()
            }
        }
        countdown.start()

    }

    fun toastTest() {
//        Toast.makeText(this, "Timer Out", Toast.LENGTH_SHORT).show()
        startActivity(Intent(this, SignInActivity::class.java))
    }

    fun loadingAnimation() {
        val imageLogo = findViewById<CardView>(R.id.splash_activity_card_logo)
        val anim = AnimationUtils.loadAnimation(this, R.anim.splash_logo)
        imageLogo.startAnimation(anim)
    }

    override fun onBackPressed() {
        exiting = true
        super.onBackPressed()
    }

}