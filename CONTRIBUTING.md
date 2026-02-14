# Contributing

## Branch Strategy

| Branch | Purpose |
|--------|---------|
| `master` | Stable, released code. Every commit here is either a release or a release-ready state. |
| `develop` | Active development. All feature branches merge here first. |

### Rules

- **Never push directly to `master`.** All changes go through `develop` first.
- **`develop` → `master`** merge happens only when preparing a release.
- Feature branches are created from `develop` and merged back into `develop` via PR.
- Hotfix branches are created from `master`, merged into both `master` and `develop`.

### Branch Naming

| Type | Pattern | Example |
|------|---------|---------|
| Feature | `feature/<short-name>` | `feature/dialog-fragment-support` |
| Bug fix | `fix/<short-name>` | `fix/lifecycle-leak` |
| Hotfix (from master) | `hotfix/<short-name>` | `hotfix/crash-on-api-21` |
| Chore / CI | `chore/<short-name>` | `chore/update-dependencies` |
| Docs | `docs/<short-name>` | `docs/migration-guide` |

### Workflow

```
feature/xyz ──PR──► develop ──release──► master ──tag──► v2.0.5
                                                           │
                                                     GitHub Actions
                                                     publishes to
                                                     Maven Central
```

## Release Process

1. Ensure `develop` is stable and all tests pass
2. Merge `develop` into `master`
3. Update version in `gradle/libs.versions.toml` (`vbpd = "X.Y.Z"`)
4. Commit, tag, and push:
   ```bash
   git tag vX.Y.Z
   git push origin master --tags
   ```
5. CI automatically: validates version → runs checks → publishes to Maven Central → creates GitHub Release

## Development Setup

1. Clone the repository
2. Open in Android Studio
3. For local publishing, create `local.properties` with signing and Maven Central credentials

## Code Quality

The project uses:
- **Detekt** — Kotlin static analysis
- **ktlint** — Kotlin code formatting
- **Kover** — code coverage

Run all checks: `./gradlew check`
