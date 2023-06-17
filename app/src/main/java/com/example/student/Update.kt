package com.example.student

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.view.Gravity
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieDrawable
import com.thecode.aestheticdialogs.*

private var mLastClickTime: Long = 0
private lateinit var databasehelper: DatabaseHelper
var isAllFieldsChecked_Update = false

class Update : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Initialize the database helper
        databasehelper = DatabaseHelper(this)
        setContentView(R.layout.activity_update)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        // Get references to the EditText fields
        val roll = findViewById<EditText>(R.id.update_rollNo)
        val name = findViewById<EditText>(R.id.update_Name)
        val classname = findViewById<EditText>(R.id.update_classname)
        val sub1 = findViewById<EditText>(R.id.update_subject1)
        val sub2 = findViewById<EditText>(R.id.update_subject2)

        // Set focus on the roll EditText field
        roll.requestFocus()

        // Set up animation
        setupAnim()

        // Handle close button click
        val close = findViewById<Button>(R.id.btn_close)
        close.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
        }

        // Handle update button click
        val update = findViewById<Button>(R.id.btn_update)
        update.setOnClickListener {
            // Check for multiple rapid clicks
            if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                return@setOnClickListener
            }
            mLastClickTime = SystemClock.elapsedRealtime()

            // Perform field validation
            isAllFieldsChecked_Update = checkAllFields()
            if (isAllFieldsChecked_Update) {
                // Show confirmation dialog
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Are You Sure")
                builder.setMessage("Want To Update")
                builder.setIcon(android.R.drawable.ic_dialog_alert)
                builder.setPositiveButton("Yes") { dialogInterface, which ->
                    // Perform student data update
                    if (databasehelper.updateStudent(
                            StudentModel(
                                roll = roll.text.toString().toInt(),
                                name = name.text.toString(),
                                classname = classname.text.toString(),
                                sub1 = sub1.text.toString(),
                                sub2 = sub2.text.toString()
                            )
                        )
                    ) {
                        // Show success dialog
                        AestheticDialog.Builder(this, DialogStyle.FLAT, DialogType.SUCCESS)
                            .setTitle("Success")
                            .setMessage("Data Updated Successfully")
                            .setCancelable(true)
                            .setDarkMode(false)
                            .setGravity(Gravity.CENTER)
                            .setAnimation(DialogAnimation.ZOOM)
                            .setOnClickListener(object : OnDialogClickListener {
                                override fun onClick(dialog: AestheticDialog.Builder) {
                                    dialog.dismiss()
                                    // Clear input fields
                                    roll.getText().clear()
                                    name.getText().clear()
                                    classname.getText().clear()
                                    sub1.getText().clear()
                                    sub2.getText().clear()
                                }
                            })
                            .show()
                    } else {
                        // Show error dialog
                        AestheticDialog.Builder(this, DialogStyle.FLAT, DialogType.ERROR)
                            .setTitle("Alert")
                            .setMessage("Entered Record Doesn't Exist")
                            .setCancelable(true)
                            .setDarkMode(false)
                            .setGravity(Gravity.CENTER)
                            .setAnimation(DialogAnimation.ZOOM)
                            .setOnClickListener(object : OnDialogClickListener {
                                override fun onClick(dialog: AestheticDialog.Builder) {
                                    dialog.dismiss()
                                    // Clear input fields
                                    roll.getText().clear()
                                    name.getText().clear()
                                    classname.getText().clear()
                                    sub1.getText().clear()
                                    sub2.getText().clear()
                                }
                            })
                            .show()
                    }
                }
                builder.setNegativeButton("No") { dialogInterface, which ->
                    // User clicked "No" in the confirmation dialog
                    Toast.makeText(applicationContext, "Clicked No", Toast.LENGTH_LONG).show()
                }
                val alertDialog: AlertDialog = builder.create()
                alertDialog.setCancelable(false)
                alertDialog.show()
            }
        }
    }

    // Field validation function
    private fun checkAllFields(): Boolean {
        val roll = findViewById<EditText>(R.id.update_rollNo)
        val name = findViewById<EditText>(R.id.update_Name)
        val classname = findViewById<EditText>(R.id.update_classname)
        val sub1 = findViewById<EditText>(R.id.update_subject1)
        val sub2 = findViewById<EditText>(R.id.update_subject2)

        // Check each field for empty values
        if (roll!!.length() == 0) {
            roll.error = "This field is required"
            return false
        }
        if (name!!.length() == 0) {
            name.error = "This field is required"
            return false
        }
        if (classname!!.length() == 0) {
            classname.error = "This field is required"
            return false
        }
        if (sub1!!.length() == 0) {
            sub1.error = "This field is required"
            return false
        } else if (sub2!!.length() == 0) {
            sub2.error = "This field is required"
            return false
        }
        // All fields are validated
        return true
    }

    // Set up animation
    private fun setupAnim() {
        val animate = findViewById<LottieAnimationView>(R.id.Lottie_Update)
        animate.setAnimation(R.raw.update)
        animate.repeatCount = LottieDrawable.INFINITE
        animate.playAnimation()
    }
}
