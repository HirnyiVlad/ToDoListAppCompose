package org.hirnyivlad.todolistappktor.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.plugins.websocket.webSocketSession
import io.ktor.client.request.delete
import io.ktor.client.request.parameter
import io.ktor.client.request.url
import io.ktor.websocket.Frame
import io.ktor.websocket.WebSocketSession
import io.ktor.websocket.close
import io.ktor.websocket.readText
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.isActive
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.hirnyivlad.todolistappktor.data.remote.dto.TaskDto
import org.hirnyivlad.todolistappktor.data.remote.dto.TaskToSend
import org.hirnyivlad.todolistappktor.domain.model.Task
import org.hirnyivlad.todolistappktor.util.Resource

class AppSocketServiceImpl(
    private val client: HttpClient
) : AppSocketService {
    private var socket: WebSocketSession? = null
    override suspend fun initSession(): Resource<Unit> {
        return try {
            socket = client.webSocketSession {
                url(AppSocketService.Endpoints.AppSocket.url)
            }
            if (socket?.isActive == true) {
                Resource.Success(Unit)
            } else Resource.Error("Couldn't establish connection")
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.localizedMessage ?: "UnknownError")
        }
    }

    override suspend fun createTask(task: TaskToSend): Boolean {
        return try {
            val jsonString = Json.encodeToString(task)
            socket?.send(Frame.Text(jsonString))
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    override suspend fun closeSession() {
        socket?.close()
    }
}