package com.example.student

import StudentAdapter
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class Display : AppCompatActivity() {

    private var studentModel: StudentModel? = null
    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var recyclerView: RecyclerView
    private var adapter: StudentAdapter? = null

    var roll = 1

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        databaseHelper = DatabaseHelper(this)
        setContentView(R.layout.activity_display)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val close = findViewById<Button>(R.id.close_disp)
        close.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
        }

        initRecyclerView()
        getData()

        studentModel = databaseHelper.getStudent(roll)
        studentModel?.let {
            Log.d("pass", "name:${it.name}")
        } ?: Toast.makeText(this@Display, "No Data Found", Toast.LENGTH_LONG).show()
    }

    private fun getData() {
        val stdList = databaseHelper.getStudents()
        adapter?.addItems(stdList as ArrayList<StudentModel>)
    }

    private fun initRecyclerView() {
        recyclerView = findViewById<RecyclerView>(R.id.rv_dispdata)
        recyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        adapter = StudentAdapter()
        recyclerView.adapter = adapter
    }

}
