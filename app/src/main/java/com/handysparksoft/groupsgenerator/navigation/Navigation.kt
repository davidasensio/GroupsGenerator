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
import com.handysparksoft.groupsgenerator.ui.screens.generate.GenerateScreen
import com.handysparksoft.groupsgenerator.ui.screens.generate.GenerateViewModel
import com.handysparksoft.groupsgenerator.ui.screens.main.MainScreen
import com.handysparksoft.groupsgenerator.ui.screens.main.MainViewModel

@Composable
fun Navigation(
    mainViewModel: MainViewModel,
    detailViewModel: DetailViewModel,
    generateViewModel: GenerateViewModel
) {
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
                    mainViewModel.loadSavedLists()
                }
            )
        }
        composable(NavItem.Detail) { backStackEntry ->
            val listIdSelected: String = backStackEntry.findArg(NavArg.ListId.key)
            detailViewModel.aListId = listIdSelected
            DetailScreen(
                viewModel = detailViewModel,
                onUpClick = {
                    mainViewModel.loadSavedLists()
                    navController.popBackStack()
                },
                onGenerateClick = {
                    navController.navigate(
                        NavItem.Generate.createNavRoute(
                            listIdSelected
                        )
                    )
                }
            )
        }
        composable(NavItem.Create) {
            CreateScreen(
                onUpClick = { navController.popBackStack() },
                onCreateClick = {
                    mainViewModel.addAList(it)
                    mainViewModel.loadSavedLists()
                    navController.popBackStack()
                }
            )
        }
        composable(NavItem.Generate) { backStackEntry ->
            generateViewModel.aListId = backStackEntry.findArg(NavArg.ListId.key)
            GenerateScreen(
                viewModel = generateViewModel,
                onUpClick = { navController.popBackStack() }
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
