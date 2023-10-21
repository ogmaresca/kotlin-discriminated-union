package com.ogm.kotlin.discriminatedunion

/**
 * A discriminated union with 3 possible types
 * @see <a href="https://en.wikipedia.org/wiki/Tagged_union">Tagged Union</a>
 * @see [TriDiscriminatedUnion] for the implementation
 */
interface ITriDiscriminatedUnion<T1, T2, T3> : IDiscriminatedUnion<T1, T2> {
	val thirdOrNull: T3?

	val isThirdType: Boolean

	fun thirdOr(defaultValue: T3): T3

	fun orDefaults(
		type1Default: T1,
		type2Default: T2,
		type3Default: T3,
	): Triple<T1, T2, T3>

	fun orDefaults(
		defaults: Triple<T1, T2, T3>,
	): Triple<T1, T2, T3>

	/**
	 * If [isFirstType], return a [Triple] with this union's value in the [Triple]'s first value and null in the second and third values
	 * Else if [isSecondType], return a [Triple] with this union's value in the [Triple]'s second value and null in the first and third values
	 * Else if [isThirdType], return a [Triple] with null in the first and second values and this union's value in the [Triple]'s third value
	 * Else, return a [Triple] with null values
	 */
	fun toTriple(): Triple<T1?, T2?, T3?>
}
