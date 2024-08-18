package com.crosska.jobberdev

import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SignInActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sign_in)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val prefs = this.getSharedPreferences("com.crosska.jobberdev", MODE_PRIVATE)
        if (!prefs.getBoolean("hasActive", false)) { // Нет активного аккаунта
            startUpAnimation()
        } else { // Уже есть активный аккаунт

//            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    fun startUpAnimation() {
        val appName = findViewById<TextView>(R.id.sign_in_activity_textview_appname)
        var anim = AnimationUtils.loadAnimation(this, R.anim.sign_in_appname)
        appName.startAnimation(anim)
        val logData = findViewById<CardView>(R.id.sign_in_activity_cardview_log_data)
        anim = AnimationUtils.loadAnimation(this, R.anim.sign_in_logdata)
        logData.startAnimation(anim)
        val humanIco = findViewById<CardView>(R.id.sign_in_activity_cardview_humanico)
        anim = AnimationUtils.loadAnimation(this, R.anim.sign_in_human_ico)
        humanIco.startAnimation(anim)
    }

    override fun onBackPressed() {
        this.finishAffinity()
        super.onBackPressed()
    }

}