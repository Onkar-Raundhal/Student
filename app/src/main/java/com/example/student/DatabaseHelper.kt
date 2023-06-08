package com.example.student

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DatabaseHelper(context: Context):SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

    companion object {
        private const val DB_NAME = "Student.db"
        private const val DB_VERSION = 2
        private const val TABLE_NAME = "students"
        private const val COL_ROLL = "roll"
        private const val COL_NAME = "name"
        private const val COL_CLASSNAME = "classname"
        private const val COL_SUB1 = "sub1"
        private const val COL_SUB2 = "sub2"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createStudentsTable =
            "CREATE TABLE $TABLE_NAME ($COL_ROLL INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, $COL_NAME TEXT, $COL_CLASSNAME TEXT, $COL_SUB1 TEXT, $COL_SUB2 TEXT);"
        db?.execSQL(createStudentsTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val dropStudentsTable = "DROP TABLE IF EXISTS $TABLE_NAME;"
        db?.execSQL(dropStudentsTable)
        onCreate(db)
    }

    @SuppressLint("Range")
    fun getstudents(): List<StudentModel> {
        val studentslist = ArrayList<StudentModel>()
        val db = getWritableDatabase()
        val selectQuery = "SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(selectQuery, null)
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    val stud = StudentModel()
                    stud.roll = Integer.parseInt(cursor.getString(cursor.getColumnIndex(COL_ROLL)))
                    stud.name = cursor.getString(cursor.getColumnIndex(COL_NAME))
                    stud.classname = cursor.getString(cursor.getColumnIndex(COL_CLASSNAME))
                    stud.sub1 = cursor.getString(cursor.getColumnIndex(COL_SUB1))
                    stud.sub2 = cursor.getString(cursor.getColumnIndex(COL_SUB2))
                    studentslist.add(stud)
                } while (cursor.moveToNext())
            }
        }
        cursor.close()
        return studentslist
    }

    fun addstudents(students: StudentModel): Boolean {
        val db = this.writableDatabase
        val values = ContentValues()
        // values.put(COL_ROLL, students.roll)
        values.put(COL_NAME, students.name)
        values.put(COL_CLASSNAME, students.classname)
        values.put(COL_SUB1, students.sub1)
        values.put(COL_SUB2, students.sub2)
        val _success = db.insert(TABLE_NAME, null, values)
        db.close()
        return (Integer.parseInt("$_success") != -1)
    }

    @SuppressLint("Range")
    fun getStudent(_roll: Int): StudentModel? {
        val student = StudentModel()
        val db = writableDatabase
        val selectQuery = "SELECT * FROM $TABLE_NAME WHERE $COL_ROLL= $_roll"
        val cursor = db.rawQuery(selectQuery, null)
        if (cursor.moveToFirst()) {
//          Log.d("if","${cursor.getString(cursor.getColumnIndex(COL_NAME))}")
            student.roll = Integer.parseInt(cursor.getString(cursor.getColumnIndex(COL_ROLL)))
            student.name = cursor.getString(cursor.getColumnIndex(COL_NAME))
            student.classname = cursor.getString(cursor.getColumnIndex(COL_CLASSNAME))
            student.sub1 = cursor.getString(cursor.getColumnIndex(COL_SUB1))
            student.sub2 = cursor.getString(cursor.getColumnIndex(COL_SUB2))
            cursor.close()
            return student
        }
        cursor.close()
        return null
    }

    fun deleteStudent(_roll: Int): Boolean {
        val db = this.writableDatabase
        return db.delete(TABLE_NAME, "$COL_ROLL=?", arrayOf(_roll.toString())).toLong() > 0
        db.close()
//        return (Integer.parseInt("$_success")!= -1)
    }

    fun updateStudent(students: StudentModel): Boolean {
        val db = this.writableDatabase
        val values = ContentValues()
        Log.d("update","1")
        values.put(COL_ROLL, students.roll)
        values.put(COL_NAME, students.name)
        Log.d("update","2")
        values.put(COL_CLASSNAME, students.classname)
        values.put(COL_SUB1, students.sub1)
        values.put(COL_SUB2, students.sub2)
        Log.d("update","3")
        return db.update(TABLE_NAME, values, "$COL_ROLL=?",arrayOf(students.roll.toString())).toLong() > 0
//        Log.d("update","4")
        db.close()
        //return (Integer.parseInt("$_success")!= -1)
    }
    @SuppressLint("Range")
    fun getrollno():Int{
        //val query="SELECT $COL_ROLL FROM $TABLE_NAME"
        val query="SELECT MAX($COL_ROLL) FROM $TABLE_NAME"
        Log.d("check2","0")
        //val query = "SELECT * FROM $TABLE_NAME"
        Log.d("check2","1")
        val db = writableDatabase
        Log.d("check2","2")
        val cursor = db.rawQuery(query, null)
        Log.d("check2","3")
       if (cursor.moveToFirst()) {
           Log.d("check2","4")
           return cursor.getInt(0)
       }
        Log.d("check2","5")
        cursor.close()
        Log.d("check2","6")
        return 0
    }
}