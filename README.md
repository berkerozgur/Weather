# 🌤️ Weather App

A modern Android weather application built with **Kotlin** and **Jetpack Compose**. This app allows
users to view a list of cities in Türkiye and view its current weather data, using the *
*openweathermap** API.

---

## ✨ Features

- 📍 Select from a list of cities in Türkiye
- ☁️ Live weather data with temperature, conditions, wind, etc.
- 🧱 Beautiful UI built entirely in **Jetpack Compose**
- ⚡ Reactive architecture using **ViewModel** and **StateFlow**
- 🖼️ Image loading with **Coil**
- 🔧 Easy environment setup via `env` file and [
  `dotenv-kotlin`](https://github.com/cdimascio/dotenv-kotlin)

---

## 🛠️ Tech Stack

- **Kotlin**
- **Jetpack Compose**
- **MVVM Architecture**
- **Retrofit** for networking
- **Coil** for image loading
- **openweathermap API**
- **dotenv-kotlin** for local API config

---

## 🚀 Getting Started

1. **Clone the repo**

   ```bashbash
   git clone https://github.com/berkerozgur/Weather.git
   ```

2. **Get an API key**\
   Sign up at [openweathermap.org](https://openweathermap.org/) and generate a free API key.

3. **Create a **`env`** file**\
   Inside `app/src/main/assets/`, create a file named `env`:

   ```
   API_KEY=your_openweathermap_api_key
   ```

   See [`dotenv-kotlin`](https://github.com/cdimascio/dotenv-kotlin) for more details on setting up
   environment configuration in Android.

4. **Build & Run** Open the project in Android Studio and run it on an emulator or device.

---