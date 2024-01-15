package org.hirnyivlad.todolistappktor.presentation.bottom_nav_bar

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.hirnyivlad.todolistappktor.ui.theme.DefaultBgColor


@Composable
fun CustomIndicator(selected: Boolean) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Transparent)
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .size(40.dp)
                .background(Color.Transparent)
        ) {
            if (selected) {
                drawCircle(
                    color = DefaultBgColor,
                    center = Offset(size.width / 2, size.height / 2),
                    radius = size.width
                )
            }
        }
    }
}