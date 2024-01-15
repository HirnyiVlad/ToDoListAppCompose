package org.hirnyivlad.todolistappktor.presentation.main_screen

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import org.hirnyivlad.todolistappktor.data.remote.AppSocketService
import org.hirnyivlad.todolistappktor.data.remote.TaskService
import org.hirnyivlad.todolistappktor.data.remote.dto.TaskDto
import org.hirnyivlad.todolistappktor.domain.model.Task
import org.hirnyivlad.todolistappktor.presentation.main_screen.sort_dialog.DialogState
import javax.inject.Inject

@HiltViewModel
open class MainScreenViewModel @Inject constructor(
    private val taskService: TaskService,
    private val socketService: AppSocketService,
) : ViewModel() {
    private val _dialogState = mutableStateOf(DialogState())
    val dialogState: State<DialogState> = _dialogState

    private val _state = mutableStateOf(MainScreenState())
    val state: State<MainScreenState> = _state

    private val _toastEvent = MutableSharedFlow<String>()
    val toastEvent = _toastEvent.asSharedFlow()

    private val _isSuccessToastMessage = mutableStateOf<Boolean>(false)
    val isSuccessToastMessage: State<Boolean> = _isSuccessToastMessage

    private val _isRedColumnVisible = mutableStateOf(false)
    val isRedColumnVisible: State<Boolean> = _isRedColumnVisible

    private val _isGreenColumnVisible = mutableStateOf(false)
    val isGreenColumnVisible: State<Boolean> = _isGreenColumnVisible

    private val _isPinkColumnVisible = mutableStateOf(false)
    val isPinkColumnVisible: State<Boolean> = _isPinkColumnVisible

    private val _isBrownColumnVisible = mutableStateOf(false)
    val isBrownColumnVisible: State<Boolean> = _isBrownColumnVisible

    fun toggleRedColumnVisibility() {
        _isRedColumnVisible.value = !_isRedColumnVisible.value
    }

    fun toggleGreenColumnVisibility() {
        _isGreenColumnVisible.value = !_isGreenColumnVisible.value
    }

    fun togglePinkColumnVisibility() {
        _isPinkColumnVisible.value = !_isPinkColumnVisible.value
    }

    fun toggleBrownColumnVisibility() {
        _isBrownColumnVisible.value = !_isBrownColumnVisible.value
    }

    private fun deleteTask(id: String) {
        viewModelScope.launch {
            taskService.deleteTask(id)
            getAllTasks()
        }
    }

    private fun editTaskCompleteness(task: Task, context: Context) {
        viewModelScope.launch {
            val editTaskCompleteness = TaskDto(
                text = "",
                description = "",
                color = "",
                timestamp = 0,
                id = "",
                isCompleted = task.isCompleted
            )
            val isCompletedSuccessfully =
                taskService.setCompleteState(task.id, editTaskCompleteness)
            getAllTasks()
            if (isCompletedSuccessfully) {
                Toast.makeText(context, "Successfully edited", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show()

            }
        }
    }

    fun disconnect() {
        viewModelScope.launch {
            socketService.closeSession()
        }
    }

    fun getAllTasks() {
        viewModelScope.launch {
            _state.value = state.value.copy(isLoading = true)
            val result = taskService.getAllTasks()
            _state.value = _state.value.copy(
                tasks = result,
                isLoading = false
            )
        }
    }

    override fun onCleared() {
        super.onCleared()
        disconnect()
    }

    fun dropDownItemPicked(
        task: Task,
        it: DropDownItem,
        navController: NavController,
        context: Context
    ) {
        viewModelScope.launch {
            if (it.text == "Edit") {
                if (task.description.isEmpty()) {
                    navController.navigate(
                        "edit_screen/" +
                                "${task.id}/" +
                                "${task.text}/" +
                                "${task.color}/" +
                                "empty"
                    )
                } else {
                    navController.navigate(
                        "edit_screen/" +
                                "${task.id}/" +
                                "${task.text}/" +
                                "${task.color}/" +
                                task.description
                    )
                }
            } else if (it.text == "Completed" || it.text == "Make Uncompleted") {
                editTaskCompleteness(task, context)

            } else {
                deleteTask(task.id)
            }
        }
    }

    fun sortByTitle() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = taskService.getAllTasks()
            _state.value = _state.value.copy(
                tasks = result.sortedBy { it.text }
            )
        }
    }

    fun updateDialogState(state: Boolean) {
        _dialogState.value = _dialogState.value.copy(showDialog = state)
    }

}