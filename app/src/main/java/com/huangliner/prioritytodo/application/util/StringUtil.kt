package com.huangliner.prioritytodo.application.util

import java.io.UnsupportedEncodingException
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.time.format.DateTimeFormatter
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeParseException

class StringUtil {
    companion object {
        fun String.toLocalDate() = LocalDate.parse(this)

        fun String.toLocalDateTime(): LocalDateTime {
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            return LocalDateTime.parse(this, formatter)
        }

        fun String.convertDateStringToDateInt() : DateInt {
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            val localDate = LocalDate.parse(this, formatter)

            val year = localDate.year
            val month = localDate.monthValue - 1
            val day = localDate.dayOfMonth
            return DateInt(year,month,day)
        }
    }
}