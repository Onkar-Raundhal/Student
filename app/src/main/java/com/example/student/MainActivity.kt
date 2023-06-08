package com.example.student

import android.app.PendingIntent
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import kotlin.math.log

class MainActivity : AppCompatActivity() {
    lateinit var editText: EditText
    var dbHandler: DatabaseHelper?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        dbHandler=DatabaseHelper(this)
        val btn_add=findViewById<Button>(R.id.btn_ADD)
        btn_add.setOnClickListener {
            val intent = Intent(this, Add_Student::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right,
                R.anim.slide_out_left)
        }
        val display=findViewById<Button>(R.id.btn_DISPLAY)
        display.setOnClickListener {
            val intent = Intent(this, Display::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right,
                R.anim.slide_out_left)
        }
        val update=findViewById<Button>(R.id.btn_UPDATE)
        update.setOnClickListener {
            val intent = Intent(this, Update::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right,
                R.anim.slide_out_left)
        }
        val delete=findViewById<Button>(R.id.btn_DELETE)
        delete.setOnClickListener {
            val intent = Intent(this, Delete::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right,
                R.anim.slide_out_left)
        }

        }
}