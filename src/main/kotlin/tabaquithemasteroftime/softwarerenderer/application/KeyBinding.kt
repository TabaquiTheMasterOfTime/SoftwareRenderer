package tabaquithemasteroftime.softwarerenderer.application

import tabaquithemasteroftime.softwarerenderer.awtutils.Key

/**
 * Key binding.
 */
data class KeyBinding(
    val key: Key,
    val action: () -> Unit
)

/**
 * Returns a [KeyBinding] with
 * given [Key] and [action].
 */
fun Key.bind(
    action: () -> Unit
) = KeyBinding(this, action)