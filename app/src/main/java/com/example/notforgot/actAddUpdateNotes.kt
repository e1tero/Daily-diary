package com.example.notforgot

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isEmpty
import androidx.core.view.isNotEmpty
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.act_add_update_notes.*
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class actAddUpdateNotes : AppCompatActivity() {

    lateinit var state: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_add_update_notes)

        val actionBar = supportActionBar
        actionBar?.setTitle((Html.fromHtml("<font color=\"#ffffff\">" + "MyPets" + "</font>")))

        state = intent.getStringExtra("state")

        if (state == "AddOnly") {
            //
        } else {
            val db = DbManager(this)
            try {
                val arr: ArrayList<String> = ArrayList(db.selectForUpdate(state.toInt()))
                etTitle.setText(arr[0])
                etDesc.setText(arr[1])

            } catch (e: Exception){
                Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
            }
        }

        save_menu.setOnClickListener {
            if (etTitle.text.toString() == "" || etDesc.text.toString() == "") {
                Toast.makeText(
                    this,
                    "Заполните все поля",
                    Toast.LENGTH_SHORT
                ).show()
            } else {

                val db = DbManager(this)
                try {
                    val sdf = SimpleDateFormat("yyyy/MM/dd-hh:mm:ss")
                    val dtNow = sdf.format(Date())
                    db.insert(etTitle.text.toString(), etDesc.text.toString(), dtNow)
                    db.insert(etTitle.text.toString(), etDesc.text.toString(), dtNow)
                    Toast.makeText(this, "Insert new notes", Toast.LENGTH_SHORT).show()
                    finish()
                } catch (e: Exception) {
                    Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_add_update, menu)
        if (state == "AddOnly") {
            val item: MenuItem = menu!!.findItem(R.id.delete_menu)
            item.isVisible = false
        }

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item != null) {
            if (item.itemId == R.id.go_back && state == "AddOnly") {
                if (etTitle.text.toString() == "" || etDesc.text.toString() == "") {
                    Toast.makeText(
                        this,
                        "Заполните все поля",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    val db = DbManager(this)
                    try {
                        val sdf = SimpleDateFormat("yyyy/MM/dd-hh:mm:ss")
                        val dtNow = sdf.format(Date())
                        db.insert(etTitle.text.toString(), etDesc.text.toString(), dtNow)
                        Toast.makeText(this, "Новое дело добавлено", Toast.LENGTH_SHORT).show()
                        finish()
                    } catch (e: Exception) {
                        Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
                    }
                }
            } else if (item.itemId == R.id.go_back && state != "AddOnly") {
                if (etTitle.text.toString() == "" || etDesc.text.toString() == "") {
                    Toast.makeText(this, "Что-то пошло не так", Toast.LENGTH_LONG).show()
                } else {
                    val db = DbManager(this)
                    try {
                        db.update(state.toInt(), etTitle.text.toString(), etDesc.text.toString())
                        Toast.makeText(this, "Все апдейтнулось как надо", Toast.LENGTH_LONG).show()
                        finish()
                    } catch (e: Exception) {
                        Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
                    }
                }
            } else if (item.itemId == R.id.delete_menu) {
                AlertDialog.Builder(this)
                    .setTitle("Ты точно хочешь этого!?")
                    .setMessage("Ты хочешь удалить эту запись, ты серьезно?")
                    .setIcon(R.drawable.ic_delete)
                    .setNegativeButton("Delete") { dialog, which ->
                        val db = DbManager(this)
                        try {
                            db.delete(state.toInt())
                            Toast.makeText(this, "Пока пока заметочка", Toast.LENGTH_LONG).show()
                            finish()
                        } catch (e: Exception) {
                            Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
                        }
                    }
                    .setPositiveButton("Cancel") { dialog, which ->
                        dialog.dismiss()
                    }
                    .create()
                    .show()
            }
        }

        return super.onOptionsItemSelected(item)
    }
}
