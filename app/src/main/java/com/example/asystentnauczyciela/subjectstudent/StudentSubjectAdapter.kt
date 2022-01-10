package com.example.asystentnauczyciela.subjectstudent

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.asystentnauczyciela.R
import com.example.asystentnauczyciela.entities.GroupsWithStudents

class StudentSubjectAdapter(
    private val students: LiveData<List<GroupsWithStudents>>,
    private val listener: StudentSubjectFragment
): RecyclerView.Adapter<StudentSubjectAdapter.StudentsListHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentSubjectAdapter.StudentsListHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.one_student, parent, false)

        return StudentsListHolder(view)
    }

    override fun onBindViewHolder(holder: StudentSubjectAdapter.StudentsListHolder, position: Int) {
        holder.studentName.text = students.value?.get(position)?.students?.get(position)?.studentName
        holder.studentAlbum.text = students.value?.get(position)?.students?.get(position)?.studentAlbum
    }

    override fun getItemCount() = students.value?.size?:0

    inner class StudentsListHolder(view: View): RecyclerView.ViewHolder(view), View.OnClickListener {
        val studentName: TextView = view.findViewById(R.id.student_name)
        val studentAlbum: TextView = view.findViewById(R.id.student_album)

        init {
            view.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition

            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(position)
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }
}