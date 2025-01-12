[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.kirich1409/viewbindingpropertydelegate/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.github.kirich1409/viewbindingpropertydelegate)

# ViewBindingPropertyDelegate

Make work with [Android View Binding](https://d.android.com/topic/libraries/view-binding) simpler. The library:
- manages ViewBinding lifecycle and clears the reference to it to prevent memory leaks
- eliminates the need to keep nullable references to Views or ViewBindings
- creates ViewBinding lazily

The library comes in two variants: with and without reflection. The artifacts are respectively: `vbpd` and `vbpd-reflection`. **Prefer to use the one without reflection for better performance**

## IMPORTANT: Enable ViewBinding before using the library
Each Gradle module in your project where you need to use ViewBinding must be properly configured. Refer to the [official guide](https://d.android.com/topic/libraries/view-binding) on how to do that 

## Add the library to a project

```kotlin
allprojects {
  repositories {
    mavenCentral()
  }
}

dependencies {
    val vbpdVersion = "2.0.0-beta01"
    // recommended
    implementation("dev.androidbroadcast.vbpd:vbpd:$vbpdVersion")
    
    // additional factories that use reflection under hood
    implementation("dev.androidbroadcast.vbpd:vbpd-reflection:$vbpdVersion")
}
```

## Samples

```kotlin
class ProfileFragment : Fragment(R.layout.profile) { 
   // RECOMMENDED 
   // no reflection API is used under the hood
   private val profileBinding: ProfileBinding by viewBinding(ProfileBinding::bind)
}
```

# License

   Copyright 2020-2025 Kirill Rozov

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
