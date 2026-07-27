package tabaquithemasteroftime.softwarerenderer.utils.multithreading

/**
 * Entity for work execution.
 */
interface WorkExecutor {

    /**
     * Invokes [block] for each index in [0]..[count]-1.
     */
    fun invoke(
        count: Int,
        block: (Int) -> Unit
    )
}