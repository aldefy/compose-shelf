---
title: SideSheetDefaults
description: API reference for SideSheetDefaults and SideSheetColors
---

## SideSheetDefaults

```kotlin
object SideSheetDefaults {
    val SheetMaxWidth: Dp = 360.dp
    val ModalElevation: Dp = 1.dp
    val ScrimColor: Color = Color.Black.copy(alpha = 0.32f)
    val DragHandleColor: Color = Color(0xFF79747E).copy(alpha = 0.4f)

    fun expandedShape(edge: SideSheetEdge): Shape
    fun standardShape(): Shape
    fun bottomSheetShape(): Shape

    @Composable
    fun colors(
        containerColor: Color = /* adaptive light/dark */,
        contentColor: Color = /* adaptive light/dark */,
        scrimColor: Color = ScrimColor,
        handleColor: Color = /* adaptive light/dark */,
    ): SideSheetColors
}
```

### Constants

| Constant | Value | Description |
|----------|-------|-------------|
| `SheetMaxWidth` | `360.dp` | Default sheet width per M3 spec |
| `ModalElevation` | `1.dp` | M3 Level 1 elevation |
| `ScrimColor` | `#000000` @ 32% | Default scrim overlay color |
| `DragHandleColor` | `#79747E` @ 40% | Default drag handle color |

### Shape Functions

| Function | Description |
|----------|-------------|
| `expandedShape(edge)` | 28dp rounded on inner edge only |
| `standardShape()` | No rounding (`RectangleShape`) |
| `bottomSheetShape()` | 28dp rounded on top edge |

## SideSheetColors

```kotlin
@Immutable
data class SideSheetColors(
    val containerColor: Color,
    val contentColor: Color,
    val scrimColor: Color,
    val handleColor: Color,
)
```

## SideSheetDragHandle

```kotlin
@Composable
fun SideSheetDragHandle(
    modifier: Modifier = Modifier,
    color: Color = SideSheetDefaults.DragHandleColor,
)
```

A vertical pill (4dp wide x 32dp tall) matching the M3 drag handle spec.
