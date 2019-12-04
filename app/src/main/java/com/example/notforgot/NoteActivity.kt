package com.example.notforgot

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html

class NoteActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note)

        val actionBar = supportActionBar
        actionBar?.setTitle((Html.fromHtml("<font color=\"#ffffff\">" + "Полезные советы и заметки" + "</font>")))
    }

}
