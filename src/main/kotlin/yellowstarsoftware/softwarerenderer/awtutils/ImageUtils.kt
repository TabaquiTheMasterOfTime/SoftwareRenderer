package yellowstarsoftware.softwarerenderer.awtutils

import java.awt.image.BufferedImage
import javax.imageio.ImageIO

/**
 * Loads an [Image] from resources by given [name].
 */
fun loadImage(name: String): Image {
    return Resources.getResource("/$name").use { stream ->
        val original = runCatching {
            ImageIO.read(stream)
        }.getOrElse { original ->
            throw IllegalArgumentException("Can't load image <$name>").apply {
                addSuppressed(original)
            }
        }
        val target = BufferedImage(
            original.width,
            original.height,
            BufferedImage.TYPE_4BYTE_ABGR
        )
        target.createGraphics().also { graphics ->
            graphics.drawImage(original, 0, 0, null)
            graphics.dispose()
        }
        Image.of(target)
    }
}

private object Resources {

    fun getResource(
        name: String
    ) = Resources
        .javaClass
        .getResourceAsStream(name) ?: throw IllegalArgumentException("Failed to load resource <$name>")
}