package santa.polytecha.cryptoburger

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import java.math.BigInteger

internal class KitchenTest {
	
	lateinit var c1: Cook
	lateinit var c2: Cook
	lateinit var c3: Cook
	lateinit var kitchen: Kitchen
	
	@BeforeEach
	fun setUp() {
		c1 = Cook { x: Burger -> x.clone().inc() } // Add 1 to all element of the vector
		c2 = Cook { x: Burger -> x * 2 } // Multiply all elements by 2
		c3 = Cook { x: Burger -> x.rem(9) } // Modulo 9
		kitchen = Kitchen(c1, c2, c3)
	}

	@Test
	fun apply() {
		val b = Burger(BigInteger.valueOf(2), BigInteger.valueOf(3), BigInteger.valueOf(4))
		assertEquals(Burger(BigInteger.valueOf(6), BigInteger.valueOf(8), BigInteger.valueOf(1)), kitchen(b))
	}
}