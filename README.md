# drumur

Drumur is a drum pattern generator. It is written in Kotlin with Compose Multiplatform, to support both desktop and Android.

## How to run

### Desktop
- `./gradlew run` - run application
- `./gradlew package` - package native distribution into `build/compose/binaries`

### Android

Android will be supported in the future.

- `./gradlew installDebug` - install Android application on an Android device (on a real device or on an emulator)

## Repository structure

`android`: code to start the Android app
`common/src/androidMain`: app code for Android
`common/src/commonMain`: app code that is independent of the platform
`common/src/desktopMain`: app code for desktop
`desktop`: code to start the desktop app