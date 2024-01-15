package org.hirnyivlad.todolistappktor.navigation

import org.hirnyivlad.todolistappktor.R

sealed class Destinations(
    val route: String,
    val icon: Int,
    val selectedIcon: Int
) {

    object AddTaskScreen : Destinations(
        route = "add_task_screen",
        icon = R.drawable.plus_circle_icon,
        selectedIcon = R.drawable.plus_circle_selected
    )

    object GroupScreen : Destinations(
        route = "group_screen",
        icon = R.drawable.house_icon,
        selectedIcon = R.drawable.house_selected_icon
    )

    object MainScreen : Destinations(
        route = "main_screen",
        icon = R.drawable.list_icon,
        selectedIcon = R.drawable.list_selected_icon
    )
}
