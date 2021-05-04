package com.example.optlearn.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.example.optlearn.R




class SuccessOrFailureTaskFragment : Fragment() {
    lateinit var inputFragmentView : View
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //Get data from clicked recyclerview item
        val name = arguments?.getString("name")
        val id = arguments?.getInt("id")!!
        val time = arguments?.getString("time")
        var breaks = arguments?.getString("breaks")
        var timeBreaks = arguments?.getString("time_breaks")
        var success = arguments?.getInt("success")
        if (success == 1) {
            inputFragmentView = inflater.inflate(R.layout.success_task_layout, container, false)
        }else if (success == 2){
            inputFragmentView = inflater.inflate(R.layout.failure_task_layout, container, false)
        }
        val nameTV = inputFragmentView.findViewById<TextView>(R.id.sof_task_nameTV)
        var progressBar = inputFragmentView.findViewById<ProgressBar>(R.id.sof_task_progressBar)

        val breakTV = inputFragmentView.findViewById<TextView>(R.id.sof_task_breakTV)
        val timeTV = inputFragmentView.findViewById<TextView>(R.id.sof_task_timeTV)
        val finishBT = inputFragmentView.findViewById<Button>(R.id.sof_task_finishBT)

        nameTV.text = name
        progressBar.progress = 100


        finishBT.setOnClickListener {
            val action = SuccessOrFailureTaskFragmentDirections.actionSuccessOrFailureTaskFragmentToActivePlan()
            findNavController().navigate(action)
        }


        return inputFragmentView
    }


}