# Compose Shelf

[![Maven Central](https://img.shields.io/maven-central/v/io.github.aldefy/shelf)](https://central.sonatype.com/artifact/io.github.aldefy/shelf)
[![Kotlin](https://img.shields.io/badge/Kotlin-2.1.20-blue.svg?logo=kotlin)](https://kotlinlang.org)
[![Compose Multiplatform](https://img.shields.io/badge/Compose%20Multiplatform-1.8.0-blue)](https://www.jetbrains.com/compose-multiplatform/)
[![License](https://img.shields.io/badge/License-Apache%202.0-green.svg)](LICENSE)

**Material 3 Side Sheet for Compose Multiplatform** — the missing M3 component Google never shipped for Compose.

Google shipped `SideSheetBehavior` and `SideSheetDialog` for Views (Material 1.8.0), but Compose has zero implementation. Compose Shelf fills that gap with three variants:

- **ModalSideSheet** — popup overlay with scrim and swipe-to-dismiss
- **StandardSideSheet** — inline panel without popup or scrim
- **AdaptiveSheet** — side sheet on tablets, bottom sheet on phones

<p align="center">
  <img src="assets/demo.gif" alt="Compose Shelf Demo" width="600"/>
</p>

## Installation

```kotlin
// build.gradle.kts
kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation("io.github.aldefy:shelf:<version>")
        }
    }
}
```

<details>
<summary>Version catalog</summary>

```toml
# gradle/libs.versions.toml
[versions]
compose-shelf = "<version>"

[libraries]
compose-shelf = { module = "io.github.aldefy:shelf", version.ref = "compose-shelf" }
```

</details>

## Quick Start

```kotlin
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
```

### Adaptive (side sheet + bottom sheet)

```kotlin
// Side sheet on tablets (≥600dp), bottom sheet on phones
AdaptiveSheet(
    onDismissRequest = { showSheet = false },
) {
    Text("Adapts to screen width!")
}
```

### Standard (inline panel)

```kotlin
Row {
    MainContent(modifier = Modifier.weight(1f))
    StandardSideSheet {
        Text("Persistent detail panel")
    }
}
```

## M3 Spec Compliance

| Feature | Status |
|---------|--------|
| 28dp corner radius (cornerLarge) | Implemented |
| Level 1 elevation shadow | Implemented |
| Drag handle indicator | Implemented |
| Scrim overlay (32% black) | Implemented |
| Swipe-to-dismiss | Implemented |
| RTL support | Implemented |
| Accessibility (paneTitle, dismiss action) | Implemented |
| Start/End edge | Implemented |

## Customization

Every parameter has an M3-compliant default but is fully overridable:

```kotlin
ModalSideSheet(
    onDismissRequest = { /* ... */ },
    sheetMaxWidth = 420.dp,
    edge = SideSheetEdge.Start,
    shadowElevation = 4.dp,
    shape = RoundedCornerShape(16.dp),
    colors = SideSheetDefaults.colors(
        containerColor = MaterialTheme.colorScheme.surfaceContainerLow,
        scrimColor = Color.Black.copy(alpha = 0.5f),
    ),
    dragHandle = { SideSheetDragHandle() },
    gesturesEnabled = true,
) {
    // Content
}
```

## Platform Support

| Platform | Artifact |
|----------|----------|
| Android | `shelf-android` |
| JVM Desktop | `shelf-jvm` |
| iOS arm64 | `shelf-iosarm64` |
| iOS Simulator arm64 | `shelf-iossimulatorarm64` |
| iOS x64 | `shelf-iosx64` |
| WasmJs | `shelf-wasmjs` |

## Documentation

Full docs at [aldefy.github.io/compose-shelf](https://aldefy.github.io/compose-shelf)

## License

```
Copyright 2025 Adit Lal

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
