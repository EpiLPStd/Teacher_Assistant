package com.example.asystentnauczyciela.select

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.asystentnauczyciela.R
import com.example.asystentnauczyciela.entities.Subject

class SelectAdapter(
    private val subjects: LiveData<List<Subject>>,
    private val listener: SelectFragment
): RecyclerView.Adapter<SelectAdapter.SubjectsListHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubjectsListHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.one_subject, parent, false)

        return SubjectsListHolder(view)
    }

    override fun onBindViewHolder(holder: SubjectsListHolder, position: Int) {
        holder.subjectName.text = subjects.value?.get(position)?.subjectName
    }

    override fun getItemCount() = subjects.value?.size?:0

    inner class SubjectsListHolder(view: View): RecyclerView.ViewHolder(view), View.OnClickListener {
        val subjectName: TextView = view.findViewById(R.id.subject_name)

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