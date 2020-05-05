# ViewBindingPropertyDelegate

Make work with [Android View Binding](https://developer.android.com/topic/libraries/view-binding) simpler

## Add library to a project

```groovy
allprojects {
  repositories {
    maven { url 'https://jitpack.io' }
  }
}

dependencies {
    implementation 'com.github.kirich1409:ViewBindingPropertyDelegate:0.1'
}
```

## Sample 

```kotlin
class ProfileFragment : Fragment(R.layout.profile) {

    val viewBindingUsingReflection: ProfileBinding by viewBinding()

    val viewBinding by viewBinding(ProfileBinding::bind)
}
```

```kotlin
class ProfileActivity : AppCompatActivity(R.layout.profile) {

    val viewBindingUsingReflection: ProfileBinding by viewBinding(R.id.container)

    val viewBinding by viewBinding(R.id.container, ProfileBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Don't need to call setContentView(viewBinding.root)
    }
}
```
