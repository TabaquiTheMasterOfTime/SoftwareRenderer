package yellowstarsoftware.softwarerenderer.utils

import yellowstarsoftware.yellowstar.math.geometry.Quaternion
import yellowstarsoftware.yellowstar.math.geometry.Vector3D
import yellowstarsoftware.yellowstar.math.geometry.objects.Plane
import yellowstarsoftware.yellowstar.math.geometry.rotateVector
import yellowstarsoftware.yellowstar.math.geometry.times
import kotlin.math.tan

/**
 * Perspective camera.
 * @property position position of the camera
 * @property right right direction of the camera
 * @property up up direction of the camera
 * @property forward forward direction of the camera
 * @property near near plane distance
 * @property virtualWindowWidthHalf half of the virtual window's width
 * @property virtualWindowHeightHalf half of the virtual window's height
 */
data class Camera(
    val position: Vector3D,
    val right: Vector3D,
    val up: Vector3D,
    val forward: Vector3D,
    val near: Float,
    val virtualWindowWidthHalf: Float,
    val virtualWindowHeightHalf: Float
) {

    companion object {

        /**
         * Creates a [Camera].
         * @param position position of the camera
         * @param rotation rotation of the camera
         * @param near near plane distance
         * @param fovY y field of view
         * @param aspect window aspect ratio
         */
        fun of(
            position: Vector3D,
            rotation: Quaternion,
            near: Float,
            fovY: Float,
            aspect: Float
        ): Camera {
            val virtualWindowHeightHalf = tan(fovY / 2.0f) * near
            val virtualWindowWidthHalf = virtualWindowHeightHalf * aspect
            return Camera(
                position = position,
                right = rotation.rotateVector(Vector3D.I),
                up = rotation.rotateVector(Vector3D.J),
                forward = rotation.rotateVector(Vector3D.K),
                near = near,
                virtualWindowWidthHalf = virtualWindowWidthHalf,
                virtualWindowHeightHalf = virtualWindowHeightHalf
            )
        }
    }
}

/**
 * Returns near [Plane] of the camera.
 */
val Camera.nearPlane get() = Plane(
    point = position + near * forward,
    normal = forward
)

/**
 * Returns direction from the center of the camera
 * to the point on a virtual window with coordinates ([alpha], [beta]).
 */
fun Camera.makeDirection(
    alpha: Float,
    beta: Float
) = near * forward + alpha * right + beta * up