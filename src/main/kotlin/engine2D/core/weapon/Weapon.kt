package engine2D.core.weapon

import engine2D.core.SpriteBox
import engine2D.core.projectile.Projectile
import engine2D.core.projectile.DirectionProjectile
import java.awt.Color
import java.awt.Rectangle
import kotlin.math.PI


enum class WeaponEnum {
    smg, shotgun
}

class Weapon(
    val timeBetweenShot: Int,
    val projectileAmount: Int,
    private val projectileInitMethod: (projectile: Projectile, index: Int) -> Unit,
) {

    var timeCounter = 0
    var indexProjectileInit = 0
    var isReady = false

    fun projectileInit(projectile: Projectile) {
        projectileInitMethod(projectile, indexProjectileInit)
        indexProjectileInit++
    }

    companion object {

        val SMG1 = Weapon(
            timeBetweenShot = 25,
            projectileAmount = 2
        ) { projectile, index ->
            projectile.resetParams(
                spriteBox = SpriteBox(Rectangle(5,5), color = Color.WHITE),
                speed = 6,
                typeDirection = DirectionProjectile.SHOT_PLAYER_DIRECTION
            )
            when (index) {
                1 -> {
                    projectile.spriteBox.color = Color.YELLOW
                }
                else -> {
                    projectile.speed = 8
                }
            }
            projectile.repopFromPlayer()
        }
        val SMG2 = Weapon(
            timeBetweenShot = 30,
            projectileAmount = 3
        ) { projectile, index ->
            projectile.resetParams(
                spriteBox = SpriteBox(Rectangle(5,5), color = Color.WHITE),
                speed = 6,
                typeDirection = DirectionProjectile.SHOT_PLAYER_DIRECTION
            )
            when (index) {
                1 -> {
                    projectile.spriteBox.color = Color.YELLOW
                }
                2 -> {
                    projectile.speed = 8
                    projectile.variance = 15
                }
                else -> {
                    projectile.speed = 10
                    projectile.spriteBox.color = Color.YELLOW
                }
            }
            projectile.repopFromPlayer()
        }
        val SMG3 = Weapon(
            timeBetweenShot = 35,
            projectileAmount = 4
        ) { projectile, index ->
            projectile.resetParams(
                spriteBox = SpriteBox(Rectangle(5,5), color = Color.WHITE),
                speed = 6,
                typeDirection = DirectionProjectile.SHOT_PLAYER_DIRECTION
            )
            when (index) {
                1 -> {
                    projectile.spriteBox.color = Color.YELLOW
                }
                2 -> {
                    projectile.speed = 8
                    projectile.variance = 15
                }
                3 -> {
                    projectile.speed = 10
                    projectile.spriteBox.color = Color.YELLOW
                }
                else -> {
                    projectile.speed = 12
                    projectile.variance = -15
                }
            }
            projectile.repopFromPlayer()
        }
        val SMG4 = Weapon(
            timeBetweenShot = 45,
            projectileAmount = 5
        ) { projectile, index ->
            projectile.resetParams(
                spriteBox = SpriteBox(Rectangle(5,5), color = Color.WHITE),
                speed = 7,
                typeDirection = DirectionProjectile.SHOT_PLAYER_DIRECTION
            )
            when (index) {
                1 -> {
                    projectile.spriteBox.color = Color.YELLOW
                }
                2 -> {
                    projectile.speed = 9
                    projectile.variance = 15
                }
                3 -> {
                    projectile.speed = 11
                    projectile.spriteBox.color = Color.YELLOW
                }
                4 -> {
                    projectile.speed = 13
                    projectile.variance = -15
                }
                else -> {
                    projectile.speed = 15
                    projectile.spriteBox.color = Color.YELLOW
                }
            }
            projectile.repopFromPlayer()
        }

        val weapons = hashMapOf(
            WeaponEnum.smg to SMG1
        )
    }
}