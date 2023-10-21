package com.ogm.kotlin.discriminatedunion

/**
 * A discriminated union with 2 possible types
 * @see <a href="https://en.wikipedia.org/wiki/Tagged_union">Tagged Union</a>
 * @see [DiscriminatedUnion] for the implementation
 */
interface IDiscriminatedUnion<T1, T2> {
	val unionValue: Any?

	val firstOrNull: T1?

	val secondOrNull: T2?

	val isFirstType: Boolean

	val isSecondType: Boolean

	val position: Int

	fun firstOr(defaultValue: T1): T1

	fun secondOr(defaultValue: T2): T2

	fun orDefaults(
		type1Default: T1,
		type2Default: T2,
	): Pair<T1, T2>

	fun orDefaults(
		defaults: Pair<T1, T2>,
	): Pair<T1, T2>
}
