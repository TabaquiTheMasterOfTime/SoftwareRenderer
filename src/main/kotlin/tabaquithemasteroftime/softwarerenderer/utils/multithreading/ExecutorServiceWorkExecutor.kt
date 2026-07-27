package tabaquithemasteroftime.softwarerenderer.utils.multithreading

import java.util.concurrent.ExecutorService

/**
 * [WorkExecutor] implemented using [ExecutorService].
 * @param partsCount the count of parts into which the work will be divided
 * @param executor [ExecutorService]
 */
class ExecutorServiceWorkExecutor(
    private val partsCount: Int,
    private val executor: ExecutorService
) : WorkExecutor {

    override fun invoke(
        count: Int,
        block: (Int) -> Unit
    ) {
        val partSize = count / partsCount
        val futures = (0 until partsCount).map { index ->
            executor.submit {
                val startI = index * partSize
                val endI = if (index == partsCount - 1) {
                    count - 1
                } else {
                    startI + partSize
                }
                for (i in startI..endI) {
                    block.invoke(i)
                }
            }
        }
        futures.forEach { it.get() }
    }
}