package com.huangliner.prioritytodo.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.huangliner.prioritytodo.application.util.RecyclerViewDiffUtil
import com.huangliner.prioritytodo.data.database.entiry.TodoItem
import com.huangliner.prioritytodo.databinding.DueDateTodoItemRowLayoutBinding

class HomeDueDateRecyclerViewAdapter : RecyclerView.Adapter<HomeDueDateRecyclerViewAdapter.ItemLayout>() {
    private var dueDateItemList = emptyList<TodoItem>()
    private lateinit var clickListener :(TodoItem) -> Unit
    class ItemLayout(val binding: DueDateTodoItemRowLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: TodoItem) {
            binding.todoItem = item
        }

        companion object {
            fun from(parent: ViewGroup): ItemLayout {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = DueDateTodoItemRowLayoutBinding.inflate(layoutInflater, parent, false)
                return ItemLayout(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemLayout = ItemLayout.from(parent)

    override fun onBindViewHolder(holder: ItemLayout, position: Int) {
        val todoItem = dueDateItemList[position]
        holder.bind(todoItem)
        holder.binding.clDueDateRootLayout.setOnClickListener {
            this.clickListener(todoItem)
        }
    }

    override fun getItemCount() = dueDateItemList.size
    fun setClickListener(listener : (TodoItem)->Unit) {
        this.clickListener = listener
    }

    fun submitList(newList : List<TodoItem>){
        val diff = RecyclerViewDiffUtil(this.dueDateItemList, newList)
        val diffUtilResult = DiffUtil.calculateDiff(diff)
        this.dueDateItemList = newList
        diffUtilResult.dispatchUpdatesTo(this)
    }
}