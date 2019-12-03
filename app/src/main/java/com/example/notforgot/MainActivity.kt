package com.example.notforgot

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.text.Html
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ListAdapter
import android.widget.TextView
import androidx.core.view.isEmpty
import androidx.core.view.isNotEmpty
import androidx.core.view.isVisible
import androidx.core.view.size
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Boolean.FALSE
import java.lang.Boolean.TRUE


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadData()

        val actionBar = supportActionBar
        actionBar?.setTitle((Html.fromHtml("<font color=\"#ffffff\">" + getString(R.string.action_bar_text) + "</font>")))



        add_menu.setOnClickListener{
            val i = Intent(this, actAddUpdateNotes::class.java)
            i.putExtra("state", "AddOnly")
            startActivity(i)
        }


    }

    override fun onResume() {
        super.onResume()
        loadData()
    }

    fun loadData() {
        val db = DbManager(this)
        val adapter = listAdapter(db.select())
        listNotes.adapter = adapter

        try {
            val item = listNotes.getItemAtPosition(0)
        } catch (e: IndexOutOfBoundsException){
            placeholder.visibility = View.VISIBLE
            return
        }

        placeholder.visibility = View.GONE

        listNotes.setOnItemClickListener { parent, view, position, id ->
            val i = Intent(this,actAddUpdateNotes::class.java)
            i.putExtra("state", listNotes.getItemAtPosition(position).toString())
            startActivity(i)
        }

    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)

    }



    inner class listAdapter: BaseAdapter {

        var listItem = ArrayList<listItem>()


        constructor(listItem: ArrayList<listItem>) {
            this.listItem = listItem
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val view = layoutInflater.inflate(R.layout.custom_listview, null)
            val tvTitle: TextView = view!!.findViewById(R.id.tvTitle)
            val tvDateCreated: TextView = view!!.findViewById(R.id.tvDateCreatedView)

            tvTitle.text = listItem[position].title
            tvDateCreated.text = listItem[position].datetime

            return view
        }

        override fun getItem(position: Int): Any {
           return listItem[position].id
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getCount(): Int {
            return listItem.count()
        }

    }


}
