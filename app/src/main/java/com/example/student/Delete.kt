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
private lateinit var databaseHelper: DatabaseHelper
private var isAllFieldsCheckedDel = false

class Delete : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delete)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        setupAnim()
        databaseHelper = DatabaseHelper(this)
        val delEditText = findViewById<EditText>(R.id.delete_roll)
        delEditText.requestFocus()

        val delClose = findViewById<Button>(R.id.del_btn_close)
        delClose.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
        }

        val delBtn = findViewById<Button>(R.id.del_btn)
        delBtn.setOnClickListener {
            // Check if the button click is within a specific time interval to prevent multiple clicks
            if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                return@setOnClickListener
            }
            mLastClickTime = SystemClock.elapsedRealtime()

            isAllFieldsCheckedDel = checkAllFields()
            if (isAllFieldsCheckedDel) {
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Are You Sure")
                builder.setMessage("Want To Delete Data")
                builder.setIcon(android.R.drawable.ic_dialog_alert)
                builder.setPositiveButton("Yes") { dialogInterface, which ->
                    val roll = delEditText.text.toString().toInt()
                    if (databaseHelper.deleteStudent(roll)) {
                        // Show success dialog
                        AestheticDialog.Builder(this, DialogStyle.FLAT, DialogType.SUCCESS)
                            .setTitle("Success")
                            .setMessage("Data Deleted Successfully")
                            .setCancelable(true)
                            .setDarkMode(true)
                            .setGravity(Gravity.CENTER)
                            .setAnimation(DialogAnimation.ZOOM)
                            .setOnClickListener(object : OnDialogClickListener {
                                override fun onClick(dialog: AestheticDialog.Builder) {
                                    dialog.dismiss()
                                    delEditText.getText().clear()
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
                                    delEditText.getText().clear()
                                }
                            })
                            .show()
                    }
                }
                builder.setNegativeButton("No") { dialogInterface, which ->
                    // No action required for "No" button
                }
                val alertDialog: AlertDialog = builder.create()
                alertDialog.setCancelable(false)
                alertDialog.show()
            }
        }
    }

    private fun checkAllFields(): Boolean {
        val rollEditText = findViewById<EditText>(R.id.delete_roll)
        if (rollEditText!!.length() == 0) {
            rollEditText!!.error = "This field is required"
            return false
        }
        // All fields have been checked and are valid
        return true
    }

    private fun setupAnim() {
        val animate = findViewById<LottieAnimationView>(R.id.Lottie_Delete)
        animate.setAnimation(R.raw.delete)
        animate.repeatCount = LottieDrawable.INFINITE
        animate.playAnimation()
    }
}
