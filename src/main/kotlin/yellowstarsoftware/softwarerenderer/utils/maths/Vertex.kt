package yellowstarsoftware.softwarerenderer.utils.maths

import yellowstarsoftware.yellowstar.math.geometry.Vector2D
import yellowstarsoftware.yellowstar.math.geometry.Vector3D

/**
 * Vertex of a polygon.
 * @property point position of the vertex
 * @property textureCoordinates texture coordinates of the vertex
 */
class Vertex(
    val point: Vector3D,
    val textureCoordinates: Vector2D
)