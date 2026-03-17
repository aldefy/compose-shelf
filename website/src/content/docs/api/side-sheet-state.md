---
title: SideSheetState
description: API reference for SideSheetState and rememberSideSheetState
---

## SideSheetState

```kotlin
@Stable
class SideSheetState(
    initialValue: SideSheetValue = SideSheetValue.Hidden,
    density: Density,
    confirmValueChange: (SideSheetValue) -> Boolean = { true },
)
```

### Properties

| Property | Type | Description |
|----------|------|-------------|
| `currentValue` | `SideSheetValue` | Current sheet state (`Hidden` or `Expanded`) |
| `targetValue` | `SideSheetValue` | Target state during animation |
| `isVisible` | `Boolean` | `true` when `currentValue` is `Expanded` |
| `offset` | `Float` | Current drag offset in pixels |

### Functions

| Function | Description |
|----------|-------------|
| `suspend expand()` | Animate to `Expanded` state |
| `suspend hide()` | Animate to `Hidden` state |

## rememberSideSheetState

```kotlin
@Composable
fun rememberSideSheetState(
    initialValue: SideSheetValue = SideSheetValue.Hidden,
    confirmValueChange: (SideSheetValue) -> Boolean = { true },
): SideSheetState
```

Returns a `SideSheetState` that survives recomposition and configuration changes via `rememberSaveable`.

### Confirm Value Change

Use `confirmValueChange` to intercept state transitions:

```kotlin
val sheetState = rememberSideSheetState(
    confirmValueChange = { newValue ->
        if (newValue == SideSheetValue.Hidden && hasUnsavedChanges) {
            // Prevent dismiss when there are unsaved changes
            false
        } else {
            true
        }
    }
)
```

## SideSheetValue

```kotlin
enum class SideSheetValue {
    Hidden,
    Expanded,
}
```

## SideSheetEdge

```kotlin
enum class SideSheetEdge {
    Start,
    End,
}
```
