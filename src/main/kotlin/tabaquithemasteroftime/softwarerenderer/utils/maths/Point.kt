package tabaquithemasteroftime.softwarerenderer.utils.maths

import tabaquithemasteroftime.yellowstar.math.geometry.Vector2D

/**
 * Point ([x], [y]).
 */
data class Point(
    val x: Int,
    val y: Int
)

/**
 * Converts [this] [Point] into a [Vector2D].
 */
fun Point.asVector() = Vector2D(x.toFloat(), y.toFloat())