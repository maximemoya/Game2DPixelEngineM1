package engine2D.core

import java.awt.Color
import java.awt.Rectangle

enum class ItemType {
    XPGEM
}

class Item(val spriteBox: SpriteBox, val type: ItemType) {

    fun setPosition(posX: Int, posY: Int) {
        spriteBox.rectangle.x = posX
        spriteBox.rectangle.y = posY
    }

    fun setPosition(enemy: Enemy) {
        spriteBox.rectangle.x = enemy.spriteBox.rectangle.x + enemy.spriteBox.rectangle.width / 2
        spriteBox.rectangle.y = enemy.spriteBox.rectangle.y + enemy.spriteBox.rectangle.height / 2
    }

    companion object {

        private fun createItem(posX: Int, posY: Int, type: ItemType): Item =
            Item(SpriteBox(Rectangle(posX, posY, 8, 8), null, Color.GREEN), type)

        private fun createItemsXPGEMList(): List<Item> {
            val itemsXPGEM = mutableListOf<Item>()
            for (i in Enemy.enemies.indices) {
                itemsXPGEM.add(createItem(-100, -100, ItemType.XPGEM))
            }
            return itemsXPGEM.toList()
        }

        val itemsXPGEM = createItemsXPGEMList()

    }

}