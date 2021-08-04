package com.example.todolist.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.example.todolist.database.RoomTask
import com.example.todolist.database.TaskEntity
import com.example.todolist.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val adapter by lazy { TaskListAdapter() }
    private lateinit var listTask : LiveData<List<TaskEntity>>
    lateinit var database : RoomTask

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

       setupDatabase()
       setupRecyclerView()
       insertListeners()

        listTask.observe(this, Observer {
            it?.let { list -> adapter.setData(list) }
        })

        binding.fbtNewTask.setOnClickListener {
            val newTaskActivity = Intent(this, AddTaskActivity::class.java)
            startActivity(newTaskActivity)
        }
    }

    //Criação do Banco de Dados
    private fun setupDatabase() {
        database = RoomTask.getTaskDatabase(this)!!
        listTask = database.itemDao().getAllTaskInfo()
    }

    //Configuração do RecyclerView
    private fun setupRecyclerView() {
        binding.rcvTasks.adapter = adapter
    }
    //Menu item
    private fun insertListeners() {
        binding.fbtNewTask.setOnClickListener {
            startActivityIfNeeded(Intent(this, AddTaskActivity::class.java), CREATE_NEW_TASK)
        }

        adapter.listenerEdit = {
            val intent = Intent(this, AddTaskActivity::class.java)
            intent.putExtra(AddTaskActivity.TASK_ID, it.id)
            startActivityIfNeeded(intent, CREATE_NEW_TASK)
        }

        adapter.listenerDelete = {
            database.itemDao().deleteTask(it)

        }
    }

    /*private fun resetData() {

        //Fazendo a Query em background e Setando o Adapter novamente
        doAsync {
            val result = queryData()
            uiThread {
                adapter.setData(result)
            }
        }


    private fun queryData(): LiveData<List<TaskEntity>> {
        return database.itemDao().getAllTaskInfo()

    }}*/
    companion object {
        private const val CREATE_NEW_TASK = 1000
    }
}




        /*updateList()
        insertListeners()
    }

    private fun insertListeners() {
        binding.fbtNewTask.setOnClickListener{
            startActivityIfNeeded(Intent(this, AddTaskActivity::class.java), CREATE_NEW_TASK )
        }

        adapter.listenerEdit = {
            val intent = Intent(this, AddTaskActivity::class.java)
            intent.putExtra(AddTaskActivity.TASK_ID, it.id)
            startActivityIfNeeded(intent, CREATE_NEW_TASK )
        }

        adapter.listenerDelete = {
            deleteTask()
            updateList()
        }

    }

    private fun deleteTask() {
        TODO("Not yet implemented")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CREATE_NEW_TASK && resultCode == Activity.RESULT_OK) updateList()
    }

    private fun updateList(taskRepository: TaskRepository) {
        val list = ()
        binding.includeEmptyState.emptyState.visibility = if(list.isEmpty()) View.VISIBLE
        else View.GONE

        adapter.submitList(list)

    }


    companion object {
        private const val CREATE_NEW_TASK = 1000
    }
}*/