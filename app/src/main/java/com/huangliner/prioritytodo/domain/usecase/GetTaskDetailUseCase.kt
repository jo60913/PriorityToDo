package com.huangliner.prioritytodo.domain.usecase

import com.huangliner.prioritytodo.data.database.entiry.TodoItem
import com.huangliner.prioritytodo.domain.repository.IRepositry
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@ActivityRetainedScoped
class GetTaskDetailUseCase @Inject constructor(
    private val repository : IRepositry
) {
    suspend fun execute(taskNo : String) = withContext(Dispatchers.IO){
        val mainTask = repository.getTaskByTaskNo(taskNo)
        val subTask = repository.getSubTaskByParentNo(taskNo)
        val taskList = mutableListOf<TodoItem>()
        taskList.add(mainTask)
        taskList.addAll(subTask)
        taskList
    }
}