package com.huangliner.prioritytodo.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.huangliner.prioritytodo.application.util.Category
import com.huangliner.prioritytodo.application.util.Priority
import com.huangliner.prioritytodo.domain.usecase.AddTaskUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class AddTaskViewModel @Inject constructor(
    private val addTaskUsecase: AddTaskUsecase
) : ViewModel() {
    fun addTask(
        title:String,
        content:String,
        endDate : LocalDateTime,
        priority: Priority,
        subTasks:List<String>,
        category: Category,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            addTaskUsecase.execute(
                title = title,
                content = content,
                endDate = endDate,
                priority = priority,
                subTasks = subTasks,
                category = category,
            )
        }
    }


}