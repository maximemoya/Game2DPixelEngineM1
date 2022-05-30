package engine2D.core.groundItem

import engine2D.core.enemy.Enemy
import engine2D.core.SpriteBox
import java.awt.Color
import java.awt.Rectangle

enum class ItemType {
    XPGEM
}

class GroundItem(val spriteBox: SpriteBox, val type: ItemType) {

    fun setPosition(posX: Int, posY: Int) {
        spriteBox.rectangle.x = posX
        spriteBox.rectangle.y = posY
    }

    fun setPosition(enemy: Enemy) {
        spriteBox.rectangle.x = enemy.spriteBox.rectangle.x + enemy.spriteBox.rectangle.width / 2
        spriteBox.rectangle.y = enemy.spriteBox.rectangle.y + enemy.spriteBox.rectangle.height / 2
    }

    companion object {

        private fun createItem(posX: Int, posY: Int, type: ItemType): GroundItem =
            GroundItem(SpriteBox(Rectangle(posX, posY, 8, 8), null, Color.GREEN), type)

        private fun createItemsXPGEMList(): List<GroundItem> {
            val itemsXPGEM = mutableListOf<GroundItem>()
            for (i in Enemy.enemies.indices) {
                itemsXPGEM.add(createItem(-100, -100, ItemType.XPGEM))
            }
            return itemsXPGEM.toList()
        }

        val itemsXPGEM = createItemsXPGEMList()

    }

}