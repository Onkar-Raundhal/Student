package com.example.student

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnAdd = findViewById<Button>(R.id.btn_ADD)
        btnAdd.setOnClickListener {
            navigateToAddStudent()
        }

        val btnDisplay = findViewById<Button>(R.id.btn_DISPLAY)
        btnDisplay.setOnClickListener {
            navigateToDisplay()
        }

        val btnUpdate = findViewById<Button>(R.id.btn_UPDATE)
        btnUpdate.setOnClickListener {
            navigateToUpdate()
        }

        val btnDelete = findViewById<Button>(R.id.btn_DELETE)
        btnDelete.setOnClickListener {
            navigateToDelete()
        }
    }

    private fun navigateToAddStudent() {
        val intent = Intent(this, AddStudent::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }

    private fun navigateToDisplay() {
        val intent = Intent(this, Display::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }

    private fun navigateToUpdate() {
        val intent = Intent(this, Update::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }

    private fun navigateToDelete() {
        val intent = Intent(this, Delete::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }
}
