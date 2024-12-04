package com.example.examen2_pde.horario

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.examen2_pde.R

class ScheduleAdapter(private val scheduleList: List<ScheduleItem>) :
    RecyclerView.Adapter<ScheduleAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val subject: TextView = view.findViewById(R.id.text_subject)
        val time: TextView = view.findViewById(R.id.text_time)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_schedule, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = scheduleList[position]
        holder.subject.text = item.subject
        holder.time.text = item.time
    }

    override fun getItemCount() = scheduleList.size
}
