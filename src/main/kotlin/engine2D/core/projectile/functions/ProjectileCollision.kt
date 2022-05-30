package engine2D.core.projectile.functions

import engine2D.core.enemy.Enemy
import engine2D.core.groundItem.GroundItem
import engine2D.core.projectile.Projectile

class ProjectileCollision {

    companion object {

        fun collisionWithEnemy(enemy: Enemy, i: Int) {
            Projectile.projectiles.forEach { projectile ->
                if (projectile.isActive){
                    if (enemy.spriteBox.rectangle.intersects(projectile.spriteBox.rectangle)) {
                        GroundItem.itemsXPGEM[i].setPosition(enemy)
                        enemy.respawnRandomOutScreen()
                        projectile.setPosition(-100, -100)
                    }
                }
            }
        }

    }

}