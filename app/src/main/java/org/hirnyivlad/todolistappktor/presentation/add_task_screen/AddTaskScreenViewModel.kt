package org.hirnyivlad.todolistappktor.presentation.add_task_screen

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.hirnyivlad.todolistappktor.data.remote.AppSocketService
import org.hirnyivlad.todolistappktor.data.remote.TaskService
import org.hirnyivlad.todolistappktor.data.remote.dto.TaskDto
import org.hirnyivlad.todolistappktor.data.remote.dto.TaskToSend
import org.hirnyivlad.todolistappktor.domain.model.Task
import org.hirnyivlad.todolistappktor.presentation.main_screen.MainScreenState
import org.hirnyivlad.todolistappktor.util.Resource
import javax.inject.Inject

@HiltViewModel
class AddTaskScreenViewModel @Inject constructor(
    private val taskService: TaskService,
    private val socketService: AppSocketService,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val taskId: String? = savedStateHandle["taskId"]
    private val taskTextFromNavigation: String? = savedStateHandle["taskText"]
    private val _taskDescriptionFromNavigation: String? = savedStateHandle["taskDesc"]
    private val taskColorFromNavigation: String? = savedStateHandle["taskColor"]
    private val taskDescriptionFromNavigation =
        _taskDescriptionFromNavigation.takeIf { it != "empty" }

    private val _toastEvent = MutableSharedFlow<String>()
    val toastEvent = _toastEvent.asSharedFlow()

    private val _titleText = mutableStateOf(taskTextFromNavigation ?: "")
    val titleText: State<String> = _titleText

    private val _descriptionText = mutableStateOf(taskDescriptionFromNavigation ?: "")
    val descriptionText: State<String> = _descriptionText

    private val _color = mutableStateOf(taskColorFromNavigation ?: "")
    val color: State<String> = _color
    fun connectToApp() {
        viewModelScope.launch {
            val result = socketService.initSession()
            when (result) {
                is Resource.Success -> {
                }

                is Resource.Error -> {
                    _toastEvent.emit(result.message ?: "Unknown error")
                }
            }
        }
    }

    fun onTitleChange(title: String) {
        _titleText.value = title
    }

    fun onDescriptionChange(description: String) {
        _descriptionText.value = description
    }

    fun disconnect() {
        viewModelScope.launch {
            socketService.closeSession()
        }
    }

    fun createTask(context: Context) {
        viewModelScope.launch {
            if (titleText.value.isNotBlank()) {
                val taskEntity = TaskToSend(
                    text = titleText.value,
                    description = descriptionText.value,
                    color = color.value
                )
                val isSuccess = socketService.createTask(taskEntity)
                if (isSuccess) {
                    Toast.makeText(
                        context,
                        "Task successfully added",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        context,
                        "Something went wrong",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                _titleText.value = ""
                _descriptionText.value = ""
            }
        }
    }

    fun pickColor(color: String) {
        _color.value = color
    }

    override fun onCleared() {
        super.onCleared()
        disconnect()
    }

    fun isPickedColorRed(): Boolean = _color.value == "red"
    fun isPickedColorPink(): Boolean = _color.value == "pink"
    fun isPickedColorGreen(): Boolean = _color.value == "green"
    fun isPickedColorBrown(): Boolean = _color.value == "brown"

    fun editTask() {
        viewModelScope.launch {
            val editedTask = TaskDto(
                text = titleText.value,
                description = descriptionText.value,
                id = taskId!!,
                isCompleted = false,
                timestamp = 0,
                color = color.value,
            )
            taskService.editTask(taskId, editedTask)
        }
    }
}