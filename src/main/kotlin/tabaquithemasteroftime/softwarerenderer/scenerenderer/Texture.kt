package tabaquithemasteroftime.softwarerenderer.scenerenderer

import tabaquithemasteroftime.softwarerenderer.awtutils.Image
import tabaquithemasteroftime.softwarerenderer.utils.Color
import tabaquithemasteroftime.yellowstar.math.geometry.Vector2D

/**
 * Texture.
 */
interface Texture {

    /**
     * Width of the texture.
     */
    val width: Int

    /**
     * Height of the texture.
     */
    val height: Int

    /**
     * Gets pixel ([x], [y]) of the texture.
     */
    operator fun get(
        x: Int,
        y: Int
    ): Color

    companion object {

        /**
         * Creates a [Texture] implemented by [Image].
         */
        fun of(image: Image): Texture = ImageTexture(image)
    }
}

/**
 * Gets [Color] of pixel of [this] texture at given [coordinates].
 */
operator fun Texture.get(
    coordinates: Vector2D
): Color {
    val i = (coordinates.x * width.toFloat()).toInt()
    val j = (coordinates.y * height.toFloat()).toInt()
    return this[i, j]
}

private class ImageTexture(
    private val image: Image
) : Texture {

    override val width: Int
        get() = image.width

    override val height: Int
        get() = image.height

    override fun get(
        x: Int,
        y: Int
    ) = image[x.mod(width), y.mod(height)]
}