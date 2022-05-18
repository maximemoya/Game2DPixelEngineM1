package engine2D

import engine2D.core.Player
import java.awt.EventQueue
import java.awt.event.ActionListener
import javax.swing.JFrame
import javax.swing.Timer

class Application : JFrame() {

    companion object{
        val ScreenX = 900
        val ScreenY = 800

        fun startApplication() {
            EventQueue.invokeLater {
                val ex = Application()
                ex.isVisible = true
                ex.addKeyListener(Player.keyListener)

                val time = Timer(100, actionListener)
                time.start()
            }

        }
    }

    init {
        initUI()
    }

    private fun initUI() {
        add(Board())

        setSize(ScreenX, ScreenY + 50)

        title = "Application"
        defaultCloseOperation = EXIT_ON_CLOSE
        setLocationRelativeTo(null)
    }

}

val actionListener = ActionListener {
}