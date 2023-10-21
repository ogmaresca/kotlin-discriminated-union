package com.ogm.kotlin.discriminatedunion

/**
 * A discriminated union with 2 possible types
 * @see <a href="https://en.wikipedia.org/wiki/Tagged_union">Tagged Union</a>
 * @see [TriDiscriminatedUnion] for a discriminated union with 3 types
 */
@JvmInline
value class DiscriminatedUnion<T1, T2> private constructor(
	private val value: Value<T1, T2>
) : IDiscriminatedUnion<T1, T2> {
	@Suppress("IMPLICIT_CAST_TO_ANY")
	override val unionValue
		get() = when (value) {
			is Value1<T1, T2> -> value.value
			is Value2<T1, T2> -> value.value
		}

	override val firstOrNull: T1?
		get() = when (value) {
			is Value1<T1, T2> -> value.value
			else -> null
		}

	override val secondOrNull: T2?
		get() = when (value) {
			is Value2<T1, T2> -> value.value
			else -> null
		}

	override val isFirstType: Boolean
		get() = value is Value1<T1, T2>

	override val isSecondType: Boolean
		get() = value is Value2<T1, T2>

	override val position: Int
		get() = when (value) {
			is Value1<T1, T2> -> 1
			is Value2<T1, T2> -> 2
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

	override fun firstOr(defaultValue: T1): T1 {
		return when (value) {
			is Value1<T1, T2> -> value.value
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
			is Value2<T1, T2> -> value.value
			else -> defaultValue
		}
	}

	inline fun secondOrGet(block: () -> T2): T2 {
		return if (isFirstType) {
			block()
		} else {
			@Suppress("UNCHECKED_CAST")
			secondOrNull as T2
		}
	}

	inline fun takeIf(
		type1Predicate: (T1) -> Boolean,
		type2Predicate: (T2) -> Boolean,
	): DiscriminatedUnion<T1, T2>? {
		return if (isFirstType) {
			@Suppress("UNCHECKED_CAST")
			takeIf { type1Predicate(firstOrNull as T1) }
		} else {
			@Suppress("UNCHECKED_CAST")
			takeIf { type2Predicate(secondOrNull as T2) }
		}
	}

	inline fun takeIfFirst(
		predicate: (T1) -> Boolean,
	): DiscriminatedUnion<T1, T2>? {
		return if (isFirstType) {
			@Suppress("UNCHECKED_CAST")
			takeIf { predicate(firstOrNull as T1) }
		} else {
			this
		}
	}

	inline fun takeIfSecond(
		predicate: (T2) -> Boolean,
	): DiscriminatedUnion<T1, T2>? {
		return if (isFirstType) {
			this
		} else {
			@Suppress("UNCHECKED_CAST")
			takeIf { predicate(secondOrNull as T2) }
		}
	}

	inline fun takeUnless(
		type1Predicate: (T1) -> Boolean,
		type2Predicate: (T2) -> Boolean,
	): DiscriminatedUnion<T1, T2>? {
		return if (isFirstType) {
			@Suppress("UNCHECKED_CAST")
			takeUnless { type1Predicate(firstOrNull as T1) }
		} else {
			@Suppress("UNCHECKED_CAST")
			takeUnless { type2Predicate(secondOrNull as T2) }
		}
	}

	inline fun takeUnlessFirst(
		predicate: (T1) -> Boolean,
	): DiscriminatedUnion<T1, T2>? {
		return if (isFirstType) {
			@Suppress("UNCHECKED_CAST")
			takeUnless { predicate(firstOrNull as T1) }
		} else {
			this
		}
	}

	inline fun takeUnlessSecond(
		predicate: (T2) -> Boolean,
	): DiscriminatedUnion<T1, T2>? {
		return if (isFirstType) {
			this
		} else {
			@Suppress("UNCHECKED_CAST")
			takeUnless { predicate(secondOrNull as T2) }
		}
	}

	inline fun also(
		type1Also: (T1) -> Unit,
		type2Also: (T2) -> Unit,
	): DiscriminatedUnion<T1, T2> {
		return if (isFirstType) {
			@Suppress("UNCHECKED_CAST")
			also { type1Also(firstOrNull as T1) }
		} else {
			@Suppress("UNCHECKED_CAST")
			also { type2Also(secondOrNull as T2) }
		}
	}

	inline fun alsoFirst(
		block: (T1) -> Unit,
	): DiscriminatedUnion<T1, T2> {
		return if (isFirstType) {
			@Suppress("UNCHECKED_CAST")
			also { block(firstOrNull as T1) }
		} else {
			this
		}
	}

	inline fun alsoSecond(
		block: (T2) -> Unit,
	): DiscriminatedUnion<T1, T2> {
		return if (isFirstType) {
			this
		} else {
			@Suppress("UNCHECKED_CAST")
			also { block(secondOrNull as T2) }
		}
	}

	inline fun apply(
		type1Apply: T1.() -> Unit,
		type2Apply: T2.() -> Unit,
	): DiscriminatedUnion<T1, T2> {
		return if (isFirstType) {
			@Suppress("UNCHECKED_CAST")
			apply { (firstOrNull as T1).type1Apply() }
		} else {
			@Suppress("UNCHECKED_CAST")
			apply { (secondOrNull as T2).type2Apply() }
		}
	}

	inline fun applyFirst(
		block: T1.() -> Unit,
	): DiscriminatedUnion<T1, T2> {
		return if (isFirstType) {
			@Suppress("UNCHECKED_CAST")
			apply { (firstOrNull as T1).block() }
		} else {
			this
		}
	}

	inline fun applySecond(
		block: T2.() -> Unit,
	): DiscriminatedUnion<T1, T2> {
		return if (isFirstType) {
			this
		} else {
			@Suppress("UNCHECKED_CAST")
			apply { (secondOrNull as T2).block() }
		}
	}

	/**
	 * If [isFirstType], execute [type1Block] on the value and return the result.
	 * Else, execute [type2Block] on the value and return the result.
	 */
	inline fun <R> map(
		type1Block: (T1) -> R,
		type2Block: (T2) -> R,
	): R {
		return if (isFirstType) {
			@Suppress("UNCHECKED_CAST")
			type1Block(firstOrNull as T1)
		} else {
			@Suppress("UNCHECKED_CAST")
			type2Block(secondOrNull as T2)
		}
	}

	/**
	 * If [isFirstType] is true, return the value directly.
	 * Else, execute [block] on the value and return the result.
	 */
	inline fun mapToFirst(
		block: (T2) -> T1,
	): T1 {
		return if (isFirstType) {
			@Suppress("UNCHECKED_CAST")
			firstOrNull as T1
		} else {
			@Suppress("UNCHECKED_CAST")
			block(secondOrNull as T2)
		}
	}

	/**
	 * If [isSecondType] is true, return the value directly.
	 * Else, execute [block] on the value and return the result.
	 */
	inline fun mapToSecond(
		block: (T1) -> T2,
	): T2 {
		return if (isFirstType) {
			@Suppress("UNCHECKED_CAST")
			block(firstOrNull as T1)
		} else {
			@Suppress("UNCHECKED_CAST")
			secondOrNull as T2
		}
	}

	inline fun <R1, R2> flatMap(
		type1Block: (T1) -> R1,
		type2Block: (T2) -> R2,
	): DiscriminatedUnion<R1, R2> {
		return if (isFirstType) {
			@Suppress("UNCHECKED_CAST")
			first(type1Block(firstOrNull as T1))
		} else {
			@Suppress("UNCHECKED_CAST")
			second(type2Block(secondOrNull as T2))
		}
	}

	inline fun <R1> flatMapFirst(
		block: (T1) -> R1,
	): DiscriminatedUnion<R1, T2> {
		return if (isFirstType) {
			@Suppress("UNCHECKED_CAST")
			first(block(firstOrNull as T1))
		} else {
			@Suppress("UNCHECKED_CAST")
			this as DiscriminatedUnion<R1, T2>
		}
	}

	inline fun <R2> flatMapSecond(
		block: (T2) -> R2,
	): DiscriminatedUnion<T1, R2> {
		return if (isFirstType) {
			@Suppress("UNCHECKED_CAST")
			this as DiscriminatedUnion<T1, R2>
		} else {
			@Suppress("UNCHECKED_CAST")
			(second(block(secondOrNull as T2)))
		}
	}

	override fun orDefaults(
		type1Default: T1,
		type2Default: T2,
	): Pair<T1, T2> {
		return if (isFirstType) {
			@Suppress("UNCHECKED_CAST")
			(firstOrNull as T1) to type2Default
		} else {
			@Suppress("UNCHECKED_CAST")
			type1Default to (secondOrNull as T2)
		}
	}

	override fun orDefaults(
		defaults: Pair<T1, T2>,
	): Pair<T1, T2> {
		return if (isFirstType) {
			@Suppress("UNCHECKED_CAST")
			defaults.copy(first = (firstOrNull as T1))
		} else {
			@Suppress("UNCHECKED_CAST")
			defaults.copy(second = (secondOrNull as T2))
		}
	}

	/**
	 * If [isFirstType], return a [Pair] with this union's value in the [Pair]'s first value and null in the second value
	 * Else, return a [Pair] with null in the first value and this union's value in the [Pair]'s second value
	 */
	fun toPair(): Pair<T1?, T2?> {
		return map({ it to null }) { null to it }
	}

	/**
	 * If [isFirstType] is true, execute [type1Predicate] on the value of the first type and return the result.
	 * Else, execute [type2Predicate] on the value of the second type and return the result.
	 */
	inline fun anyOf(
		type1Predicate: (T1) -> Boolean,
		type2Predicate: (T2) -> Boolean,
	): Boolean {
		return if (isFirstType) {
			@Suppress("UNCHECKED_CAST")
			type1Predicate(firstOrNull as T1)
		} else {
			@Suppress("UNCHECKED_CAST")
			type2Predicate(secondOrNull as T2)
		}
	}

	/**
	 * If [isFirstType] is true, return [type1Predicate].
	 * Else, execute [type2Predicate] on the value of the second type and return the result.
	 */
	inline fun anyOf(
		type1Predicate: Boolean,
		type2Predicate: (T2) -> Boolean,
	): Boolean {
		return if (isFirstType) {
			type1Predicate
		} else {
			@Suppress("UNCHECKED_CAST")
			type2Predicate(secondOrNull as T2)
		}
	}

	/**
	 * If [isFirstType] is true, execute [type1Predicate] on the value of the first type and return the result.
	 * Else, return [type2Predicate].
	 */
	inline fun anyOf(
		type1Predicate: (T1) -> Boolean,
		type2Predicate: Boolean,
	): Boolean {
		return if (isFirstType) {
			@Suppress("UNCHECKED_CAST")
			type1Predicate(firstOrNull as T1)
		} else {
			type2Predicate
		}
	}

	/**
	 * If [isFirstType] is true, execute [type1Predicate] on the value of the first type and return true if [type1Predicate] returns false.
	 * Else, execute [type2Predicate] on the value of the second type and return true if [type2Predicate] returns false.
	 */
	inline fun noneOf(
		type1Predicate: (T1) -> Boolean,
		type2Predicate: (T2) -> Boolean,
	): Boolean {
		return if (isFirstType) {
			@Suppress("UNCHECKED_CAST")
			!type1Predicate(firstOrNull as T1)
		} else {
			@Suppress("UNCHECKED_CAST")
			!type2Predicate(secondOrNull as T2)
		}
	}

	/**
	 * Return a [DiscriminatedUnion] with the types reversed
	 */
	fun reverse(): DiscriminatedUnion<T2, T1> {
		return when (value) {
			is Value1<T1, T2> -> second(value.value)
			is Value2<T1, T2> -> first(value.value)
		}
	}

	override fun toString() = value.toString()

	private sealed interface Value<T1, T2>

	@JvmInline
	private value class Value1<T1, T2>(val value: T1) : Value<T1, T2> {
		override fun toString() = value.toString()
	}

	@JvmInline
	private value class Value2<T1, T2>(val value: T2) : Value<T1, T2> {
		override fun toString() = value.toString()
	}

	companion object {
		val <T> DiscriminatedUnion<T, T>.value
			@JvmStatic get() = when (value) {
				is Value1<T, T> -> value.value
				is Value2<T, T> -> value.value
			}

		@JvmStatic
		val <T1 : Any, T2 : Any> DiscriminatedUnion<T1?, T2?>?.allNull: Boolean
			get() = takeUnless { it?.unionValue == null } == null

		@JvmStatic
		fun <T1, T2> first(value: T1): DiscriminatedUnion<T1, T2> {
			return DiscriminatedUnion(Value1(value))
		}

		@JvmStatic
		fun <T1, T2> second(value: T2): DiscriminatedUnion<T1, T2> {
			return DiscriminatedUnion(Value2(value))
		}

		fun <T> DiscriminatedUnion<T, out Throwable>.toResult(): Result<T> {
			return map({ Result.success(it) }) { Result.failure(it) }
		}

		@JvmStatic
		fun <T1 : Any, T2 : Any> DiscriminatedUnion<T1?, T2?>?.takeUnlessAllNull(): DiscriminatedUnion<T1, T2>? {
			@Suppress("UNCHECKED_CAST")
			return takeUnless { it?.unionValue == null } as DiscriminatedUnion<T1, T2>?
		}

		@JvmStatic
		fun <T1, T2> DiscriminatedUnion<T1, T2>?.orNullableTypes(): DiscriminatedUnion<T1?, T2?> {
			return if (this == null) {
				first(null)
			} else {
				@Suppress("UNCHECKED_CAST")
				this as DiscriminatedUnion<T1?, T2?>
			}
		}

		@JvmStatic
		fun <T1, T2> DiscriminatedUnion<DiscriminatedUnion<T1, T2>, DiscriminatedUnion<T1, T2>>.flatten(): DiscriminatedUnion<T1, T2> {
			return when (value) {
				is Value1<DiscriminatedUnion<T1, T2>, DiscriminatedUnion<T1, T2>> -> value.value
				is Value2<DiscriminatedUnion<T1, T2>, DiscriminatedUnion<T1, T2>> -> value.value
			}
		}

		@JvmStatic
		@JvmName("flattenFirst")
		fun <T1, T2> DiscriminatedUnion<DiscriminatedUnion<T1, T2>, T2>.flatten(): DiscriminatedUnion<T1, T2> {
			@Suppress("UNCHECKED_CAST")
			return when (value) {
				is Value1<DiscriminatedUnion<T1, T2>, T2> -> value.value
				is Value2<DiscriminatedUnion<T1, T2>, T2> -> this as DiscriminatedUnion<T1, T2>
			}
		}

		@JvmStatic
		@JvmName("flattenSecond")
		fun <T1, T2> DiscriminatedUnion<T1, DiscriminatedUnion<T1, T2>>.flatten(): DiscriminatedUnion<T1, T2> {
			@Suppress("UNCHECKED_CAST")
			return when (value) {
				is Value1<T1, DiscriminatedUnion<T1, T2>> -> this as DiscriminatedUnion<T1, T2>
				is Value2<T1, DiscriminatedUnion<T1, T2>> -> value.value
			}
		}

		@JvmStatic
		fun <T1, T2> Iterable<DiscriminatedUnion<T1, T2>>.flattenUnions() = map {
			when (it.value) {
				is Value1<T1, T2> -> it.value.value
				is Value2<T1, T2> -> it.value.value
			}
		}

		@JvmStatic
		fun <T1, T2> Sequence<DiscriminatedUnion<T1, T2>>.flattenUnions() = map {
			when (it.value) {
				is Value1<T1, T2> -> it.value.value
				is Value2<T1, T2> -> it.value.value
			}
		}

		@JvmStatic
		inline fun <T1 : Any, T2 : Any, T3> DiscriminatedUnion<T1?, T2?>?.thirdTypeIfAllNull(thirdValue: () -> T3): TriDiscriminatedUnion<T1, T2, T3> {
			return if (allNull) {
				return TriDiscriminatedUnion.third(thirdValue())
			} else if (this!!.isFirstType) {
				TriDiscriminatedUnion.first(firstOrNull!!)
			} else {
				TriDiscriminatedUnion.second(secondOrNull!!)
			}
		}

		// TODO documentation
		// TODO add JSON support with Jackson JSON and kotlinx.serialization
		// TODO JSON support serialization modes: serialize as first successful, last successful, and type with most non-null fields
	}
}
