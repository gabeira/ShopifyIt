# ShopifyIt App
![N|Solid](https://raw.githubusercontent.com/gabeira/ShopifyIt/master/app/src/main/res/mipmap-mdpi/ic_launcher.png)

This is an Android App to show Shopify Github Repositories.

  - Kotlin Development
  - MVVM Architecture
  - Coroutines
  - Room Persistence
  - Retrofit Http Client
  - Dagger

## Configuration

This Project was developed using [Android Studio](https://developer.android.com/studio/)

To Download the code from this Repository you can use Android Studio or command line, running:
```sh
git clone https://github.com/gabeira/ShopifyIt
```
To Build the Project, you can use Android Studio or from command line, just run:
```sh
./gradlew build
```
To install debug app from command line, use:
```sh
./adb install /app/build/outputs/apk/app-debug.apk
```

## External Libs Reference

- [Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html)
- [Kotlin Coroutine Adapter](https://github.com/JakeWharton/retrofit2-kotlin-coroutines-adapter)
- [Room Persistence](https://developer.android.com/topic/libraries/architecture/room)
- [Retrofit](https://square.github.io/retrofit/)
- [Dagger](https://google.github.io/dagger/)

## Tests

There is some small tests done, but essential for the functionalities, you can run on Android Studio or from the command line,
to run the Unit Tests just use:
```sh
./gradlew test
```

Also there is some Connected Android Tests, but this requires to have a device or emulator connected:
```sh
./gradlew connectedAndroidTest
```