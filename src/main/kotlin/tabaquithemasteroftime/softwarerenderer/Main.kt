@file:JvmName("Main")

package tabaquithemasteroftime.softwarerenderer

import tabaquithemasteroftime.softwarerenderer.application.Application
import tabaquithemasteroftime.softwarerenderer.application.run

fun main() {
    Application.of(
        bufferWidth = 512,
        bufferHeight = 512
    ).run(
        windowWidth = 700,
        windowHeight = 700,
    )
}