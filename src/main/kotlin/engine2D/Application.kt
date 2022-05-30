package engine2D

import engine2D.core.player.Player
import java.awt.EventQueue
import javax.swing.JFrame

class Application : JFrame() {

    init {
        add(GameWindow())

        title = "GameEngineM1"
        setSize(ScreenX, ScreenY)
        setLocationRelativeTo(null)
        defaultCloseOperation = EXIT_ON_CLOSE
        addKeyListener(Player.keyListener)
    }

    companion object{
        val ScreenX = 900
        val ScreenY = 800

        fun startApplication() {
            EventQueue.invokeLater {
                val appGame = Application()
                appGame.isVisible = true
            }

        }
    }

}