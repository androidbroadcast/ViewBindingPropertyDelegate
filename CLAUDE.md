# CLAUDE.md

## Project

ViewBindingPropertyDelegate — Android library for simplifying ViewBinding lifecycle management.

## Branch Strategy

- `master` — stable, released code. Never push directly.
- `develop` — active development. All feature branches merge here.
- Feature branches: `feature/<name>`, `fix/<name>`, `chore/<name>`, `docs/<name>`
- PRs always target `develop`, except hotfixes which target `master` (and then merge into `develop` too).
- Release: merge `develop` → `master`, tag `vX.Y.Z`, push tag to trigger CI release.

## Build

```bash
./gradlew check                    # full check (build + tests + detekt + ktlint)
./gradlew assembleRelease          # build AARs
./gradlew ktlintFormat             # auto-fix formatting
./gradlew koverHtmlReport          # code coverage report
```

## Project Structure

- `vbpd-core/` — base ViewBindingProperty classes (lazy, eager)
- `vbpd/` — delegates for Activity, Fragment, ViewGroup, ViewHolder (no reflection)
- `vbpd-reflection/` — reflection-based delegates + ViewBindingCache
- `sample/` — demo app
- `gradle/conventions-plugins/` — shared Gradle build logic (Detekt, ktlint, Kover, publishing)

## Conventions

- Version is in `gradle/libs.versions.toml` under `vbpd`
- Convention plugins apply Detekt, ktlint, Kover to all library modules
- Publishing uses Vanniktech Maven Publish to Sonatype Central Portal
- CI credentials via `ORG_GRADLE_PROJECT_*` env vars; local via `local.properties`
- Language: Kotlin, explicit API mode enabled
