---
title: Installation
description: Add Compose Shelf to your project
---

## Gradle Dependency

Add the dependency to your `commonMain` source set:

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

### Version Catalog

```toml
# gradle/libs.versions.toml
[versions]
compose-shelf = "<version>"

[libraries]
compose-shelf = { module = "io.github.aldefy:shelf", version.ref = "compose-shelf" }
```

Then reference it:

```kotlin
commonMain.dependencies {
    implementation(libs.compose.shelf)
}
```

## Platform Artifacts

Gradle resolves the correct artifact automatically. For reference:

| Platform | Artifact ID |
|----------|-------------|
| Android | `shelf-android` |
| JVM Desktop | `shelf-jvm` |
| iOS arm64 | `shelf-iosarm64` |
| iOS Simulator arm64 | `shelf-iossimulatorarm64` |
| iOS x64 | `shelf-iosx64` |
| WasmJs | `shelf-wasmjs` |

## Requirements

- Kotlin 2.1.0+
- Compose Multiplatform 1.7.0+
- Android minSdk 23 (if targeting Android)

## Dependencies

Compose Shelf has **zero Material dependency**. It only depends on:

- `compose.runtime`
- `compose.foundation`
- `compose.ui`
- `compose.animation`

This means it works with any design system — Material 3, Material 2, or custom.
