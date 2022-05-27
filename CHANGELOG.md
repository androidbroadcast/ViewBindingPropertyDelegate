## 1.5.3
November 19, 2021

- Fix clearing of a Fragment when it is destroyed 

## 1.5.2
November 13, 2021

### New feature "Strict mode"
New library mode to check correctness of ViewBinding usage. Enabled by default.
Call ViewBindingPropertyDelegate.strictMode = false to switch to old behavior

### Added a callback called when a ViewBinding inside ViewBindingProperty is destroyed
Callback is triggered when a ViewBinding in ViewBindingPropertyDelegate is destroyed
Instead of overriding Fragment.onDestroyView(), use

```kotlin
viewBinding(
    ..., 
    onViewDestroyed = { vb: ViewBinding ->
        // reset views inside the ViewBinding 
    }
)
```

### ViewBindingProperty will throw an error if it is created before the host is ready
ViewBindingPropertyDelegate throws an exception if it is used before the host
(Fragment, Activity, etc.) is ready to create a ViewBinding. As an example, accessing
ViewBindingPropertyDelegate in a Fragment, before onViewCreated() has been called, will now throw
an Exception.

### Other
- Fix memory leaks
- Fix bugs

## 1.4.7

- Bugs fixing

## 1.4.6

- Bugs fixing

## 1.4.5

- Update built-in ProGuard rules

## 1.4.4

- Improve checking of a `Fragment`'s `ViewLifecycleOwner`

## 1.4.3

- Update ProGuard rules

## 1.4.2

- Migrate to Maven Central. This is the last release available via JCenter
- Artifacts group and id parameters were changed
- Support of [ViewTreeLifecycleOwner](https://d.android.com/reference/androidx/lifecycle/ViewTreeLifecycleOwner) for `ViewGroup` bindings
- `LifecycleViewBindingProperty` lifecycle management improvements
- Improvements in viewBinding implementation for `DialogFragment`
- Bugfixes

## 1.4.0

- Added support of `RecyclerView.ViewHolder`
- Added support of `ViewGroup`
- Added an option to use the `viewBinding` delegate without specifying rootView.
- Improved speed of `viewBinding` usage with reflection
- More ways of creating `ViewBinding` using `ViewBinding.inflate` instead of `ViewBinding.bind`
- *Breaking changes* `viewBinding()` in Activities uses `ViewBinding.bind` instead of `ViewBinding.inflate`. Use `viewBinding(CreateMethod.INFLATE)` to switch to old behavior 
- Split implementation of lazy `viewBinding` delegate and implementation with lifecycle
- Minor improvements and bugfixes

## 1.3.1

- Bug fixes

## 1.3.0

- Added support of creating `ViewBinding` using `ViewBinding.inflate(LayoutInflater)`
- Added an option to create `ViewBindingProperty` using a `Class<ViewBinding>` instance
- Upgrade view binding library to 4.1.0
- Improved performance: removed the check working on the main thread within `ViewBindingProperty`
- Update the sample

## 1.2.2

- Bug fixing

## 1.2.1:

- Fix bugs

## 1.2.0:

- Add an artifact `vbpd-noreflection` without reflection when creating a `ViewBindingProperty`
- Fix a bug in Fragment

## 1.1.0:

- Simplify API

## 1.0.0:

- Add Android 4.0+ support
- Support Android View Binding inside `Fragment`, `ComponentActivity` and `DialogFragment`
