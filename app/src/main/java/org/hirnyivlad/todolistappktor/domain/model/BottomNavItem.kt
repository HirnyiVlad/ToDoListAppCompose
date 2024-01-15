package org.hirnyivlad.todolistappktor.domain.model

import androidx.compose.ui.graphics.vector.ImageVector


data class BottomNavItem(
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
)