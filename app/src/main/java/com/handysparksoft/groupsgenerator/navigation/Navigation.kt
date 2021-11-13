package com.handysparksoft.groupsgenerator.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.handysparksoft.groupsgenerator.ui.screens.detail.DetailScreen
import com.handysparksoft.groupsgenerator.ui.screens.main.MainScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = NavItem.Main.route) {
        composable(NavItem.Main.route) {
            MainScreen(onGroupClick = { group ->
                navController.navigate(NavItem.Detail.createNavRoute(group.id))
            })
        }
        composable(
            route = NavItem.Detail.route,
            arguments = NavItem.Detail.args
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt(NavArg.GroupId.key)
            requireNotNull(
                value = id,
                lazyMessage = { "Required value should be not null. Detail screen needs an id" }
            )
            DetailScreen(groupId = id, onUpClick = { navController.popBackStack() })
        }
    }
}
