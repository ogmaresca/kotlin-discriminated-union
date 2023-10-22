package com.ogm.kotlin.range.extensions

import com.ogm.kotlin.discriminatedunion.DiscriminatedUnion
import com.ogm.kotlin.discriminatedunion.QuadDiscriminatedUnion
import com.ogm.kotlin.discriminatedunion.TriDiscriminatedUnion
import com.ogm.kotlin.discriminatedunion.TriDiscriminatedUnion.Companion.flatten
import com.ogm.kotlin.discriminatedunion.TriDiscriminatedUnion.Companion.flattenFirstAndSecond
import com.ogm.kotlin.discriminatedunion.TriDiscriminatedUnion.Companion.flattenFirstAndThird
import com.ogm.kotlin.discriminatedunion.TriDiscriminatedUnion.Companion.flattenSecondAndThird
import com.ogm.kotlin.discriminatedunion.TriDiscriminatedUnion.Companion.flattenToQuadDiscriminatedUnion
import com.ogm.kotlin.discriminatedunion.TriDiscriminatedUnion.Companion.flattenUnions
import com.ogm.kotlin.discriminatedunion.TriDiscriminatedUnion.Companion.fourthTypeIfAllNull
import com.ogm.kotlin.discriminatedunion.TriDiscriminatedUnion.Companion.orNullableTypes
import com.ogm.kotlin.discriminatedunion.TriDiscriminatedUnion.Companion.takeUnlessAllNull
import com.ogm.kotlin.discriminatedunion.TriDiscriminatedUnion.Companion.toResult
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.UUID
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatIllegalStateException
import org.junit.jupiter.api.Test

class TriDiscriminatedUnionTests {
	private val day = LocalDate.of(2023, 10, 21)
	private val y2k = LocalDate.of(2000, 1, 1)
	private val union1 = TriDiscriminatedUnion.first<String, Int, LocalDate>("first")
	private val union2 = TriDiscriminatedUnion.second<String, Int, LocalDate>(2)
	private val union3 = TriDiscriminatedUnion.third<String, Int, LocalDate>(day)

	@Test
	fun gettersTest() {
		assertThat(union1.firstOrNull).isEqualTo("first")
		assertThat(union1.secondOrNull).isNull()
		assertThat(union1.thirdOrNull).isNull()
		assertThat(union1.isFirstType).isTrue()
		assertThat(union1.isSecondType).isFalse()
		assertThat(union1.isThirdType).isFalse()
		assertThat(union1.firstOrThrow()).isEqualTo("first")
		assertThat(union1.firstOrThrow { "mock error" }).isEqualTo("first")
		assertThatIllegalStateException().isThrownBy {
			union1.secondOrThrow()
		}.withMessage("TriDiscriminatedUnion is type 1")
		assertThatIllegalStateException().isThrownBy {
			union1.secondOrThrow { "mock error" }
		}.withMessage("mock error")
		assertThatIllegalStateException().isThrownBy {
			union1.thirdOrThrow()
		}.withMessage("TriDiscriminatedUnion is type 1")
		assertThatIllegalStateException().isThrownBy {
			union1.thirdOrThrow { "mock error" }
		}.withMessage("mock error")
		assertThat(union1.firstOr("default")).isEqualTo("first")
		assertThat(union1.firstOrGet { "default" }).isEqualTo("first")
		assertThat(union1.secondOr(1337)).isEqualTo(1337)
		assertThat(union1.secondOrGet { 1337 }).isEqualTo(1337)
		assertThat(union1.thirdOr(y2k)).isEqualTo(y2k)
		assertThat(union1.thirdOrGet { y2k }).isEqualTo(y2k)
		assertThat(union1.toString()).isEqualTo("first")
		assertThat(union1.hashCode()).isEqualTo("first".hashCode())
		@Suppress("AssertBetweenInconvertibleTypes")
		assertThat(union1).isNotEqualTo("first")
		assertThat(union1).isEqualTo(union1)
		assertThat(union1).isEqualTo(TriDiscriminatedUnion.first<String, Int, LocalDate>("first"))
		assertThat(union1).isNotEqualTo(TriDiscriminatedUnion.first<String, Int, LocalDate>("lorem ispum"))
		assertThat(union1).isNotEqualTo(union2)
		assertThat(union1).isNotEqualTo(union3)
		assertThat(union1.position).isEqualTo(1)

		assertThat(union2.firstOrNull).isNull()
		assertThat(union2.secondOrNull).isEqualTo(2)
		assertThat(union2.thirdOrNull).isNull()
		assertThat(union2.isFirstType).isFalse()
		assertThat(union2.isSecondType).isTrue()
		assertThat(union2.isThirdType).isFalse()
		assertThatIllegalStateException().isThrownBy {
			union2.firstOrThrow()
		}.withMessage("TriDiscriminatedUnion is type 2")
		assertThatIllegalStateException().isThrownBy {
			union2.firstOrThrow { "mock error" }
		}.withMessage("mock error")
		assertThat(union2.secondOrThrow()).isEqualTo(2)
		assertThat(union2.secondOrThrow { "mock error" }).isEqualTo(2)
		assertThatIllegalStateException().isThrownBy {
			union2.thirdOrThrow()
		}.withMessage("TriDiscriminatedUnion is type 2")
		assertThatIllegalStateException().isThrownBy {
			union2.thirdOrThrow { "mock error" }
		}.withMessage("mock error")
		assertThat(union2.firstOr("default")).isEqualTo("default")
		assertThat(union2.firstOrGet { "default" }).isEqualTo("default")
		assertThat(union2.secondOr(1337)).isEqualTo(2)
		assertThat(union2.secondOrGet { 1337 }).isEqualTo(2)
		assertThat(union2.thirdOr(y2k)).isEqualTo(y2k)
		assertThat(union2.thirdOrGet { y2k }).isEqualTo(y2k)
		assertThat(union2.toString()).isEqualTo("2")
		assertThat(union2.hashCode()).isEqualTo(2.hashCode())
		@Suppress("AssertBetweenInconvertibleTypes")
		assertThat(union2).isNotEqualTo(2)
		assertThat(union2).isEqualTo(union2)
		assertThat(union2).isEqualTo(TriDiscriminatedUnion.second<String, Int, LocalDate>(2))
		assertThat(union2).isNotEqualTo(TriDiscriminatedUnion.second<String, Int, LocalDate>(1337))
		assertThat(union2).isNotEqualTo(TriDiscriminatedUnion.first<String, Int, LocalDate>("2"))
		assertThat(union2).isNotEqualTo(TriDiscriminatedUnion.third<String, Int, LocalDate>(LocalDate.ofEpochDay(2)))
		assertThat(union2).isNotEqualTo(union1)
		assertThat(union2).isNotEqualTo(union3)
		assertThat(union2.position).isEqualTo(2)

		assertThat(union3.firstOrNull).isNull()
		assertThat(union3.secondOrNull).isNull()
		assertThat(union3.thirdOrNull).isEqualTo(day)
		assertThat(union3.isFirstType).isFalse()
		assertThat(union3.isSecondType).isFalse()
		assertThat(union3.isThirdType).isTrue()
		assertThatIllegalStateException().isThrownBy {
			union3.firstOrThrow()
		}.withMessage("TriDiscriminatedUnion is type 3")
		assertThatIllegalStateException().isThrownBy {
			union3.firstOrThrow { "mock error" }
		}.withMessage("mock error")
		assertThatIllegalStateException().isThrownBy {
			union3.secondOrThrow()
		}.withMessage("TriDiscriminatedUnion is type 3")
		assertThatIllegalStateException().isThrownBy {
			union3.secondOrThrow { "mock error" }
		}.withMessage("mock error")
		assertThat(union3.thirdOrThrow()).isEqualTo(day)
		assertThat(union3.thirdOrThrow { "mock error" }).isEqualTo(day)
		assertThat(union3.firstOr("default")).isEqualTo("default")
		assertThat(union3.firstOrGet { "default" }).isEqualTo("default")
		assertThat(union3.secondOr(1337)).isEqualTo(1337)
		assertThat(union3.secondOrGet { 1337 }).isEqualTo(1337)
		assertThat(union3.thirdOr(y2k)).isEqualTo(day)
		assertThat(union3.thirdOrGet { y2k }).isEqualTo(day)
		assertThat(union3.toString()).isEqualTo("2023-10-21")
		assertThat(union3.hashCode()).isEqualTo(day.hashCode())
		@Suppress("AssertBetweenInconvertibleTypes")
		assertThat(union3).isNotEqualTo(day)
		assertThat(union3).isEqualTo(union3)
		assertThat(union3).isEqualTo(TriDiscriminatedUnion.third<String, Int, LocalDate>(day))
		assertThat(union3).isNotEqualTo(TriDiscriminatedUnion.third<String, Int, LocalDate>(y2k))
		assertThat(union3).isNotEqualTo(TriDiscriminatedUnion.second<String, Int, LocalDate>(day.toEpochDay().toInt()))
		assertThat(union3).isNotEqualTo(TriDiscriminatedUnion.first<String, Int, LocalDate>(day.toString()))
		assertThat(union3).isNotEqualTo(union1)
		assertThat(union3).isNotEqualTo(union2)
		assertThat(union3.position).isEqualTo(3)
	}

	@Test
	fun takeIfTest() {
		assertThat(listOf(true, false)).`as`("type1").allSatisfy { type1 ->
			fun Boolean.toStringPredicate() = if (this) String::isNotBlank else String::isBlank
			fun Boolean.toIntPredicate() = if (this) ::isPositive else ::isNegative
			fun Boolean.toLocalDatePredicate() = if (this) ::isPositiveDay else ::isNegativeDay

			assertThat(listOf(true, false)).`as`("type2").allSatisfy { type2 ->
				assertThat(listOf(true, false)).`as`("type3").allSatisfy { type3 ->

					assertThat(listOf(union1 to type1, union2 to type2, union3 to type3)).allSatisfy { (it, expected) ->
						if (expected) {
							assertThat(it.takeIf(type1.toStringPredicate(), type2.toIntPredicate(), type3.toLocalDatePredicate())).isEqualTo(it)
						} else {
							assertThat(it.takeIf(type1.toStringPredicate(), type2.toIntPredicate(), type3.toLocalDatePredicate())).isNull()
						}
					}
				}
			}

			assertThat(union1.takeIfFirst(type1.toStringPredicate()))
				.apply { if (type1) isEqualTo(union1) else isNull() }
			assertThat(union2.takeIfFirst(type1.toStringPredicate())).isEqualTo(union2)
			assertThat(union3.takeIfFirst(type1.toStringPredicate())).isEqualTo(union3)

			assertThat(union1.takeIfSecond(type1.toIntPredicate())).isEqualTo(union1)
			assertThat(union2.takeIfSecond(type1.toIntPredicate()))
				.apply { if (type1) isEqualTo(union2) else isNull() }
			assertThat(union3.takeIfSecond(type1.toIntPredicate())).isEqualTo(union3)

			assertThat(union1.takeIfThird(type1.toLocalDatePredicate())).isEqualTo(union1)
			assertThat(union2.takeIfThird(type1.toLocalDatePredicate())).isEqualTo(union2)
			assertThat(union3.takeIfThird(type1.toLocalDatePredicate()))
				.apply { if (type1) isEqualTo(union3) else isNull() }
		}
	}

	@Test
	fun takeUnlessTest() {
		assertThat(listOf(true, false)).`as`("type1").allSatisfy { type1 ->
			fun Boolean.toStringPredicate() = if (this) String::isNotBlank else String::isBlank
			fun Boolean.toIntPredicate() = if (this) ::isPositive else ::isNegative
			fun Boolean.toLocalDatePredicate() = if (this) ::isPositiveDay else ::isNegativeDay

			assertThat(listOf(true, false)).`as`("type2").allSatisfy { type2 ->
				assertThat(listOf(true, false)).`as`("type3").allSatisfy { type3 ->

					assertThat(listOf(union1 to type1, union2 to type2, union3 to type3)).allSatisfy { (it, expected) ->
						if (expected) {
							assertThat(it.takeUnless(type1.toStringPredicate(), type2.toIntPredicate(), type3.toLocalDatePredicate())).isNull()
						} else {
							assertThat(it.takeUnless(type1.toStringPredicate(), type2.toIntPredicate(), type3.toLocalDatePredicate())).isEqualTo(it)
						}
					}
				}
			}

			assertThat(union1.takeUnlessFirst(type1.toStringPredicate()))
				.apply { if (type1) isNull() else isEqualTo(union1) }
			assertThat(union2.takeUnlessFirst(type1.toStringPredicate())).isEqualTo(union2)
			assertThat(union3.takeUnlessFirst(type1.toStringPredicate())).isEqualTo(union3)

			assertThat(union1.takeUnlessSecond(type1.toIntPredicate())).isEqualTo(union1)
			assertThat(union2.takeUnlessSecond(type1.toIntPredicate()))
				.apply { if (type1) isNull() else isEqualTo(union2) }
			assertThat(union3.takeUnlessSecond(type1.toIntPredicate())).isEqualTo(union3)

			assertThat(union1.takeUnlessThird(type1.toLocalDatePredicate())).isEqualTo(union1)
			assertThat(union2.takeUnlessThird(type1.toLocalDatePredicate())).isEqualTo(union2)
			assertThat(union3.takeUnlessThird(type1.toLocalDatePredicate()))
				.apply { if (type1) isNull() else isEqualTo(union3) }
		}
	}

	@Test
	fun alsoTest() {
		val executed = mutableListOf<String>()
		union1.also(
			{ executed += listOf(it, "union1.also(true, false, false)") },
			{ executed += listOf(it.toString(), "union1.also(false, true, false)") },
			{ executed += listOf(it.toString(), "union1.also(false, false, true)") },
		)
		union1.alsoFirst { executed += "union1.alsoFirst(true)" }
		union1.alsoSecond { executed += "union1.alsoSecond(true)" }
		union1.alsoThird { executed += "union1.alsoThird(true)" }

		union2.also(
			{ executed += listOf(it, "union2.also(true, false, false)") },
			{ executed += listOf(it.toString(), "union2.also(false, true, false)") },
			{ executed += listOf(it.toString(), "union2.also(false, false, true)") },
		)
		union2.alsoFirst { executed += "union2.alsoFirst(true)" }
		union2.alsoSecond { executed += "union2.alsoSecond(true)" }
		union2.alsoThird { executed += "union2.alsoThird(true)" }

		union3.also(
			{ executed += listOf(it, "union3.also(true, false, false)") },
			{ executed += listOf(it.toString(), "union3.also(false, true, false)") },
			{ executed += listOf(it.toString(), "union3.also(false, false, true)") },
		)
		union3.alsoFirst { executed += "union3.alsoFirst(true)" }
		union3.alsoSecond { executed += "union3.alsoSecond(true)" }
		union3.alsoThird { executed += "union3.alsoThird(true)" }

		assertThat(executed).containsExactly(
			"first", "union1.also(true, false, false)", "union1.alsoFirst(true)",
			"2", "union2.also(false, true, false)", "union2.alsoSecond(true)",
			"2023-10-21", "union3.also(false, false, true)", "union3.alsoThird(true)",
		)

		TriDiscriminatedUnion.first<String?, UUID?, BigDecimal?>(null).alsoFirst {
			assertThat(it).isNull()
			executed += it.toString()
		}
		TriDiscriminatedUnion.second<String?, UUID?, BigDecimal?>(null).alsoSecond {
			assertThat(it).isNull()
			executed += it.toString()
		}
		TriDiscriminatedUnion.third<String?, UUID?, BigDecimal?>(null).alsoThird {
			assertThat(it).isNull()
			executed += it.toString()
		}

		assertThat(executed).containsExactly(
			"first", "union1.also(true, false, false)", "union1.alsoFirst(true)",
			"2", "union2.also(false, true, false)", "union2.alsoSecond(true)",
			"2023-10-21", "union3.also(false, false, true)", "union3.alsoThird(true)",
			"null", "null", "null",
		)
	}

	@Test
	fun applyTest() {
		val executed = mutableListOf<String>()
		union1.apply(
			{ executed += listOf(this, "union1.apply(true, false, false)") },
			{ executed += listOf(toString(), "union1.apply(false, true, false)") },
			{ executed += listOf(toString(), "union1.apply(false, false, true)") },
		)
		union1.applyFirst { executed += "union1.applyFirst(true)" }
		union1.applySecond { executed += "union1.applySecond(true)" }
		union1.applyThird { executed += "union1.applyThird(true)" }

		union2.apply(
			{ executed += listOf(this, "union2.apply(true, false, false)") },
			{ executed += listOf(toString(), "union2.apply(false, true, false)") },
			{ executed += listOf(toString(), "union2.apply(false, false, true)") },
		)
		union2.applyFirst { executed += "union2.applyFirst(true)" }
		union2.applySecond { executed += "union2.applySecond(true)" }
		union2.applyThird { executed += "union2.applyThird(true)" }

		union3.apply(
			{ executed += listOf(this, "union3.apply(true, false, false)") },
			{ executed += listOf(toString(), "union3.apply(false, true, false)") },
			{ executed += listOf(toString(), "union3.apply(false, false, true)") },
		)
		union3.applyFirst { executed += "union3.applyFirst(true)" }
		union3.applySecond { executed += "union3.applySecond(true)" }
		union3.applyThird { executed += "union3.applyThird(true)" }

		assertThat(executed).containsExactly(
			"first", "union1.apply(true, false, false)", "union1.applyFirst(true)",
			"2", "union2.apply(false, true, false)", "union2.applySecond(true)",
			"2023-10-21", "union3.apply(false, false, true)", "union3.applyThird(true)",
		)
	}

	@Test
	fun mapTest() {
		assertThat(union1.map({ it.repeat(2) }, { (it * it).toString() }, { it.plusDays(1).toString() })).isEqualTo("firstfirst")
		assertThat(union1.mapToFirst({ (it * it).toString() }, { it.plusDays(1).toString() })).isEqualTo("first")
		assertThat(union1.mapToSecond({ it.first().code }, { it.toEpochDay().toInt() })).isEqualTo(102)
		assertThat(union1.mapToThird({ LocalDate.ofEpochDay(it.first().code.toLong()) }, { LocalDate.ofEpochDay(it.toLong()) })).isEqualTo(LocalDate.of(1970, 4, 13))
		assertThat(union1.mapFirstToSecond { it.first().code }).isEqualTo(DiscriminatedUnion.first<Int, LocalDate>(102))
		assertThat(union1.mapFirstToThird { LocalDate.ofEpochDay(it.first().code.toLong()) }).isEqualTo(DiscriminatedUnion.second<Int, LocalDate>(LocalDate.of(1970, 4, 13)))
		assertThat(union1.mapSecondToFirst { (it * it).toString() }).isEqualTo(DiscriminatedUnion.first<String, LocalDate>("first"))
		assertThat(union1.mapSecondToThird { LocalDate.ofEpochDay(it.toLong()) }).isEqualTo(DiscriminatedUnion.first<String, LocalDate>("first"))
		assertThat(union1.mapThirdToFirst { it.plusDays(1).toString() }).isEqualTo(DiscriminatedUnion.first<String, Int>("first"))
		assertThat(union1.mapThirdToSecond { it.toEpochDay().toInt() }).isEqualTo(DiscriminatedUnion.first<String, Int>("first"))

		assertThat(union2.map({ it.repeat(2) }, { (it * it).toString() }, { it.plusDays(1).toString() })).isEqualTo("4")
		assertThat(union2.mapToFirst({ (it * it).toString() }, { it.plusDays(1).toString() })).isEqualTo("4")
		assertThat(union2.mapToSecond({ it.first().code }, { it.toEpochDay().toInt() })).isEqualTo(2)
		assertThat(union2.mapToThird({ LocalDate.ofEpochDay(it.first().code.toLong()) }, { LocalDate.ofEpochDay(it.toLong()) })).isEqualTo(LocalDate.of(1970, 1, 3))
		assertThat(union2.mapFirstToSecond { it.first().code }).isEqualTo(DiscriminatedUnion.first<Int, LocalDate>(2))
		assertThat(union2.mapFirstToThird { LocalDate.ofEpochDay(it.first().code.toLong()) }).isEqualTo(DiscriminatedUnion.first<Int, LocalDate>(2))
		assertThat(union2.mapSecondToFirst { (it * it).toString() }).isEqualTo(DiscriminatedUnion.first<String, LocalDate>("4"))
		assertThat(union2.mapSecondToThird { LocalDate.ofEpochDay(it.toLong()) }).isEqualTo(DiscriminatedUnion.second<String, LocalDate>(LocalDate.of(1970, 1, 3)))
		assertThat(union2.mapThirdToFirst { it.plusDays(1).toString() }).isEqualTo(DiscriminatedUnion.second<String, Int>(2))
		assertThat(union2.mapThirdToSecond { it.toEpochDay().toInt() }).isEqualTo(DiscriminatedUnion.second<String, Int>(2))

		assertThat(union3.map({ it.repeat(2) }, { (it * it).toString() }, { it.plusDays(1).toString() })).isEqualTo("2023-10-22")
		assertThat(union3.mapToFirst({ (it * it).toString() }, { it.plusDays(1).toString() })).isEqualTo("2023-10-22")
		assertThat(union3.mapToSecond({ it.first().code }, { it.toEpochDay().toInt() })).isEqualTo(19651)
		assertThat(union3.mapToThird({ LocalDate.ofEpochDay(it.first().code.toLong()) }, { LocalDate.ofEpochDay(it.toLong()) })).isEqualTo("2023-10-21")
		assertThat(union3.mapFirstToSecond { it.first().code }).isEqualTo(DiscriminatedUnion.second<Int, LocalDate>(day))
		assertThat(union3.mapFirstToThird { LocalDate.ofEpochDay(it.first().code.toLong()) }).isEqualTo(DiscriminatedUnion.second<Int, LocalDate>(day))
		assertThat(union3.mapSecondToFirst { (it * it).toString() }).isEqualTo(DiscriminatedUnion.second<String, LocalDate>(day))
		assertThat(union3.mapSecondToThird { LocalDate.ofEpochDay(it.toLong()) }).isEqualTo(DiscriminatedUnion.second<String, LocalDate>(day))
		assertThat(union3.mapThirdToFirst { it.plusDays(1).toString() }).isEqualTo(DiscriminatedUnion.first<String, Int>("2023-10-22"))
		assertThat(union3.mapThirdToSecond { it.toEpochDay().toInt() }).isEqualTo(DiscriminatedUnion.second<String, Int>(19651))
	}

	@Test
	fun flatMapTest() {
		assertThat(union1.flatMap({ it.repeat(2) }, { LocalTime.ofSecondOfDay(it.toLong()) }, { it.plusDays(1) }))
			.isEqualTo(TriDiscriminatedUnion.first<String, LocalTime, LocalDate>("firstfirst"))
		assertThat(union1.flatMapFirst { it.repeat(2) })
			.isEqualTo(TriDiscriminatedUnion.first<String, Int, LocalDate>("firstfirst"))
		assertThat(union1.flatMapSecond { it * it }).isEqualTo(union1)
		assertThat(union1.flatMapThird { it.atStartOfDay() }).isEqualTo(union1)

		assertThat(union2.flatMap({ it.repeat(2) }, { LocalTime.ofSecondOfDay(it.toLong()) }, { it.plusDays(1) }))
			.isEqualTo(TriDiscriminatedUnion.second<String, LocalTime, LocalDate>(LocalTime.of(0, 0, 2)))
		assertThat(union2.flatMapFirst { it.repeat(2) }).isEqualTo(union2)
		assertThat(union2.flatMapSecond { it * it })
			.isEqualTo(TriDiscriminatedUnion.second<String, Int, LocalDate>(4))
		assertThat(union2.flatMapThird { it.atStartOfDay() }).isEqualTo(union2)

		assertThat(union3.flatMap({ it.repeat(2) }, { LocalTime.ofSecondOfDay(it.toLong()) }, { it.plusDays(1) }))
			.isEqualTo(TriDiscriminatedUnion.third<String, LocalTime, LocalDate>(LocalDate.of(2023, 10, 22)))
		assertThat(union3.flatMapFirst { it.repeat(2) }).isEqualTo(union3)
		assertThat(union3.flatMapSecond { it * it }).isEqualTo(union3)
		assertThat(union3.flatMapThird { it.atStartOfDay() })
			.isEqualTo(TriDiscriminatedUnion.third<String, Int, LocalDateTime>(LocalDateTime.of(day, LocalTime.MIDNIGHT)))
	}

	@Test
	fun flatMapToQuadDiscriminatedUnionTest() {
		assertThat(union1.flatMapFirstToQuadDiscriminatedUnion { DiscriminatedUnion.first<String, LocalTime>(it.repeat(2)) })
			.isEqualTo(QuadDiscriminatedUnion.first<String, LocalTime, Int, LocalDate>("firstfirst"))
		assertThat(union1.flatMapFirstToQuadDiscriminatedUnion { DiscriminatedUnion.second<String, LocalTime>(LocalTime.ofSecondOfDay(it.first().code.toLong())) })
			.isEqualTo(QuadDiscriminatedUnion.second<String, LocalTime, Int, LocalDate>(LocalTime.of(0, 1, 42, 0)))
		assertThat(union2.flatMapFirstToQuadDiscriminatedUnion { DiscriminatedUnion.first<String, LocalTime>(it.repeat(2)) })
			.isEqualTo(QuadDiscriminatedUnion.third<String, LocalTime, Int, LocalDate>(2))
		assertThat(union2.flatMapFirstToQuadDiscriminatedUnion { DiscriminatedUnion.second<String, LocalTime>(LocalTime.ofSecondOfDay(it.first().code.toLong())) })
			.isEqualTo(QuadDiscriminatedUnion.third<String, LocalTime, Int, LocalDate>(2))
		assertThat(union3.flatMapFirstToQuadDiscriminatedUnion { DiscriminatedUnion.first<String, LocalTime>(it.repeat(2)) })
			.isEqualTo(QuadDiscriminatedUnion.fourth<String, LocalTime, Int, LocalDate>(day))
		assertThat(union3.flatMapFirstToQuadDiscriminatedUnion { DiscriminatedUnion.second<String, LocalTime>(LocalTime.ofSecondOfDay(it.first().code.toLong())) })
			.isEqualTo(QuadDiscriminatedUnion.fourth<String, LocalTime, Int, LocalDate>(day))

		assertThat(union1.flatMapSecondToQuadDiscriminatedUnion { DiscriminatedUnion.first<LocalTime, Long>(LocalTime.ofSecondOfDay(it.toLong())) })
			.isEqualTo(QuadDiscriminatedUnion.first<String, LocalTime, Long, LocalDate>("first"))
		assertThat(union1.flatMapSecondToQuadDiscriminatedUnion { DiscriminatedUnion.second<LocalTime, Long>(it.toLong()) })
			.isEqualTo(QuadDiscriminatedUnion.first<String, LocalTime, Long, LocalDate>("first"))
		assertThat(union2.flatMapSecondToQuadDiscriminatedUnion { DiscriminatedUnion.first<LocalTime, Long>(LocalTime.ofSecondOfDay(it.toLong())) })
			.isEqualTo(QuadDiscriminatedUnion.second<String, LocalTime, Long, LocalDate>(LocalTime.of(0, 0, 2, 0)))
		assertThat(union2.flatMapSecondToQuadDiscriminatedUnion { DiscriminatedUnion.second<LocalTime, Long>((it * it).toLong()) })
			.isEqualTo(QuadDiscriminatedUnion.third<String, LocalTime, Long, LocalDate>(4L))
		assertThat(union3.flatMapSecondToQuadDiscriminatedUnion { DiscriminatedUnion.first<LocalTime, Long>(LocalTime.ofSecondOfDay(it.toLong())) })
			.isEqualTo(QuadDiscriminatedUnion.fourth<String, LocalTime, Long, LocalDate>(day))
		assertThat(union3.flatMapSecondToQuadDiscriminatedUnion { DiscriminatedUnion.second<LocalTime, Long>(it.toLong()) })
			.isEqualTo(QuadDiscriminatedUnion.fourth<String, LocalTime, Long, LocalDate>(day))

		assertThat(union1.flatMapThirdToQuadDiscriminatedUnion { DiscriminatedUnion.first<BigDecimal, LocalTime>(BigDecimal.valueOf(it.toEpochDay())) })
			.isEqualTo(QuadDiscriminatedUnion.first<String, Int, BigDecimal, LocalTime>("first"))
		assertThat(union1.flatMapThirdToQuadDiscriminatedUnion { DiscriminatedUnion.second<BigDecimal, LocalTime>(LocalTime.ofSecondOfDay(it.toEpochDay())) })
			.isEqualTo(QuadDiscriminatedUnion.first<String, Int, BigDecimal, LocalTime>("first"))
		assertThat(union2.flatMapThirdToQuadDiscriminatedUnion { DiscriminatedUnion.first<BigDecimal, LocalTime>(BigDecimal.valueOf(it.toEpochDay())) })
			.isEqualTo(QuadDiscriminatedUnion.second<String, Int, BigDecimal, LocalTime>(2))
		assertThat(union2.flatMapThirdToQuadDiscriminatedUnion { DiscriminatedUnion.second<BigDecimal, LocalTime>(LocalTime.ofSecondOfDay(it.toEpochDay())) })
			.isEqualTo(QuadDiscriminatedUnion.second<String, Int, BigDecimal, LocalTime>(2))
		assertThat(union3.flatMapThirdToQuadDiscriminatedUnion { DiscriminatedUnion.first<BigDecimal, LocalTime>(BigDecimal.valueOf(it.toEpochDay())) })
			.isEqualTo(QuadDiscriminatedUnion.third<String, Int, BigDecimal, LocalTime>(BigDecimal.valueOf(19651L)))
		assertThat(union3.flatMapThirdToQuadDiscriminatedUnion { DiscriminatedUnion.second<BigDecimal, LocalTime>(LocalTime.ofSecondOfDay(it.toEpochDay())) })
			.isEqualTo(QuadDiscriminatedUnion.fourth<String, Int, BigDecimal, LocalTime>(LocalTime.of(5, 27, 31)))
	}

	@Test
	fun anyOfTest() {
		assertThat(listOf(true, false)).`as`("type1").allSatisfy { type1 ->
			assertThat(listOf(true, false)).`as`("type2").allSatisfy { type2 ->
				assertThat(listOf(true, false)).`as`("type3").allSatisfy { type3 ->
					fun Boolean.toStringPredicate() = if (this) String::isNotBlank else String::isBlank
					fun Boolean.toIntPredicate() = if (this) ::isPositive else ::isNegative
					fun Boolean.toLocalDatePredicate() = if (this) ::isPositiveDay else ::isNegativeDay

					assertThat(listOf(union1 to type1, union2 to type2, union3 to type3)).allSatisfy { (it, expected) ->
						assertThat(it.anyOf(type1.toStringPredicate(), type2.toIntPredicate(), type3.toLocalDatePredicate())).isEqualTo(expected)
						assertThat(it.anyOf(type1, type2.toIntPredicate(), type3.toLocalDatePredicate())).isEqualTo(expected)
						assertThat(it.anyOf(type1, type2, type3.toLocalDatePredicate())).isEqualTo(expected)
						assertThat(it.anyOf(type1, type2.toIntPredicate(), type3)).isEqualTo(expected)
						assertThat(it.anyOf(type1.toStringPredicate(), type2, type3.toLocalDatePredicate())).isEqualTo(expected)
						assertThat(it.anyOf(type1.toStringPredicate(), type2, type3)).isEqualTo(expected)
						assertThat(it.anyOf(type1.toStringPredicate(), type2.toIntPredicate(), type3)).isEqualTo(expected)
					}
				}
			}
		}
	}

	@Test
	fun noneOfTest() {
		assertThat(listOf(true, false)).`as`("type1").allSatisfy { type1 ->
			assertThat(listOf(true, false)).`as`("type2").allSatisfy { type2 ->
				assertThat(listOf(true, false)).`as`("type3").allSatisfy { type3 ->
					fun Boolean.toStringPredicate() = if (this) String::isNotBlank else String::isBlank
					fun Boolean.toIntPredicate() = if (this) ::isPositive else ::isNegative
					fun Boolean.toLocalDatePredicate() = if (this) ::isPositiveDay else ::isNegativeDay

					assertThat(union1.noneOf(type1.toStringPredicate(), type2.toIntPredicate(), type3.toLocalDatePredicate())).isEqualTo(!type1)
					assertThat(union2.noneOf(type1.toStringPredicate(), type2.toIntPredicate(), type3.toLocalDatePredicate())).isEqualTo(!type2)
					assertThat(union3.noneOf(type1.toStringPredicate(), type2.toIntPredicate(), type3.toLocalDatePredicate())).isEqualTo(!type3)
				}
			}
		}
	}

	@Test
	fun reverseTest() {
		assertThat(union1.reverse())
			.isEqualTo(TriDiscriminatedUnion.third<LocalDate, Int, String>("first"))
			.isNotEqualTo(union1)
			.isNotEqualTo(union2)
			.isNotEqualTo(union3)
		assertThat(union1.reverse().reverse()).isEqualTo(union1)
		assertThat(union1.reverseFirstTwo())
			.isEqualTo(TriDiscriminatedUnion.second<Int, String, LocalDate>("first"))
			.isNotEqualTo(union1)
			.isNotEqualTo(union2)
			.isNotEqualTo(union3)
		assertThat(union1.reverseFirstTwo().reverseFirstTwo()).isEqualTo(union1)
		assertThat(union1.reverseLastTwo())
			.isEqualTo(union1)
			.isNotEqualTo(union2)
			.isNotEqualTo(union3)
		assertThat(union1.reverseLastTwo().reverseLastTwo()).isEqualTo(union1)

		assertThat(union2.reverse())
			.isNotEqualTo(union1)
			.isEqualTo(union2)
			.isNotEqualTo(union3)
		assertThat(union2.reverse().reverse()).isEqualTo(union2)
		assertThat(union2.reverseFirstTwo())
			.isEqualTo(TriDiscriminatedUnion.first<Int, String, LocalDate>(2))
			.isNotEqualTo(union1)
			.isNotEqualTo(union2)
			.isNotEqualTo(union3)
		assertThat(union2.reverseFirstTwo().reverseFirstTwo()).isEqualTo(union2)
		assertThat(union2.reverseLastTwo())
			.isEqualTo(TriDiscriminatedUnion.third<String, LocalDate, Int>(2))
			.isNotEqualTo(union1)
			.isNotEqualTo(union2)
			.isNotEqualTo(union3)
		assertThat(union2.reverseLastTwo().reverseLastTwo()).isEqualTo(union2)

		assertThat(union3.reverse())
			.isEqualTo(TriDiscriminatedUnion.first<LocalDate, Int, String>(day))
			.isNotEqualTo(union1)
			.isNotEqualTo(union2)
			.isNotEqualTo(union3)
		assertThat(union3.reverse().reverse()).isEqualTo(union3)
		assertThat(union3.reverseFirstTwo())
			.isNotEqualTo(union1)
			.isNotEqualTo(union2)
			.isEqualTo(union3)
		assertThat(union3.reverseFirstTwo().reverseFirstTwo()).isEqualTo(union3)
		assertThat(union3.reverseLastTwo())
			.isEqualTo(TriDiscriminatedUnion.second<String, LocalDate, Int>(day))
			.isNotEqualTo(union1)
			.isNotEqualTo(union2)
			.isNotEqualTo(union3)
		assertThat(union3.reverseLastTwo().reverseLastTwo()).isEqualTo(union3)
	}

	@Test
	fun toTripleTest() {
		assertThat(union1.toTriple()).isEqualTo(Triple("first", null, null))
		assertThat(union2.toTriple()).isEqualTo(Triple(null, 2, null))
		assertThat(union3.toTriple()).isEqualTo(Triple(null, null, day))
	}

	@Test
	fun toPair() {
		assertThat(union1.toPair()).isEqualTo(Pair("first", null))
		assertThat(union2.toPair()).isEqualTo(Pair(null, 2))
		assertThat(union3.toPair()).isEqualTo(Pair(null, null))
	}

	@Test
	fun orDefaultsTest() {
		assertThat(union1.orDefaults("lorem ipsum", 1337, y2k))
			.isEqualTo(Triple("first", 1337, y2k))
		assertThat(union1.orDefaults(Triple("lorem ipsum", 1337, y2k)))
			.isEqualTo(Triple("first", 1337, y2k))
		assertThat(union2.orDefaults("lorem ipsum", 1337, y2k))
			.isEqualTo(Triple("lorem ipsum", 2, y2k))
		assertThat(union2.orDefaults(Triple("lorem ipsum", 1337, y2k)))
			.isEqualTo(Triple("lorem ipsum", 2, y2k))
		assertThat(union3.orDefaults("lorem ipsum", 1337, y2k))
			.isEqualTo(Triple("lorem ipsum", 1337, day))
		assertThat(union3.orDefaults(Triple("lorem ipsum", 1337, y2k)))
			.isEqualTo(Triple("lorem ipsum", 1337, day))

		assertThat(union1.orDefaults("lorem ipsum", 1337))
			.isEqualTo(Pair("first", 1337))
		assertThat(union1.orDefaults(Pair("lorem ipsum", 1337)))
			.isEqualTo(Pair("first", 1337))
		assertThat(union2.orDefaults("lorem ipsum", 1337))
			.isEqualTo(Pair("lorem ipsum", 2))
		assertThat(union2.orDefaults(Pair("lorem ipsum", 1337)))
			.isEqualTo(Pair("lorem ipsum", 2))
		assertThat(union3.orDefaults("lorem ipsum", 1337))
			.isEqualTo(Pair("lorem ipsum", 1337))
		assertThat(union3.orDefaults(Pair("lorem ipsum", 1337)))
			.isEqualTo(Pair("lorem ipsum", 1337))
	}

	@Test
	fun toResultTest() {
		assertThat(TriDiscriminatedUnion.first<String, Int, IllegalStateException>("lorem ipsum").toResult())
			.isEqualTo(Result.success(DiscriminatedUnion.first<String, Int>("lorem ipsum")))
			.satisfies {
				assertThat(it.isSuccess).isTrue()
				assertThat(it.isFailure).isFalse()
				assertThat(it.getOrNull()).isEqualTo(DiscriminatedUnion.first<String, Int>("lorem ipsum"))
				assertThat(it.exceptionOrNull()).isNull()
			}

		assertThat(TriDiscriminatedUnion.second<String, Int, IllegalStateException>(1337).toResult())
			.isEqualTo(Result.success(DiscriminatedUnion.second<String, Int>(1337)))
			.satisfies {
				assertThat(it.isSuccess).isTrue()
				assertThat(it.isFailure).isFalse()
				assertThat(it.getOrNull()).isEqualTo(DiscriminatedUnion.second<String, Int>(1337))
				assertThat(it.exceptionOrNull()).isNull()
			}

		assertThat(TriDiscriminatedUnion.third<String, Int, IllegalStateException>(IllegalStateException("Mock error")).toResult()).satisfies {
			assertThat(it.isSuccess).isFalse()
			assertThat(it.isFailure).isTrue()
			assertThat(it.getOrNull()).isNull()
			assertThat(it.exceptionOrNull()).isExactlyInstanceOf(IllegalStateException::class.java).hasMessage("Mock error")
		}
	}

	@Test
	fun takeUnlessAllNullTest() {
		assertThat(TriDiscriminatedUnion.first<String?, Int?, LocalDate?>(null).takeUnlessAllNull()).isNull()
		assertThat(TriDiscriminatedUnion.first<String?, Int?, LocalDate?>("lorem ipsum").takeUnlessAllNull())
			.isEqualTo(TriDiscriminatedUnion.first<String, Int, LocalDate>("lorem ipsum"))

		assertThat(TriDiscriminatedUnion.second<String?, Int?, LocalDate?>(null).takeUnlessAllNull()).isNull()
		assertThat(TriDiscriminatedUnion.second<String?, Int?, LocalDate?>(1337).takeUnlessAllNull())
			.isEqualTo(TriDiscriminatedUnion.second<String, Int, LocalDate>(1337))

		assertThat(TriDiscriminatedUnion.third<String?, Int?, LocalDate?>(null).takeUnlessAllNull()).isNull()
		assertThat(TriDiscriminatedUnion.third<String?, Int?, LocalDate?>(y2k).takeUnlessAllNull())
			.isEqualTo(TriDiscriminatedUnion.third<String, Int, LocalDate>(y2k))
	}

	@Test
	fun orNullableTypesTest() {
		assertThat(TriDiscriminatedUnion.first<String, Int, LocalDate>("lorem ipsum").orNullableTypes())
			.isEqualTo(TriDiscriminatedUnion.first<String?, Int?, LocalDate?>("lorem ipsum"))
		assertThat(TriDiscriminatedUnion.second<String, Int, LocalDate>(1337).orNullableTypes())
			.isEqualTo(TriDiscriminatedUnion.second<String?, Int?, LocalDate?>(1337))
		assertThat(TriDiscriminatedUnion.third<String, Int, LocalDate>(y2k).orNullableTypes())
			.isEqualTo(TriDiscriminatedUnion.third<String?, Int?, LocalDate?>(y2k))
		assertThat((null as TriDiscriminatedUnion<String, Int, LocalDate>?).orNullableTypes())
			.isEqualTo(TriDiscriminatedUnion.first<String?, Int?, LocalDate?>(null))
	}

	@Test
	fun flattenTest() {
		assertThat(TriDiscriminatedUnion.first<TriDiscriminatedUnion<String, Int, LocalDate>, TriDiscriminatedUnion<String, Int, LocalDate>, TriDiscriminatedUnion<String, Int, LocalDate>>(union1).flatten())
			.isEqualTo(union1)
		assertThat(TriDiscriminatedUnion.first<TriDiscriminatedUnion<String, Int, LocalDate>, TriDiscriminatedUnion<String, Int, LocalDate>, TriDiscriminatedUnion<String, Int, LocalDate>>(union2).flatten())
			.isEqualTo(union2)
		assertThat(TriDiscriminatedUnion.first<TriDiscriminatedUnion<String, Int, LocalDate>, TriDiscriminatedUnion<String, Int, LocalDate>, TriDiscriminatedUnion<String, Int, LocalDate>>(union3).flatten())
			.isEqualTo(union3)
		assertThat(TriDiscriminatedUnion.second<TriDiscriminatedUnion<String, Int, LocalDate>, TriDiscriminatedUnion<String, Int, LocalDate>, TriDiscriminatedUnion<String, Int, LocalDate>>(union1).flatten())
			.isEqualTo(union1)
		assertThat(TriDiscriminatedUnion.second<TriDiscriminatedUnion<String, Int, LocalDate>, TriDiscriminatedUnion<String, Int, LocalDate>, TriDiscriminatedUnion<String, Int, LocalDate>>(union2).flatten())
			.isEqualTo(union2)
		assertThat(TriDiscriminatedUnion.second<TriDiscriminatedUnion<String, Int, LocalDate>, TriDiscriminatedUnion<String, Int, LocalDate>, TriDiscriminatedUnion<String, Int, LocalDate>>(union3).flatten())
			.isEqualTo(union3)
		assertThat(TriDiscriminatedUnion.third<TriDiscriminatedUnion<String, Int, LocalDate>, TriDiscriminatedUnion<String, Int, LocalDate>, TriDiscriminatedUnion<String, Int, LocalDate>>(union1).flatten())
			.isEqualTo(union1)
		assertThat(TriDiscriminatedUnion.third<TriDiscriminatedUnion<String, Int, LocalDate>, TriDiscriminatedUnion<String, Int, LocalDate>, TriDiscriminatedUnion<String, Int, LocalDate>>(union2).flatten())
			.isEqualTo(union2)
		assertThat(TriDiscriminatedUnion.third<TriDiscriminatedUnion<String, Int, LocalDate>, TriDiscriminatedUnion<String, Int, LocalDate>, TriDiscriminatedUnion<String, Int, LocalDate>>(union3).flatten())
			.isEqualTo(union3)

		assertThat(TriDiscriminatedUnion.first<TriDiscriminatedUnion<String, Int, LocalDate>, TriDiscriminatedUnion<String, Int, LocalDate>, LocalDate>(union1).flatten())
			.isEqualTo(union1)
		assertThat(TriDiscriminatedUnion.first<TriDiscriminatedUnion<String, Int, LocalDate>, TriDiscriminatedUnion<String, Int, LocalDate>, LocalDate>(union2).flatten())
			.isEqualTo(union2)
		assertThat(TriDiscriminatedUnion.first<TriDiscriminatedUnion<String, Int, LocalDate>, TriDiscriminatedUnion<String, Int, LocalDate>, LocalDate>(union3).flatten())
			.isEqualTo(union3)
		assertThat(TriDiscriminatedUnion.second<TriDiscriminatedUnion<String, Int, LocalDate>, TriDiscriminatedUnion<String, Int, LocalDate>, LocalDate>(union1).flatten())
			.isEqualTo(union1)
		assertThat(TriDiscriminatedUnion.second<TriDiscriminatedUnion<String, Int, LocalDate>, TriDiscriminatedUnion<String, Int, LocalDate>, LocalDate>(union2).flatten())
			.isEqualTo(union2)
		assertThat(TriDiscriminatedUnion.second<TriDiscriminatedUnion<String, Int, LocalDate>, TriDiscriminatedUnion<String, Int, LocalDate>, LocalDate>(union3).flatten())
			.isEqualTo(union3)
		assertThat(TriDiscriminatedUnion.third<TriDiscriminatedUnion<String, Int, LocalDate>, TriDiscriminatedUnion<String, Int, LocalDate>, LocalDate>(y2k).flatten())
			.isEqualTo(TriDiscriminatedUnion.third<String, Int, LocalDate>(y2k))

		assertThat(TriDiscriminatedUnion.first<TriDiscriminatedUnion<String, Int, LocalDate>, Int, TriDiscriminatedUnion<String, Int, LocalDate>>(union1).flatten())
			.isEqualTo(union1)
		assertThat(TriDiscriminatedUnion.first<TriDiscriminatedUnion<String, Int, LocalDate>, Int, TriDiscriminatedUnion<String, Int, LocalDate>>(union2).flatten())
			.isEqualTo(union2)
		assertThat(TriDiscriminatedUnion.first<TriDiscriminatedUnion<String, Int, LocalDate>, Int, TriDiscriminatedUnion<String, Int, LocalDate>>(union3).flatten())
			.isEqualTo(union3)
		assertThat(TriDiscriminatedUnion.second<TriDiscriminatedUnion<String, Int, LocalDate>, Int, TriDiscriminatedUnion<String, Int, LocalDate>>(1337).flatten())
			.isEqualTo(TriDiscriminatedUnion.second<String, Int, LocalDate>(1337))
		assertThat(TriDiscriminatedUnion.third<TriDiscriminatedUnion<String, Int, LocalDate>, Int, TriDiscriminatedUnion<String, Int, LocalDate>>(union1).flatten())
			.isEqualTo(union1)
		assertThat(TriDiscriminatedUnion.third<TriDiscriminatedUnion<String, Int, LocalDate>, Int, TriDiscriminatedUnion<String, Int, LocalDate>>(union2).flatten())
			.isEqualTo(union2)
		assertThat(TriDiscriminatedUnion.third<TriDiscriminatedUnion<String, Int, LocalDate>, Int, TriDiscriminatedUnion<String, Int, LocalDate>>(union3).flatten())
			.isEqualTo(union3)

		assertThat(TriDiscriminatedUnion.first<String, TriDiscriminatedUnion<String, Int, LocalDate>, TriDiscriminatedUnion<String, Int, LocalDate>>("lorem ipsum").flatten())
			.isEqualTo(TriDiscriminatedUnion.first<String, Int, LocalDate>("lorem ipsum"))
		assertThat(TriDiscriminatedUnion.second<String, TriDiscriminatedUnion<String, Int, LocalDate>, TriDiscriminatedUnion<String, Int, LocalDate>>(union1).flatten())
			.isEqualTo(union1)
		assertThat(TriDiscriminatedUnion.second<String, TriDiscriminatedUnion<String, Int, LocalDate>, TriDiscriminatedUnion<String, Int, LocalDate>>(union2).flatten())
			.isEqualTo(union2)
		assertThat(TriDiscriminatedUnion.second<String, TriDiscriminatedUnion<String, Int, LocalDate>, TriDiscriminatedUnion<String, Int, LocalDate>>(union3).flatten())
			.isEqualTo(union3)
		assertThat(TriDiscriminatedUnion.third<String, TriDiscriminatedUnion<String, Int, LocalDate>, TriDiscriminatedUnion<String, Int, LocalDate>>(union1).flatten())
			.isEqualTo(union1)
		assertThat(TriDiscriminatedUnion.third<String, TriDiscriminatedUnion<String, Int, LocalDate>, TriDiscriminatedUnion<String, Int, LocalDate>>(union2).flatten())
			.isEqualTo(union2)
		assertThat(TriDiscriminatedUnion.third<String, TriDiscriminatedUnion<String, Int, LocalDate>, TriDiscriminatedUnion<String, Int, LocalDate>>(union3).flatten())
			.isEqualTo(union3)

		assertThat(TriDiscriminatedUnion.first<TriDiscriminatedUnion<String, Int, LocalDate>, Int, LocalDate>(union1).flatten())
			.isEqualTo(union1)
		assertThat(TriDiscriminatedUnion.first<TriDiscriminatedUnion<String, Int, LocalDate>, Int, LocalDate>(union2).flatten())
			.isEqualTo(union2)
		assertThat(TriDiscriminatedUnion.first<TriDiscriminatedUnion<String, Int, LocalDate>, Int, LocalDate>(union3).flatten())
			.isEqualTo(union3)
		assertThat(TriDiscriminatedUnion.second<TriDiscriminatedUnion<String, Int, LocalDate>, Int, LocalDate>(1337).flatten())
			.isEqualTo(TriDiscriminatedUnion.second<String, Int, LocalDate>(1337))
		assertThat(TriDiscriminatedUnion.third<TriDiscriminatedUnion<String, Int, LocalDate>, Int, LocalDate>(y2k).flatten())
			.isEqualTo(TriDiscriminatedUnion.third<String, Int, LocalDate>(y2k))

		assertThat(TriDiscriminatedUnion.first<String, TriDiscriminatedUnion<String, Int, LocalDate>, LocalDate>("lorem ipsum").flatten())
			.isEqualTo(TriDiscriminatedUnion.first<String, Int, LocalDate>("lorem ipsum"))
		assertThat(TriDiscriminatedUnion.second<String, TriDiscriminatedUnion<String, Int, LocalDate>, LocalDate>(union1).flatten())
			.isEqualTo(union1)
		assertThat(TriDiscriminatedUnion.second<String, TriDiscriminatedUnion<String, Int, LocalDate>, LocalDate>(union2).flatten())
			.isEqualTo(union2)
		assertThat(TriDiscriminatedUnion.second<String, TriDiscriminatedUnion<String, Int, LocalDate>, LocalDate>(union3).flatten())
			.isEqualTo(union3)
		assertThat(TriDiscriminatedUnion.third<String, TriDiscriminatedUnion<String, Int, LocalDate>, LocalDate>(y2k).flatten())
			.isEqualTo(TriDiscriminatedUnion.third<String, Int, LocalDate>(y2k))

		assertThat(TriDiscriminatedUnion.first<String, Int, TriDiscriminatedUnion<String, Int, LocalDate>>("lorem ipsum").flatten())
			.isEqualTo(TriDiscriminatedUnion.first<String, Int, LocalDate>("lorem ipsum"))
		assertThat(TriDiscriminatedUnion.second<String, Int, TriDiscriminatedUnion<String, Int, LocalDate>>(1337).flatten())
			.isEqualTo(TriDiscriminatedUnion.second<String, Int, LocalDate>(1337))
		assertThat(TriDiscriminatedUnion.third<String, Int, TriDiscriminatedUnion<String, Int, LocalDate>>(union1).flatten())
			.isEqualTo(union1)
		assertThat(TriDiscriminatedUnion.third<String, Int, TriDiscriminatedUnion<String, Int, LocalDate>>(union2).flatten())
			.isEqualTo(union2)
		assertThat(TriDiscriminatedUnion.third<String, Int, TriDiscriminatedUnion<String, Int, LocalDate>>(union3).flatten())
			.isEqualTo(union3)

		assertThat(TriDiscriminatedUnion.first<Int, Int, LocalDate>(1337).flattenFirstAndSecond())
			.isEqualTo(DiscriminatedUnion.first<Int, LocalDate>(1337))
		assertThat(TriDiscriminatedUnion.second<Int, Int, LocalDate>(1337).flattenFirstAndSecond())
			.isEqualTo(DiscriminatedUnion.first<Int, LocalDate>(1337))
		assertThat(TriDiscriminatedUnion.third<Int, Int, LocalDate>(y2k).flattenFirstAndSecond())
			.isEqualTo(DiscriminatedUnion.second<Int, LocalDate>(y2k))

		assertThat(TriDiscriminatedUnion.first<Int, LocalDate, LocalDate>(1337).flattenSecondAndThird())
			.isEqualTo(DiscriminatedUnion.first<Int, LocalDate>(1337))
		assertThat(TriDiscriminatedUnion.second<Int, LocalDate, LocalDate>(y2k).flattenSecondAndThird())
			.isEqualTo(DiscriminatedUnion.second<Int, LocalDate>(y2k))
		assertThat(TriDiscriminatedUnion.third<Int, LocalDate, LocalDate>(y2k).flattenSecondAndThird())
			.isEqualTo(DiscriminatedUnion.second<Int, LocalDate>(y2k))

		assertThat(TriDiscriminatedUnion.first<Int, LocalDate, Int>(1337).flattenFirstAndThird())
			.isEqualTo(DiscriminatedUnion.first<Int, LocalDate>(1337))
		assertThat(TriDiscriminatedUnion.second<Int, LocalDate, Int>(y2k).flattenFirstAndThird())
			.isEqualTo(DiscriminatedUnion.second<Int, LocalDate>(y2k))
		assertThat(TriDiscriminatedUnion.third<Int, LocalDate, Int>(1337).flattenFirstAndThird())
			.isEqualTo(DiscriminatedUnion.first<Int, LocalDate>(1337))
	}

	@Test
	fun flattenToQuadDiscriminatedUnionTest() {
		val day = LocalDate.of(2023, 10, 21)
		val uuid = UUID.randomUUID()

		assertThat(TriDiscriminatedUnion.first<DiscriminatedUnion<String, Int>, LocalDate, UUID>(DiscriminatedUnion.first("lorem ipsum")).flattenToQuadDiscriminatedUnion())
			.isEqualTo(QuadDiscriminatedUnion.first<String, Int, LocalDate, UUID>("lorem ipsum"))
		assertThat(TriDiscriminatedUnion.first<DiscriminatedUnion<String, Int>, LocalDate, UUID>(DiscriminatedUnion.second(1337)).flattenToQuadDiscriminatedUnion())
			.isEqualTo(QuadDiscriminatedUnion.second<String, Int, LocalDate, UUID>(1337))
		assertThat(TriDiscriminatedUnion.second<DiscriminatedUnion<String, Int>, LocalDate, UUID>(day).flattenToQuadDiscriminatedUnion())
			.isEqualTo(QuadDiscriminatedUnion.third<String, Int, LocalDate, UUID>(day))
		assertThat(TriDiscriminatedUnion.third<DiscriminatedUnion<String, Int>, LocalDate, UUID>(uuid).flattenToQuadDiscriminatedUnion())
			.isEqualTo(QuadDiscriminatedUnion.fourth<String, Int, LocalDate, UUID>(uuid))

		assertThat(TriDiscriminatedUnion.first<String, DiscriminatedUnion<Int, LocalDate>, UUID>("lorem ipsum").flattenToQuadDiscriminatedUnion())
			.isEqualTo(QuadDiscriminatedUnion.first<String, Int, LocalDate, UUID>("lorem ipsum"))
		assertThat(TriDiscriminatedUnion.second<String, DiscriminatedUnion<Int, LocalDate>, UUID>(DiscriminatedUnion.first(1337)).flattenToQuadDiscriminatedUnion())
			.isEqualTo(QuadDiscriminatedUnion.second<String, Int, LocalDate, UUID>(1337))
		assertThat(TriDiscriminatedUnion.second<String, DiscriminatedUnion<Int, LocalDate>, UUID>(DiscriminatedUnion.second(day)).flattenToQuadDiscriminatedUnion())
			.isEqualTo(QuadDiscriminatedUnion.third<String, Int, LocalDate, UUID>(day))
		assertThat(TriDiscriminatedUnion.third<String, DiscriminatedUnion<Int, LocalDate>, UUID>(uuid).flattenToQuadDiscriminatedUnion())
			.isEqualTo(QuadDiscriminatedUnion.fourth<String, Int, LocalDate, UUID>(uuid))

		assertThat(TriDiscriminatedUnion.first<String, Int, DiscriminatedUnion<LocalDate, UUID>>("lorem ipsum").flattenToQuadDiscriminatedUnion())
			.isEqualTo(QuadDiscriminatedUnion.first<String, Int, LocalDate, UUID>("lorem ipsum"))
		assertThat(TriDiscriminatedUnion.second<String, Int, DiscriminatedUnion<LocalDate, UUID>>(1337).flattenToQuadDiscriminatedUnion())
			.isEqualTo(QuadDiscriminatedUnion.second<String, Int, LocalDate, UUID>(1337))
		assertThat(TriDiscriminatedUnion.third<String, Int, DiscriminatedUnion<LocalDate, UUID>>(DiscriminatedUnion.first(day)).flattenToQuadDiscriminatedUnion())
			.isEqualTo(QuadDiscriminatedUnion.third<String, Int, LocalDate, UUID>(day))
		assertThat(TriDiscriminatedUnion.third<String, Int, DiscriminatedUnion<LocalDate, UUID>>(DiscriminatedUnion.second(uuid)).flattenToQuadDiscriminatedUnion())
			.isEqualTo(QuadDiscriminatedUnion.fourth<String, Int, LocalDate, UUID>(uuid))
	}

	@Test
	fun flattenUnionsTest() {
		assertThat(listOf(union1, union2, union3).flattenUnions())
			.isEqualTo(listOf("first", 2, day))

		assertThat(sequenceOf(union1, union2, union3).flattenUnions().toList())
			.isEqualTo(listOf("first", 2, day))
	}

	@Test
	fun fourthTypeIfAllNullTest() {
		val uuid = UUID.randomUUID()

		assertThat(TriDiscriminatedUnion.first<String?, Int?, LocalDate?>("lorem ipsum").fourthTypeIfAllNull { uuid })
			.isEqualTo(QuadDiscriminatedUnion.first<String?, Int?, LocalDate?, UUID?>("lorem ipsum"))
		assertThat(TriDiscriminatedUnion.first<String?, Int?, LocalDate?>(null).fourthTypeIfAllNull { uuid })
			.isEqualTo(QuadDiscriminatedUnion.fourth<String?, Int?, LocalDate?, UUID?>(uuid))
		assertThat(TriDiscriminatedUnion.second<String?, Int?, LocalDate?>(1337).fourthTypeIfAllNull { uuid })
			.isEqualTo(QuadDiscriminatedUnion.second<String?, Int?, LocalDate?, UUID?>(1337))
		assertThat(TriDiscriminatedUnion.second<String?, Int?, LocalDate?>(null).fourthTypeIfAllNull { uuid })
			.isEqualTo(QuadDiscriminatedUnion.fourth<String?, Int?, LocalDate?, UUID?>(uuid))
		assertThat(TriDiscriminatedUnion.third<String?, Int?, LocalDate?>(y2k).fourthTypeIfAllNull { uuid })
			.isEqualTo(QuadDiscriminatedUnion.third<String?, Int?, LocalDate?, UUID?>(y2k))
		assertThat(TriDiscriminatedUnion.third<String?, Int?, LocalDate?>(null).fourthTypeIfAllNull { uuid })
			.isEqualTo(QuadDiscriminatedUnion.fourth<String?, Int?, LocalDate?, UUID?>(uuid))
	}

	private companion object {
		fun isPositive(value: Int) = value >= 0

		fun isNegative(value: Int) = value < 0

		fun isPositiveDay(value: LocalDate) = value.toEpochDay() >= 0

		fun isNegativeDay(value: LocalDate) = value.toEpochDay() < 0
	}
}
