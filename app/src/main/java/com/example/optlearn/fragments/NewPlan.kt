package com.example.optlearn.fragments

import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.optlearn.R
import com.example.optlearn.mvvm.TaskViewModel
import com.example.optlearn.room.Task
import com.example.optlearn.room.TaskDatabase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*


class NewPlan : Fragment() {

    lateinit var viewModel : TaskViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider.AndroidViewModelFactory(activity?.application!!).create(TaskViewModel::class.java)
        //
        var timeIntoTask = ""
        var switchIsEnabled = false
        var breaksDurationIntoTask = ""
            var inputFragmentView = inflater.inflate(R.layout.fragment_new_plan, container, false)

            val enterButton = inputFragmentView.findViewById<Button>(R.id.new_enterButton)
            val taskName = inputFragmentView.findViewById<EditText>(R.id.new_name)
            val breaksSwitch = inputFragmentView.findViewById<SwitchCompat>(R.id.new_breaksSwitch)
            val breaksPicker = inputFragmentView.findViewById<NumberPicker>(R.id.new_breaksPicker)
            val breaksText = inputFragmentView.findViewById<TextView>(R.id.new_breakTimeText)
            val durationButton = inputFragmentView.findViewById<Button>(R.id.new_durationButton)
            val breaksDurationButton =inputFragmentView.findViewById<Button>(R.id.new_durationBreaksButton)

        durationButton.setOnClickListener {

            val datePickerDialog = TimePickerDialog(inputFragmentView.context,R.style.DialogTheme,
                { p0, hour, minute ->
                Toast.makeText(inputFragmentView.context, "Hour : $hour Minutes  : $minute",Toast.LENGTH_LONG).show()
                    timeIntoTask="$hour:$minute"
                    durationButton.text = timeIntoTask
                }, 0, 0, true
            )

            datePickerDialog.show()

        }

        breaksSwitch.setOnCheckedChangeListener { p0, p1 ->
            if (p1){
                breaksPicker.visibility = View.VISIBLE
                breaksText.visibility = View.VISIBLE
                breaksDurationButton.visibility = View.VISIBLE
                switchIsEnabled = true

            }else {
                breaksPicker.visibility = View.INVISIBLE
                breaksText.visibility = View.INVISIBLE
                breaksDurationButton.visibility = View.INVISIBLE
                switchIsEnabled = false

            }
        }
        //Set NumberPicker values
        breaksPicker.minValue = 0
        breaksPicker.maxValue = 10

        breaksDurationButton.setOnClickListener {
            val datePickerDialog = TimePickerDialog(inputFragmentView.context,R.style.DialogTheme,
                { p0, hour, minute ->
                    Toast.makeText(inputFragmentView.context, "Hour : $hour Minutes  : $minute",Toast.LENGTH_LONG).show()
                    breaksDurationIntoTask="$hour:$minute"
                    breaksDurationButton.text = breaksDurationIntoTask
                }, 0, 0, true
            )

            datePickerDialog.show()
        }
        enterButton.setOnClickListener {
        val nameIntoTask = taskName.text.toString()


            if (switchIsEnabled == false){
                //Insert object without breaks
                       viewModel.insertTask(Task(nameIntoTask,timeIntoTask,0,""))
            }else {
                //Insert object with breaks
                        viewModel.insertTask(Task(nameIntoTask,timeIntoTask,breaksPicker.value,breaksDurationIntoTask))
            }

        }

        return inputFragmentView
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)





    }
}

