package io.github.aldefy.sidesheet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp

/**
 * Default drag handle indicator for side sheets.
 *
 * A vertical pill (4dp wide × 32dp tall) rendered at the sheet's inner edge,
 * matching the Material 3 drag handle spec.
 *
 * @param modifier modifier for the handle.
 * @param color the handle color, defaults to `onSurfaceVariant`.
 */
@Composable
fun SideSheetDragHandle(
    modifier: Modifier = Modifier,
    color: Color = SideSheetDefaults.DragHandleColor,
) {
    Box(
        modifier = modifier
            .padding(vertical = 16.dp, horizontal = 8.dp)
            .semantics { contentDescription = "Drag handle" }
            .width(4.dp)
            .height(32.dp)
            .clip(RoundedCornerShape(2.dp))
            .background(color),
    )
}
