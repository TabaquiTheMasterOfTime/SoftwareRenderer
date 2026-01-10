package yellowstarsoftware.softwarerenderer.scenerenderer

/**
 * Depth buffer.
 */
interface DepthBuffer {

    /**
     * Clears this buffer.
     */
    fun clear()

    /**
     * Gets value of the buffer at position ([x], [y]).
     */
    operator fun get(
        x: Int,
        y: Int
    ): Float

    /**
     * Sets [value] to the buffer at position ([x], [y]).
     */
    operator fun set(
        x: Int,
        y: Int,
        value: Float
    )

    companion object {

        /**
         * Creates a [DepthBuffer] with given [width] and [height].
         */
        fun of(
            width: Int,
            height: Int
        ): DepthBuffer = DepthBufferImpl(
            width = width,
            values = FloatArray(size = width * height)
        )
    }
}

private class DepthBufferImpl(
    private val width: Int,
    private val values: FloatArray
) : DepthBuffer {

    override fun clear() {
        values.fill(Float.MAX_VALUE)
    }

    override fun get(
        x: Int,
        y: Int
    ): Float {
        return values[y * width + x]
    }

    override fun set(
        x: Int,
        y: Int,
        value: Float
    ) {
        values[y * width + x] = value
    }
}