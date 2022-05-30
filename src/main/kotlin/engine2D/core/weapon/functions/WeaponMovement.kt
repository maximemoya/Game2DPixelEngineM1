package engine2D.core.weapon.functions

import engine2D.core.projectile.Projectile
import engine2D.core.weapon.Weapon

class WeaponMovement {

    companion object {

        fun weaponsMove() {
            Weapon.weapons.values.forEach { weapon ->

                // projectiles init :
                if (weapon.timeCounter == 0 || weapon.timeCounter >= weapon.timeBetweenShot) {
                    weapon.isReady = true
                    while (weapon.indexProjectileInit < weapon.projectileAmount) {
                        val projectile = Projectile.projectiles.find { !it.isActive }
                        if (projectile != null) {

                            weapon.projectileInit(projectile)
                        } else {
                            break
                        }
                    }
                    println("projectiles actives : ${Projectile.projectiles.count { it.isActive }} / ${Projectile.projectiles.count()}")
                    weapon.indexProjectileInit = 0
                    weapon.timeCounter = 0
                }

                weapon.timeCounter++
            }

            // projectiles move :
            Projectile.projectiles.forEach {
                if (it.isActive) {
                    it.move()
                }
            }

        }

    }

}