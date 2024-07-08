package com.huangliner.prioritytodo.application.util

sealed class Failure(
    val data: String? = null,
) {
    class FailMessage(data: String) : Failure(data)
    class Exception(data: String) : Failure(data)
}