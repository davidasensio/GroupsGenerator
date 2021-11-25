package com.handysparksoft.groupsgenerator.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.handysparksoft.groupsgenerator.ui.screens.create.CreateScreen
import com.handysparksoft.groupsgenerator.ui.screens.detail.DetailScreen
import com.handysparksoft.groupsgenerator.ui.screens.detail.DetailViewModel
import com.handysparksoft.groupsgenerator.ui.screens.main.MainScreen
import com.handysparksoft.groupsgenerator.ui.screens.main.MainViewModel

@Composable
fun Navigation(mainViewModel: MainViewModel, detailViewModel: DetailViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = NavItem.Main.route) {
        composable(NavItem.Main) {
            MainScreen(
                viewModel = mainViewModel,
                onAListClick = { aList ->
                    navController.navigate(NavItem.Detail.createNavRoute(aList.id))
                },
                onCreateClick = {
                    navController.navigate(NavItem.Create.baseRoute)
                }
            )
        }
        composable(NavItem.Detail) { backStackEntry ->
            detailViewModel.aListId = backStackEntry.findArg(NavArg.GroupId.key)
            DetailScreen(
                viewModel = detailViewModel,
                onUpClick = { navController.popBackStack() }
            )
        }
        composable(NavItem.Create) {
            CreateScreen(
                onUpClick = { navController.popBackStack() },
                onCreateClick = {
                    mainViewModel.addAList(it)
                    navController.navigate(NavItem.Main.baseRoute)
                }
            )
        }
    }
}

private fun NavGraphBuilder.composable(
    navItem: NavItem,
    content: @Composable (NavBackStackEntry) -> Unit
) {
    composable(route = navItem.route, arguments = navItem.args) {
        content(it)
    }
}

private inline fun <reified T> NavBackStackEntry.findArg(key: String): T {
    val value = arguments?.get(key)
    requireNotNull(value = value)
    return value as T
}
