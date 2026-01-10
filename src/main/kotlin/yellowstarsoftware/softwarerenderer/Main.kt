@file:JvmName("Main")

package yellowstarsoftware.softwarerenderer

import yellowstarsoftware.softwarerenderer.application.Application
import yellowstarsoftware.softwarerenderer.application.run

fun main() {
    Application.of(
        bufferWidth = 512,
        bufferHeight = 512
    ).run(
        windowWidth = 700,
        windowHeight = 700,
    )
}