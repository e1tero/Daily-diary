package com.example.notforgot

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html

class FoodActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food)

        val actionBar = supportActionBar
        actionBar?.setTitle((Html.fromHtml("<font color=\"#ffffff\">" + "Запасы еды" + "</font>")))
    }
}
