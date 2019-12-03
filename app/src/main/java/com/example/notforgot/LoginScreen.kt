package com.example.notforgot

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import kotlinx.android.synthetic.main.activity_login_screen.*
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import java.util.regex.Pattern


class LoginScreen : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    var emailRegEx: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_screen)

        auth = FirebaseAuth.getInstance()

        password.transformationMethod = PasswordTransformationMethod.getInstance()

        register_button.setOnClickListener {
            val intent = Intent(this, RegisterScreen::class.java)
            startActivity(intent)
            finish()
        }

        emailRegEx = "^[A-Za-z0-9._%+\\-]+@[A-Za-z0-9.\\-]+\\.[A-Za-z]{2,4}$"
        login_button.setOnClickListener {
            val email = email_text.text.toString()
            val password = password.text.toString()
            val pattern = Pattern.compile(emailRegEx)
            val matcher = pattern.matcher(email_text.getText().toString())
            if (email.isEmpty() || password.isEmpty()) {

                Toast.makeText(
                    this@LoginScreen,
                    getString(R.string.empty_fields),
                    Toast.LENGTH_LONG
                ).show()
            } else if (!matcher.find()) {
                Toast.makeText(this@LoginScreen, getString(R.string.incorrect_email), Toast.LENGTH_LONG)
                    .show()
            } else {
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(
                                this@LoginScreen,
                                getString(R.string.successful_login),
                                Toast.LENGTH_LONG
                            ).show()
                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                            finish()

                        } else {
                            Toast.makeText(
                                this@LoginScreen,
                                getString(R.string.wrong_entry),
                                Toast.LENGTH_LONG
                            ).show()

                        }
                    }
            }
        }
    }
}

