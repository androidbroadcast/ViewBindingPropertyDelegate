# ViewBindingPropertyDelegate: Тесты и Качество кода

**Дата**: 2026-02-14
**Статус**: Одобрен
**Версия библиотеки**: 2.0.4

## Цель

Добавить тестовое покрытие, статический анализ и контроль качества кода в проект ViewBindingPropertyDelegate. Исправить CI/CD пайплайн.

## Решения

### Подход

«Фундамент сначала» — последовательная стратегия:
1. Инструменты (Detekt + ktlint + Kover) → 2. Исправление замечаний линтеров → 3. Тесты → 4. CI

### Тестовый стек

| Инструмент | Назначение |
|---|---|
| kotlin.test | Ассерты (Kotlin-нативные) |
| JUnit4 | Раннер (совместимость с Robolectric и AndroidX Test) |
| AndroidX Test | ActivityScenario, FragmentScenario — единое API для локальных и instrumented тестов |
| Robolectric | Локальный запуск Android-тестов без эмулятора |
| MockK | Kotlin-нативный мокинг |

### Инструменты качества

| Инструмент | Назначение |
|---|---|
| Detekt | Статический анализ Kotlin-кода |
| ktlint | Форматирование и стиль кода |
| Kover | Измерение покрытия тестами (JetBrains) |

## Секция 1: Инфраструктура качества кода

### Detekt
- Подключается через convention plugin `vbpdconfig.gradle.kts`
- Кастомный `detekt.yml` с правилами для библиотеки
- Задача `detekt` добавляется в `check`

### ktlint
- Через плагин `ktlint-gradle`
- Стандартные Kotlin Coding Conventions
- Задача `ktlintCheck` добавляется в `check`

### Kover
- Подключается через convention plugin
- HTML и XML отчёты
- Минимальный порог покрытия: 60% (с планом повышения)

## Секция 2: Тестовое покрытие

### vbpd-core (юнит-тесты)
- `LazyViewBindingProperty` — ленивая инициализация, кэширование, очистка
- `EagerViewBindingProperty` — немедленная инициализация

### vbpd (Robolectric + AndroidX Test)
- `ActivityViewBindingProperty` — lifecycle Activity, очистка при onDestroy
- `FragmentViewBindingProperty` — lifecycle Fragment, очистка при onDestroyView
- `ViewGroupBindings` — обычный режим / edit mode
- `ViewHolderBindings` — привязка к ViewHolder
- DialogFragment — специальная обработка

### vbpd-reflection (юнит + Robolectric)
- `ViewBindingCache` — включение/выключение, корректность
- Рефлексия: BIND vs INFLATE, merge-layouts
- Lifecycle-тесты с reflection API

## Секция 3: Исправление CI

### build.yml (develop)
- Убрать `continue-on-error: true`
- Добавить `testReleaseUnitTest`
- Добавить `detekt`, `ktlintCheck`
- Добавить `koverXmlReport`

### android.yml (master)
- Аналогичные проверки для PR
- Upload coverage report как артефакт

## Что НЕ входит в скоуп
- Git pre-commit hooks
- Изменение существующего публичного API
- Новые фичи библиотеки
- Автоматизация публикации на Maven Central
