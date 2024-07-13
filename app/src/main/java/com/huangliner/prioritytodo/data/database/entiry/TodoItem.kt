package com.huangliner.prioritytodo.data.database.entiry

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.huangliner.prioritytodo.application.util.Category
import com.huangliner.prioritytodo.application.util.Priority
import java.time.LocalDate

@Entity(tableName = "TodoItem")
data class TodoItem(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "No")
    var no: Long = 0,

    @ColumnInfo(name = "ParentNo")
    var parentNo: String,

    @ColumnInfo(name = "IsDone")
    var isDown: Boolean,

    @ColumnInfo(name = "Title")
    var title: String,

    @ColumnInfo(name = "Content")
    var content: String,

    @ColumnInfo(name = "Priority")
    var priority: Priority,

    @ColumnInfo(name = "Category")
    var category: Category,

    @ColumnInfo(name = "DueDate")
    var dueDate: LocalDate,

    @ColumnInfo(name = "CreateDate")
    var createDate: LocalDate
) {
    constructor(
        parentNo: String,
        isDown: Boolean,
        title: String,
        content: String,
        priority: Priority,
        category: Category,
        dueDate: LocalDate,
        createDate: LocalDate
    ) : this(
        no = 0,
        parentNo,
        isDown,
        title,
        content,
        priority,
        category,
        dueDate,
        createDate
    )

    constructor() : this(
        no = 0,
        "",
        false,
        "",
        "",
        Priority.Low,
        Category.Work,
        LocalDate.now(),
        LocalDate.now()
    )
}