package com.example.mynotes_1

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object{
        private val DATABASE_NAME = "NOTES_DATABASE"
        private val DATABASE_VERSION = 1
        val TABLE_NAME = "user_table"
        val KEY_ID = "id"
        val KEY_NOTES = "notes"
        val KEY_CHECK = "checkBox"
        val KEY_DATETIME= "date"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val USER_TABLE = ("CREATE TABLE " + TABLE_NAME + " (" +
                KEY_ID + " INTEGER PRIMARY KEY, " +
                KEY_NOTES + " TEXT, " +
                KEY_CHECK + " INTEGER, " +
                KEY_DATETIME + " TEXT)")

        if (db != null) {
            db.execSQL(USER_TABLE)
        }

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        if (db != null) {
            db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
            onCreate(db)
        }
    }

    fun addNote(note: Notes){
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_ID, note.id)
        contentValues.put(KEY_NOTES, note.notes)
        contentValues.put(KEY_CHECK, note.check)
        contentValues.put(KEY_DATETIME, note.date)
        db.insert(TABLE_NAME, null, contentValues)
        db.close()
    }

    @SuppressLint("Range")
    fun readNotes() : MutableList<Notes> {
        val noteList: MutableList<Notes> = mutableListOf()
        val selectQuery = "SELECT * FROM $TABLE_NAME"
        val db = this.readableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return noteList
        }
        var id: Int
        var notes: String
        var check: Int
        var date: String
        if(cursor.moveToFirst()){
            do {
                id = cursor.getInt(cursor.getColumnIndex("id"))
                notes = cursor.getString(cursor.getColumnIndex("notes"))
                check = cursor.getInt(cursor.getColumnIndex("checkBox"))
                date = cursor.getString(cursor.getColumnIndex("date"))
                val note = Notes(id=id, notes=notes, check=check, date = date)
                noteList.add(note)
            }while (cursor.moveToNext())
        }
        return noteList
    }

    fun updateNotes(note: Notes){
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_ID, note.id)
       // contentValues.put(KEY_NOTES, note.notes)
        contentValues.put(KEY_CHECK, note.check)
        //contentValues.put(KEY_DATETIME, note.date)
        db.update(TABLE_NAME, contentValues, "id=" + note.id, null)
        db.close()
    }
}