package com.example.optlearn.fragments

import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.optlearn.R
import com.example.optlearn.mvvm.TaskViewModel
import com.example.optlearn.room.Task
import com.example.optlearn.viewModel
import kotlinx.android.synthetic.main.task_layout.*
import java.util.concurrent.TimeUnit


class TaskFragment : Fragment() {
    lateinit var timeTV: TextView
    lateinit var breakTimeTV: TextView
    lateinit var countDownTimer: CountDownTimer
    lateinit var breakCountDownTimer: CountDownTimer
    lateinit var progressBar: ProgressBar
     var timeBreaks : String? = null
    var timerIsRunning = false
    var timeInStopMoment = 0L


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val inputFragmentView = inflater.inflate(R.layout.task_layout, container, false)
        val nameTV = inputFragmentView.findViewById<TextView>(R.id.task_nameTV)
        timeTV = inputFragmentView.findViewById(R.id.task_timeTV)
        val breakTV = inputFragmentView.findViewById<TextView>(R.id.task_breakTV)
        val breakBT = inputFragmentView.findViewById<Button>(R.id.task_breakBT)
         breakTimeTV = inputFragmentView.findViewById<Button>(R.id.task_break_timeTV)
        val finishBT = inputFragmentView.findViewById<Button>(R.id.task_finishBT)
         progressBar = inputFragmentView.findViewById<ProgressBar>(R.id.task_progressBar)
        //Get data from clicked recyclerview item
        val name = arguments?.getString("name")
        val id = arguments?.getInt("id")!!
        val time = arguments?.getString("time")
        var breaks = arguments?.getString("breaks")
         timeBreaks = arguments?.getString("time_breaks")
        var success = arguments?.getInt("success")

        var timeInMillis = changeStringTimetoMills(time.toString())
        progressBar.min = 0
        if (timeInStopMoment == 0L)
        progressBar.max = timeInMillis.toInt()
        progressBar.progress = progressBar.max
        progressBar.scaleX = -1F
        var viewModel = ViewModelProvider(this).get(TaskViewModel::class.java)
        breakBT.setOnClickListener {
            //Wystartowanie zegara
            when(breakBT.text) {
                "Start" -> {
                  startTimer(timeInMillis,timeTV,"Normal")
                breakBT.text = "Break"
                }
                "Break"-> {
                    //Jezeli zegar jest wystartowany sprawdza
                    if (breaks?.toLong()!! > 0 && timerIsRunning) {
                        breaks = (breaks!!.toInt() - 1).toString()
                        breakTV.text = "Breaks : $breaks"
                        breakBT.text = "Finish break"
                        startTimer(changeStringTimetoMills(timeBreaks.toString()),breakTimeTV,"Break")
                        pauseTimer()
                    }
                }
                "Finish break"->{
                    resumeTimer()
                    breakCountDownTimer.cancel()
                    breakBT.text = "Break"
                }
            }

        }

        nameTV.text = name.toString()

         convertTime(changeStringTimetoMills(time.toString()),timeTV)
        breakTV.text = "Breaks : $breaks"

        finishBT.setOnClickListener {


            progressBar.progress = 0

            val dialogBuilder = AlertDialog.Builder(context)
            val alertDialog = dialogBuilder.create()
            var dialogView = inflater.inflate(R.layout.finish_dialog_layout, container, false)
            alertDialog.setView(dialogView)
            alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            val positive = dialogView.findViewById<Button>(R.id.finish_dialog_positive)
            val negative = dialogView.findViewById<Button>(R.id.finish_dialog_negative)
            val neutral = dialogView.findViewById<Button>(R.id.finish_dialog_neutral)
            alertDialog.show()

            positive.setOnClickListener {
                alertDialog.dismiss()
                viewModel.updateTask(Task(id,name,time,breaks?.toInt(),timeBreaks,1))
                inputFragmentView.findNavController().navigate(R.id.action_taskFragment_to_activePlan)
            }

            negative.setOnClickListener {
                viewModel.updateTask(Task(id,name,time,breaks?.toInt(),timeBreaks,2))
                alertDialog.dismiss()
                inputFragmentView.findNavController().navigate(R.id.action_taskFragment_to_activePlan)
            }
            neutral.setOnClickListener {
                alertDialog.dismiss()
                progressBar.progress = timeInStopMoment.toInt()
            }
        }
        return inputFragmentView
    }
    //Wystartowanie zegara
    fun startTimer(time : Long,textview : TextView,timer: String){
        if (timer == "Normal") {
            countDownTimer = object : CountDownTimer(time, 1000) {
                override fun onTick(millsUntilFinished: Long) {
                    convertTime(millsUntilFinished, textview)
                    timerIsRunning = true
                    progressBar.progress = millsUntilFinished.toInt()
                    timeInStopMoment = millsUntilFinished

                }

                override fun onFinish() {
                    Toast.makeText(activity?.applicationContext, "Finish", Toast.LENGTH_LONG).show()
                    timerIsRunning = false
                }
            }.start()
        }else if (timer == "Break"){
            breakCountDownTimer = object : CountDownTimer(time, 1000) {
                override fun onTick(millsUntilFinished: Long) {
                    convertTime(millsUntilFinished, textview)
                    timerIsRunning = true

                }

                override fun onFinish() {
                    Toast.makeText(context, "Finish", Toast.LENGTH_LONG).show()
                    timerIsRunning = false
                }
            }.start()
        }

    }

    fun pauseTimer(){
        countDownTimer.cancel()
        //Wystartowanie zegara przerwy
        breakTimeTV.visibility = View.VISIBLE
        breakTimeTV.text = timeBreaks
    }

    fun resumeTimer(){
        pauseTimer()
        breakTimeTV.visibility = View.GONE
        startTimer(timeInStopMoment,timeTV,"Normal")
    }
    fun convertTime(time : Long,textView: TextView){
        val hours = TimeUnit.MILLISECONDS.toHours(time) % 24
        val minutes = TimeUnit.MILLISECONDS.toMinutes(time) % 60
        val seconds = TimeUnit.MILLISECONDS.toSeconds(time) % 60

        textView.text = "$hours:$minutes:$seconds"
    }


    fun changeStringTimetoMills(time : String): Long {
        var list: List<String>? = time?.split(":")
          return  TimeUnit.HOURS.toMillis(list!![0].toLong()) + TimeUnit.MINUTES.toMillis(list[1].toLong())
    }




}