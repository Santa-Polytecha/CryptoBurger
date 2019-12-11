package santa.polytecha.cryptoburger

class Kitchen(initialCapacity: Int) : ArrayList<Cook>(initialCapacity) {
	
	constructor(): this(1)
	constructor(c: Collection<Cook>): this(c.size) {
		for (x in c)
			add(x)
	}
	constructor(vararg cooks: Cook): this(cooks.size) {
		for (x in cooks)
			add(x)
	}
	
	fun apply(x: Burger): Burger {
		var result = x.clone()
		for (cook in this)
			result = cook(result)
		return result
	}
	
	operator fun invoke(x: Burger): Burger = apply(x)
}