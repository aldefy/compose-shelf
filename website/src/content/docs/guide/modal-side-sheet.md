---
title: Modal Side Sheet
description: Full-screen overlay side sheet with scrim and swipe-to-dismiss
---

`ModalSideSheet` renders in a `Popup` above the current content with a scrim overlay. It's the Compose equivalent of `SideSheetDialog` from the View system.

## Basic Usage

```kotlin
var showSheet by remember { mutableStateOf(false) }

if (showSheet) {
    ModalSideSheet(
        onDismissRequest = { showSheet = false },
    ) {
        // Your content here
    }
}
```

## Edge Control

By default the sheet slides from the **end** edge (right in LTR, left in RTL). Override with `edge`:

```kotlin
ModalSideSheet(
    onDismissRequest = { /* ... */ },
    edge = SideSheetEdge.Start, // Slides from the start edge
) {
    // Content
}
```

RTL layout direction is handled automatically.

## Drag Handle

Add a drag handle indicator at the sheet edge:

```kotlin
ModalSideSheet(
    onDismissRequest = { /* ... */ },
    dragHandle = { SideSheetDragHandle() },
) {
    // Content
}
```

Or provide a custom drag handle:

```kotlin
dragHandle = {
    Box(
        modifier = Modifier
            .width(4.dp)
            .height(48.dp)
            .background(Color.Gray, RoundedCornerShape(2.dp))
    )
}
```

## Programmatic Control

```kotlin
val sheetState = rememberSideSheetState()
val scope = rememberCoroutineScope()

// Expand
scope.launch { sheetState.expand() }

// Hide (triggers onDismissRequest)
scope.launch { sheetState.hide() }

// Check visibility
if (sheetState.isVisible) { /* ... */ }
```

## Disable Swipe-to-Dismiss

```kotlin
ModalSideSheet(
    onDismissRequest = { /* ... */ },
    gesturesEnabled = false, // Only dismiss via scrim tap or programmatic hide
) {
    // Content
}
```

## Desktop Popup Theming

On JVM Desktop, `Popup` does not inherit `CompositionLocal` providers like `MaterialTheme`. Capture theme values before the sheet and re-provide them inside:

```kotlin
val colorScheme = MaterialTheme.colorScheme
val typography = MaterialTheme.typography

ModalSideSheet(
    onDismissRequest = { /* ... */ },
    colors = SideSheetDefaults.colors(
        containerColor = colorScheme.surface,
    ),
) {
    MaterialTheme(colorScheme = colorScheme, typography = typography) {
        // Now MaterialTheme.typography etc. work correctly
        Text("Styled text", style = MaterialTheme.typography.titleLarge)
    }
}
```

This is a known Compose Desktop limitation and is not needed on Android, iOS, or Web.
