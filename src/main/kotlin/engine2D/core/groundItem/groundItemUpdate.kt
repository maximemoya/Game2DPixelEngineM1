package engine2D.core.groundItem

import engine2D.core.groundItem.functions.GroundItemCollision

fun groundItemsUpdate(){
    GroundItemCollision.collisionItemsWithPlayer()
}