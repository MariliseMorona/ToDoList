package com.example.todolist.di

import com.example.todolist.database.RoomTask
import com.example.todolist.repository.TaskRepository
import com.example.todolist.viewmodel.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object TaskDataSource {
    var module = module {
        factory {
            CoroutineScope(Dispatchers.IO)
        }

        single {
            RoomTask.getTaskDatabase(context = get())
        }

        single {
            get<RoomTask>().itemDao()
        }

        factory {
            TaskRepository(get())
        }

        viewModel { MainViewModel(get()) }
    }

}
    /*private val list = arrayListOf<Task>()

    fun getList() = list.toList()

    fun insertTask(task: Task){
        if(task.id == 0){
            list.add(task.copy(id = list.size+1))
        }else{
            list.remove(task)
            list.add(task)
        }

    }

    fun findById(taskId: Int) = list.find {
           it.id == taskId
       }

    fun deleteTask(task: Task) {
       list.remove(task)
    }
}
*/