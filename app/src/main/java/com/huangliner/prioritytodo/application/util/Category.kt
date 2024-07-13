package com.huangliner.prioritytodo.application.util

sealed class Category {
    object Work : Category()
    object Personal : Category()
}