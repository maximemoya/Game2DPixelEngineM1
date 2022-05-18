package engine2D.core

import java.awt.Color
import java.awt.Rectangle
import javax.swing.Timer
import javax.swing.plaf.ColorUIResource
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

enum class TypeProjectile {
    SHOT_PLAYER_DIRECTION, SHOT_INVERSE_PLAYER_DIRECTION, SHOT_90DEGREE_PLAYER_DIRECTION, SHOT_270DEGREE_PLAYER_DIRECTION,
    SHOT_LEFT, SHOT_DOWN, SHOT_RIGHT, SHOT_UP,
    SHOT_UP_LEFT, SHOT_UP_RIGHT, SHOT_DOWN_LEFT, SHOT_DOWN_RIGHT
}

class Projectile(
    val spriteBox: SpriteBox,
    var speed: Int,
    var type: TypeProjectile = TypeProjectile.SHOT_PLAYER_DIRECTION,
    var timeBetweenShot: Int,
    var varianceX: Int,
    var varianceY: Int,
    var varianceAngle: Double,
) {

    var directionRadian: Double = DirectionInRadian.LEFT
    var directionRadianBefore: Double = DirectionInRadian.LEFT
    var speedX: Int = 0
    var speedY: Int = 0
    var timer = Timer(timeBetweenShot) { setToCenterPlayerPosition() }

    init {
        timer.start()
    }

    fun resetTimer() {
        setToCenterPlayerPosition()
        timer.stop()
        timer = Timer(timeBetweenShot) { setToCenterPlayerPosition() }
        timer.start()
    }

    fun updateVariance() {
        val beforeX = varianceX
        when (check4DirectionAccordingRadianDirection(directionRadianBefore)) {
            DirectionPattern.LEFT -> {
                when (check4DirectionAccordingRadianDirection(directionRadian)) {
                    DirectionPattern.DOWN -> {
                        varianceX = varianceY
                        varianceY = beforeX
                    }
                    DirectionPattern.UP -> {
                        varianceX = varianceY
                        varianceY = beforeX
                        varianceAngle *= -1
                    }
                    DirectionPattern.RIGHT -> {
                        varianceAngle *= -1
                    }
                    else -> {}
                }
            }
            DirectionPattern.RIGHT -> {
                when (check4DirectionAccordingRadianDirection(directionRadian)) {
                    DirectionPattern.DOWN -> {
                        varianceX = varianceY
                        varianceY = beforeX
                        varianceAngle *= -1
                    }
                    DirectionPattern.UP -> {
                        varianceX = varianceY
                        varianceY = beforeX
                    }
                    DirectionPattern.LEFT -> {
                        varianceAngle *= -1
                    }
                    else -> {}
                }
            }
            DirectionPattern.UP -> {
                when (check4DirectionAccordingRadianDirection(directionRadian)) {
                    DirectionPattern.DOWN -> {
                        varianceAngle *= -1
                    }
                    DirectionPattern.LEFT -> {
                        varianceX = varianceY
                        varianceY = beforeX
                        varianceAngle *= -1
                    }
                    DirectionPattern.RIGHT -> {
                        varianceX = varianceY
                        varianceY = beforeX
                    }
                    else -> {}
                }
            }
            DirectionPattern.DOWN -> {
                when (check4DirectionAccordingRadianDirection(directionRadian)) {
                    DirectionPattern.UP -> {
                        varianceAngle *= -1
                    }
                    DirectionPattern.LEFT -> {
                        varianceX = varianceY
                        varianceY = beforeX
                    }
                    DirectionPattern.RIGHT -> {
                        varianceX = varianceY
                        varianceY = beforeX
                        varianceAngle *= -1
                    }
                    else -> {}
                }
            }
            else -> {}
        }
    }

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

    fun move() {
        spriteBox.rectangle.x += speedX
        spriteBox.rectangle.y += speedY
//        if (spriteBox.rectangle.x < -spriteBox.rectangle.width ||
//            spriteBox.rectangle.x > Application.ScreenX ||
//            spriteBox.rectangle.y < -spriteBox.rectangle.height ||
//            spriteBox.rectangle.y > Application.ScreenY
//        ) {
//
//        }
    }

    fun setDirection(radian: Double): Projectile {
        directionRadianBefore = directionRadian
        directionRadian = radian
        updateVariance()
        speedX = (cos(radian) * speed).toInt()
        speedY = (sin(radian) * speed).toInt()
        return this
    }

    fun setPosition(x: Int, y: Int) {
        spriteBox.rectangle.x = x
        spriteBox.rectangle.y = y
    }

    fun setToCenterPlayerPosition() {
        when (type) {
            TypeProjectile.SHOT_PLAYER_DIRECTION -> {
                setDirection(Player.player.directionAngle + varianceAngle)
            }
            TypeProjectile.SHOT_INVERSE_PLAYER_DIRECTION -> {
                setDirection(Player.player.directionAngle + PI)
            }
            TypeProjectile.SHOT_90DEGREE_PLAYER_DIRECTION -> {
                setDirection(Player.player.directionAngle + PI / 2)
            }
            TypeProjectile.SHOT_270DEGREE_PLAYER_DIRECTION -> {
                setDirection(Player.player.directionAngle + (3 * PI / 2))
            }
            TypeProjectile.SHOT_LEFT -> {
                setDirection(DirectionInRadian.LEFT)
            }
            TypeProjectile.SHOT_RIGHT -> {
                setDirection(DirectionInRadian.RIGHT)
            }
            TypeProjectile.SHOT_UP -> {
                setDirection(DirectionInRadian.UP)
            }
            TypeProjectile.SHOT_DOWN -> {
                setDirection(DirectionInRadian.DOWN)
            }
            TypeProjectile.SHOT_UP_LEFT -> {
                setDirection(DirectionInRadian.UP_LEFT)
            }
            TypeProjectile.SHOT_UP_RIGHT -> {
                setDirection(DirectionInRadian.UP_RIGHT)
            }
            TypeProjectile.SHOT_DOWN_LEFT -> {
                setDirection(DirectionInRadian.DOWN_LEFT)
            }
            TypeProjectile.SHOT_DOWN_RIGHT -> {
                setDirection(DirectionInRadian.DOWN_RIGHT)
            }
        }
        spriteBox.rectangle.x =
            Player.player.spriteBox.rectangle.x + (Player.player.spriteBox.rectangle.width / 2) + varianceX
        spriteBox.rectangle.y =
            Player.player.spriteBox.rectangle.y + (Player.player.spriteBox.rectangle.height / 2) + varianceY
    }

    companion object {

        fun createProjectile(
            posX: Int,
            posY: Int,
            speed: Int,
            angleInRadian: Double,
            type: TypeProjectile,
            timeBetweenShot: Int,
            varianceX: Int = 0,
            varianceY: Int = 0,
            varianceAngle: Double = 0.0,
        ): Projectile =
            Projectile(
                spriteBox = SpriteBox(
                    rectangle = Rectangle(posX, posY, 6, 6),
                    sprite = null,
                    color = ColorUIResource.CYAN,
                ),
                speed = speed,
                type = type,
                timeBetweenShot = timeBetweenShot,
                varianceX = varianceX,
                varianceY = varianceY,
                varianceAngle = varianceAngle,
            ).setDirection(angleInRadian)

        // INIT LIST with shot player direction :
        val projectiles: MutableList<Projectile> = mutableListOf(
            createProjectile(
                Player.player.spriteBox.rectangle.x + (Player.player.spriteBox.rectangle.width / 2),
                Player.player.spriteBox.rectangle.y + (Player.player.spriteBox.rectangle.height / 2),
                Player.player.speed * 2,
                Player.player.directionAngle,
                TypeProjectile.SHOT_PLAYER_DIRECTION,
                950,
                0,
                -25
            ),
            createProjectile(
                Player.player.spriteBox.rectangle.x + (Player.player.spriteBox.rectangle.width / 2),
                Player.player.spriteBox.rectangle.y + (Player.player.spriteBox.rectangle.height / 2),
                Player.player.speed * 2,
                Player.player.directionAngle,
                TypeProjectile.SHOT_PLAYER_DIRECTION,
                1000
            ),
            createProjectile(
                Player.player.spriteBox.rectangle.x + (Player.player.spriteBox.rectangle.width / 2),
                Player.player.spriteBox.rectangle.y + (Player.player.spriteBox.rectangle.height / 2),
                Player.player.speed * 2,
                Player.player.directionAngle,
                TypeProjectile.SHOT_PLAYER_DIRECTION,
                1050,
                0,
                25
            ),
        )
    }

}

enum class ArmementEnum {
    shotgun
}

class Armement(
    val projectiles: List<Projectile>
) {

    companion object {

        val armements = hashMapOf(
            ArmementEnum.shotgun to Armement(
                listOf(
                    Projectile(
                        spriteBox = SpriteBox(Rectangle(0, 0, 0, 0), null, Color.WHITE),
                        speed = Player.player.speed * 2,
                        type = TypeProjectile.SHOT_PLAYER_DIRECTION,
                        timeBetweenShot = 2000,
                        varianceX = 0,
                        varianceY = 0,
                        varianceAngle = 0.0
                    )
                )
            ),
        )
    }
}

fun test () {
    Armement.armements[ArmementEnum.shotgun]
}