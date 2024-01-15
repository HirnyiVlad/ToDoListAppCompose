package org.hirnyivlad.todolistappktor.data.remote

import kotlinx.coroutines.flow.Flow
import org.hirnyivlad.todolistappktor.data.remote.dto.TaskDto
import org.hirnyivlad.todolistappktor.domain.model.Task

interface TaskService {
    suspend fun getAllTasks(): List<Task>
    suspend fun deleteTask(id: String)
    suspend fun editTask(id: String, task: TaskDto)
    suspend fun setCompleteState(id: String, task: TaskDto): Boolean

    companion object {
        const val BASE_URL = "your url -> http://"
    }

    sealed class EndPoints(val url: String) {
        object GetAllTasks : EndPoints("$BASE_URL/your endpoint")
    }
}