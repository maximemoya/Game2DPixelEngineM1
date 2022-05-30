package engine2D.core.groundItem.functions

import engine2D.core.groundItem.GroundItem
import engine2D.core.player.Player

class GroundItemCollision {

    companion object {

        fun collisionItemsWithPlayer(){
            GroundItem.itemsXPGEM.forEach { itemXPGEM ->
                if (Player.player.spriteBox.rectangle.intersects(itemXPGEM.spriteBox.rectangle)) {
                    itemXPGEM.setPosition(-100, -100)
                    Player.player.experienceIncreased(10)
                }
            }
        }

    }

}