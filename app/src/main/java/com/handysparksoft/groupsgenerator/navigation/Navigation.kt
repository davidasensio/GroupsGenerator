package com.handysparksoft.groupsgenerator.navigation

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.unit.dp
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
import com.handysparksoft.groupsgenerator.ui.screens.main.MainBottomSheetContent
import com.handysparksoft.groupsgenerator.ui.screens.main.MainScreen
import com.handysparksoft.groupsgenerator.ui.screens.main.MainViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Navigation(
    mainViewModel: MainViewModel,
    detailViewModel: DetailViewModel,
    generateViewModel: GenerateViewModel
) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = NavItem.Main.route) {
        composable(NavItem.Main) {
            val bottomSheetState = rememberModalBottomSheetState(
                initialValue = ModalBottomSheetValue.Hidden
            )
            val scope = rememberCoroutineScope()
            ModalBottomSheetLayout(
                sheetContent = {
                    MainBottomSheetContent(
                        onSortAlphabetically = {
                            mainViewModel.sortAlphabetically()
                            scope.launch { bottomSheetState.hide() }
                        },
                        onSortNumerically = {
                            mainViewModel.sortNumerically()
                            scope.launch { bottomSheetState.hide() }
                        }
                    )
                },
                sheetState = bottomSheetState,
                sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
            ) {
                MainScreen(
                    viewModel = mainViewModel,
                    onAListClick = { aList ->
                        navController.navigate(NavItem.Detail.createNavRoute(aList.id))
                    },
                    onCreateClick = {
                        navController.navigate(NavItem.Create.baseRoute)
                        mainViewModel.loadSavedLists()
                    },
                    onSortClick = {
                        scope.launch { bottomSheetState.show() }
                    }
                )
            }
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

@Suppress("DEPRECATION")
private inline fun <reified T> NavBackStackEntry.findArg(key: String): T {
    val value = arguments?.get(key)
    requireNotNull(value = value)
    return value as T
}
