package io.github.aldefy.sidesheet

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.semantics.dismiss
import androidx.compose.ui.semantics.paneTitle
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.window.Popup
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

/**
 * A modal side sheet that slides in from the [edge] of the screen with a scrim overlay.
 *
 * This composable renders in a [Popup] above the current content, similar to
 * `ModalBottomSheet` but anchored to the side.
 *
 * @param onDismissRequest called when the scrim is tapped or the sheet is swiped away.
 * @param modifier modifier for the sheet surface.
 * @param sheetState the [SideSheetState] controlling visibility and drag.
 * @param sheetMaxWidth maximum width of the sheet (default 360dp per Material 3 spec).
 * @param edge which edge the sheet slides from.
 * @param gesturesEnabled whether swipe-to-dismiss is enabled.
 * @param shape shape of the sheet surface.
 * @param shadowElevation the shadow elevation for the sheet surface.
 * @param colors color configuration for the sheet.
 * @param dragHandle optional drag handle composable rendered at the sheet edge.
 * @param content the sheet content.
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ModalSideSheet(
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    sheetState: SideSheetState = rememberSideSheetState(),
    sheetMaxWidth: Dp = SideSheetDefaults.SheetMaxWidth,
    edge: SideSheetEdge = SideSheetEdge.End,
    gesturesEnabled: Boolean = true,
    shape: Shape = SideSheetDefaults.expandedShape(edge),
    shadowElevation: Dp = SideSheetDefaults.ModalElevation,
    colors: SideSheetColors = SideSheetDefaults.colors(),
    dragHandle: @Composable (() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit,
) {
    val scope = rememberCoroutineScope()
    val layoutDirection = LocalLayoutDirection.current
    val density = LocalDensity.current

    // Resolve whether the sheet is physically on the right or left side
    val isPhysicallyEnd = when (edge) {
        SideSheetEdge.End -> layoutDirection == LayoutDirection.Ltr
        SideSheetEdge.Start -> layoutDirection == LayoutDirection.Rtl
    }

    // Pre-calculate sheet width in pixels for initial anchor setup
    val sheetWidthPx = with(density) { sheetMaxWidth.toPx() }

    // Set up anchors immediately so the sheet starts off-screen
    var anchorsInitialized by remember { mutableStateOf(false) }
    if (!anchorsInitialized) {
        sheetState.updateAnchors(sheetWidthPx, isPhysicallyEnd)
        anchorsInitialized = true
    }

    // Expand on entry, then watch for user-driven hide → dismiss
    LaunchedEffect(sheetState) {
        sheetState.expand()
        snapshotFlow { sheetState.currentValue }
            .drop(1) // skip the initial emission
            .filter { it == SideSheetValue.Hidden }
            .collect { onDismissRequest() }
    }

    Popup(
        onDismissRequest = {
            scope.launch { sheetState.hide() }
        },
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            // Scrim
            SideSheetScrim(
                color = colors.scrimColor,
                visible = sheetState.isVisible || sheetState.targetValue == SideSheetValue.Expanded,
                onDismissRequest = {
                    scope.launch { sheetState.hide() }
                },
            )

            // Sheet surface
            val sheetAlignment = if (isPhysicallyEnd) Alignment.CenterEnd else Alignment.CenterStart

            Box(
                modifier = Modifier
                    .align(sheetAlignment)
                    .semantics {
                        paneTitle = "Side sheet"
                        dismiss {
                            scope.launch { sheetState.hide() }
                            true
                        }
                    }
                    .width(sheetMaxWidth)
                    .fillMaxHeight()
                    .onSizeChanged { size ->
                        // Update anchors if actual size differs from pre-calculated
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
                    .shadow(elevation = shadowElevation, shape = shape)
                    .clip(shape)
                    .background(colors.containerColor)
                    .then(modifier),
            ) {
                Column(modifier = Modifier.fillMaxSize()) {
                    if (dragHandle != null) {
                        dragHandle()
                    }
                    content()
                }
            }
        }
    }
}
