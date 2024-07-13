package com.huangliner.prioritytodo.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.huangliner.prioritytodo.domain.usecase.DueDateTodoUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val dueDateTodoUsecase: DueDateTodoUsecase
) :ViewModel(){
    val dueDateList = dueDateTodoUsecase.execute().flowOn(Dispatchers.IO)
}