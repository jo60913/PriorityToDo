package com.huangliner.prioritytodo.domain.usecase

import com.huangliner.prioritytodo.data.database.entiry.TodoItem
import com.huangliner.prioritytodo.domain.repository.IRepositry
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

@ActivityRetainedScoped
class UpdateTaskUseCase @Inject constructor(
    private val repository : IRepositry
) {
    suspend fun execute(tasks : List<TodoItem>) = withContext(Dispatchers.IO){
        Timber.e("更新 todo")
        repository.upsertSubTask(tasks)
    }
}