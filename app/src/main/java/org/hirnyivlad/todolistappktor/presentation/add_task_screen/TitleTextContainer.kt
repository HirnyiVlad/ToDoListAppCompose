package org.hirnyivlad.todolistappktor.presentation.add_task_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.hirnyivlad.todolistappktor.ui.theme.DefaultBgColor
import org.hirnyivlad.todolistappktor.ui.theme.ItemBg

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TitleTextContainer(
    viewModel: AddTaskScreenViewModel,
) {
    TextField(
        value = viewModel.titleText.value,
        onValueChange = viewModel::onTitleChange,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = ItemBg,
            unfocusedBorderColor = ItemBg,
            cursorColor = ItemBg
        ),
        placeholder = {
            Text(
                text = "Title",
                modifier = Modifier.fillMaxWidth(),
                style = LocalTextStyle.current.copy(textAlign = TextAlign.Center)
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .background(DefaultBgColor)
            .border(width = 3.dp, ItemBg, shape = RectangleShape)
            .drawBehind {
                val additionalOutlinePath = Path().apply {
                    moveTo(0f, 120f)
                    lineTo(-20f, 120f)
                    lineTo(-20f, -40f)
                    lineTo(10f, -40f)
                    moveTo(0f, 0f)
                    lineTo(10f, -40f)
                    lineTo(960f, -40f)
                    lineTo(960f, 0f)
                    close()
                }
                drawPath(
                    path = additionalOutlinePath,
                    color = ItemBg
                )
            }
    )
}