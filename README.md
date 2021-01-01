# ViewBindingPropertyDelegate

Make work with [Android View Binding](https://developer.android.com/topic/libraries/view-binding) simpler

## IMPORTANT: Enable ViewBinding before use the library
Every Gradle module of your project need to enable ViewBinding. How to do that you can find in the [official guide](https://d.android.com/topic/libraries/view-binding)

## Add library to a project

```groovy
allprojects {
  repositories {
    jcenter()

    // or for the ASAP availability
    maven { url = 'https://dl.bintray.com/kirich1409/maven' }
  }
}

dependencies {
    implementation 'com.kirich1409.viewbindingpropertydelegate:viewbindingpropertydelegate:1.4.0'
    
    // To use only without reflection variants of viewBinding
    implementation 'com.kirich1409.viewbindingpropertydelegate:vbpd-noreflection:1.4.0'
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

It's very important that for some cases in `DialogFragment` you need to use `dialogViewBinding`
instead of `viewBinding`

```kotlin
class ProfileDialogFragment : DialogFragment() {

    // Using reflection API under the hood
    private val viewBinding: ProfileBinding by dialogViewBinding(R.id.container)

    // Without reflection
    private val viewBinding by dialogViewBinding(ProfileBinding::bind, R.id.container)

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setView(R.layout.profile)
            .create()
    }
}
```

```kotlin
class ProfileDialogFragment : DialogFragment() {

    // Using reflection API under the hood
    private val viewBinding: ProfileBinding by viewBinding()

    // Without reflection
    private val viewBinding by viewBinding(ProfileBinding::bind)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.profile, container, false)
    }
}
```
