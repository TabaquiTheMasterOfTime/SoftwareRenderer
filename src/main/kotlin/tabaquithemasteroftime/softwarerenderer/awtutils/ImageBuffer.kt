package tabaquithemasteroftime.softwarerenderer.awtutils

import tabaquithemasteroftime.softwarerenderer.awtutils.ImageBuffer.Companion.BYTES_PER_PIXEL
import tabaquithemasteroftime.softwarerenderer.utils.Color
import tabaquithemasteroftime.softwarerenderer.utils.blue
import tabaquithemasteroftime.softwarerenderer.utils.green
import tabaquithemasteroftime.softwarerenderer.utils.red
import tabaquithemasteroftime.yellowstar.math.utils.mapAndCoerce
import java.awt.Graphics
import java.awt.image.BufferedImage
import javax.swing.JComponent

/**
 * Image buffer.
 */
interface ImageBuffer {

    /**
     * Width of the instance.
     */
    val width: Int

    /**
     * Height of the instance.
     */
    val height: Int

    /**
     * Clears this buffer with black color.
     */
    fun clear()

    /**
     * Sets pixel [color] of this image
     * at given position ([x], [y]).
     */
    operator fun set(
        x: Int,
        y: Int,
        color: Color
    )

    /**
     * Draws this buffer using [component] and [graphics].
     */
    fun draw(
        component: JComponent,
        graphics: Graphics
    )

    companion object {

        /**
         * Count of bytes per pixel.
         */
        const val BYTES_PER_PIXEL = 3

        /**
         * Creates an [ImageBuffer] with given [width] and [height].
         */
        fun of(
            width: Int,
            height: Int
        ): ImageBuffer = ImageBufferImpl.of(
            width = width,
            height = height
        )
    }
}

private class ImageBufferImpl(
    private val pixels: IntArray,
    private val buffer: BufferedImage
) : ImageBuffer {

    override val width: Int
        get() = buffer.width

    override val height: Int
        get() = buffer.height

    override fun clear() {
        this.pixels.fill(0)
    }

    override operator fun set(
        x: Int,
        y: Int,
        color: Color
    ) {
        val r = mapAndCoerce(color.red, 0f, 1f, 0, 255)
        val g = mapAndCoerce(color.green, 0f, 1f, 0, 255)
        val b = mapAndCoerce(color.blue, 0f, 1f, 0, 255)
        val baseIndex = (y * width + x) * ImageBuffer.BYTES_PER_PIXEL
        this.pixels[baseIndex] = r
        this.pixels[baseIndex + 1] = g
        this.pixels[baseIndex + 2] = b
    }

    override fun draw(
        component: JComponent,
        graphics: Graphics
    ) {
        buffer.raster.setPixels(0, 0, width, height, pixels)
        graphics.drawImage(buffer, 0, 0, component.width, component.height, null)
    }

    companion object {

        fun of(
            width: Int,
            height: Int
        ) = ImageBufferImpl(
            pixels = IntArray(size = width * height * BYTES_PER_PIXEL),
            buffer = BufferedImage(
                width,
                height,
                BufferedImage.TYPE_INT_RGB
            )
        )
    }
}