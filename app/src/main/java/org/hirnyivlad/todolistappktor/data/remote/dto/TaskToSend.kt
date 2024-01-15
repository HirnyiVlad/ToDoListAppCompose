package org.hirnyivlad.todolistappktor.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class TaskToSend(
    val text: String,
    val description: String,
    val color: String,
)
