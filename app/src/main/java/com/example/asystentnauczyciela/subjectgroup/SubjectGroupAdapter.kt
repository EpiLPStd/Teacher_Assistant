package com.example.asystentnauczyciela.subjectgroup

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.asystentnauczyciela.R
import com.example.asystentnauczyciela.entities.Group
import com.example.asystentnauczyciela.entities.SubjectGroups

class SubjectGroupAdapter(
    private val groups: LiveData<List<SubjectGroups>>,
    private val listener: SubjectGroupFragment
): RecyclerView.Adapter<SubjectGroupAdapter.GroupsListHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupsListHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.one_group, parent, false)

        return GroupsListHolder(view)
    }

    override fun onBindViewHolder(holder: GroupsListHolder, position: Int) {
        holder.groupName.text = groups.value?.get(position)?.groups?.get(position)?.groupName
        holder.groupDay.text = groups.value?.get(position)?.groups?.get(position)?.groupDayOfWeek
        holder.groupStart.text = groups.value?.get(position)?.groups?.get(position)?.groupStart
        holder.groupEnd.text = groups.value?.get(position)?.groups?.get(position)?.groupEnd
    }

    override fun getItemCount() = groups.value?.size?:0

    inner class GroupsListHolder(view: View): RecyclerView.ViewHolder(view), View.OnClickListener {
        val groupName: TextView = view.findViewById(R.id.group_name)
        val groupDay: TextView = view.findViewById(R.id.group_day)
        val groupStart: TextView = view.findViewById(R.id.group_start)
        val groupEnd: TextView = view.findViewById(R.id.group_end)

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