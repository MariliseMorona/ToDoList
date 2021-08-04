package com.example.todolist.repository

import androidx.lifecycle.LiveData
import com.example.todolist.database.TaskEntity

class TaskRepository(private val dao: com.example.todolist.database.Dao) {
    val taskItem : LiveData<List<TaskEntity>> = dao.getAllTaskInfo()

    fun addTask(task: TaskEntity){
        dao.insertTask(task)
    }
    fun deleteTask(task: TaskEntity){
        dao.deleteTask(task)
    }
    fun updateTask(task: TaskEntity){
        dao.updateTask(task)
    }
}
