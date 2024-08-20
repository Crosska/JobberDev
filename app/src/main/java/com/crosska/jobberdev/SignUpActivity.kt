package com.crosska.jobberdev

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.Locale


class SignUpActivity : AppCompatActivity() {

    var registrationAccessUsername = false
    var registrationAccessLogin = false
    var registrationAccessPassword = arrayOf(false, false)


    override fun onCreate(savedInstanceState: Bundle?) {
        setAppLocale(this, "English")
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sign_up)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.sign_up_activity_main_view)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        checkUsernameRule()
        checkLoginRule()
        checkPassword()
        /*val button = findViewById<Button>(R.id.fragment_button)
        val fragment = BlankFragment()
        button.setOnClickListener { // Показ фрагмента на экране
            supportFragmentManager.beginTransaction()
                .replace(R.id.sign_up_activity_main_view, fragment)
                .addToBackStack(fragment.toString())
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit()

        }*/

    }

    fun setAppLocale(context: Context, language: String) {
        val locale = Locale(language)
        Locale.setDefault(locale)
        val config = context.resources.configuration
        config.setLocale(locale)
        context.createConfigurationContext(config)
        context.resources.updateConfiguration(config, context.resources.displayMetrics)
    }

    private fun checkUsernameRule() {
        val usernameNotifier = findViewById<TextView>(R.id.sign_up_activity_textview_username)
        val usernameEdittext = findViewById<EditText>(R.id.sign_up_activity_edittext_username)
        usernameEdittext.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                if (usernameEdittext.text.isEmpty()) {
                    usernameNotifier.text =
                        getString(R.string.sign_up_activity_notifier_username_EMPTY)
                    usernameNotifier.setTextColor(getColor(R.color.error))
                    registrationAccessUsername = false
                } else if (usernameEdittext.text.length < 4) {
                    usernameNotifier.text =
                        getString(R.string.sign_up_activity_notifier_username_SHORT)
                    usernameNotifier.setTextColor(getColor(R.color.error))
                    registrationAccessUsername = false
                } else {

                    //
                    // Проверка на существующий username
                    //

                    usernameNotifier.text =
                        getString(R.string.sign_up_activity_notifier_username_GRANTED)
                    usernameNotifier.setTextColor(getColor(R.color.main_accent))
                    registrationAccessUsername = true
                }
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        })
    }

    private fun checkLoginRule() {
        val loginNotifier = findViewById<TextView>(R.id.sign_up_activity_textview_login)
        val loginEdittext = findViewById<EditText>(R.id.sign_up_activity_edittext_login)
        loginEdittext.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                if (loginEdittext.text.isEmpty()) {
                    loginNotifier.text = getString(R.string.sign_up_activity_notifier_login_EMPTY)
                    loginNotifier.setTextColor(getColor(R.color.error))
                    registrationAccessLogin = false
                } else if (loginEdittext.text.length < 4) {
                    loginNotifier.text = getString(R.string.sign_up_activity_notifier_login_SHORT)
                    loginNotifier.setTextColor(getColor(R.color.error))
                    registrationAccessLogin = false
                } else {

                    //
                    // Проверка на существующий login
                    //

                    loginNotifier.text =
                        getString(R.string.sign_up_activity_notifier_username_GRANTED)
                    loginNotifier.setTextColor(getColor(R.color.main_accent))
                    registrationAccessLogin = true
                }
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        })
    }

    fun checkPassword() {
        val passwordNotifier = findViewById<TextView>(R.id.sign_up_activity_textview_password)
        val passwordEdittext = findViewById<EditText>(R.id.sign_up_activity_edittext_password)
        val passwordEdittextRe = findViewById<EditText>(R.id.sign_up_activity_edittext_password_rp)

        passwordEdittext.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                if (passwordEdittext.text.isEmpty()) {
                    passwordNotifier.text =
                        getString(R.string.sign_up_activity_notifier_password_EMPTY)
                    passwordNotifier.setTextColor(getColor(R.color.error))
                    registrationAccessPassword[0] = false
                } else if (passwordEdittext.text.length < 8) {
                    passwordNotifier.text =
                        getString(R.string.sign_up_activity_notifier_password_SHORT)
                    passwordNotifier.setTextColor(getColor(R.color.error))
                    registrationAccessPassword[0] = false
                } else {
                    if (passwordEdittext.text.toString() == passwordEdittextRe.text.toString()) {
                        passwordNotifier.text =
                            getString(R.string.sign_up_activity_notifier_password_GRANTED)
                        passwordNotifier.setTextColor(getColor(R.color.main_accent))
                        registrationAccessPassword[0] = true
                        registrationAccessPassword[1] = true
                    } else {
                        passwordNotifier.text =
                            getString(R.string.sign_up_activity_notifier_password_GRANTED_MAIN)
                        passwordNotifier.setTextColor(getColor(R.color.error))
                        registrationAccessPassword[0] = true
                        registrationAccessPassword[1] = false
                    }
                }
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        })

        passwordEdittextRe.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                if (passwordEdittextRe.text.isEmpty() || (passwordEdittext.text.toString() != passwordEdittextRe.text.toString())
                ) {
                    passwordNotifier.text =
                        getString(R.string.sign_up_activity_notifier_password_NOTMATCHING)
                    passwordNotifier.setTextColor(getColor(R.color.error))
                    registrationAccessPassword[1] = false
                } else if (passwordEdittext.text.toString() == passwordEdittextRe.text.toString() && registrationAccessPassword[0]) {
                    passwordNotifier.text =
                        getString(R.string.sign_up_activity_notifier_password_GRANTED)
                    passwordNotifier.setTextColor(getColor(R.color.main_accent))
                    registrationAccessPassword[1] = true
                }
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        })
    }

    fun cancelRegistrationPressed(view: View) {
        this.finish()
    }

    fun confirmRegistrationPressed(view: View) {
        if (!registrationAccessLogin || !registrationAccessPassword[0] || !registrationAccessPassword[1] || !registrationAccessUsername) {
            val registerButton = findViewById<TextView>(R.id.sign_up_activity_button_register)
            val anim = AnimationUtils.loadAnimation(this, R.anim.sign_up_register_error)
            registerButton.startAnimation(anim)
        } else {
            Toast.makeText(this, "Success!", Toast.LENGTH_SHORT).show()
        }

    }

}