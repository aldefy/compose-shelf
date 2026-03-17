---
title: Standard Side Sheet
description: Inline side sheet without popup or scrim
---

`StandardSideSheet` renders inline alongside your main content — no popup, no scrim. Use it for persistent detail panels in wide-screen layouts.

## Basic Usage

```kotlin
Row {
    // Main content takes remaining space
    MainContent(modifier = Modifier.weight(1f))

    // Side sheet panel
    StandardSideSheet {
        Column(modifier = Modifier.padding(24.dp)) {
            Text("Detail Panel")
        }
    }
}
```

## Collapsible Panel

Use `sheetState` to programmatically show/hide:

```kotlin
val sheetState = rememberSideSheetState(SideSheetValue.Expanded)
val scope = rememberCoroutineScope()

Row {
    Column(modifier = Modifier.weight(1f)) {
        Button(onClick = {
            scope.launch {
                if (sheetState.isVisible) sheetState.hide()
                else sheetState.expand()
            }
        }) {
            Text("Toggle Panel")
        }
    }

    StandardSideSheet(
        sheetState = sheetState,
        sheetWidth = 300.dp,
    ) {
        Text("Collapsible panel content")
    }
}
```

## Key Differences from Modal

| Feature | ModalSideSheet | StandardSideSheet |
|---------|---------------|-------------------|
| Rendering | Popup overlay | Inline in layout |
| Scrim | Yes (32% black) | No |
| Default state | Hidden | Expanded |
| Elevation | Level 1 shadow | None |
| Shape | Rounded inner edge | No rounding |
| Use case | Temporary detail | Persistent panel |
