package engine2D.core.player.functions

import engine2D.core.enemy.Enemy
import engine2D.core.player.Player

class PlayerCollision {

    companion object {

        fun collisionWithEnemy(enemy : Enemy){
            if (Player.player.spriteBox.rectangle.intersects(enemy.spriteBox.rectangle)) {
                enemy.growUp()
                Player.player.growDown()
                enemy.respawnRandomOutScreen()
            }
        }

    }

}