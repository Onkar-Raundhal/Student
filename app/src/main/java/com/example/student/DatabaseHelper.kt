package com.example.student

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

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
        // Create the students table
        val createStudentsTable =
            "CREATE TABLE $TABLE_NAME ($COL_ROLL INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, $COL_NAME TEXT, $COL_CLASSNAME TEXT, $COL_SUB1 TEXT, $COL_SUB2 TEXT);"
        db?.execSQL(createStudentsTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        // Drop and recreate the students table when upgrading the database
        val dropStudentsTable = "DROP TABLE IF EXISTS $TABLE_NAME;"
        db?.execSQL(dropStudentsTable)
        onCreate(db)
    }

    @SuppressLint("Range")
    fun getStudents(): List<StudentModel> {
        // Retrieve all students from the database
        val studentsList = ArrayList<StudentModel>()
        val db = writableDatabase
        val selectQuery = "SELECT * FROM $TABLE_NAME"
        db.rawQuery(selectQuery, null).use { cursor ->
            if (cursor.moveToFirst()) {
                do {
                    val student = StudentModel()
                    student.roll = cursor.getInt(cursor.getColumnIndex(COL_ROLL))
                    student.name = cursor.getString(cursor.getColumnIndex(COL_NAME))
                    student.classname = cursor.getString(cursor.getColumnIndex(COL_CLASSNAME))
                    student.sub1 = cursor.getString(cursor.getColumnIndex(COL_SUB1))
                    student.sub2 = cursor.getString(cursor.getColumnIndex(COL_SUB2))
                    studentsList.add(student)
                } while (cursor.moveToNext())
            }
        }
        return studentsList
    }

    fun addStudent(student: StudentModel): Boolean {
        // Add a new student to the database
        val db = writableDatabase
        val values = ContentValues()
        values.put(COL_NAME, student.name)
        values.put(COL_CLASSNAME, student.classname)
        values.put(COL_SUB1, student.sub1)
        values.put(COL_SUB2, student.sub2)
        val success = db.insert(TABLE_NAME, null, values)
        return success != -1L
    }

    @SuppressLint("Range")
    fun getStudent(roll: Int): StudentModel? {
        // Retrieve a student from the database based on their roll number
        val db = writableDatabase
        val selectQuery = "SELECT * FROM $TABLE_NAME WHERE $COL_ROLL = $roll"
        db.rawQuery(selectQuery, null).use { cursor ->
            if (cursor.moveToFirst()) {
                val student = StudentModel()
                student.roll = cursor.getInt(cursor.getColumnIndex(COL_ROLL))
                student.name = cursor.getString(cursor.getColumnIndex(COL_NAME))
                student.classname = cursor.getString(cursor.getColumnIndex(COL_CLASSNAME))
                student.sub1 = cursor.getString(cursor.getColumnIndex(COL_SUB1))
                student.sub2 = cursor.getString(cursor.getColumnIndex(COL_SUB2))
                return student
            }
        }
        return null
    }

    fun deleteStudent(roll: Int): Boolean {
        // Delete a student from the database based on their roll number
        val db = writableDatabase
        val success = db.delete(TABLE_NAME, "$COL_ROLL=?", arrayOf(roll.toString()))
        return success > 0
    }

    fun updateStudent(student: StudentModel): Boolean {
        // Update a student's information in the database
        val db = writableDatabase
        val values = ContentValues()
        values.put(COL_NAME, student.name)
        values.put(COL_CLASSNAME, student.classname)
        values.put(COL_SUB1, student.sub1)
        values.put(COL_SUB2, student.sub2)
        val success = db.update(TABLE_NAME, values, "$COL_ROLL=?", arrayOf(student.roll.toString()))
        return success > 0
    }

    @SuppressLint("Range")
    fun getRollNo(): Int {
        // Get the maximum roll number from the students table
        val query = "SELECT MAX($COL_ROLL) FROM $TABLE_NAME"
        val db = writableDatabase
        db.rawQuery(query, null).use { cursor ->
            if (cursor.moveToFirst()) {
                return cursor.getInt(0)
            }
        }
        return 0
    }
}
