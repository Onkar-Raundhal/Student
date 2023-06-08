package com.example.student

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class Display : AppCompatActivity() {

    var studentModel: StudentModel? = null
    private lateinit var databasehelper: DatabaseHelper
    private lateinit var recyclerview: RecyclerView
    private var adapter: StudentAdapter? = null

    var roll = 1
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        databasehelper = DatabaseHelper(this)
        setContentView(R.layout.activity_display)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
//        val rolll = findViewById<TextView>(R.id.roll)
//        val name = findViewById<TextView>(R.id.name)
//        val cname = findViewById<TextView>(R.id.cname)
//        val subject2 = findViewById<TextView>(R.id.update_subject2)
//        val subject1 = findViewById<TextView>(R.id.update_subject1)
        val close = findViewById<Button>(R.id.close_disp)
        //val prev = findViewById<Button>(R.id.previous)
        //val next = findViewById<Button>(R.id.next)
        initView()
        initRecyclerView()
        getdata()

        close.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_left,
                R.anim.slide_out_right);
        }
        studentModel = databasehelper.getStudent(roll)
        studentModel?.let {
            Log.d("pass", "name:${it.name}")
//            rolll.text = "Roll No is: "+it.roll.toString()
//            name.text = "Name is: "+it.name.toString()
//            cname.text = "Classname is: "+it.classname.toString()
//            subject1.text = "Java Marks Are: "+it.sub1.toString()
//            subject2.text = "Kotlin Marks Are: "+it.sub2.toString()
        }?:Toast.makeText(this@Display,"No Data Found",Toast.LENGTH_LONG).show()
//        prev.setOnClickListener {
//            roll -= 1
//            studentModel = databasehelper.getStudent(roll)
//            studentModel?.let {
//                Log.d("pass", "name:${it.name}")
//                rolll.text = "Roll No is: "+it.roll.toString()
//                name.text = "Name is: "+it.name.toString()
//                cname.text = "Classname is: "+it.classname.toString()
//                subject1.text = "Java Marks Are: "+it.sub1.toString()
//                subject2.text = "Kotlin Marks Are: "+it.sub2.toString()
//            }?:Toast.makeText(this@Display,"No Data Found",Toast.LENGTH_LONG).show()
//        }
//        next.setOnClickListener {
//            roll += 1
//            studentModel = databasehelper.getStudent(roll)
//            studentModel?.let {
//                Log.d("pass", "name:${it.name}")
//                rolll.text = "Roll No is: "+it.roll.toString()
//                name.text = "Name is: "+it.name.toString()
//                cname.text = "Classname is: "+it.classname.toString()
//                subject1.text = "Java Marks Are: "+it.sub1.toString()
//                subject2.text = "Kotlin Marks Are: "+it.sub2.toString()
//            }?:Toast.makeText(this@Display,"No Data Found",Toast.LENGTH_LONG).show()
//        }
    }
    private fun getdata() {
        val stdlist = databasehelper.getstudents()
        adapter?.additems(stdlist as ArrayList<StudentModel>)
    }
    private fun initRecyclerView() {
        val rv= findViewById<RecyclerView>(R.id.rv_dispdata)
        rv.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        adapter = StudentAdapter()
        rv.adapter = adapter
    }
    private fun initView() {
        var rv= findViewById<RecyclerView>(R.id.rv_dispdata)
        val rolll = findViewById<TextView>(R.id.roll)
        val name = findViewById<TextView>(R.id.name)
        val cname = findViewById<TextView>(R.id.cname)
        val subject2 = findViewById<TextView>(R.id.update_subject2)
        val subject1 = findViewById<TextView>(R.id.update_subject1)
    }
}