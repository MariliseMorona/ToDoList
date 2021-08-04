package com.example.todolist.ui

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.todolist.database.Dao
import com.example.todolist.database.RoomTask
import com.example.todolist.database.TaskEntity
import com.example.todolist.databinding.ActivityAddTaskBinding
import com.example.todolist.di.TaskDataSource
import com.example.todolist.extensions.format
import com.example.todolist.extensions.text
import com.example.todolist.repository.TaskRepository
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.util.*

class AddTaskActivity: AppCompatActivity() {

    private lateinit var binding: ActivityAddTaskBinding
    lateinit var database : RoomTask
    lateinit var currentTask : TaskEntity
    //private var allTasks : MutableLiveData<List<TaskEntity>> = MutableLiveData()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)
        insertListeners()


    }
     private fun insertListeners() {
     binding.txtInputDate.editText?.setOnClickListener{
         val datePicker = MaterialDatePicker.Builder.datePicker()
             .build()
         datePicker.addOnPositiveButtonClickListener {
            val timeZone = TimeZone.getDefault()
            val offset = timeZone.getOffset(Date().time) * -1
            binding.txtInputDate.text = Date(it + offset).format()

         }
             datePicker.show(supportFragmentManager, "DATE_PICKER_TAG")
     }
     binding.txtInputHour.editText?.setOnClickListener{
         val timePicker = MaterialTimePicker.Builder()
             .setTimeFormat(TimeFormat.CLOCK_24H)
             .build()
         timePicker.addOnPositiveButtonClickListener {
         val minute = if(timePicker.minute in 0..9) "${timePicker.minute}" else timePicker.minute
         val hour = if(timePicker.hour in 0..9) "${timePicker.hour}" else timePicker.hour
         binding.txtInputHour.text = "$hour:$minute"
         }
         timePicker.show(supportFragmentManager, null)

     }
     binding.btnCanceledTask.setOnClickListener {
        finish()
     }

     binding.btnAddTask.setOnClickListener {
        val task = TaskEntity(
            title = binding.txtInputTitulo.text,
            description = binding.txtInputDescription.text,
            date = binding.txtInputDate.text,
            hour = binding.txtInputHour.text,
            id = intent.getIntExtra(TASK_ID, 0)
        )
         doAsync {
             database.itemDao().insertTask(task)

             uiThread {
                 setResult(Activity.RESULT_OK)
                 finish() //Finaliza a Activity para Retornar a Tela principal
             }
             //insertTask()
             /*setResult(Activity.RESULT_OK)
         finish()
     }*/
         }
     }
     }
    companion object{
        const val TASK_ID = "task_id"
    }
}
