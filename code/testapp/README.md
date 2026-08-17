# Concierge Test App — Local Setup

A minimal Android app for running the Brand Concierge extension against a live Adobe
Experience Platform (AEP) Mobile SDK instance. Use it to launch the chat, send a message,
and get a streamed response.

## Prerequisites

- **Android Studio** (Ladybug or newer).
- **JDK 17** (bundled with recent Android Studio).
- **Android SDK** with the API level used by the project installed (`targetSdk = 36`).
- An **emulator or physical device** with **working internet access**. This is required —
  on startup the SDK downloads its configuration from `assets.adobedtm.com`, and the ECID
  used in requests is minted only after that config loads. Without network the chat never
  becomes ready.

## One-time setup

1. Clone the repo and check out the branch:
   ```bash
   git clone <repo-url>
   cd aepsdk-concierge-android
   ```
2. Open the **`code/`** directory in Android Studio (this is the Gradle root — it contains
   `settings.gradle.kts` with the `:testapp` and `:concierge` modules). Let Gradle sync.
   - `code/local.properties` points Gradle at your Android SDK. Android Studio creates it
     automatically; if building from the CLI, ensure it has `sdk.dir=/path/to/Android/sdk`.
3. No credentials or secrets are needed — the AEP App ID is already configured in
   [`ChatApp.kt`](src/main/kotlin/com/adobe/marketing/mobile/conciergetestapp/ChatApp.kt):
   ```kotlin
   MobileCore.setApplication(this)
   MobileCore.setLogLevel(LoggingMode.VERBOSE)
   MobileCore.initialize(this, APP_ID) { ... }
   ```
   `APP_ID` references the staging Adobe Launch property; the SDK auto-registers the
   extensions declared in [`build.gradle.kts`](build.gradle.kts).

## Run the app

From Android Studio: pick the **`testapp`** run configuration, choose an
emulator/device with internet, and press **Run**.

Or from the command line (in `code/`):
```bash
./gradlew :testapp:installDebug
```

## Send a request and get a response

1. Launch the app — it opens on the **Concierge Test App** home screen.
2. Wait for the SDK to finish initializing (config download + ECID). Once ready, the
   **🗨️ Compose Chat** button appears. (VERBOSE logs stream to Logcat under the
   `AdobeExperienceSDK` / `BrandConcierge` tags if you want to watch progress.)
3. Tap **🗨️ Compose Chat** to open the chat dialog.
4. Type a message (for example, *"What can I create with Adobe apps?"*) and send it.
5. The assistant's reply streams back into the conversation.

## Troubleshooting

- **The "Compose Chat" button never appears.** The chat is gated on configuration being
  loaded and an ECID being available, both of which require Adobe VPN network. Confirm the
  emulator/device can reach the internet (open a browser on it), then cold boot. Check
- **Gradle can't find the Android SDK.** Ensure `code/local.properties` has a valid
  `sdk.dir`.
