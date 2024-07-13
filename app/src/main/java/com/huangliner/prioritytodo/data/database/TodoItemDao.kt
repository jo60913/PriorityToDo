package com.huangliner.prioritytodo.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.huangliner.prioritytodo.data.database.entiry.TodoItem

@Dao
interface TodoItemDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertMainTodoItem(todoItem: TodoItem): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertSubTodoItem(todoItem: List<TodoItem>)

    @Delete
    suspend fun deleteTodoItem(todoItem: TodoItem)

    @Query("SELECT * FROM TodoItem WHERE `No` = :no")
    suspend fun selectTodoItemByNo(no: String) : TodoItem

    @Query("SELECT *\n" +
            "FROM TodoItem\n" +
            "WHERE DueDate BETWEEN datetime('now', 'localtime') AND datetime('now', 'localtime', '+1 day') AND ParentNo = 0")
    fun getDueDateTodo(): List<TodoItem>

    @Query("SELECT * FROM TodoItem WHERE Title LIKE '%' || :keyWord || '%' OR Content LIKE '%' || :keyWord || '%'")
    suspend fun searchTodoItemByKeyWord(keyWord: String): List<TodoItem>
}