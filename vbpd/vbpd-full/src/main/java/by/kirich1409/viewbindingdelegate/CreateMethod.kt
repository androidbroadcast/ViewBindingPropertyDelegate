package by.kirich1409.viewbindingdelegate

/**
 * Method that will be used to create [ViewBinding].
 */
enum class CreateMethod {
    /**
     * Use `ViewBinding.bind(View)`
     */
    BIND,

    /**
     * Use `ViewBinding.inflate(LayoutInflater)`
     */
    INFLATE
}