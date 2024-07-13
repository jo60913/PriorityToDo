package com.huangliner.prioritytodo.application.util

import java.time.LocalTime
import java.time.format.DateTimeFormatter

class LocalTimeUtil {
    companion object{
        fun LocalTime.toHHmmss(): String {
            return this.format(DateTimeFormatter.ofPattern("HH:mm:ss"))
        }
    }
}