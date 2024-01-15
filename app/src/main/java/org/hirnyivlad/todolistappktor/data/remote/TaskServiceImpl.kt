package org.hirnyivlad.todolistappktor.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.util.InternalAPI
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.hirnyivlad.todolistappktor.data.remote.dto.TaskDto
import org.hirnyivlad.todolistappktor.domain.model.Task

class TaskServiceImpl(
    private val client: HttpClient
) : TaskService {
    override suspend fun getAllTasks(): List<Task> {
        return try {
            val listTasksDto: List<TaskDto> = client.get(
                TaskService
                    .EndPoints
                    .GetAllTasks
                    .url
            ).body()
            listTasksDto.map {
                it.toTask()
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    override suspend fun deleteTask(id: String) {
        try {
            client.delete(TaskService.EndPoints.GetAllTasks.url) {
                parameter("id", id)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override suspend fun editTask(id: String, task: TaskDto) {
        try {
            client.put(TaskService.EndPoints.GetAllTasks.url) {
                contentType(ContentType.Application.Json)
                parameter("id", id)
                parameter("task_edit", true)
                setBody(task)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override suspend fun setCompleteState(id: String, task: TaskDto): Boolean {
        return try {
            client.put(TaskService.EndPoints.GetAllTasks.url) {
                contentType(ContentType.Application.Json)
                parameter("id", id)
                parameter("task_edit", false)
                setBody(task)
            }
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}