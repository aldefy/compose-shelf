---
title: AdaptiveSheet
description: API reference for AdaptiveSheet composable
---

## Signature

```kotlin
@Composable
fun AdaptiveSheet(
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    sheetState: SideSheetState = rememberSideSheetState(),
    widthBreakpoint: Dp = 600.dp,
    sideSheetEdge: SideSheetEdge = SideSheetEdge.End,
    sideSheetMaxWidth: Dp = SideSheetDefaults.SheetMaxWidth,
    colors: SideSheetColors = SideSheetDefaults.colors(),
    content: @Composable ColumnScope.() -> Unit,
)
```

## Parameters

| Parameter | Type | Default | Description |
|-----------|------|---------|-------------|
| `onDismissRequest` | `() -> Unit` | Required | Called on dismiss |
| `modifier` | `Modifier` | `Modifier` | Modifier for the sheet |
| `sheetState` | `SideSheetState` | `rememberSideSheetState()` | State controlling visibility |
| `widthBreakpoint` | `Dp` | `600.dp` | Width threshold for side vs bottom sheet |
| `sideSheetEdge` | `SideSheetEdge` | `End` | Edge for the side sheet variant |
| `sideSheetMaxWidth` | `Dp` | `360.dp` | Max width of the side sheet variant |
| `colors` | `SideSheetColors` | `SideSheetDefaults.colors()` | Color configuration |
| `content` | `@Composable ColumnScope.() -> Unit` | Required | Sheet content |

## Behavior

- Uses `BoxWithConstraints` to measure available width
- `maxWidth >= widthBreakpoint` → renders `ModalSideSheet`
- `maxWidth < widthBreakpoint` → renders internal `BottomSheetFallback`
- The 600dp default aligns with M3 Compact → Medium window size class boundary
