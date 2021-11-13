package com.handysparksoft.groupsgenerator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.handysparksoft.groupsgenerator.ui.GroupsGeneratorApp
import com.handysparksoft.groupsgenerator.ui.screens.detail.DetailScreen
import com.handysparksoft.groupsgenerator.ui.screens.main.MainScreen
import com.handysparksoft.groupsgenerator.ui.theme.GroupsGeneratorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GroupsGeneratorApp {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "main") {
                    composable("main") {
                        MainScreen(navController)
                    }
                    composable(
                        route = "detail/{groupId}", // "detail?groupId={groupId}"
                        arguments = listOf(
                            navArgument("groupId") { type = NavType.IntType })
                    ) { backStackEntry ->
                        val id = backStackEntry.arguments?.getInt("groupId")
                        requireNotNull(
                            value = id,
                            lazyMessage = { "Required value should be not null. Detail screen needs an id" }
                        )
                        DetailScreen(id)
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    GroupsGeneratorTheme {
        Greeting("Android")
    }
}
