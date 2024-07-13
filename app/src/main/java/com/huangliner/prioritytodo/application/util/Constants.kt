package com.huangliner.prioritytodo.application.util

class Constants {
    companion object{
        const val BACKEND_URL = "https://priority-todo-api.vercel.app/"
        const val HTTP_READ_TIMEOUT = 30L
        const val HTTP_CONNECT_TIMEOUT = 30L

        const val BACKEND_ERROR_SUCCESSFUL_ERROR_FLAG = "0"
        const val DATABASE_VERSION = 1

        const val PRIORITY_HIGH = "0"
        const val PRIORITY_MEDIUM = "1"
        const val PRIORITY_LOW = "2"

        const val CATEGORY_WORK = "0"
        const val CATEGORY_PERSONAL = "1"
    }
}