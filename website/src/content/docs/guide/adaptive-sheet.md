---
title: Adaptive Sheet
description: Side sheet on tablets, bottom sheet on phones
---

`AdaptiveSheet` automatically switches between a side sheet and a bottom sheet based on screen width. This is the recommended entry point for most apps.

## How It Works

- **Width >= 600dp** (Medium+): Renders `ModalSideSheet` from the side
- **Width < 600dp** (Compact): Renders a bottom sheet fallback (slides from bottom)

The 600dp breakpoint aligns with Material 3 window size classes (Compact → Medium).

## Basic Usage

```kotlin
var showSheet by remember { mutableStateOf(false) }

if (showSheet) {
    AdaptiveSheet(
        onDismissRequest = { showSheet = false },
    ) {
        Column(modifier = Modifier.padding(24.dp)) {
            Text("I'm a side sheet on tablet, bottom sheet on phone!")
        }
    }
}
```

## Custom Breakpoint

Override the width threshold:

```kotlin
AdaptiveSheet(
    onDismissRequest = { /* ... */ },
    widthBreakpoint = 840.dp, // Only use side sheet on Expanded windows
) {
    // Content
}
```

## Configuration

All parameters are passed through to the underlying sheet:

```kotlin
AdaptiveSheet(
    onDismissRequest = { /* ... */ },
    sideSheetEdge = SideSheetEdge.Start,
    sideSheetMaxWidth = 400.dp,
    colors = SideSheetDefaults.colors(
        containerColor = MaterialTheme.colorScheme.surfaceContainerLow,
    ),
) {
    // Content
}
```

## When to Use

| Scenario | Recommendation |
|----------|---------------|
| Tablet + phone app | `AdaptiveSheet` |
| Tablet/desktop only | `ModalSideSheet` |
| Persistent desktop panel | `StandardSideSheet` |
| Phone only | Material `ModalBottomSheet` |
