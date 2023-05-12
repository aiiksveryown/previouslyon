# Previously On

<img src="logo.jpg" align="left" width="150" />

Previously On is an Android application that helps users keep track of their favorite TV shows.

This project was adapted from the open source project [Vuey](https://github.com/m4ykey/Vuey/tree/ddcc8654023e6534ae252630fba596d02297e479) by [m4ykey](https://github.com/m4ykey).

## Features

- Discover popular TV shows.
- Search for TV shows.
- View TV show details.

## Project Setup

1. Clone repository and open the project in the latest version of Android Studio.
2. Generate and import your `google-services.json` file and put it in the `/app` directory.
3. Create `local.properties` and import it to `/app`.
4. Add your TMDB API key to `local.properties`:
```
TMDB_API_KEY=YOUR_TMDB_API_KEY
```
5. Build and run the project.

## Tech Stack

- [Retrofit](https://square.github.io/retrofit/) for network calls.
- [Kotlin Coroutines](https://github.com/Kotlin/kotlinx.coroutines) for asynchronous programming.
- [Dagger-Hilt](https://developer.android.com/training/dependency-injection/hilt-android) for dependency injection.
- [OkHttpClient](https://square.github.io/okhttp/4.x/okhttp/okhttp3/-ok-http-client/) for HTTP requests.
- [Firebase](https://firebase.google.com/) for backend services.
- [Room Database](https://developer.android.com/training/data-storage/room) for local data persistence.
- [Gson](https://github.com/google/gson) for handling JSON.
- [Coil](https://coil-kt.github.io/coil/) for image loading.