package io.github.aldefy.sidesheet

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

/**
 * Color configuration for side sheet composables.
 */
@Immutable
data class SideSheetColors(
    val containerColor: Color,
    val contentColor: Color,
    val scrimColor: Color,
    val handleColor: Color,
)
