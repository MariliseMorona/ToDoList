package com.example.todolist.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.R
import com.example.todolist.database.TaskEntity
import com.example.todolist.databinding.ItemTaskBinding


class TaskListAdapter : RecyclerView.Adapter<TaskListAdapter.TaskViewHolder>(){

    var task = listOf<TaskEntity>()
    set(value){
        field = value
        notifyDataSetChanged()
    }

    var listenerEdit : (TaskEntity) -> Unit = {}
    var listenerDelete : (TaskEntity) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder{
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemTaskBinding.inflate(inflater, parent, false)
        return TaskViewHolder(binding)
    }
    override fun getItemCount(): Int {
        return task.size
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int){
        val task = task[position]
        holder.bind(task)
    }

    inner class TaskViewHolder(
        private val bindind: ItemTaskBinding
        ) : RecyclerView.ViewHolder(bindind.root) {

        fun bind(item: TaskEntity) {
            bindind.txtTitleList.text = item.title
            bindind.txtHourList.text = item.hour
            bindind.mgvMore.setOnClickListener {
                showPopup(item)
            }
        }

        private fun showPopup(item: TaskEntity) {
            val ivMore = bindind.mgvMore
            val popupMenu = PopupMenu(ivMore.context, ivMore)
            popupMenu.menuInflater.inflate(R.menu.menu, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.action_edit -> listenerEdit
                    R.id.action_delete -> listenerDelete
                }
                return@setOnMenuItemClickListener true
            }
            popupMenu.show()
        }
    }
        fun setData(newTask : List<TaskEntity>) {
            task = newTask
            notifyDataSetChanged()
        }

        fun getData(position: Int) : TaskEntity {
            return task.get(position)
        }
    }

/*
class DiffCallBack : DiffUtil.ItemCallback<Task>(){
    override fun areItemsTheSame(oldItem: Task, newItem: Task) = oldItem == newItem
    override fun areContentsTheSame(oldItem: Task, newItem: Task) = oldItem.id  == newItem.id
}*/
