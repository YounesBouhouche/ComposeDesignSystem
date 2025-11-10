# Compose Design System

A comprehensive Material Design 3 component library for Jetpack Compose with expressive animations, networking utilities, and theming support.

## 🌟 Features

- **Material Design 3** components with expressive animations
- **Dynamic theming** with Material You support
- **Network utilities** for safe API calls with proper error handling
- **Custom UI components** built on top of Material 3
- **Resource state management** for loading, error, and success states
- **Image loading** with Coil integration
- **Responsive design** utilities

## 📋 Requirements

- **Min SDK**: 30 (Android 11)
- **Target SDK**: 36
- **Kotlin**: 2.2.21
- **Java**: 21
- **Compose BOM**: 2025.10.01

## 🚀 Installation

### Add to your project

1. Add JitPack repository to your `settings.gradle.kts`:

```kotlin
dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven { url = URI.create("https://jitpack.io") }
    }
}
```

2. Add the dependency to your app's `build.gradle.kts`:

```kotlin
dependencies {
    implementation("com.github.YounesBouhouche:ComposeDesignSystem:v0.1.0")
}
```

## 📚 Components

### UI Components

#### ExpressiveButton
Animated buttons with Material 3 Expressive design supporting loading states and icons.

```kotlin
ExpressiveButton(
    text = { Text("Click Me") },
    icon = Icons.Default.Add,
    size = ButtonDefaults.MediumContainerHeight,
    loading = false,
    outlined = false,
    onClick = { /* Handle click */ }
)
```

#### ButtonsRow
Animated row of buttons with dynamic weight distribution on press.

```kotlin
ButtonsRow(
    count = 3,
    icon = { index -> Icons.Default.Star },
    text = { index -> "Button $index" },
    outlined = { index -> index == 1 },
    onClick = { index -> /* Handle click */ },
    expandedWeight = 1.15f
)
```

#### Dialog
Customizable Material 3 dialog with flexible content.

```kotlin
Dialog(
    visible = showDialog,
    onDismissRequest = { showDialog = false },
    title = "Confirmation",
    cancelText = "Cancel",
    okListener = { /* Handle OK */ },
    cancelListener = { /* Handle Cancel */ }
) {
    Text("Dialog content goes here")
}
```

#### MyImage
Image component with loading states, error handling, and Coil integration.

```kotlin
MyImage(
    model = "https://example.com/image.jpg",
    icon = Icons.Default.Image,
    shape = CircleShape,
    onClick = { /* Handle click */ },
    onSuccess = { /* Handle success */ },
    onError = { /* Handle error */ }
)
```

#### ResourceView
Generic component for handling different resource states (Idle, Loading, Success, Error).

```kotlin
ResourceView(
    resource = myResource,
    loadingContent = { CircularProgressIndicator() },
    errorContent = { error -> ErrorView(error) }
) { data ->
    // Success content
    SuccessContent(data)
}
```

#### ExpressiveIconButton
Icon button with Material 3 Expressive animations.

```kotlin
ExpressiveIconButton(
    icon = Icons.Default.Favorite,
    onClick = { /* Handle click */ },
    enabled = true
)
```

#### IconContainer
Container for icons with customizable background and styling.

```kotlin
IconContainer(
    icon = Icons.Default.Star,
    backgroundColor = MaterialTheme.colorScheme.primaryContainer,
    iconTint = MaterialTheme.colorScheme.onPrimaryContainer
)
```

### Theming

#### AppTheme
Flexible theming system with dynamic color support, AMOLED mode, and Material You integration.

```kotlin
AppTheme(
    primary = Color(0xFF6200EE),
    dark = isSystemInDarkTheme(),
    amoled = false,
    dynamic = true
) {
    // Your app content
}
```

**Features:**
- Dynamic color scheme from primary, secondary, or tertiary colors
- Material You dynamic colors (Android 12+)
- AMOLED mode with pure black backgrounds
- Custom typography support

### Networking

#### Result Type
Type-safe result wrapper for handling success and error states.

```kotlin
sealed interface Result<out D, out E: Error> {
    data class Success<out D>(val data: D): Result<D, Nothing>
    data class Error<out E: Error>(val error: E): Result<Nothing, E>
}

// Usage
result
    .onSuccess { data -> println(data) }
    .onError { error -> println(error) }
    .map { data -> transformData(data) }
```

#### Safe Network Calls
Utilities for safe API calls with automatic error handling.

```kotlin
suspend fun fetchData(): Result<MyData, NetworkError> {
    return safeCall(
        getBody = { body() },
        getCode = { code() }
    ) {
        api.getData()
    }
}
```

**Supported Error Types:**
- `NO_INTERNET`: Network connection unavailable
- `SERIALIZATION_ERROR`: JSON parsing failed
- `BAD_REQUEST`: 400 error
- `UNAUTHORIZED`: 401 error
- `FORBIDDEN`: 403 error
- `NOT_FOUND`: 404 error
- `REQUEST_TIMEOUT`: 408 error
- `TOO_MANY_REQUESTS`: 429 error
- `SERVER_ERROR`: 5xx errors
- `UNKNOWN`: Other errors

### Utilities

#### Resource State Management
State wrapper for async operations with Loading, Success, and Error states.

```kotlin
sealed interface Resource<out T, out E: Error> {
    data object Idle : Resource<Nothing, Nothing>
    data object Loading : Resource<Nothing, Nothing>
    data class Success<out T>(val data: T) : Resource<T, Nothing>
    data class Error<out E: Error>(val error: E) : Resource<Nothing, E>
}
```

#### Typography Utilities
Custom typography extensions and utilities for consistent text styling.

#### Padding Values
Standardized padding values for consistent spacing throughout your app.

#### Long to String Utils
Utilities for formatting long values to human-readable strings.

## 🎨 Design Philosophy

This library follows Material Design 3 principles with emphasis on:

- **Expressive animations**: Smooth, delightful interactions
- **Dynamic color**: Adaptive theming based on user preferences
- **Type safety**: Comprehensive error handling with sealed interfaces
- **Composability**: Small, reusable components that work together
- **Accessibility**: Built-in support for accessibility features

## 📦 Dependencies

Key dependencies included:
- Jetpack Compose (Material 3)
- Material Kolor (Dynamic theming)
- Coil (Image loading)
- KMPalette (Color extraction)
- Kotlinx Serialization
- Material Motion Compose Navigation
- And more...

## 🔧 Building from Source

```bash
git clone https://github.com/yourusername/compose-design-system.git
cd compose-design-system
./gradlew build
```

## 📄 License

This project is available under the MIT License.

## 🤝 Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

## 📞 Support

For issues and questions, please use the GitHub issue tracker.

## 🙏 Acknowledgments

Built with:
- [Jetpack Compose](https://developer.android.com/jetpack/compose)
- [Material Design 3](https://m3.material.io/)
- [Material Kolor](https://github.com/jordond/materialkolor)
- [Coil](https://coil-kt.github.io/coil/)
- [KMPalette](https://github.com/jordond/kmpalette)

---

Made with ❤️ by [YounesBouhouche](https://www.linkedin.com/in/younesbouh05?utm_source=share&utm_campaign=share_via&utm_content=profile&utm_medium=android_app) for the Android community

