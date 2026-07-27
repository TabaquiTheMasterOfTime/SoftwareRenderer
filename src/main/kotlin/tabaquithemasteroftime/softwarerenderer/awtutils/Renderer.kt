package tabaquithemasteroftime.softwarerenderer.awtutils

import java.awt.Graphics2D
import javax.swing.JComponent

/**
 * Renderer callback.
 */
fun interface Renderer {

    /**
     * Renders a frame with given [component] and [graphics].
     */
    fun render(
        component: JComponent,
        graphics: Graphics2D
    )
}