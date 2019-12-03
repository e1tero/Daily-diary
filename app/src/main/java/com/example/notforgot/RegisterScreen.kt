package com.example.notforgot

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import kotlinx.android.synthetic.main.activity_login_screen.*
import kotlinx.android.synthetic.main.activity_register_screen.*
import java.util.regex.Pattern

class RegisterScreen : AppCompatActivity() {

    var emailRegEx: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_screen)

        register_password.transformationMethod = PasswordTransformationMethod.getInstance()
        again_password.transformationMethod = PasswordTransformationMethod.getInstance()


        login_finish_button.setOnClickListener{
            val intent = Intent(this, LoginScreen::class.java)
            startActivity(intent)
        }

        emailRegEx = "^[A-Za-z0-9._%+\\-]+@[A-Za-z0-9.\\-]+\\.[A-Za-z]{2,4}$"
        register_finish_button.setOnClickListener {
            val password = register_password.text.toString()
            val second_password = again_password.text.toString()
            val email = mail_window.text.toString()
            val pattern = Pattern.compile(emailRegEx)
            val matcher = pattern.matcher(mail_window.getText().toString())
            if (email.isEmpty() || password.isEmpty() || second_password.isEmpty() || email.isEmpty()) {
                Toast.makeText(
                    this@RegisterScreen,
                    getString(R.string.empty_fields),
                    Toast.LENGTH_LONG
                ).show()
            } else if (!matcher.find()) {
                Toast.makeText(
                    this@RegisterScreen,
                    getString(R.string.incorrect_email),
                    Toast.LENGTH_LONG
                )
                    .show()
            }
                else if (second_password != password) {
              Toast.makeText(this@RegisterScreen, getString(R.string.mismatched_passwords),Toast.LENGTH_LONG).show()
            } else {
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener{
                    if (!it.isSuccessful) return@addOnCompleteListener

                        //
                        Log.d("Main", "Successfully created user with uid: ${it.result!!.user!!.uid}")
                    }

                Toast.makeText(this@RegisterScreen, getString(R.string.successful_registration),Toast.LENGTH_LONG).show()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }


        }
    }
}
