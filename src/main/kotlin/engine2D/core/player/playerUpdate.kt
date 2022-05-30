package engine2D.core.player

fun playerUpdate(){
    Player.player.checkIsDead()
    Player.player.move()
}