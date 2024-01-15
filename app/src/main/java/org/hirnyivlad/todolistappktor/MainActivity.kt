package org.hirnyivlad.todolistappktor

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import org.hirnyivlad.todolistappktor.domain.model.BottomNavItem
import org.hirnyivlad.todolistappktor.navigation.NavGraph
import org.hirnyivlad.todolistappktor.presentation.add_task_screen.AddTaskScreen
import org.hirnyivlad.todolistappktor.presentation.bottom_nav_bar.BottomNavigationBar
import org.hirnyivlad.todolistappktor.presentation.group_screen.GroupScreen
import org.hirnyivlad.todolistappktor.presentation.main_screen.MainScreen
import org.hirnyivlad.todolistappktor.ui.SetStatusBarColor
import org.hirnyivlad.todolistappktor.ui.theme.ItemBg
import org.hirnyivlad.todolistappktor.ui.theme.ToDoListAppKtorTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ToDoListAppKtorTheme {
                SetStatusBarColor(color = ItemBg)
                val navController: NavHostController = rememberNavController()
                val bottomBarHeight = 75.dp
                val bottomBarOffsetHeightPx = remember {
                    mutableStateOf(0f)
                }
                val buttonsVisible = remember { mutableStateOf(true) }
                Scaffold(
                    bottomBar = {
                        BottomNavigationBar(
                            navController = navController,
                            state = buttonsVisible,
                            modifier = Modifier
                        )
                    }
                ) { paddingValues ->  
                    Box(modifier = Modifier.padding(paddingValues)){
                        NavGraph(navController = navController)
                    }
                }
            }
        }
    }
}
