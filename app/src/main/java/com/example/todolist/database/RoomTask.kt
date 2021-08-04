package com.example.todolist.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.launch

@Database( entities = [TaskEntity::class], version = 1)
abstract class RoomTask : RoomDatabase(){

    abstract fun itemDao(): Dao

    companion object{
        private var INSTANCE: RoomTask? = null

        @Synchronized
        fun getTaskDatabase(context: Context): RoomTask{
            return INSTANCE ?: synchronized(this){
            val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RoomTask::class.java,
                    "TaskDB")
                    .addCallback(TaskCallback(context))
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

    private class TaskCallback(private val scope: Context): RoomDatabase.Callback(){
        override fun onCreate(db: SupportSQLiteDatabase){
            super.onCreate(db)
            INSTANCE?.let {
                scope.runCatching {
                    populateDatabase(it.itemDao())
                }
            }
        }
        private fun populateDatabase (itemDao: Dao){
            val task = getAll()
            itemDao.insertTask(task)
        }
        fun getAll(): List<TaskEntity>{
            return listOf()
        }
    }
}
