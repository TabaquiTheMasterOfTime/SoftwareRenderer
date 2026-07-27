package tabaquithemasteroftime.softwarerenderer.awtutils

import tabaquithemasteroftime.softwarerenderer.utils.Color
import tabaquithemasteroftime.softwarerenderer.utils.asColor
import tabaquithemasteroftime.yellowstar.math.geometry.Vector3D
import tabaquithemasteroftime.yellowstar.math.utils.mapAndCoerce
import java.awt.image.BufferedImage

/**
 * Image.
 */
interface Image {

    /**
     * Width of the instance.
     */
    val width: Int

    /**
     * Height of the instance.
     */
    val height: Int

    /**
     * Gets the color of the pixel at ([i], [j]).
     */
    operator fun get(i: Int, j: Int): Color

    companion object {

        /**
         * Creates an [Image] implemented by given [bufferedImage].
         */
        fun of(
            bufferedImage: BufferedImage
        ): Image = ImageImpl(bufferedImage)
    }
}

private class ImageImpl(
    private val bufferedImage: BufferedImage
) : Image {

    override val width: Int
        get() = bufferedImage.width

    override val height: Int
        get() = bufferedImage.height

    override fun get(
        i: Int,
        j: Int
    ): Color {
        val rgb = bufferedImage.getRGB(i, j)
        val awtColor = java.awt.Color(rgb)
        val r = awtColor.red
        val g = awtColor.green
        val b = awtColor.blue
        return Vector3D(
            mapAndCoerce(r.toFloat(), 0f, 255f, 0f, 1f),
            mapAndCoerce(g.toFloat(), 0f, 255f, 0f, 1f),
            mapAndCoerce(b.toFloat(), 0f, 255f, 0f, 1f)
        ).asColor()
    }
}