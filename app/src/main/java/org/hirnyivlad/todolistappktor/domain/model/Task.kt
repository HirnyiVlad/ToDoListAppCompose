package org.hirnyivlad.todolistappktor.domain.model

data class Task(
    val text: String,
    val description: String,
    val color: String,
    val formattedTime: String,
    val id: String,
    val isCompleted: Boolean,
)


