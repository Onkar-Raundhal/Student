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
import com.thecode.aestheticdialogs.*

class Add_Student : AppCompatActivity() {

    private var mLastClickTime: Long = 0
    private lateinit var databasehelper: DatabaseHelper
    var isAllFieldsChecked_Add = false
    var lastclick=0L
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_student)
        setupAnim()
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        databasehelper = DatabaseHelper(this)
        val roll = findViewById<EditText>(R.id.Add_rollNo)
        roll.setText("${databasehelper.getrollno().plus(1)}")
        Log.d("check", "RollNo: ${databasehelper.getrollno()}")
        val name = findViewById<EditText>(R.id.Add_Name)
        name.requestFocus()
        val cnmae = findViewById<EditText>(R.id.Add_classname)
        val sub1 = findViewById<EditText>(R.id.Add_subject1)
        val sub2 = findViewById<EditText>(R.id.Add_subject2)
        val close = findViewById<Button>(R.id.add_btn_close)
        close.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_left,
                R.anim.slide_out_right);
        }

        val save = findViewById<Button>(R.id.add_btn_save)
        save.setOnClickListener {
            if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
                return@setOnClickListener
            }
            mLastClickTime = SystemClock.elapsedRealtime()
            isAllFieldsChecked_Add = CheckAllFields()
            if (isAllFieldsChecked_Add) {
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Are You Sure")
                builder.setMessage("Want To Proceed")
                builder.setIcon(android.R.drawable.ic_dialog_alert)
                builder.setPositiveButton("Yes") { dialogInterface, which ->
                    databasehelper.addstudents(
                        StudentModel(
                            name = name.text.toString(),
                            classname = cnmae.text.toString(),
                            sub1 = sub1.text.toString(),
                            sub2 = sub2.text.toString()
                        )
                    )
                    AestheticDialog.Builder(this, DialogStyle.FLAT, DialogType.SUCCESS)
                        .setTitle("Success")
                        .setMessage("Data Inserted Successfully")
                        .setCancelable(true)
                        .setDarkMode(false)
                        .setGravity(Gravity.CENTER)
                        .setAnimation(DialogAnimation.ZOOM)
                        .setOnClickListener(object : OnDialogClickListener {
                            override fun onClick(dialog: AestheticDialog.Builder) {
//                                Toast.makeText(applicationContext, "Data Inserted Successfully", Toast.LENGTH_LONG)
//                                    .show()
                                dialog.dismiss()
                                name.getText().clear();
                                cnmae.getText().clear();
                                sub1.getText().clear();
                                sub2.getText().clear();
                                inter()
                            }
                        })
                        .show()

//                    val intent = Intent(this, MainActivity::class.java)
//                    startActivity(intent)

//                    val intent = Intent(this, Add_Student::class.java)
//                    overridePendingTransition(0, 0);
//                    startActivity(intent);
//                    overridePendingTransition(0, 0);

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
        val name = findViewById<EditText>(R.id.Add_Name)
        val cnmae = findViewById<EditText>(R.id.Add_classname)
        val sub1 = findViewById<EditText>(R.id.Add_subject1)
        val sub2 = findViewById<EditText>(R.id.Add_subject2)
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
        }
        else if (sub2!!.length() == 0) {
            sub2!!.error = "This field is required"
            return false
        }
        // after all validation return true.
        return true
    }
    private fun inter(){
        val intent = Intent(this, Add_Student::class.java)
        overridePendingTransition(0, 0);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }
    private fun setupAnim() {
        val animate= findViewById<LottieAnimationView>(R.id.Lottie_Add)
        animate.setAnimation(R.raw.add)
        animate.repeatCount = LottieDrawable.INFINITE
        animate.playAnimation()
    }
}
