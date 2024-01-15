package org.hirnyivlad.todolistappktor.data.remote.dto

import kotlinx.serialization.Serializable
import org.hirnyivlad.todolistappktor.domain.model.Task
import java.text.DateFormat
import java.util.Date

@Serializable
data class TaskDto(
    val text: String,
    val description: String,
    val isCompleted: Boolean,
    val timestamp: Long,
    val color: String,
    val id: String
) {
    fun toTask(): Task {
        val date = Date(timestamp)
        val formattedDate = DateFormat.getDateInstance(DateFormat.DEFAULT)
            .format(date)
        return Task(
            text = text,
            description = description,
            color = color,
            formattedTime = formattedDate,
            id = id,
            isCompleted = isCompleted
        )
    }
}
