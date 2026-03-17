package io.github.aldefy.sidesheet

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.LayoutDirection
import kotlin.math.roundToInt

/**
 * A standard (non-modal) side sheet that renders inline alongside content.
 *
 * Unlike [ModalSideSheet], this does not render in a popup and has no scrim.
 * It is intended to be placed inside a `Row` or similar layout alongside
 * the main content.
 *
 * @param modifier modifier for the sheet surface.
 * @param sheetState the [SideSheetState] controlling visibility and drag.
 * @param sheetWidth the width of the sheet.
 * @param edge which edge the sheet is anchored to.
 * @param gesturesEnabled whether swipe-to-dismiss is enabled.
 * @param shape shape of the sheet surface.
 * @param colors color configuration for the sheet.
 * @param content the sheet content.
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun StandardSideSheet(
    modifier: Modifier = Modifier,
    sheetState: SideSheetState = rememberSideSheetState(SideSheetValue.Expanded),
    sheetWidth: Dp = SideSheetDefaults.SheetMaxWidth,
    edge: SideSheetEdge = SideSheetEdge.End,
    gesturesEnabled: Boolean = true,
    shape: Shape = SideSheetDefaults.standardShape(),
    colors: SideSheetColors = SideSheetDefaults.colors(),
    content: @Composable ColumnScope.() -> Unit,
) {
    val layoutDirection = LocalLayoutDirection.current
    val isPhysicallyEnd = when (edge) {
        SideSheetEdge.End -> layoutDirection == LayoutDirection.Ltr
        SideSheetEdge.Start -> layoutDirection == LayoutDirection.Rtl
    }

    // Expand on first composition if state is hidden
    LaunchedEffect(Unit) {
        if (!sheetState.isVisible) {
            sheetState.expand()
        }
    }

    Column(
        modifier = modifier
            .width(sheetWidth)
            .fillMaxHeight()
            .onSizeChanged { size ->
                sheetState.updateAnchors(
                    sheetWidthPx = size.width.toFloat(),
                    isEndEdge = isPhysicallyEnd,
                )
            }
            .offset {
                IntOffset(
                    x = sheetState.offset.roundToInt(),
                    y = 0,
                )
            }
            .anchoredDraggable(
                state = sheetState.anchoredDraggableState,
                orientation = Orientation.Horizontal,
                enabled = gesturesEnabled,
            )
            .clip(shape)
            .background(colors.containerColor),
        content = content,
    )
}
