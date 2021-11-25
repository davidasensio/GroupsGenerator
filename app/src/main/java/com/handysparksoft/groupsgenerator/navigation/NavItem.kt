package com.handysparksoft.groupsgenerator.navigation

import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class NavItem(
    val baseRoute: String,
    private val navArgs: List<NavArg> = emptyList()
) {
    val route = run {
        val argKeys = navArgs.map { "{${it.key}}" }
        listOf(baseRoute)
            .plus(argKeys)
            .joinToString(separator = "/")
    }
    val args = navArgs.map {
        navArgument(it.key) { type = it.navType }
    }

    object Main : NavItem("main")
    object Detail : NavItem("detail", navArgs = listOf(NavArg.GroupId)) {
        fun createNavRoute(aListId: String) = "$baseRoute/$aListId"
    }
}

enum class NavArg(val key: String, val navType: NavType<*>) {
    GroupId(key = "aListId", navType = NavType.StringType)
}
