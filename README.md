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

## Samples

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

```kotlin
class ProfileDialogFragment : DialogFragment() {

    private val viewBindingUsingReflection: ProfileBinding by dialogViewBinding(R.id.container)

    private val viewBindingWithoutReflection by dialogViewBinding { fragment ->
        ProfileBinding.bind(fragment.dialog!!.window!!.decorView.findViewById(R.id.container))
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setView(R.layout.profile)
            .create()
    }
}
```

```kotlin
class ProfileDialogFragment : DialogFragment() {

    private val viewBindingUsingReflection: ProfileBinding by dialogViewBinding(R.id.container)

    private val viewBindingWithoutReflection by viewBinding { fragment ->
        ProfileBinding.bind(fragment.requireView())
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.profile, container, false)
    }
}
```
