package santa.polytecha.cryptoburger

data class Cook(
	var map: (x: Burger) -> Burger
) {
	fun apply(x: Burger): Burger = map(x)
	
	operator fun invoke(x: Burger): Burger = apply(x)
}