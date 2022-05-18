package engine2D.core

import engine2D.Application
import engine2D.core.Player.Companion.keyMove.*
import java.awt.Color
import java.awt.Rectangle
import java.awt.event.KeyEvent
import java.awt.event.KeyListener
import kotlin.math.PI

class Player(
    val spriteBox: SpriteBox,
    val speed: Int,
) {

    var health = 100
    val healthTotal = 100
    var experience = 0
    var experienceToReach = 50
    var fullExperience = 0
    var level = 0
    var directionAngle: Double = DirectionInRadian.LEFT
    val keyMoves = hashMapOf(
        LEFT to false,
        RIGHT to false,
        UP to false,
        DOWN to false,
    )

    fun move() {

        if (spriteBox.rectangle.x < 0) {
            spriteBox.rectangle.x = 1
        }
//        else if (spriteBox.rectangle.x > Application.ScreenY - spriteBox.rectangle.width){
//            spriteBox.rectangle.x = Application.ScreenY - spriteBox.rectangle.width - 1
//        }

        if (spriteBox.rectangle.y < 0) {
            spriteBox.rectangle.y = 1
        }
//        else if (spriteBox.rectangle.x > Application.ScreenY - spriteBox.rectangle.width){
//            spriteBox.rectangle.x = Application.ScreenY - spriteBox.rectangle.width - 1
//        }

        if (keyMoves[LEFT] == true) {
            spriteBox.rectangle.x -= speed
            directionAngle = DirectionInRadian.LEFT
        } else if (keyMoves[RIGHT] == true) {
            spriteBox.rectangle.x += speed
            directionAngle = DirectionInRadian.RIGHT
        }

        if (keyMoves[UP] == true) {
            spriteBox.rectangle.y -= speed
            directionAngle = DirectionInRadian.UP
        } else if (keyMoves[DOWN] == true) {
            spriteBox.rectangle.y += speed
            directionAngle = DirectionInRadian.DOWN
        }

    }

    fun levelUp() {
        level++
        experienceToReach += 50
        experience = 0
        setHealthToFull()
        when (level) {
            1 -> {
                Projectile.projectiles.add(
                    Projectile.createProjectile(
                        posX = player.spriteBox.rectangle.x + (player.spriteBox.rectangle.width / 2),
                        posY = player.spriteBox.rectangle.y + (player.spriteBox.rectangle.height / 2),
                        speed = player.speed + 1,
                        angleInRadian = (player.directionAngle + PI),
                        type = TypeProjectile.SHOT_INVERSE_PLAYER_DIRECTION,
                        timeBetweenShot = 1000,
                    )
                )
            }
            2 -> {
                Projectile.projectiles.add(
                    Projectile.createProjectile(
                        posX = player.spriteBox.rectangle.x + (player.spriteBox.rectangle.width / 2),
                        posY = player.spriteBox.rectangle.y + (player.spriteBox.rectangle.height / 2),
                        speed = player.speed + 1,
                        angleInRadian = (player.directionAngle + PI / 2),
                        type = TypeProjectile.SHOT_90DEGREE_PLAYER_DIRECTION,
                        timeBetweenShot = 1000,
                    )
                )
            }
            3 -> {
                Projectile.projectiles.add(
                    Projectile.createProjectile(
                        posX = player.spriteBox.rectangle.x + (player.spriteBox.rectangle.width / 2),
                        posY = player.spriteBox.rectangle.y + (player.spriteBox.rectangle.height / 2),
                        speed = player.speed + 1,
                        angleInRadian = (player.directionAngle + (3 * PI / 2)),
                        type = TypeProjectile.SHOT_270DEGREE_PLAYER_DIRECTION,
                        timeBetweenShot = 1000,
                    )
                )
            }
            4 -> {
                Projectile.projectiles.add(
                    Projectile.createProjectile(
                        posX = player.spriteBox.rectangle.x + (player.spriteBox.rectangle.width / 2),
                        posY = player.spriteBox.rectangle.y + (player.spriteBox.rectangle.height / 2),
                        speed = player.speed + 1,
                        angleInRadian = DirectionInRadian.UP_LEFT,
                        type = TypeProjectile.SHOT_UP_LEFT,
                        timeBetweenShot = 1000,
                    )
                )
                Projectile.projectiles.add(
                    Projectile.createProjectile(
                        posX = player.spriteBox.rectangle.x + (player.spriteBox.rectangle.width / 2),
                        posY = player.spriteBox.rectangle.y + (player.spriteBox.rectangle.height / 2),
                        speed = player.speed + 1,
                        angleInRadian = DirectionInRadian.UP_RIGHT,
                        type = TypeProjectile.SHOT_UP_RIGHT,
                        timeBetweenShot = 1000,
                    )
                )
                Projectile.projectiles.add(
                    Projectile.createProjectile(
                        posX = player.spriteBox.rectangle.x + (player.spriteBox.rectangle.width / 2),
                        posY = player.spriteBox.rectangle.y + (player.spriteBox.rectangle.height / 2),
                        speed = player.speed + 1,
                        angleInRadian = DirectionInRadian.DOWN_LEFT,
                        type = TypeProjectile.SHOT_DOWN_LEFT,
                        timeBetweenShot = 1000,
                    )
                )
                Projectile.projectiles.add(
                    Projectile.createProjectile(
                        posX = player.spriteBox.rectangle.x + (player.spriteBox.rectangle.width / 2),
                        posY = player.spriteBox.rectangle.y + (player.spriteBox.rectangle.height / 2),
                        speed = player.speed + 1,
                        angleInRadian = DirectionInRadian.DOWN_RIGHT,
                        type = TypeProjectile.SHOT_DOWN_RIGHT,
                        timeBetweenShot = 1000,
                    )
                )
            }
            else -> {}
        }
        Projectile.projectiles.forEach { it.resetTimer() }

    }

    fun setHealthToFull() {
        health = healthTotal
        while (spriteBox.rectangle.width < 80) {
            spriteBox.rectangle.x -= 1
            spriteBox.rectangle.y -= 1
            spriteBox.rectangle.width += 2
            spriteBox.rectangle.height += 2
        }
    }

    fun growUp(coef: Int = 2) {
        if (spriteBox.rectangle.width < 80) {
            spriteBox.rectangle.x -= coef
            spriteBox.rectangle.y -= coef
            spriteBox.rectangle.width += coef * 2
            spriteBox.rectangle.height += coef * 2
        }

    }

    fun growDown(coef: Int = 2) {
        if (spriteBox.rectangle.width > 4) {
            spriteBox.rectangle.x += coef
            spriteBox.rectangle.y += coef
            spriteBox.rectangle.width -= coef * 2
            spriteBox.rectangle.height -= coef * 2
        }
        health -= 5

    }

    fun experienceIncreased(amount: Int) {
        fullExperience += amount
        experience += amount
        if (experience >= experienceToReach) {
            levelUp()
        }
    }

    companion object {

        enum class keyMove {
            LEFT, RIGHT, UP, DOWN
        }

        val player = Player(
            spriteBox = SpriteBox(
                rectangle = Rectangle(Application.ScreenX / 2, Application.ScreenY / 2, 80, 80),
                sprite = null,
                color = Color.BLUE,
            ),
            speed = 5
        )

        val keyListener = object : KeyListener {
            override fun keyTyped(e: KeyEvent?) {
            }

            override fun keyPressed(e: KeyEvent?) {
                when (e?.keyCode) {
                    KeyEvent.VK_UP -> {
                        player.keyMoves[UP] = true
                    }
                    KeyEvent.VK_DOWN -> {
                        player.keyMoves[DOWN] = true
                    }
                    KeyEvent.VK_LEFT -> {
                        player.keyMoves[LEFT] = true
                    }
                    KeyEvent.VK_RIGHT -> {
                        player.keyMoves[RIGHT] = true
                    }
                    else -> {}
                }
            }

            override fun keyReleased(e: KeyEvent?) {
                when (e?.keyCode) {
                    KeyEvent.VK_UP -> {
                        player.keyMoves[UP] = false
                    }
                    KeyEvent.VK_DOWN -> {
                        player.keyMoves[DOWN] = false
                    }
                    KeyEvent.VK_LEFT -> {
                        player.keyMoves[LEFT] = false
                    }
                    KeyEvent.VK_RIGHT -> {
                        player.keyMoves[RIGHT] = false
                    }
                    else -> {}
                }
            }
        }
    }
}