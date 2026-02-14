# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project

ViewBindingPropertyDelegate — Android library that simplifies ViewBinding lifecycle management using Kotlin property delegates. Manages binding lifecycle, clears references to prevent memory leaks, and creates bindings lazily.

## Build Commands

```bash
./gradlew check                    # full check: build + unit tests + detekt + ktlint
./gradlew assembleRelease          # build AARs for all library modules
./gradlew ktlintFormat             # auto-fix Kotlin formatting
./gradlew koverHtmlReport          # generate code coverage report

# per-module commands
./gradlew :vbpd-core:check        # check single module
./gradlew :vbpd:assembleRelease   # build single module AAR

# tests
./gradlew test                                        # run all unit tests
./gradlew :vbpd-core:testDebugUnitTest                # tests for single module
./gradlew :vbpd:testDebugUnitTest --tests '*.ActivityViewBindingPropertyTest'  # single test class
```

Tests use JUnit 4 + Robolectric + MockK. Test sources are in `<module>/src/test/kotlin/`.

## Architecture

Three library modules with a linear dependency chain:

```
vbpd-core  ←(api)—  vbpd  ←(api)—  vbpd-reflection
```

### vbpd-core (foundation)
Defines `ViewBindingProperty<R, T>` interface (extends `ReadOnlyProperty` + `clear()`), plus `LazyViewBindingProperty` and `EagerViewBindingProperty` base classes. No Android component dependencies — only `androidx.viewbinding` + `androidx.annotation`.

### vbpd (no-reflection delegates)
Lifecycle-aware delegates for Activity, Fragment, ViewGroup, ViewHolder. Registers lifecycle callbacks to auto-clear bindings (Activity → `onDestroy`, Fragment → view destruction). AndroidX dependencies (`fragment`, `activity`, `recyclerview`) are **compile-only** — consumers provide their own versions.

Key files: `ActivityViewBindings.kt`, `FragmentViewBindings.kt`, `ViewGroupBindings.kt`, `ViewHolderBindings.kt`, `internal/VbpdUtils.kt`.

### vbpd-reflection (reflection + cache)
Adds reified-type overloads that discover `bind()`/`inflate()` methods via reflection. `ViewBindingCache` caches reflection lookups. `CreateMethod` enum selects BIND vs INFLATE strategy. Consumer ProGuard rules keep ViewBinding `bind()` and `inflate()` methods.

## Branch Strategy

- `master` — stable releases. Never push directly.
- `develop` — active development. All feature branches merge here.
- Feature branches: `feature/<name>`, `fix/<name>`, `chore/<name>`, `docs/<name>`
- PRs target `develop`, except hotfixes → `master` (then merge into `develop`).
- Release: merge `develop` → `master`, update version in `gradle/libs.versions.toml`, tag `vX.Y.Z`, push. CI publishes to Maven Central.

## Conventions

- **Version**: `gradle/libs.versions.toml` → `vbpd` key
- **Kotlin explicit API mode** enabled — all public declarations need explicit visibility modifiers
- **JVM target**: Java 11
- **Convention plugins** in `gradle/conventions-plugins/vbpd-library-base/` apply Detekt, ktlint, Kover, and Vanniktech Maven Publish to all library modules
- **Publishing**: Vanniktech Maven Publish → Sonatype Central Portal. CI uses `ORG_GRADLE_PROJECT_*` env vars; local dev uses `local.properties` with signing + Maven Central credentials
- **Package**: `dev.androidbroadcast.vbpd` (namespace per module)

## CI

- **android.yml**: Runs `./gradlew check` on push/PR to `master`
- **build.yml**: Builds release AARs on push to `develop`, uploads as artifacts
- **Dependabot**: Weekly checks for Gradle deps and GitHub Actions
