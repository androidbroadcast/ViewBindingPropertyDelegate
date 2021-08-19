## 1.5.0
19 Aug 2021

**New feature "Strict mode"**
New library mode to check correct usage of `ViewBinding`. Enabled by default. To return previous 
  behaviour call `ViewBindingPropertyDelegate.strcitMode = false`
  
- Callback when a `ViewBinding` in `ViewBindingPropertyDelegate` will be destroyed
Instead of overriding `Fragment.onDestroyView()` use
```kotlin  
viewBinding(
    ..., 
    onViewDestroyed = { vb: ViewBinding ->
        // reset views inside the ViewBinding 
    }
)
``` 
- `ViewBindingPropertyDelegate` throws an exception when it will be used before host 
  (`Fragment`, `Activity`, etc.) be ready to create a `ViewBinding`. As an example, access to 
  `ViewBindingPropertyDelegate` in a `Fragment` before `onViewCreated()` will be called now throw 
  an zException`

## 1.4.7

- Bugs fixing

## 1.4.6

- Bugs fixing

## 1.4.5

- Update builtin ProGuard rules

## 1.4.4

- Improve checking of `Fragment`'s `ViewLifecycleOwner`

## 1.4.3

- Update ProGuard rules

## 1.4.2

- Migrate to Maven Central. This is the latest releases available via JCenter.
- Artifacts group and id were changed
- Support of [ViewTreeLifecycleOwner](https://d.android.com/reference/androidx/lifecycle/ViewTreeLifecycleOwner) for `ViewGroup` bindings
- `LifecycleViewBindingProperty` lifecycle management improvements
- Improvements in viewBinding work for `DialogFragment`
- Bugfixes

## 1.4.0

- Added support of `RecyclerView.ViewHolder`
- Added support of `ViewGroup`
- Added possibility to use the `viewBinding` delegate without specifying rootView.
- Improved speed of usage `viewBinding` with reflection
- More ways of creating `ViewBinding` using `ViewBinding.inflate` instead of `ViewBinding.bind`
- *Breaking changes* `viewBinding()` in Activities use `ViewBinding.bind` instead of `ViewBinding.inflate`. To return previous behaviour use `viewBinding(CreateMethod.INFLATE)`
- Split implementation of lazy `viewBinding` delegate and implementation with lifecycle
- Minor improvements and bugfixes

## 1.3.1

- Bug fixes

## 1.3.0

- Added support of create `ViewBinding` using `ViewBinding.inflate(LayoutInflater)`
- Added possibility to create `ViewBindingProperty` using `Class<ViewBinding>` instance
- Upgrade view binding library to 4.1.0
- Improved performance: removed check working from the main thread with `ViewBindingProperty`
- Update sample

## 1.2.2

- Bug fixing

## 1.2.1:

- Fix bugs

## 1.2.0:

- Add artifact `vbpd-noreflection` without reflection when create a `ViewBindingProperty`
- Fix bug in Fragment

## 1.1.0:

- Simplify API

## 1.0.0:

- Add support Android 4.0+
- Support of Android View Binding inside `Fragment`, `ComponentActivity` and `DialogFragment`
