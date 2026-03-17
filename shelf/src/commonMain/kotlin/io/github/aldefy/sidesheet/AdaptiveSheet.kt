package io.github.aldefy.sidesheet

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
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
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

/**
 * An adaptive sheet that renders as a [ModalSideSheet] on wide screens and
 * as a bottom sheet on narrow screens.
 *
 * The breakpoint is configurable via [widthBreakpoint] (default 600dp,
 * the Material 3 Compact → Medium boundary).
 *
 * @param onDismissRequest called when the sheet is dismissed.
 * @param modifier modifier for the sheet surface.
 * @param sheetState the [SideSheetState] controlling visibility and drag.
 * @param widthBreakpoint screens wider than this use a side sheet; narrower use a bottom sheet.
 * @param sideSheetEdge which edge the side sheet slides from (ignored for bottom sheet).
 * @param sideSheetMaxWidth maximum width of the side sheet variant.
 * @param bottomSheetMaxHeight maximum height of the bottom sheet variant.
 * @param colors color configuration for the sheet.
 * @param content the sheet content.
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AdaptiveSheet(
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    sheetState: SideSheetState = rememberSideSheetState(),
    widthBreakpoint: Dp = 600.dp,
    sideSheetEdge: SideSheetEdge = SideSheetEdge.End,
    sideSheetMaxWidth: Dp = SideSheetDefaults.SheetMaxWidth,
    bottomSheetMaxHeight: Dp = 640.dp,
    colors: SideSheetColors = SideSheetDefaults.colors(),
    content: @Composable ColumnScope.() -> Unit,
) {
    BoxWithConstraints {
        if (maxWidth >= widthBreakpoint) {
            // Wide screen: render as side sheet
            ModalSideSheet(
                onDismissRequest = onDismissRequest,
                modifier = modifier,
                sheetState = sheetState,
                sheetMaxWidth = sideSheetMaxWidth,
                edge = sideSheetEdge,
                colors = colors,
                content = content,
            )
        } else {
            // Narrow screen: render as bottom sheet
            BottomSheetFallback(
                onDismissRequest = onDismissRequest,
                modifier = modifier,
                sheetState = sheetState,
                maxHeight = bottomSheetMaxHeight,
                shape = SideSheetDefaults.bottomSheetShape(),
                colors = colors,
                content = content,
            )
        }
    }
}

/**
 * Internal bottom sheet implementation used as fallback in [AdaptiveSheet].
 * Uses the same [SideSheetState] but with vertical orientation anchors.
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun BottomSheetFallback(
    onDismissRequest: () -> Unit,
    modifier: Modifier,
    sheetState: SideSheetState,
    maxHeight: Dp,
    shape: Shape,
    colors: SideSheetColors,
    content: @Composable ColumnScope.() -> Unit,
) {
    val scope = rememberCoroutineScope()
    val density = LocalDensity.current
    val sheetHeightPx = with(density) { maxHeight.toPx() }

    // Set up anchors immediately so the sheet starts off-screen (below)
    var anchorsInitialized by remember { mutableStateOf(false) }
    if (!anchorsInitialized) {
        sheetState.updateVerticalAnchors(sheetHeightPx)
        anchorsInitialized = true
    }

    LaunchedEffect(sheetState) {
        sheetState.expand()
        snapshotFlow { sheetState.currentValue }
            .drop(1)
            .filter { it == SideSheetValue.Hidden }
            .collect { onDismissRequest() }
    }

    Popup(
        onDismissRequest = {
            scope.launch { sheetState.hide() }
        },
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            SideSheetScrim(
                color = colors.scrimColor,
                visible = sheetState.isVisible || sheetState.targetValue == SideSheetValue.Expanded,
                onDismissRequest = {
                    scope.launch { sheetState.hide() }
                },
            )

            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .height(maxHeight)
                    .onSizeChanged { size ->
                        sheetState.updateVerticalAnchors(
                            sheetHeightPx = size.height.toFloat(),
                        )
                    }
                    .offset {
                        IntOffset(
                            x = 0,
                            y = sheetState.offset.roundToInt(),
                        )
                    }
                    .anchoredDraggable(
                        state = sheetState.anchoredDraggableState,
                        orientation = Orientation.Vertical,
                    )
                    .clip(shape)
                    .background(colors.containerColor)
                    .then(modifier),
            ) {
                Column {
                    content()
                }
            }
        }
    }
}
