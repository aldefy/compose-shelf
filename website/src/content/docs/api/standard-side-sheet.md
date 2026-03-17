---
title: StandardSideSheet
description: API reference for StandardSideSheet composable
---

## Signature

```kotlin
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
)
```

## Parameters

| Parameter | Type | Default | Description |
|-----------|------|---------|-------------|
| `modifier` | `Modifier` | `Modifier` | Modifier for the sheet surface |
| `sheetState` | `SideSheetState` | `rememberSideSheetState(Expanded)` | State controlling visibility and drag |
| `sheetWidth` | `Dp` | `360.dp` | Width of the sheet |
| `edge` | `SideSheetEdge` | `End` | Which edge the sheet is anchored to |
| `gesturesEnabled` | `Boolean` | `true` | Whether swipe gestures are enabled |
| `shape` | `Shape` | `RectangleShape` | Shape of the sheet surface |
| `colors` | `SideSheetColors` | `SideSheetDefaults.colors()` | Color configuration |
| `content` | `@Composable ColumnScope.() -> Unit` | Required | Sheet content |

## Behavior

- Renders inline in the layout (no popup, no scrim)
- Defaults to `Expanded` state
- Intended for use inside a `Row` alongside main content
- No elevation shadow by default
