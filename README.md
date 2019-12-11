# CrytoBurger

> Project presented by :
> * Antoine GANNE
> * Léa CHEMOUL
> * Valentin Berger

## Documentation

### Vector

The class `Vector` represents a mathematical vector, storing `BigInteger` as numbers. Using Kotlin flexibility, the mathematical operators have been override to be clearer:

```kotlin
// a = [2, 3, 4]
val a = Vector(BigInteger.valueOf(2), BigInteger.valueOf(3), BigInteger.valueOf(4))
// b = [5, 6, 7]
val b = Vector(BigInteger.valueOf(5), BigInteger.valueOf(6), BigInteger.valueOf(7))

a[0] // 2
a[1] // 3
a[2] // 4

a[0] = 2 // Set the value of 'x' to 2

a++ // a = [3, 4, 5]
a-- // a = [2, 3, 4]

-a // Return Vector [-2, -3, -4]

c = a.clone() // c = a, without reference
c *= 2 // c = [4, 6, 8]

// Multiply all values of a by 2:
val r = a.map { x: BigInteger -> x.multiply(BigInteger.valueOf(2)) }
// r = 2 * a
// a stays unchanged

a.sum() // sum(a) = 2 + 3 + 4 = 9

a.dotProduct(b) // a dot b = 56

a.crossProduct(b) // a x b = Vector [-3, 6, -3]

a.euclideanNorm() // ||a|| = sqrt(29)
```

### Burger

`Burger` is a type alias of `Vector`.

### Cook

The class `Cook` contains a function that can be defined during instantiation. This method takes a `Burger` as an argument and return another one.

```kotlin
val burger = Burger(BigInteger.valueOf(2), BigInteger.valueOf(3), BigInteger.valueOf(4))
val cook = Cook { x: Burger -> x.clone().inc() }

cook(burger) // Return Burger [3, 4, 5]
// One can also call cook.apply(burger).
```

### Kitchen

`Kitchen` represents an ordered list of cooks. Its main goal is to create a computation chain: When a kitchen is created, one can send it a burger, and the kitchen will apply the mathematical function on it from the first cook, then take the result and give it to the second cook, take the result and give it to the third cook, etc...

```kotlin
val c1 = Cook { x: Burger -> x.clone().inc() } // Add 1 to all element of the vector
val c2 = Cook { x: Burger -> x * 2 } // Multiply all elements by 2
val c3 = Cook { x: Burger -> x.rem(9) } // Modulo 9
val kitchen = Kitchen(c1, c2, c3)

// b = [2, 3, 4]
val b = Burger(BigInteger.valueOf(2), BigInteger.valueOf(3), BigInteger.valueOf(4))
kitchen(b) // Return Burger [6, 8, 1]
```
