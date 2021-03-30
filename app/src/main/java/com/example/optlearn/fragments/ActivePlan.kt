package com.example.optlearn.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.optlearn.R
import com.example.optlearn.mvvm.TaskViewModel
import com.example.optlearn.recyclers.ActiveRecyclerViewAdapter


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
        val recyclerViewAdapter =
            inputFragmentView?.findViewById<RecyclerView>(R.id.active_recyclerView)

       val list = viewModel.getAllTasks()

      list.observe(viewLifecycleOwner, Observer {
         if (it.isNotEmpty()) {
            taskAdapter = ActiveRecyclerViewAdapter(it)
            recyclerViewAdapter?.adapter = taskAdapter
        }
      })
        recyclerViewAdapter?.layoutManager = LinearLayoutManager(
            activity?.applicationContext, LinearLayoutManager.VERTICAL, false
        )
        recyclerViewAdapter?.setHasFixedSize(true)

        // Inflate the layout for this fragment
        return inputFragmentView
    }


}