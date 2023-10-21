package com.ogm.kotlin.discriminatedunion

/**
 * A discriminated union with 3 possible types
 * @see <a href="https://en.wikipedia.org/wiki/Tagged_union">Tagged Union</a>
 * @see [TriDiscriminatedUnion] for the implementation
 */
interface ITriDiscriminatedUnion<T1, T2, T3> {
	val unionValue: Any?

	val firstOrNull: T1?

	val secondOrNull: T2?

	val thirdOrNull: T3?

	val isFirstType: Boolean

	val isSecondType: Boolean

	val isThirdType: Boolean

	val position: Int

	fun firstOr(defaultValue: T1): T1

	fun secondOr(defaultValue: T2): T2

	fun thirdOr(defaultValue: T3): T3

	fun orDefaults(
		type1Default: T1,
		type2Default: T2,
		type3Default: T3,
	): Triple<T1, T2, T3>

	fun orDefaults(
		defaults: Triple<T1, T2, T3>,
	): Triple<T1, T2, T3>
}
