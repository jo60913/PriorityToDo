package com.huangliner.prioritytodo.application.util

import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class LocalDateUtil {
    companion object {

        fun LocalDate.toyyyyMMddString(): String {
            val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            return this.format(dateFormatter)
        }

        fun LocalTime.toHHmmSSString(): String {
            val timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss")
            return this.format(timeFormatter)
        }

    }
}