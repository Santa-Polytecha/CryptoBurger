package santa.polytecha.cryptoburger

import fr.polytech.berger.cryptaception.Cryptaception

typealias Burger = Vector

fun <PK, SK> Cryptaception<PK, SK>.encrypt(vector: Vector): Vector {
	val result = Vector(vector.numberOfDimensions)
	for (i in 0 until vector.size)
		result[i] = encrypt(vector[i])
	return result
}

fun <PK, SK> Cryptaception<PK, SK>.decryptToVector(vector: Vector): Vector {
	val result = Vector(vector.numberOfDimensions)
	for (i in 0 until vector.size)
		result[i] = decryptToBigInteger(vector[i])
	return result
}

fun <PK, SK> Cryptaception<PK, SK>.decryptToBurger(burger: Burger): Burger = decryptToVector(burger)
