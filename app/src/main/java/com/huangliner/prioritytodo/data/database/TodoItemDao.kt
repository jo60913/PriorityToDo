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
}