package com.example.asystentnauczyciela.studentmarks

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.asystentnauczyciela.R
import com.example.asystentnauczyciela.entities.Mark

class StudentMarksAdapter(
    private val marks: LiveData<List<Mark>>
): RecyclerView.Adapter<StudentMarksAdapter.MarksListHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarksListHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.one_mark, parent, false)

        return MarksListHolder(view)
    }

    override fun onBindViewHolder(holder: MarksListHolder, position: Int) {
        try {
            holder.markValue.text = marks.value?.get(position)?.markValue
            holder.markType.text = marks.value?.get(position)?.markType
            holder.markDescription.text = marks.value?.get(position)?.markDescription
        }
        catch(e: java.lang.IndexOutOfBoundsException) {
            holder.markValue.text = null
            holder.markType.text = null
            holder.markDescription.text = null
        }
    }

    override fun getItemCount() = marks.value?.size?:0

    inner class MarksListHolder(view: View): RecyclerView.ViewHolder(view) {
        val markValue: TextView = view.findViewById(R.id.mark_value)
        val markType: TextView = view.findViewById(R.id.mark_type)
        val markDescription: TextView = view.findViewById(R.id.mark_description)
    }
}