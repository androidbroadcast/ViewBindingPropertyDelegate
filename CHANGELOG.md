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
