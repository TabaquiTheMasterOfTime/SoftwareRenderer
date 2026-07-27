package tabaquithemasteroftime.softwarerenderer.utils.maths

import tabaquithemasteroftime.yellowstar.math.geometry.BarycentricCoordinates
import tabaquithemasteroftime.yellowstar.math.geometry.Vector2D
import tabaquithemasteroftime.yellowstar.math.geometry.Vector3D

/**
 * Entity for barycentric coordinates calculation.
 */
class BarycentricCache2D private constructor(
    private val p0: Vector2D,
    private val d1: Vector2D,
    private val d2: Vector2D,
    private val yScale: Float,
    private val zScale: Float
) {

    /**
     * Obtains barycentric coordinated of given [point].
     */
    fun toBarycentric(
        point: Vector2D
    ): BarycentricCoordinates {
        val v = point - p0
        val y = (v dot d2) * yScale
        val z = (v dot d1) * zScale
        val x = 1f - z - y
        return BarycentricCoordinates(
            x = x,
            y = y,
            z = z
        )
    }

    companion object {

        /**
         * Creates a [BarycentricCache2D] with
         * given basis [p0], [p1], [p2].
         */
        fun of(
            p0: Vector2D,
            p1: Vector2D,
            p2: Vector2D
        ): BarycentricCache2D {
            val e1 = p1 - p0
            val e2 = p2 - p0
            val d1 = e1.orthogonal
            val d2 = e2.orthogonal
            val yScale = 1f / (e1 dot d2)
            val zScale = 1f / (e2 dot d1)
            return BarycentricCache2D(
                p0 = p0,
                d1 = d1,
                d2 = d2,
                yScale = yScale,
                zScale = zScale
            )
        }
    }
}

/**
 * Entity for barycentric coordinates calculation.
 */
class BarycentricCache3D private constructor(
    private val p0: Vector3D,
    private val d1: Vector3D,
    private val d2: Vector3D,
    private val yScale: Float,
    private val zScale: Float
) {

    /**
     * Obtains barycentric coordinated of given [point].
     */
    fun toBarycentric(
        point: Vector3D
    ): BarycentricCoordinates {
        val v = point - p0
        val y = (v dot d2) * yScale
        val z = (v dot d1) * zScale
        val x = 1f - z - y
        return BarycentricCoordinates(
            x = x,
            y = y,
            z = z
        )
    }

    companion object {

        /**
         * Creates a [BarycentricCache3D] with
         * given basis [p0], [p1], [p2].
         */
        fun of(
            normal: Vector3D,
            p0: Vector3D,
            p1: Vector3D,
            p2: Vector3D
        ): BarycentricCache3D {
            val e1 = p1 - p0
            val e2 = p2 - p0
            val d1 = e1 cross normal
            val d2 = e2 cross normal
            val yScale = 1f / (e1 dot d2)
            val zScale = 1f / (e2 dot d1)
            return BarycentricCache3D(
                p0 = p0,
                d1 = d1,
                d2 = d2,
                yScale = yScale,
                zScale = zScale
            )
        }
    }
}