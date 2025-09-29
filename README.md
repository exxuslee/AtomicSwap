# AtomicSwap (Android, Kotlin)

Минималистичное приложение с архитектурой Clean Architecture и модульным делением. Интерфейс на Jetpack Compose (Material3) с нижней навигацией на 4 вкладки: Тейкер, Мейкер, История, Настройки. Данные — SQLDelight, сеть — Ktor Client, DI — Koin, изображения — Coil, навигация — Navigation Compose. Есть переключатель темы (день/ночь) и i18n (en/ru).

## Стек
- Kotlin 2.0.20, Gradle Version Catalog
- Jetpack Compose + Material3, Navigation Compose
- Koin (android, compose)
- SQLDelight (android driver)
- Ktor Client (OkHttp, ContentNegotiation, Kotlinx Serialization JSON)
- Coil (coil-compose)

## Архитектура и модули
Проект разделён по Clean Architecture слоям и техническим core-модулям:

- `app` — точка входа, навигация, инициализация Koin, интеграция фич.
- `feature` — единый модуль фич с пакетами:
  - `com.exxlexxlee.atomicswap.feature.taker` — экран тейкера
  - `com.exxlexxlee.atomicswap.feature.maker` — экран мейкера
  - `com.exxlexxlee.atomicswap.feature.history` — экран истории
  - `com.exxlexxlee.atomicswap.feature.settings` — экран настроек + `ThemeController`
- `data` — реализации репозиториев, работа с БД (SQLDelight) и сетью (Ktor).
- `domain` — модели и интерфейсы репозиториев.
- `core:database` — схема SQLDelight (`history.sq`) и Koin-модуль драйвера.
- `core:network` — Ktor `HttpClient` и Koin-модуль.
- `core:ui` — тема Compose (`AtomicComposeTheme`).

Навигация: один `NavHost` в `app`, 4 роута соответствуют вкладкам в BottomBar.

## Локализация
- Строки вынесены в ресурсы с переводами: `values/strings.xml` (en, по умолчанию) и `values-ru/strings.xml`.
- Нижняя навигация использует `stringResource` для подписей.

## Тема (день/ночь)
- В `feature.settings.ThemeController` хранится состояние тёмной темы (`StateFlow<Boolean>`), зарегистрирован как `single` в Koin.
- `MainActivity` подписывается на состояние и пробрасывает в `AtomicComposeTheme(isDark)`.
- В `SettingsScreen` есть `Switch` для переключения темы.

## Версии и зависимости
Все версии вынесены в Gradle Version Catalog: `gradle/libs.versions.toml`.

Ключевые плагины:
- `com.android.application`, `com.android.library`
- `org.jetbrains.kotlin.android`, `org.jetbrains.kotlin.plugin.compose`
- `app.cash.sqldelight`

Минимальные требования
- minSdk 24, target/compileSdk 35
- JDK 17+

## Сборка и запуск
1) Откройте проект в Android Studio (последняя версия с поддержкой Kotlin 2.0.20).
2) Убедитесь, что в `local.properties` прописаны пути до SDK/NDK (обычно Android Studio делает это автоматически).
3) Синхронизируйте Gradle.
4) Соберите и запустите:
   - IDE: Run → Run ‘app’
   - CLI: `./gradlew :app:assembleDebug`

## Где что находится
- BottomBar и навигация: `app/src/main/java/com/example/atomicswap/ui/BottomNav.kt` и `MainActivity`/`MainContent`.
- Тема: `core/ui/.../theme/Theme.kt` (`AtomicComposeTheme`).
- SQLDelight схема: `core/database/.../sqldelight/.../history.sq`.
- Репозиторий истории (пример): `data/.../DataModule.kt` (заготовка с маппингом сущностей).
- Экран настроек с переключателем темы: `feature/.../feature/settings/SettingsScreen.kt`.

## Заметки
- Иконка лаунчера: адаптивная (`@mipmap/ic_launcher`).
- Для добавления зависимостей используйте алиасы из `libs.versions.toml`.
- Для новых строк добавляйте ключи в оба файла локализации (en/ru).

## Лицензия
MIT (или укажите вашу лицензию).