package tabaquithemasteroftime.softwarerenderer.scenerenderer

import tabaquithemasteroftime.softwarerenderer.utils.Color
import tabaquithemasteroftime.yellowstar.math.geometry.Vector3D

/**
 * Source of light.
 * @property center center of the light source
 * @property radius radius of the light source
 * @property color color of the light source
 */
data class LightSource(
    val center: Vector3D,
    val radius: Float,
    val color: Color
)