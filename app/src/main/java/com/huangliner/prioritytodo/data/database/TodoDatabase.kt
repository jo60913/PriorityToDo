package com.huangliner.prioritytodo.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.huangliner.prioritytodo.application.util.Constants.Companion.DATABASE_VERSION
import com.huangliner.prioritytodo.application.util.DateTypeConverter
import com.huangliner.prioritytodo.data.database.entiry.TodoItem

@Database(
    entities = [TodoItem::class],
    version = DATABASE_VERSION
)
@TypeConverters(DateTypeConverter::class)
abstract class TodoDatabase : RoomDatabase() {
    abstract fun getTodoItemDao():TodoItemDao
}