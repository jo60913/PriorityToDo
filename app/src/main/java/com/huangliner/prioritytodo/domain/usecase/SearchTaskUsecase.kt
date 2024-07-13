package com.huangliner.prioritytodo.domain.usecase

import com.huangliner.prioritytodo.domain.repository.IRepositry
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@ActivityRetainedScoped
class SearchTaskUsecase @Inject constructor(
    private val IRepositry: IRepositry
) {
    suspend fun execute(keyWord: String) = withContext(Dispatchers.IO){
        IRepositry.searchTodoItem(keyWord)
    }
}