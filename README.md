# ViewBindingPropertyDelegate

Make work with [Android View Binding](https://developer.android.com/topic/libraries/view-binding) simpler

## Add library to a project

```groovy
allprojects {
  repositories {
    ...
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
    private val viewBinding: ProfileBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Use viewBinding
}
```
