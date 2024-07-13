package com.huangliner.prioritytodo.domain.usecase

import com.huangliner.prioritytodo.domain.repository.IRepositry
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class DueDateTodoUsecase @Inject constructor(
    private val repository : IRepositry
){
    fun execute() = repository.getDueDateTodo()

}