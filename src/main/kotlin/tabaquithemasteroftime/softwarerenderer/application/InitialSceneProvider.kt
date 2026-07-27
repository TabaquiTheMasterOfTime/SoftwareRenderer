package tabaquithemasteroftime.softwarerenderer.application

import tabaquithemasteroftime.softwarerenderer.awtutils.loadImage
import tabaquithemasteroftime.softwarerenderer.scenerenderer.Polygon
import tabaquithemasteroftime.softwarerenderer.scenerenderer.Scene
import tabaquithemasteroftime.softwarerenderer.scenerenderer.Texture
import tabaquithemasteroftime.softwarerenderer.utils.Colors
import tabaquithemasteroftime.softwarerenderer.utils.asColor
import tabaquithemasteroftime.softwarerenderer.utils.maths.Vertex
import tabaquithemasteroftime.yellowstar.math.geometry.Vector2D
import tabaquithemasteroftime.yellowstar.math.geometry.Vector3D

/**
 * Entity that provides initial [Scene].
 */
interface InitialSceneProvider {

    /**
     * Provides initial [Scene].
     */
    fun provide(): Scene

    companion object {

        /**
         * Creates an [InitialSceneProvider].
         */
        fun of() : InitialSceneProvider = InitialSceneProviderImpl()
    }
}

private class InitialSceneProviderImpl : InitialSceneProvider {

    override fun provide(): Scene {
        return Scene(
            polygons = makePolygons(),
            lightSources = emptyList(),
            ambientLight = Colors.WHITE.rgb.times(0.5f).asColor()
        )
    }

    private fun makePolygons() = buildList<Polygon> {
        val wallTexture = Texture.of(loadImage("Bricks.png"))
        val floorTexture = Texture.of(loadImage("Asphalt.png"))
        val roofTexture = Texture.of(loadImage("Roof.png"))
        val textureScale = 0.001f
        this += makeRectangle(
            Vector3D.ZERO,
            width = 100_000f,
            height = 100_000f,
            normal = Vector3D.J,
            right = Vector3D.I,
            textureScale = textureScale,
            texture = floorTexture
        )

        this += makeBuilding(
            floorCenter = Vector3D(2_000f, 0f, 1000f),
            width = 1500f,
            length = 3000f,
            height = 1000f,
            textureScale = textureScale,
            wallTexture = wallTexture,
            roofTexture = roofTexture
        )

        this += makeBuilding(
            floorCenter = Vector3D(7_000f, 0f, 1_500f),
            width = 5_000f,
            length = 5_000f,
            height = 1_000f,
            textureScale = textureScale,
            wallTexture = wallTexture,
            roofTexture = roofTexture
        )
    }
}

private fun makeBuilding(
    floorCenter: Vector3D,
    width: Float,
    height: Float,
    length: Float,
    textureScale: Float,
    wallTexture : Texture,
    roofTexture : Texture
) : List<Polygon> {
    val center = floorCenter + Vector3D(0f, height/2f, 0f)
    return listOf(
        // west wall
        makeRectangle(
            center = center + Vector3D(-width/2f, 0f, 0f),
            width = length,
            height = height,
            normal = -Vector3D.I,
            right = -Vector3D.K,
            textureScale = textureScale,
            texture = wallTexture
        ),
        // east wall
        makeRectangle(
            center = center + Vector3D(width/2f, 0f, 0f),
            width = length,
            height = height,
            normal = Vector3D.I,
            right = -Vector3D.K,
            textureScale = textureScale,
            texture = wallTexture
        ),
        // north wall
        makeRectangle(
            center = center + Vector3D(0f, 0f, length/2f),
            width = width,
            height = height,
            normal = Vector3D.K,
            right = -Vector3D.I,
            textureScale = textureScale,
            texture = wallTexture
        ),
        // south wall
        makeRectangle(
            center = center + Vector3D(0f, 0f, -length/2f),
            width = width,
            height = height,
            normal = -Vector3D.K,
            right = Vector3D.I,
            textureScale = textureScale,
            texture = wallTexture
        ),
        // roof
        makeRectangle(
            center = floorCenter + Vector3D(0f, height, 0f),
            width = width,
            height = length,
            normal = Vector3D.J,
            right = Vector3D.I,
            textureScale = textureScale,
            texture = roofTexture
        )
    )
}

private fun makeRectangle(
    center: Vector3D,
    width: Float,
    height: Float,
    right: Vector3D,
    normal: Vector3D,
    textureScale: Float,
    texture: Texture
) : Polygon {
    val up = right cross normal
    val points = run {
        val e1 = right * width / 2f
        val e2 = up * height / 2f
        listOf(
            center - e1 - e2,
            center - e1 + e2,
            center + e1 + e2,
            center + e1 - e2
        )
    }
    val textureCoordinates = run {
        val textureWidth = width * textureScale
        val textureHeight = height * textureScale
        listOf(
            Vector2D(0f, 0f),
            Vector2D(0f, textureHeight),
            Vector2D(textureWidth, textureHeight),
            Vector2D(textureWidth, 0f),
        )
    }
    return Polygon(
        vertices = (0..3).map { index ->
            Vertex(
                point = points[index],
                textureCoordinates = textureCoordinates[index]
            )
        },
        normal = normal.normalized,
        info = texture
    )
}