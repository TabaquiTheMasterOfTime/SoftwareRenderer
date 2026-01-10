package yellowstarsoftware.softwarerenderer.application

import yellowstarsoftware.softwarerenderer.awtutils.Key
import yellowstarsoftware.softwarerenderer.scenerenderer.LightSource
import yellowstarsoftware.softwarerenderer.scenerenderer.SceneRenderer
import yellowstarsoftware.softwarerenderer.utils.Camera
import yellowstarsoftware.softwarerenderer.utils.Colors
import yellowstarsoftware.yellowstar.math.geometry.PI
import yellowstarsoftware.yellowstar.math.geometry.PID2
import yellowstarsoftware.yellowstar.math.geometry.Quaternion
import yellowstarsoftware.yellowstar.math.geometry.Vector3D
import java.awt.Graphics2D
import javax.swing.JComponent
import kotlin.math.abs
import kotlin.system.exitProcess

/**
 * Application.
 */
interface Application {

    /**
     * Current camera.
     */
    val camera: Camera

    /**
     * [List] of [KeyBinding] that are triggered
     * once per a key press event.
     */
    val reactiveKeyBindings: List<KeyBinding>

    /**
     * [List] of [KeyBinding] that are triggered
     * on each frame while their corresponding key is pressed.
     */
    val continuousKeyBindings: List<KeyBinding>

    /**
     * Called for each frame.
     */
    fun onFrame(
        component: JComponent,
        graphics: Graphics2D
    )

    companion object {

        /**
         * Creates an [Application].
         * @param bufferWidth width of the buffer
         * @param bufferHeight height of the buffer
         */
        fun of(
            bufferWidth: Int,
            bufferHeight: Int
        ): Application = ApplicationImpl.of(
            bufferWidth = bufferWidth,
            bufferHeight = bufferHeight
        )
    }
}

private class ApplicationImpl(
    private val sceneManager: SceneManager,
    private val cameraManager: CameraManager,
    private val sceneRenderer: SceneRenderer
) : Application {

    private var isAccelerationEnabled = false
    private val speed get() = if (isAccelerationEnabled) 80f else 20f
    private val angleSpeed = PI / 50f

    override val camera get() = cameraManager.camera

    override val reactiveKeyBindings = listOf(
        Key.VK_ESCAPE.bind {
            exitProcess(0)
        },
        Key.VK_ENTER.bind {
            sceneManager += LightSource(
                center = camera.position,
                radius = 50_000F,
                color = Colors.YELLOW_LAMP_COLOR
            )
        },
        Key.VK_X.bind {
            isAccelerationEnabled = !isAccelerationEnabled
        }
    )

    override val continuousKeyBindings = listOf(
        Key.VK_W.bind {
            cameraManager.moveForward(speed)
        },
        Key.VK_S.bind {
            cameraManager.moveForward(-speed)
        },
        Key.VK_D.bind {
            cameraManager.moveStrafe(speed)
        },
        Key.VK_A.bind {
            cameraManager.moveStrafe(-speed)
        },
        Key.VK_SPACE.bind {
            cameraManager.moveUp(speed)
        },
        Key.VK_C.bind {
            cameraManager.moveUp(-speed)
        },
        Key.VK_RIGHT.bind {
            cameraManager.rotateHorizontally(angleSpeed)
        },
        Key.VK_LEFT.bind {
            cameraManager.rotateHorizontally(-angleSpeed)
        },
        Key.VK_UP.bind {
            cameraManager.rotateVertically(angleSpeed)
        },
        Key.VK_DOWN.bind {
            cameraManager.rotateVertically(-angleSpeed)
        }
    )

    override fun onFrame(
        component: JComponent,
        graphics: Graphics2D
    ) {
        sceneRenderer.render(
            scene = sceneManager.scene,
            camera = cameraManager.camera,
            component = component,
            graphics = graphics
        )
    }

    companion object {

        fun of(
            bufferWidth: Int,
            bufferHeight: Int
        ): Application {
            val initialScene = InitialSceneProvider.of().provide()
            val sceneManager = SceneManager.of(initialScene)
            val cameraManager = CameraManager.of(
                initialCamera = Camera.of(
                    position = Vector3D.ZERO,
                    rotation = Quaternion.NO_ROTATION,
                    near = 1f,
                    fovY = PID2,
                    aspect = 1f,
                ),
                validator = { camera ->
                    val directionY = camera.forward.y
                    // checking if "vertical" angle is in [-PI/2, PI/2]
                    abs(directionY) < 0.999f
                }
            )
            val sceneRenderer = SceneRenderer.of(
                bufferWidth = bufferWidth,
                bufferHeight = bufferHeight
            )
            return ApplicationImpl(
                sceneManager = sceneManager,
                cameraManager = cameraManager,
                sceneRenderer = sceneRenderer
            )
        }
    }
}