# ViewBindingPropertyDelegate

Make work with [Android View Binding](https://developer.android.com/topic/libraries/view-binding) simpler

## Add library to a project

```groovy
allprojects {
  repositories {
    jcenter()
  }
}

dependencies {
    implementation 'com.kirich1409.viewbindingpropertydelegate:viewbindingpropertydelegate:1.0.0'
}
```

## Samples

```kotlin
class ProfileFragment : Fragment(R.layout.profile) {

    private val viewBinding: ProfileBinding by viewBinding()

    private val viewBindingWithoutReflection by viewBinding(ProfileBinding::bind)
}
```

```kotlin
class ProfileActivity : AppCompatActivity(R.layout.profile) {

    private val viewBinding: ProfileBinding by viewBinding(R.id.container)
}
```

```kotlin
class ProfileDialogFragment : DialogFragment() {

    private val viewBinding: ProfileBinding by dialogViewBinding(R.id.container)

    // Creating via default way will work too for that case
    // private val viewBinding: ProfileBinding by viewBinding()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setView(R.layout.profile)
            .create()
    }
}
```

```kotlin
class ProfileDialogFragment : DialogFragment() {

    private val viewBinding: ProfileBinding by dialogViewBinding(R.id.container)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.profile, container, false)
    }
}
```
