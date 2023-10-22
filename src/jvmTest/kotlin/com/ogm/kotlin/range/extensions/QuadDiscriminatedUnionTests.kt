package com.ogm.kotlin.range.extensions

import com.ogm.kotlin.discriminatedunion.QuadDiscriminatedUnion
import com.ogm.kotlin.discriminatedunion.QuadDiscriminatedUnion.Companion.flattenUnions
import com.ogm.kotlin.discriminatedunion.QuadDiscriminatedUnion.Companion.orNullableTypes
import com.ogm.kotlin.discriminatedunion.QuadDiscriminatedUnion.Companion.takeUnlessAllNull
import com.ogm.kotlin.discriminatedunion.QuadDiscriminatedUnion.Companion.toResult
import com.ogm.kotlin.discriminatedunion.Quadruple
import com.ogm.kotlin.discriminatedunion.TriDiscriminatedUnion
import java.math.BigDecimal
import java.time.Instant
import java.time.LocalDate
import java.util.UUID
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatIllegalStateException
import org.junit.jupiter.api.Test

class QuadDiscriminatedUnionTests {
	private val day = LocalDate.of(2023, 10, 21)
	private val y2k = LocalDate.of(2000, 1, 1)
	private val uuid1 = UUID.randomUUID()
	private val uuid2 = UUID.randomUUID()
	private val union1 = QuadDiscriminatedUnion.first<String, Int, LocalDate, UUID>("first")
	private val union2 = QuadDiscriminatedUnion.second<String, Int, LocalDate, UUID>(2)
	private val union3 = QuadDiscriminatedUnion.third<String, Int, LocalDate, UUID>(day)
	private val union4 = QuadDiscriminatedUnion.fourth<String, Int, LocalDate, UUID>(uuid1)

	@Test
	fun gettersTest() {
		assertThat(union1.firstOrNull).isEqualTo("first")
		assertThat(union1.secondOrNull).isNull()
		assertThat(union1.thirdOrNull).isNull()
		assertThat(union1.fourthOrNull).isNull()
		assertThat(union1.isFirstType).isTrue()
		assertThat(union1.isSecondType).isFalse()
		assertThat(union1.isThirdType).isFalse()
		assertThat(union1.isFourthType).isFalse()
		assertThat(union1.firstOrThrow()).isEqualTo("first")
		assertThat(union1.firstOrThrow { "mock error" }).isEqualTo("first")
		assertThatIllegalStateException().isThrownBy {
			union1.secondOrThrow()
		}.withMessage("QuadDiscriminatedUnion is type 1")
		assertThatIllegalStateException().isThrownBy {
			union1.secondOrThrow { "mock error" }
		}.withMessage("mock error")
		assertThatIllegalStateException().isThrownBy {
			union1.thirdOrThrow()
		}.withMessage("QuadDiscriminatedUnion is type 1")
		assertThatIllegalStateException().isThrownBy {
			union1.thirdOrThrow { "mock error" }
		}.withMessage("mock error")
		assertThatIllegalStateException().isThrownBy {
			union1.fourthOrThrow()
		}.withMessage("QuadDiscriminatedUnion is type 1")
		assertThatIllegalStateException().isThrownBy {
			union1.fourthOrThrow { "mock error" }
		}.withMessage("mock error")
		assertThat(union1.firstOr("default")).isEqualTo("first")
		assertThat(union1.firstOrGet { "default" }).isEqualTo("first")
		assertThat(union1.secondOr(1337)).isEqualTo(1337)
		assertThat(union1.secondOrGet { 1337 }).isEqualTo(1337)
		assertThat(union1.thirdOr(y2k)).isEqualTo(y2k)
		assertThat(union1.thirdOrGet { y2k }).isEqualTo(y2k)
		assertThat(union1.fourthOr(uuid2)).isEqualTo(uuid2)
		assertThat(union1.fourthOrGet { uuid2 }).isEqualTo(uuid2)
		assertThat(union1.toString()).isEqualTo("first")
		assertThat(union1.hashCode()).isEqualTo("first".hashCode())
		@Suppress("AssertBetweenInconvertibleTypes")
		assertThat(union1).isNotEqualTo("first")
		assertThat(union1).isEqualTo(union1)
		assertThat(union1).isEqualTo(QuadDiscriminatedUnion.first<String, Int, LocalDate, UUID>("first"))
		assertThat(union1).isNotEqualTo(QuadDiscriminatedUnion.first<String, Int, LocalDate, UUID>("lorem ispum"))
		assertThat(union1).isNotEqualTo(QuadDiscriminatedUnion.fourth<String, Int, LocalDate, UUID>(uuid1))
		assertThat(union1).isNotEqualTo(QuadDiscriminatedUnion.fourth<String, Int, LocalDate, UUID>(uuid2))
		assertThat(union1).isNotEqualTo(union2)
		assertThat(union1).isNotEqualTo(union3)
		assertThat(union1).isNotEqualTo(union4)
		assertThat(union1.position).isEqualTo(1)

		assertThat(union2.firstOrNull).isNull()
		assertThat(union2.secondOrNull).isEqualTo(2)
		assertThat(union2.thirdOrNull).isNull()
		assertThat(union2.fourthOrNull).isNull()
		assertThat(union2.isFirstType).isFalse()
		assertThat(union2.isSecondType).isTrue()
		assertThat(union2.isThirdType).isFalse()
		assertThat(union2.isFourthType).isFalse()
		assertThatIllegalStateException().isThrownBy {
			union2.firstOrThrow()
		}.withMessage("QuadDiscriminatedUnion is type 2")
		assertThatIllegalStateException().isThrownBy {
			union2.firstOrThrow { "mock error" }
		}.withMessage("mock error")
		assertThat(union2.secondOrThrow()).isEqualTo(2)
		assertThat(union2.secondOrThrow { "mock error" }).isEqualTo(2)
		assertThatIllegalStateException().isThrownBy {
			union2.thirdOrThrow()
		}.withMessage("QuadDiscriminatedUnion is type 2")
		assertThatIllegalStateException().isThrownBy {
			union2.thirdOrThrow { "mock error" }
		}.withMessage("mock error")
		assertThatIllegalStateException().isThrownBy {
			union2.fourthOrThrow()
		}.withMessage("QuadDiscriminatedUnion is type 2")
		assertThatIllegalStateException().isThrownBy {
			union2.fourthOrThrow { "mock error" }
		}.withMessage("mock error")
		assertThat(union2.firstOr("default")).isEqualTo("default")
		assertThat(union2.firstOrGet { "default" }).isEqualTo("default")
		assertThat(union2.secondOr(1337)).isEqualTo(2)
		assertThat(union2.secondOrGet { 1337 }).isEqualTo(2)
		assertThat(union2.thirdOr(y2k)).isEqualTo(y2k)
		assertThat(union2.thirdOrGet { y2k }).isEqualTo(y2k)
		assertThat(union2.fourthOr(uuid2)).isEqualTo(uuid2)
		assertThat(union2.fourthOrGet { uuid2 }).isEqualTo(uuid2)
		assertThat(union2.toString()).isEqualTo("2")
		assertThat(union2.hashCode()).isEqualTo(2.hashCode())
		@Suppress("AssertBetweenInconvertibleTypes")
		assertThat(union2).isNotEqualTo(2)
		assertThat(union2).isEqualTo(union2)
		assertThat(union2).isEqualTo(QuadDiscriminatedUnion.second<String, Int, LocalDate, UUID>(2))
		assertThat(union2).isNotEqualTo(QuadDiscriminatedUnion.second<String, Int, LocalDate, UUID>(1337))
		assertThat(union2).isNotEqualTo(QuadDiscriminatedUnion.first<String, Int, LocalDate, UUID>("2"))
		assertThat(union2).isNotEqualTo(QuadDiscriminatedUnion.third<String, Int, LocalDate, UUID>(LocalDate.ofEpochDay(2)))
		assertThat(union2).isNotEqualTo(QuadDiscriminatedUnion.fourth<String, Int, LocalDate, UUID>(uuid1))
		assertThat(union2).isNotEqualTo(QuadDiscriminatedUnion.fourth<String, Int, LocalDate, UUID>(uuid2))
		assertThat(union2).isNotEqualTo(union1)
		assertThat(union2).isNotEqualTo(union3)
		assertThat(union2).isNotEqualTo(union4)
		assertThat(union2.position).isEqualTo(2)

		assertThat(union3.firstOrNull).isNull()
		assertThat(union3.secondOrNull).isNull()
		assertThat(union3.thirdOrNull).isEqualTo(day)
		assertThat(union3.fourthOrNull).isNull()
		assertThat(union3.isFirstType).isFalse()
		assertThat(union3.isSecondType).isFalse()
		assertThat(union3.isThirdType).isTrue()
		assertThat(union3.isFourthType).isFalse()
		assertThatIllegalStateException().isThrownBy {
			union3.firstOrThrow()
		}.withMessage("QuadDiscriminatedUnion is type 3")
		assertThatIllegalStateException().isThrownBy {
			union3.firstOrThrow { "mock error" }
		}.withMessage("mock error")
		assertThatIllegalStateException().isThrownBy {
			union3.secondOrThrow()
		}.withMessage("QuadDiscriminatedUnion is type 3")
		assertThatIllegalStateException().isThrownBy {
			union3.secondOrThrow { "mock error" }
		}.withMessage("mock error")
		assertThat(union3.thirdOrThrow()).isEqualTo(day)
		assertThat(union3.thirdOrThrow { "mock error" }).isEqualTo(day)
		assertThatIllegalStateException().isThrownBy {
			union3.fourthOrThrow()
		}.withMessage("QuadDiscriminatedUnion is type 3")
		assertThatIllegalStateException().isThrownBy {
			union3.fourthOrThrow { "mock error" }
		}.withMessage("mock error")
		assertThat(union3.firstOr("default")).isEqualTo("default")
		assertThat(union3.firstOrGet { "default" }).isEqualTo("default")
		assertThat(union3.secondOr(1337)).isEqualTo(1337)
		assertThat(union3.secondOrGet { 1337 }).isEqualTo(1337)
		assertThat(union3.thirdOr(y2k)).isEqualTo(day)
		assertThat(union3.thirdOrGet { y2k }).isEqualTo(day)
		assertThat(union3.fourthOr(uuid2)).isEqualTo(uuid2)
		assertThat(union3.fourthOrGet { uuid2 }).isEqualTo(uuid2)
		assertThat(union3.toString()).isEqualTo("2023-10-21")
		assertThat(union3.hashCode()).isEqualTo(day.hashCode())
		@Suppress("AssertBetweenInconvertibleTypes")
		assertThat(union3).isNotEqualTo(day)
		assertThat(union3).isEqualTo(union3)
		assertThat(union3).isEqualTo(QuadDiscriminatedUnion.third<String, Int, LocalDate, UUID>(day))
		assertThat(union3).isNotEqualTo(QuadDiscriminatedUnion.third<String, Int, LocalDate, UUID>(y2k))
		assertThat(union3).isNotEqualTo(QuadDiscriminatedUnion.fourth<String, Int, LocalDate, UUID>(uuid1))
		assertThat(union3).isNotEqualTo(QuadDiscriminatedUnion.fourth<String, Int, LocalDate, UUID>(uuid2))
		assertThat(union3).isNotEqualTo(QuadDiscriminatedUnion.second<String, Int, LocalDate, UUID>(day.toEpochDay().toInt()))
		assertThat(union3).isNotEqualTo(QuadDiscriminatedUnion.first<String, Int, LocalDate, UUID>(day.toString()))
		assertThat(union3).isNotEqualTo(union1)
		assertThat(union3).isNotEqualTo(union2)
		assertThat(union3).isNotEqualTo(union4)
		assertThat(union3.position).isEqualTo(3)

		assertThat(union4.firstOrNull).isNull()
		assertThat(union4.secondOrNull).isNull()
		assertThat(union4.thirdOrNull).isNull()
		assertThat(union4.fourthOrNull).isEqualTo(uuid1)
		assertThat(union4.isFirstType).isFalse()
		assertThat(union4.isSecondType).isFalse()
		assertThat(union4.isThirdType).isFalse()
		assertThat(union4.isFourthType).isTrue()
		assertThatIllegalStateException().isThrownBy {
			union4.firstOrThrow()
		}.withMessage("QuadDiscriminatedUnion is type 4")
		assertThatIllegalStateException().isThrownBy {
			union4.firstOrThrow { "mock error" }
		}.withMessage("mock error")
		assertThatIllegalStateException().isThrownBy {
			union4.secondOrThrow()
		}.withMessage("QuadDiscriminatedUnion is type 4")
		assertThatIllegalStateException().isThrownBy {
			union4.secondOrThrow { "mock error" }
		}.withMessage("mock error")
		assertThatIllegalStateException().isThrownBy {
			union4.thirdOrThrow()
		}.withMessage("QuadDiscriminatedUnion is type 4")
		assertThatIllegalStateException().isThrownBy {
			union4.thirdOrThrow { "mock error" }
		}.withMessage("mock error")
		assertThat(union4.fourthOrThrow()).isEqualTo(uuid1)
		assertThat(union4.fourthOrThrow { "mock error" }).isEqualTo(uuid1)
		assertThat(union4.firstOr("default")).isEqualTo("default")
		assertThat(union4.firstOrGet { "default" }).isEqualTo("default")
		assertThat(union4.secondOr(1337)).isEqualTo(1337)
		assertThat(union4.secondOrGet { 1337 }).isEqualTo(1337)
		assertThat(union4.thirdOr(y2k)).isEqualTo(y2k)
		assertThat(union4.thirdOrGet { y2k }).isEqualTo(y2k)
		assertThat(union4.fourthOr(uuid2)).isEqualTo(uuid1)
		assertThat(union4.fourthOrGet { uuid2 }).isEqualTo(uuid1)
		assertThat(union4.toString()).isEqualTo(uuid1.toString())
		assertThat(union4.hashCode()).isEqualTo(uuid1.hashCode())
		@Suppress("AssertBetweenInconvertibleTypes")
		assertThat(union4).isNotEqualTo(uuid1)
		assertThat(union4).isEqualTo(union4)
		assertThat(union4).isNotEqualTo(QuadDiscriminatedUnion.first<String, Int, LocalDate, UUID>(uuid1.toString()))
		assertThat(union4).isNotEqualTo(QuadDiscriminatedUnion.third<String, Int, LocalDate, UUID>(day))
		assertThat(union4).isNotEqualTo(QuadDiscriminatedUnion.third<String, Int, LocalDate, UUID>(y2k))
		assertThat(union4).isEqualTo(QuadDiscriminatedUnion.fourth<String, Int, LocalDate, UUID>(uuid1))
		assertThat(union4).isNotEqualTo(QuadDiscriminatedUnion.fourth<String, Int, LocalDate, UUID>(uuid2))
		assertThat(union4).isNotEqualTo(QuadDiscriminatedUnion.second<String, Int, LocalDate, UUID>(day.toEpochDay().toInt()))
		assertThat(union4).isNotEqualTo(QuadDiscriminatedUnion.first<String, Int, LocalDate, UUID>(day.toString()))
		assertThat(union4).isNotEqualTo(union1)
		assertThat(union4).isNotEqualTo(union2)
		assertThat(union4).isNotEqualTo(union3)
		assertThat(union4.position).isEqualTo(4)
	}

	@Test
	fun takeIfTest() {
		assertThat(listOf(true, false)).`as`("type1").allSatisfy { type1 ->
			fun Boolean.toStringPredicate() = if (this) String::isNotBlank else String::isBlank
			fun Boolean.toIntPredicate() = if (this) ::isPositive else ::isNegative
			fun Boolean.toLocalDatePredicate() = if (this) ::isPositiveDay else ::isNegativeDay
			fun Boolean.toUuidPredicate() = if (this) uuid1::equals else uuid2::equals

			assertThat(listOf(true, false)).`as`("type2").allSatisfy { type2 ->
				assertThat(listOf(true, false)).`as`("type3").allSatisfy { type3 ->
					assertThat(listOf(true, false)).`as`("type4").allSatisfy { type4 ->
						assertThat(listOf(union1 to type1, union2 to type2, union3 to type3, union4 to type4)).allSatisfy { (it, expected) ->
							if (expected) {
								assertThat(it.takeIf(type1.toStringPredicate(), type2.toIntPredicate(), type3.toLocalDatePredicate(), type4.toUuidPredicate())).isEqualTo(it)
							} else {
								assertThat(it.takeIf(type1.toStringPredicate(), type2.toIntPredicate(), type3.toLocalDatePredicate(), type4.toUuidPredicate())).isNull()
							}
						}
					}
				}
			}

			assertThat(union1.takeIfFirst(type1.toStringPredicate()))
				.apply { if (type1) isEqualTo(union1) else isNull() }
			assertThat(union2.takeIfFirst(type1.toStringPredicate())).isEqualTo(union2)
			assertThat(union3.takeIfFirst(type1.toStringPredicate())).isEqualTo(union3)
			assertThat(union4.takeIfFirst(type1.toStringPredicate())).isEqualTo(union4)

			assertThat(union1.takeIfSecond(type1.toIntPredicate())).isEqualTo(union1)
			assertThat(union2.takeIfSecond(type1.toIntPredicate()))
				.apply { if (type1) isEqualTo(union2) else isNull() }
			assertThat(union3.takeIfSecond(type1.toIntPredicate())).isEqualTo(union3)
			assertThat(union4.takeIfSecond(type1.toIntPredicate())).isEqualTo(union4)

			assertThat(union1.takeIfThird(type1.toLocalDatePredicate())).isEqualTo(union1)
			assertThat(union2.takeIfThird(type1.toLocalDatePredicate())).isEqualTo(union2)
			assertThat(union3.takeIfThird(type1.toLocalDatePredicate()))
				.apply { if (type1) isEqualTo(union3) else isNull() }
			assertThat(union4.takeIfThird(type1.toLocalDatePredicate())).isEqualTo(union4)

			assertThat(union1.takeIfFourth(type1.toUuidPredicate())).isEqualTo(union1)
			assertThat(union2.takeIfFourth(type1.toUuidPredicate())).isEqualTo(union2)
			assertThat(union3.takeIfFourth(type1.toUuidPredicate())).isEqualTo(union3)
			assertThat(union4.takeIfFourth(type1.toUuidPredicate()))
				.apply { if (type1) isEqualTo(union4) else isNull() }
		}
	}

	@Test
	fun takeUnlessTest() {
		assertThat(listOf(true, false)).`as`("type1").allSatisfy { type1 ->
			fun Boolean.toStringPredicate() = if (this) String::isNotBlank else String::isBlank
			fun Boolean.toIntPredicate() = if (this) ::isPositive else ::isNegative
			fun Boolean.toLocalDatePredicate() = if (this) ::isPositiveDay else ::isNegativeDay
			fun Boolean.toUuidPredicate() = if (this) uuid1::equals else uuid2::equals

			assertThat(listOf(true, false)).`as`("type2").allSatisfy { type2 ->
				assertThat(listOf(true, false)).`as`("type3").allSatisfy { type3 ->
					assertThat(listOf(true, false)).`as`("type4").allSatisfy { type4 ->
						assertThat(listOf(union1 to type1, union2 to type2, union3 to type3, union4 to type4)).allSatisfy { (it, expected) ->
							if (expected) {
								assertThat(it.takeUnless(type1.toStringPredicate(), type2.toIntPredicate(), type3.toLocalDatePredicate(), type4.toUuidPredicate())).isNull()
							} else {
								assertThat(it.takeUnless(type1.toStringPredicate(), type2.toIntPredicate(), type3.toLocalDatePredicate(), type4.toUuidPredicate())).isEqualTo(it)
							}
						}
					}
				}
			}

			assertThat(union1.takeUnlessFirst(type1.toStringPredicate()))
				.apply { if (type1) isNull() else isEqualTo(union1) }
			assertThat(union2.takeUnlessFirst(type1.toStringPredicate())).isEqualTo(union2)
			assertThat(union3.takeUnlessFirst(type1.toStringPredicate())).isEqualTo(union3)
			assertThat(union4.takeUnlessFirst(type1.toStringPredicate())).isEqualTo(union4)

			assertThat(union1.takeUnlessSecond(type1.toIntPredicate())).isEqualTo(union1)
			assertThat(union2.takeUnlessSecond(type1.toIntPredicate()))
				.apply { if (type1) isNull() else isEqualTo(union2) }
			assertThat(union3.takeUnlessSecond(type1.toIntPredicate())).isEqualTo(union3)
			assertThat(union4.takeUnlessSecond(type1.toIntPredicate())).isEqualTo(union4)

			assertThat(union1.takeUnlessThird(type1.toLocalDatePredicate())).isEqualTo(union1)
			assertThat(union2.takeUnlessThird(type1.toLocalDatePredicate())).isEqualTo(union2)
			assertThat(union3.takeUnlessThird(type1.toLocalDatePredicate()))
				.apply { if (type1) isNull() else isEqualTo(union3) }
			assertThat(union4.takeUnlessThird(type1.toLocalDatePredicate())).isEqualTo(union4)

			assertThat(union1.takeUnlessFourth(type1.toUuidPredicate())).isEqualTo(union1)
			assertThat(union2.takeUnlessFourth(type1.toUuidPredicate())).isEqualTo(union2)
			assertThat(union3.takeUnlessFourth(type1.toUuidPredicate())).isEqualTo(union3)
			assertThat(union4.takeUnlessFourth(type1.toUuidPredicate()))
				.apply { if (type1) isNull() else isEqualTo(union4) }
		}
	}

	@Test
	fun alsoTest() {
		val executed = mutableListOf<String>()
		union1.also(
			{ executed += listOf(it, "union1.also(true, false, false, false)") },
			{ executed += listOf(it.toString(), "union1.also(false, true, false, false)") },
			{ executed += listOf(it.toString(), "union1.also(false, false, true, false)") },
			{ executed += listOf(it.toString(), "union1.also(false, false, false, true)") },
		)
		union1.alsoFirst { executed += "union1.alsoFirst(true)" }
		union1.alsoSecond { executed += "union1.alsoSecond(true)" }
		union1.alsoThird { executed += "union1.alsoThird(true)" }
		union1.alsoFourth { executed += "union1.alsoFourth(true)" }

		union2.also(
			{ executed += listOf(it, "union2.also(true, false, false, false)") },
			{ executed += listOf(it.toString(), "union2.also(false, true, false, false)") },
			{ executed += listOf(it.toString(), "union2.also(false, false, true, false)") },
			{ executed += listOf(it.toString(), "union2.also(false, false, false, true)") },
		)
		union2.alsoFirst { executed += "union2.alsoFirst(true)" }
		union2.alsoSecond { executed += "union2.alsoSecond(true)" }
		union2.alsoFourth { executed += "union2.alsoFourth(true)" }

		union3.also(
			{ executed += listOf(it, "union3.also(true, false, false, false)") },
			{ executed += listOf(it.toString(), "union3.also(false, true, false, false)") },
			{ executed += listOf(it.toString(), "union3.also(false, false, true, false)") },
			{ executed += listOf(it.toString(), "union3.also(false, false, false, true)") },
		)
		union3.alsoFirst { executed += "union3.alsoFirst(true)" }
		union3.alsoSecond { executed += "union3.alsoSecond(true)" }
		union3.alsoThird { executed += "union3.alsoThird(true)" }
		union3.alsoFourth { executed += "union3.alsoFourth(true)" }

		union4.also(
			{ executed += listOf(it, "union4.also(true, false, false, false)") },
			{ executed += listOf(it.toString(), "union4.also(false, true, false, false)") },
			{ executed += listOf(it.toString(), "union4.also(false, false, true, false)") },
			{ executed += listOf(it.toString(), "union4.also(false, false, false, true)") },
		)
		union4.alsoFirst { executed += "union4.alsoFirst(true)" }
		union4.alsoSecond { executed += "union4.alsoSecond(true)" }
		union4.alsoThird { executed += "union4.alsoThird(true)" }
		union4.alsoFourth { executed += "union4.alsoFourth(true)" }

		assertThat(executed).containsExactly(
			"first", "union1.also(true, false, false, false)", "union1.alsoFirst(true)",
			"2", "union2.also(false, true, false, false)", "union2.alsoSecond(true)",
			"2023-10-21", "union3.also(false, false, true, false)", "union3.alsoThird(true)",
			uuid1.toString(), "union4.also(false, false, false, true)", "union4.alsoFourth(true)",
		)

		QuadDiscriminatedUnion.first<String?, UUID?, BigDecimal?, Instant?>(null).alsoFirst {
			assertThat(it).isNull()
			executed += it.toString()
		}
		QuadDiscriminatedUnion.second<String?, UUID?, BigDecimal?, Instant?>(null).alsoSecond {
			assertThat(it).isNull()
			executed += it.toString()
		}
		QuadDiscriminatedUnion.third<String?, UUID?, BigDecimal?, Instant?>(null).alsoThird {
			assertThat(it).isNull()
			executed += it.toString()
		}
		QuadDiscriminatedUnion.fourth<String?, UUID?, BigDecimal?, Instant?>(null).alsoFourth {
			assertThat(it).isNull()
			executed += it.toString()
		}

		assertThat(executed).containsExactly(
			"first", "union1.also(true, false, false, false)", "union1.alsoFirst(true)",
			"2", "union2.also(false, true, false, false)", "union2.alsoSecond(true)",
			"2023-10-21", "union3.also(false, false, true, false)", "union3.alsoThird(true)",
			uuid1.toString(), "union4.also(false, false, false, true)", "union4.alsoFourth(true)",
			"null", "null", "null", "null",
		)
	}

	@Test
	fun applyTest() {
		val executed = mutableListOf<String>()
		union1.apply(
			{ executed += listOf(this, "union1.apply(true, false, false, false)") },
			{ executed += listOf(toString(), "union1.apply(false, true, false, false)") },
			{ executed += listOf(toString(), "union1.apply(false, false, true, false)") },
			{ executed += listOf(toString(), "union1.apply(false, false, false, true)") },
		)
		union1.applyFirst { executed += "union1.applyFirst(true)" }
		union1.applySecond { executed += "union1.applySecond(true)" }
		union1.applyThird { executed += "union1.applyThird(true)" }
		union1.applyFourth { executed += "union1.applyFourth(true)" }

		union2.apply(
			{ executed += listOf(this, "union2.apply(true, false, false, false)") },
			{ executed += listOf(toString(), "union2.apply(false, true, false, false)") },
			{ executed += listOf(toString(), "union2.apply(false, false, true, false)") },
			{ executed += listOf(toString(), "union2.apply(false, false, false, true)") },
		)
		union2.applyFirst { executed += "union2.applyFirst(true)" }
		union2.applySecond { executed += "union2.applySecond(true)" }
		union2.applyFourth { executed += "union2.applyFourth(true)" }

		union3.apply(
			{ executed += listOf(this, "union3.apply(true, false, false, false)") },
			{ executed += listOf(toString(), "union3.apply(false, true, false, false)") },
			{ executed += listOf(toString(), "union3.apply(false, false, true, false)") },
			{ executed += listOf(toString(), "union3.apply(false, false, false, true)") },
		)
		union3.applyFirst { executed += "union3.applyFirst(true)" }
		union3.applySecond { executed += "union3.applySecond(true)" }
		union3.applyThird { executed += "union3.applyThird(true)" }
		union3.applyFourth { executed += "union3.applyFourth(true)" }

		union4.apply(
			{ executed += listOf(this, "union4.apply(true, false, false, false)") },
			{ executed += listOf(toString(), "union4.apply(false, true, false, false)") },
			{ executed += listOf(toString(), "union4.apply(false, false, true, false)") },
			{ executed += listOf(toString(), "union4.apply(false, false, false, true)") },
		)
		union4.applyFirst { executed += "union4.applyFirst(true)" }
		union4.applySecond { executed += "union4.applySecond(true)" }
		union4.applyThird { executed += "union4.applyThird(true)" }
		union4.applyFourth { executed += "union4.applyFourth(true)" }

		assertThat(executed).containsExactly(
			"first", "union1.apply(true, false, false, false)", "union1.applyFirst(true)",
			"2", "union2.apply(false, true, false, false)", "union2.applySecond(true)",
			"2023-10-21", "union3.apply(false, false, true, false)", "union3.applyThird(true)",
			uuid1.toString(), "union4.apply(false, false, false, true)", "union4.applyFourth(true)",
		)
	}

	/*@Test
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
			.isEqualTo(QuadDiscriminatedUnion.first<String, LocalTime, LocalDate>("firstfirst"))
		assertThat(union1.flatMapFirst { it.repeat(2) })
			.isEqualTo(QuadDiscriminatedUnion.first<String, Int, LocalDate, UUID>("firstfirst"))
		assertThat(union1.flatMapSecond { it * it }).isEqualTo(union1)
		assertThat(union1.flatMapThird { it.atStartOfDay() }).isEqualTo(union1)

		assertThat(union2.flatMap({ it.repeat(2) }, { LocalTime.ofSecondOfDay(it.toLong()) }, { it.plusDays(1) }))
			.isEqualTo(QuadDiscriminatedUnion.second<String, LocalTime, LocalDate>(LocalTime.of(0, 0, 2)))
		assertThat(union2.flatMapFirst { it.repeat(2) }).isEqualTo(union2)
		assertThat(union2.flatMapSecond { it * it })
			.isEqualTo(QuadDiscriminatedUnion.second<String, Int, LocalDate, UUID>(4))
		assertThat(union2.flatMapThird { it.atStartOfDay() }).isEqualTo(union2)

		assertThat(union3.flatMap({ it.repeat(2) }, { LocalTime.ofSecondOfDay(it.toLong()) }, { it.plusDays(1) }))
			.isEqualTo(QuadDiscriminatedUnion.third<String, LocalTime, LocalDate>(LocalDate.of(2023, 10, 22)))
		assertThat(union3.flatMapFirst { it.repeat(2) }).isEqualTo(union3)
		assertThat(union3.flatMapSecond { it * it }).isEqualTo(union3)
		assertThat(union3.flatMapThird { it.atStartOfDay() })
			.isEqualTo(QuadDiscriminatedUnion.third<String, Int, LocalDateTime>(LocalDateTime.of(day, LocalTime.MIDNIGHT)))
	}*/

	@Test
	fun anyOfTest() {
		assertThat(listOf(true, false)).`as`("type1").allSatisfy { type1 ->
			assertThat(listOf(true, false)).`as`("type2").allSatisfy { type2 ->
				assertThat(listOf(true, false)).`as`("type3").allSatisfy { type3 ->
					assertThat(listOf(true, false)).`as`("type4").allSatisfy { type4 ->
						fun Boolean.toStringPredicate() = if (this) String::isNotBlank else String::isBlank
						fun Boolean.toIntPredicate() = if (this) ::isPositive else ::isNegative
						fun Boolean.toLocalDatePredicate() = if (this) ::isPositiveDay else ::isNegativeDay
						fun Boolean.toUuidPredicate() = if (this) uuid1::equals else uuid2::equals

						assertThat(union1.anyOf(type1.toStringPredicate(), type2.toIntPredicate(), type3.toLocalDatePredicate(), type4.toUuidPredicate())).isEqualTo(type1)
						assertThat(union2.anyOf(type1.toStringPredicate(), type2.toIntPredicate(), type3.toLocalDatePredicate(), type4.toUuidPredicate())).isEqualTo(type2)
						assertThat(union3.anyOf(type1.toStringPredicate(), type2.toIntPredicate(), type3.toLocalDatePredicate(), type4.toUuidPredicate())).isEqualTo(type3)
						assertThat(union4.anyOf(type1.toStringPredicate(), type2.toIntPredicate(), type3.toLocalDatePredicate(), type4.toUuidPredicate())).isEqualTo(type4)
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
					assertThat(listOf(true, false)).`as`("type4").allSatisfy { type4 ->
						fun Boolean.toStringPredicate() = if (this) String::isNotBlank else String::isBlank
						fun Boolean.toIntPredicate() = if (this) ::isPositive else ::isNegative
						fun Boolean.toLocalDatePredicate() = if (this) ::isPositiveDay else ::isNegativeDay
						fun Boolean.toUuidPredicate() = if (this) uuid1::equals else uuid2::equals

						assertThat(union1.noneOf(type1.toStringPredicate(), type2.toIntPredicate(), type3.toLocalDatePredicate(), type4.toUuidPredicate())).isEqualTo(!type1)
						assertThat(union2.noneOf(type1.toStringPredicate(), type2.toIntPredicate(), type3.toLocalDatePredicate(), type4.toUuidPredicate())).isEqualTo(!type2)
						assertThat(union3.noneOf(type1.toStringPredicate(), type2.toIntPredicate(), type3.toLocalDatePredicate(), type4.toUuidPredicate())).isEqualTo(!type3)
						assertThat(union4.noneOf(type1.toStringPredicate(), type2.toIntPredicate(), type3.toLocalDatePredicate(), type4.toUuidPredicate())).isEqualTo(!type4)
					}
				}
			}
		}
	}

	/*@Test
	fun reverseTest() {
		assertThat(union1.reverse())
			.isEqualTo(QuadDiscriminatedUnion.third<LocalDate, Int, String>("first"))
			.isNotEqualTo(union1)
			.isNotEqualTo(union2)
			.isNotEqualTo(union3)
		assertThat(union1.reverse().reverse()).isEqualTo(union1)
		assertThat(union1.reverseFirstTwo())
			.isEqualTo(QuadDiscriminatedUnion.second<Int, String, LocalDate>("first"))
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
			.isEqualTo(QuadDiscriminatedUnion.first<Int, String, LocalDate>(2))
			.isNotEqualTo(union1)
			.isNotEqualTo(union2)
			.isNotEqualTo(union3)
		assertThat(union2.reverseFirstTwo().reverseFirstTwo()).isEqualTo(union2)
		assertThat(union2.reverseLastTwo())
			.isEqualTo(QuadDiscriminatedUnion.third<String, LocalDate, Int>(2))
			.isNotEqualTo(union1)
			.isNotEqualTo(union2)
			.isNotEqualTo(union3)
		assertThat(union2.reverseLastTwo().reverseLastTwo()).isEqualTo(union2)

		assertThat(union3.reverse())
			.isEqualTo(QuadDiscriminatedUnion.first<LocalDate, Int, String>(day))
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
			.isEqualTo(QuadDiscriminatedUnion.second<String, LocalDate, Int>(day))
			.isNotEqualTo(union1)
			.isNotEqualTo(union2)
			.isNotEqualTo(union3)
		assertThat(union3.reverseLastTwo().reverseLastTwo()).isEqualTo(union3)
	}*/

	@Test
	fun toQuadrupleTest() {
		assertThat(union1.toQuadruple()).isEqualTo(Quadruple("first", null, null, null))
		assertThat(union2.toQuadruple()).isEqualTo(Quadruple(null, 2, null, null))
		assertThat(union3.toQuadruple()).isEqualTo(Quadruple(null, null, day, null))
		assertThat(union4.toQuadruple()).isEqualTo(Quadruple(null, null, null, uuid1))
	}

	@Test
	fun toTripleTest() {
		assertThat(union1.toTriple()).isEqualTo(Triple("first", null, null))
		assertThat(union2.toTriple()).isEqualTo(Triple(null, 2, null))
		assertThat(union3.toTriple()).isEqualTo(Triple(null, null, day))
		assertThat(union4.toTriple()).isEqualTo(Triple(null, null, null))
	}

	@Test
	fun toPair() {
		assertThat(union1.toPair()).isEqualTo(Pair("first", null))
		assertThat(union2.toPair()).isEqualTo(Pair(null, 2))
		assertThat(union3.toPair()).isEqualTo(Pair(null, null))
		assertThat(union4.toPair()).isEqualTo(Pair(null, null))
	}

	@Test
	fun orDefaultsTest() {
		assertThat(union1.orDefaults("lorem ipsum", 1337, y2k, uuid2))
			.isEqualTo(Quadruple("first", 1337, y2k, uuid2))
		assertThat(union1.orDefaults(Quadruple("lorem ipsum", 1337, y2k, uuid2)))
			.isEqualTo(Quadruple("first", 1337, y2k, uuid2))
		assertThat(union2.orDefaults("lorem ipsum", 1337, y2k, uuid2))
			.isEqualTo(Quadruple("lorem ipsum", 2, y2k, uuid2))
		assertThat(union2.orDefaults(Quadruple("lorem ipsum", 1337, y2k, uuid2)))
			.isEqualTo(Quadruple("lorem ipsum", 2, y2k, uuid2))
		assertThat(union3.orDefaults("lorem ipsum", 1337, y2k, uuid2))
			.isEqualTo(Quadruple("lorem ipsum", 1337, day, uuid2))
		assertThat(union3.orDefaults(Quadruple("lorem ipsum", 1337, y2k, uuid2)))
			.isEqualTo(Quadruple("lorem ipsum", 1337, day, uuid2))
		assertThat(union4.orDefaults("lorem ipsum", 1337, y2k, uuid2))
			.isEqualTo(Quadruple("lorem ipsum", 1337, y2k, uuid1))
		assertThat(union4.orDefaults(Quadruple("lorem ipsum", 1337, y2k, uuid2)))
			.isEqualTo(Quadruple("lorem ipsum", 1337, y2k, uuid1))
		
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
		assertThat(union4.orDefaults("lorem ipsum", 1337, y2k))
			.isEqualTo(Triple("lorem ipsum", 1337, y2k))
		assertThat(union4.orDefaults(Triple("lorem ipsum", 1337, y2k)))
			.isEqualTo(Triple("lorem ipsum", 1337, y2k))

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
		assertThat(union4.orDefaults("lorem ipsum", 1337))
			.isEqualTo(Pair("lorem ipsum", 1337))
		assertThat(union4.orDefaults(Pair("lorem ipsum", 1337)))
			.isEqualTo(Pair("lorem ipsum", 1337))
	}

	@Test
	fun toResultTest() {
		assertThat(QuadDiscriminatedUnion.first<String, Int, Boolean, IllegalStateException>("lorem ipsum").toResult())
			.isEqualTo(Result.success(TriDiscriminatedUnion.first<String, Int, Boolean>("lorem ipsum")))
			.satisfies {
				assertThat(it.isSuccess).isTrue()
				assertThat(it.isFailure).isFalse()
				assertThat(it.getOrNull()).isEqualTo(TriDiscriminatedUnion.first<String, Int, Boolean>("lorem ipsum"))
				assertThat(it.exceptionOrNull()).isNull()
			}

		assertThat(QuadDiscriminatedUnion.second<String, Int, Boolean, IllegalStateException>(1337).toResult())
			.isEqualTo(Result.success(TriDiscriminatedUnion.second<String, Int, Boolean>(1337)))
			.satisfies {
				assertThat(it.isSuccess).isTrue()
				assertThat(it.isFailure).isFalse()
				assertThat(it.getOrNull()).isEqualTo(TriDiscriminatedUnion.second<String, Int, Boolean>(1337))
				assertThat(it.exceptionOrNull()).isNull()
			}

		assertThat(QuadDiscriminatedUnion.third<String, Int, Boolean, IllegalStateException>(true).toResult())
			.isEqualTo(Result.success(TriDiscriminatedUnion.third<String, Int, Boolean>(true)))
			.satisfies {
				assertThat(it.isSuccess).isTrue()
				assertThat(it.isFailure).isFalse()
				assertThat(it.getOrNull()).isEqualTo(TriDiscriminatedUnion.third<String, Int, Boolean>(true))
				assertThat(it.exceptionOrNull()).isNull()
			}

		assertThat(QuadDiscriminatedUnion.fourth<String, Int, Boolean, IllegalStateException>(IllegalStateException("Mock error")).toResult()).satisfies {
			assertThat(it.isSuccess).isFalse()
			assertThat(it.isFailure).isTrue()
			assertThat(it.getOrNull()).isNull()
			assertThat(it.exceptionOrNull()).isExactlyInstanceOf(IllegalStateException::class.java).hasMessage("Mock error")
		}
	}

	@Test
	fun takeUnlessAllNullTest() {
		assertThat(QuadDiscriminatedUnion.first<String?, Int?, LocalDate?, UUID?>(null).takeUnlessAllNull()).isNull()
		assertThat(QuadDiscriminatedUnion.first<String?, Int?, LocalDate?, UUID?>("lorem ipsum").takeUnlessAllNull())
			.isEqualTo(QuadDiscriminatedUnion.first<String, Int, LocalDate, UUID>("lorem ipsum"))

		assertThat(QuadDiscriminatedUnion.second<String?, Int?, LocalDate?, UUID?>(null).takeUnlessAllNull()).isNull()
		assertThat(QuadDiscriminatedUnion.second<String?, Int?, LocalDate?, UUID?>(1337).takeUnlessAllNull())
			.isEqualTo(QuadDiscriminatedUnion.second<String, Int, LocalDate, UUID>(1337))

		assertThat(QuadDiscriminatedUnion.third<String?, Int?, LocalDate?, UUID?>(null).takeUnlessAllNull()).isNull()
		assertThat(QuadDiscriminatedUnion.third<String?, Int?, LocalDate?, UUID?>(y2k).takeUnlessAllNull())
			.isEqualTo(QuadDiscriminatedUnion.third<String, Int, LocalDate, UUID>(y2k))

		assertThat(QuadDiscriminatedUnion.fourth<String?, Int?, LocalDate?, UUID?>(null).takeUnlessAllNull()).isNull()
		assertThat(QuadDiscriminatedUnion.fourth<String?, Int?, LocalDate?, UUID?>(uuid2).takeUnlessAllNull())
			.isEqualTo(QuadDiscriminatedUnion.fourth<String, Int, LocalDate, UUID>(uuid2))
	}

	@Test
	fun orNullableTypesTest() {
		assertThat(QuadDiscriminatedUnion.first<String, Int, LocalDate, UUID>("lorem ipsum").orNullableTypes())
			.isEqualTo(QuadDiscriminatedUnion.first<String?, Int?, LocalDate?, UUID?>("lorem ipsum"))
		assertThat(QuadDiscriminatedUnion.second<String, Int, LocalDate, UUID>(1337).orNullableTypes())
			.isEqualTo(QuadDiscriminatedUnion.second<String?, Int?, LocalDate?, UUID?>(1337))
		assertThat(QuadDiscriminatedUnion.third<String, Int, LocalDate, UUID>(y2k).orNullableTypes())
			.isEqualTo(QuadDiscriminatedUnion.third<String?, Int?, LocalDate?, UUID?>(y2k))
		assertThat(QuadDiscriminatedUnion.fourth<String, Int, LocalDate, UUID>(uuid2).orNullableTypes())
			.isEqualTo(QuadDiscriminatedUnion.fourth<String?, Int?, LocalDate?, UUID?>(uuid2))
		assertThat((null as QuadDiscriminatedUnion<String, Int, LocalDate, UUID>?).orNullableTypes())
			.isEqualTo(QuadDiscriminatedUnion.first<String?, Int?, LocalDate?, UUID?>(null))
	}

	/*@Test
	fun flattenTest() {
		assertThat(QuadDiscriminatedUnion.first<QuadDiscriminatedUnion<String, Int, LocalDate, UUID>, QuadDiscriminatedUnion<String, Int, LocalDate, UUID>, QuadDiscriminatedUnion<String, Int, LocalDate, UUID>>(union1).flatten())
			.isEqualTo(union1)
		assertThat(QuadDiscriminatedUnion.first<QuadDiscriminatedUnion<String, Int, LocalDate, UUID>, QuadDiscriminatedUnion<String, Int, LocalDate, UUID>, QuadDiscriminatedUnion<String, Int, LocalDate, UUID>>(union2).flatten())
			.isEqualTo(union2)
		assertThat(QuadDiscriminatedUnion.first<QuadDiscriminatedUnion<String, Int, LocalDate, UUID>, QuadDiscriminatedUnion<String, Int, LocalDate, UUID>, QuadDiscriminatedUnion<String, Int, LocalDate, UUID>>(union3).flatten())
			.isEqualTo(union3)
		assertThat(QuadDiscriminatedUnion.second<QuadDiscriminatedUnion<String, Int, LocalDate, UUID>, QuadDiscriminatedUnion<String, Int, LocalDate, UUID>, QuadDiscriminatedUnion<String, Int, LocalDate, UUID>>(union1).flatten())
			.isEqualTo(union1)
		assertThat(QuadDiscriminatedUnion.second<QuadDiscriminatedUnion<String, Int, LocalDate, UUID>, QuadDiscriminatedUnion<String, Int, LocalDate, UUID>, QuadDiscriminatedUnion<String, Int, LocalDate, UUID>>(union2).flatten())
			.isEqualTo(union2)
		assertThat(QuadDiscriminatedUnion.second<QuadDiscriminatedUnion<String, Int, LocalDate, UUID>, QuadDiscriminatedUnion<String, Int, LocalDate, UUID>, QuadDiscriminatedUnion<String, Int, LocalDate, UUID>>(union3).flatten())
			.isEqualTo(union3)
		assertThat(QuadDiscriminatedUnion.third<QuadDiscriminatedUnion<String, Int, LocalDate, UUID>, QuadDiscriminatedUnion<String, Int, LocalDate, UUID>, QuadDiscriminatedUnion<String, Int, LocalDate, UUID>>(union1).flatten())
			.isEqualTo(union1)
		assertThat(QuadDiscriminatedUnion.third<QuadDiscriminatedUnion<String, Int, LocalDate, UUID>, QuadDiscriminatedUnion<String, Int, LocalDate, UUID>, QuadDiscriminatedUnion<String, Int, LocalDate, UUID>>(union2).flatten())
			.isEqualTo(union2)
		assertThat(QuadDiscriminatedUnion.third<QuadDiscriminatedUnion<String, Int, LocalDate, UUID>, QuadDiscriminatedUnion<String, Int, LocalDate, UUID>, QuadDiscriminatedUnion<String, Int, LocalDate, UUID>>(union3).flatten())
			.isEqualTo(union3)

		assertThat(QuadDiscriminatedUnion.first<QuadDiscriminatedUnion<String, Int, LocalDate, UUID>, QuadDiscriminatedUnion<String, Int, LocalDate, UUID>, LocalDate>(union1).flatten())
			.isEqualTo(union1)
		assertThat(QuadDiscriminatedUnion.first<QuadDiscriminatedUnion<String, Int, LocalDate, UUID>, QuadDiscriminatedUnion<String, Int, LocalDate, UUID>, LocalDate>(union2).flatten())
			.isEqualTo(union2)
		assertThat(QuadDiscriminatedUnion.first<QuadDiscriminatedUnion<String, Int, LocalDate, UUID>, QuadDiscriminatedUnion<String, Int, LocalDate, UUID>, LocalDate>(union3).flatten())
			.isEqualTo(union3)
		assertThat(QuadDiscriminatedUnion.second<QuadDiscriminatedUnion<String, Int, LocalDate, UUID>, QuadDiscriminatedUnion<String, Int, LocalDate, UUID>, LocalDate>(union1).flatten())
			.isEqualTo(union1)
		assertThat(QuadDiscriminatedUnion.second<QuadDiscriminatedUnion<String, Int, LocalDate, UUID>, QuadDiscriminatedUnion<String, Int, LocalDate, UUID>, LocalDate>(union2).flatten())
			.isEqualTo(union2)
		assertThat(QuadDiscriminatedUnion.second<QuadDiscriminatedUnion<String, Int, LocalDate, UUID>, QuadDiscriminatedUnion<String, Int, LocalDate, UUID>, LocalDate>(union3).flatten())
			.isEqualTo(union3)
		assertThat(QuadDiscriminatedUnion.third<QuadDiscriminatedUnion<String, Int, LocalDate, UUID>, QuadDiscriminatedUnion<String, Int, LocalDate, UUID>, LocalDate>(y2k).flatten())
			.isEqualTo(QuadDiscriminatedUnion.third<String, Int, LocalDate, UUID>(y2k))

		assertThat(QuadDiscriminatedUnion.first<QuadDiscriminatedUnion<String, Int, LocalDate, UUID>, Int, QuadDiscriminatedUnion<String, Int, LocalDate, UUID>>(union1).flatten())
			.isEqualTo(union1)
		assertThat(QuadDiscriminatedUnion.first<QuadDiscriminatedUnion<String, Int, LocalDate, UUID>, Int, QuadDiscriminatedUnion<String, Int, LocalDate, UUID>>(union2).flatten())
			.isEqualTo(union2)
		assertThat(QuadDiscriminatedUnion.first<QuadDiscriminatedUnion<String, Int, LocalDate, UUID>, Int, QuadDiscriminatedUnion<String, Int, LocalDate, UUID>>(union3).flatten())
			.isEqualTo(union3)
		assertThat(QuadDiscriminatedUnion.second<QuadDiscriminatedUnion<String, Int, LocalDate, UUID>, Int, QuadDiscriminatedUnion<String, Int, LocalDate, UUID>>(1337).flatten())
			.isEqualTo(QuadDiscriminatedUnion.second<String, Int, LocalDate, UUID>(1337))
		assertThat(QuadDiscriminatedUnion.third<QuadDiscriminatedUnion<String, Int, LocalDate, UUID>, Int, QuadDiscriminatedUnion<String, Int, LocalDate, UUID>>(union1).flatten())
			.isEqualTo(union1)
		assertThat(QuadDiscriminatedUnion.third<QuadDiscriminatedUnion<String, Int, LocalDate, UUID>, Int, QuadDiscriminatedUnion<String, Int, LocalDate, UUID>>(union2).flatten())
			.isEqualTo(union2)
		assertThat(QuadDiscriminatedUnion.third<QuadDiscriminatedUnion<String, Int, LocalDate, UUID>, Int, QuadDiscriminatedUnion<String, Int, LocalDate, UUID>>(union3).flatten())
			.isEqualTo(union3)

		assertThat(QuadDiscriminatedUnion.first<String, QuadDiscriminatedUnion<String, Int, LocalDate, UUID>, QuadDiscriminatedUnion<String, Int, LocalDate, UUID>>("lorem ipsum").flatten())
			.isEqualTo(QuadDiscriminatedUnion.first<String, Int, LocalDate, UUID>("lorem ipsum"))
		assertThat(QuadDiscriminatedUnion.second<String, QuadDiscriminatedUnion<String, Int, LocalDate, UUID>, QuadDiscriminatedUnion<String, Int, LocalDate, UUID>>(union1).flatten())
			.isEqualTo(union1)
		assertThat(QuadDiscriminatedUnion.second<String, QuadDiscriminatedUnion<String, Int, LocalDate, UUID>, QuadDiscriminatedUnion<String, Int, LocalDate, UUID>>(union2).flatten())
			.isEqualTo(union2)
		assertThat(QuadDiscriminatedUnion.second<String, QuadDiscriminatedUnion<String, Int, LocalDate, UUID>, QuadDiscriminatedUnion<String, Int, LocalDate, UUID>>(union3).flatten())
			.isEqualTo(union3)
		assertThat(QuadDiscriminatedUnion.third<String, QuadDiscriminatedUnion<String, Int, LocalDate, UUID>, QuadDiscriminatedUnion<String, Int, LocalDate, UUID>>(union1).flatten())
			.isEqualTo(union1)
		assertThat(QuadDiscriminatedUnion.third<String, QuadDiscriminatedUnion<String, Int, LocalDate, UUID>, QuadDiscriminatedUnion<String, Int, LocalDate, UUID>>(union2).flatten())
			.isEqualTo(union2)
		assertThat(QuadDiscriminatedUnion.third<String, QuadDiscriminatedUnion<String, Int, LocalDate, UUID>, QuadDiscriminatedUnion<String, Int, LocalDate, UUID>>(union3).flatten())
			.isEqualTo(union3)

		assertThat(QuadDiscriminatedUnion.first<QuadDiscriminatedUnion<String, Int, LocalDate, UUID>, Int, LocalDate>(union1).flatten())
			.isEqualTo(union1)
		assertThat(QuadDiscriminatedUnion.first<QuadDiscriminatedUnion<String, Int, LocalDate, UUID>, Int, LocalDate>(union2).flatten())
			.isEqualTo(union2)
		assertThat(QuadDiscriminatedUnion.first<QuadDiscriminatedUnion<String, Int, LocalDate, UUID>, Int, LocalDate>(union3).flatten())
			.isEqualTo(union3)
		assertThat(QuadDiscriminatedUnion.second<QuadDiscriminatedUnion<String, Int, LocalDate, UUID>, Int, LocalDate>(1337).flatten())
			.isEqualTo(QuadDiscriminatedUnion.second<String, Int, LocalDate, UUID>(1337))
		assertThat(QuadDiscriminatedUnion.third<QuadDiscriminatedUnion<String, Int, LocalDate, UUID>, Int, LocalDate>(y2k).flatten())
			.isEqualTo(QuadDiscriminatedUnion.third<String, Int, LocalDate, UUID>(y2k))

		assertThat(QuadDiscriminatedUnion.first<String, QuadDiscriminatedUnion<String, Int, LocalDate, UUID>, LocalDate>("lorem ipsum").flatten())
			.isEqualTo(QuadDiscriminatedUnion.first<String, Int, LocalDate, UUID>("lorem ipsum"))
		assertThat(QuadDiscriminatedUnion.second<String, QuadDiscriminatedUnion<String, Int, LocalDate, UUID>, LocalDate>(union1).flatten())
			.isEqualTo(union1)
		assertThat(QuadDiscriminatedUnion.second<String, QuadDiscriminatedUnion<String, Int, LocalDate, UUID>, LocalDate>(union2).flatten())
			.isEqualTo(union2)
		assertThat(QuadDiscriminatedUnion.second<String, QuadDiscriminatedUnion<String, Int, LocalDate, UUID>, LocalDate>(union3).flatten())
			.isEqualTo(union3)
		assertThat(QuadDiscriminatedUnion.third<String, QuadDiscriminatedUnion<String, Int, LocalDate, UUID>, LocalDate>(y2k).flatten())
			.isEqualTo(QuadDiscriminatedUnion.third<String, Int, LocalDate, UUID>(y2k))

		assertThat(QuadDiscriminatedUnion.first<String, Int, QuadDiscriminatedUnion<String, Int, LocalDate, UUID>>("lorem ipsum").flatten())
			.isEqualTo(QuadDiscriminatedUnion.first<String, Int, LocalDate, UUID>("lorem ipsum"))
		assertThat(QuadDiscriminatedUnion.second<String, Int, QuadDiscriminatedUnion<String, Int, LocalDate, UUID>>(1337).flatten())
			.isEqualTo(QuadDiscriminatedUnion.second<String, Int, LocalDate, UUID>(1337))
		assertThat(QuadDiscriminatedUnion.third<String, Int, QuadDiscriminatedUnion<String, Int, LocalDate, UUID>>(union1).flatten())
			.isEqualTo(union1)
		assertThat(QuadDiscriminatedUnion.third<String, Int, QuadDiscriminatedUnion<String, Int, LocalDate, UUID>>(union2).flatten())
			.isEqualTo(union2)
		assertThat(QuadDiscriminatedUnion.third<String, Int, QuadDiscriminatedUnion<String, Int, LocalDate, UUID>>(union3).flatten())
			.isEqualTo(union3)

		assertThat(QuadDiscriminatedUnion.first<Int, Int, LocalDate>(1337).flattenFirstAndSecond())
			.isEqualTo(DiscriminatedUnion.first<Int, LocalDate>(1337))
		assertThat(QuadDiscriminatedUnion.second<Int, Int, LocalDate>(1337).flattenFirstAndSecond())
			.isEqualTo(DiscriminatedUnion.first<Int, LocalDate>(1337))
		assertThat(QuadDiscriminatedUnion.third<Int, Int, LocalDate>(y2k).flattenFirstAndSecond())
			.isEqualTo(DiscriminatedUnion.second<Int, LocalDate>(y2k))

		assertThat(QuadDiscriminatedUnion.first<Int, LocalDate, LocalDate>(1337).flattenSecondAndThird())
			.isEqualTo(DiscriminatedUnion.first<Int, LocalDate>(1337))
		assertThat(QuadDiscriminatedUnion.second<Int, LocalDate, LocalDate>(y2k).flattenSecondAndThird())
			.isEqualTo(DiscriminatedUnion.second<Int, LocalDate>(y2k))
		assertThat(QuadDiscriminatedUnion.third<Int, LocalDate, LocalDate>(y2k).flattenSecondAndThird())
			.isEqualTo(DiscriminatedUnion.second<Int, LocalDate>(y2k))

		assertThat(QuadDiscriminatedUnion.first<Int, LocalDate, Int>(1337).flattenFirstAndThird())
			.isEqualTo(DiscriminatedUnion.first<Int, LocalDate>(1337))
		assertThat(QuadDiscriminatedUnion.second<Int, LocalDate, Int>(y2k).flattenFirstAndThird())
			.isEqualTo(DiscriminatedUnion.second<Int, LocalDate>(y2k))
		assertThat(QuadDiscriminatedUnion.third<Int, LocalDate, Int>(1337).flattenFirstAndThird())
			.isEqualTo(DiscriminatedUnion.first<Int, LocalDate>(1337))
	}*/

	@Test
	fun flattenUnionsTest() {
		assertThat(listOf(union1, union2, union3, union4).flattenUnions())
			.isEqualTo(listOf("first", 2, day, uuid1))

		assertThat(sequenceOf(union1, union2, union3, union4).flattenUnions().toList())
			.isEqualTo(listOf("first", 2, day, uuid1))
	}

	private companion object {
		fun isPositive(value: Int) = value >= 0

		fun isNegative(value: Int) = value < 0

		fun isPositiveDay(value: LocalDate) = value.toEpochDay() >= 0

		fun isNegativeDay(value: LocalDate) = value.toEpochDay() < 0
	}
}
