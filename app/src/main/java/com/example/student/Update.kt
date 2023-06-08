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
        databasehelper = DatabaseHelper(this)
        setContentView(R.layout.activity_update)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        val roll = findViewById<EditText>(R.id.update_rollNo)
        roll.requestFocus()
        val name = findViewById<EditText>(R.id.update_Name)
        val cnmae = findViewById<EditText>(R.id.update_classname)
        val sub1 = findViewById<EditText>(R.id.update_subject1)
        val sub2 = findViewById<EditText>(R.id.update_subject2)
        setupAnim()
        val close = findViewById<Button>(R.id.btn_close)
        close.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_left,
                R.anim.slide_out_right);
        }


        val update = findViewById<Button>(R.id.btn_update)
        update.setOnClickListener {

            if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
                return@setOnClickListener
            }
            mLastClickTime = SystemClock.elapsedRealtime()

            isAllFieldsChecked_Update = CheckAllFields()
            if (isAllFieldsChecked_Update) {
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Are You Sure")
                builder.setMessage("Want To Update")
                builder.setIcon(android.R.drawable.ic_dialog_alert)
                builder.setPositiveButton("Yes") { dialogInterface, which ->
                    if (databasehelper.updateStudent(
                            StudentModel(
                                roll = roll.text.toString().toInt(),
                                name = name.text.toString(),
                                classname = cnmae.text.toString(),
                                sub1 = sub1.text.toString(),
                                sub2 = sub2.text.toString()
                            )
                        )
                    ) {
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
                                    roll.getText().clear();
                                    name.getText().clear();
                                    cnmae.getText().clear();
                                    sub1.getText().clear();
                                    sub2.getText().clear();

                                }
                            })
                            .show()

//                        Toast.makeText(
//                            applicationContext,
//                            "Data Updated Successfully",
//                            Toast.LENGTH_LONG
//                        )
//                            .show()
//                        roll.getText().clear();
//                        name.getText().clear();
//                        cnmae.getText().clear();
//                        sub1.getText().clear();
//                        sub2.getText().clear();
                    } else {
//                        Toast.makeText(
//                            applicationContext,
//                            "Entered Record Doesn't Exist",
//                            Toast.LENGTH_LONG
//                        )
//                            .show()
//                        roll.getText().clear();
//                        name.getText().clear();
//                        cnmae.getText().clear();
//                        sub1.getText().clear();
//                        sub2.getText().clear();
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
                                    roll.getText().clear();
                                    name.getText().clear();
                                    cnmae.getText().clear();
                                    sub1.getText().clear();
                                    sub2.getText().clear();

                                }
                            })
                            .show()
                    }
                }
                //performing cancel action
//            builder.setNeutralButton("Cancel"){dialogInterface , which ->
//                Toast.makeText(applicationContext,"clicked cancel\n operation cancel",Toast.LENGTH_LONG).show()
//            }
                builder.setNegativeButton("No") { dialogInterface, which ->

                    Toast.makeText(applicationContext, "clicked No", Toast.LENGTH_LONG).show()
                }
                val alertDialog: AlertDialog = builder.create()
                alertDialog.setCancelable(false)
                alertDialog.show()
            }
        }
    }

    private fun CheckAllFields(): Boolean {
        var roll = findViewById<EditText>(R.id.update_rollNo)
        val name = findViewById<EditText>(R.id.update_Name)
        val cnmae = findViewById<EditText>(R.id.update_classname)
        val sub1 = findViewById<EditText>(R.id.update_subject1)
        val sub2 = findViewById<EditText>(R.id.update_subject2)
        if (roll!!.length() == 0) {
            roll!!.error = "This field is required"
            return false
        }
        if (name!!.length() == 0) {
            name!!.error = "This field is required"
            return false
        }
        if (cnmae!!.length() == 0) {
            cnmae!!.error = "This field is required"
            return false
        }
        if (sub1!!.length() == 0) {
            sub1!!.error = "This field is required"
            return false
        } else if (sub2!!.length() == 0) {
            sub2!!.error = "This field is required"
            return false
        }
        // after all validation return true.
        return true
    }
    private fun setupAnim() {
        val animate= findViewById<LottieAnimationView>(R.id.Lottie_Update)
        animate.setAnimation(R.raw.update)
        animate.repeatCount = LottieDrawable.INFINITE
        animate.playAnimation()
    }
}