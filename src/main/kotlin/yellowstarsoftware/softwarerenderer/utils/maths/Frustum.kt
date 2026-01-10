package yellowstarsoftware.softwarerenderer.utils.maths

import yellowstarsoftware.softwarerenderer.utils.Camera
import yellowstarsoftware.yellowstar.math.geometry.objects.Plane
import yellowstarsoftware.yellowstar.math.geometry.times

/**
 * Frustum.
 */
interface Frustum {

    /**
     * Clips given [polygon].
     */
    fun <Info : Any> clipPolygon(
        polygon: Polygon<Info>
    ): Polygon<Info>?
}

/**
 * Creates a [Frustum] by [this] [Camera].
 */
fun Camera.createFrustum(): Frustum = FrustumImpl.of(this)

private class FrustumImpl private constructor(
    private val planes: List<Plane>
) : Frustum {

    override fun <Info : Any> clipPolygon(
        polygon: Polygon<Info>
    ): Polygon<Info>? {
        var result: Polygon<Info>? = polygon
        for (plane in planes) {
            if (result == null) return null
            result = result.clip(plane)
        }
        return result
    }

    companion object {

        fun of(camera: Camera): Frustum {
            val position = camera.position
            val direction = camera.forward
            val e1 = camera.right
            val e2 = camera.up
            val near = camera.near
            val a0 = near * direction
            val windowCenter = position + a0
            val a1 = a0 + e1 - e2
            val a2 = a0 - e1 - e2
            val a3 = a0 + e1 + e2
            return FrustumImpl(
                planes = listOf(
                    Plane(
                        point = windowCenter + e1,
                        normal = a1.cross(e2).normalized
                    ),
                    Plane(
                        point = windowCenter - e1,
                        normal = e2.cross(a2).normalized
                    ),
                    Plane(
                        point = windowCenter + e2,
                        normal = a3.cross(-e1).normalized
                    ),
                    Plane(
                        point = windowCenter - e2,
                        normal = a1.cross(e1).normalized
                    ),
                    Plane(
                        point = windowCenter,
                        normal = direction
                    )
                )
            )
        }
    }
}

