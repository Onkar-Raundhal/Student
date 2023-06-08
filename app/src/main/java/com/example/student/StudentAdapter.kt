package com.example.student

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class StudentAdapter: RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {

    private var stdlist: ArrayList<StudentModel> = ArrayList()

    fun additems(items: ArrayList<StudentModel>) {
        this.stdlist=items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)= StudentViewHolder (
     LayoutInflater.from(parent.context).inflate(R.layout.stud_items, parent, false)
    )

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val std = stdlist[position]
        holder.bindview(std)
    }

    override fun getItemCount(): Int {
        return stdlist.size
    }

    class StudentViewHolder(var view: View) : RecyclerView.ViewHolder(view) {
        private var viewholder_roll = view.findViewById<TextView>(R.id.roll)
        private var viewholder_name = view.findViewById<TextView>(R.id.name)
        private var viewholder_cname = view.findViewById<TextView>(R.id.cname)
        private var viewholder_subject2 = view.findViewById<TextView>(R.id.update_subject2)
        private var viewholder_subject1 = view.findViewById<TextView>(R.id.update_subject1)

        fun bindview(it: StudentModel) {
            viewholder_roll.text = "Roll No is: " + it.roll.toString()
            viewholder_name.text = "Name is: " + it.name.toString()
            viewholder_cname.text = "Classname is: " + it.classname.toString()
            viewholder_subject2.text = "Java Marks Are: " + it.sub1.toString()
            viewholder_subject1.text = "Kotlin Marks Are: " + it.sub2.toString()
        }
    }
}