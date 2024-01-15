package org.hirnyivlad.todolistappktor.data.remote

import kotlinx.coroutines.flow.Flow
import org.hirnyivlad.todolistappktor.data.remote.dto.TaskDto
import org.hirnyivlad.todolistappktor.data.remote.dto.TaskToSend
import org.hirnyivlad.todolistappktor.domain.model.Task
import org.hirnyivlad.todolistappktor.util.Resource

interface AppSocketService {
    suspend fun initSession(): Resource<Unit>
    suspend fun createTask(task: TaskToSend): Boolean
    suspend fun closeSession()

    companion object {
        const val BASE_URL = "enter your url ->  ws://"
    }

    sealed class Endpoints(val url: String) {
        object AppSocket : Endpoints("$BASE_URL/your-socket")
    }
}