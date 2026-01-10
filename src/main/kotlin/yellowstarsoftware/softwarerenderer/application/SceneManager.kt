package yellowstarsoftware.softwarerenderer.application

import yellowstarsoftware.softwarerenderer.scenerenderer.LightSource
import yellowstarsoftware.softwarerenderer.scenerenderer.Scene

/**
 * Scene manager.
 */
interface SceneManager {

    /**
     * Current scene.
     */
    val scene: Scene

    /**
     * Adds [lightSource] to the scene.
     */
    operator fun plusAssign(lightSource: LightSource)

    companion object {

        /**
         * Creates a [SceneManager] with [initialScene].
         */
        fun of(
            initialScene: Scene
        ): SceneManager = SceneManagerImpl(initialScene)
    }
}

private class SceneManagerImpl(
    override var scene: Scene
) : SceneManager {

    override fun plusAssign(lightSource: LightSource) {
        scene = scene.copy(lightSources = scene.lightSources + lightSource)
    }
}