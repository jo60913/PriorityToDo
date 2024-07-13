package com.huangliner.prioritytodo.domain.usecase

import com.huangliner.prioritytodo.application.util.Category
import com.huangliner.prioritytodo.application.util.Priority
import com.huangliner.prioritytodo.data.database.entiry.TodoItem
import com.huangliner.prioritytodo.domain.repository.IRepositry
import dagger.hilt.android.scopes.ActivityRetainedScoped
import java.time.LocalDate
import java.time.LocalDateTime
import javax.inject.Inject

/**
 * 新增任務
 */
@ActivityRetainedScoped
class AddTaskUsecase @Inject constructor(
    private val repository :IRepositry
) {
    suspend fun execute(
        title: String,
        content: String,
        endDate: LocalDateTime,
        priority: Priority,
        subTasks: List<String>,
        category: Category
    ) {
        val todoItem = TodoItem(
            title = title,
            parentNo = "0",
            content = content,
            isDown = false,
            priority = priority,
            category = category,
            dueDate = endDate,
            createDate = LocalDateTime.now()
        )
        val mainTaskNo = repository.upsertMainTodoItem(todoItem)

        val subTask = subTasks.map {
            if(it.trim().isEmpty())
                return
            return@map TodoItem(
                title = "",
                parentNo = mainTaskNo.toString(),
                content = it,
                isDown = false,
                priority = priority,
                category = category,
                dueDate = endDate,
                createDate = LocalDateTime.now()
            )
        }
        repository.upsertSubTask(todoItemList = subTask)
    }
}