package engine2D.core

import java.awt.Color
import java.awt.Rectangle
import kotlin.math.PI

class Sprite(
    var actualImage: String,
    val leftImage: String,
    val downImage: String,
    val rightImage: String,
    val upImage: String,
)

class SpriteBox(
    val rectangle: Rectangle,
    val sprite: Sprite? = null,
    val color: Color? = null,
)

class DirectionInRadian {
    companion object {
        const val RIGHT: Double = 0.0
        const val DOWN: Double = PI / 2
        const val LEFT: Double = PI
        const val UP: Double = (3 * PI) / 2
        const val DOWN_RIGHT = PI / 4
        const val DOWN_LEFT = (3 * PI) / 4
        const val UP_LEFT = (5 * PI) / 4
        const val UP_RIGHT = (7 * PI) / 4

    }
}