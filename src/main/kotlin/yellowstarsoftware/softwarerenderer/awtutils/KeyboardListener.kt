package yellowstarsoftware.softwarerenderer.awtutils

/**
 * Keyboard events listener.
 */
interface KeyboardListener {

    /**
     * Called when [key] was pressed.
     */
    fun onKeyDown(key: Key)

    /**
     * Called when [key] was released.
     */
    fun onKeyUp(key: Key)
}