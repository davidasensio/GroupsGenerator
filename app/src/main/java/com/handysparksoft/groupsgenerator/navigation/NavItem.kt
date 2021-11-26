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

    object Main : NavItem(baseRoute = "main")
    object Detail : NavItem(baseRoute = "detail", navArgs = listOf(NavArg.ListId)) {
        fun createNavRoute(aListId: String) = "$baseRoute/$aListId"
    }
    object Create : NavItem(baseRoute = "create")
    object Generate : NavItem(baseRoute = "generate", navArgs = listOf(NavArg.ListId)) {
        fun createNavRoute(aListId: String) = "$baseRoute/$aListId"
    }
}

enum class NavArg(val key: String, val navType: NavType<*>) {
    ListId(key = "aListId", navType = NavType.StringType)
}
