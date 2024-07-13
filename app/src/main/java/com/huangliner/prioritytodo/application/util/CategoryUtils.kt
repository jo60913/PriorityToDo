package com.huangliner.prioritytodo.application.util

object CategoryUtils {
    fun fromCategory(category: Category): String {
        return when (category) {
            Category.Personal -> Constants.CATEGORY_PERSONAL
            Category.Work -> Constants.CATEGORY_WORK
        }
    }

    fun toCategory(categorySt: String): Category {
        return when (categorySt) {
            Constants.CATEGORY_PERSONAL -> Category.Personal
            Constants.CATEGORY_WORK -> Category.Work
            else -> Category.Personal
        }
    }
}