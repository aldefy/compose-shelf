package io.github.aldefy.sidesheet

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Default values for side sheet composables.
 */
object SideSheetDefaults {

    /** Default maximum width of a side sheet, per Material 3 spec (360dp). */
    val SheetMaxWidth: Dp = 360.dp

    /** Modal side sheet elevation — M3 Level 1 (1dp). */
    val ModalElevation: Dp = 1.dp

    /** Default scrim color: 32% black. */
    val ScrimColor: Color = Color.Black.copy(alpha = 0.32f)

    /** Default container color for light theme. */
    private val ContainerColorLight: Color = Color(0xFFF7F2FA)

    /** Default container color for dark theme. */
    private val ContainerColorDark: Color = Color(0xFF2B2930)

    /** Default content color for light theme. */
    private val ContentColorLight: Color = Color(0xFF1D1B20)

    /** Default content color for dark theme. */
    private val ContentColorDark: Color = Color(0xFFE6E1E5)

    /** Default handle/drag indicator color (M3 onSurfaceVariant). */
    private val HandleColorLight: Color = Color(0xFF79747E)
    private val HandleColorDark: Color = Color(0xFF938F99)

    /** Default drag handle color for [SideSheetDragHandle]. Uses M3 onSurfaceVariant at 40% opacity. */
    val DragHandleColor: Color = Color(0xFF79747E).copy(alpha = 0.4f)

    /** M3 shapeAppearanceCornerLarge = 28dp. */
    private val SheetCornerRadius: Dp = 28.dp

    /**
     * Shape for an expanded modal side sheet. Rounds only the inner edge:
     * - [SideSheetEdge.End]: topStart + bottomStart rounded
     * - [SideSheetEdge.Start]: topEnd + bottomEnd rounded
     */
    fun expandedShape(edge: SideSheetEdge): Shape = when (edge) {
        SideSheetEdge.End -> RoundedCornerShape(
            topStart = SheetCornerRadius,
            bottomStart = SheetCornerRadius,
            topEnd = 0.dp,
            bottomEnd = 0.dp,
        )
        SideSheetEdge.Start -> RoundedCornerShape(
            topStart = 0.dp,
            bottomStart = 0.dp,
            topEnd = SheetCornerRadius,
            bottomEnd = SheetCornerRadius,
        )
    }

    /** Shape for a standard (inline) side sheet — no rounded corners. */
    fun standardShape(): Shape = RoundedCornerShape(0.dp)

    /** Shape for the bottom sheet fallback in [AdaptiveSheet]. */
    fun bottomSheetShape(): Shape = RoundedCornerShape(
        topStart = SheetCornerRadius,
        topEnd = SheetCornerRadius,
        bottomStart = 0.dp,
        bottomEnd = 0.dp,
    )

    /**
     * Creates default [SideSheetColors] that adapt to light/dark theme.
     */
    @Composable
    fun colors(
        containerColor: Color = if (isSystemInDarkTheme()) ContainerColorDark else ContainerColorLight,
        contentColor: Color = if (isSystemInDarkTheme()) ContentColorDark else ContentColorLight,
        scrimColor: Color = ScrimColor,
        handleColor: Color = if (isSystemInDarkTheme()) HandleColorDark else HandleColorLight,
    ): SideSheetColors = SideSheetColors(
        containerColor = containerColor,
        contentColor = contentColor,
        scrimColor = scrimColor,
        handleColor = handleColor,
    )
}
