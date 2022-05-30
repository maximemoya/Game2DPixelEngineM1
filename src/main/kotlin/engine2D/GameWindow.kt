package engine2D

import engine2D.core.enemy.Enemy
import engine2D.core.groundItem.GroundItem
import engine2D.core.logicalUpdates
import engine2D.core.player.Player
import engine2D.core.projectile.Projectile
import java.awt.*
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.io.InputStream
import javax.imageio.ImageIO
import javax.swing.ImageIcon
import javax.swing.JPanel
import javax.swing.Timer

class GameWindow : JPanel(), ActionListener {
    private val SCREEN_WIDTH = Application.ScreenX
    private val SCREEN_HEIGHT = Application.ScreenY
    private val DELAY_TIMER = 30
    private var timerGraphicDrawing: Timer? = null
    private var timerLogicalSystem: Timer? = null
    private var star: Image? = null

    // -------------------
    // ACTION LISTENERS :

    object GameLogic : ActionListener {
        override fun actionPerformed(e: ActionEvent?) {
            logicalUpdates()
        }
    }

    override fun actionPerformed(e: ActionEvent) {
        repaint()
    }

    // --------------
    // INITIALIZE :

    private fun loadImage() {

        try {
//            val inputStream: InputStream = BufferedInputStream(FileInputStream("src/main/resources/star.png"))
            val inputStream: InputStream? = ClassLoader.getSystemResourceAsStream("star.png")
//            val inputStream: InputStream? = this::class.java.classLoader.getResourceAsStream("star.png")
            val image = ImageIO.read(inputStream)
            star = ImageIcon(image).image
        } catch (ignored: Exception) {
            ignored.printStackTrace()
        }

    }

    private fun initBoard() {
        background = Color.BLACK
        preferredSize = Dimension(SCREEN_WIDTH, SCREEN_HEIGHT)

        // STAR:
        loadImage()

        // TIMER:
        timerGraphicDrawing = Timer(DELAY_TIMER, this)
        timerGraphicDrawing!!.start()
        timerLogicalSystem = Timer(DELAY_TIMER, GameLogic)
        timerLogicalSystem!!.start()
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
        Projectile.projectiles.forEach { projectile ->
            if (projectile.isActive){
                g.color = projectile.spriteBox.color
                g.fillRect(
                    projectile.spriteBox.rectangle.x,
                    projectile.spriteBox.rectangle.y,
                    projectile.spriteBox.rectangle.width,
                    projectile.spriteBox.rectangle.height,
                )
            }
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
        for (item in GroundItem.itemsXPGEM) {
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