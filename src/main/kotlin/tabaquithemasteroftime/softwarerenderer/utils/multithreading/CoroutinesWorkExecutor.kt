package tabaquithemasteroftime.softwarerenderer.utils.multithreading

import kotlinx.coroutines.*

/**
 * [WorkExecutor] implemented using coroutines.
 * @param partsCount count of coroutines
 * @param dispatcher coroutine dispatcher
 */
class CoroutinesWorkExecutor(
    private val partsCount: Int,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default
) : WorkExecutor {

    override fun invoke(
        count: Int,
        block: (Int) -> Unit
    ) {
        runBlocking(dispatcher) {
            val partSize = count / partsCount
            repeat(partsCount) { index ->
                launch {
                    val startI = index * partSize
                    val endI = if (index == partsCount - 1) {
                        count - 1
                    } else {
                        startI + partSize
                    }
                    for (i in startI..endI) {
                        block.invoke(i)
                        yield()
                    }
                }
            }
        }
    }
}