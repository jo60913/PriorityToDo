package com.huangliner.prioritytodo.application.util

import androidx.room.TypeConverter
import com.huangliner.prioritytodo.application.util.Constants.Companion.CATEGORY_PERSONAL
import com.huangliner.prioritytodo.application.util.Constants.Companion.CATEGORY_WORK
import com.huangliner.prioritytodo.application.util.Constants.Companion.PRIORITY_HIGH
import com.huangliner.prioritytodo.application.util.Constants.Companion.PRIORITY_LOW
import com.huangliner.prioritytodo.application.util.Constants.Companion.PRIORITY_MEDIUM
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class DateTypeConverter {
    private val dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    private val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    private val timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss")

    @TypeConverter
    fun fromLocalDateTime(value: LocalDateTime?): String? {
        return value?.format(dateTimeFormatter)
    }

    @TypeConverter
    fun toLocalDateTime(value: String?): LocalDateTime? {
        return value?.let { LocalDateTime.parse(it, dateTimeFormatter) }
    }

    @TypeConverter
    fun fromLocalDate(localDate: LocalDate?): String? {
        return localDate?.format(dateFormatter)
    }

    @TypeConverter
    fun toLocalDate(value: String?): LocalDate? {
        return value?.let { LocalDate.parse(it, dateFormatter) }
    }

    @TypeConverter
    fun fromLocalTime(localTime: LocalTime?): String? {
        return localTime?.format(timeFormatter)
    }

    @TypeConverter
    fun toLocalTime(value: String?): LocalTime? {
        return value?.let { LocalTime.parse(it, timeFormatter) }
    }

    @TypeConverter
    fun fromPriority(priority: Priority): String {
        return when (priority) {
            Priority.High -> PRIORITY_HIGH
            Priority.Medium -> PRIORITY_MEDIUM
            Priority.Low -> PRIORITY_LOW
        }
    }

    @TypeConverter
    fun toPriority(prioritySt: String): Priority {
        return when (prioritySt) {
            PRIORITY_HIGH -> Priority.High
            PRIORITY_MEDIUM -> Priority.Medium
            PRIORITY_LOW -> Priority.Low
            else -> Priority.Low
        }
    }

    @TypeConverter
    fun fromCategory(category: Category): String {
        return when (category) {
            Category.Personal -> CATEGORY_PERSONAL
            Category.Work -> CATEGORY_WORK
        }
    }

    @TypeConverter
    fun toCategory(categorySt: String): Category {
        return when (categorySt) {
            CATEGORY_PERSONAL -> Category.Personal
            CATEGORY_WORK -> Category.Work
            else -> Category.Personal
        }
    }
}