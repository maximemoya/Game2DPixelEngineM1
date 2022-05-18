package engine2D

import engine2D.core.Enemy
import engine2D.core.Item
import engine2D.core.Player
import engine2D.core.Projectile
import java.awt.*
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.net.URL
import javax.swing.ImageIcon
import javax.swing.JPanel
import javax.swing.Timer
import kotlin.system.exitProcess

class Board : JPanel(), ActionListener {
    private val B_WIDTH = Application.ScreenX
    private val B_HEIGHT = Application.ScreenY
    private val DELAY = 30
    private var star: Image? = null
    private var timer: Timer? = null
    private var timer2: Timer? = null

    // -------------------
    // ACTION LISTENERS :
    object gameLogic : ActionListener {
        override fun actionPerformed(e: ActionEvent?) {

            // GAME OVER:
            if (Player.player.health <= 0) {
                println(
                    "\tGAME OVER\nYOUR SCORE: ${Player.player.fullExperience} => niveau ${Player.player.level} atteint !" +
                            "\nCONGRATULATIONS :D"
                )
                exitProcess(0)
            }

            // PLAYER:
            Player.player.move()

            // ENEMIES:
            for (i in Enemy.enemies.indices) {
                val enemy = Enemy.enemies[i]
                enemy.move()

                // PLAYER VS ENEMY :
                if (Player.player.spriteBox.rectangle.intersects(enemy.spriteBox.rectangle)) {
                    enemy.growUp()
                    Player.player.growDown()
                    enemy.respawnCornerInScreen()
                }

                // PROJECTILES VS ENEMY :
                Projectile.projectiles.forEach { projectile ->
                    if (enemy.spriteBox.rectangle.intersects(projectile.spriteBox.rectangle)) {
                        Item.itemsXPGEM[i].setPosition(enemy)
                        enemy.respawnCornerInScreen()
                        projectile.setPosition(-100, -100)
                    }
                }
            }

            // PROJECTILES:
            Projectile.projectiles.forEach { projectile ->
                projectile.move()
            }

            // ITEMS XP:
            Item.itemsXPGEM.forEach { itemXPGEM ->
                if (Player.player.spriteBox.rectangle.intersects(itemXPGEM.spriteBox.rectangle)) {
                    itemXPGEM.setPosition(-100, -100)
                    Player.player.experienceIncreased(10)
                }
            }

            // BACKGROUND SPECTATORS:
            Enemy.spectators.forEach { spectator ->
                spectator.move()
            }

        }
    }

    override fun actionPerformed(e: ActionEvent) {
        repaint()
    }

    // --------------
    // INITIALIZE :
    private fun loadImage() {
        val url: URL? = ClassLoader.getSystemResource("star.png")
        if(url != null){
            val ii = ImageIcon(url)
            star = ii.image
        }
    }

    private fun initBoard() {
        background = Color.BLACK
        preferredSize = Dimension(B_WIDTH, B_HEIGHT)

        // STAR:
        loadImage()

        // TIMER:
        timer = Timer(DELAY, this)
        timer!!.start()
        timer2 = Timer(DELAY, gameLogic)
        timer2!!.start()
    }

    init {
        initBoard()
    }

    // -------
    // PAINT:
    public override fun paintComponent(g: Graphics) {
        super.paintComponent(g)
        drawStar(g)
        drawSpectators(g)
        drawItems(g)
        drawEnemies(g)
        drawPlayer(g)
        drawProjectiles(g)
        drawPlayerStats(g)
    }

    private fun drawStar(g: Graphics) {
        g.drawImage(star, 200, 200, this)
        Toolkit.getDefaultToolkit().sync()
    }

    private fun drawPlayer(g: Graphics) {
        g.color = Player.player.spriteBox.color
        g.fillRect(
            Player.player.spriteBox.rectangle.x,
            Player.player.spriteBox.rectangle.y,
            Player.player.spriteBox.rectangle.width,
            Player.player.spriteBox.rectangle.height,
        )
    }

    private fun drawEnemies(g: Graphics) {
        for (enemy in Enemy.enemies) {
            g.color = enemy.spriteBox.color
            g.fillRect(
                enemy.spriteBox.rectangle.x,
                enemy.spriteBox.rectangle.y,
                enemy.spriteBox.rectangle.width,
                enemy.spriteBox.rectangle.height,
            )
        }
    }

    private fun drawProjectiles(g: Graphics) {
        for (projectile in Projectile.projectiles) {
            g.color = projectile.spriteBox.color
            g.fillRect(
                projectile.spriteBox.rectangle.x,
                projectile.spriteBox.rectangle.y,
                projectile.spriteBox.rectangle.width,
                projectile.spriteBox.rectangle.height,
            )
        }
    }

    private fun drawSpectators(g: Graphics) {
        for (spectator in Enemy.spectators) {
            g.color = spectator.spriteBox.color
            g.fillRect(
                spectator.spriteBox.rectangle.x,
                spectator.spriteBox.rectangle.y,
                spectator.spriteBox.rectangle.width,
                spectator.spriteBox.rectangle.height,
            )
        }
    }

    private fun drawItems(g: Graphics) {
        for (item in Item.itemsXPGEM) {
            g.color = item.spriteBox.color
            g.fillRect(
                item.spriteBox.rectangle.x,
                item.spriteBox.rectangle.y,
                item.spriteBox.rectangle.width,
                item.spriteBox.rectangle.height,
            )
        }
    }

    private fun drawPlayerStats(g: Graphics) {
        g.color = Color.WHITE
        g.font = Font("Cambria", Font.PLAIN, 18)
        g.drawString("PLAYER LIFE: ${Player.player.health}/${Player.player.healthTotal}", 10, 20)
        g.drawString("PLAYER EXP: ${Player.player.experience}/${Player.player.experienceToReach}", 10, 40)
        g.drawString("PLAYER LEVEL: ${Player.player.level}", 10, 60)
    }

}