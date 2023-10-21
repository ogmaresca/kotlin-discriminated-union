package com.ogm.kotlin.discriminatedunion

/**
 * A discriminated union with 3 possible types
 * @see <a href="https://en.wikipedia.org/wiki/Tagged_union">Tagged Union</a>
 * @see [TriDiscriminatedUnion] for the implementation
 */
interface IQuadDiscriminatedUnion<T1, T2, T3, T4> : ITriDiscriminatedUnion<T1, T2, T3> {
	val fourthOrNull: T4?

	val isFourthType: Boolean

	fun fourthOr(defaultValue: T4): T4

	fun orDefaults(
		type1Default: T1,
		type2Default: T2,
		type3Default: T3,
		type4Default: T4,
	): Quadruple<T1, T2, T3, T4>

	fun orDefaults(
		defaults: Quadruple<T1, T2, T3, T4>,
	): Quadruple<T1, T2, T3, T4>

	/**
	 * If [isFirstType], return a [Quadruple] with this union's value in the [Quadruple]'s first value and null in the last 3 values
	 * Else if [isSecondType], return a [Quadruple] with this union's value in the [Quadruple]'s second value and null in the first, third, and fourth values
	 * Else if [isThirdType], return a [Quadruple] with null in the first, second, and fourth values and this union's value in the [Quadruple]'s third value
	 * Else if [isFourthType], return a [Quadruple] with null in the first 3 values and this union's value in the [Quadruple]'s fourth value
	 * Else, return a [Quadruple] with null values
	 */
	fun toQuadruple(): Quadruple<T1?, T2?, T3?, T4?>
}
