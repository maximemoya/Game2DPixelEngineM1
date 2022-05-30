package engine2D.core.enemy

import engine2D.Application
import engine2D.core.DirectionInRadian
import engine2D.core.SpriteBox
import engine2D.core.player.Player
import java.awt.Color
import java.awt.Rectangle
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt
import kotlin.random.Random

enum class DirectionPattern {
    LEFT, UP_LEFT, UP, UP_RIGHT, RIGHT, DOWN_RIGHT, DOWN, DOWN_LEFT, GOTOPLAYER, RANDOM
}

class Enemy(
    val spriteBox: SpriteBox,
    var directionPattern: DirectionPattern,
    var speed: Int = 0,
    var directionRadian: Double? = null
) {

    var speedX: Int = 0
    var speedY: Int = 0

    init {
        if (directionRadian != null) {
            setSpeedXYAccordingToDirection()
        } else {
            when (directionPattern) {
                DirectionPattern.LEFT -> {
                    directionRadian = DirectionInRadian.LEFT
                    speedX = -speed
                    speedY = 0
                }
                DirectionPattern.UP_LEFT -> {
                    directionRadian = DirectionInRadian.UP_LEFT
                    setSpeedXYAccordingToDirection()
                }
                DirectionPattern.UP -> {
                    directionRadian = DirectionInRadian.UP
                    speedX = 0
                    speedY = -speed
                }
                DirectionPattern.UP_RIGHT -> {
                    directionRadian = DirectionInRadian.UP_RIGHT
                    setSpeedXYAccordingToDirection()
                }
                DirectionPattern.RIGHT -> {
                    directionRadian = DirectionInRadian.RIGHT
                    speedX = speed
                    speedY = 0
                }
                DirectionPattern.DOWN_RIGHT -> {
                    directionRadian = DirectionInRadian.DOWN_RIGHT
                    setSpeedXYAccordingToDirection()
                }
                DirectionPattern.DOWN -> {
                    directionRadian = DirectionInRadian.DOWN
                    speedX = 0
                    speedY = speed
                }
                DirectionPattern.DOWN_LEFT -> {
                    directionRadian = DirectionInRadian.DOWN_LEFT
                    setSpeedXYAccordingToDirection()
                }
                DirectionPattern.RANDOM -> {
                    directionRadian = (Random.nextDouble(2 * PI))
                    setSpeedXYAccordingToDirection()
                }
                DirectionPattern.GOTOPLAYER -> {
                    directionToPlayerUpdate()
                }
            }
        }
    }

    fun directionToPlayerUpdate() {
        val deltaX = Player.player.spriteBox.rectangle.x - spriteBox.rectangle.x
        val deltaY = Player.player.spriteBox.rectangle.y - spriteBox.rectangle.y
        val deltaDistance = sqrt((deltaX * deltaX).toDouble() + (deltaY * deltaY))
        var step = (deltaDistance / speed).toInt()
        if (step < 1) {
            step = 1
        }
        speedX = (deltaX / step)
        speedY = (deltaY / step)
    }

    fun setSpeedXYAccordingToDirection() {
        speedX = (cos(directionRadian!!) * speed).toInt()
        speedY = (sin(directionRadian!!) * speed).toInt()
    }

    fun move() {
        if (directionPattern == DirectionPattern.GOTOPLAYER) {
            directionToPlayerUpdate()
        }
        spriteBox.rectangle.x += speedX
        spriteBox.rectangle.y += speedY
        if (spriteBox.rectangle.x < -spriteBox.rectangle.width ||
            spriteBox.rectangle.x > Application.ScreenX ||
            spriteBox.rectangle.y < -spriteBox.rectangle.height ||
            spriteBox.rectangle.y > Application.ScreenY
        ) {
            respawnCornerInScreen()
        }
    }

    fun respawnCenter() {
        spriteBox.rectangle.x = (Application.ScreenX - spriteBox.rectangle.width) / 2
        spriteBox.rectangle.y = (Application.ScreenY - spriteBox.rectangle.height) / 2
    }

    fun respawnRandomInScreen() {
        spriteBox.rectangle.x = Random.nextInt(Application.ScreenX)
        spriteBox.rectangle.y = Random.nextInt(Application.ScreenY)
    }

    fun respawnRandomAroundPlayerInScreen() {
        spriteBox.rectangle.x = Random.nextInt(Application.ScreenX)
        spriteBox.rectangle.y = Random.nextInt(Application.ScreenY)

        if (spriteBox.rectangle.x > Player.player.spriteBox.rectangle.x - 16 && spriteBox.rectangle.x < Player.player.spriteBox.rectangle.x + Player.player.spriteBox.rectangle.width + 16) {
            if (spriteBox.rectangle.x - (Player.player.spriteBox.rectangle.x + Player.player.spriteBox.rectangle.width) <= 0) {
                spriteBox.rectangle.x = Player.player.spriteBox.rectangle.x - 16
            } else {
                spriteBox.rectangle.x =
                    Player.player.spriteBox.rectangle.x + Player.player.spriteBox.rectangle.width + 16
            }
        }

        if (spriteBox.rectangle.y > Player.player.spriteBox.rectangle.y - 16 && spriteBox.rectangle.y < Player.player.spriteBox.rectangle.y + Player.player.spriteBox.rectangle.height + 16) {
            if (spriteBox.rectangle.y - (Player.player.spriteBox.rectangle.y + Player.player.spriteBox.rectangle.height) <= 0) {
                spriteBox.rectangle.y = Player.player.spriteBox.rectangle.y - 16
            } else {
                spriteBox.rectangle.y =
                    Player.player.spriteBox.rectangle.y + Player.player.spriteBox.rectangle.height + 16
            }
        }
    }

    fun respawnCornerInScreen() {
        when (Random.nextInt(4)) {
            0 -> {
                spriteBox.rectangle.x = 0
                spriteBox.rectangle.y = 0
            }
            1 -> {
                spriteBox.rectangle.x = Application.ScreenX - spriteBox.rectangle.width
                spriteBox.rectangle.y = 0
            }
            2 -> {
                spriteBox.rectangle.x = Application.ScreenX - spriteBox.rectangle.width
                spriteBox.rectangle.y = Application.ScreenY - spriteBox.rectangle.height
            }
            else -> {
                spriteBox.rectangle.x = 0
                spriteBox.rectangle.y = Application.ScreenY - spriteBox.rectangle.height
            }
        }
    }

    private enum class Direction4 {
        LEFT, UP, RIGHT, DOWN
    }

    fun respawnRandomOutScreen() {
        when (Direction4.values().random()) {
            Direction4.LEFT -> {
                spriteBox.rectangle.x = -spriteBox.rectangle.width
                spriteBox.rectangle.y =
                    Random.nextInt(Application.ScreenY + spriteBox.rectangle.height) - spriteBox.rectangle.height
            }
            Direction4.UP -> {
                spriteBox.rectangle.x =
                    Random.nextInt(Application.ScreenX + spriteBox.rectangle.width) - spriteBox.rectangle.width
                spriteBox.rectangle.y = -spriteBox.rectangle.height
            }
            Direction4.RIGHT -> {
                spriteBox.rectangle.x = Application.ScreenX
                spriteBox.rectangle.y =
                    Random.nextInt(Application.ScreenY + spriteBox.rectangle.height) - spriteBox.rectangle.height
            }
            Direction4.DOWN -> {
                spriteBox.rectangle.x =
                    Random.nextInt(Application.ScreenX + spriteBox.rectangle.width) - spriteBox.rectangle.width
                spriteBox.rectangle.y = Application.ScreenY
            }
        }
    }

    fun growUp() {
        spriteBox.rectangle.x -= 4
        spriteBox.rectangle.y -= 4
        spriteBox.rectangle.width += 8
        spriteBox.rectangle.height += 8
    }

    fun growDown() {
        spriteBox.rectangle.x++
        spriteBox.rectangle.y++
        spriteBox.rectangle.width -= 2
        spriteBox.rectangle.height -= 2
    }

    companion object {

        private fun createEnemy(
            posX: Int, posY: Int, speed: Int, directionPattern: DirectionPattern, color: Color = Color.RED
        ): Enemy =
            Enemy(
                spriteBox = SpriteBox(
                    rectangle = Rectangle(posX, posY, 32, 32),
                    sprite = null,
                    color = color
                ),
                directionPattern,
                speed,
            )

        val enemies: List<Enemy> = listOf(
            createEnemy(-50, -50, 2, DirectionPattern.GOTOPLAYER),
            createEnemy(-50, -50, 2, DirectionPattern.GOTOPLAYER),
            createEnemy(-50, -50, 2, DirectionPattern.GOTOPLAYER),
            createEnemy(-50, -50, 2, DirectionPattern.GOTOPLAYER),
            createEnemy(-50, -50, 2, DirectionPattern.GOTOPLAYER),
            createEnemy(-50, -50, 2, DirectionPattern.GOTOPLAYER),
            createEnemy(-50, -50, 2, DirectionPattern.GOTOPLAYER),
            createEnemy(-50, -50, 2, DirectionPattern.GOTOPLAYER),
        )

        private fun createSpectators(count: Int, speed: Int): List<Enemy> {
            val spectators = mutableListOf<Enemy>()
            for (i in 0 until count) {
                spectators.add(createEnemy(-50, -50, speed, DirectionPattern.RANDOM, Color.darkGray))
                spectators.add(createEnemy(-50, -50, speed, DirectionPattern.RANDOM, Color.darkGray))
                spectators.add(createEnemy(-50, -50, speed, DirectionPattern.RANDOM, Color.darkGray))
                spectators.add(createEnemy(-50, -50, speed, DirectionPattern.RANDOM, Color.darkGray))
                spectators.add(createEnemy(-50, -50, speed, DirectionPattern.RANDOM, Color.darkGray))
                spectators.add(createEnemy(-50, -50, speed, DirectionPattern.RANDOM, Color.darkGray))
                spectators.add(createEnemy(-50, -50, speed, DirectionPattern.RANDOM, Color.darkGray))
                spectators.add(createEnemy(-50, -50, speed, DirectionPattern.RANDOM, Color.darkGray))
            }
            return spectators.toList()
        }

        val spectators: List<Enemy> =
            createSpectators(12, 3)
    }
}