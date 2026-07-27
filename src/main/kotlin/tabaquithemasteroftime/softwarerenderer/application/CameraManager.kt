package tabaquithemasteroftime.softwarerenderer.application

import tabaquithemasteroftime.softwarerenderer.utils.Camera
import tabaquithemasteroftime.yellowstar.math.geometry.Quaternion
import tabaquithemasteroftime.yellowstar.math.geometry.Vector3D
import tabaquithemasteroftime.yellowstar.math.geometry.rotateVector

/**
 * Camera manager.
 */
interface CameraManager {

    /**
     * Current camera.
     */
    val camera: Camera

    /**
     * Updates current camera.
     */
    fun updateCamera(block: (Camera) -> Camera)

    companion object {

        /**
         * Creates a [CameraManager].
         * @param initialCamera initial camera
         * @param validator validator that determines which camera states are allowed
         */
        fun of(
            initialCamera: Camera,
            validator: (Camera) -> Boolean
        ): CameraManager = CameraManagerImpl(
            camera = initialCamera,
            validator = validator
        )
    }
}

/**
 * Moves the camera forward by [delta].
 */
fun CameraManager.moveForward(delta: Float) {
    updateCamera { camera ->
        camera.copy(
            position = camera.position + camera.forward * delta
        )
    }
}

/**
 * Moves the camera sideways by [delta].
 */
fun CameraManager.moveStrafe(delta: Float) {
    updateCamera { camera ->
        camera.copy(
            position = camera.position + camera.right * delta
        )
    }
}

/**
 * Moves the camera up by [delta].
 */
fun CameraManager.moveUp(delta: Float) {
    updateCamera { camera ->
        camera.copy(
            position = camera.position + camera.up * delta
        )
    }
}

/**
 * Rotates the camera horizontally by [delta].
 */
fun CameraManager.rotateHorizontally(delta: Float) {
    rotate(
        axis = Vector3D.J,
        angle = delta
    )
}

/**
 * Rotates the camera vertically by [delta].
 */
fun CameraManager.rotateVertically(delta: Float) {
    rotate(
        axis = -camera.right,
        angle = delta
    )
}

private fun CameraManager.rotate(
    axis: Vector3D,
    angle: Float
) {
    updateCamera { camera ->
        val rotation = Quaternion.fromRotation(axis.normalized, angle)
        camera.copy(
            right = rotation.rotateVector(camera.right),
            up = rotation.rotateVector(camera.up),
            forward = rotation.rotateVector(camera.forward),
        )
    }
}

private class CameraManagerImpl(
    override var camera: Camera,
    private val validator: (Camera) -> Boolean
) : CameraManager {

    override fun updateCamera(
        block: (Camera) -> Camera
    ) {
        val newCamera = block.invoke(camera)
        if (validator.invoke(newCamera)) {
            camera = newCamera
        }
    }
}