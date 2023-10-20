package com.ogm.kotlin.discriminatedunion

@JvmInline
value class DiscriminatedUnion<T1, T2> private constructor(
	private val value: Value<T1, T2>
) {
	val unionValue: Any?
		get() = when (value) {
			is Value1<T1, T2> -> value.value
			is Value2<T1, T2> -> value.value
		}

	val firstOrNull: T1?
		get() = when (value) {
			is Value1<T1, T2> -> value.value
			else -> null
		}

	val secondOrNull: T2?
		get() = when (value) {
			is Value2<T1, T2> -> value.value
			else -> null
		}

	val isFirstType: Boolean
		get() = value is Value1<T1, T2>

	val isSecondType: Boolean
		get() = value is Value2<T1, T2>

	inline fun firstOrThrow(errorMessage: () -> String = { "DiscriminatedUnion is of the second type" }): T1 {
		check(isFirstType, errorMessage)

		@Suppress("UNCHECKED_CAST")
		return firstOrNull as T1
	}

	inline fun secondOrThrow(errorMessage: () -> String = { "DiscriminatedUnion is of the first type" }): T2 {
		check(isSecondType, errorMessage)

		@Suppress("UNCHECKED_CAST")
		return secondOrNull as T2
	}

	fun firstOr(defaultValue: T1): T1 {
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

	fun secondOr(defaultValue: T2): T2 {
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
			@Suppress("UNCHECKED_CAST")
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
			@Suppress("UNCHECKED_CAST")
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
	): DiscriminatedUnion<T1, T2>? {
		return if (isFirstType) {
			@Suppress("UNCHECKED_CAST")
			also { (firstOrNull as T1).type1Apply() }
		} else {
			@Suppress("UNCHECKED_CAST")
			also { (secondOrNull as T2).type2Apply() }
		}
	}

	inline fun applyFirst(
		block: T1.() -> Unit,
	): DiscriminatedUnion<T1, T2>? {
		return if (isFirstType) {
			@Suppress("UNCHECKED_CAST")
			also { (firstOrNull as T1).block() }
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
			also { (secondOrNull as T2).block() }
		}
	}

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

	fun orDefaults(
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

	fun orDefaults(
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

		fun <T1, T2> DiscriminatedUnion<T1, T2>.toPair(): Pair<T1?, T2?> {
			return map({ it to null }) { null to it }
		}
	}
}
