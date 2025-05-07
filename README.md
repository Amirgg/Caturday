# Caturday 🐾

A modern Android application built using Jetpack Compose and Clean Architecture to display a list of cat breeds, with support for pagination, offline caching, and favoriting.

![caturday](https://github.com/user-attachments/assets/cb6095c4-b950-4930-be5b-707f223c2df3)


---

## 🛠 Setup Instructions
1-Clone the repository:
```
git clone https://github.com/Amirgg/Caturday.git
cd caturday
```

2-Open in Android Studio (Arctic Fox or newer) with Kotlin 1.9+ and AGP 8.2+.

3-Register in `thecapapi.com` and add your Api Key to `local.properties` file.
```
API_KEY=your_valid_api_key
```

4-Run the app:
  - Ensure you have an emulator or device connected.
  - Press Run

## 🧱 Architectural Overview
This project follows Clean Architecture principles and is modularized into:
- **Data Layer:** Handles remote and local data sources.
  - `remote/`: Uses Retrofit with Kotlinx Serialization.
  - `db/`: Uses Room for local storage.
  - `repo/`: Abstracts data sources behind interfaces.
- **Domain Layer:** Contains models and use cases.
- **UI Layer:** Built with Jetpack Compose and MVVM.
- **DI Layer:** Hilt modules for dependency injection.
- **Util Layer:** Common helpers, constants, and extensions.
  
Data is observed from a single Room source. Pagination is triggered when reaching the list’s end, fetching the next page via the network and updating the database.

## 🔌 Library Choices
### Core
- **Jetpack Compose:** Declarative UI framework.
- **Material 3:** Modern Material components.
- **Room:** Local database persistence.
- **Retrofit + Kotlinx Serialization:** Networking.
- **DataStore Preferences:** Persistent key-value storage.
### DI & Navigation
- **Hilt:** Dependency injection.
- **Navigation Compose:** Screen navigation.
- **Hilt Navigation Compose:** Navigation-aware DI.
### Async & Testing
- **Kotlin Coroutines:** Asynchronous operations.
- **MockK:** Unit testing mocks.
- **Coroutine Test:** Structured testing of suspend functions.
### Image Loading
- **Coil Compose:** Image loading optimized for Compose.
### Tooling & Plugins
- **Ktlint:** Code formatting.
- **Safe Args:** Type-safe navigation.
- **KSP / KAPT:** Annotation processing.

## 📌 Notes
- Uses a **single source of truth**: Room database is the data source for the UI.
- Supports **offline-first** behavior.
- Favorites are persisted and toggleable.
- Pagination handled with awareness of pre-cached data.
- Supports light, dark theme with persistence across app restarts.

## 🌐 API Considerations
- The `/breeds` endpoint is documented to return an `image` field, but this field is **not present** in the actual response.
- The API is **accessible without an API key**, but usage may be subject to stricter **rate limits** or **quota restrictions**. It’s highly recommended to use a valid API key.
- Fields like `wikipedia_url` and `reference_image_id` can be **null**, and should be handled accordingly in the UI and domain models.

















