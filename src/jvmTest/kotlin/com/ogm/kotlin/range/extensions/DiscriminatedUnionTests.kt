package com.ogm.kotlin.range.extensions

import com.ogm.kotlin.discriminatedunion.DiscriminatedUnion
import com.ogm.kotlin.discriminatedunion.DiscriminatedUnion.Companion.flatten
import com.ogm.kotlin.discriminatedunion.DiscriminatedUnion.Companion.flattenToQuadDiscriminatedUnion
import com.ogm.kotlin.discriminatedunion.DiscriminatedUnion.Companion.flattenToTriDiscriminatedUnion
import com.ogm.kotlin.discriminatedunion.DiscriminatedUnion.Companion.flattenUnions
import com.ogm.kotlin.discriminatedunion.DiscriminatedUnion.Companion.orNullableTypes
import com.ogm.kotlin.discriminatedunion.DiscriminatedUnion.Companion.takeUnlessAllNull
import com.ogm.kotlin.discriminatedunion.DiscriminatedUnion.Companion.thirdTypeIfAllNull
import com.ogm.kotlin.discriminatedunion.DiscriminatedUnion.Companion.toResult
import com.ogm.kotlin.discriminatedunion.QuadDiscriminatedUnion
import com.ogm.kotlin.discriminatedunion.TriDiscriminatedUnion
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalTime
import java.util.UUID
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatIllegalStateException
import org.junit.jupiter.api.Test

class DiscriminatedUnionTests {
	private val union1 = DiscriminatedUnion.first<String, Int>("first")
	private val union2 = DiscriminatedUnion.second<String, Int>(2)

	@Test
	fun gettersTest() {
		assertThat(union1.firstOrNull).isEqualTo("first")
		assertThat(union1.secondOrNull).isNull()
		assertThat(union1.isFirstType).isTrue()
		assertThat(union1.isSecondType).isFalse()
		assertThat(union1.firstOrThrow()).isEqualTo("first")
		assertThat(union1.firstOrThrow { "mock error" }).isEqualTo("first")
		assertThatIllegalStateException()
			.isThrownBy {
				union1.secondOrThrow()
			}.withMessage("DiscriminatedUnion is type 1")
		assertThatIllegalStateException()
			.isThrownBy {
				union1.secondOrThrow { "mock error" }
			}.withMessage("mock error")
		assertThat(union1.firstOr("default")).isEqualTo("first")
		assertThat(union1.firstOrGet { "default" }).isEqualTo("first")
		assertThat(union1.secondOr(1337)).isEqualTo(1337)
		assertThat(union1.secondOrGet { 1337 }).isEqualTo(1337)
		assertThat(union1.toString()).isEqualTo("first")
		assertThat(union1.hashCode()).isEqualTo("first".hashCode())
		assertThat(union1).isNotEqualTo("first")
		assertThat(union1).isEqualTo(union1)
		assertThat(union1).isEqualTo(DiscriminatedUnion.first<String, Int>("first"))
		assertThat(union1).isNotEqualTo(DiscriminatedUnion.first<String, Int>("lorem ispum"))
		assertThat(union1).isNotEqualTo(DiscriminatedUnion.second<String, Int>(1))
		assertThat(union1).isNotEqualTo(union2)
		assertThat(union1.position).isEqualTo(1)

		assertThat(union2.firstOrNull).isNull()
		assertThat(union2.secondOrNull).isEqualTo(2)
		assertThat(union2.isFirstType).isFalse()
		assertThat(union2.isSecondType).isTrue()
		assertThatIllegalStateException()
			.isThrownBy {
				union2.firstOrThrow()
			}.withMessage("DiscriminatedUnion is type 2")
		assertThatIllegalStateException()
			.isThrownBy {
				union2.firstOrThrow { "mock error" }
			}.withMessage("mock error")
		assertThat(union2.secondOrThrow()).isEqualTo(2)
		assertThat(union2.secondOrThrow { "mock error" }).isEqualTo(2)
		assertThat(union2.firstOr("default")).isEqualTo("default")
		assertThat(union2.firstOrGet { "default" }).isEqualTo("default")
		assertThat(union2.secondOr(1337)).isEqualTo(2)
		assertThat(union2.secondOrGet { 1337 }).isEqualTo(2)
		assertThat(union2.toString()).isEqualTo("2")
		assertThat(union2.hashCode()).isEqualTo(2)
		assertThat(union2).isNotEqualTo(2)
		assertThat(union2).isEqualTo(union2)
		assertThat(union2).isNotEqualTo(DiscriminatedUnion.first<String, Int>("2"))
		assertThat(union2).isEqualTo(DiscriminatedUnion.second<String, Int>(2))
		assertThat(union2).isNotEqualTo(DiscriminatedUnion.second<String, Int>(1337))
		assertThat(union2).isNotEqualTo(union1)
		assertThat(union2.position).isEqualTo(2)
	}

	@Test
	fun takeIfTest() {
		assertThat(union1.takeIf(String::isNotBlank, ::isPositive)).isEqualTo(union1)
		assertThat(union1.takeIf(String::isNotBlank, ::isNegative)).isEqualTo(union1)
		assertThat(union1.takeIf(String::isNullOrBlank, ::isPositive)).isNull()
		assertThat(union1.takeIf(String::isNullOrBlank, ::isNegative)).isNull()

		assertThat(union2.takeIf(String::isNotBlank, ::isPositive)).isEqualTo(union2)
		assertThat(union2.takeIf(String::isNotBlank, ::isNegative)).isNull()
		assertThat(union2.takeIf(String::isNullOrBlank, ::isPositive)).isEqualTo(union2)
		assertThat(union2.takeIf(String::isNullOrBlank, ::isNegative)).isNull()

		assertThat(union1.takeIfFirst(String::isNotBlank)).isEqualTo(union1)
		assertThat(union1.takeIfFirst(String::isNullOrBlank)).isNull()
		assertThat(union1.takeIfSecond(::isPositive)).isEqualTo(union1)
		assertThat(union1.takeIfSecond(::isNegative)).isEqualTo(union1)

		assertThat(union2.takeIfFirst(String::isNotBlank)).isEqualTo(union2)
		assertThat(union2.takeIfFirst(String::isNullOrBlank)).isEqualTo(union2)
		assertThat(union2.takeIfSecond(::isPositive)).isEqualTo(union2)
		assertThat(union2.takeIfSecond(::isNegative)).isNull()
	}

	@Test
	fun takeUnlessTest() {
		assertThat(union1.takeUnless(String::isNotBlank, ::isPositive)).isNull()
		assertThat(union1.takeUnless(String::isNotBlank, ::isNegative)).isNull()
		assertThat(union1.takeUnless(String::isNullOrBlank, ::isPositive)).isEqualTo(union1)
		assertThat(union1.takeUnless(String::isNullOrBlank, ::isNegative)).isEqualTo(union1)

		assertThat(union2.takeUnless(String::isNotBlank, ::isPositive)).isNull()
		assertThat(union2.takeUnless(String::isNotBlank, ::isNegative)).isEqualTo(union2)
		assertThat(union2.takeUnless(String::isNullOrBlank, ::isPositive)).isNull()
		assertThat(union2.takeUnless(String::isNullOrBlank, ::isNegative)).isEqualTo(union2)

		assertThat(union1.takeUnlessFirst(String::isNotBlank)).isNull()
		assertThat(union1.takeUnlessFirst(String::isNullOrBlank)).isEqualTo(union1)
		assertThat(union1.takeUnlessSecond(::isPositive)).isEqualTo(union1)
		assertThat(union1.takeUnlessSecond(::isNegative)).isEqualTo(union1)

		assertThat(union2.takeUnlessFirst(String::isNotBlank)).isEqualTo(union2)
		assertThat(union2.takeUnlessFirst(String::isNullOrBlank)).isEqualTo(union2)
		assertThat(union2.takeUnlessSecond(::isPositive)).isNull()
		assertThat(union2.takeUnlessSecond(::isNegative)).isEqualTo(union2)
	}

	@Test
	fun alsoTest() {
		val executed = mutableListOf<String>()
		union1.also({ executed += listOf(it, "union1.also(true, false)") }) { executed += listOf(it.toString(), "union1.also(false, true)") }
		union1.alsoFirst { executed += "union1.alsoFirst(true)" }
		union1.alsoSecond { executed += "union1.alsoSecond(true)" }

		union2.also({ executed += listOf(it, "union2.also(true, false)") }) { executed += listOf(it.toString(), "union2.also(false, true)") }
		union2.alsoFirst { executed += "union2.alsoFirst(true)" }
		union2.alsoSecond { executed += "union2.alsoSecond(true)" }

		assertThat(executed).containsExactly(
			"first",
			"union1.also(true, false)",
			"union1.alsoFirst(true)",
			"2",
			"union2.also(false, true)",
			"union2.alsoSecond(true)",
		)

		DiscriminatedUnion.first<String?, UUID?>(null).alsoFirst {
			assertThat(it).isNull()
			executed += it.toString()
		}
		DiscriminatedUnion.second<String?, UUID?>(null).alsoSecond {
			assertThat(it).isNull()
			executed += it.toString()
		}

		assertThat(executed).containsExactly(
			"first",
			"union1.also(true, false)",
			"union1.alsoFirst(true)",
			"2",
			"union2.also(false, true)",
			"union2.alsoSecond(true)",
			"null",
			"null",
		)
	}

	@Test
	fun applyTest() {
		val executed = mutableListOf<String>()
		union1.apply({ executed += listOf(this, "union1.apply(true, false)") }) { executed += listOf(toString(), "union1.apply(false, true)") }
		union1.applyFirst { executed += "union1.applyFirst(true)" }
		union1.applySecond { executed += "union1.applySecond(true)" }

		union2.apply({ executed += listOf(this, "union2.apply(true, false)") }) { executed += listOf(toString(), "union2.apply(false, true)") }
		union2.applyFirst { executed += "union2.applyFirst(true)" }
		union2.applySecond { executed += "union2.applySecond(true)" }

		assertThat(executed).containsExactly(
			"first",
			"union1.apply(true, false)",
			"union1.applyFirst(true)",
			"2",
			"union2.apply(false, true)",
			"union2.applySecond(true)",
		)
	}

	@Test
	fun mapTest() {
		assertThat(union1.map({ it.repeat(2) }) { (it * it).toString() }).isEqualTo("firstfirst")
		assertThat(union1.mapToFirst { (it * it).toString() }).isEqualTo("first")
		assertThat(union1.mapToSecond { it.first().code }).isEqualTo(102)

		assertThat(union2.map({ it.repeat(2) }) { (it * it).toString() }).isEqualTo("4")
		assertThat(union2.mapToFirst { (it * it).toString() }).isEqualTo("4")
		assertThat(union2.mapToSecond { it.first().code }).isEqualTo(2)
	}

	@Test
	fun flatMapTest() {
		assertThat(union1.flatMap({ it.repeat(2) }) { it * it }).isEqualTo(DiscriminatedUnion.first<String, Int>("firstfirst"))
		assertThat(union1.flatMapFirst { it.repeat(2) }).isEqualTo(DiscriminatedUnion.first<String, Int>("firstfirst"))
		assertThat(union1.flatMapSecond { it * it }).isEqualTo(union1)

		assertThat(union2.flatMap({ it.repeat(2) }) { it * it }).isEqualTo(DiscriminatedUnion.second<String, Int>(4))
		assertThat(union2.flatMapFirst { it.repeat(2) }).isEqualTo(union2)
		assertThat(union2.flatMapSecond { it * it }).isEqualTo(DiscriminatedUnion.second<String, Int>(4))
	}

	@Test
	fun flatMapToTriDiscriminatedUnionTest() {
		assertThat(union1.flatMapFirstToTriDiscriminatedUnion { DiscriminatedUnion.first<String, LocalDate>(it.repeat(2)) })
			.isEqualTo(TriDiscriminatedUnion.first<String, LocalDate, Int>("firstfirst"))
		assertThat(union1.flatMapFirstToTriDiscriminatedUnion { DiscriminatedUnion.second<String, LocalDate>(LocalDate.ofEpochDay(it.first().code.toLong())) })
			.isEqualTo(TriDiscriminatedUnion.second<String, LocalDate, Int>(LocalDate.of(1970, 4, 13)))
		assertThat(union2.flatMapFirstToTriDiscriminatedUnion { DiscriminatedUnion.first<String, LocalDate>(it.repeat(2)) })
			.isEqualTo(TriDiscriminatedUnion.third<String, LocalDate, Int>(2))
		assertThat(union2.flatMapFirstToTriDiscriminatedUnion { DiscriminatedUnion.second<String, LocalDate>(LocalDate.ofEpochDay(it.first().code.toLong())) })
			.isEqualTo(TriDiscriminatedUnion.third<String, LocalDate, Int>(2))

		assertThat(union1.flatMapSecondToTriDiscriminatedUnion { DiscriminatedUnion.first<Long, LocalDate>(it.toLong()) })
			.isEqualTo(TriDiscriminatedUnion.first<String, Long, LocalDate>("first"))
		assertThat(union1.flatMapSecondToTriDiscriminatedUnion { DiscriminatedUnion.second<Long, LocalDate>(LocalDate.ofEpochDay(it.toLong())) })
			.isEqualTo(TriDiscriminatedUnion.first<String, Long, LocalDate>("first"))
		assertThat(union2.flatMapSecondToTriDiscriminatedUnion { DiscriminatedUnion.first<Long, LocalDate>(it.toLong()) })
			.isEqualTo(TriDiscriminatedUnion.second<String, Long, LocalDate>(2L))
		assertThat(union2.flatMapSecondToTriDiscriminatedUnion { DiscriminatedUnion.second<Long, LocalDate>(LocalDate.ofEpochDay(it.toLong())) })
			.isEqualTo(TriDiscriminatedUnion.third<String, Long, LocalDate>(LocalDate.of(1970, 1, 3)))
	}

	@Test
	fun flatMapToQuadDiscriminatedUnionTest() {
		assertThat(union1.flatMapFirstToQuadDiscriminatedUnion { TriDiscriminatedUnion.first<String, LocalDate, LocalTime>(it.repeat(2)) })
			.isEqualTo(QuadDiscriminatedUnion.first<String, LocalDate, LocalTime, Int>("firstfirst"))
		assertThat(union1.flatMapFirstToQuadDiscriminatedUnion { TriDiscriminatedUnion.second<String, LocalDate, LocalTime>(LocalDate.ofEpochDay(it.first().code.toLong())) })
			.isEqualTo(QuadDiscriminatedUnion.second<String, LocalDate, LocalTime, Int>(LocalDate.of(1970, 4, 13)))
		assertThat(union1.flatMapFirstToQuadDiscriminatedUnion { TriDiscriminatedUnion.third<String, LocalDate, LocalTime>(LocalTime.ofSecondOfDay(it.first().code.toLong())) })
			.isEqualTo(QuadDiscriminatedUnion.third<String, LocalDate, LocalTime, Int>(LocalTime.of(0, 1, 42, 0)))
		assertThat(union2.flatMapFirstToQuadDiscriminatedUnion { TriDiscriminatedUnion.first<String, LocalDate, LocalTime>(it.repeat(2)) })
			.isEqualTo(QuadDiscriminatedUnion.fourth<String, LocalDate, LocalTime, Int>(2))
		assertThat(union2.flatMapFirstToQuadDiscriminatedUnion { TriDiscriminatedUnion.second<String, LocalDate, LocalTime>(LocalDate.ofEpochDay(it.first().code.toLong())) })
			.isEqualTo(QuadDiscriminatedUnion.fourth<String, LocalDate, LocalTime, Int>(2))
		assertThat(union2.flatMapFirstToQuadDiscriminatedUnion { TriDiscriminatedUnion.third<String, LocalDate, LocalTime>(LocalTime.ofSecondOfDay(it.first().code.toLong())) })
			.isEqualTo(QuadDiscriminatedUnion.fourth<String, LocalDate, LocalTime, Int>(2))

		assertThat(union1.flatMapSecondToQuadDiscriminatedUnion { TriDiscriminatedUnion.first<BigDecimal, LocalDate, LocalTime>(BigDecimal.valueOf(it.toLong())) })
			.isEqualTo(QuadDiscriminatedUnion.first<String, BigDecimal, LocalDate, LocalTime>("first"))
		assertThat(union1.flatMapSecondToQuadDiscriminatedUnion { TriDiscriminatedUnion.second<BigDecimal, LocalDate, LocalTime>(LocalDate.ofEpochDay(it.toLong())) })
			.isEqualTo(QuadDiscriminatedUnion.first<String, BigDecimal, LocalDate, LocalTime>("first"))
		assertThat(union1.flatMapSecondToQuadDiscriminatedUnion { TriDiscriminatedUnion.third<BigDecimal, LocalDate, LocalTime>(LocalTime.ofSecondOfDay(it.toLong())) })
			.isEqualTo(QuadDiscriminatedUnion.first<String, BigDecimal, LocalDate, LocalTime>("first"))
		assertThat(union2.flatMapSecondToQuadDiscriminatedUnion { TriDiscriminatedUnion.first<BigDecimal, LocalDate, LocalTime>(BigDecimal.valueOf(it.toLong())) })
			.isEqualTo(QuadDiscriminatedUnion.second<String, BigDecimal, LocalDate, LocalTime>(BigDecimal.valueOf(2L)))
		assertThat(union2.flatMapSecondToQuadDiscriminatedUnion { TriDiscriminatedUnion.second<BigDecimal, LocalDate, LocalTime>(LocalDate.ofEpochDay(it.toLong())) })
			.isEqualTo(QuadDiscriminatedUnion.third<String, BigDecimal, LocalDate, LocalTime>(LocalDate.of(1970, 1, 3)))
		assertThat(union2.flatMapSecondToQuadDiscriminatedUnion { TriDiscriminatedUnion.third<BigDecimal, LocalDate, LocalTime>(LocalTime.ofSecondOfDay(it.toLong())) })
			.isEqualTo(QuadDiscriminatedUnion.fourth<String, BigDecimal, LocalDate, LocalTime>(LocalTime.of(0, 0, 2, 0)))
	}

	@Test
	fun anyOfTest() {
		assertThat(union1.anyOf(String::isNotBlank, ::isPositive)).isTrue()
		assertThat(union1.anyOf(true, ::isPositive)).isTrue()
		assertThat(union1.anyOf(false, ::isPositive)).isFalse()
		assertThat(union1.anyOf(String::isNotBlank, true)).isTrue()
		assertThat(union1.anyOf(String::isNotBlank, false)).isTrue()
		assertThat(union1.anyOf(String::isBlank, ::isNegative)).isFalse()
		assertThat(union1.anyOf(true, ::isNegative)).isTrue()
		assertThat(union1.anyOf(false, ::isNegative)).isFalse()
		assertThat(union1.anyOf(String::isBlank, true)).isFalse()
		assertThat(union1.anyOf(String::isBlank, false)).isFalse()
		assertThat(union1.anyOf(String::isNotBlank, ::isNegative)).isTrue()
		assertThat(union1.anyOf(String::isBlank, ::isPositive)).isFalse()

		assertThat(union2.anyOf(String::isNotBlank, ::isPositive)).isTrue()
		assertThat(union2.anyOf(true, ::isPositive)).isTrue()
		assertThat(union2.anyOf(false, ::isPositive)).isTrue()
		assertThat(union2.anyOf(String::isNotBlank, true)).isTrue()
		assertThat(union2.anyOf(String::isNotBlank, false)).isFalse()
		assertThat(union2.anyOf(String::isBlank, ::isNegative)).isFalse()
		assertThat(union2.anyOf(true, ::isNegative)).isFalse()
		assertThat(union2.anyOf(false, ::isNegative)).isFalse()
		assertThat(union2.anyOf(String::isBlank, true)).isTrue()
		assertThat(union2.anyOf(String::isBlank, false)).isFalse()
		assertThat(union2.anyOf(String::isNotBlank, ::isNegative)).isFalse()
		assertThat(union2.anyOf(String::isBlank, ::isPositive)).isTrue()
	}

	@Test
	fun noneOfTest() {
		assertThat(union1.noneOf(String::isNotBlank, ::isPositive)).isFalse()
		assertThat(union1.noneOf(String::isBlank, ::isNegative)).isTrue()
		assertThat(union1.noneOf(String::isNotBlank, ::isNegative)).isFalse()
		assertThat(union1.noneOf(String::isBlank, ::isPositive)).isTrue()

		assertThat(union2.noneOf(String::isNotBlank, ::isPositive)).isFalse()
		assertThat(union2.noneOf(String::isBlank, ::isNegative)).isTrue()
		assertThat(union2.noneOf(String::isNotBlank, ::isNegative)).isTrue()
		assertThat(union2.noneOf(String::isBlank, ::isPositive)).isFalse()
	}

	@Test
	fun reverseTest() {
		assertThat(union1.reverse())
			.isEqualTo(DiscriminatedUnion.second<Int, String>("first"))
			.isNotEqualTo(union1)
			.isNotEqualTo(union2)
		assertThat(union1.reverse().reverse()).isEqualTo(union1)

		assertThat(union2.reverse())
			.isEqualTo(DiscriminatedUnion.first<Int, String>(2))
			.isNotEqualTo(union2)
			.isNotEqualTo(union1)
		assertThat(union2.reverse().reverse()).isEqualTo(union2)
	}

	@Test
	fun toPairTest() {
		assertThat(union1.toPair()).isEqualTo("first" to null)
		assertThat(union2.toPair()).isEqualTo(null to 2)
	}

	@Test
	fun orDefaultsTest() {
		assertThat(union1.orDefaults("lorem ipsum", 1337)).isEqualTo("first" to 1337)
		assertThat(union1.orDefaults("lorem ipsum" to 1337)).isEqualTo("first" to 1337)
		assertThat(union2.orDefaults("lorem ipsum", 1337)).isEqualTo("lorem ipsum" to 2)
		assertThat(union2.orDefaults("lorem ipsum" to 1337)).isEqualTo("lorem ipsum" to 2)
	}

	@Test
	fun fromResultTest() {
		assertThat(DiscriminatedUnion.from(Result.success("lorem ipsum"))).satisfies {
			assertThat(it.position).isEqualTo(1)
			assertThat(it.isFirstType).isTrue()
			assertThat(it.isSecondType).isFalse()
			assertThat(it.firstOrNull).isEqualTo("lorem ipsum")
			assertThat(it.secondOrNull).isNull()
		}
		assertThat(DiscriminatedUnion.from(Result.failure<String>(IllegalStateException("Mock error")))).satisfies {
			assertThat(it.position).isEqualTo(2)
			assertThat(it.isFirstType).isFalse()
			assertThat(it.isSecondType).isTrue()
			assertThat(it.firstOrNull).isNull()
			assertThat(it.secondOrNull).isExactlyInstanceOf(IllegalStateException::class.java).hasMessage("Mock error")
		}
	}

	@Test
	fun toResultTest() {
		assertThat(DiscriminatedUnion.first<String, IllegalStateException>("lorem ipsum").toResult())
			.isEqualTo(Result.success("lorem ipsum"))
			.satisfies {
				assertThat(it.isSuccess).isTrue()
				assertThat(it.isFailure).isFalse()
				assertThat(it.getOrNull()).isEqualTo("lorem ipsum")
				assertThat(it.exceptionOrNull()).isNull()
			}

		assertThat(DiscriminatedUnion.second<String, IllegalStateException>(IllegalStateException("Mock error")).toResult()).satisfies {
			assertThat(it.isSuccess).isFalse()
			assertThat(it.isFailure).isTrue()
			assertThat(it.getOrNull()).isNull()
			assertThat(it.exceptionOrNull()).isExactlyInstanceOf(IllegalStateException::class.java).hasMessage("Mock error")
		}
	}

	@Test
	fun takeUnlessAllNullTest() {
		assertThat(DiscriminatedUnion.first<String?, Int?>(null).takeUnlessAllNull()).isNull()
		assertThat(DiscriminatedUnion.first<String?, Int?>("lorem ipsum").takeUnlessAllNull())
			.isEqualTo(DiscriminatedUnion.first<String, Int>("lorem ipsum"))

		assertThat(DiscriminatedUnion.second<String?, Int?>(null).takeUnlessAllNull()).isNull()
		assertThat(DiscriminatedUnion.second<String?, Int?>(1337).takeUnlessAllNull())
			.isEqualTo(DiscriminatedUnion.second<String, Int>(1337))
	}

	@Test
	fun orNullableTypesTest() {
		assertThat(DiscriminatedUnion.first<String, Int>("lorem ipsum").orNullableTypes())
			.isEqualTo(DiscriminatedUnion.first<String?, Int?>("lorem ipsum"))
		assertThat(DiscriminatedUnion.second<String, Int>(1337).orNullableTypes())
			.isEqualTo(DiscriminatedUnion.second<String?, Int?>(1337))
		assertThat((null as DiscriminatedUnion<String, Int>?).orNullableTypes())
			.isEqualTo(DiscriminatedUnion.first<String?, Int?>(null))
	}

	@Test
	fun flattenTest() {
		assertThat(DiscriminatedUnion.first<DiscriminatedUnion<String, Int>, DiscriminatedUnion<String, Int>>(union1).flatten())
			.isEqualTo(union1)
		assertThat(DiscriminatedUnion.first<DiscriminatedUnion<String, Int>, DiscriminatedUnion<String, Int>>(union2).flatten())
			.isEqualTo(union2)
		assertThat(DiscriminatedUnion.second<DiscriminatedUnion<String, Int>, DiscriminatedUnion<String, Int>>(union1).flatten())
			.isEqualTo(union1)
		assertThat(DiscriminatedUnion.second<DiscriminatedUnion<String, Int>, DiscriminatedUnion<String, Int>>(union2).flatten())
			.isEqualTo(union2)

		assertThat(DiscriminatedUnion.first<DiscriminatedUnion<String, Int>, Int>(union1).flatten())
			.isEqualTo(union1)
		assertThat(DiscriminatedUnion.first<DiscriminatedUnion<String, Int>, Int>(union2).flatten())
			.isEqualTo(union2)
		assertThat(DiscriminatedUnion.second<DiscriminatedUnion<String, Int>, Int>(1337).flatten())
			.isEqualTo(DiscriminatedUnion.second<String, Int>(1337))

		assertThat(DiscriminatedUnion.first<String, DiscriminatedUnion<String, Int>>("lorem ipsum").flatten())
			.isEqualTo(DiscriminatedUnion.first<String, Int>("lorem ipsum"))
		assertThat(DiscriminatedUnion.second<String, DiscriminatedUnion<String, Int>>(union1).flatten())
			.isEqualTo(union1)
		assertThat(DiscriminatedUnion.second<String, DiscriminatedUnion<String, Int>>(union2).flatten())
			.isEqualTo(union2)
	}

	@Test
	fun flattenToTriDiscriminatedUnionTest() {
		assertThat(DiscriminatedUnion.first<DiscriminatedUnion<String, Int>, Boolean>(union1).flattenToTriDiscriminatedUnion())
			.isEqualTo(TriDiscriminatedUnion.first<String, Int, Boolean>("first"))
		assertThat(DiscriminatedUnion.first<DiscriminatedUnion<String, Int>, Boolean>(union2).flattenToTriDiscriminatedUnion())
			.isEqualTo(TriDiscriminatedUnion.second<String, Int, Boolean>(2))
		assertThat(DiscriminatedUnion.second<DiscriminatedUnion<String, Int>, Boolean>(false).flattenToTriDiscriminatedUnion())
			.isEqualTo(TriDiscriminatedUnion.third<String, Int, Boolean>(false))

		assertThat(DiscriminatedUnion.first<Boolean, DiscriminatedUnion<String, Int>>(false).flattenToTriDiscriminatedUnion())
			.isEqualTo(TriDiscriminatedUnion.first<Boolean, String, Int>(false))
		assertThat(DiscriminatedUnion.second<Boolean, DiscriminatedUnion<String, Int>>(union1).flattenToTriDiscriminatedUnion())
			.isEqualTo(TriDiscriminatedUnion.second<Boolean, String, Int>("first"))
		assertThat(DiscriminatedUnion.second<Boolean, DiscriminatedUnion<String, Int>>(union2).flattenToTriDiscriminatedUnion())
			.isEqualTo(TriDiscriminatedUnion.third<Boolean, String, Int>(2))
	}

	@Test
	fun flattenToQuadDiscriminatedUnionTest() {
		val day = LocalDate.of(2023, 10, 21)
		assertThat(DiscriminatedUnion.first<DiscriminatedUnion<String, Int>, DiscriminatedUnion<Boolean, LocalDate>>(union1).flattenToQuadDiscriminatedUnion())
			.isEqualTo(QuadDiscriminatedUnion.first<String, Int, Boolean, LocalDate>("first"))
		assertThat(DiscriminatedUnion.first<DiscriminatedUnion<String, Int>, DiscriminatedUnion<Boolean, LocalDate>>(union2).flattenToQuadDiscriminatedUnion())
			.isEqualTo(QuadDiscriminatedUnion.second<String, Int, Boolean, LocalDate>(2))
		assertThat(DiscriminatedUnion.second<DiscriminatedUnion<String, Int>, DiscriminatedUnion<Boolean, LocalDate>>(DiscriminatedUnion.first(false)).flattenToQuadDiscriminatedUnion())
			.isEqualTo(QuadDiscriminatedUnion.third<String, Int, Boolean, LocalDate>(false))
		assertThat(DiscriminatedUnion.second<DiscriminatedUnion<String, Int>, DiscriminatedUnion<Boolean, LocalDate>>(DiscriminatedUnion.second(day)).flattenToQuadDiscriminatedUnion())
			.isEqualTo(QuadDiscriminatedUnion.fourth<String, Int, Boolean, LocalDate>(day))

		assertThat(DiscriminatedUnion.first<Boolean, TriDiscriminatedUnion<String, Int, LocalDate>>(false).flattenToQuadDiscriminatedUnion())
			.isEqualTo(QuadDiscriminatedUnion.first<Boolean, String, Int, LocalDate>(false))
		assertThat(DiscriminatedUnion.second<Boolean, TriDiscriminatedUnion<String, Int, LocalDate>>(TriDiscriminatedUnion.first("lorem ipsum")).flattenToQuadDiscriminatedUnion())
			.isEqualTo(QuadDiscriminatedUnion.second<Boolean, String, Int, LocalDate>("lorem ipsum"))
		assertThat(DiscriminatedUnion.second<Boolean, TriDiscriminatedUnion<String, Int, LocalDate>>(TriDiscriminatedUnion.second(1337)).flattenToQuadDiscriminatedUnion())
			.isEqualTo(QuadDiscriminatedUnion.third<Boolean, String, Int, LocalDate>(1337))
		assertThat(DiscriminatedUnion.second<Boolean, TriDiscriminatedUnion<String, Int, LocalDate>>(TriDiscriminatedUnion.third(day)).flattenToQuadDiscriminatedUnion())
			.isEqualTo(QuadDiscriminatedUnion.fourth<Boolean, String, Int, LocalDate>(day))

		assertThat(DiscriminatedUnion.first<TriDiscriminatedUnion<Boolean, String, Int>, LocalDate>(TriDiscriminatedUnion.first(false)).flattenToQuadDiscriminatedUnion())
			.isEqualTo(QuadDiscriminatedUnion.first<Boolean, String, Int, LocalDate>(false))
		assertThat(DiscriminatedUnion.first<TriDiscriminatedUnion<Boolean, String, Int>, LocalDate>(TriDiscriminatedUnion.second("lorem ipsum")).flattenToQuadDiscriminatedUnion())
			.isEqualTo(QuadDiscriminatedUnion.second<Boolean, String, Int, LocalDate>("lorem ipsum"))
		assertThat(DiscriminatedUnion.first<TriDiscriminatedUnion<Boolean, String, Int>, LocalDate>(TriDiscriminatedUnion.third(1337)).flattenToQuadDiscriminatedUnion())
			.isEqualTo(QuadDiscriminatedUnion.third<Boolean, String, Int, LocalDate>(1337))
		assertThat(DiscriminatedUnion.second<TriDiscriminatedUnion<Boolean, String, Int>, LocalDate>(day).flattenToQuadDiscriminatedUnion())
			.isEqualTo(QuadDiscriminatedUnion.fourth<Boolean, String, Int, LocalDate>(day))
	}

	@Test
	fun flattenUnionsTest() {
		assertThat(listOf(union1, union2, DiscriminatedUnion.first("lorem ipsum"), DiscriminatedUnion.second(1337)).flattenUnions())
			.isEqualTo(listOf("first", 2, "lorem ipsum", 1337))

		assertThat(sequenceOf(union1, union2, DiscriminatedUnion.first("lorem ipsum"), DiscriminatedUnion.second(1337)).flattenUnions().toList())
			.isEqualTo(listOf("first", 2, "lorem ipsum", 1337))
	}

	@Test
	fun thirdTypeIfAllNullTest() {
		val today = LocalDate.now()

		assertThat(DiscriminatedUnion.first<String?, Int?>("lorem ipsum").thirdTypeIfAllNull { today })
			.isEqualTo(TriDiscriminatedUnion.first<String?, Int?, LocalDate?>("lorem ipsum"))
		assertThat(DiscriminatedUnion.first<String?, Int?>(null).thirdTypeIfAllNull { today })
			.isEqualTo(TriDiscriminatedUnion.third<String?, Int?, LocalDate?>(today))
		assertThat(DiscriminatedUnion.second<String?, Int?>(1337).thirdTypeIfAllNull { today })
			.isEqualTo(TriDiscriminatedUnion.second<String?, Int?, LocalDate?>(1337))
		assertThat(DiscriminatedUnion.second<String?, Int?>(null).thirdTypeIfAllNull { today })
			.isEqualTo(TriDiscriminatedUnion.third<String?, Int?, LocalDate?>(today))
	}

	private companion object {
		fun isPositive(value: Int) = value >= 0

		fun isNegative(value: Int) = value < 0
	}
}
