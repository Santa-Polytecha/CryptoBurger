package santa.polytecha.cryptoburger

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.math.BigInteger

internal class CookTest {
	
	@Test
	fun apply() {
		val burger = Burger(BigInteger.valueOf(2), BigInteger.valueOf(3), BigInteger.valueOf(4))
		val cook = Cook { x: Burger -> x.clone().inc() }
		
		assertEquals(Vector(BigInteger.valueOf(3), BigInteger.valueOf(4), BigInteger.valueOf(5)), cook(burger))
	}
}