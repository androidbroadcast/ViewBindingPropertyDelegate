# ViewBindingPropertyDelegate
[![](https://jitpack.io/v/kirich1409/ViewBindingPropertyDelegate.svg)](https://jitpack.io/#kirich1409/ViewBindingPropertyDelegate)

Make work with [Android View Binding](https://developer.android.com/topic/libraries/view-binding) simpler

## Add library to a project

```groovy
allprojects {
  repositories {
    maven { url 'https://jitpack.io' }
  }
}

dependencies {
    implementation 'com.github.kirich1409:ViewBindingPropertyDelegate:0.3'
}
```

## Sample 

```kotlin
class ProfileFragment : Fragment(R.layout.profile) {

    private val viewBindingUsingReflection: ProfileBinding by viewBinding()

    private val viewBindingWithoutReflection by viewBinding { fragment ->
        ProfileBinding.bind(fragment.requireView())
    }
}
```

```kotlin
class ProfileActivity : AppCompatActivity(R.layout.profile) {

    private val viewBindingUsingReflection: ProfileBinding by viewBinding(R.id.container)

    private val viewBindingWithoutReflection by viewBinding { activity ->
        ProfileBinding.bind(activity.findViewById(R.id.container))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Don't need to call setContentView(viewBinding.root)
    }
}
```
