[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.kirich1409/viewbindingpropertydelegate/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.github.kirich1409/viewbindingpropertydelegate)

# ViewBindingPropertyDelegate

Make work with [Android View Binding](https://d.android.com/topic/libraries/view-binding) simpler. The library do:
- managing ViewBinding lifecycle and clear reference to it to prevent memory leaks
- you don't need to keep nullable reference to Views or ViewBindings
- create ViewBinding lazy

The library has two variants: reflection and without it. They were divided in separate artifacts: `viewbindingpropertydelegate` and `viewbindingpropertydelegate-noreflection`. **Prefer to use variant without reflection for better performance**

## IMPORTANT: Enable ViewBinding before use the library
Every Gradle module of your project where you need use ViewBinding must be configured properly. How to do that you can find in the [official guide](https://d.android.com/topic/libraries/view-binding)

## Add library to a project

```groovy
allprojects {
  repositories {
    mavenCentral()
  }
}

dependencies {
    implementation 'com.github.kirich1409:viewbindingpropertydelegate:1.5.3'
    
    // To use only without reflection variants of viewBinding
    implementation 'com.github.kirich1409:viewbindingpropertydelegate-noreflection:1.5.3'
}
```

## Samples

```kotlin
class ProfileFragment : Fragment(R.layout.profile) {

    // Using reflection API under the hood and ViewBinding.bind
    private val viewBinding: ProfileBinding by viewBinding()

    // Using reflection API under the hood and ViewBinding.inflate
    private val viewBinding: ProfileBinding by viewBinding(createMethod = CreateMethod.INFLATE)

    // Without reflection
    private val viewBinding by viewBinding(ProfileBinding::bind)
}
```

```kotlin
class ProfileActivity : AppCompatActivity(R.layout.profile) {

    // Using reflection API under the hood
    private val viewBinding: ProfileBinding by viewBinding(R.id.container)

    // Without reflection
    private val viewBinding by viewBinding(ProfileBinding::bind, R.id.container)
}
```

# License

   Copyright 2020-2021 Kirill Rozov

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
