package org.hirnyivlad.todolistappktor.presentation.bottom_nav_bar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import org.hirnyivlad.todolistappktor.domain.model.BottomNavItem
import org.hirnyivlad.todolistappktor.navigation.Destinations
import org.hirnyivlad.todolistappktor.ui.theme.ItemBg


@Composable
fun BottomNavigationBar(
    navController: NavHostController,
    state: MutableState<Boolean>,
    modifier: Modifier
) {
    val screens = listOf(
        Destinations.AddTaskScreen,
        Destinations.GroupScreen,
        Destinations.MainScreen
    )
    NavigationBar(
        modifier = modifier,
        containerColor = ItemBg
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        screens.forEach { screen ->
            NavigationBarItem(
                modifier = modifier.padding(bottom = 15.dp),
                icon = {
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .background(Color.Transparent),
                        contentAlignment = androidx.compose.ui.Alignment.Center
                    ) {
                        CustomIndicator(selected = currentRoute == screen.route)
                        Icon(
                            painter = painterResource(id = if (currentRoute == screen.route) screen.selectedIcon else screen.icon),
                            contentDescription = "",
                            modifier = Modifier.size(40.dp),
                        )
                    }
                },
                selected = currentRoute == screen.route,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Color.White
                ),
                interactionSource = NoRippleInteractionSource()

            )
        }
    }
}