package org.hirnyivlad.todolistappktor.presentation.group_screen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.NavController
import kotlinx.coroutines.flow.collectLatest
import org.hirnyivlad.todolistappktor.presentation.main_screen.DropDownItem
import org.hirnyivlad.todolistappktor.presentation.main_screen.MainScreenViewModel
import org.hirnyivlad.todolistappktor.ui.theme.BrownColorPriority
import org.hirnyivlad.todolistappktor.ui.theme.DefaultBgColor
import org.hirnyivlad.todolistappktor.ui.theme.GreenColorPriority
import org.hirnyivlad.todolistappktor.ui.theme.PinkColorPriority
import org.hirnyivlad.todolistappktor.ui.theme.RedColorPriority

@Composable
fun GroupScreen(
    viewModel: MainScreenViewModel = hiltViewModel(),
    navController: NavController
) {
    val context = LocalContext.current
    LaunchedEffect(key1 = true) {
        viewModel.toastEvent.collectLatest { message ->
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
        }
    }
    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(key1 = lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_START) {
                viewModel.getAllTasks()
            } else if (event == Lifecycle.Event.ON_STOP) {
                viewModel.disconnect()
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
    val state = viewModel.state.value
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(DefaultBgColor)
            .padding(8.dp)
    ) {
        item {
            PriorityRow(
                priority = "Very High Priority",
                color = RedColorPriority,

                onToggleClick = viewModel::toggleRedColumnVisibility
            )
        }
        if (viewModel.isRedColumnVisible.value) {
            items(state.tasks) { task ->
                if (task.color == "red") {
                    if (task.isCompleted) {
                        CompletedTaskItem(
                            task = task,
                            dropdownItems = listOf(
                                DropDownItem("Make Uncompleted"),
                                DropDownItem("Delete")
                            ),
                            onItemClick = {
                                viewModel.dropDownItemPicked(
                                    task = task,
                                    it = it,
                                    navController = navController,
                                    context = context
                                )
                            }
                        )
                    } else {
                        TaskItem(
                            task = task,
                            dropdownItems = listOf(
                                DropDownItem("Edit"),
                                DropDownItem("Completed"),
                                DropDownItem("Delete")
                            ),
                            onItemClick = {
                                viewModel.dropDownItemPicked(
                                    task = task,
                                    it = it,
                                    navController = navController,
                                    context = context
                                )
                            }
                        )
                    }
                }
            }
        }
        item {
            PriorityRow(
                priority = "High Priority",
                color = GreenColorPriority,
                onToggleClick = viewModel::toggleGreenColumnVisibility
            )
        }
        if (viewModel.isGreenColumnVisible.value) {
            items(state.tasks) { task ->

                if (task.color == "green") {
                    if (task.isCompleted) {
                        CompletedTaskItem(
                            task = task,
                            dropdownItems = listOf(
                                DropDownItem("Make Uncompleted"),
                                DropDownItem("Delete")
                            ),
                            onItemClick = {
                                viewModel.dropDownItemPicked(
                                    task = task,
                                    it = it,
                                    navController = navController,
                                    context = context
                                )
                            }
                        )
                    } else {
                        TaskItem(
                            task = task,
                            dropdownItems = listOf(
                                DropDownItem("Edit"),
                                DropDownItem("Completed"),
                                DropDownItem("Delete")
                            ),
                            onItemClick = {
                                viewModel.dropDownItemPicked(
                                    task = task,
                                    it = it,
                                    navController = navController,
                                    context = context
                                )
                            }
                        )
                    }
                }
            }
        }
        item {
            PriorityRow(
                priority = "Medium Priority",
                color = PinkColorPriority,
                onToggleClick = viewModel::togglePinkColumnVisibility
            )
        }
        if (viewModel.isPinkColumnVisible.value) {
            items(state.tasks) { task ->
                if (task.color == "pink") {
                    if (task.isCompleted) {
                        CompletedTaskItem(
                            task = task,
                            dropdownItems = listOf(
                                DropDownItem("Make Uncompleted"),
                                DropDownItem("Delete")
                            ),
                            onItemClick = {
                                viewModel.dropDownItemPicked(
                                    task = task,
                                    it = it,
                                    navController = navController,
                                    context = context
                                )
                            }
                        )
                    } else {
                        TaskItem(
                            task = task,
                            dropdownItems = listOf(
                                DropDownItem("Edit"),
                                DropDownItem("Completed"),
                                DropDownItem("Delete")
                            ),
                            onItemClick = {
                                viewModel.dropDownItemPicked(
                                    task = task,
                                    it = it,
                                    navController = navController,
                                    context = context
                                )
                            }
                        )
                    }
                }
            }
        }
        item {
            PriorityRow(
                priority = "Low Priority",
                color = BrownColorPriority,
                onToggleClick = viewModel::toggleBrownColumnVisibility
            )
        }
        if (viewModel.isBrownColumnVisible.value) {
            items(state.tasks) { task ->
                if (task.color == "brown") {
                    if (task.isCompleted) {
                        CompletedTaskItem(
                            task = task,
                            dropdownItems = listOf(
                                DropDownItem("Make Uncompleted"),
                                DropDownItem("Delete")
                            ),
                            onItemClick = {
                                viewModel.dropDownItemPicked(
                                    task = task,
                                    it = it,
                                    navController = navController,
                                    context = context
                                )
                            }
                        )
                    } else {
                        TaskItem(
                            task = task,
                            dropdownItems = listOf(
                                DropDownItem("Edit"),
                                DropDownItem("Completed"),
                                DropDownItem("Delete")
                            ),
                            onItemClick = {
                                viewModel.dropDownItemPicked(
                                    task = task,
                                    it = it,
                                    navController = navController,
                                    context = context
                                )
                            }
                        )
                    }
                }
            }
        }
    }
}
