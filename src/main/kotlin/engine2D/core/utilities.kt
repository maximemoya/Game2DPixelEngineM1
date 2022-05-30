package engine2D.core

import engine2D.Application
import java.awt.Color
import java.awt.Rectangle
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

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
    var color: Color? = null,
)

class Direction(
    var x:Int,
    var y:Int,
    var radian:Double,
){
    constructor(radian:Double, speed:Int) : this(0,0,radian) {
        x = ((cos(radian) * speed).toInt())
        y = ((sin(radian) * speed).toInt())
    }

}

class SpriteMovable(
    val spriteBox: SpriteBox,
    var speed: Int,
    radian: Double,
){
    var direction = Direction(radian, speed)
}

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

class Utilities() {
    companion object {

        fun isOutOfScreen(spriteBox: SpriteBox, delta: Int = 0): Boolean =
            (spriteBox.rectangle.x < -(spriteBox.rectangle.width +  delta) ||
                    spriteBox.rectangle.x > (Application.ScreenX + delta) ||
                    spriteBox.rectangle.y < -(spriteBox.rectangle.height + delta) ||
                    spriteBox.rectangle.y > (Application.ScreenY + delta))

        fun calculVariance(variance:Int, movable: SpriteMovable): Pair<Double,Double> {
            val varianceX = cos(movable.direction.radian + (PI/2)) * variance
            val varianceY = sin(movable.direction.radian + (PI/2)) * variance
            return (varianceX to varianceY)
        }
    }

}
