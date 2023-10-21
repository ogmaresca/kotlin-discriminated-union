package com.ogm.kotlin.discriminatedunion

/**
 * @see [Pair]
 * @see [Triple]
 */
data class Quadruple<out T1, out T2, out T3, out T4>(
	val first: T1,
	val second: T2,
	val third: T3,
	val fourth: T4,
) {
	constructor(
		firstAndSecond: Pair<T1, T2>,
		thirdAndFourth: Pair<T3, T4>,
	) : this(firstAndSecond.first, firstAndSecond.second, thirdAndFourth.first, thirdAndFourth.second)

	constructor(
		first: T1,
		second: T2,
		thirdAndFourth: Pair<T3, T4>,
	) : this(first, second, thirdAndFourth.first, thirdAndFourth.second)

	constructor(
		firstAndSecond: Pair<T1, T2>,
		third: T3,
		fourth: T4,
	) : this(firstAndSecond.first, firstAndSecond.second, third, fourth)

	constructor(
		first: T1,
		secondAndThird: Pair<T2, T3>,
		fourth: T4,
	) : this(first, secondAndThird.first, secondAndThird.second, fourth)

	constructor(
		firstSecondThird: Triple<T1, T2, T3>,
		fourth: T4,
	) : this(firstSecondThird.first, firstSecondThird.second, firstSecondThird.third, fourth)

	constructor(
		first: T1,
		secondThirdFourth: Triple<T2, T3, T4>,
	) : this(first, secondThirdFourth.first, secondThirdFourth.second, secondThirdFourth.third)

	fun toPairs(): Pair<Pair<T1, T2>, Pair<T3, T4>> = (first to second) to (third to fourth)

	override fun toString() = "($first, $second, $third, $fourth)"

	companion object {
		@JvmStatic
		fun <T> Quadruple<T, T, T, T>.toList() = listOf(first, second, third, fourth)

		@JvmStatic
		fun <T1, T2> Quadruple<T1, T2, T1, T2>.toListOfPairs() = listOf((first to second), (third to fourth))
	}
}
