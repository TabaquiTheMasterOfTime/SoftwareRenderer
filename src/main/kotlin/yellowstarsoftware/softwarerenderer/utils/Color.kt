package yellowstarsoftware.softwarerenderer.utils

import yellowstarsoftware.yellowstar.math.geometry.Vector3D

/**
 * Color.
 */
@JvmInline
value class Color(val rgb: Vector3D)

/**
 * Red component of this [Color].
 */
inline val Color.red get() = rgb.x

/**
 * Green component of this [Color].
 */
inline val Color.green get() = rgb.y

/**
 * Blue component of this [Color].
 */
inline val Color.blue get() = rgb.z

/**
 * Obtains a [Color] from [this] [Vector3D].
 */
fun Vector3D.asColor() = Color(this)