package tabaquithemasteroftime.softwarerenderer.scenerenderer

import tabaquithemasteroftime.softwarerenderer.utils.maths.BarycentricCache2D
import tabaquithemasteroftime.softwarerenderer.utils.maths.Point
import tabaquithemasteroftime.softwarerenderer.utils.maths.asVector
import tabaquithemasteroftime.yellowstar.math.geometry.Vector2D
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

/**
 *
 * Executes [block] for every pixel of
 * the triangle defined by [points].
 * [width] and [height] are used to prevent out of bounds thing.
 *
 * My first attempt to implement triangle rasterisation algorithm.
 * The algorithm works perfectly but very slow.
 *
 * The code is unused but stays in the project
 * so that you can use it and see how slow it is.
 *
 * @see forEveryTrianglePixelV2
 */
inline fun forEveryTrianglePixel(
    points: List<Point>,
    width: Int,
    height: Int,
    block: (
        x: Int,
        y: Int
    ) -> Unit
) {
    if (points.size < 3) return
    var minX = Int.MAX_VALUE
    var minY = Int.MAX_VALUE
    var maxX = 0
    var maxY = 0
    points.forEach { (x, y) ->
        minX = min(minX, x).coerceIn(0, width - 1)
        minY = min(minY, y).coerceIn(0, height - 1)
        maxX = max(maxX, x).coerceIn(0, width - 1)
        maxY = max(maxY, y).coerceIn(0, height - 1)
    }

    val (p0, p1, p2) = points.map(Point::asVector)
    val barycentricCache = BarycentricCache2D.of(p0, p1, p2)

    for (x in minX..maxX) {
        for (y in minY..maxY) {
            val point = Vector2D(x.toFloat(), y.toFloat())
            val bc = barycentricCache.toBarycentric(point)
            if (
                bc.x >= 0f &&
                bc.y >= 0f &&
                bc.z >= 0f &&
                abs(bc.x + bc.y + bc.z - 1f) < 0.001f
            ) {
                block.invoke(x, y)
            }
        }
    }
}