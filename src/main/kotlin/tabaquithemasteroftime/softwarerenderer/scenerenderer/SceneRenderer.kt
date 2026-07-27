package tabaquithemasteroftime.softwarerenderer.scenerenderer

import tabaquithemasteroftime.softwarerenderer.awtutils.ImageBuffer
import tabaquithemasteroftime.softwarerenderer.utils.*
import tabaquithemasteroftime.softwarerenderer.utils.maths.*
import tabaquithemasteroftime.softwarerenderer.utils.multithreading.CoroutinesWorkExecutor
import tabaquithemasteroftime.softwarerenderer.utils.multithreading.WorkExecutor
import tabaquithemasteroftime.yellowstar.math.geometry.*
import tabaquithemasteroftime.yellowstar.math.geometry.algorithms.getLinePlaneIntersectionParameter
import tabaquithemasteroftime.yellowstar.math.geometry.objects.Segment3D
import tabaquithemasteroftime.yellowstar.math.geometry.transformations.pointPerspectiveProjection
import tabaquithemasteroftime.yellowstar.math.utils.map
import tabaquithemasteroftime.yellowstar.math.utils.sqr
import java.awt.Graphics2D
import javax.swing.JComponent

/**
 * Entity for rendering a [Scene].
 */
interface SceneRenderer {

    /**
     * Renders [scene] from [camera] POV
     * into [component] using [graphics].
     */
    fun render(
        scene: Scene,
        camera: Camera,
        component: JComponent,
        graphics: Graphics2D
    )

    companion object {

        /**
         * Creates a [SceneRenderer] with given
         * [bufferWidth] and [bufferHeight] of its internal buffer.
         */
        fun of(
            bufferWidth: Int,
            bufferHeight: Int
        ): SceneRenderer {
            val imageBuffer = ImageBuffer.of(bufferWidth, bufferHeight)
            val depthBuffer = DepthBuffer.of(bufferWidth, bufferHeight)
            return SceneRendererImpl(
                colorBuffer = imageBuffer,
                depthBuffer = depthBuffer,
                workExecutor = CoroutinesWorkExecutor(
                    partsCount = 16
                )
            )
        }
    }
}

private class SceneRendererImpl(
    private val colorBuffer: ImageBuffer,
    private val depthBuffer: DepthBuffer,
    private val workExecutor: WorkExecutor
) : SceneRenderer {

    private val bufferWidth = colorBuffer.width
    private val bufferHeight = colorBuffer.height

    override fun render(
        scene: Scene,
        camera: Camera,
        component: JComponent,
        graphics: Graphics2D
    ) {
        colorBuffer.clear()
        depthBuffer.clear()
        val frustum = camera.createFrustum()
        for (polygon in scene.polygons) {

            // cull back faces
            val polygonPoint = polygon.vertices.firstOrNull() ?: continue
            if ((polygonPoint.point - camera.position) dot polygon.normal > 0f) continue

            // clip by frustum
            val clipped = frustum.clipPolygon(polygon) ?: continue
            val polygonPlane = clipped.plane

            val triangles = clipped.triangulate()
            for (triangle in triangles) {
                val screenPoints = projectPolygonPoints(
                    bufferWidth = bufferWidth,
                    bufferHeight = bufferHeight,
                    polygon = triangle,
                    camera = camera
                )
                val (v0, v1, v2) = triangle.vertices
                val barycentricCache = BarycentricCache3D.of(
                    normal = polygonPlane.normal,
                    p0 = v0.point,
                    p1 = v1.point,
                    p2 = v2.point
                )
                forEveryTrianglePixelV2(
                    points = screenPoints,
                    width = bufferWidth,
                    height = bufferHeight,
                    workExecutor = workExecutor
                ) { x, y ->
                    val alpha = map(x.toFloat(), 0f, bufferWidth - 1f, -1f, 1f)
                    val beta = map(y.toFloat(), 0f, bufferHeight - 1f, 1f, -1f)
                    val direction = camera.makeDirection(
                        alpha = alpha,
                        beta = beta
                    )
                    val depth = getLinePlaneIntersectionParameter(
                        startPoint = camera.position,
                        direction = direction,
                        plane = polygonPlane
                    )
                    val pixelPosition = camera.position + depth * direction
                    if (depth < depthBuffer[x, y]) {
                        depthBuffer[x, y] = depth
                        val barycentric = barycentricCache.toBarycentric(pixelPosition)
                        val pixelTextureCoordinates = barycentric.toCartesian(
                            p0 = v0.textureCoordinates,
                            p1 = v1.textureCoordinates,
                            p2 = v2.textureCoordinates
                        )
                        val textureColor = triangle.info[pixelTextureCoordinates]
                        colorBuffer[x, y] = fragmentShaderWeDeserve(
                            pixelPosition = pixelPosition,
                            textureColor = textureColor,
                            polygon = polygon,
                            scene = scene
                        )
                    }
                }
            }
        }

        colorBuffer.draw(component, graphics)
    }
}

private fun projectPolygonPoints(
    bufferWidth: Int,
    bufferHeight: Int,
    polygon: Polygon,
    camera: Camera
): List<Point> {
    return polygon.vertices.map { vertex ->
        val projected = projectPoint(vertex.point, camera)
        val x = map(projected.x, -1f, 1f, 0f, bufferWidth - 1f)
        val y = map(projected.y, -1f, 1f, bufferHeight - 1f, 0f)
        Point(x.toInt(), y.toInt())
    }
}

private fun projectPoint(
    point: Vector3D,
    camera: Camera
): Vector2D {
    val nearPlane = camera.nearPlane
    val a = pointPerspectiveProjection(
        point = point,
        plane = nearPlane,
        projectionCenter = camera.position
    )
    val e1 = camera.right
    val e2 = camera.up
    val v = a - nearPlane.point
    val x = (v dot e1) * camera.virtualWindowWidthHalf
    val y = (v dot e2) * camera.virtualWindowHeightHalf
    return Vector2D(x, y)
}

private fun fragmentShaderWeDeserve(
    pixelPosition: Vector3D,
    textureColor: Color,
    polygon: Polygon,
    scene: Scene
): Color {
    var resultColor = scene.ambientLight.rgb
    for (light in scene.lightSources) {
        val lightDirection = pixelPosition - light.center
        if (lightDirection.lengthSquared > sqr(light.radius)) continue
        val distance = lightDirection.length
        val lightCoefficient = (-lightDirection dot polygon.normal) / distance
        if (lightCoefficient <= 0f) continue
        if (isLightBlocked(pixelPosition, light, polygon, scene)) continue
        val distanceCoefficient = 1f - distance/light.radius
        resultColor += lightCoefficient * distanceCoefficient * light.color.rgb
    }
    return (resultColor * textureColor.rgb).asColor()
}

private fun isLightBlocked(
    pixelPosition: Vector3D,
    light: LightSource,
    polygon: Polygon,
    scene: Scene
): Boolean {
    val segment = Segment3D(
        first = pixelPosition,
        second = light.center
    )
    scene.polygons.forEach { shadowCaster ->
        if (shadowCaster !== polygon && shadowCaster.intersectsBySegment(segment)) {
            return true
        }
    }
    return false
}