package com.example.optlearn.recyclers

import android.app.Application
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.optlearn.R
import com.example.optlearn.mvvm.TaskViewModel
import com.example.optlearn.room.Task


class ActiveRecyclerViewAdapter(var list: List<Task>) : RecyclerView.Adapter<MyHolder>() {
    lateinit var viewModel: TaskViewModel
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
       val view = LayoutInflater.from(parent.context).inflate(R.layout.task_layout, parent, false)
        viewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(Application()).create(TaskViewModel::class.java)
        return MyHolder(view)
    }

    override fun getItemCount(): Int {

        return list.size
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.name.text = list[position].name
        holder.time.text = "Time : ${list[position].time}"
        holder.breaks.text = "Breaks :${list[position].breaks.toString()}"

        holder.itemView.setOnClickListener {

          /*  viewModel.deleteTask(list[position])
            holder.itemView.visibility = View.GONE
            notifyItemRemoved(position)*/

            

        }
    }

}

class MyHolder(view: View) : RecyclerView.ViewHolder(view) {

    var name = view.findViewById<TextView>(R.id.task_name)
    var time = view.findViewById<TextView>(R.id.task_time)
    var breaks = view.findViewById<TextView>(R.id.task_breaks)
}