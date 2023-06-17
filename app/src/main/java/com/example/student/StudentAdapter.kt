import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.student.R
import com.example.student.StudentModel

class StudentAdapter: RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {

    private var stdlist: ArrayList<StudentModel> = ArrayList()

    // Method to update the adapter data with new items
    fun addItems(items: ArrayList<StudentModel>) {
        this.stdlist = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        // Inflate the item view layout and create a new StudentViewHolder
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.stud_items, parent, false)
        return StudentViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        // Retrieve the student at the given position and bind it to the ViewHolder
        val student = stdlist[position]
        holder.bindView(student)
    }

    override fun getItemCount(): Int {
        // Return the total number of students in the list
        return stdlist.size
    }

    // ViewHolder class representing an item view in the RecyclerView
    class StudentViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val rollTextView: TextView = view.findViewById(R.id.roll)
        private val nameTextView: TextView = view.findViewById(R.id.name)
        private val classNameTextView: TextView = view.findViewById(R.id.cname)
        private val subject2TextView: TextView = view.findViewById(R.id.update_subject2)
        private val subject1TextView: TextView = view.findViewById(R.id.update_subject1)

        fun bindView(student: StudentModel) {
            // Set the data of the student to the respective views in the item layout
            rollTextView.text = "Roll No is: ${student.roll}"
            nameTextView.text = "Name is: ${student.name}"
            classNameTextView.text = "Classname is: ${student.classname}"
            subject2TextView.text = "Java Marks Are: ${student.sub1}"
            subject1TextView.text = "Kotlin Marks Are: ${student.sub2}"
        }
    }
}
