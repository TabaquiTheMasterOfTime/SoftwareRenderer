package yellowstarsoftware.softwarerenderer.scenerenderer

import yellowstarsoftware.softwarerenderer.utils.Color

/**
 * Scene.
 * @property polygons polygons of the [Scene]
 * @property lightSources sources of light
 * @property ambientLight ambient light
 */
data class Scene(
    val polygons: List<Polygon>,
    val lightSources: List<LightSource>,
    val ambientLight: Color
)