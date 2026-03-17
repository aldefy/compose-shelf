---
title: Quick Start
description: Get a side sheet running in 2 minutes
---

## Minimal Modal Side Sheet

```kotlin
import io.github.aldefy.sidesheet.ModalSideSheet
import io.github.aldefy.sidesheet.rememberSideSheetState

@Composable
fun MyScreen() {
    var showSheet by remember { mutableStateOf(false) }

    Button(onClick = { showSheet = true }) {
        Text("Open Sheet")
    }

    if (showSheet) {
        ModalSideSheet(
            onDismissRequest = { showSheet = false },
        ) {
            Column(modifier = Modifier.padding(24.dp)) {
                Text("Hello from the side sheet!")
            }
        }
    }
}
```

That's it. The sheet slides in from the end edge with a scrim overlay, supports swipe-to-dismiss, and follows the M3 spec out of the box.

## With State Control

For programmatic expand/hide:

```kotlin
val sheetState = rememberSideSheetState()
val scope = rememberCoroutineScope()

ModalSideSheet(
    onDismissRequest = { showSheet = false },
    sheetState = sheetState,
) {
    Button(onClick = {
        scope.launch {
            sheetState.hide()
        }
    }) {
        Text("Close")
    }
}
```

## Adaptive (Side Sheet + Bottom Sheet)

Switch between side sheet (tablets) and bottom sheet (phones) automatically:

```kotlin
AdaptiveSheet(
    onDismissRequest = { showSheet = false },
) {
    Text("I adapt to screen width!")
}
```

## Next Steps

- [Modal Side Sheet Guide](/compose-shelf/guide/modal-side-sheet/) — full API walkthrough
- [Customization](/compose-shelf/guide/customization/) — colors, shapes, elevation, drag handle
- [API Reference](/compose-shelf/api/modal-side-sheet/) — complete parameter docs
