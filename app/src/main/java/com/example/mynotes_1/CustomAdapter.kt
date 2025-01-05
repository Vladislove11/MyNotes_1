package com.example.mynotes_1

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class CustomAdapter(private val notesAll : MutableList<Notes>, private val context: Context, val database: DBHelper) :
    RecyclerView.Adapter<CustomAdapter.NotesViewHolder>() {
    private var onNotesClickListener: OnNotesClickListener? = null

    interface OnNotesClickListener {
        fun onNotesClick(notes: Notes, position: Int)
    }

    class NotesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val numTV: TextView = itemView.findViewById(R.id.numTV)
        val notesTV: TextView = itemView.findViewById(R.id.notesTV)
        val checkBoxCB: CheckBox = itemView.findViewById(R.id.checkboxCB)
        val date: TextView = itemView.findViewById(R.id.dateTimeTV)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)
        return NotesViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        val notes = notesAll[position]
        holder.numTV.text = "${notes.id}"
        holder.notesTV.text = notes.notes
        holder.checkBoxCB.isChecked = false
        holder.date.text = notes.date
        holder.itemView.setOnClickListener {
            if (onNotesClickListener != null) {
                onNotesClickListener!!.onNotesClick(notes, position)
            }
        }

        if (notesAll[position].check == 0) {
            holder.checkBoxCB.isChecked = false
        } else {
            holder.checkBoxCB.isChecked = true
        }
        holder.checkBoxCB.setOnCheckedChangeListener(null)
        holder.checkBoxCB.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                notesAll[position].check = 1
            } else {
                notesAll[position].check = 0
            }
            //notifyItemChanged(position)
            database.updateNotes(notesAll[position])
            //Toast.makeText(context, "${holder.layoutPosition}", Toast.LENGTH_LONG).show()

            holder.layoutPosition

        }

    }

    private fun updateRecord(check: Int) {
        //val note = Notes(Integer.parseInt(updateId), updateName, updateEmail)
        //dataBase.updateNotes(note)
        Toast.makeText(context, "Данные обновлены", Toast.LENGTH_SHORT).show()
    }

    override fun getItemCount() = notesAll.size

    fun setOnNotesClickListener(onNotesClickListener: OnNotesClickListener) {
        this.onNotesClickListener = onNotesClickListener
    }

}