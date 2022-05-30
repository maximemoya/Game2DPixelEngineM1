package engine2D.core.enemy.functions

import engine2D.core.enemy.Enemy
import engine2D.core.player.functions.PlayerCollision
import engine2D.core.projectile.functions.ProjectileCollision

class EnemyMovement {

    companion object {

        fun enemiesMove() {
            for (i in Enemy.enemies.indices) {
                val enemy = Enemy.enemies[i]

                // ENEMY :
                enemy.move()

                // COLLISION WITH PLAYER :
                PlayerCollision.collisionWithEnemy(enemy)

                // COLLISION WITH PROJECTILES :
                ProjectileCollision.collisionWithEnemy(enemy, i)
            }
        }

        fun backgroundSpectatorsMove() {
            Enemy.spectators.forEach { spectator ->
                spectator.move()
            }
        }

    }

}
