package org.hirnyivlad.todolistappktor.presentation.main_screen

import org.hirnyivlad.todolistappktor.domain.model.Task

data class MainScreenState(
    val tasks: List<Task> = emptyList(),
    val isLoading:Boolean = false
)
