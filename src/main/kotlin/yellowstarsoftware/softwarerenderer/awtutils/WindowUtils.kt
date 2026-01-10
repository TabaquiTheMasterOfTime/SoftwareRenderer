package yellowstarsoftware.softwarerenderer.awtutils

import yellowstarsoftware.softwarerenderer.awtutils.internal.ListenersHelper
import java.awt.EventQueue
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.event.KeyAdapter
import java.awt.event.KeyEvent
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent
import javax.swing.JComponent
import javax.swing.JFrame
import javax.swing.Timer

/**
 * Shows a window.
 * @param width width of the window
 * @param height height of the window
 * @param repaintDelay delay before repaint
 * @param renderer renderer
 * @param keyboardListener keyboard event listener
 */
fun showWindow(
    width: Int,
    height: Int,
    repaintDelay: Int,
    renderer: Renderer,
    keyboardListener: KeyboardListener
) {
    EventQueue.invokeLater {
        val frame = JFrame()
        frame.setSize(width, height)
        frame.isResizable = false

        val canvasComponent = object : JComponent() {
            override fun paint(g: Graphics?) {
                super.paint(g) // TODO: do we really need this call?
                val graphics = g as? Graphics2D ?: error("graphics is null or not a Graphics2D")
                renderer.render(this, graphics)
            }
        }
        frame.add(canvasComponent)

        canvasComponent.isFocusable = true
        canvasComponent.addKeyboardListener(keyboardListener)

        val timer = Timer(repaintDelay) {
            frame.repaint()
        }

        frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        val windowListener = object : WindowAdapter() {
            override fun windowClosing(e: WindowEvent?) {
                super.windowClosing(e)
                timer.stop()
            }
        }
        frame.addWindowListener(windowListener)
        timer.start()
        frame.isVisible = true
    }
}

private fun JComponent.addKeyboardListener(
    keyboardListener: KeyboardListener
) {
    val helper = ListenersHelper.of()
    addKeyListener(
        object : KeyAdapter() {
            override fun keyPressed(e: KeyEvent) {
                val key = helper.mapKey(e.keyCode)
                keyboardListener.onKeyDown(key)
            }

            override fun keyReleased(e: KeyEvent) {
                val key = helper.mapKey(e.keyCode)
                keyboardListener.onKeyUp(key)
            }
        }
    )
}