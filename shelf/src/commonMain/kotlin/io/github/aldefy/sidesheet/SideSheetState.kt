package io.github.aldefy.sidesheet

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.SpringSpec
import androidx.compose.animation.core.exponentialDecay
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.animateTo
import androidx.compose.foundation.gestures.snapTo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp

/**
 * State holder for side sheet composables.
 *
 * Backed by [AnchoredDraggableState] to support swipe-to-dismiss gestures.
 *
 * @param initialValue the initial [SideSheetValue].
 * @param density the [Density] for pixel conversion.
 * @param confirmValueChange optional callback to veto state changes.
 * @param animationSpec the animation spec for sheet open/close.
 */
@Stable
@OptIn(ExperimentalFoundationApi::class)
@Suppress("DEPRECATION")
class SideSheetState(
    initialValue: SideSheetValue = SideSheetValue.Hidden,
    density: Density,
    val confirmValueChange: (SideSheetValue) -> Boolean = { true },
    animationSpec: AnimationSpec<Float> = SpringSpec(),
) {
    internal val anchoredDraggableState = AnchoredDraggableState(
        initialValue = initialValue,
        positionalThreshold = { distance -> distance * 0.5f },
        velocityThreshold = { with(density) { 125.dp.toPx() } },
        snapAnimationSpec = animationSpec,
        decayAnimationSpec = exponentialDecay(),
        confirmValueChange = confirmValueChange,
    )

    /** The current value of the sheet. */
    val currentValue: SideSheetValue
        get() = anchoredDraggableState.currentValue

    /** The target value the sheet is animating towards. */
    val targetValue: SideSheetValue
        get() = anchoredDraggableState.targetValue

    /** Whether the sheet is currently visible (expanded). */
    val isVisible: Boolean
        get() = currentValue == SideSheetValue.Expanded

    /** The current offset of the sheet in pixels, or 0 if not yet initialized. */
    val offset: Float
        get() = anchoredDraggableState.offset.takeIf { !it.isNaN() } ?: 0f

    /** Animate the sheet to the [SideSheetValue.Expanded] state. */
    suspend fun expand() {
        anchoredDraggableState.animateTo(SideSheetValue.Expanded)
    }

    /** Animate the sheet to the [SideSheetValue.Hidden] state. */
    suspend fun hide() {
        anchoredDraggableState.animateTo(SideSheetValue.Hidden)
    }

    /** Snap the sheet to [value] without animation. */
    internal suspend fun snapTo(value: SideSheetValue) {
        anchoredDraggableState.snapTo(value)
    }

    /** Update the anchors for horizontal dragging (side sheet). */
    internal fun updateAnchors(sheetWidthPx: Float, isEndEdge: Boolean) {
        val newAnchors = DraggableAnchors {
            if (isEndEdge) {
                SideSheetValue.Hidden at sheetWidthPx
                SideSheetValue.Expanded at 0f
            } else {
                SideSheetValue.Hidden at -sheetWidthPx
                SideSheetValue.Expanded at 0f
            }
        }
        anchoredDraggableState.updateAnchors(newAnchors)
    }

    /** Update the anchors for vertical dragging (bottom sheet fallback). */
    internal fun updateVerticalAnchors(sheetHeightPx: Float) {
        val newAnchors = DraggableAnchors {
            SideSheetValue.Hidden at sheetHeightPx
            SideSheetValue.Expanded at 0f
        }
        anchoredDraggableState.updateAnchors(newAnchors)
    }

    companion object {
        fun saver(
            density: Density,
            confirmValueChange: (SideSheetValue) -> Boolean,
            animationSpec: AnimationSpec<Float>,
        ): Saver<SideSheetState, SideSheetValue> = Saver(
            save = { it.currentValue },
            restore = { savedValue ->
                SideSheetState(
                    initialValue = savedValue,
                    density = density,
                    confirmValueChange = confirmValueChange,
                    animationSpec = animationSpec,
                )
            },
        )
    }
}

/**
 * Creates and remembers a [SideSheetState].
 *
 * @param initialValue the initial [SideSheetValue].
 * @param confirmValueChange optional callback to veto state changes.
 * @param animationSpec the animation spec for sheet open/close.
 */
@Composable
fun rememberSideSheetState(
    initialValue: SideSheetValue = SideSheetValue.Hidden,
    confirmValueChange: (SideSheetValue) -> Boolean = { true },
    animationSpec: AnimationSpec<Float> = SpringSpec(),
): SideSheetState {
    val density = androidx.compose.ui.platform.LocalDensity.current
    return rememberSaveable(
        saver = SideSheetState.saver(density, confirmValueChange, animationSpec),
    ) {
        SideSheetState(
            initialValue = initialValue,
            density = density,
            confirmValueChange = confirmValueChange,
            animationSpec = animationSpec,
        )
    }
}
