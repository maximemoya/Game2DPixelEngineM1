package engine2D.core.projectile.functions

import engine2D.core.projectile.Projectile

class ProjectileMovement {

    companion object {

        fun projectilesMove(){
            Projectile.projectiles.forEach { projectile ->
                projectile.move()
            }
        }

    }

}