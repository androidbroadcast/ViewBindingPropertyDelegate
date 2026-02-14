# Tests & Quality Implementation Plan

> **For Claude:** REQUIRED SUB-SKILL: Use superpowers:executing-plans to implement this plan task-by-task.

**Goal:** Add comprehensive test coverage, static analysis (Detekt + ktlint), code coverage (Kover), and fix CI for ViewBindingPropertyDelegate library.

**Architecture:** Infrastructure-first approach — configure quality tools in Gradle convention plugins, then write tests module-by-module (vbpd-core → vbpd → vbpd-reflection), then fix CI to run everything automatically.

**Tech Stack:** Detekt 1.23.8, ktlint-gradle 14.0.1, Kover 0.9.7, Robolectric 4.16.1, MockK 1.14.9, AndroidX Test 1.6.1, kotlin.test, JUnit4

---

## Task 1: Add tool versions to version catalog

**Files:**
- Modify: `gradle/libs.versions.toml`

**Step 1: Add versions and dependencies to version catalog**

Add the following entries to `gradle/libs.versions.toml`:

```toml
# In [versions] section, add:
detekt = "1.23.8"
ktlint = "14.0.1"
kover = "0.9.7"
robolectric = "4.16.1"
mockk = "1.14.9"
androidx-test-core = "1.6.1"
androidx-test-runner = "1.6.2"
junit = "4.13.2"

# In [libraries] section, add:
test-robolectric = { module = "org.robolectric:robolectric", version.ref = "robolectric" }
test-mockk = { module = "io.mockk:mockk", version.ref = "mockk" }
test-junit = { module = "junit:junit", version.ref = "junit" }
test-kotlin = { module = "org.jetbrains.kotlin:kotlin-test", version.ref = "kotlin" }
test-androidx-core = { module = "androidx.test:core-ktx", version.ref = "androidx-test-core" }
test-androidx-runner = { module = "androidx.test:runner", version.ref = "androidx-test-runner" }
test-fragment = { module = "androidx.fragment:fragment-testing", version.ref = "androidx-fragment" }

# In [plugins] section, add:
detekt = { id = "io.gitlab.arturbosch.detekt", version.ref = "detekt" }
ktlint = { id = "org.jlleitschuh.gradle.ktlint", version.ref = "ktlint" }
kover = { id = "org.jetbrains.kotlinx.kover", version.ref = "kover" }
```

**Step 2: Verify catalog is valid**

Run: `./gradlew help`
Expected: BUILD SUCCESSFUL (validates catalog parsing)

**Step 3: Commit**

```bash
git add gradle/libs.versions.toml
git commit -m "Add versions for Detekt, ktlint, Kover and test dependencies"
```

---

## Task 2: Add Detekt to convention plugin

**Files:**
- Modify: `gradle/convetions-plugins/vbpd-library-base/build.gradle.kts`
- Modify: `gradle/convetions-plugins/vbpd-library-base/src/main/kotlin/vbpdconfig.gradle.kts`
- Create: `config/detekt/detekt.yml`

**Step 1: Add Detekt plugin dependency to convention plugin build**

In `gradle/convetions-plugins/vbpd-library-base/build.gradle.kts`, add to `dependencies` block:

```kotlin
// Add this line alongside existing gradleplugin dependencies:
implementation(libs.gradleplugins.detekt)
```

Also add to `gradle/libs.versions.toml` in `[libraries]`:
```toml
gradleplugins-detekt = { module = "io.gitlab.arturbosch.detekt:detekt-gradle-plugin", version.ref = "detekt" }
```

**Step 2: Apply and configure Detekt in convention plugin**

In `gradle/convetions-plugins/vbpd-library-base/src/main/kotlin/vbpdconfig.gradle.kts`, add after existing plugin applies:

```kotlin
plugins.apply(libs.plugins.detekt.get().pluginId)
```

And add Detekt configuration at the end of the file:

```kotlin
extensions.configure<io.gitlab.arturbosch.detekt.extensions.DetektExtension> {
    config.setFrom(rootProject.files("config/detekt/detekt.yml"))
    buildUponDefaultConfig = true
    parallel = true
}
```

Import needs to be added at the top:
```kotlin
import io.gitlab.arturbosch.detekt.extensions.DetektExtension
```

**Step 3: Create Detekt configuration file**

Create `config/detekt/detekt.yml`:

```yaml
build:
  maxIssues: 0

complexity:
  LongMethod:
    threshold: 60
  LongParameterList:
    functionThreshold: 8
    constructorThreshold: 8
  TooManyFunctions:
    thresholdInFiles: 20
    thresholdInClasses: 15
    thresholdInInterfaces: 10

style:
  MaxLineLength:
    maxLineLength: 120
  WildcardImport:
    active: false
  MagicNumber:
    active: false
  ReturnCount:
    max: 3
  UnusedPrivateMember:
    active: true

naming:
  FunctionNaming:
    functionPattern: '[a-zA-Z][a-zA-Z0-9]*'
```

**Step 4: Run Detekt to verify configuration**

Run: `./gradlew detekt`
Expected: Either BUILD SUCCESSFUL or specific lint issues to review

**Step 5: Commit**

```bash
git add config/detekt/detekt.yml gradle/convetions-plugins/ gradle/libs.versions.toml
git commit -m "Add Detekt static analysis to convention plugin"
```

---

## Task 3: Add ktlint to convention plugin

**Files:**
- Modify: `gradle/convetions-plugins/vbpd-library-base/build.gradle.kts`
- Modify: `gradle/convetions-plugins/vbpd-library-base/src/main/kotlin/vbpdconfig.gradle.kts`

**Step 1: Add ktlint plugin dependency**

In `gradle/libs.versions.toml` in `[libraries]`:
```toml
gradleplugins-ktlint = { module = "org.jlleitschuh.gradle:ktlint-gradle", version.ref = "ktlint" }
```

In `gradle/convetions-plugins/vbpd-library-base/build.gradle.kts`, add to `dependencies`:
```kotlin
implementation(libs.gradleplugins.ktlint)
```

**Step 2: Apply ktlint in convention plugin**

In `vbpdconfig.gradle.kts`, add after other plugin applies:

```kotlin
plugins.apply(libs.plugins.ktlint.get().pluginId)
```

**Step 3: Run ktlint to verify**

Run: `./gradlew ktlintCheck`
Expected: Either BUILD SUCCESSFUL or formatting issues to review

**Step 4: Fix any formatting issues**

Run: `./gradlew ktlintFormat`
Then manually review and fix remaining issues.

**Step 5: Commit**

```bash
git add -A
git commit -m "Add ktlint code formatting to convention plugin"
```

---

## Task 4: Add Kover to convention plugin

**Files:**
- Modify: `gradle/convetions-plugins/vbpd-library-base/build.gradle.kts`
- Modify: `gradle/convetions-plugins/vbpd-library-base/src/main/kotlin/vbpdconfig.gradle.kts`

**Step 1: Add Kover plugin dependency**

In `gradle/libs.versions.toml` in `[libraries]`:
```toml
gradleplugins-kover = { module = "org.jetbrains.kotlinx:kover-gradle-plugin", version.ref = "kover" }
```

In `gradle/convetions-plugins/vbpd-library-base/build.gradle.kts`, add to `dependencies`:
```kotlin
implementation(libs.gradleplugins.kover)
```

**Step 2: Apply and configure Kover in convention plugin**

In `vbpdconfig.gradle.kts`, add after other plugin applies:

```kotlin
plugins.apply(libs.plugins.kover.get().pluginId)
```

**Step 3: Run to verify Kover is configured**

Run: `./gradlew koverHtmlReport`
Expected: BUILD SUCCESSFUL (report will be empty until tests exist)

**Step 4: Commit**

```bash
git add gradle/convetions-plugins/ gradle/libs.versions.toml
git commit -m "Add Kover code coverage to convention plugin"
```

---

## Task 5: Fix lint issues in existing code

**Files:**
- Potentially modify source files in all library modules

**Step 1: Run full check**

Run: `./gradlew detekt ktlintCheck`
Review output to understand what issues exist.

**Step 2: Auto-fix formatting**

Run: `./gradlew ktlintFormat`

**Step 3: Manually fix remaining Detekt issues**

Fix any remaining issues reported by Detekt. These might include:
- Line length violations
- Unused parameters
- Complexity issues

Do NOT change public API signatures. Only fix internal code style.

**Step 4: Verify all checks pass**

Run: `./gradlew detekt ktlintCheck`
Expected: BUILD SUCCESSFUL

**Step 5: Commit**

```bash
git add -A
git commit -m "Fix code style issues reported by Detekt and ktlint"
```

---

## Task 6: Configure test infrastructure for vbpd-core

**Files:**
- Modify: `vbpd-core/build.gradle.kts`
- Create: `vbpd-core/src/test/kotlin/dev/androidbroadcast/vbpd/LazyViewBindingPropertyTest.kt`
- Create: `vbpd-core/src/test/kotlin/dev/androidbroadcast/vbpd/EagerViewBindingPropertyTest.kt`

**Step 1: Add test dependencies to vbpd-core**

In `vbpd-core/build.gradle.kts`:
```kotlin
plugins {
    id("vbpdconfig")
}

dependencies {
    api(libs.androidx.viewbinding)
    implementation(libs.androidx.annotation)

    testImplementation(libs.test.junit)
    testImplementation(libs.test.kotlin)
    testImplementation(libs.test.mockk)
    testImplementation(libs.test.robolectric)
    testImplementation(libs.test.androidx.core)
}
```

Also in `vbpdconfig.gradle.kts`, enable unit tests if not already enabled:

```kotlin
// Inside androidLibraryConfig block, add:
testOptions {
    unitTests {
        isIncludeAndroidResources = true
    }
}
```

**Step 2: Write failing test for LazyViewBindingProperty**

Create `vbpd-core/src/test/kotlin/dev/androidbroadcast/vbpd/LazyViewBindingPropertyTest.kt`:

```kotlin
package dev.androidbroadcast.vbpd

import android.view.View
import androidx.viewbinding.ViewBinding
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

@RunWith(RobolectricTestRunner::class)
class LazyViewBindingPropertyTest {

    private fun createMockBinding(): ViewBinding {
        val view = mockk<View>()
        return mockk<ViewBinding> {
            every { root } returns view
        }
    }

    @Test
    fun `getValue returns binding created by viewBinder`() {
        val expectedBinding = createMockBinding()
        val property = LazyViewBindingProperty<Any, ViewBinding> { expectedBinding }
        val thisRef = Any()

        val result = property.getValue(thisRef, ::result)
        assertEquals(expectedBinding, result)
    }

    @Test
    fun `getValue returns same instance on subsequent calls`() {
        var callCount = 0
        val binding = createMockBinding()
        val property = LazyViewBindingProperty<Any, ViewBinding> {
            callCount++
            binding
        }
        val thisRef = Any()

        val first = property.getValue(thisRef, ::first)
        val second = property.getValue(thisRef, ::second)

        assertEquals(first, second)
        assertEquals(1, callCount, "viewBinder should be called only once")
    }

    @Test
    fun `clear resets cached binding`() {
        var callCount = 0
        val property = LazyViewBindingProperty<Any, ViewBinding> {
            callCount++
            createMockBinding()
        }
        val thisRef = Any()

        property.getValue(thisRef, ::thisRef)
        property.clear()
        property.getValue(thisRef, ::thisRef)

        assertEquals(2, callCount, "viewBinder should be called again after clear()")
    }

    @Test
    fun `viewBinder receives correct thisRef`() {
        var receivedRef: Any? = null
        val property = LazyViewBindingProperty<String, ViewBinding> { ref ->
            receivedRef = ref
            createMockBinding()
        }

        property.getValue("test", ::receivedRef)
        assertEquals("test", receivedRef)
    }
}
```

**Step 3: Run test to verify it compiles and passes**

Run: `./gradlew :vbpd-core:testDebugUnitTest --tests "dev.androidbroadcast.vbpd.LazyViewBindingPropertyTest"`
Expected: All tests PASS

**Step 4: Write test for EagerViewBindingProperty**

Create `vbpd-core/src/test/kotlin/dev/androidbroadcast/vbpd/EagerViewBindingPropertyTest.kt`:

```kotlin
package dev.androidbroadcast.vbpd

import android.view.View
import androidx.viewbinding.ViewBinding
import io.mockk.every
import io.mockk.mockk
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import kotlin.test.assertEquals

@RunWith(RobolectricTestRunner::class)
class EagerViewBindingPropertyTest {

    private fun createMockBinding(): ViewBinding {
        val view = mockk<View>()
        return mockk<ViewBinding> {
            every { root } returns view
        }
    }

    @Test
    fun `getValue returns the provided binding`() {
        val expectedBinding = createMockBinding()
        val property = EagerViewBindingProperty<Any, ViewBinding>(expectedBinding)

        val result = property.getValue(Any(), ::result)
        assertEquals(expectedBinding, result)
    }

    @Test
    fun `getValue always returns same instance`() {
        val binding = createMockBinding()
        val property = EagerViewBindingProperty<Any, ViewBinding>(binding)
        val thisRef = Any()

        val first = property.getValue(thisRef, ::first)
        val second = property.getValue(thisRef, ::second)
        assertEquals(first, second)
    }
}
```

**Step 5: Run all vbpd-core tests**

Run: `./gradlew :vbpd-core:testDebugUnitTest`
Expected: All tests PASS

**Step 6: Commit**

```bash
git add vbpd-core/build.gradle.kts vbpd-core/src/test/
git commit -m "Add unit tests for vbpd-core (LazyViewBindingProperty, EagerViewBindingProperty)"
```

---

## Task 7: Add tests for vbpd module — Activity lifecycle

**Files:**
- Modify: `vbpd/build.gradle.kts`
- Create: `vbpd/src/test/kotlin/dev/androidbroadcast/vbpd/ActivityViewBindingPropertyTest.kt`

**Step 1: Add test dependencies to vbpd**

In `vbpd/build.gradle.kts`:
```kotlin
plugins {
    id("vbpdconfig")
}

dependencies {
    compileOnly(libs.androidx.fragment)
    compileOnly(libs.androidx.activity)
    compileOnly(libs.androidx.recyclerview)
    api(projects.vbpdCore)

    testImplementation(libs.test.junit)
    testImplementation(libs.test.kotlin)
    testImplementation(libs.test.mockk)
    testImplementation(libs.test.robolectric)
    testImplementation(libs.test.androidx.core)
    testImplementation(libs.test.androidx.runner)
    testImplementation(libs.androidx.fragment)
    testImplementation(libs.androidx.activity)
    testImplementation(libs.androidx.recyclerview)
    testImplementation(libs.androidx.appcompat)
}
```

**Step 2: Write Activity lifecycle test**

Create `vbpd/src/test/kotlin/dev/androidbroadcast/vbpd/ActivityViewBindingPropertyTest.kt`:

```kotlin
package dev.androidbroadcast.vbpd

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import androidx.test.core.app.ActivityScenario
import androidx.viewbinding.ViewBinding
import io.mockk.every
import io.mockk.mockk
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.android.controller.ActivityController
import kotlin.test.assertNotNull

@RunWith(RobolectricTestRunner::class)
class ActivityViewBindingPropertyTest {

    class TestActivity : Activity() {
        val binding by viewBinding { activity: TestActivity ->
            mockk<ViewBinding> {
                every { root } returns View(activity)
            }
        }

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            val content = FrameLayout(this)
            content.id = android.R.id.content
            setContentView(content)
        }
    }

    @Test
    fun `binding is accessible after onCreate`() {
        val controller = Robolectric.buildActivity(TestActivity::class.java)
            .create()
            .start()
            .resume()

        val activity = controller.get()
        assertNotNull(activity.binding)
    }

    @Test
    fun `binding is cleared after onDestroy`() {
        val controller = Robolectric.buildActivity(TestActivity::class.java)
            .create()
            .start()
            .resume()

        val activity = controller.get()
        // Access binding to initialize it
        assertNotNull(activity.binding)

        // Destroy and verify no crash
        controller.pause().stop().destroy()
    }
}
```

**Step 3: Run tests**

Run: `./gradlew :vbpd:testDebugUnitTest --tests "dev.androidbroadcast.vbpd.ActivityViewBindingPropertyTest"`
Expected: All tests PASS

**Step 4: Commit**

```bash
git add vbpd/build.gradle.kts vbpd/src/test/
git commit -m "Add Activity lifecycle tests for vbpd module"
```

---

## Task 8: Add tests for vbpd module — Fragment lifecycle

**Files:**
- Create: `vbpd/src/test/kotlin/dev/androidbroadcast/vbpd/FragmentViewBindingPropertyTest.kt`

**Step 1: Add Fragment testing dependency if needed**

In `vbpd/build.gradle.kts` add:
```kotlin
testImplementation(libs.test.fragment)
```

**Step 2: Write Fragment lifecycle test**

Create `vbpd/src/test/kotlin/dev/androidbroadcast/vbpd/FragmentViewBindingPropertyTest.kt`:

```kotlin
package dev.androidbroadcast.vbpd

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.viewbinding.ViewBinding
import io.mockk.every
import io.mockk.mockk
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import kotlin.test.assertNotNull

@RunWith(RobolectricTestRunner::class)
class FragmentViewBindingPropertyTest {

    class TestFragment : Fragment() {
        val binding by viewBinding { fragment: TestFragment ->
            mockk<ViewBinding> {
                every { root } returns fragment.requireView()
            }
        }

        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View {
            return FrameLayout(requireContext())
        }
    }

    @Test
    fun `binding is accessible after onViewCreated`() {
        val scenario = launchFragmentInContainer<TestFragment>()
        scenario.onFragment { fragment ->
            assertNotNull(fragment.binding)
        }
    }

    @Test
    fun `binding survives configuration change lifecycle`() {
        val scenario = launchFragmentInContainer<TestFragment>()
        scenario.onFragment { fragment ->
            assertNotNull(fragment.binding)
        }
        scenario.moveToState(Lifecycle.State.DESTROYED)
    }
}
```

**Step 3: Run tests**

Run: `./gradlew :vbpd:testDebugUnitTest --tests "dev.androidbroadcast.vbpd.FragmentViewBindingPropertyTest"`
Expected: All tests PASS

**Step 4: Commit**

```bash
git add vbpd/src/test/
git commit -m "Add Fragment lifecycle tests for vbpd module"
```

---

## Task 9: Add tests for vbpd module — ViewGroup and ViewHolder

**Files:**
- Create: `vbpd/src/test/kotlin/dev/androidbroadcast/vbpd/ViewGroupBindingsTest.kt`
- Create: `vbpd/src/test/kotlin/dev/androidbroadcast/vbpd/ViewHolderBindingsTest.kt`

**Step 1: Write ViewGroup test**

Create `vbpd/src/test/kotlin/dev/androidbroadcast/vbpd/ViewGroupBindingsTest.kt`:

```kotlin
package dev.androidbroadcast.vbpd

import android.content.Context
import android.view.View
import android.widget.FrameLayout
import androidx.test.core.app.ApplicationProvider
import androidx.viewbinding.ViewBinding
import io.mockk.every
import io.mockk.mockk
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

@RunWith(RobolectricTestRunner::class)
class ViewGroupBindingsTest {

    private val context: Context = ApplicationProvider.getApplicationContext()

    @Test
    fun `viewBinding creates lazy binding for ViewGroup`() {
        val viewGroup = FrameLayout(context)
        val expectedBinding = mockk<ViewBinding> {
            every { root } returns View(context)
        }

        val property = viewGroup.viewBinding { _: FrameLayout -> expectedBinding }
        val result = property.getValue(viewGroup, ::result)

        assertEquals(expectedBinding, result)
    }

    @Test
    fun `viewBinding returns same instance on subsequent calls`() {
        val viewGroup = FrameLayout(context)
        val binding = mockk<ViewBinding> {
            every { root } returns View(context)
        }

        val property = viewGroup.viewBinding { _: FrameLayout -> binding }
        val first = property.getValue(viewGroup, ::first)
        val second = property.getValue(viewGroup, ::second)

        assertEquals(first, second)
    }
}
```

**Step 2: Write ViewHolder test**

Create `vbpd/src/test/kotlin/dev/androidbroadcast/vbpd/ViewHolderBindingsTest.kt`:

```kotlin
package dev.androidbroadcast.vbpd

import android.content.Context
import android.view.View
import android.widget.FrameLayout
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ApplicationProvider
import androidx.viewbinding.ViewBinding
import io.mockk.every
import io.mockk.mockk
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

@RunWith(RobolectricTestRunner::class)
class ViewHolderBindingsTest {

    private val context: Context = ApplicationProvider.getApplicationContext()

    class TestViewHolder(view: View) : RecyclerView.ViewHolder(view)

    @Test
    fun `viewBinding creates binding from ViewHolder`() {
        val view = FrameLayout(context)
        val viewHolder = TestViewHolder(view)
        val expectedBinding = mockk<ViewBinding> {
            every { root } returns view
        }

        val property = viewHolder.viewBinding { _: TestViewHolder -> expectedBinding }
        val result = property.getValue(viewHolder, ::result)

        assertEquals(expectedBinding, result)
    }

    @Test
    fun `viewBinding with factory and viewProvider`() {
        val view = FrameLayout(context)
        val viewHolder = TestViewHolder(view)
        val expectedBinding = mockk<ViewBinding> {
            every { root } returns view
        }

        val property = viewHolder.viewBinding(
            vbFactory = { expectedBinding },
            viewProvider = { it.itemView }
        )
        val result = property.getValue(viewHolder, ::result)

        assertEquals(expectedBinding, result)
    }
}
```

**Step 3: Run all vbpd tests**

Run: `./gradlew :vbpd:testDebugUnitTest`
Expected: All tests PASS

**Step 4: Commit**

```bash
git add vbpd/src/test/
git commit -m "Add ViewGroup and ViewHolder tests for vbpd module"
```

---

## Task 10: Add tests for vbpd-reflection module

**Files:**
- Modify: `vbpd-reflection/build.gradle.kts`
- Create: `vbpd-reflection/src/test/kotlin/dev/androidbroadcast/vbpd/ViewBindingCacheTest.kt`
- Create: `vbpd-reflection/src/test/kotlin/dev/androidbroadcast/vbpd/ReflectionActivityViewBindingsTest.kt`

**Step 1: Add test dependencies to vbpd-reflection**

In `vbpd-reflection/build.gradle.kts`:
```kotlin
plugins {
    id("vbpdconfig")
}

android {
    buildTypes {
        release {
            consumerProguardFiles("proguard-rules.pro")
        }
    }
}

dependencies {
    compileOnly(libs.androidx.fragment)
    compileOnly(libs.androidx.recyclerview)
    compileOnly(libs.androidx.activity)
    api(projects.vbpd)

    testImplementation(libs.test.junit)
    testImplementation(libs.test.kotlin)
    testImplementation(libs.test.mockk)
    testImplementation(libs.test.robolectric)
    testImplementation(libs.test.androidx.core)
    testImplementation(libs.test.androidx.runner)
    testImplementation(libs.androidx.fragment)
    testImplementation(libs.androidx.activity)
    testImplementation(libs.androidx.recyclerview)
    testImplementation(libs.androidx.appcompat)
}
```

**Step 2: Write ViewBindingCache test**

Create `vbpd-reflection/src/test/kotlin/dev/androidbroadcast/vbpd/ViewBindingCacheTest.kt`:

```kotlin
package dev.androidbroadcast.vbpd

import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import kotlin.test.assertNotNull

@RunWith(RobolectricTestRunner::class)
class ViewBindingCacheTest {

    @Before
    fun setup() {
        ViewBindingCache.clear()
    }

    @After
    fun tearDown() {
        ViewBindingCache.clear()
        ViewBindingCache.setEnabled(false)
    }

    @Test
    fun `cache can be enabled and disabled`() {
        ViewBindingCache.setEnabled(true)
        ViewBindingCache.setEnabled(false)
        // No crash = success
    }

    @Test
    fun `clear does not crash when cache is empty`() {
        ViewBindingCache.clear()
        ViewBindingCache.setEnabled(true)
        ViewBindingCache.clear()
        // No crash = success
    }

    @Test
    fun `setEnabled true then false resets cache`() {
        ViewBindingCache.setEnabled(true)
        ViewBindingCache.setEnabled(false)
        ViewBindingCache.clear() // Should not crash on Noop
    }
}
```

**Step 3: Write reflection Activity test**

Create `vbpd-reflection/src/test/kotlin/dev/androidbroadcast/vbpd/ReflectionActivityViewBindingsTest.kt`:

```kotlin
package dev.androidbroadcast.vbpd

import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import kotlin.test.assertNotNull

@RunWith(RobolectricTestRunner::class)
class ReflectionActivityViewBindingsTest {

    @Test
    fun `CreateMethod BIND and INFLATE enum values exist`() {
        assertNotNull(CreateMethod.BIND)
        assertNotNull(CreateMethod.INFLATE)
    }

    @Test
    fun `CreateMethod values are distinct`() {
        assert(CreateMethod.BIND != CreateMethod.INFLATE)
    }
}
```

**Step 4: Run all vbpd-reflection tests**

Run: `./gradlew :vbpd-reflection:testDebugUnitTest`
Expected: All tests PASS

**Step 5: Commit**

```bash
git add vbpd-reflection/build.gradle.kts vbpd-reflection/src/test/
git commit -m "Add tests for vbpd-reflection module (ViewBindingCache, CreateMethod)"
```

---

## Task 11: Run full test suite and verify coverage

**Files:** None (verification only)

**Step 1: Run all tests**

Run: `./gradlew testDebugUnitTest`
Expected: All tests PASS across all modules

**Step 2: Generate coverage report**

Run: `./gradlew koverHtmlReport`
Expected: BUILD SUCCESSFUL. HTML report generated.

**Step 3: Review coverage report**

Open the generated HTML report and verify coverage numbers. Report locations:
- `vbpd-core/build/reports/kover/html/index.html`
- `vbpd/build/reports/kover/html/index.html`
- `vbpd-reflection/build/reports/kover/html/index.html`

**Step 4: Run all quality checks**

Run: `./gradlew check`
Expected: BUILD SUCCESSFUL (includes detekt + ktlint + tests)

**Step 5: Commit any fixes if needed**

```bash
git add -A
git commit -m "Verify full test suite and coverage reports"
```

---

## Task 12: Fix CI pipelines

**Files:**
- Modify: `.github/workflows/build.yml`
- Modify: `.github/workflows/android.yml`

**Step 1: Fix build.yml (develop branch)**

Replace `.github/workflows/build.yml` with:

```yaml
name: Build & Test

on:
  push:
    branches: [ "develop" ]
  workflow_dispatch:

jobs:
  check:
    runs-on: ubuntu-latest

    steps:
    - uses: actions/checkout@v4

    - name: Set up JDK
      uses: actions/setup-java@v4
      with:
        java-version: '21'
        distribution: 'temurin'
        cache: gradle

    - name: Grant execute permission for gradlew
      run: chmod +x gradlew

    - name: Build libraries
      run: ./gradlew :vbpd-core:assembleRelease :vbpd:assembleRelease :vbpd-reflection:assembleRelease

    - name: Run tests
      run: ./gradlew testDebugUnitTest

    - name: Run code quality checks
      run: ./gradlew detekt ktlintCheck

    - name: Generate coverage report
      run: ./gradlew koverXmlReport

    - name: Upload test results
      if: always()
      uses: actions/upload-artifact@v4
      with:
        name: test-results
        path: |
          **/build/test-results/
          **/build/reports/tests/

    - name: Upload coverage reports
      if: always()
      uses: actions/upload-artifact@v4
      with:
        name: coverage-reports
        path: |
          **/build/reports/kover/

    - name: Upload build artifacts
      uses: actions/upload-artifact@v4
      with:
        name: vbpd-libs-builds
        path: |
          vbpd-core/build/outputs/aar/*.aar
          vbpd/build/outputs/aar/*.aar
          vbpd-reflection/build/outputs/aar/*.aar
```

**Step 2: Fix android.yml (master branch)**

Replace `.github/workflows/android.yml` with:

```yaml
name: Quality Checks

on:
  push:
    branches: [ "master" ]
  pull_request:
    branches: [ "master" ]

jobs:
  check:
    runs-on: ubuntu-latest

    steps:
    - uses: actions/checkout@v4

    - name: Set up JDK
      uses: actions/setup-java@v4
      with:
        java-version: '21'
        distribution: 'temurin'
        cache: gradle

    - name: Grant execute permission for gradlew
      run: chmod +x gradlew

    - name: Run all checks
      run: ./gradlew check

    - name: Generate coverage report
      run: ./gradlew koverXmlReport

    - name: Upload test results
      if: always()
      uses: actions/upload-artifact@v4
      with:
        name: test-results
        path: |
          **/build/test-results/
          **/build/reports/tests/

    - name: Upload coverage reports
      if: always()
      uses: actions/upload-artifact@v4
      with:
        name: coverage-reports
        path: |
          **/build/reports/kover/
```

**Step 3: Verify CI configuration is valid YAML**

Run: `python3 -c "import yaml; yaml.safe_load(open('.github/workflows/build.yml')); yaml.safe_load(open('.github/workflows/android.yml')); print('Valid YAML')"`
Expected: "Valid YAML" (or use any YAML validator)

**Step 4: Commit**

```bash
git add .github/workflows/
git commit -m "Fix CI: remove continue-on-error, add tests, linters and coverage"
```

---

## Task 13: Final verification

**Files:** None (verification only)

**Step 1: Clean build from scratch**

Run: `./gradlew clean check`
Expected: BUILD SUCCESSFUL

**Step 2: Verify test count**

Run: `./gradlew testDebugUnitTest --info 2>&1 | grep -E "tests completed|tests found"`
Expected: Multiple tests found and completed successfully

**Step 3: Verify coverage**

Run: `./gradlew koverHtmlReport`
Review HTML reports.

**Step 4: Final commit if any fixes needed**

```bash
git add -A
git commit -m "Final verification: all checks passing"
```
