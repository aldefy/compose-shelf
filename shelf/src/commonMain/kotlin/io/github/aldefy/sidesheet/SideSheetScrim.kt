package io.github.aldefy.sidesheet

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput

/**
 * Full-screen scrim overlay that fades in when the sheet is visible.
 * Tapping the scrim triggers [onDismissRequest].
 */
@Composable
internal fun SideSheetScrim(
    color: Color,
    visible: Boolean,
    onDismissRequest: () -> Unit,
) {
    val alpha by animateFloatAsState(
        targetValue = if (visible) 1f else 0f,
        label = "ScrimAlpha",
    )

    if (alpha > 0f) {
        val scrimColor = color.copy(alpha = color.alpha * alpha)
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(onDismissRequest) {
                    detectTapGestures { onDismissRequest() }
                },
        ) {
            drawRect(color = scrimColor)
        }
    }
}
