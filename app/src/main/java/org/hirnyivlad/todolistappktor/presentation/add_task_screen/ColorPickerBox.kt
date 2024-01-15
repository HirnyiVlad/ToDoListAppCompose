package org.hirnyivlad.todolistappktor.presentation.add_task_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp

@Composable
fun ColorPickerBox(
    backgroundColorPicked: Color,
    backgroundColorUnpicked: Color,
    onClick: () -> Unit,
    isColorPicked:Boolean
) {
    Box(
        modifier = Modifier
            .height(40.dp)
            .width(40.dp)
            .background(if (isColorPicked) backgroundColorPicked else backgroundColorUnpicked)
            .border(width = 1.dp,if (isColorPicked) Color.Black else Color.Gray, shape = RectangleShape)
            .clickable(onClick = onClick)
    )
}