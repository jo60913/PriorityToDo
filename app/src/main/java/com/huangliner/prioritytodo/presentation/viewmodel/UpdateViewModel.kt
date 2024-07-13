package com.huangliner.prioritytodo.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.huangliner.prioritytodo.data.database.entiry.TodoItem
import com.huangliner.prioritytodo.domain.usecase.DeleteTasksUseCase
import com.huangliner.prioritytodo.domain.usecase.GetTaskDetailUseCase
import com.huangliner.prioritytodo.domain.usecase.UpdateTaskUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpdateViewModel @Inject constructor(
    private val getTaskDetailUsecase: GetTaskDetailUseCase,
    private val updateTaskUseCase: UpdateTaskUseCase,
    private val deleteTasksUseCase: DeleteTasksUseCase
): ViewModel() {
    private val _taskList = MutableLiveData<List<TodoItem>>()

    val taskList : LiveData<List<TodoItem>> = _taskList

    fun getTaskDetail(taskNo:String){
        viewModelScope.launch {
            val taskDetail = getTaskDetailUsecase.execute(taskNo)
            _taskList.postValue(taskDetail)
        }
    }

    fun updateTask(subTasks: List<TodoItem>) {
        viewModelScope.launch {
            updateTaskUseCase.execute(subTasks)
        }
    }

    fun deleteTasks(originSubTask: List<TodoItem>) {
        viewModelScope.launch {
            deleteTasksUseCase.execute(originSubTask)
        }
    }

}