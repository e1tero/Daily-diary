package com.example.notforgot

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle


class LaunchScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launch_screen)


        val intent = Intent(this, LoginScreen::class.java)
        startActivity(intent)
        finish()

    }
}
