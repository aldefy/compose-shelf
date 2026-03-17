---
title: Customization
description: Override colors, shapes, elevation, and more
---

Every visual aspect of Compose Shelf has an M3-compliant default but is fully overridable.

## Colors

```kotlin
ModalSideSheet(
    onDismissRequest = { /* ... */ },
    colors = SideSheetDefaults.colors(
        containerColor = Color(0xFF1E1E2E),
        contentColor = Color.White,
        scrimColor = Color.Black.copy(alpha = 0.5f),
        handleColor = Color.Gray,
    ),
) {
    // Content
}
```

### Default Colors

| Property | Light | Dark |
|----------|-------|------|
| `containerColor` | `#F7F2FA` | `#2B2930` |
| `contentColor` | `#1D1B20` | `#E6E1E5` |
| `scrimColor` | `#000000` @ 32% | `#000000` @ 32% |
| `handleColor` | `#79747E` | `#938F99` |

## Shape

```kotlin
// Custom corner radius
ModalSideSheet(
    shape = RoundedCornerShape(16.dp),
) { /* ... */ }

// No rounding
ModalSideSheet(
    shape = RectangleShape,
) { /* ... */ }
```

Default: 28dp on the inner edge only (topStart + bottomStart for End edge).

## Elevation

```kotlin
ModalSideSheet(
    shadowElevation = 4.dp, // More prominent shadow
) { /* ... */ }

// No shadow
ModalSideSheet(
    shadowElevation = 0.dp,
) { /* ... */ }
```

Default: 1dp (M3 Level 1).

## Width

```kotlin
ModalSideSheet(
    sheetMaxWidth = 420.dp, // Wider than default 360dp
) { /* ... */ }
```

## Edge

```kotlin
// Slide from start (left in LTR)
ModalSideSheet(
    edge = SideSheetEdge.Start,
    shape = SideSheetDefaults.expandedShape(SideSheetEdge.Start),
) { /* ... */ }
```

## Drag Handle

Use the built-in handle or provide your own:

```kotlin
// Default M3 drag handle
ModalSideSheet(
    dragHandle = { SideSheetDragHandle() },
) { /* ... */ }

// Custom colored handle
ModalSideSheet(
    dragHandle = {
        SideSheetDragHandle(color = MaterialTheme.colorScheme.primary)
    },
) { /* ... */ }

// No drag handle (default)
ModalSideSheet(
    dragHandle = null,
) { /* ... */ }
```

## Disable Gestures

```kotlin
ModalSideSheet(
    gesturesEnabled = false,
) { /* ... */ }
```

The sheet can still be dismissed by tapping the scrim or programmatically via `sheetState.hide()`.
