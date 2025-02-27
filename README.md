# Movie App

## 📌 Overview

This is a Movie App that allows users to search for movies, view details, and mark/unmark movies as favorites. The app utilizes **Retrofit** for network requests, **Room Database** for local storage, and **ViewModel** for managing UI-related data.

---

## 🚀 Features

- **Movie Search**: Search for movies by title.
- **Movie Details**: View details including title, overview, release date, and trailer.
- **Favorites Management**: Mark/unmark movies as favorites and persist the state using RoomDB.
- **Offline Support**: Favorite movies remain available even without an internet connection.
- **Modern UI**: Uses ViewPager2, RecyclerView, and Material Design components.

---

## 🛠️ Tech Stack

- **Programming Language**: Kotlin
- **Architecture**: MVVM (Model-View-ViewModel)
- **Network Layer**: Retrofit with Coroutines
- **Local Storage**: Room Database
- **Dependency Injection**: Hilt (Dagger)
- **UI Components**: RecyclerView, ViewPager2, SearchView
- **Testing**: JUnit, Mockito, Espresso (for UI Tests)
