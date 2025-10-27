# 📶 Android Wi-Fi Scanner App

A lightweight Android app that scans nearby Wi-Fi networks and displays their **signal strength (RSSI)**, **signal bars**, and **security type (WPA2, WPA3, etc.)**, sorted by strength.  
The app automatically refreshes every few seconds to show live signal updates.

---

## 🚀 Features

✅ Scan nearby Wi-Fi networks  
✅ Display SSID and RSSI (in dBm)  
✅ Convert signal strength (RSSI) → percentage  
✅ Show signal bars (1–4 based on strength)  
✅ Sort networks by strongest signal  
✅ Display security type (WPA3 / WPA2 / WPA / WEP / Open)  
✅ Auto-refresh scan results every few seconds  

---

## 🧩 Screenshots

| Wi-Fi List | Signal Icons | Auto Refresh |
|-------------|---------------|---------------|
| ![screenshot1](docs/screenshot1.png) | ![screenshot2](docs/screenshot2.png) | ![screenshot3](docs/screenshot3.png) |



---

## 🏗️ Project Structure

WifiScannerApp/
├── app/
│ ├── java/com/example/wifiscanner/
│ │ ├── MainActivity.java
│ │ └── WifiListAdapter.java
│ └── res/
│ ├── layout/
│ │ ├── activity_main.xml
│ │ └── list_item_wifi.xml
│ └── drawable/
│ ├── ic_signal_1.xml
│ ├── ic_signal_2.xml
│ ├── ic_signal_3.xml
│ └── ic_signal_4.xml
├── AndroidManifest.xml
└── README.md


---

## ⚙️ Setup & Run

1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/WifiScannerApp.git
   cd WifiScannerApp

2. Open the project in Android Studio.

3. Make sure your AndroidManifest.xml includes:
   <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION"/>
   <uses-permission android:name="android.permission.ACCESS_WIFI_STATE"/>
   <uses-permission android:name="android.permission.CHANGE_WIFI_STATE"/>
4. Run the app on a real device (Wi-Fi scanning may not work properly on emulators).

5. 📜 License

This project is open-source under the MIT License.
You’re free to use, modify, and distribute it.


---

Would you like me to also include the **AndroidManifest.xml** in the zip (so it’s fully import-ready for Android Studio)?
