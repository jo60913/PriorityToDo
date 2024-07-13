package com.huangliner.prioritytodo.domain.usecase

import com.huangliner.prioritytodo.data.database.entiry.TodoItem
import com.huangliner.prioritytodo.domain.repository.IRepositry
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@ActivityRetainedScoped
class DeleteTasksUseCase @Inject constructor(
    private val repository : IRepositry
) {
    suspend fun execute(tasks : List<TodoItem>) = withContext(Dispatchers.IO){
        repository.deleteTasks(tasks)
    }
}