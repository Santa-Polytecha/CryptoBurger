package santa.polytecha.cryptoburger

import java.io.Serializable
import java.math.BigInteger
import kotlin.collections.ArrayList

class Vector(
	private var _numberOfDimensions: Int = 2
) : Serializable, Iterable<BigInteger> {
	
	private var array: Array<BigInteger> = Array(_numberOfDimensions) { x -> BigInteger(x.toString()) }
	
	var numberOfDimensions: Int
		get() = _numberOfDimensions
		set(value) {
			_numberOfDimensions = value
			if (_numberOfDimensions != array.size) {
				val newArray = array.copyOf(_numberOfDimensions)
				newArray.map { x -> x ?: 0f }
				array = newArray as Array<BigInteger>
			}
		}
	
	var size: Int
		get() = _numberOfDimensions
		set(value) {
			numberOfDimensions = value
		}
	
	constructor(v: Vector): this(v.numberOfDimensions) {
		array = v.array.copyOf()
	}
	constructor(numberOfDimensions: Int, defaultValue: BigInteger): this(numberOfDimensions) {
		array.fill(defaultValue)
	}
	
	private fun checkIndex(index: Int): Boolean {
		return index in 0 until _numberOfDimensions
	}
	private fun checkIndexOrThrow(index: Int) {
		if (!checkIndex(index))
			throw IndexOutOfBoundsException("Index $index is out of bound (size = $_numberOfDimensions).")
	}
	
	fun getElement(index: Int): BigInteger {
		return array[index]
	}
	
	fun setElement(index: Int, value: BigInteger) {
		array[index] = value
	}
	
	fun map(f: (x: BigInteger) -> BigInteger): Vector {
		val v = clone()
		for (i in 0 until size)
			v.setElement(i, f(v.getElement(i)))
		return v
	}
	private fun mapAssign(f: (x: BigInteger) -> BigInteger) {
		for (i in 0 until size)
			setElement(i, f(getElement(i)))
	}
	
	fun mapIndexed(f: (i: Int, x: BigInteger) -> BigInteger): Vector {
		val v = clone()
		for (i in 0 until size)
			v.setElement(i, f(i, v.getElement(i)))
		return v
	}
	private fun mapIndexedAssign(f: (i: Int, x: BigInteger) -> BigInteger) {
		for (i in 0 until size)
			setElement(i, f(i, getElement(i)))
	}
	
	fun sum(): BigInteger {
		var result = BigInteger.ZERO
		for (x in this)
			result = result.add(x)
		return result
	}
	
	fun dotProduct(v: Vector): BigInteger {
		if (_numberOfDimensions != v.numberOfDimensions)
			throw IndexOutOfBoundsException("Cannot compute the cross product of vector of size $_numberOfDimensions with vector of size ${v.numberOfDimensions}.")
		
		return mapIndexed { i: Int, x: BigInteger -> x.multiply(v[i]) }.sum()
	}
	
	fun BigInteger.sqrt(n: BigInteger): BigInteger {
		var a = BigInteger.ONE
		var b = n.shiftRight(5).add(BigInteger.valueOf(8))
		while (b >= a) {
			val mid = a.add(b).shiftRight(1)
			if (mid.multiply(mid) > n)
				b = mid.subtract(BigInteger.ONE) else
				a = mid.add(BigInteger.ONE)
		}
		return a.subtract(BigInteger.ONE)
	}
	
	fun euclideanNorm(): BigInteger {
		return map { x: BigInteger -> x.pow(2) }.sum().sqrt(BigInteger.valueOf(2))
	}
	
	fun crossProduct(v: Vector): Vector {
		if (_numberOfDimensions != 3 || v.numberOfDimensions != 3)
			throw IndexOutOfBoundsException("Cannot apply cross product operation on vectors of size $_numberOfDimensions and ${v.numberOfDimensions}. Both vectors need to be of size 3.")
		
		val result = Vector(3)
		result[0] = this[1].multiply(v[2]) - this[2].multiply(v[1])
		result[1] = this[2].multiply(v[0]) - this[0].multiply(v[2])
		result[2] = this[0].multiply(v[1]) - this[1].multiply(v[0])
		return result
	}
	
	fun clone(): Vector {
		val clone = Vector(_numberOfDimensions)
		clone.array = array.copyOf()
		return clone
	}
	
	fun toList(): List<BigInteger> {
		val list = ArrayList<BigInteger>(_numberOfDimensions)
		list.addAll(this)
		return list
	}
	
	//region ITERABLE
	
	override fun iterator(): Iterator<BigInteger> {
		return array.iterator()
	}
	
	//endregion
	
	//region OPERATORS
	
	operator fun unaryPlus(): Vector = clone()
	
	operator fun unaryMinus(): Vector {
		return clone().map { x: BigInteger -> BigInteger.ZERO.subtract(x) }
	}
	
	operator fun inc(): Vector {
		return clone().map { x: BigInteger -> x.add(BigInteger.ONE) }
	}
	
	operator fun dec(): Vector {
		return clone().map { x: BigInteger -> x.subtract(BigInteger.ONE) }
	}
	
	operator fun plus(v: Vector): Vector {
		if (_numberOfDimensions != v.numberOfDimensions)
			throw IndexOutOfBoundsException("Cannot add vector of size $_numberOfDimensions with vector of size ${v.numberOfDimensions}.")
		
		return mapIndexed { i: Int, x: BigInteger -> x.add(v[i]) }
	}
	
	operator fun minus(v: Vector): Vector {
		if (_numberOfDimensions != v.numberOfDimensions)
			throw IndexOutOfBoundsException("Cannot subtract vector of size $_numberOfDimensions with vector of size ${v.numberOfDimensions}.")
		
		return mapIndexed { i: Int, x: BigInteger -> x.subtract(v[i]) }
	}
	
	operator fun times(v: Vector): Vector {
		if (_numberOfDimensions != v.numberOfDimensions)
			throw IndexOutOfBoundsException("Cannot multiply vector of size $_numberOfDimensions with vector of size ${v.numberOfDimensions}.")
		
		return mapIndexed { i: Int, x: BigInteger -> x.multiply(v[i]) }
	}
	operator fun times(scalar: BigInteger): Vector {
		return map { x: BigInteger -> x.multiply(scalar) }
	}
	operator fun times(scalar: Int): Vector {
		return map { x: BigInteger -> x.multiply(BigInteger(scalar.toString())) }
	}
	operator fun times(scalar: Float): Vector {
		return map { x: BigInteger -> x.multiply(BigInteger(scalar.toString())) }
	}
	operator fun times(scalar: Double): Vector {
		return map { x: BigInteger -> x.multiply(BigInteger(scalar.toString())) }
	}
	
	operator fun div(v: Vector): Vector {
		if (_numberOfDimensions != v.numberOfDimensions)
			throw IndexOutOfBoundsException("Cannot divide vector of size $_numberOfDimensions with vector of size ${v.numberOfDimensions}.")
		
		return mapIndexed { i: Int, x: BigInteger -> x.divide(v[i]) }
	}
	
	operator fun rem(v: Vector): Vector {
		if (_numberOfDimensions != v.numberOfDimensions)
			throw IndexOutOfBoundsException("Cannot compute remainder vector of size $_numberOfDimensions with vector of size ${v.numberOfDimensions}.")
		
		return mapIndexed { i: Int, x: BigInteger -> x.mod(v[i]) }
	}
	operator fun rem(bigInteger: BigInteger): Vector {
		return map { x: BigInteger -> x.mod(bigInteger) }
	}
	operator fun rem(scalar: Int): Vector {
		return map { x: BigInteger -> x.mod(BigInteger(scalar.toString())) }
	}
	operator fun rem(scalar: Float): Vector {
		return map { x: BigInteger -> x.mod(BigInteger(scalar.toString())) }
	}
	operator fun rem(scalar: Double): Vector {
		return map { x: BigInteger -> x.mod(BigInteger(scalar.toString())) }
	}
	
	operator fun contains(x: BigInteger): Boolean = array.contains(x)
	
	operator fun invoke(f: (x: BigInteger) -> BigInteger): Vector = map(f)
	
	operator fun plusAssign(v: Vector) {
		if (_numberOfDimensions != v.numberOfDimensions)
			throw IndexOutOfBoundsException("Cannot add vector of size $_numberOfDimensions with vector of size ${v.numberOfDimensions}.")
		
		mapIndexedAssign { i: Int, x: BigInteger -> x.add(v[i]) }
	}
	
	operator fun minusAssign(v: Vector) {
		if (_numberOfDimensions != v.numberOfDimensions)
			throw IndexOutOfBoundsException("Cannot subtract vector of size $_numberOfDimensions with vector of size ${v.numberOfDimensions}.")
		
		mapIndexedAssign { i: Int, x: BigInteger -> x.subtract(v[i]) }
	}
	
	operator fun timesAssign(v: Vector) {
		if (_numberOfDimensions != v.numberOfDimensions)
			throw IndexOutOfBoundsException("Cannot multiply vector of size $_numberOfDimensions with vector of size ${v.numberOfDimensions}.")
		
		mapIndexedAssign { i: Int, x: BigInteger -> x.multiply(v[i]) }
	}
	operator fun timesAssign(scalar: BigInteger) {
		mapAssign { x: BigInteger -> x.multiply(scalar) }
	}
	operator fun timesAssign(scalar: Int) {
		mapAssign { x: BigInteger -> x.multiply(BigInteger(scalar.toString())) }
	}
	operator fun timesAssign(scalar: Float) {
		mapAssign { x: BigInteger -> x.multiply(BigInteger(scalar.toString())) }
	}
	operator fun timesAssign(scalar: Double) {
		mapAssign { x: BigInteger -> x.multiply(BigInteger(scalar.toString())) }
	}
	
	operator fun divAssign(v: Vector) {
		if (_numberOfDimensions != v.numberOfDimensions)
			throw IndexOutOfBoundsException("Cannot divide vector of size $_numberOfDimensions with vector of size ${v.numberOfDimensions}.")
		
		mapIndexedAssign { i: Int, x: BigInteger -> x.divide(v[i]) }
	}
	
	operator fun remAssign(v: Vector) {
		if (_numberOfDimensions != v.numberOfDimensions)
			throw IndexOutOfBoundsException("Cannot compute remainder vector of size $_numberOfDimensions with vector of size ${v.numberOfDimensions}.")
		
		mapIndexedAssign { i: Int, x: BigInteger -> x.mod(v[i]) }
	}
	operator fun remAssign(bigInteger: BigInteger) {
		mapAssign { x: BigInteger -> x.mod(bigInteger) }
	}
	operator fun remAssign(scalar: Int) {
		mapAssign { x: BigInteger -> x.mod(BigInteger(scalar.toString())) }
	}
	operator fun remAssign(scalar: Float) {
		mapAssign { x: BigInteger -> x.mod(BigInteger(scalar.toString())) }
	}
	operator fun remAssign(scalar: Double) {
		mapAssign { x: BigInteger -> x.mod(BigInteger(scalar.toString())) }
	}
	
	operator fun get(index: Int): BigInteger = getElement(index)
	
	operator fun set(index: Int, value: BigInteger) = setElement(index, value)
	
	//endregion
	
	//region OBJECT OVERRIDES
	
	override fun equals(other: Any?): Boolean {
		if (this === other) return true
		if (other !is Vector) return false
		
		if (_numberOfDimensions != other._numberOfDimensions) return false
		if (!array.contentEquals(other.array)) return false
		
		return true
	}
	
	override fun hashCode(): Int {
		var result = _numberOfDimensions
		result = 31 * result + array.contentHashCode()
		return result
	}
	
	override fun toString(): String {
		return array.joinToString(prefix = "[", postfix = "]") { x: BigInteger -> x.toString() }
	}
	
	//endregion
}