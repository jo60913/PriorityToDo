package com.huangliner.prioritytodo.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.huangliner.prioritytodo.application.util.NetworkResult
import com.huangliner.prioritytodo.data.database.entiry.TodoItem
import com.huangliner.prioritytodo.domain.usecase.DueDateTodoUsecase
import com.huangliner.prioritytodo.domain.usecase.SearchTaskUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val dueDateTodoUsecase: DueDateTodoUsecase,
    private val searchTaskUsecase: SearchTaskUsecase
) :ViewModel(){
    private val _todoItem = MutableLiveData<NetworkResult<List<TodoItem>>>()

    val todoItem : LiveData<NetworkResult<List<TodoItem>>> = _todoItem

    init {
        getDueDateTodo()
    }

    fun getDueDateTodo(){
        _todoItem.value = NetworkResult.Loading()
        viewModelScope.launch {
            val dueDateTodoList = dueDateTodoUsecase.execute()
            _todoItem.postValue(NetworkResult.Success(dueDateTodoList))
        }
    }

    fun searchTodo(keyWord : String) {
        _todoItem.value = NetworkResult.Loading()
        viewModelScope.launch {
            val result = searchTaskUsecase.execute(keyWord)
            _todoItem.postValue(NetworkResult.Success(result))
        }
    }
}