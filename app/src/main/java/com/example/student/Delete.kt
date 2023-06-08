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
var isAllFieldsChecked_del = false
class Delete : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delete)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        setupAnim()
        databasehelper = DatabaseHelper(this)
        val del_edittxt=findViewById<EditText>(R.id.delete_roll)
        del_edittxt.requestFocus()
        val del_close=findViewById<Button>(R.id.del_btn_close)
        del_close.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_left,
                R.anim.slide_out_right);
        }

        val del = findViewById<Button>(R.id.del_btn)
        del.setOnClickListener {

            if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
                return@setOnClickListener
            }
            mLastClickTime = SystemClock.elapsedRealtime()

            isAllFieldsChecked_del = CheckAllFields()
            if (isAllFieldsChecked_del) {

                val builder = AlertDialog.Builder(this)
                builder.setTitle("Are You Sure")
                builder.setMessage("Want To Delete Data")
                builder.setIcon(android.R.drawable.ic_dialog_alert)
                builder.setPositiveButton("Yes") { dialogInterface, which ->
                    if (databasehelper.deleteStudent(_roll =del_edittxt.text.toString().toInt())){
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
                                    del_edittxt.getText().clear()
                                }
                            })
                            .show()
                    //Toast.makeText(applicationContext, "Data Deleted Successfully", Toast.LENGTH_LONG).show()
                    //del_edittxt.getText().clear()
                    }
                    else{
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
                                    del_edittxt.getText().clear()
                                }
                            })
                            .show()
                        //Toast.makeText(applicationContext, "Entered Record Doesn't Exist", Toast.LENGTH_LONG).show()
                        //del_edittxt.getText().clear()
                    }
                }

             ;
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
        val roll = findViewById<EditText>(R.id.delete_roll)
        if (roll!!.length() == 0) {
            roll!!.error = "This field is required"
            return false
        }
        // after all validation return true.
        return true
    }
    private fun setupAnim() {
        val animate= findViewById<LottieAnimationView>(R.id.Lottie_Delete)
        animate.setAnimation(R.raw.delete)
        animate.repeatCount = LottieDrawable.INFINITE
        animate.playAnimation()
    }
}