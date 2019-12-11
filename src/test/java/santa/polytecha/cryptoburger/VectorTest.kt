package santa.polytecha.cryptoburger

import org.junit.jupiter.api.BeforeEach

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.math.BigInteger

internal class VectorTest {
	
	lateinit var a: Vector
	lateinit var b: Vector
	lateinit var c: Vector

	@BeforeEach
	fun setUp() {
		a = Vector(BigInteger.valueOf(2), BigInteger.valueOf(3), BigInteger.valueOf(4))
		b = Vector(BigInteger.valueOf(5), BigInteger.valueOf(6), BigInteger.valueOf(7))
		c = Vector(BigInteger.valueOf(-3), BigInteger.valueOf(6), BigInteger.valueOf(-3))
	}
	
	@Test
	fun get() {
		assertEquals(BigInteger.valueOf(2), a[0])
		assertEquals(BigInteger.valueOf(3), a[1])
		assertEquals(BigInteger.valueOf(4), a[2])
		
		assertEquals(BigInteger.valueOf(5), b[0])
		assertEquals(BigInteger.valueOf(6), b[1])
		assertEquals(BigInteger.valueOf(7), b[2])
		
		assertEquals(BigInteger.valueOf(-3), c[0])
		assertEquals(BigInteger.valueOf(6), c[1])
		assertEquals(BigInteger.valueOf(-3), c[2])
	}
	
	@Test
	fun set() {
		a[0] = BigInteger.valueOf(0)
		a[2] = BigInteger.valueOf(6)
		assertEquals(BigInteger.valueOf(0), a[0])
		assertEquals(BigInteger.valueOf(3), a[1])
		assertEquals(BigInteger.valueOf(6), a[2])
	}
	
	@Test
	fun map() {
		val r = a.map { x -> x.add(BigInteger.ONE) }
		
		// a must stay unchanged
		assertEquals(BigInteger.valueOf(2), a[0])
		assertEquals(BigInteger.valueOf(3), a[1])
		assertEquals(BigInteger.valueOf(4), a[2])
		
		// r = a + 1
		assertEquals(BigInteger.valueOf(3), r[0])
		assertEquals(BigInteger.valueOf(4), r[1])
		assertEquals(BigInteger.valueOf(5), r[2])
	}
	
	@Test
	fun sum() {
		assertEquals(BigInteger.valueOf(9), a.sum())
		assertEquals(BigInteger.valueOf(18), b.sum())
		assertEquals(BigInteger.valueOf(0), c.sum())
	}
	
	@Test
	fun dotProduct() {
		assertEquals(BigInteger.valueOf(56), a.dotProduct(b))
	}
	
	@Test
	fun euclideanNorm() {
		assertEquals(kotlin.math.sqrt(29.0), a.euclideanNorm())
	}
	
	@Test
	fun crossProduct() {
		assertEquals(c, a.crossProduct(b))
	}
}