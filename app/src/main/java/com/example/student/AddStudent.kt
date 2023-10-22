package com.example.student

import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.Gravity
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieDrawable
import com.example.student.MainActivity.Companion.filter
import com.thecode.aestheticdialogs.AestheticDialog
import com.thecode.aestheticdialogs.DialogAnimation
import com.thecode.aestheticdialogs.DialogStyle
import com.thecode.aestheticdialogs.DialogType
import com.thecode.aestheticdialogs.OnDialogClickListener

class AddStudent : AppCompatActivity() {

    // Variable to track the last click time
    private var mLastClickTime: Long = 0

    // Database helper instance
    private lateinit var databaseHelper: DatabaseHelper

    // Variable to check if all fields are checked
    private var isAllFieldsChecked_Add = false

    // Variable to track the last click time
    var lastclick = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_student)

        // Set up animation
        setupAnim()

        // Enable back button in the action bar
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        // Initialize database helper
        databaseHelper = DatabaseHelper(this)

        // Set the roll number field with the next available roll number
        val roll = findViewById<EditText>(R.id.Add_rollNo)
        roll.setText("${databaseHelper.getRollNo().plus(1)}")

        Log.d("check", "RollNo: ${databaseHelper.getRollNo()}")

        val name = findViewById<EditText>(R.id.Add_Name)
        name.requestFocus()
        val cnmae = findViewById<EditText>(R.id.Add_classname)
        val sub1 = findViewById<EditText>(R.id.Add_subject1)
        val sub2 = findViewById<EditText>(R.id.Add_subject2)
        val close = findViewById<Button>(R.id.add_btn_close)
        sub1.filters = arrayOf(filter)
        sub2.filters = arrayOf(filter)

        // Close button click listener
        close.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
        }

        val save = findViewById<Button>(R.id.add_btn_save)

        // Save button click listener
        save.setOnClickListener {
            // Check if the button was clicked too quickly (within 1 second)
            if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                return@setOnClickListener
            }
            mLastClickTime = SystemClock.elapsedRealtime()

            // Check if all fields are filled
            isAllFieldsChecked_Add = checkAllFields()

            if (isAllFieldsChecked_Add) {
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Are You Sure")
                builder.setMessage("Want To Proceed")
                builder.setIcon(android.R.drawable.ic_dialog_alert)

                // Yes button click listener
                builder.setPositiveButton("Yes") { dialogInterface, which ->
                    // Add student data to the database
                    databaseHelper.addStudent(
                        StudentModel(
                            name = name.text.toString(),
                            classname = cnmae.text.toString(),
                            sub1 = sub1.text.toString(),
                            sub2 = sub2.text.toString()
                        )
                    )

                    // Show success dialog using AestheticDialog library
                    AestheticDialog.Builder(this, DialogStyle.FLAT, DialogType.SUCCESS)
                        .setTitle("Success")
                        .setMessage("Data Inserted Successfully")
                        .setCancelable(true)
                        .setDarkMode(false)
                        .setGravity(Gravity.CENTER)
                        .setAnimation(DialogAnimation.ZOOM)
                        .setOnClickListener(object : OnDialogClickListener {
                            override fun onClick(dialog: AestheticDialog.Builder) {
                                dialog.dismiss()

                                // Clear input fields
                                name.text.clear()
                                cnmae.text.clear()
                                sub1.text.clear()
                                sub2.text.clear()

                                // Refresh the activity
                                inter()
                            }
                        })
                        .show()
                }

                // No button click listener
                builder.setNegativeButton("No") { dialogInterface, which ->
                    Toast.makeText(applicationContext, "clicked No", Toast.LENGTH_LONG).show()
                }

                val alertDialog: AlertDialog = builder.create()
                alertDialog.setCancelable(false)
                alertDialog.show()
            }
        }
    }

    override fun onBackPressed() {
        navigateToMain()
    }

    // Check if all input fields are filled
    private fun checkAllFields(): Boolean {
        val name = findViewById<EditText>(R.id.Add_Name)
        val cname = findViewById<EditText>(R.id.Add_classname)
        val sub1 = findViewById<EditText>(R.id.Add_subject1)
        val sub2 = findViewById<EditText>(R.id.Add_subject2)
        sub1.filters = arrayOf(filter)
        sub2.filters = arrayOf(filter)

        if (name.length() == 0) {
            name.error = "This field is required"
            return false
        }

        if (cname.length() == 0) {
            cname.error = "This field is required"
            return false
        }

        if (sub1.length() == 0) {
            sub1.error = "This field is required"
            return false
        } else if (sub2!!.length() == 0) {
            sub2.error = "This field is required"
            return false
        }

        // All fields are filled
        return true
    }

    // Refresh the activity
    private fun inter() {
        val intent = Intent(this, AddStudent::class.java)
        overridePendingTransition(0, 0)
        startActivity(intent)
        overridePendingTransition(0, 0)
    }

    // Set up animation for the activity
    private fun setupAnim() {
        val animate = findViewById<LottieAnimationView>(R.id.Lottie_Add)
        animate.setAnimation(R.raw.add)
        animate.repeatCount = LottieDrawable.INFINITE
        animate.playAnimation()
    }

    private fun navigateToMain() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }

}
