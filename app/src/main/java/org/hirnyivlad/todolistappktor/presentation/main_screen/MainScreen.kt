package org.hirnyivlad.todolistappktor.presentation.main_screen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.NavController
import kotlinx.coroutines.flow.collectLatest
import org.hirnyivlad.todolistappktor.R
import org.hirnyivlad.todolistappktor.domain.model.Task
import org.hirnyivlad.todolistappktor.presentation.bottom_nav_bar.NoRippleInteractionSource
import org.hirnyivlad.todolistappktor.presentation.main_screen.sort_dialog.SortDialog
import org.hirnyivlad.todolistappktor.ui.theme.DefaultBgColor

@Composable
fun MainScreen(
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
    val dialogState by remember {
        viewModel.dialogState
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DefaultBgColor)
    ) {
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
            items(state.tasks) { task ->
                if (task.isCompleted) {
                    ItemMainScreenCompleted(
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
                    ItemMainScreen(
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
        if (dialogState.showDialog) {
            SortDialog(onDialogDismiss = { viewModel.updateDialogState(false) },
                onDialogSubmitTitle = {
                    viewModel.sortByTitle()
                    viewModel.updateDialogState(false)
                },
                onDialogSubmitDate = {
                    viewModel.getAllTasks()
                    viewModel.updateDialogState(false)
                }
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.End
        ) {
            IconButton(
                onClick = { viewModel.updateDialogState(true) },
                modifier = Modifier.size(70.dp),
                interactionSource = NoRippleInteractionSource(),
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.sort_btn_icon),
                    contentDescription = "sort",
                    modifier = Modifier.fillMaxHeight(),
                )
            }
        }
    }
}


