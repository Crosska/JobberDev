package com.crosska.jobberdev

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import java.util.Locale

class SignInActivity : AppCompatActivity() {

    var timer: CountDownTimer? = null
    var errorShowing = false

    override fun onCreate(savedInstanceState: Bundle?) {
        setAppLocale(this, "English")
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sign_in)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.sign_in_activity_main_view)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val prefs = this.getSharedPreferences("com.crosska.jobberdev", MODE_PRIVATE)
        if (!prefs.getBoolean("hasActive", false)) { // Нет активного аккаунта
            Toast.makeText(this, "Нет аккаунта", Toast.LENGTH_SHORT).show()
            findViewById<CardView>(R.id.sign_in_activity_cardview_error).visibility = View.INVISIBLE
            startUpAnimation()
        } else { // Уже есть активный аккаунт
            Toast.makeText(this, "Есть аккаунт", Toast.LENGTH_SHORT).show()
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

    fun signInButtonPressed(view: View) {
        hideKeyboard()
        val loginEditText = findViewById<EditText>(R.id.sign_in_activity_edittext_login)
        val passwordEditText = findViewById<EditText>(R.id.sign_in_activity_edittext_password)
        if (loginEditText.text.isNotEmpty() && passwordEditText.text.isNotEmpty()) {
            // Всё ввели
        } else if (loginEditText.text.isNotEmpty() && passwordEditText.text.isEmpty()) {
            // Забыли ввести пароль
            showError(getString(R.string.sign_in_activity_error_no_password), this)
        } else if (loginEditText.text.isEmpty() && passwordEditText.text.isNotEmpty()) {
            // Забыли ввести логин
            showError(getString(R.string.sign_in_activity_error_no_login), this)
        } else {
            // Не ввели ничего
            showError(getString(R.string.sign_in_activity_error_no_data), this)
        }
    }

    fun setAppLocale(context: Context, language: String) {
        val locale = Locale(language)
        Locale.setDefault(locale)
        val config = context.resources.configuration
        config.setLocale(locale)
        context.createConfigurationContext(config)
        context.resources.updateConfiguration(config, context.resources.displayMetrics)
    }

    private fun showError(error: String, context: Context) {
        val errorCardView = findViewById<CardView>(R.id.sign_in_activity_cardview_error)
        if (errorShowing) {
            timer?.cancel()
            val animHide = AnimationUtils.loadAnimation(context, R.anim.sign_in_error_hide)
            animHide.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(arg0: Animation) {
                    errorCardView.visibility = View.VISIBLE
                }

                override fun onAnimationRepeat(arg0: Animation) {
                }

                override fun onAnimationEnd(arg0: Animation) {
                    errorCardView.visibility = View.INVISIBLE
                    showErrorAction(errorCardView, context, error)
                }
            })
            errorCardView.startAnimation(animHide)
        } else {
            showErrorAction(errorCardView, context, error)
        }
    }

    fun showErrorAction(errorCardView: CardView, context: Context, error: String) {
        errorShowing = true
        val anim_show = AnimationUtils.loadAnimation(this, R.anim.sign_in_error_show)
        timer = object : CountDownTimer(3000, 500) {
            override fun onTick(millisUntilFinished: Long) {}

            override fun onFinish() {
                val animHide = AnimationUtils.loadAnimation(context, R.anim.sign_in_error_hide)
                animHide.setAnimationListener(object : Animation.AnimationListener {
                    override fun onAnimationStart(arg0: Animation) {
                        errorCardView.visibility = View.VISIBLE
                    }

                    override fun onAnimationRepeat(arg0: Animation) {
                    }

                    override fun onAnimationEnd(arg0: Animation) {
                        errorCardView.visibility = View.INVISIBLE
                        errorShowing = false
                    }
                })
                errorCardView.startAnimation(animHide)
            }
        }

        findViewById<TextView>(R.id.sign_in_activity_textview_error).text = error
        anim_show.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(arg0: Animation) {
                errorCardView.visibility = View.VISIBLE
            }

            override fun onAnimationRepeat(arg0: Animation) {
            }

            override fun onAnimationEnd(arg0: Animation) {
                (timer as CountDownTimer).start()
            }
        })
        errorCardView.startAnimation(anim_show)
    }

    fun signUpButtonPressed(view: View) {
        val intent = Intent(this@SignInActivity, SignUpActivity::class.java)
        startActivity(intent)
    }

    fun forgotPasswordButtonPressed(view: View) {

    }

    fun Fragment.hideKeyboard() {
        view?.let { activity?.hideKeyboard(it) }
    }

    fun Activity.hideKeyboard() {
        hideKeyboard(currentFocus ?: View(this))
    }

    fun Context.hideKeyboard(view: View) {
        val inputMethodManager =
            getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

}