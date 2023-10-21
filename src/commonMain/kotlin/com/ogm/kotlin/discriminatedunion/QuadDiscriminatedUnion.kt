package com.ogm.kotlin.discriminatedunion

/**
 * A discriminated union with 3 possible types
 * @see <a href="https://en.wikipedia.org/wiki/Tagged_union">Tagged Union</a>
 * @see [DiscriminatedUnion] for a discriminated union with 2 types
 */
@JvmInline
value class QuadDiscriminatedUnion<T1, T2, T3, T4> private constructor(
	private val value: Value<T1, T2, T3, T4>
) : IQuadDiscriminatedUnion<T1, T2, T3, T4> {
	@Suppress("IMPLICIT_CAST_TO_ANY")
	val unionValue
		get() = when (value) {
			is Value1<T1, T2, T3, T4> -> value.value
			is Value2<T1, T2, T3, T4> -> value.value
			is Value3<T1, T2, T3, T4> -> value.value
			is Value4<T1, T2, T3, T4> -> value.value
		}

	override val firstOrNull: T1?
		get() = when (value) {
			is Value1<T1, T2, T3, T4> -> value.value
			else -> null
		}

	override val secondOrNull: T2?
		get() = when (value) {
			is Value2<T1, T2, T3, T4> -> value.value
			else -> null
		}

	override val thirdOrNull: T3?
		get() = when (value) {
			is Value3<T1, T2, T3, T4> -> value.value
			else -> null
		}

	override val fourthOrNull: T4?
		get() = when (value) {
			is Value4<T1, T2, T3, T4> -> value.value
			else -> null
		}

	override val isFirstType: Boolean
		get() = value is Value1<T1, T2, T3, T4>

	override val isSecondType: Boolean
		get() = value is Value2<T1, T2, T3, T4>

	override val isThirdType: Boolean
		get() = value is Value3<T1, T2, T3, T4>

	override val isFourthType: Boolean
		get() = value is Value3<T1, T2, T3, T4>

	override val position: Int
		get() = when (value) {
			is Value1<T1, T2, T3, T4> -> 1
			is Value2<T1, T2, T3, T4> -> 2
			is Value3<T1, T2, T3, T4> -> 3
			is Value4<T1, T2, T3, T4> -> 4
		}

	inline fun firstOrThrow(errorMessage: () -> String = { "QuadDiscriminatedUnion is type $position" }): T1 {
		check(isFirstType, errorMessage)

		@Suppress("UNCHECKED_CAST")
		return firstOrNull as T1
	}

	inline fun secondOrThrow(errorMessage: () -> String = { "QuadDiscriminatedUnion is type $position" }): T2 {
		check(isSecondType, errorMessage)

		@Suppress("UNCHECKED_CAST")
		return secondOrNull as T2
	}

	inline fun thirdOrThrow(errorMessage: () -> String = { "QuadDiscriminatedUnion is type $position" }): T3 {
		check(isThirdType, errorMessage)

		@Suppress("UNCHECKED_CAST")
		return thirdOrNull as T3
	}

	inline fun fourthOrThrow(errorMessage: () -> String = { "QuadDiscriminatedUnion is type $position" }): T4 {
		check(isFourthType, errorMessage)

		@Suppress("UNCHECKED_CAST")
		return fourthOrNull as T4
	}

	override fun firstOr(defaultValue: T1): T1 {
		return when (value) {
			is Value1<T1, T2, T3, T4> -> value.value
			else -> defaultValue
		}
	}

	inline fun firstOrGet(block: () -> T1): T1 {
		return if (isFirstType) {
			@Suppress("UNCHECKED_CAST")
			firstOrNull as T1
		} else {
			block()
		}
	}

	override fun secondOr(defaultValue: T2): T2 {
		return when (value) {
			is Value2<T1, T2, T3, T4> -> value.value
			else -> defaultValue
		}
	}

	inline fun secondOrGet(block: () -> T2): T2 {
		return if (isSecondType) {
			@Suppress("UNCHECKED_CAST")
			secondOrNull as T2
		} else {
			block()
		}
	}

	override fun thirdOr(defaultValue: T3): T3 {
		return when (value) {
			is Value3<T1, T2, T3, T4> -> value.value
			else -> defaultValue
		}
	}

	inline fun thirdOrGet(block: () -> T3): T3 {
		return if (isThirdType) {
			@Suppress("UNCHECKED_CAST")
			thirdOrNull as T3
		} else {
			block()
		}
	}

	override fun fourthOr(defaultValue: T4): T4 {
		return when (value) {
			is Value4<T1, T2, T3, T4> -> value.value
			else -> defaultValue
		}
	}

	inline fun fourthOrGet(block: () -> T4): T4 {
		return if (isThirdType) {
			@Suppress("UNCHECKED_CAST")
			fourthOrNull as T4
		} else {
			block()
		}
	}

	/*inline fun takeIf(
		type1Predicate: (T1) -> Boolean,
		type2Predicate: (T2) -> Boolean,
		type3Predicate: (T3) -> Boolean,
	): QuadDiscriminatedUnion<T1, T2, T3, T4>? {
		return if (isFirstType) {
			@Suppress("UNCHECKED_CAST")
			takeIf { type1Predicate(firstOrNull as T1) }
		} else if (isSecondType) {
			@Suppress("UNCHECKED_CAST")
			takeIf { type2Predicate(secondOrNull as T2) }
		} else {
			@Suppress("UNCHECKED_CAST")
			takeIf { type3Predicate(thirdOrNull as T3) }
		}
	}

	inline fun takeIfFirst(
		predicate: (T1) -> Boolean,
	): QuadDiscriminatedUnion<T1, T2, T3, T4>? {
		return if (isFirstType) {
			@Suppress("UNCHECKED_CAST")
			takeIf { predicate(firstOrNull as T1) }
		} else {
			this
		}
	}

	inline fun takeIfSecond(
		predicate: (T2) -> Boolean,
	): QuadDiscriminatedUnion<T1, T2, T3, T4>? {
		return if (isSecondType) {
			@Suppress("UNCHECKED_CAST")
			takeIf { predicate(secondOrNull as T2) }
		} else {
			this
		}
	}

	inline fun takeIfThird(
		predicate: (T3) -> Boolean,
	): QuadDiscriminatedUnion<T1, T2, T3, T4>? {
		return if (isThirdType) {
			@Suppress("UNCHECKED_CAST")
			takeIf { predicate(thirdOrNull as T3) }
		} else {
			this
		}
	}

	inline fun takeUnless(
		type1Predicate: (T1) -> Boolean,
		type2Predicate: (T2) -> Boolean,
		type3Predicate: (T3) -> Boolean,
	): QuadDiscriminatedUnion<T1, T2, T3, T4>? {
		return if (isFirstType) {
			@Suppress("UNCHECKED_CAST")
			takeUnless { type1Predicate(firstOrNull as T1) }
		} else if (isSecondType) {
			@Suppress("UNCHECKED_CAST")
			takeUnless { type2Predicate(secondOrNull as T2) }
		} else {
			@Suppress("UNCHECKED_CAST")
			takeUnless { type3Predicate(thirdOrNull as T3) }
		}
	}

	inline fun takeUnlessFirst(
		predicate: (T1) -> Boolean,
	): QuadDiscriminatedUnion<T1, T2, T3, T4>? {
		return if (isFirstType) {
			@Suppress("UNCHECKED_CAST")
			takeUnless { predicate(firstOrNull as T1) }
		} else {
			this
		}
	}

	inline fun takeUnlessSecond(
		predicate: (T2) -> Boolean,
	): QuadDiscriminatedUnion<T1, T2, T3, T4>? {
		return if (isSecondType) {
			@Suppress("UNCHECKED_CAST")
			takeUnless { predicate(secondOrNull as T2) }
		} else {
			this
		}
	}

	inline fun takeUnlessThird(
		predicate: (T3) -> Boolean,
	): QuadDiscriminatedUnion<T1, T2, T3, T4>? {
		return if (isThirdType) {
			@Suppress("UNCHECKED_CAST")
			takeUnless { predicate(thirdOrNull as T3) }
		} else {
			this
		}
	}

	inline fun also(
		type1Also: (T1) -> Unit,
		type2Also: (T2) -> Unit,
		type3Also: (T3) -> Unit,
	): QuadDiscriminatedUnion<T1, T2, T3, T4> {
		return if (isFirstType) {
			@Suppress("UNCHECKED_CAST")
			also { type1Also(firstOrNull as T1) }
		} else if (isSecondType) {
			@Suppress("UNCHECKED_CAST")
			also { type2Also(secondOrNull as T2) }
		} else {
			@Suppress("UNCHECKED_CAST")
			also { type3Also(thirdOrNull as T3) }
		}
	}

	inline fun alsoFirst(
		block: (T1) -> Unit,
	): QuadDiscriminatedUnion<T1, T2, T3, T4> {
		return if (isFirstType) {
			@Suppress("UNCHECKED_CAST")
			also { block(firstOrNull as T1) }
		} else {
			this
		}
	}

	inline fun alsoSecond(
		block: (T2) -> Unit,
	): QuadDiscriminatedUnion<T1, T2, T3, T4> {
		return if (isSecondType) {
			@Suppress("UNCHECKED_CAST")
			also { block(secondOrNull as T2) }
		} else {
			this
		}
	}

	inline fun alsoThird(
		block: (T3) -> Unit,
	): QuadDiscriminatedUnion<T1, T2, T3, T4> {
		return if (isThirdType) {
			@Suppress("UNCHECKED_CAST")
			also { block(thirdOrNull as T3) }
		} else {
			this
		}
	}

	inline fun apply(
		type1Apply: T1.() -> Unit,
		type2Apply: T2.() -> Unit,
		type3Apply: T3.() -> Unit,
	): QuadDiscriminatedUnion<T1, T2, T3, T4> {
		return if (isFirstType) {
			@Suppress("UNCHECKED_CAST")
			apply { (firstOrNull as T1).type1Apply() }
		} else if (isSecondType) {
			@Suppress("UNCHECKED_CAST")
			apply { (secondOrNull as T2).type2Apply() }
		} else {
			@Suppress("UNCHECKED_CAST")
			apply { (thirdOrNull as T3).type3Apply() }
		}
	}

	inline fun applyFirst(
		block: T1.() -> Unit,
	): QuadDiscriminatedUnion<T1, T2, T3, T4> {
		return if (isFirstType) {
			@Suppress("UNCHECKED_CAST")
			also { (firstOrNull as T1).block() }
		} else {
			this
		}
	}

	inline fun applySecond(
		block: T2.() -> Unit,
	): QuadDiscriminatedUnion<T1, T2, T3, T4> {
		return if (isSecondType) {
			@Suppress("UNCHECKED_CAST")
			also { (secondOrNull as T2).block() }
		} else {
			this
		}
	}

	inline fun applyThird(
		block: T3.() -> Unit,
	): QuadDiscriminatedUnion<T1, T2, T3, T4> {
		return if (isThirdType) {
			@Suppress("UNCHECKED_CAST")
			also { (thirdOrNull as T3).block() }
		} else {
			this
		}
	}*/

	/**
	 * If [isFirstType], execute [type1Block] on the value and return the result.
	 * Else if [isSecondType], execute [type2Block] on the value and return the result.
	 * Else if [isThirdType], execute [type3Block] on the value and return the result.
	 * Else, execute [type4Block] on the value and return the result.
	 */
	inline fun <R> map(
		type1Block: (T1) -> R,
		type2Block: (T2) -> R,
		type3Block: (T3) -> R,
		type4Block: (T4) -> R,
	): R {
		return if (isFirstType) {
			@Suppress("UNCHECKED_CAST")
			type1Block(firstOrNull as T1)
		} else if (isSecondType) {
			@Suppress("UNCHECKED_CAST")
			type2Block(secondOrNull as T2)
		} else if (isThirdType) {
			@Suppress("UNCHECKED_CAST")
			type3Block(thirdOrNull as T3)
		} else {
			@Suppress("UNCHECKED_CAST")
			type4Block(fourthOrNull as T4)
		}
	}/*

	/**
	 * If [isFirstType] is true, return the value directly.
	 * Else if [isSecondType], execute [type2Block] on the value and return the result.
	 * Else, execute [type3Block] on the value and return the result.
	 */
	inline fun mapToFirst(
		type2Block: (T2) -> T1,
		type3Block: (T3) -> T1,
	): T1 {
		return if (isFirstType) {
			@Suppress("UNCHECKED_CAST")
			firstOrNull as T1
		} else if (isSecondType) {
			@Suppress("UNCHECKED_CAST")
			type2Block(secondOrNull as T2)
		} else {
			@Suppress("UNCHECKED_CAST")
			type3Block(thirdOrNull as T3)
		}
	}

	/**
	 * If [isSecondType] is true, return the value directly.
	 * Else if [isFirstType], execute [type1Block] on the value and return the result.
	 * Else, execute [type3Block] on the value and return the result.
	 */
	inline fun mapToSecond(
		type1Block: (T1) -> T2,
		type3Block: (T3) -> T2,
	): T2 {
		return if (isFirstType) {
			@Suppress("UNCHECKED_CAST")
			type1Block(firstOrNull as T1)
		} else if (isSecondType) {
			@Suppress("UNCHECKED_CAST")
			secondOrNull as T2
		} else {
			@Suppress("UNCHECKED_CAST")
			type3Block(thirdOrNull as T3)
		}
	}

	/**
	 * If [isThirdType] is true, return the value directly.
	 * Else if [isFirstType], execute [type1Block] on the value and return the result.
	 * Else, execute [type2Block] on the value and return the result.
	 */
	inline fun mapToThird(
		type1Block: (T1) -> T3,
		type2Block: (T2) -> T3,
	): T3 {
		return if (isFirstType) {
			@Suppress("UNCHECKED_CAST")
			type1Block(firstOrNull as T1)
		} else if (isSecondType) {
			@Suppress("UNCHECKED_CAST")
			type2Block(secondOrNull as T2)
		} else {
			@Suppress("UNCHECKED_CAST")
			thirdOrNull as T3
		}
	}

	/**
	 * If [isFirstType] is true, execute [block] and return a 2 value [DiscriminatedUnion].
	 * Else return a 2 value [DiscriminatedUnion] of the second and third types.
	 */
	inline fun mapFirstToSecond(
		block: (T1) -> T2,
	): DiscriminatedUnion<T2, T3> {
		return if (isFirstType) {
			@Suppress("UNCHECKED_CAST")
			DiscriminatedUnion.first(block(firstOrNull as T1))
		} else if (isSecondType) {
			@Suppress("UNCHECKED_CAST")
			DiscriminatedUnion.first(secondOrNull as T2)
		} else {
			@Suppress("UNCHECKED_CAST")
			DiscriminatedUnion.second(thirdOrNull as T3)
		}
	}

	/**
	 * If [isFirstType] is true, execute [block] and return a 2 value [DiscriminatedUnion].
	 * Else return a 2 value [DiscriminatedUnion] of the second and third types.
	 */
	inline fun mapFirstToThird(
		block: (T1) -> T3,
	): DiscriminatedUnion<T2, T3> {
		return if (isFirstType) {
			@Suppress("UNCHECKED_CAST")
			DiscriminatedUnion.second(block(firstOrNull as T1))
		} else if (isSecondType) {
			@Suppress("UNCHECKED_CAST")
			DiscriminatedUnion.first(secondOrNull as T2)
		} else {
			@Suppress("UNCHECKED_CAST")
			DiscriminatedUnion.second(thirdOrNull as T3)
		}
	}

	/**
	 * If [isSecondType] is true, execute [block] and return a 2 value [DiscriminatedUnion].
	 * Else return a 2 value [DiscriminatedUnion] of the third and third types.
	 */
	inline fun mapSecondToFirst(
		block: (T2) -> T1,
	): DiscriminatedUnion<T1, T3> {
		return if (isFirstType) {
			@Suppress("UNCHECKED_CAST")
			DiscriminatedUnion.first(firstOrNull as T1)
		} else if (isSecondType) {
			@Suppress("UNCHECKED_CAST")
			DiscriminatedUnion.first(block(secondOrNull as T2))
		} else {
			@Suppress("UNCHECKED_CAST")
			DiscriminatedUnion.second(thirdOrNull as T3)
		}
	}

	/**
	 * If [isSecondType] is true, execute [block] and return a 2 value [DiscriminatedUnion].
	 * Else return a 2 value [DiscriminatedUnion] of the first and third types.
	 */
	inline fun mapSecondToThird(
		block: (T2) -> T3,
	): DiscriminatedUnion<T1, T3> {
		return if (isFirstType) {
			@Suppress("UNCHECKED_CAST")
			DiscriminatedUnion.first(firstOrNull as T1)
		} else if (isSecondType) {
			@Suppress("UNCHECKED_CAST")
			DiscriminatedUnion.second(block(secondOrNull as T2))
		} else {
			@Suppress("UNCHECKED_CAST")
			DiscriminatedUnion.second(thirdOrNull as T3)
		}
	}

	/**
	 * If [isThirdType] is true, execute [block] and return a 2 value [DiscriminatedUnion].
	 * Else return a 2 value [DiscriminatedUnion] of the first and second types.
	 */
	inline fun mapThirdToFirst(
		block: (T3) -> T1,
	): DiscriminatedUnion<T1, T2> {
		return if (isFirstType) {
			@Suppress("UNCHECKED_CAST")
			DiscriminatedUnion.first(firstOrNull as T1)
		} else if (isSecondType) {
			@Suppress("UNCHECKED_CAST")
			DiscriminatedUnion.second(secondOrNull as T2)
		} else {
			@Suppress("UNCHECKED_CAST")
			DiscriminatedUnion.first(block(thirdOrNull as T3))
		}
	}

	/**
	 * If [isThirdType] is true, execute [block] and return a 2 value [DiscriminatedUnion].
	 * Else return a 2 value [DiscriminatedUnion] of the first and second types.
	 */
	inline fun mapThirdToSecond(
		block: (T3) -> T2,
	): DiscriminatedUnion<T1, T2> {
		return if (isFirstType) {
			@Suppress("UNCHECKED_CAST")
			DiscriminatedUnion.first(firstOrNull as T1)
		} else if (isSecondType) {
			@Suppress("UNCHECKED_CAST")
			DiscriminatedUnion.second(secondOrNull as T2)
		} else {
			@Suppress("UNCHECKED_CAST")
			DiscriminatedUnion.second(block(thirdOrNull as T3))
		}
	}

	inline fun <R1, R2, R3> flatMap(
		type1Block: (T1) -> R1,
		type2Block: (T2) -> R2,
		type3Block: (T3) -> R3,
	): QuadDiscriminatedUnion<R1, R2, R3> {
		return if (isFirstType) {
			@Suppress("UNCHECKED_CAST")
			first(type1Block(firstOrNull as T1))
		} else if (isSecondType) {
			@Suppress("UNCHECKED_CAST")
			second(type2Block(secondOrNull as T2))
		} else {
			@Suppress("UNCHECKED_CAST")
			third(type3Block(thirdOrNull as T3))
		}
	}

	inline fun <R1> flatMapFirst(
		block: (T1) -> R1,
	): QuadDiscriminatedUnion<R1, T2, T3> {
		return if (isFirstType) {
			@Suppress("UNCHECKED_CAST")
			first(block(firstOrNull as T1))
		} else {
			@Suppress("UNCHECKED_CAST")
			this as QuadDiscriminatedUnion<R1, T2, T3>
		}
	}

	inline fun <R2> flatMapSecond(
		block: (T2) -> R2,
	): QuadDiscriminatedUnion<T1, R2, T3> {
		return if (isSecondType) {
			@Suppress("UNCHECKED_CAST")
			(second(block(secondOrNull as T2)))
		} else {
			@Suppress("UNCHECKED_CAST")
			this as QuadDiscriminatedUnion<T1, R2, T3>
		}
	}

	inline fun <R3> flatMapThird(
		block: (T3) -> R3,
	): QuadDiscriminatedUnion<T1, T2, R3> {
		return if (isThirdType) {
			@Suppress("UNCHECKED_CAST")
			(third(block(thirdOrNull as T3)))
		} else {
			@Suppress("UNCHECKED_CAST")
			this as QuadDiscriminatedUnion<T1, T2, R3>
		}
	}*/

	override fun orDefaults(
		type1Default: T1,
		type2Default: T2,
	): Pair<T1, T2> {
		return if (isFirstType) {
			@Suppress("UNCHECKED_CAST")
			(firstOrNull as T1) to type2Default
		} else if (isSecondType) {
			@Suppress("UNCHECKED_CAST")
			type1Default to (secondOrNull as T2)
		} else {
			type1Default to type2Default
		}
	}

	override fun orDefaults(
		defaults: Pair<T1, T2>,
	): Pair<T1, T2> {
		return if (isFirstType) {
			@Suppress("UNCHECKED_CAST")
			defaults.copy(first = (firstOrNull as T1))
		} else if (isSecondType) {
			@Suppress("UNCHECKED_CAST")
			defaults.copy(second = (secondOrNull as T2))
		} else {
			defaults
		}
	}

	/**
	 * If [isFirstType], return a [Pair] with this union's value in the [Pair]'s first value and null in the second value
	 * Else if [isSecondType], return a [Pair] with null in the first value and this union's value in the [Pair]'s second value
	 * Else, return a [Pair] with null values
	 */
	override fun toPair(): Pair<T1?, T2?> {
		return map({ it to null }, { null to it }, { null to null }, { null to null })
	}

	override fun orDefaults(
		type1Default: T1,
		type2Default: T2,
		type3Default: T3,
	): Triple<T1, T2, T3> {
		return if (isFirstType) {
			@Suppress("UNCHECKED_CAST")
			Triple((firstOrNull as T1), type2Default, type3Default)
		} else if (isSecondType) {
			@Suppress("UNCHECKED_CAST")
			Triple(type1Default, (secondOrNull as T2), type3Default)
		} else if (isThirdType) {
			@Suppress("UNCHECKED_CAST")
			Triple(type1Default, type2Default, (thirdOrNull as T3))
		} else {
			Triple(type1Default, type2Default, type3Default)
		}
	}

	override fun orDefaults(
		defaults: Triple<T1, T2, T3>,
	): Triple<T1, T2, T3> {
		return if (isFirstType) {
			@Suppress("UNCHECKED_CAST")
			defaults.copy(first = (firstOrNull as T1))
		} else if (isSecondType) {
			@Suppress("UNCHECKED_CAST")
			defaults.copy(second = (secondOrNull as T2))
		} else if (isThirdType) {
			@Suppress("UNCHECKED_CAST")
			defaults.copy(third = (thirdOrNull as T3))
		} else {
			defaults
		}
	}

	/**
	 * If [isFirstType], return a [Triple] with this union's value in the [Triple]'s first value and null in the second and third values
	 * Else if [isSecondType], return a [Triple] with this union's value in the [Triple]'s second value and null in the first and third values
	 * Else if [isThirdType], return a [Triple] with null in the first and second values and this union's value in the [Triple]'s third value
	 * Else, return a [Triple] with null values
	 */
	override fun toTriple(): Triple<T1?, T2?, T3?> {
		return map(
			{ Triple(it, null, null) },
			{ Triple(null, it, null) },
			{ Triple(null, null, it) },
			{ Triple(null, null, null) },
		)
	}

	override fun orDefaults(
		type1Default: T1,
		type2Default: T2,
		type3Default: T3,
		type4Default: T4,
	): Quadruple<T1, T2, T3, T4> {
		return if (isFirstType) {
			@Suppress("UNCHECKED_CAST")
			Quadruple((firstOrNull as T1), type2Default, type3Default, type4Default)
		} else if (isSecondType) {
			@Suppress("UNCHECKED_CAST")
			Quadruple(type1Default, (secondOrNull as T2), type3Default, type4Default)
		} else if (isThirdType) {
			@Suppress("UNCHECKED_CAST")
			Quadruple(type1Default, type2Default, (thirdOrNull as T3), type4Default)
		} else {
			@Suppress("UNCHECKED_CAST")
			Quadruple(type1Default, type2Default, type3Default, (fourthOrNull as T4))
		}
	}

	override fun orDefaults(
		defaults: Quadruple<T1, T2, T3, T4>,
	): Quadruple<T1, T2, T3, T4> {
		return if (isFirstType) {
			@Suppress("UNCHECKED_CAST")
			defaults.copy(first = (firstOrNull as T1))
		} else if (isSecondType) {
			@Suppress("UNCHECKED_CAST")
			defaults.copy(second = (secondOrNull as T2))
		} else if (isThirdType) {
			@Suppress("UNCHECKED_CAST")
			defaults.copy(third = (thirdOrNull as T3))
		} else {
			@Suppress("UNCHECKED_CAST")
			defaults.copy(fourth = (fourthOrNull as T4))
		}
	}

	/**
	 * If [isFirstType], return a [Quadruple] with this union's value in the [Quadruple]'s first value and null in the last 3 values
	 * Else if [isSecondType], return a [Quadruple] with this union's value in the [Quadruple]'s second value and null in the first, third, and fourth values
	 * Else if [isThirdType], return a [Quadruple] with null in the first, second, and fourth values and this union's value in the [Quadruple]'s third value
	 * Else, return a [Quadruple] with null in the first 3 values and this union's value in the [Quadruple]'s fourth value
	 */
	override fun toQuadruple(): Quadruple<T1?, T2?, T3?, T4?> {
		return map(
			{ Quadruple(it, null, null, null) },
			{ Quadruple(null, it, null, null) },
			{ Quadruple(null, null, it, null) },
			{ Quadruple(null, null, null, it) },
		)
	}/*

	/**
	 * If [isFirstType] is true, execute [type1Predicate] on the value of the first type and return the result.
	 * Else if [isSecondType], execute [type2Predicate] on the value of the second type and return the result.
	 * Else, execute [type3Predicate] on the value of the third type and return the result.
	 */
	inline fun anyOf(
		type1Predicate: (T1) -> Boolean,
		type2Predicate: (T2) -> Boolean,
		type3Predicate: (T3) -> Boolean,
	): Boolean {
		return if (isFirstType) {
			@Suppress("UNCHECKED_CAST")
			type1Predicate(firstOrNull as T1)
		} else if (isSecondType) {
			@Suppress("UNCHECKED_CAST")
			type2Predicate(secondOrNull as T2)
		} else {
			@Suppress("UNCHECKED_CAST")
			type3Predicate(thirdOrNull as T3)
		}
	}

	/**
	 * If [isFirstType] is true, return [type1Predicate].
	 * Else if [isSecondType], execute [type2Predicate] on the value of the second type and return the result.
	 * Else, execute [type3Predicate] on the value of the second type and return the result.
	 */
	inline fun anyOf(
		type1Predicate: Boolean,
		type2Predicate: (T2) -> Boolean,
		type3Predicate: (T3) -> Boolean,
	): Boolean {
		return if (isFirstType) {
			type1Predicate
		} else if (isSecondType) {
			@Suppress("UNCHECKED_CAST")
			type2Predicate(secondOrNull as T2)
		} else {
			@Suppress("UNCHECKED_CAST")
			type3Predicate(thirdOrNull as T3)
		}
	}

	/**
	 * If [isFirstType] is true, return [type1Predicate].
	 * Else if [isSecondType], return [type2Predicate].
	 * Else, execute [type3Predicate] on the value of the second type and return the result.
	 */
	inline fun anyOf(
		type1Predicate: Boolean,
		type2Predicate: Boolean,
		type3Predicate: (T3) -> Boolean,
	): Boolean {
		return if (isFirstType) {
			type1Predicate
		} else if (isSecondType) {
			type2Predicate
		} else {
			@Suppress("UNCHECKED_CAST")
			type3Predicate(thirdOrNull as T3)
		}
	}

	/**
	 * If [isFirstType] is true, return [type1Predicate].
	 * Else if [isSecondType], execute [type2Predicate] on the value of the second type and return the result.
	 * Else, return [type3Predicate].
	 */
	inline fun anyOf(
		type1Predicate: Boolean,
		type2Predicate: (T2) -> Boolean,
		type3Predicate: Boolean,
	): Boolean {
		return if (isFirstType) {
			type1Predicate
		} else if (isSecondType) {
			@Suppress("UNCHECKED_CAST")
			type2Predicate(secondOrNull as T2)
		} else {
			type3Predicate
		}
	}

	/**
	 * If [isFirstType] is true, execute [type1Predicate] on the value of the first type and return the result.
	 * Else if [isSecondType], return [type2Predicate].
	 * Else, execute [type3Predicate] on the value of the second type and return the result.
	 */
	inline fun anyOf(
		type1Predicate: (T1) -> Boolean,
		type2Predicate: Boolean,
		type3Predicate: (T3) -> Boolean,
	): Boolean {
		return if (isFirstType) {
			@Suppress("UNCHECKED_CAST")
			type1Predicate(firstOrNull as T1)
		} else if (isSecondType) {
			type2Predicate
		} else {
			@Suppress("UNCHECKED_CAST")
			type3Predicate(thirdOrNull as T3)
		}
	}

	/**
	 * If [isFirstType] is true, execute [type1Predicate] on the value of the first type and return the result.
	 * Else if [isSecondType], return [type2Predicate].
	 * Else, return [type3Predicate].
	 */
	inline fun anyOf(
		type1Predicate: (T1) -> Boolean,
		type2Predicate: Boolean,
		type3Predicate: Boolean,
	): Boolean {
		return if (isFirstType) {
			@Suppress("UNCHECKED_CAST")
			type1Predicate(firstOrNull as T1)
		} else if (isSecondType) {
			type2Predicate
		} else {
			type3Predicate
		}
	}

	/**
	 * If [isFirstType] is true, execute [type1Predicate] on the value of the first type and return the result.
	 * Else if [isSecondType], execute [type2Predicate] on the value of the second type and return the result.
	 * Else, return [type3Predicate]
	 */
	inline fun anyOf(
		type1Predicate: (T1) -> Boolean,
		type2Predicate: (T2) -> Boolean,
		type3Predicate: Boolean,
	): Boolean {
		return if (isFirstType) {
			@Suppress("UNCHECKED_CAST")
			type1Predicate(firstOrNull as T1)
		} else if (isSecondType) {
			@Suppress("UNCHECKED_CAST")
			type2Predicate(secondOrNull as T2)
		} else {
			type3Predicate
		}
	}

	/**
	 * If [isFirstType] is true, execute [type1Predicate] on the value of the first type and return true if [type1Predicate] returns false.
	 * Else if [isSecondType], execute [type2Predicate] on the value of the second type and return true if [type2Predicate] returns false.
	 * Else, execute [type3Predicate] on the value of the second type and return true if [type3Predicate] returns false.
	 */
	inline fun noneOf(
		type1Predicate: (T1) -> Boolean,
		type2Predicate: (T2) -> Boolean,
		type3Predicate: (T3) -> Boolean,
	): Boolean {
		return if (isFirstType) {
			@Suppress("UNCHECKED_CAST")
			!type1Predicate(firstOrNull as T1)
		} else if (isSecondType) {
			@Suppress("UNCHECKED_CAST")
			!type2Predicate(secondOrNull as T2)
		} else {
			@Suppress("UNCHECKED_CAST")
			!type3Predicate(thirdOrNull as T3)
		}
	}

	/**
	 * Return a [QuadDiscriminatedUnion] with the types reversed
	 */
	fun reverse(): QuadDiscriminatedUnion<T3, T2, T1> {
		return when (value) {
			is Value1<T1, T2, T3, T4> -> third(value.value)
			is Value2<T1, T2, T3, T4> -> second(value.value)
			is Value3<T1, T2, T3, T4> -> first(value.value)
		}
	}

	/**
	 * Return a [QuadDiscriminatedUnion] with the first two types reversed
	 */
	fun reverseFirstTwo(): QuadDiscriminatedUnion<T2, T1, T3> {
		return when (value) {
			is Value1<T1, T2, T3, T4> -> second(value.value)
			is Value2<T1, T2, T3, T4> -> first(value.value)
			is Value3<T1, T2, T3, T4> -> third(value.value)
		}
	}

	/**
	 * Return a [QuadDiscriminatedUnion] with the last two types reversed
	 */
	fun reverseLastTwo(): QuadDiscriminatedUnion<T1, T3, T2> {
		return when (value) {
			is Value1<T1, T2, T3, T4> -> first(value.value)
			is Value2<T1, T2, T3, T4> -> third(value.value)
			is Value3<T1, T2, T3, T4> -> second(value.value)
		}
	}*/

	override fun toString() = value.toString()

	private sealed interface Value<T1, T2, T3, T4>

	@JvmInline
	private value class Value1<T1, T2, T3, T4>(val value: T1) : Value<T1, T2, T3, T4> {
		override fun toString() = value.toString()
	}

	@JvmInline
	private value class Value2<T1, T2, T3, T4>(val value: T2) : Value<T1, T2, T3, T4> {
		override fun toString() = value.toString()
	}

	@JvmInline
	private value class Value3<T1, T2, T3, T4>(val value: T3) : Value<T1, T2, T3, T4> {
		override fun toString() = value.toString()
	}

	@JvmInline
	private value class Value4<T1, T2, T3, T4>(val value: T4) : Value<T1, T2, T3, T4> {
		override fun toString() = value.toString()
	}

	companion object {
		val <T> QuadDiscriminatedUnion<T, T, T, T>.value
			@JvmStatic get() = when (value) {
				is Value1<T, T, T, T> -> value.value
				is Value2<T, T, T, T> -> value.value
				is Value3<T, T, T, T> -> value.value
				is Value4<T, T, T, T> -> value.value
			}

		@JvmStatic
		val <T1 : Any, T2 : Any, T3: Any, T4: Any> QuadDiscriminatedUnion<T1?, T2?, T3?, T4?>?.allNull: Boolean
			get() = takeUnless { it?.unionValue == null } == null

		@JvmStatic
		fun <T1, T2, T3, T4> first(value: T1): QuadDiscriminatedUnion<T1, T2, T3, T4> {
			return QuadDiscriminatedUnion(Value1(value))
		}

		@JvmStatic
		fun <T1, T2, T3, T4> second(value: T2): QuadDiscriminatedUnion<T1, T2, T3, T4> {
			return QuadDiscriminatedUnion(Value2(value))
		}

		@JvmStatic
		fun <T1, T2, T3, T4> third(value: T3): QuadDiscriminatedUnion<T1, T2, T3, T4> {
			return QuadDiscriminatedUnion(Value3(value))
		}

		@JvmStatic
		fun <T1, T2, T3, T4> fourth(value: T4): QuadDiscriminatedUnion<T1, T2, T3, T4> {
			return QuadDiscriminatedUnion(Value4(value))
		}

		fun <T1, T2, T3> QuadDiscriminatedUnion<T1, T2, T3, out Throwable>.toResult(): Result<TriDiscriminatedUnion<T1, T2 ,T3>> {
			return map(
				{ Result.success(TriDiscriminatedUnion.first(it)) },
				{ Result.success(TriDiscriminatedUnion.second(it)) },
				{ Result.success(TriDiscriminatedUnion.third(it)) },
				{ Result.failure(it) },
			)
		}

		@JvmStatic
		fun <T1 : Any, T2 : Any, T3: Any, T4 : Any> QuadDiscriminatedUnion<T1?, T2?, T3?, T4?>?.takeUnlessAllNull(): QuadDiscriminatedUnion<T1, T2, T3, T4>? {
			@Suppress("UNCHECKED_CAST")
			return takeUnless { it?.unionValue == null } as QuadDiscriminatedUnion<T1, T2, T3, T4>?
		}

		@JvmStatic
		fun <T1, T2, T3, T4> QuadDiscriminatedUnion<T1, T2, T3, T4>?.orNullableTypes(): QuadDiscriminatedUnion<T1?, T2?, T3?, T4?> {
			return if (this == null) {
				first(null)
			} else {
				@Suppress("UNCHECKED_CAST")
				this as QuadDiscriminatedUnion<T1?, T2?, T3?, T4?>
			}
		}

		/*@JvmStatic
		fun <T1, T2, T3, T4> QuadDiscriminatedUnion<QuadDiscriminatedUnion<T1, T2, T3, T4>, QuadDiscriminatedUnion<T1, T2, T3, T4>, QuadDiscriminatedUnion<T1, T2, T3, T4>>.flatten(): QuadDiscriminatedUnion<T1, T2, T3, T4> {
			return when (value) {
				is Value1<QuadDiscriminatedUnion<T1, T2, T3, T4>, QuadDiscriminatedUnion<T1, T2, T3, T4>, QuadDiscriminatedUnion<T1, T2, T3, T4>> -> value.value
				is Value2<QuadDiscriminatedUnion<T1, T2, T3, T4>, QuadDiscriminatedUnion<T1, T2, T3, T4>, QuadDiscriminatedUnion<T1, T2, T3, T4>> -> value.value
				is Value3<QuadDiscriminatedUnion<T1, T2, T3, T4>, QuadDiscriminatedUnion<T1, T2, T3, T4>, QuadDiscriminatedUnion<T1, T2, T3, T4>> -> value.value
			}
		}

		@JvmStatic
		@JvmName("flattenFirstAndSecond")
		fun <T1, T2, T3, T4> QuadDiscriminatedUnion<QuadDiscriminatedUnion<T1, T2, T3, T4>, QuadDiscriminatedUnion<T1, T2, T3, T4>, T3>.flatten(): QuadDiscriminatedUnion<T1, T2, T3, T4> {
			@Suppress("UNCHECKED_CAST")
			return when (value) {
				is Value1<QuadDiscriminatedUnion<T1, T2, T3, T4>, QuadDiscriminatedUnion<T1, T2, T3, T4>, T3> -> value.value
				is Value2<QuadDiscriminatedUnion<T1, T2, T3, T4>, QuadDiscriminatedUnion<T1, T2, T3, T4>, T3> -> value.value
				is Value3<QuadDiscriminatedUnion<T1, T2, T3, T4>, QuadDiscriminatedUnion<T1, T2, T3, T4>, T3> -> this as QuadDiscriminatedUnion<T1, T2, T3, T4>
			}
		}

		@JvmStatic
		@JvmName("flattenFirstAndThird")
		fun <T1, T2, T3, T4> QuadDiscriminatedUnion<QuadDiscriminatedUnion<T1, T2, T3, T4>, T2, QuadDiscriminatedUnion<T1, T2, T3, T4>>.flatten(): QuadDiscriminatedUnion<T1, T2, T3, T4> {
			@Suppress("UNCHECKED_CAST")
			return when (value) {
				is Value1<QuadDiscriminatedUnion<T1, T2, T3, T4>, T2, QuadDiscriminatedUnion<T1, T2, T3, T4>> -> value.value
				is Value2<QuadDiscriminatedUnion<T1, T2, T3, T4>, T2, QuadDiscriminatedUnion<T1, T2, T3, T4>> -> this as QuadDiscriminatedUnion<T1, T2, T3, T4>
				is Value3<QuadDiscriminatedUnion<T1, T2, T3, T4>, T2, QuadDiscriminatedUnion<T1, T2, T3, T4>> -> value.value
			}
		}

		@JvmStatic
		@JvmName("flattenSecondAndThird")
		fun <T1, T2, T3, T4> QuadDiscriminatedUnion<T1, QuadDiscriminatedUnion<T1, T2, T3, T4>, QuadDiscriminatedUnion<T1, T2, T3, T4>>.flatten(): QuadDiscriminatedUnion<T1, T2, T3, T4> {
			@Suppress("UNCHECKED_CAST")
			return when (value) {
				is Value1<T1, QuadDiscriminatedUnion<T1, T2, T3, T4>, QuadDiscriminatedUnion<T1, T2, T3, T4>> -> this as QuadDiscriminatedUnion<T1, T2, T3, T4>
				is Value2<T1, QuadDiscriminatedUnion<T1, T2, T3, T4>, QuadDiscriminatedUnion<T1, T2, T3, T4>> -> value.value
				is Value3<T1, QuadDiscriminatedUnion<T1, T2, T3, T4>, QuadDiscriminatedUnion<T1, T2, T3, T4>> -> value.value
			}
		}

		@JvmStatic
		@JvmName("flattenFirst")
		fun <T1, T2, T3, T4> QuadDiscriminatedUnion<QuadDiscriminatedUnion<T1, T2, T3, T4>, T2, T3>.flatten(): QuadDiscriminatedUnion<T1, T2, T3, T4> {
			@Suppress("UNCHECKED_CAST")
			return when (value) {
				is Value1<QuadDiscriminatedUnion<T1, T2, T3, T4>, T2, T3> -> value.value
				is Value2<QuadDiscriminatedUnion<T1, T2, T3, T4>, T2, T3> -> this as QuadDiscriminatedUnion<T1, T2, T3, T4>
				is Value3<QuadDiscriminatedUnion<T1, T2, T3, T4>, T2, T3> -> this as QuadDiscriminatedUnion<T1, T2, T3, T4>
			}
		}

		@JvmStatic
		@JvmName("flattenSecond")
		fun <T1, T2, T3, T4> QuadDiscriminatedUnion<T1, QuadDiscriminatedUnion<T1, T2, T3, T4>, T3>.flatten(): QuadDiscriminatedUnion<T1, T2, T3, T4> {
			@Suppress("UNCHECKED_CAST")
			return when (value) {
				is Value1<T1, QuadDiscriminatedUnion<T1, T2, T3, T4>, T3> -> this as QuadDiscriminatedUnion<T1, T2, T3, T4>
				is Value2<T1, QuadDiscriminatedUnion<T1, T2, T3, T4>, T3> -> value.value
				is Value3<T1, QuadDiscriminatedUnion<T1, T2, T3, T4>, T3> -> this as QuadDiscriminatedUnion<T1, T2, T3, T4>
			}
		}

		@JvmStatic
		@JvmName("flattenThird")
		fun <T1, T2, T3, T4> QuadDiscriminatedUnion<T1, T2, QuadDiscriminatedUnion<T1, T2, T3, T4>>.flatten(): QuadDiscriminatedUnion<T1, T2, T3, T4> {
			@Suppress("UNCHECKED_CAST")
			return when (value) {
				is Value1<T1, T2, QuadDiscriminatedUnion<T1, T2, T3, T4>> -> this as QuadDiscriminatedUnion<T1, T2, T3, T4>
				is Value2<T1, T2, QuadDiscriminatedUnion<T1, T2, T3, T4>> -> this as QuadDiscriminatedUnion<T1, T2, T3, T4>
				is Value3<T1, T2, QuadDiscriminatedUnion<T1, T2, T3, T4>> -> value.value
			}
		}

		@JvmStatic
		fun <T1, T2> QuadDiscriminatedUnion<T1, T1, T2>.flattenFirstAndSecond(): DiscriminatedUnion<T1, T2> {
			return when (value) {
				is Value1<T1, T1, T2> -> DiscriminatedUnion.first(value.value)
				is Value2<T1, T1, T2> -> DiscriminatedUnion.first(value.value)
				is Value3<T1, T1, T2> -> DiscriminatedUnion.second(value.value)
			}
		}

		@JvmStatic
		fun <T1, T2> QuadDiscriminatedUnion<T1, T2, T1>.flattenFirstAndThird(): DiscriminatedUnion<T1, T2> {
			return when (value) {
				is Value1<T1, T2, T1> -> DiscriminatedUnion.first(value.value)
				is Value2<T1, T2, T1> -> DiscriminatedUnion.second(value.value)
				is Value3<T1, T2, T1> -> DiscriminatedUnion.first(value.value)
			}
		}

		@JvmStatic
		fun <T1, T2> QuadDiscriminatedUnion<T1, T2, T2>.flattenSecondAndThird(): DiscriminatedUnion<T1, T2> {
			return when (value) {
				is Value1<T1, T2, T2> -> DiscriminatedUnion.first(value.value)
				is Value2<T1, T2, T2> -> DiscriminatedUnion.second(value.value)
				is Value3<T1, T2, T2> -> DiscriminatedUnion.second(value.value)
			}
		}

		@JvmStatic
		fun <T1, T2, T3, T4> Iterable<QuadDiscriminatedUnion<T1, T2, T3, T4>>.flattenUnions() = map {
			when (it.value) {
				is Value1<T1, T2, T3, T4> -> it.value.value
				is Value2<T1, T2, T3, T4> -> it.value.value
				is Value3<T1, T2, T3, T4> -> it.value.value
			}
		}

		@JvmStatic
		fun <T1, T2, T3, T4> Sequence<QuadDiscriminatedUnion<T1, T2, T3, T4>>.flattenUnions() = map {
			when (it.value) {
				is Value1<T1, T2, T3, T4> -> it.value.value
				is Value2<T1, T2, T3, T4> -> it.value.value
				is Value3<T1, T2, T3, T4> -> it.value.value
			}
		}*/

		/*inline fun <T1 : Any, T2 : Any, T3 : Any, T4 : Any, T5> QuadDiscriminatedUnion<T1?, T2?, T3?, T4?>?.fifthTypeIfAllNull(
			fifthValue: () -> T5,
		): QuintDiscriminatedUnion<T1, T2, T3, T4, T5> {
			return if (allNull) {
				return QuintDiscriminatedUnion.fourth(fourthValue())
			} else if (this!!.isFirstType) {
				QuintDiscriminatedUnion.first(firstOrNull!!)
			} else if (isSecondType) {
				QuintDiscriminatedUnion.second(secondOrNull!!)
			} else if (isThirdType) {
				QuintDiscriminatedUnion.third(thirdOrNull!!)
			} else {
				QuintDiscriminatedUnion.fourth(fourthOrNull!!)
			}
		}*/

		// TODO documentation
		// TODO QuintDiscriminatedUnion extensions
		// TODO add JSON support with Jackson JSON and kotlinx.serialization
		// TODO JSON support serialization modes: serialize as first successful, last successful, and type with most non-null fields
		// TODO OpenAPI support via springdoc-swaggerui
	}
}
