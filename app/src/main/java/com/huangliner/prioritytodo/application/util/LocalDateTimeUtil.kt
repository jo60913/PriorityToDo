package com.huangliner.prioritytodo.application.util

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class LocalDateTimeUtil {
    companion object {
        fun LocalDateTime.toyyyyMMdd(): String {
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            return this.format(formatter)
        }

        fun LocalDateTime.toHHmmss(): String {
            val formatter = DateTimeFormatter.ofPattern("HH:mm:ss")
            return this.format(formatter)
        }
    }
}