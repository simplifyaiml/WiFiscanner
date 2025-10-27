# Wifistrength

Android Wi-Fi Scanner app with Mock Mode for cloud/emulator testing.

## How to build locally

1. Install Android Studio and Android SDK (recommended).
2. Open the project folder in Android Studio.
3. Build > Make Project or Run on a connected device.
   - To generate an APK: `Build > Build Bundle(s) / APK(s) > Build APK(s)`

## How to get APK via GitHub Actions (CI)

1. Push this repo to GitHub.
2. The included GitHub Actions workflow will run on push and produce `app-debug.apk` as an artifact.
3. Go to Actions tab → select run → download artifact `app-debug-apk`.

## Notes

- For real Wi-Fi scanning you need to run the app on a real Android device with location permission enabled.
- Mock Mode (toggle) shows fake networks when you don't have access to Wi-Fi hardware (cloud emulators).
