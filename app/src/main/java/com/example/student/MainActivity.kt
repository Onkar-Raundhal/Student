package com.example.student

import android.content.Intent
import android.os.Bundle
import android.text.InputFilter
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar

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

    override fun onBackPressed() {
        finish()
    }

    companion object{
        val filter = InputFilter { source, start, end, dest, dstart, dend ->
            try {
                // Convert the new input to an integer
                val input = (dest.subSequence(0, dstart).toString() + source + dest.subSequence(dend, dest.length)).toInt()

                // Check if the input is within the range [0, 100]
                if (input in 0..100) {
                    null // Accept the input
                } else {
                    "" // Reject the input
                }
            } catch (e: NumberFormatException) {
                println(e)
                // This will happen if the input cannot be parsed as an integer (e.g., empty input or non-numeric characters)
                ""
            }
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
