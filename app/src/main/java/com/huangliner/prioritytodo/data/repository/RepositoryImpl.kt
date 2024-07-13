package com.huangliner.prioritytodo.data.repository

import com.huangliner.prioritytodo.data.database.TodoItemDao
import com.huangliner.prioritytodo.data.database.entiry.TodoItem
import com.huangliner.prioritytodo.data.model.request.AddAccountRequest
import com.huangliner.prioritytodo.data.model.request.LoginRequest
import com.huangliner.prioritytodo.data.network.Backend
import com.huangliner.prioritytodo.domain.repository.IRepositry
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val backend: Backend,
    private val todoItemDao: TodoItemDao
) : IRepositry {
    override suspend fun login(loginRequest: LoginRequest) = backend.login(loginRequest = loginRequest)
    override suspend fun addAccount(addAccountRequest: AddAccountRequest) = backend.addAccount(addAccountRequest)
    override suspend fun upsertMainTodoItem(todoItem: TodoItem) = todoItemDao.upsertMainTodoItem(todoItem = todoItem)
    override suspend fun upsertSubTask(todoItemList: List<TodoItem>) = todoItemDao.upsertSubTodoItem(todoItem = todoItemList)
    override suspend fun getDueDateTodo() = todoItemDao.getDueDateTodo()
    override suspend fun searchTodoItem(keyWord: String): List<TodoItem> = todoItemDao.searchTodoItemByKeyWord(keyWord = keyWord)

}