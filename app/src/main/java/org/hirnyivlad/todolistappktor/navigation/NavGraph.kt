package org.hirnyivlad.todolistappktor.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import org.hirnyivlad.todolistappktor.presentation.add_task_screen.AddTaskScreen
import org.hirnyivlad.todolistappktor.presentation.edit_screen.EditScreen
import org.hirnyivlad.todolistappktor.presentation.group_screen.GroupScreen
import org.hirnyivlad.todolistappktor.presentation.main_screen.MainScreen

@Composable
fun NavGraph(
    navController: NavHostController
) {
    NavHost(navController = navController, startDestination = Destinations.MainScreen.route) {

        composable(Destinations.AddTaskScreen.route) {
            AddTaskScreen()
        }
        composable(Destinations.GroupScreen.route) {
            GroupScreen(navController = navController)
        }
        composable(Destinations.MainScreen.route) {
            MainScreen(navController = navController)
        }
        composable("edit_screen/{taskId}/{taskText}/{taskColor}/{taskDesc}",
            arguments = listOf(
                navArgument("taskId") { type = NavType.StringType },
                navArgument("taskText") { type = NavType.StringType },
                navArgument("taskDesc") { type = NavType.StringType },
                navArgument("taskColor") { type = NavType.StringType }
            )) { backStackEntry ->
            EditScreen()
        }
    }
}