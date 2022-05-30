package engine2D.core

import java.awt.Rectangle
import kotlin.math.PI
import kotlin.math.sqrt
import kotlin.test.Test
import kotlin.test.assertEquals

internal class UtilitiesTest {

    val spriteMovable = SpriteMovable(
        SpriteBox(Rectangle(5, 10, 10, 10)),
        10,
        (5 * PI / 3)
    )

    @Test
    fun testVariance() {

        val actual = Utilities.calculVariance(1,spriteMovable)
        val expected = ((sqrt(3.0)/2) to (1.0/2))
        assertEquals(expected, actual)
        assertEquals(123.0,(1.23 * 100))
        assertEquals(1.23,(123.0 / 100))
    }
}