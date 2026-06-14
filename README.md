# People's Voice — Project 2
### TK2323 / TM2213 Mobile Programming
**Matric No:** A209816 | **Name:** Azmil Nazatul

---

## Project Description

People's Voice is an Android mobile application that empowers citizens to submit feedback, complaints, inquiries, suggestions, and appreciation directly to the relevant authorities. The app provides a transparent, accessible, and persistent platform for community engagement — ensuring every voice is heard and recorded.

Built with **Jetpack Compose** and following modern Android architecture (MVVM + Repository pattern), the app expands on Project 1 by integrating local persistence, cloud sync, live internet data, and hardware sensors.

---

## SDG Theme

**SDG 16 — Peace, Justice and Strong Institutions**

People's Voice supports SDG 16 by promoting inclusive and participatory governance. Citizens can raise issues, track their submission history, and stay informed through live news — fostering transparency and accountability between the public and institutions.

---

## Features

### Core Screens (8 Screens)
- **Login Screen** — User authentication entry point
- **Menu / Home Screen** — Navigation hub with dynamic submission counters per category
- **Form Screen** — Submit feedback with camera photo attachment and GPS location tagging
- **Result Screen** — Confirmation after submission
- **History Screen** — View all past submissions stored locally
- **History Detail Screen** — Full details of a single submission including photo and location
- **FAQ Screen** — Frequently asked questions
- **News Screen** — Live Malaysian news feed with browser link support

### Technical Pillars

#### 1. Local Persistence — Room Database
- All feedback submissions are saved permanently on-device using Room
- Data persists across app restarts and is accessible offline
- Schema: `feedback_table` with category, message, date, status, photo path, latitude, longitude

#### 2. Cloud Integration — Firebase Firestore
- Every submission is automatically synced to Firebase Firestore
- Community data is stored under the `community_feedback` collection
- Enables admin visibility and shared community feedback tracking

#### 3. Web API — GNews API (Retrofit)
- Fetches live Malaysian top headlines using the GNews API
- Built with Retrofit + Gson converter
- Displays article title, source, description, date, and a browser link to the full article
- Pull-to-refresh support via FAB

#### 4. Sensor Integration
- **Camera** — Users can attach a photo to their feedback submission
- **GPS / Location** — Automatically captures latitude and longitude when submitting feedback

---

## Tech Stack

| Technology | Usage |
|---|---|
| Jetpack Compose | UI framework |
| Navigation Compose | Screen navigation |
| ViewModel + StateFlow | State management |
| Room Database | Local persistence |
| Firebase Firestore | Cloud database |
| Retrofit + Gson | REST API client |
| GNews API | Live news feed |
| CameraX / FileProvider | Camera sensor |
| FusedLocationProvider | GPS sensor |
| Kotlin Coroutines | Async operations |
| KSP | Annotation processing |

---

## Setup Instructions

### Prerequisites
- Android Studio Hedgehog or later
- Android SDK 24+
- A physical or virtual device running Android 7.0+

### Steps

1. **Clone the repository**
   ```bash
   git clone https://github.com/YOUR_USERNAME/A209816_Azmil_Nazatul_Project2.git
   cd A209816_Azmil_Nazatul_Project2
   ```

2. **Add Firebase configuration**
    - Go to [Firebase Console](https://console.firebase.google.com)
    - Download `google-services.json` from your project settings
    - Place it in the `app/` directory

3. **Add your GNews API key**
    - Register at [gnews.io](https://gnews.io) for a free API key
    - Open `NewsViewModel.kt` and replace:
      ```kotlin
      private val API_KEY = "YOUR_GNEWS_API_KEY_HERE"
      ```
      with your actual key

4. **Sync Gradle**
    - Open the project in Android Studio
    - Click **Sync Now** when prompted

5. **Add to `gradle.properties`** (if not already present)
   ```properties
   android.disallowKotlinSourceSets=false
   ```

6. **Run the app**
    - Select a device or emulator
    - Click **Run** ▶

---

## Project Structure

```
app/src/main/java/com/example/a209816_azmil_nazatul_project02/
├── api/
│   ├── NewsApiService.kt       # Retrofit interface
│   ├── NewsModels.kt           # API response data classes
│   └── RetrofitClient.kt       # Retrofit singleton
├── data/
│   ├── FeedbackDao.kt          # Room DAO
│   └── FeedbackDatabase.kt     # Room Database
├── ui/theme/                   # App theme
├── Component.kt                # Shared UI components
├── FAQScreen.kt
├── FeedbackModels.kt           # Data models + Room Entity
├── FeedbackRepository.kt       # Repository (Room + Firestore)
├── FormScreen.kt
├── HistoryDetailScreen.kt
├── HistoryScreen.kt
├── LoginScreen.kt
├── MainActivity.kt
├── MenuScreen.kt
├── NewsScreen.kt
├── NewsViewModel.kt
├── People'sVoiceViewModel.kt
├── Result.kt
└── UserProfile.kt
```

---

## License

This project is developed for academic purposes under TK2323 / TM2213 Mobile Programming at Universiti Kebangsaan Malaysia (UKM).
