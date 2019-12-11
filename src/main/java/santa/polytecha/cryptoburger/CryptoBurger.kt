package santa.polytecha.cryptoburger

import fr.polytech.berger.cryptaception.Paillier
import java.math.BigInteger

fun main() {
	val crypta = Paillier.randomCryptaception()
	val burger = Burger(BigInteger.ONE, BigInteger.ZERO, BigInteger.ONE)
	val cook = Cook { x: Burger -> Burger(x[0].add(x[1].multiply(x[2]))) }
	val encryptedBurger = crypta.encrypt(burger)
	println("burger = $burger")
	println("encryp = $encryptedBurger")
	
	// Bob send 'encryptedBurger' à Alice
	// Alice compute
	val R = cook(encryptedBurger)
	println("aliceR = $R")
	
	// Alice send R to Bob
	println("decryp = ${crypta.decryptToBurger(R)}")
}