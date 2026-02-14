# Release Workflow Design

**Goal:** Automate the full release process — from git tag to Maven Central publication and GitHub Release creation.

**Current state:** Publishing is done manually from a local machine via `./gradlew publish` with credentials in `local.properties`. No automated release workflow exists.

---

## Trigger

Push a git tag matching `v*` (e.g., `v2.0.5`):

```bash
git tag v2.0.5
git push origin v2.0.5
```

## Pipeline

```
git push tag v2.0.5
       ↓
GitHub Actions (release.yml):
  1. Checkout + Setup JDK 21
  2. Validate: tag version == libs.versions.toml vbpd version
  3. ./gradlew check (build + tests + detekt + ktlint)
  4. ./gradlew publishAllPublicationsToMavenCentralRepository
  5. Create GitHub Release (tag message as changelog)
  6. Upload AAR artifacts to GitHub Release
```

## Versioning

Tag must match the version in `gradle/libs.versions.toml` (`vbpd = "2.0.5"`). CI validates this at step 2 — fails fast if mismatch. This prevents accidental releases with wrong versions.

## Credentials

Stored as GitHub Secrets:

| Secret | Purpose |
|--------|---------|
| `SIGNING_KEY_ID` | GPG key ID for signing |
| `SIGNING_KEY` | Base64-encoded GPG private key |
| `SIGNING_PASSWORD` | GPG key passphrase |
| `MAVEN_CENTRAL_USERNAME` | Sonatype Central Portal username |
| `MAVEN_CENTRAL_PASSWORD` | Sonatype Central Portal token |

## Changes Required

### New files
- `.github/workflows/release.yml` — tag-triggered release workflow

### Modified files
- `gradle/convetions-plugins/vbpd-library-base/src/main/kotlin/vbpdpublish.gradle.kts` — read credentials from environment variables (fallback to local.properties for local dev)
- `README.md` — add CI status and Maven Central version badges

### Deleted files
- `jitpack.yml` — JitPack is not used, config is outdated (JDK 11)

## Design Decisions

- **Tag-triggered** over workflow_dispatch: prevents accidental releases, ties version to git history
- **Version validation** over auto-extraction: explicit control, no build script magic
- **Environment variables** over secrets file: standard CI pattern, Vanniktech plugin supports it natively
- **Keep local.properties fallback**: developers can still publish locally if needed
