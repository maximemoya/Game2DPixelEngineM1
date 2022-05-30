package engine2D.core

import engine2D.core.enemy.backgroundSpectatorsUpdate
import engine2D.core.enemy.enemiesUpdate
import engine2D.core.groundItem.groundItemsUpdate
import engine2D.core.player.playerUpdate
import engine2D.core.weapon.weaponsShotUpdate

fun logicalUpdates(){
    // PLAYER / GAME OVER:
    playerUpdate()
    // ENEMIES:
    enemiesUpdate()
    // WEAPON SHOT:
    weaponsShotUpdate()
//    projectilesUpdate()
    // ITEMS XP:
    groundItemsUpdate()
    // BACKGROUND SPECTATORS:
    backgroundSpectatorsUpdate()
}