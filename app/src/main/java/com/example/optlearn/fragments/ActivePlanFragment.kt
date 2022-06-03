package com.example.optlearn.fragments

import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.optlearn.R
import com.example.optlearn.mvvm.TaskViewModel
import com.example.optlearn.recyclers.ActiveRecyclerViewAdapter
import com.example.optlearn.room.Task
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.zip.Inflater


class ActivePlan : Fragment() {
  lateinit var viewModel: TaskViewModel
    lateinit var taskAdapter : ActiveRecyclerViewAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider.AndroidViewModelFactory
            .getInstance(activity?.application!!).create(TaskViewModel::class.java)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val inputFragmentView = inflater.inflate(R.layout.fragment_active_plan, container, false)
        val recyclerView =inputFragmentView?.findViewById<RecyclerView>(R.id.active_recyclerView)
        val restartBT = inputFragmentView.findViewById<FloatingActionButton>(R.id.active_restartFAB)

       val list = viewModel.getAllTasks()

      list.observe(viewLifecycleOwner, Observer {
         if (it.isNotEmpty()) {
            taskAdapter = ActiveRecyclerViewAdapter(it as ArrayList<Task>,inputFragmentView)
            recyclerView?.adapter = taskAdapter
        }
      })
        recyclerView?.layoutManager = LinearLayoutManager(
            activity?.applicationContext, LinearLayoutManager.VERTICAL, false
        )
        recyclerView?.setHasFixedSize(true)

        restartBT.setOnClickListener {
            val dialogBuilder = AlertDialog.Builder(context)
            val alertDialog = dialogBuilder.create()
            var dialogView =LayoutInflater.from(activity?.applicationContext).inflate(R.layout.restart_dialog_layout,container,false)
            alertDialog.setView(dialogView)
            alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            val positive = dialogView.findViewById<Button>(R.id.restart_dialog_positive)
            val negative = dialogView.findViewById<Button>(R.id.restart_dialog_negative)


            positive.setOnClickListener {
                list.value?.forEach{
                    viewModel.updateTask(Task(it.id,it.name,it.time,it.breaks,it.time_breaks,0))
                }
                taskAdapter.notifyDataSetChanged()
                alertDialog.dismiss()
            }

            negative.setOnClickListener {
                alertDialog.dismiss()
            }
            alertDialog.show()
        }

        // Inflate the layout for this fragment
        return inputFragmentView
    }


}