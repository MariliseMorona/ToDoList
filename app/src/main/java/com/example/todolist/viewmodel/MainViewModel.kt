package com.example.todolist.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.todolist.database.RoomTask
import com.example.todolist.database.TaskEntity
import com.example.todolist.repository.TaskRepository

class MainViewModel(private  val repository: TaskRepository): ViewModel() {
    val task: LiveData<List<TaskEntity>> = repository.taskItem

}