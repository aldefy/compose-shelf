---
title: ModalSideSheet
description: API reference for ModalSideSheet composable
---

## Signature

```kotlin
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
)
```

## Parameters

| Parameter | Type | Default | Description |
|-----------|------|---------|-------------|
| `onDismissRequest` | `() -> Unit` | Required | Called when the scrim is tapped or the sheet is swiped away |
| `modifier` | `Modifier` | `Modifier` | Modifier applied to the sheet surface |
| `sheetState` | `SideSheetState` | `rememberSideSheetState()` | State controlling visibility and drag |
| `sheetMaxWidth` | `Dp` | `360.dp` | Maximum width of the sheet |
| `edge` | `SideSheetEdge` | `End` | Which edge the sheet slides from |
| `gesturesEnabled` | `Boolean` | `true` | Whether swipe-to-dismiss is enabled |
| `shape` | `Shape` | Inner edge rounded 28dp | Shape of the sheet surface |
| `shadowElevation` | `Dp` | `1.dp` | Shadow elevation for the sheet |
| `colors` | `SideSheetColors` | `SideSheetDefaults.colors()` | Color configuration |
| `dragHandle` | `@Composable (() -> Unit)?` | `null` | Optional drag handle composable |
| `content` | `@Composable ColumnScope.() -> Unit` | Required | Sheet content |

## Behavior

- Renders in a `Popup` above the current content
- Animates in from the specified edge on composition
- Scrim tap or swipe-to-dismiss triggers `onDismissRequest`
- Supports `SideSheetEdge.Start` and `SideSheetEdge.End` with automatic RTL handling
- Accessibility: announces as "Side sheet" pane with dismiss action
