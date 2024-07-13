package com.huangliner.prioritytodo.application.util

sealed class Priority{
    object Low : Priority()
    object Medium : Priority()
    object High : Priority()
}
