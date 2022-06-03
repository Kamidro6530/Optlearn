package com.example.optlearn.recyclers

import android.app.AlertDialog
import android.app.Application
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.optlearn.R
import com.example.optlearn.mvvm.TaskViewModel
import com.example.optlearn.room.Task


class GroupsRecyclerViewAdapter(var list: ArrayList<Task>,var inputFragmentView : View?) : RecyclerView.Adapter<MyGroupHolder>() {
    lateinit var context : Context
    lateinit var dialogView : View
    lateinit var viewModel: TaskViewModel
    lateinit var parent: ViewGroup


    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyGroupHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.group_recyclerview_item, parent, false)
        when (list[viewType].success) {
            0 -> {view.setBackgroundResource(R.drawable.task_recyclerview_background)}
            1 -> {view.setBackgroundResource(R.drawable.success_task_recyclerview_background)}
            2 -> {view.setBackgroundResource(R.drawable.failure_task_recyclerview_background)}
        }



        viewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(Application()).create(TaskViewModel::class.java)
        context = parent.context
            this.parent = parent

        dialogView = LayoutInflater.from(parent.context).inflate(R.layout.delete_dialog_layout,parent,false)
        return MyGroupHolder(view)
    }

    override fun getItemCount(): Int {

        return list.size
    }

    override fun onBindViewHolder(holder: MyGroupHolder, position: Int) {
        holder.name.text = list[position].name




        holder.itemView.setOnClickListener {
            var bundle = bundleOf(
                "id" to list[position].id,
                "name" to list[position].name,
                "time" to list[position].time,
                "breaks" to list[position].breaks.toString(),
                "time_breaks" to list[position].time_breaks,
                "success" to list[position].success
            )
            if (list[position].success == 0)
            inputFragmentView?.findNavController()?.navigate(R.id.action_activePlan_to_taskFragment, bundle)
            else
                inputFragmentView?.findNavController()?.navigate(R.id.action_activePlan_to_successOrFailureTaskFragment, bundle)
        }

        holder.itemView.setOnLongClickListener {

            val dialogBuilder = AlertDialog.Builder(context)
            val alertDialog = dialogBuilder.create()
            alertDialog.setView(dialogView)
            alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            val positive = dialogView.findViewById<Button>(R.id.delete_dialog_positive)
            val negative = dialogView.findViewById<Button>(R.id.delete_dialog_negative)


            positive.setOnClickListener {
                viewModel.deleteTask(list[position])
                list.remove(list[position])
                holder.itemView.visibility = View.GONE
                alertDialog.dismiss()
                notifyDataSetChanged()
            }

            negative.setOnClickListener {
                alertDialog.dismiss()
            }
            parent.removeAllViews()
            alertDialog.show()
            true
        }
    }

}

class MyGroupHolder(view: View) : RecyclerView.ViewHolder(view) {

    var name = view.findViewById<TextView>(R.id.group_item_name)

}