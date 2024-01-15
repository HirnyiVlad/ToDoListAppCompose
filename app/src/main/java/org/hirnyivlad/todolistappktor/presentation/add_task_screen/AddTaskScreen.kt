package org.hirnyivlad.todolistappktor.presentation.add_task_screen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import kotlinx.coroutines.flow.collectLatest
import org.hirnyivlad.todolistappktor.ui.theme.AddCircleOutline
import org.hirnyivlad.todolistappktor.ui.theme.BrownColorPriority
import org.hirnyivlad.todolistappktor.ui.theme.BrownColorPriorityUnpicked
import org.hirnyivlad.todolistappktor.ui.theme.DefaultBgColor
import org.hirnyivlad.todolistappktor.ui.theme.GreenColorPriority
import org.hirnyivlad.todolistappktor.ui.theme.GreenColorPriorityUnpicked
import org.hirnyivlad.todolistappktor.ui.theme.PinkColorPriority
import org.hirnyivlad.todolistappktor.ui.theme.PinkColorPriorityUnpicked
import org.hirnyivlad.todolistappktor.ui.theme.RedColorPriority
import org.hirnyivlad.todolistappktor.ui.theme.RedColorPriorityUnpicked

@Composable
fun AddTaskScreen(
    viewModel: AddTaskScreenViewModel = hiltViewModel()
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
                viewModel.connectToApp()
            } else if (event == Lifecycle.Event.ON_STOP) {
                viewModel.disconnect()
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
    Column(
        Modifier
            .fillMaxSize()
            .background(DefaultBgColor)
    ) {
        Spacer(modifier = Modifier.height(52.dp))
        TitleTextContainer(viewModel = viewModel)
        Spacer(modifier = Modifier.height(50.dp))
        DescriptionTextContainer(viewModel = viewModel)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 5.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            ColorPickerBox(
                onClick = { viewModel.pickColor("red") },
                backgroundColorPicked = RedColorPriority,
                backgroundColorUnpicked = RedColorPriorityUnpicked,
                isColorPicked = viewModel.isPickedColorRed()
            )
            ColorPickerBox(
                onClick = { viewModel.pickColor("green") },
                backgroundColorPicked = GreenColorPriority,
                backgroundColorUnpicked = GreenColorPriorityUnpicked,
                isColorPicked = viewModel.isPickedColorGreen()
            )
            ColorPickerBox(
                onClick = { viewModel.pickColor("pink") },
                backgroundColorPicked = PinkColorPriority,
                backgroundColorUnpicked = PinkColorPriorityUnpicked,
                isColorPicked = viewModel.isPickedColorPink()
            )
            ColorPickerBox(
                onClick = { viewModel.pickColor("brown") },
                backgroundColorPicked = BrownColorPriority,
                backgroundColorUnpicked = BrownColorPriorityUnpicked,
                isColorPicked = viewModel.isPickedColorBrown()
            )
            IconButton(
                onClick = { viewModel.createTask(context) },
                modifier = Modifier
                    .padding(end = 8.dp)
                    .border(
                        2.dp,
                        color = AddCircleOutline,
                        shape = CircleShape
                    )
            ) {
                Icon(
                    imageVector = Icons.Rounded.Add,
                    contentDescription = "Add",
                    modifier = Modifier
                        .height(40.dp)
                        .width(40.dp)
                )
            }
        }
    }
}