package tabaquithemasteroftime.softwarerenderer.application

import tabaquithemasteroftime.softwarerenderer.awtutils.*

/**
 * Runs [this] [Application].
 * @param windowWidth window width
 * @param windowHeight window height
 */
fun Application.run(
    windowWidth: Int,
    windowHeight: Int,
) {
    val pressedKeys = mutableSetOf<Key>()
    val reactiveKeyBindings = reactiveKeyBindings
    val continuousKeyBindings = continuousKeyBindings

    val renderer = Renderer { component, graphics ->
        continuousKeyBindings.forEach { binding ->
            if (binding.key in pressedKeys) {
                binding.action.invoke()
            }
        }
        onFrame(
            component = component,
            graphics = graphics
        )
    }

    val keyboardListener = object : KeyboardListener {

        override fun onKeyDown(key: Key) {
            pressedKeys += key
            processReactiveKeys(key)
        }

        override fun onKeyUp(key: Key) {
            pressedKeys -= key
        }

        private fun processReactiveKeys(key: Key) {
            reactiveKeyBindings.forEach { binding ->
                if (binding.key == key) {
                    binding.action.invoke()
                }
            }
        }
    }

    showWindow(
        width = windowWidth,
        height = windowHeight,
        repaintDelay = 1,
        renderer = renderer,
        keyboardListener = keyboardListener
    )
}