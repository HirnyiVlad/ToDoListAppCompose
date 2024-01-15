package org.hirnyivlad.todolistappktor.presentation.add_task_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import org.hirnyivlad.todolistappktor.ui.theme.DefaultBgColor
import org.hirnyivlad.todolistappktor.ui.theme.ItemBg


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DescriptionTextContainer(
    viewModel: AddTaskScreenViewModel
) {
    TextField(
        value = viewModel.descriptionText.value,
        onValueChange = viewModel::onDescriptionChange,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = ItemBg,
            unfocusedBorderColor = ItemBg,
            cursorColor = ItemBg
        ),
        placeholder = {
            Text("Description")
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(450.dp)
            .padding(start = 5.dp, bottom = 30.dp, end = 20.dp)
            .background(DefaultBgColor)
            .border(width = 3.dp, ItemBg, shape = RectangleShape)
            .drawBehind {
                val additionalOutlinePath = Path().apply {
                    moveTo(size.width, 50f)
                    lineTo(size.width + 30f, 50f)
                    lineTo(size.width + 30f, size.height + 50f)
                    lineTo(size.width, size.height + 50f)
                    moveTo(size.width, size.height + 50f)
                    lineTo(40f, size.height + 50f)
                    lineTo(40f, size.height)
                    lineTo(size.width, size.height)
                    close()
                }
                drawPath(
                    path = additionalOutlinePath,
                    color = ItemBg
                )
            }
    )
}