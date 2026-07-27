package tabaquithemasteroftime.softwarerenderer.scenerenderer

import tabaquithemasteroftime.softwarerenderer.utils.maths.Point
import tabaquithemasteroftime.softwarerenderer.utils.maths.asVector
import tabaquithemasteroftime.softwarerenderer.utils.multithreading.WorkExecutor
import tabaquithemasteroftime.yellowstar.math.geometry.Vector2D
import tabaquithemasteroftime.yellowstar.math.geometry.algorithms.linesIntersectionPoint
import tabaquithemasteroftime.yellowstar.math.geometry.objects.Line2D
import kotlin.math.max
import kotlin.math.min

/**
 * Executes [block] for every pixel of
 * the triangle defined by [points] using [workExecutor].
 * [width] and [height] are used to prevent out of bounds thing.
 */
internal inline fun forEveryTrianglePixelV2(
    points: List<Point>,
    width: Int,
    height: Int,
    workExecutor: WorkExecutor,
    crossinline block: (
        x: Int,
        y: Int
    ) -> Unit
) {
    if (points.size < 3) return
    val (q0, q1, q2) = points.sortedBy { it.y }

    // the triangle is actually a line, don't draw it
    // this excludes case where the common line is horizontal
    // (y[i] = y[j] for every i, j => minY == maxY )
    if (q0.y == q1.y && q0.y == q2.y) return

    val commonLine = makeLine(
        q0.asVector(),
        q2.asVector()
    )

    // make sure the first line is not horizontal
    if (q0.y != q1.y) {
        forEveryTrianglePixelPart(
            y0 = q0.y,
            y1 = q1.y,
            firstLine = makeLine(
                a = q0.asVector(),
                b = q1.asVector()
            ),
            secondLine = commonLine,
            width = width,
            height = height,
            workExecutor = workExecutor,
            block = block
        )
    }

    // make sure the second line is not horizontal
    if (q1.y != q2.y) {
        forEveryTrianglePixelPart(
            y0 = q1.y,
            y1 = q2.y,
            firstLine = makeLine(
                a = q1.asVector(),
                b = q2.asVector()
            ),
            secondLine = commonLine,
            width = width,
            height = height,
            workExecutor = workExecutor,
            block = block
        )
    }
}

/**
 * [firstLine] and [secondLine] must not be horizontal!
 */
private inline fun forEveryTrianglePixelPart(
    y0: Int,
    y1: Int,
    firstLine: Line2D,
    secondLine: Line2D,
    width: Int,
    height: Int,
    workExecutor: WorkExecutor,
    crossinline block: (x: Int, y: Int) -> Unit
) {
    val fromY = min(y0, y1).coerceIn(0, height - 1)
    val toY = max(y0, y1).coerceIn(0, height - 1)
    val count = toY - fromY + 1
    workExecutor.invoke(count = count) { index ->
        val y = fromY + index
        val horizontal = Line2D(
            point = Vector2D(0f, y.toFloat()),
            direction = Vector2D.I
        )
        val (x0, _) = linesIntersectionPoint(firstLine, horizontal)
        val (x1, _) = linesIntersectionPoint(secondLine, horizontal)
        val fromX = min(x0, x1).toInt().coerceIn(0, width - 1)
        val toX = max(x0, x1).toInt().coerceIn(0, width - 1)
        for (x in fromX..toX) {
            block.invoke(x, y)
        }
    }
}

private fun makeLine(
    a: Vector2D,
    b: Vector2D
) = Line2D(
    point = a,
    direction = (b - a).normalized
)