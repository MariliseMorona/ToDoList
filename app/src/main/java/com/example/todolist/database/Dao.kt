package com.example.todolist.database

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.Dao
import com.example.todolist.ui.MainActivity

@Dao
interface Dao {

    @Query(value = "SELECT * fROM TaskDB ORDER BY id DESC")
    fun getAllTaskInfo(): LiveData<List<TaskEntity>>

    @Insert
    fun insertTask(task: List<TaskEntity>)

    @Insert
    fun insertTask(task: TaskEntity?)

    @Delete
    fun deleteTask(task: TaskEntity)

    @Update
    fun updateTask(task: TaskEntity?)
}