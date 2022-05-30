package engine2D.core.projectile

import engine2D.core.DirectionInRadian
import engine2D.core.SpriteBox
import engine2D.core.Utilities
import engine2D.core.enemy.DirectionPattern
import engine2D.core.player.Player
import java.awt.Rectangle
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

enum class DirectionProjectile {
    SHOT_PLAYER_DIRECTION, SHOT_INVERSE_PLAYER_DIRECTION, SHOT_90DEGREE_PLAYER_DIRECTION, SHOT_270DEGREE_PLAYER_DIRECTION,
    SHOT_LEFT, SHOT_DOWN, SHOT_RIGHT, SHOT_UP,
    SHOT_UP_LEFT, SHOT_UP_RIGHT, SHOT_DOWN_LEFT, SHOT_DOWN_RIGHT,
    SHOT_VARIANCE_ANGLE_DIRECTION,
    SHOT_RANDOM_DIRECTION
}

class Projectile {

    var spriteBox: SpriteBox = SpriteBox(Rectangle(0, 0))
    var speed: Int = 0
    var speedX: Int = 0
    var speedY: Int = 0
    var directionProjectileEnum: DirectionProjectile = DirectionProjectile.SHOT_PLAYER_DIRECTION
    var directionRadian: Double = DirectionInRadian.LEFT
    var variance: Int = 0
    var varianceAngle: Double = 0.0
    var delayFrameCounter: Int = 0
    var delayFrameToSpawn: Int = 0
    var isActive: Boolean = false

    fun check4DirectionAccordingRadianDirection(directionRadian: Double): DirectionPattern {
        return if (directionRadian >= (PI / 4) && directionRadian < ((3 * PI) / 4)) {
            DirectionPattern.DOWN
        } else if (directionRadian >= ((3 * PI) / 4) && directionRadian < ((5 * PI) / 4)) {
            DirectionPattern.LEFT
        } else if (directionRadian >= ((5 * PI) / 4) && directionRadian < ((7 * PI) / 4)) {
            DirectionPattern.UP
        } else {
            DirectionPattern.RIGHT
        }
    }

    fun resetParams(
        spriteBox: SpriteBox = SpriteBox(Rectangle(0, 0, 0, 0)),
        typeDirection: DirectionProjectile = DirectionProjectile.SHOT_PLAYER_DIRECTION,
        speed: Int = 0,
        directionRadian: Double = 0.0,
        variance: Int = 0,
        varianceAngle: Double = 0.0,
        isActive: Boolean = false,
    ) {
        this.spriteBox = spriteBox
        this.directionProjectileEnum = typeDirection
        this.speed = speed
        this.directionRadian = directionRadian
        this.variance = variance
        this.varianceAngle = varianceAngle
        this.isActive = isActive

    }

    fun move() {

        if (isActive) {
            spriteBox.rectangle.x += speedX
            spriteBox.rectangle.y += speedY
            if (Utilities.isOutOfScreen(spriteBox)) {
                isActive = false
            }
        }

    }

    fun setDirection(radian: Double): Projectile {
        directionRadian = radian
//        updateVariance()
        speedX = (cos(radian) * speed).toInt()
        speedY = (sin(radian) * speed).toInt()
        return this
    }

    fun setPosition(x: Int, y: Int) {
        spriteBox.rectangle.x = x
        spriteBox.rectangle.y = y
    }

    private fun setToCenterPlayerPosition() {
        when (directionProjectileEnum) {
            DirectionProjectile.SHOT_PLAYER_DIRECTION -> {
                setDirection(Player.player.directionAngle + varianceAngle)
            }
            DirectionProjectile.SHOT_INVERSE_PLAYER_DIRECTION -> {
                setDirection(Player.player.directionAngle + PI + varianceAngle)
            }
            DirectionProjectile.SHOT_90DEGREE_PLAYER_DIRECTION -> {
                setDirection(Player.player.directionAngle + PI / 2 + varianceAngle)
            }
            DirectionProjectile.SHOT_270DEGREE_PLAYER_DIRECTION -> {
                setDirection(Player.player.directionAngle + (3 * PI / 2) + varianceAngle)
            }
            DirectionProjectile.SHOT_LEFT -> {
                setDirection(DirectionInRadian.LEFT)
            }
            DirectionProjectile.SHOT_RIGHT -> {
                setDirection(DirectionInRadian.RIGHT)
            }
            DirectionProjectile.SHOT_UP -> {
                setDirection(DirectionInRadian.UP)
            }
            DirectionProjectile.SHOT_DOWN -> {
                setDirection(DirectionInRadian.DOWN)
            }
            DirectionProjectile.SHOT_UP_LEFT -> {
                setDirection(DirectionInRadian.UP_LEFT)
            }
            DirectionProjectile.SHOT_UP_RIGHT -> {
                setDirection(DirectionInRadian.UP_RIGHT)
            }
            DirectionProjectile.SHOT_DOWN_LEFT -> {
                setDirection(DirectionInRadian.DOWN_LEFT)
            }
            DirectionProjectile.SHOT_DOWN_RIGHT -> {
                setDirection(DirectionInRadian.DOWN_RIGHT)
            }
            DirectionProjectile.SHOT_VARIANCE_ANGLE_DIRECTION -> {
                setDirection(varianceAngle)
            }
            DirectionProjectile.SHOT_RANDOM_DIRECTION -> {
                setDirection(Random.nextDouble(2 * PI))
            }
        }
        spriteBox.rectangle.x =
            Player.player.spriteBox.rectangle.x + (Player.player.spriteBox.rectangle.width / 2) + (cos(directionRadian + (PI / 2)) * variance).toInt()
        spriteBox.rectangle.y =
            Player.player.spriteBox.rectangle.y + (Player.player.spriteBox.rectangle.height / 2) + (sin(directionRadian + (PI / 2)) * variance).toInt()
    }

    fun repopFromPlayer() {
        setToCenterPlayerPosition()
        isActive = true
    }

    companion object {

        /*fun createProjectile(): Projectile =
            Projectile(
                spriteBox = SpriteBox(
                    rectangle = Rectangle(0, 0, 0, 0),
                    sprite = null,
                    color = null,
                ),
                speed = 0,
                directionProjectileEnum = DirectionProjectile.SHOT_PLAYER_DIRECTION,
                variance = 0,
                varianceAngle = 0.0,
            )*/

        // INIT LIST with shot player direction :
        val projectiles = List(200) {
            Projectile()
        }
    }

}