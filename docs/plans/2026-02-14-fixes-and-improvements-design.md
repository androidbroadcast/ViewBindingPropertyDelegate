# Fixes and Improvements Design

**Goal:** Fix typos, improve documentation, harden ProGuard rules, and add Dependabot for automated dependency updates.

---

## Changes

### 1. Rename `convetions-plugins` → `conventions-plugins`

Fix typo in convention plugins directory name. Update reference in `settings.gradle.kts`.

### 2. Add `rootProject.name` to convention plugins settings

Add `rootProject.name = "conventions-plugins"` to eliminate Gradle caching warning.

### 3. Update `.gitignore`

Add `firebase-debug.log` and `**/build/` patterns.

### 4. Fix README.md code examples

- Wrong import: `javax.swing.text.View` → `android.view.View`
- Obsolete callback: `onViewDestroyed()` → `onDestroyView()`
- Typo: `super.onViewCreate(view)` → `super.onViewCreated(view, savedInstanceState)`

### 5. Rename `MainAcitivty.kt` → `MainActivity.kt` in sample

### 6. Enhance ProGuard rules for vbpd-reflection

Add explicit rules for all `inflate()` overloads used by `ViewBindingCache`.

### 7. Add Dependabot configuration

`.github/dependabot.yml` for gradle and github-actions ecosystems.
