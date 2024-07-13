package com.huangliner.prioritytodo.application.util

object PriorityUtils {

    fun fromPriority(priority: Priority): String {
        return when (priority) {
            Priority.High -> Constants.PRIORITY_HIGH
            Priority.Medium -> Constants.PRIORITY_MEDIUM
            Priority.Low -> Constants.PRIORITY_LOW
        }
    }

    fun toPriority(prioritySt: String): Priority {
        return when (prioritySt) {
            Constants.PRIORITY_HIGH -> Priority.High
            Constants.PRIORITY_MEDIUM -> Priority.Medium
            Constants.PRIORITY_LOW -> Priority.Low
            else -> Priority.Low
        }
    }
}