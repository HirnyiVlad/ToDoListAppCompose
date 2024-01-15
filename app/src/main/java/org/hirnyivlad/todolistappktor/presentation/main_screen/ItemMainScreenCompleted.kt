package org.hirnyivlad.todolistappktor.presentation.main_screen

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.hirnyivlad.todolistappktor.domain.model.Task
import org.hirnyivlad.todolistappktor.ui.theme.BrownColorPriority
import org.hirnyivlad.todolistappktor.ui.theme.CategoryOutline
import org.hirnyivlad.todolistappktor.ui.theme.DropDownMenuColor
import org.hirnyivlad.todolistappktor.ui.theme.GreenColorPriority
import org.hirnyivlad.todolistappktor.ui.theme.ItemBg
import org.hirnyivlad.todolistappktor.ui.theme.PinkColorPriority
import org.hirnyivlad.todolistappktor.ui.theme.RedColorPriority

@Composable
fun ItemMainScreenCompleted(
    task: Task,
    dropdownItems: List<DropDownItem>,
    onItemClick: (DropDownItem) -> Unit
) {

    var isContextMenuVisible by rememberSaveable {
        mutableStateOf(false)
    }
    var pressOffset by remember {
        mutableStateOf(DpOffset.Zero)
    }
    var itemHeight by remember {
        mutableStateOf(0.dp)
    }
    val interactionSource = remember {
        MutableInteractionSource()
    }
    val density = LocalDensity.current

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .onSizeChanged {
                itemHeight = with(density) { it.height.toDp() }
            }
            .fillMaxWidth()
            .indication(interactionSource, LocalIndication.current)
            .pointerInput(true) {
                detectTapGestures(
                    onLongPress = {
                        isContextMenuVisible = true
                        pressOffset = DpOffset(it.x.toDp(), it.y.toDp())
                    },
                    onPress = {
                        val press = PressInteraction.Press(it)
                        interactionSource.emit(press)
                        tryAwaitRelease()
                        interactionSource.emit(PressInteraction.Release(press))
                    }
                )
            }
            .padding(vertical = 8.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxSize()
                .background(color = ItemBg)
                .defaultMinSize(minHeight = 72.dp)
                .drawBehind {
                    val triangleHeight = 31.dp.toPx()
                    val triangleWidth = 31.dp.toPx()
                    val trianglePath = Path().apply {
                        moveTo(0f, 0f)
                        lineTo(0f, triangleHeight)
                        lineTo(triangleWidth, 0f)
                        close()
                    }
                    drawPath(
                        path = trianglePath,
                        color =if (task.color.isEmpty()) {
                            Color.Transparent
                        }else{
                            CategoryOutline
                        }
                    )
                }
                .padding(1.dp)
                .drawBehind {
                    val triangleHeight = 27.dp.toPx()
                    val triangleWidth = 27.dp.toPx()
                    val trianglePath = Path().apply {
                        moveTo(0f, 0f)
                        lineTo(0f, triangleHeight)
                        lineTo(triangleWidth, 0f)
                        close()
                    }
                    drawPath(
                        path = trianglePath,
                        color = when (task.color) {
                            "red" -> RedColorPriority
                            "green" -> GreenColorPriority
                            "pink" -> PinkColorPriority
                            "brown" -> BrownColorPriority
                            else -> Color.Transparent
                        }
                    )
                }
                .padding(
                    horizontal = 16.dp,
                    vertical = 8.dp
                )
                .alpha(0.3f)
        ) {
            val textCrossedOut = buildAnnotatedString {
                withStyle(style = SpanStyle(textDecoration = TextDecoration.LineThrough)) {
                    append(task.text)
                }
            }
            val dateCrossedOut = buildAnnotatedString {
                withStyle(style = SpanStyle(textDecoration = TextDecoration.LineThrough)) {
                    append(task.formattedTime)
                }
            }
            Text(
                text = textCrossedOut,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                fontSize = 24.sp,
            )
            Text(
                text = dateCrossedOut,
                fontWeight = FontWeight.Bold,
                color = Color.DarkGray,
            )
        }
        DropdownMenu(
            expanded = isContextMenuVisible,
            onDismissRequest = {
                isContextMenuVisible = false
            },
            offset = pressOffset.copy(
                y = pressOffset.y - itemHeight
            ),
            modifier = Modifier.background(DropDownMenuColor)
        ) {
            dropdownItems.forEach {
                DropdownMenuItem(
                    onClick = {
                        onItemClick(it)
                        isContextMenuVisible = false
                    },
                    text = {
                        Text(text = it.text)
                    },
                )
            }
        }
    }
}
