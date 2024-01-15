package org.hirnyivlad.todolistappktor.presentation.main_screen.sort_dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@Composable
fun SortDialog(
    onDialogDismiss: () -> Unit,
    onDialogSubmitTitle: () -> Unit,
    onDialogSubmitDate: () -> Unit
) {
    Dialog(
        onDismissRequest = onDialogDismiss,
        properties = DialogProperties(dismissOnClickOutside = false)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 40.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Bottom
            ) {
                TextButton(
                    onClick = onDialogSubmitTitle,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(
                            RoundedCornerShape(
                                topStart = 14.dp,
                                topEnd = 14.dp
                            )
                        )
                        .background(Color.White)
                ) {
                    Text(
                        text = "Title",
                        style = TextStyle(
                            color = Color.Gray,
                            fontSize = 20.sp
                        )
                    )
                }
                Divider(
                    modifier = Modifier
                        .fillMaxWidth(),
                    color = Color.LightGray
                )
                TextButton(
                    onClick = onDialogSubmitDate,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(
                            RoundedCornerShape(
                                bottomStart = 14.dp,
                                bottomEnd = 14.dp
                            )
                        )
                        .background(Color.White)

                ) {
                    Text(
                        text = "Released date",
                        style = TextStyle(
                            color = Color.Gray,
                            fontSize = 20.sp
                        )
                    )
                }
                TextButton(
                    onClick = onDialogDismiss,
                    modifier = Modifier
                        .padding(top = 4.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(14.dp))
                        .background(Color.White)

                ) {
                    Text(
                        text = "Cancel",
                        style = TextStyle(
                            color = Color.Black,
                            fontSize = 20.sp
                        )
                    )
                }
            }
        }
    }
}
