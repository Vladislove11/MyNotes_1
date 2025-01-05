package com.example.mynotes_1

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.Date

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerViewRV : RecyclerView
    private lateinit var toolbar : Toolbar
    private lateinit var buttonAddBTN: Button
    private lateinit var addNotes: EditText
    val dataBase = DBHelper(this)

    private var notes = mutableListOf(
        Notes(1, "Первая заметка", 0, "19.12.20024 18:03"),
        Notes(2, "Вторая заметка", 1, "21.12.20024 18:43")
    )

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        title = ""

        buttonAddBTN = findViewById(R.id.buttonAddBTN)
        addNotes = findViewById(R.id.addNotesET)

        recyclerViewRV = findViewById(R.id.recycleViewRV)
        recyclerViewRV.layoutManager = LinearLayoutManager(this)

        viewDataAdapter()

        buttonAddBTN.setOnClickListener {
            val id =  getListIbNotes().toInt()
            val note = addNotes.text.toString()
            val check = 0
            val date = getCurrentDateTime()
            notes.add(Notes(id, note, check, date))
            dataBase.addNote(Notes(id, note, check, date))
            viewDataAdapter()
            addNotes.text.clear()
        } 

    }

    private fun viewDataAdapter(){
        notes = dataBase.readNotes()
        val adapter = CustomAdapter(notes, this, dataBase)
        recyclerViewRV.adapter = adapter

        //adapter.notifyItemChanged(0)
    }

    private fun getListIbNotes() : String {
        if (notes.size == 0 ) return "1"

        return (notes.last().id.toInt() + 1).toString()
    }

    @SuppressLint("SimpleDateFormat")
    private fun getCurrentDateTime() : String{
        val sdf = SimpleDateFormat("dd.MM.yyyy HH:mm")
        return sdf.format(Date())
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.context_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.exitMenuMain ->{
                finishAffinity()
                Toast.makeText(this,"Приложение завершено", Toast.LENGTH_LONG).show()
            }
        }
        return super.onOptionsItemSelected(item)
    }

}