package engine2D.core.enemy

import engine2D.core.enemy.functions.EnemyMovement

fun enemiesUpdate(){
    EnemyMovement.enemiesMove()
}

fun backgroundSpectatorsUpdate(){
    EnemyMovement.backgroundSpectatorsMove()
}