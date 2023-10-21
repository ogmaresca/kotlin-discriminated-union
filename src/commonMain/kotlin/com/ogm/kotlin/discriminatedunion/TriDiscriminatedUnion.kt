package com.ogm.kotlin.discriminatedunion

/**
 * A discriminated union with 3 possible types
 * @see <a href="https://en.wikipedia.org/wiki/Tagged_union">Tagged Union</a>
 * @see [DiscriminatedUnion] for a discriminated union with 2 types
 */
@JvmInline
value class TriDiscriminatedUnion<T1, T2, T3> private constructor(
	private val value: Value<T1, T2, T3>
) : ITriDiscriminatedUnion<T1, T2, T3> {
	@Suppress("IMPLICIT_CAST_TO_ANY")
	override val unionValue
		get() = when (value) {
			is Value1<T1, T2, T3> -> value.value
			is Value2<T1, T2, T3> -> value.value
			is Value3<T1, T2, T3> -> value.value
		}

	override val firstOrNull: T1?
		get() = when (value) {
			is Value1<T1, T2, T3> -> value.value
			else -> null
		}

	override val secondOrNull: T2?
		get() = when (value) {
			is Value2<T1, T2, T3> -> value.value
			else -> null
		}

	override val thirdOrNull: T3?
		get() = when (value) {
			is Value3<T1, T2, T3> -> value.value
			else -> null
		}

	override val isFirstType: Boolean
		get() = value is Value1<T1, T2, T3>

	override val isSecondType: Boolean
		get() = value is Value2<T1, T2, T3>

	override val isThirdType: Boolean
		get() = value is Value3<T1, T2, T3>

	override val position: Int
		get() = when (value) {
			is Value1<T1, T2, T3> -> 1
			is Value2<T1, T2, T3> -> 2
			is Value3<T1, T2, T3> -> 3
		}

	inline fun firstOrThrow(errorMessage: () -> String = { "DiscriminatedUnion is type $position" }): T1 {
		check(isFirstType, errorMessage)

		@Suppress("UNCHECKED_CAST")
		return firstOrNull as T1
	}

	inline fun secondOrThrow(errorMessage: () -> String = { "DiscriminatedUnion is type $position" }): T2 {
		check(isSecondType, errorMessage)

		@Suppress("UNCHECKED_CAST")
		return secondOrNull as T2
	}

	inline fun thirdOrThrow(errorMessage: () -> String = { "DiscriminatedUnion is type $position" }): T3 {
		check(isThirdType, errorMessage)

		@Suppress("UNCHECKED_CAST")
		return thirdOrNull as T3
	}

	override fun firstOr(defaultValue: T1): T1 {
		return when (value) {
			is Value1<T1, T2, T3> -> value.value
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
			is Value2<T1, T2, T3> -> value.value
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
			is Value3<T1, T2, T3> -> value.value
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

	inline fun takeIf(
		type1Predicate: (T1) -> Boolean,
		type2Predicate: (T2) -> Boolean,
		type3Predicate: (T3) -> Boolean,
	): TriDiscriminatedUnion<T1, T2, T3>? {
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
	): TriDiscriminatedUnion<T1, T2, T3>? {
		return if (isFirstType) {
			@Suppress("UNCHECKED_CAST")
			takeIf { predicate(firstOrNull as T1) }
		} else {
			this
		}
	}

	inline fun takeIfSecond(
		predicate: (T2) -> Boolean,
	): TriDiscriminatedUnion<T1, T2, T3>? {
		return if (isSecondType) {
			@Suppress("UNCHECKED_CAST")
			takeIf { predicate(secondOrNull as T2) }
		} else {
			this
		}
	}

	inline fun takeIfThird(
		predicate: (T3) -> Boolean,
	): TriDiscriminatedUnion<T1, T2, T3>? {
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
	): TriDiscriminatedUnion<T1, T2, T3>? {
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
	): TriDiscriminatedUnion<T1, T2, T3>? {
		return if (isFirstType) {
			@Suppress("UNCHECKED_CAST")
			takeUnless { predicate(firstOrNull as T1) }
		} else {
			this
		}
	}

	inline fun takeUnlessSecond(
		predicate: (T2) -> Boolean,
	): TriDiscriminatedUnion<T1, T2, T3>? {
		return if (isSecondType) {
			@Suppress("UNCHECKED_CAST")
			takeUnless { predicate(secondOrNull as T2) }
		} else {
			this
		}
	}

	inline fun takeUnlessThird(
		predicate: (T3) -> Boolean,
	): TriDiscriminatedUnion<T1, T2, T3>? {
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
	): TriDiscriminatedUnion<T1, T2, T3> {
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
	): TriDiscriminatedUnion<T1, T2, T3> {
		return if (isFirstType) {
			@Suppress("UNCHECKED_CAST")
			also { block(firstOrNull as T1) }
		} else {
			this
		}
	}

	inline fun alsoSecond(
		block: (T2) -> Unit,
	): TriDiscriminatedUnion<T1, T2, T3> {
		return if (isSecondType) {
			@Suppress("UNCHECKED_CAST")
			also { block(secondOrNull as T2) }
		} else {
			this
		}
	}

	inline fun alsoThird(
		block: (T3) -> Unit,
	): TriDiscriminatedUnion<T1, T2, T3> {
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
	): TriDiscriminatedUnion<T1, T2, T3> {
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
	): TriDiscriminatedUnion<T1, T2, T3> {
		return if (isFirstType) {
			@Suppress("UNCHECKED_CAST")
			also { (firstOrNull as T1).block() }
		} else {
			this
		}
	}

	inline fun applySecond(
		block: T2.() -> Unit,
	): TriDiscriminatedUnion<T1, T2, T3> {
		return if (isSecondType) {
			@Suppress("UNCHECKED_CAST")
			also { (secondOrNull as T2).block() }
		} else {
			this
		}
	}

	inline fun applyThird(
		block: T3.() -> Unit,
	): TriDiscriminatedUnion<T1, T2, T3> {
		return if (isThirdType) {
			@Suppress("UNCHECKED_CAST")
			also { (thirdOrNull as T3).block() }
		} else {
			this
		}
	}

	inline fun <R> map(
		type1Block: (T1) -> R,
		type2Block: (T2) -> R,
		type3Block: (T3) -> R,
	): R {
		return if (isFirstType) {
			@Suppress("UNCHECKED_CAST")
			type1Block(firstOrNull as T1)
		} else if (isSecondType) {
			@Suppress("UNCHECKED_CAST")
			type2Block(secondOrNull as T2)
		} else {
			@Suppress("UNCHECKED_CAST")
			type3Block(thirdOrNull as T3)
		}
	}

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
	): TriDiscriminatedUnion<R1, R2, R3> {
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
	): TriDiscriminatedUnion<R1, T2, T3> {
		return if (isFirstType) {
			@Suppress("UNCHECKED_CAST")
			first(block(firstOrNull as T1))
		} else {
			@Suppress("UNCHECKED_CAST")
			this as TriDiscriminatedUnion<R1, T2, T3>
		}
	}

	inline fun <R2> flatMapSecond(
		block: (T2) -> R2,
	): TriDiscriminatedUnion<T1, R2, T3> {
		return if (isSecondType) {
			@Suppress("UNCHECKED_CAST")
			(second(block(secondOrNull as T2)))
		} else {
			@Suppress("UNCHECKED_CAST")
			this as TriDiscriminatedUnion<T1, R2, T3>
		}
	}

	inline fun <R3> flatMapThird(
		block: (T3) -> R3,
	): TriDiscriminatedUnion<T1, T2, R3> {
		return if (isThirdType) {
			@Suppress("UNCHECKED_CAST")
			(third(block(thirdOrNull as T3)))
		} else {
			@Suppress("UNCHECKED_CAST")
			this as TriDiscriminatedUnion<T1, T2, R3>
		}
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
		} else {
			@Suppress("UNCHECKED_CAST")
			Triple(type1Default, type2Default, (thirdOrNull as T3))
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
		} else {
			@Suppress("UNCHECKED_CAST")
			defaults.copy(third = (thirdOrNull as T3))
		}
	}

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
	 * Return a [TriDiscriminatedUnion] with the types reversed
	 */
	fun reverse(): TriDiscriminatedUnion<T3, T2, T1> {
		return when (value) {
			is Value1<T1, T2, T3> -> third(value.value)
			is Value2<T1, T2, T3> -> second(value.value)
			is Value3<T1, T2, T3> -> first(value.value)
		}
	}

	/**
	 * Return a [TriDiscriminatedUnion] with the first two types reversed
	 */
	fun reverseFirstTwo(): TriDiscriminatedUnion<T2, T1, T3> {
		return when (value) {
			is Value1<T1, T2, T3> -> second(value.value)
			is Value2<T1, T2, T3> -> first(value.value)
			is Value3<T1, T2, T3> -> third(value.value)
		}
	}

	/**
	 * Return a [TriDiscriminatedUnion] with the last two types reversed
	 */
	fun reverseLastTwo(): TriDiscriminatedUnion<T1, T3, T2> {
		return when (value) {
			is Value1<T1, T2, T3> -> first(value.value)
			is Value2<T1, T2, T3> -> third(value.value)
			is Value3<T1, T2, T3> -> second(value.value)
		}
	}

	/**
	 * Return a [TriDiscriminatedUnion] with the first and third types reversed
	 */
	fun reverseEnds(): TriDiscriminatedUnion<T3, T2, T1> {
		return when (value) {
			is Value1<T1, T2, T3> -> third(value.value)
			is Value2<T1, T2, T3> -> second(value.value)
			is Value3<T1, T2, T3> -> first(value.value)
		}
	}

	override fun toString() = value.toString()

	private sealed interface Value<T1, T2, T3>

	@JvmInline
	private value class Value1<T1, T2, T3>(val value: T1) : Value<T1, T2, T3> {
		override fun toString() = value.toString()
	}

	@JvmInline
	private value class Value2<T1, T2, T3>(val value: T2) : Value<T1, T2, T3> {
		override fun toString() = value.toString()
	}

	@JvmInline
	private value class Value3<T1, T2, T3>(val value: T3) : Value<T1, T2, T3> {
		override fun toString() = value.toString()
	}

	companion object {
		val <T> TriDiscriminatedUnion<T, T, T>.value
			@JvmStatic get() = when (value) {
				is Value1<T, T, T> -> value.value
				is Value2<T, T, T> -> value.value
				is Value3<T, T, T> -> value.value
			}

		@JvmStatic
		fun <T1, T2, T3> first(value: T1): TriDiscriminatedUnion<T1, T2, T3> {
			return TriDiscriminatedUnion(Value1(value))
		}

		@JvmStatic
		fun <T1, T2, T3> second(value: T2): TriDiscriminatedUnion<T1, T2, T3> {
			return TriDiscriminatedUnion(Value2(value))
		}

		@JvmStatic
		fun <T1, T2, T3> third(value: T3): TriDiscriminatedUnion<T1, T2, T3> {
			return TriDiscriminatedUnion(Value3(value))
		}

		fun <T1, T2> TriDiscriminatedUnion<T1, T2, out Throwable>.toResult(): Result<DiscriminatedUnion<T1, T2>> {
			return map({ Result.success(DiscriminatedUnion.first(it)) }, { Result.success(DiscriminatedUnion.second(it)) }) { Result.failure(it) }
		}

		fun <T1, T2, T3> TriDiscriminatedUnion<T1, T2, T3>.toTriple(): Triple<T1?, T2?, T3?> {
			return map({ Triple(it, null, null) }, { Triple(null, it, null) }) { Triple(null, null, it) }
		}

		@JvmStatic
		fun <T1 : Any, T2 : Any, T3: Any> TriDiscriminatedUnion<T1?, T2?, T3?>?.takeUnlessAnyNull(): TriDiscriminatedUnion<T1, T2, T3>? {
			@Suppress("UNCHECKED_CAST")
			return takeUnless { it?.unionValue == null } as TriDiscriminatedUnion<T1, T2, T3>?
		}

		@JvmStatic
		fun <T1, T2, T3> TriDiscriminatedUnion<T1, T2, T3>?.orNullableTypes(): TriDiscriminatedUnion<T1?, T2?, T3?> {
			return if (this == null) {
				first(null)
			} else {
				@Suppress("UNCHECKED_CAST")
				this as TriDiscriminatedUnion<T1?, T2?, T3?>
			}
		}

		@JvmStatic
		fun <T1, T2, T3> TriDiscriminatedUnion<TriDiscriminatedUnion<T1, T2, T3>, TriDiscriminatedUnion<T1, T2, T3>, TriDiscriminatedUnion<T1, T2, T3>>.flatten(): TriDiscriminatedUnion<T1, T2, T3> {
			return when (value) {
				is Value1<TriDiscriminatedUnion<T1, T2, T3>, TriDiscriminatedUnion<T1, T2, T3>, TriDiscriminatedUnion<T1, T2, T3>> -> value.value
				is Value2<TriDiscriminatedUnion<T1, T2, T3>, TriDiscriminatedUnion<T1, T2, T3>, TriDiscriminatedUnion<T1, T2, T3>> -> value.value
				is Value3<TriDiscriminatedUnion<T1, T2, T3>, TriDiscriminatedUnion<T1, T2, T3>, TriDiscriminatedUnion<T1, T2, T3>> -> value.value
			}
		}

		@JvmStatic
		@JvmName("flattenFirstAndSecond")
		fun <T1, T2, T3> TriDiscriminatedUnion<TriDiscriminatedUnion<T1, T2, T3>, TriDiscriminatedUnion<T1, T2, T3>, T3>.flatten(): TriDiscriminatedUnion<T1, T2, T3> {
			@Suppress("UNCHECKED_CAST")
			return when (value) {
				is Value1<TriDiscriminatedUnion<T1, T2, T3>, TriDiscriminatedUnion<T1, T2, T3>, T3> -> value.value
				is Value2<TriDiscriminatedUnion<T1, T2, T3>, TriDiscriminatedUnion<T1, T2, T3>, T3> -> value.value
				is Value3<TriDiscriminatedUnion<T1, T2, T3>, TriDiscriminatedUnion<T1, T2, T3>, T3> -> this as TriDiscriminatedUnion<T1, T2, T3>
			}
		}

		@JvmStatic
		@JvmName("flattenFirstAndThird")
		fun <T1, T2, T3> TriDiscriminatedUnion<TriDiscriminatedUnion<T1, T2, T3>, T2, TriDiscriminatedUnion<T1, T2, T3>>.flatten(): TriDiscriminatedUnion<T1, T2, T3> {
			@Suppress("UNCHECKED_CAST")
			return when (value) {
				is Value1<TriDiscriminatedUnion<T1, T2, T3>, T2, TriDiscriminatedUnion<T1, T2, T3>> -> value.value
				is Value2<TriDiscriminatedUnion<T1, T2, T3>, T2, TriDiscriminatedUnion<T1, T2, T3>> -> this as TriDiscriminatedUnion<T1, T2, T3>
				is Value3<TriDiscriminatedUnion<T1, T2, T3>, T2, TriDiscriminatedUnion<T1, T2, T3>> -> value.value
			}
		}

		@JvmStatic
		@JvmName("flattenSecondAndThird")
		fun <T1, T2, T3> TriDiscriminatedUnion<T1, TriDiscriminatedUnion<T1, T2, T3>, TriDiscriminatedUnion<T1, T2, T3>>.flatten(): TriDiscriminatedUnion<T1, T2, T3> {
			@Suppress("UNCHECKED_CAST")
			return when (value) {
				is Value1<T1, TriDiscriminatedUnion<T1, T2, T3>, TriDiscriminatedUnion<T1, T2, T3>> -> this as TriDiscriminatedUnion<T1, T2, T3>
				is Value2<T1, TriDiscriminatedUnion<T1, T2, T3>, TriDiscriminatedUnion<T1, T2, T3>> -> value.value
				is Value3<T1, TriDiscriminatedUnion<T1, T2, T3>, TriDiscriminatedUnion<T1, T2, T3>> -> value.value
			}
		}

		@JvmStatic
		@JvmName("flattenFirst")
		fun <T1, T2, T3> TriDiscriminatedUnion<TriDiscriminatedUnion<T1, T2, T3>, T2, T3>.flatten(): TriDiscriminatedUnion<T1, T2, T3> {
			@Suppress("UNCHECKED_CAST")
			return when (value) {
				is Value1<TriDiscriminatedUnion<T1, T2, T3>, T2, T3> -> value.value
				is Value2<TriDiscriminatedUnion<T1, T2, T3>, T2, T3> -> this as TriDiscriminatedUnion<T1, T2, T3>
				is Value3<TriDiscriminatedUnion<T1, T2, T3>, T2, T3> -> this as TriDiscriminatedUnion<T1, T2, T3>
			}
		}

		@JvmStatic
		@JvmName("flattenSecond")
		fun <T1, T2, T3> TriDiscriminatedUnion<T1, TriDiscriminatedUnion<T1, T2, T3>, T3>.flatten(): TriDiscriminatedUnion<T1, T2, T3> {
			@Suppress("UNCHECKED_CAST")
			return when (value) {
				is Value1<T1, TriDiscriminatedUnion<T1, T2, T3>, T3> -> this as TriDiscriminatedUnion<T1, T2, T3>
				is Value2<T1, TriDiscriminatedUnion<T1, T2, T3>, T3> -> value.value
				is Value3<T1, TriDiscriminatedUnion<T1, T2, T3>, T3> -> this as TriDiscriminatedUnion<T1, T2, T3>
			}
		}

		@JvmStatic
		@JvmName("flattenThird")
		fun <T1, T2, T3> TriDiscriminatedUnion<T1, T2, TriDiscriminatedUnion<T1, T2, T3>>.flatten(): TriDiscriminatedUnion<T1, T2, T3> {
			@Suppress("UNCHECKED_CAST")
			return when (value) {
				is Value1<T1, T2, TriDiscriminatedUnion<T1, T2, T3>> -> this as TriDiscriminatedUnion<T1, T2, T3>
				is Value2<T1, T2, TriDiscriminatedUnion<T1, T2, T3>> -> this as TriDiscriminatedUnion<T1, T2, T3>
				is Value3<T1, T2, TriDiscriminatedUnion<T1, T2, T3>> -> value.value
			}
		}

		@JvmStatic
		fun <T1, T2> TriDiscriminatedUnion<T1, T1, T2>.flattenFirstAndSecond(): DiscriminatedUnion<T1, T2> {
			return when (value) {
				is Value1<T1, T1, T2> -> DiscriminatedUnion.first(value.value)
				is Value2<T1, T1, T2> -> DiscriminatedUnion.first(value.value)
				is Value3<T1, T1, T2> -> DiscriminatedUnion.second(value.value)
			}
		}

		@JvmStatic
		fun <T1, T2> TriDiscriminatedUnion<T1, T2, T1>.flattenFirstAndThird(): DiscriminatedUnion<T1, T2> {
			return when (value) {
				is Value1<T1, T2, T1> -> DiscriminatedUnion.first(value.value)
				is Value2<T1, T2, T1> -> DiscriminatedUnion.second(value.value)
				is Value3<T1, T2, T1> -> DiscriminatedUnion.first(value.value)
			}
		}

		@JvmStatic
		fun <T1, T2> TriDiscriminatedUnion<T1, T2, T2>.flattenSecondAndThird(): DiscriminatedUnion<T1, T2> {
			return when (value) {
				is Value1<T1, T2, T2> -> DiscriminatedUnion.first(value.value)
				is Value2<T1, T2, T2> -> DiscriminatedUnion.second(value.value)
				is Value3<T1, T2, T2> -> DiscriminatedUnion.second(value.value)
			}
		}

		@JvmStatic
		fun <T1, T2, T3> Iterable<TriDiscriminatedUnion<T1, T2, T3>>.flattenUnions() = map {
			when (it.value) {
				is Value1<T1, T2, T3> -> it.value.value
				is Value2<T1, T2, T3> -> it.value.value
				is Value3<T1, T2, T3> -> it.value.value
			}
		}

		@JvmStatic
		fun <T1, T2, T3> Sequence<TriDiscriminatedUnion<T1, T2, T3>>.flattenUnions() = map {
			when (it.value) {
				is Value1<T1, T2, T3> -> it.value.value
				is Value2<T1, T2, T3> -> it.value.value
				is Value3<T1, T2, T3> -> it.value.value
			}
		}

		// TODO documentation
		// TODO QuadDiscriminatedUnion functions
		// TODO QuintDiscriminatedUnion functions
		// TODO add JSON support with Jackson JSON and kotlinx.serialization
		// TODO JSON support serialization modes: serialize as first successful, last successful, and type with most non-null fields
	}
}
