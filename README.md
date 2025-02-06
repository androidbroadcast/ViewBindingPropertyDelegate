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
    // recommended
    implementation("dev.androidbroadcast.vbpd:vbpd:2.0.1")
    
    // additional factories that use reflection under hood
    implementation("dev.androidbroadcast.vbpd:vbpd-reflection:2.0.1")
}
```

## Samples

```kotlin
import javax.swing.text.View

class ProfileFragment : Fragment(R.layout.profile) {
   // RECOMMENDED 
   // no reflection API is used under the hood
   private val profileBinding: ProfileBinding by viewBinding(ProfileBinding::bind)

   override fun onViewCreated(view: View) {
       super.onViewCreate(view)
       with(profileBinding) {
          firstName.text = person.name
          lastName.text = person.surname
          email.text = person.email
       }
   }

   override fun onViewDestroyed() {
      super.onViewDestroyed()
      // profileBinding will be cleared after onViewDestroyed()
   }
}
```

## Migration from 1.0

You can use 1.X and 2.X in the same project without replacing code

- Replace packages `com.github.kirich1409.viewbindingpropertydelegate` -> `dev.androidbroadcast.vbpd`
- Replace `onViewDestroyed` with moving code to proper lifecycle callback (Fragment.onViewDestroyed(), Activity.onDestroy(), etc.)

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
