package yellowstarsoftware.softwarerenderer.utils.maths

import yellowstarsoftware.yellowstar.math.geometry.Vector3D
import yellowstarsoftware.yellowstar.math.geometry.algorithms.getLinePlaneIntersectionParameter
import yellowstarsoftware.yellowstar.math.geometry.objects.Plane
import yellowstarsoftware.yellowstar.math.geometry.objects.Segment3D
import yellowstarsoftware.yellowstar.math.geometry.objects.signedDistanceTo
import yellowstarsoftware.yellowstar.math.geometry.times
import yellowstarsoftware.yellowstar.math.geometry.toBarycentric
import yellowstarsoftware.yellowstar.math.geometry.toCartesian

/**
 * Polygon.
 * @property vertices vertices of the instance
 * @property normal normal of the instance of unit size
 * @param info additional info (e.g. texture)
 */
class Polygon<Info : Any>(
    val vertices: List<Vertex>,
    val normal: Vector3D,
    val info: Info
) {

    /**
     * Centroid of the [Polygon].
     */
    val centroid = vertices.fold(Vector3D.ZERO) { sum, vertex ->
        sum + vertex.point
    } / vertices.size.toFloat()
}

/**
 * Count of vertices of the instance.
 */
val Polygon<*>.vertexCount get() = vertices.size

/**
 * Plane of the polygon.
 */
val Polygon<*>.plane
    get() = Plane(
        point = vertices[0].point,
        normal = normal
    )

/**
 * Gets a [Vertex] of the instance at given [index].
 */
operator fun Polygon<*>.get(index: Int) = this.vertices[index]

/**
 * Splits [this] instance into [List] of triangles.
 */
fun <Info : Any> Polygon<Info>.triangulate(): List<Polygon<Info>> {
    if (vertexCount < 3) return emptyList()
    if (vertexCount == 3) return listOf(this)
    return (0..vertexCount - 2).map { index ->
        Polygon(
            vertices = listOf(
                this[0],
                this[index],
                this[index + 1]
            ),
            normal = normal,
            info = info
        )
    }
}

/**
 * Clips [this] [Polygon] by given [plane].
 */
fun <Info : Any> Polygon<Info>.clip(
    plane: Plane
): Polygon<Info>? {
    val verticesCount = this.vertexCount
    if (verticesCount < 3) return null
    val v0 = this[0]
    val v1 = this[1]
    val v2 = this[2]
    val resultVertices = mutableListOf<Vertex>()
    var lastVertex = this[verticesCount - 1]
    var lastSignedDistance = plane.signedDistanceTo(lastVertex.point)

    for (i in 0 until verticesCount) {
        val currentVertex = this[i]
        val currentSignedDistance = plane.signedDistanceTo(currentVertex.point)

        val eps = 0.001f
        val lastInRight = lastSignedDistance > eps
        val currentInRight = currentSignedDistance > eps

        // add intersection point
        if (lastInRight xor currentInRight) {
            val p1 = currentVertex.point
            val p2 = lastVertex.point
            val dir = p1 - p2
            val t = getLinePlaneIntersectionParameter(
                startPoint = p1,
                direction = dir,
                plane = plane
            )
            val point = p1 + t * dir
            val pointBarycentric = point.toBarycentric(
                normal = this.normal,
                p0 = v0.point,
                p1 = v1.point,
                p2 = v2.point
            )
            val textureCoordinates = pointBarycentric.toCartesian(
                p0 = v0.textureCoordinates,
                p1 = v1.textureCoordinates,
                p2 = v2.textureCoordinates
            )
            resultVertices += Vertex(
                point = point,
                textureCoordinates = textureCoordinates
            )
        }

        if (currentInRight) {
            resultVertices += currentVertex
        }

        lastVertex = currentVertex
        lastSignedDistance = currentSignedDistance
    }

    if (resultVertices.size < 3) return null

    return Polygon(
        vertices = resultVertices,
        normal = this.normal,
        info = this.info
    )
}

/**
 * Checks if [this] intersects [segment].
 */
fun Polygon<*>.intersectsBySegment(
    segment: Segment3D
): Boolean {
    val direction = segment.second - segment.first
    val t = getLinePlaneIntersectionParameter(
        startPoint = segment.first,
        direction = direction,
        plane = plane
    )
    if (t < 0f || t > 1f) return false
    val p = segment.first + t * direction
    for (i in vertices.indices) {
        val a = this[i].point
        val b = this[(i + 1) % vertexCount].point
        val n = normal cross (b - a)
        val dot1 = (a - centroid) dot n
        val dot2 = (a - p) dot n
        if (dot1 * dot2 < 0f) return false
    }
    return true
}