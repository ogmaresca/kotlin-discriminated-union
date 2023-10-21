package com.ogm.kotlin.range.extensions

import com.ogm.kotlin.discriminatedunion.TriDiscriminatedUnion
import com.ogm.kotlin.discriminatedunion.TriDiscriminatedUnion.Companion.value
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatIllegalStateException
import org.junit.jupiter.api.Test

class TriDiscriminatedUnionSameTypesTests {
	private val union1 = TriDiscriminatedUnion.first<String, String, String>("first")
	private val union2 = TriDiscriminatedUnion.second<String, String, String>("second")
	private val union3 = TriDiscriminatedUnion.third<String, String, String>("third")

	@Test
	fun sameTypesGettersTest() {
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
		assertThat(union1.secondOr("default")).isEqualTo("default")
		assertThat(union1.secondOrGet { "default" }).isEqualTo("default")
		assertThat(union1.thirdOr("default")).isEqualTo("default")
		assertThat(union1.thirdOrGet { "default" }).isEqualTo("default")
		assertThat(union1.toString()).isEqualTo("first")
		assertThat(union1.hashCode()).isEqualTo("first".hashCode())
		assertThat(union1.value).isEqualTo("first")
		@Suppress("AssertBetweenInconvertibleTypes")
		assertThat(union1).isNotEqualTo("first")
		assertThat(union1).isEqualTo(union1)
		assertThat(union1).isEqualTo(TriDiscriminatedUnion.first<String, String, String>("first"))
		assertThat(union1).isNotEqualTo(TriDiscriminatedUnion.second<String, String, String>("first"))
		assertThat(union1).isNotEqualTo(TriDiscriminatedUnion.third<String, String, String>("first"))
		assertThat(union1).isNotEqualTo(union2)
		assertThat(union1).isNotEqualTo(union3)
		assertThat(union1.position).isEqualTo(1)

		assertThat(union2.firstOrNull).isNull()
		assertThat(union2.secondOrNull).isEqualTo("second")
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
		assertThat(union2.secondOrThrow()).isEqualTo("second")
		assertThat(union2.secondOrThrow { "mock error" }).isEqualTo("second")
		assertThatIllegalStateException().isThrownBy {
			union2.thirdOrThrow()
		}.withMessage("TriDiscriminatedUnion is type 2")
		assertThatIllegalStateException().isThrownBy {
			union2.thirdOrThrow { "mock error" }
		}.withMessage("mock error")
		assertThat(union2.firstOr("default")).isEqualTo("default")
		assertThat(union2.firstOrGet { "default" }).isEqualTo("default")
		assertThat(union2.secondOr("default")).isEqualTo("second")
		assertThat(union2.secondOrGet { "default" }).isEqualTo("second")
		assertThat(union2.thirdOr("default")).isEqualTo("default")
		assertThat(union2.thirdOrGet { "default" }).isEqualTo("default")
		assertThat(union2.toString()).isEqualTo("second")
		assertThat(union2.hashCode()).isEqualTo("second".hashCode())
		assertThat(union2.value).isEqualTo("second")
		@Suppress("AssertBetweenInconvertibleTypes")
		assertThat(union2).isNotEqualTo("second")
		assertThat(union2).isEqualTo(union2)
		assertThat(union2).isNotEqualTo(TriDiscriminatedUnion.first<String, String, String>("second"))
		assertThat(union2).isEqualTo(TriDiscriminatedUnion.second<String, String, String>("second"))
		assertThat(union2).isNotEqualTo(union1)
		assertThat(union2).isNotEqualTo(union3)
		assertThat(union2.position).isEqualTo(2)

		assertThat(union3.firstOrNull).isNull()
		assertThat(union3.secondOrNull).isNull()
		assertThat(union3.thirdOrNull).isEqualTo("third")
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
		assertThat(union3.thirdOrThrow()).isEqualTo("third")
		assertThat(union3.thirdOrThrow { "mock error" }).isEqualTo("third")
		assertThat(union3.firstOr("default")).isEqualTo("default")
		assertThat(union3.firstOrGet { "default" }).isEqualTo("default")
		assertThat(union3.secondOr("default")).isEqualTo("default")
		assertThat(union3.secondOrGet { "default" }).isEqualTo("default")
		assertThat(union3.thirdOr("default")).isEqualTo("third")
		assertThat(union3.thirdOrGet { "default" }).isEqualTo("third")
		assertThat(union3.toString()).isEqualTo("third")
		assertThat(union3.hashCode()).isEqualTo("third".hashCode())
		assertThat(union3.value).isEqualTo("third")
		@Suppress("AssertBetweenInconvertibleTypes")
		assertThat(union3).isNotEqualTo("third")
		assertThat(union3).isEqualTo(union3)
		assertThat(union3).isNotEqualTo(TriDiscriminatedUnion.first<String, String, String>("third"))
		assertThat(union3).isNotEqualTo(TriDiscriminatedUnion.second<String, String, String>("third"))
		assertThat(union3).isEqualTo(TriDiscriminatedUnion.third<String, String, String>("third"))
		assertThat(union3).isNotEqualTo(union1)
		assertThat(union3).isNotEqualTo(union2)
		assertThat(union3.position).isEqualTo(3)
	}

	@Test
	fun takeIfTest() {
		assertThat(listOf(true, false)).`as`("type1").allSatisfy { type1 ->
			fun Boolean.toPredicate() = if (this) String::isNotBlank else String::isBlank

			assertThat(listOf(true, false)).`as`("type2").allSatisfy { type2 ->
				assertThat(listOf(true, false)).`as`("type3").allSatisfy { type3 ->

					assertThat(listOf(union1 to type1, union2 to type2, union3 to type3)).allSatisfy { (it, expected) ->
						if (expected) {
							assertThat(it.takeIf(type1.toPredicate(), type2.toPredicate(), type3.toPredicate())).isEqualTo(it)
						} else {
							assertThat(it.takeIf(type1.toPredicate(), type2.toPredicate(), type3.toPredicate())).isNull()
						}
					}
				}
			}

			assertThat(union1.takeIfFirst(type1.toPredicate()))
				.apply { if (type1) isEqualTo(union1) else isNull() }
			assertThat(union2.takeIfFirst(type1.toPredicate())).isEqualTo(union2)
			assertThat(union3.takeIfFirst(type1.toPredicate())).isEqualTo(union3)

			assertThat(union1.takeIfSecond(type1.toPredicate())).isEqualTo(union1)
			assertThat(union2.takeIfSecond(type1.toPredicate()))
				.apply { if (type1) isEqualTo(union2) else isNull() }
			assertThat(union3.takeIfSecond(type1.toPredicate())).isEqualTo(union3)

			assertThat(union1.takeIfThird(type1.toPredicate())).isEqualTo(union1)
			assertThat(union2.takeIfThird(type1.toPredicate())).isEqualTo(union2)
			assertThat(union3.takeIfThird(type1.toPredicate()))
				.apply { if (type1) isEqualTo(union3) else isNull() }
		}
	}

	@Test
	fun takeUnlessTest() {
		assertThat(listOf(true, false)).`as`("type1").allSatisfy { type1 ->
			fun Boolean.toPredicate() = if (this) String::isNotBlank else String::isBlank

			assertThat(listOf(true, false)).`as`("type2").allSatisfy { type2 ->
				assertThat(listOf(true, false)).`as`("type3").allSatisfy { type3 ->

					assertThat(listOf(union1 to type1, union2 to type2, union3 to type3)).allSatisfy { (it, expected) ->
						if (expected) {
							assertThat(it.takeUnless(type1.toPredicate(), type2.toPredicate(), type3.toPredicate())).isNull()
						} else {
							assertThat(it.takeUnless(type1.toPredicate(), type2.toPredicate(), type3.toPredicate())).isEqualTo(it)
						}
					}
				}
			}

			assertThat(union1.takeUnlessFirst(type1.toPredicate()))
				.apply { if (type1) isNull() else isEqualTo(union1) }
			assertThat(union2.takeUnlessFirst(type1.toPredicate())).isEqualTo(union2)
			assertThat(union3.takeUnlessFirst(type1.toPredicate())).isEqualTo(union3)

			assertThat(union1.takeUnlessSecond(type1.toPredicate())).isEqualTo(union1)
			assertThat(union2.takeUnlessSecond(type1.toPredicate()))
				.apply { if (type1) isNull() else isEqualTo(union2) }
			assertThat(union3.takeUnlessSecond(type1.toPredicate())).isEqualTo(union3)

			assertThat(union1.takeUnlessThird(type1.toPredicate())).isEqualTo(union1)
			assertThat(union2.takeUnlessThird(type1.toPredicate())).isEqualTo(union2)
			assertThat(union3.takeUnlessThird(type1.toPredicate()))
				.apply { if (type1) isNull() else isEqualTo(union3) }
		}
	}

	@Test
	fun alsoTest() {
		val executed = mutableListOf<String>()
		union1.also(
			{ executed += listOf(it, "union1.also(true, false, false)") },
			{ executed += listOf(it, "union1.also(false, true, false)") },
			{ executed += listOf(it, "union1.also(false, false, true)") },
		)
		union1.alsoFirst { executed += "union1.alsoFirst(true)" }
		union1.alsoSecond { executed += "union1.alsoSecond(true)" }
		union1.alsoThird { executed += "union1.alsoThird(true)" }

		union2.also(
			{ executed += listOf(it, "union2.also(true, false, false)") },
			{ executed += listOf(it, "union2.also(false, true, false)") },
			{ executed += listOf(it, "union2.also(false, false, true)") },
		)
		union2.alsoFirst { executed += "union2.alsoFirst(true)" }
		union2.alsoSecond { executed += "union2.alsoSecond(true)" }
		union2.alsoThird { executed += "union2.alsoThird(true)" }

		union3.also(
			{ executed += listOf(it, "union3.also(true, false, false)") },
			{ executed += listOf(it, "union3.also(false, true, false)") },
			{ executed += listOf(it, "union3.also(false, false, true)") },
		)
		union3.alsoFirst { executed += "union3.alsoFirst(true)" }
		union3.alsoSecond { executed += "union3.alsoSecond(true)" }
		union3.alsoThird { executed += "union3.alsoThird(true)" }

		assertThat(executed).containsExactly(
			"first", "union1.also(true, false, false)", "union1.alsoFirst(true)",
			"second", "union2.also(false, true, false)", "union2.alsoSecond(true)",
			"third", "union3.also(false, false, true)", "union3.alsoThird(true)",
		)
	}

	@Test
	fun applyTest() {
		val executed = mutableListOf<String>()
		union1.apply(
			{ executed += listOf(this, "union1.apply(true, false, false)") },
			{ executed += listOf(this, "union1.apply(false, true, false)") },
			{ executed += listOf(this, "union1.apply(false, false, true)") },
		)
		union1.applyFirst { executed += "union1.applyFirst(true)" }
		union1.applySecond { executed += "union1.applySecond(true)" }
		union1.applyThird { executed += "union1.applyThird(true)" }

		union2.apply(
			{ executed += listOf(this, "union2.apply(true, false, false)") },
			{ executed += listOf(this, "union2.apply(false, true, false)") },
			{ executed += listOf(this, "union2.apply(false, false, true)") },
		)
		union2.applyFirst { executed += "union2.applyFirst(true)" }
		union2.applySecond { executed += "union2.applySecond(true)" }
		union2.applyThird { executed += "union2.applyThird(true)" }

		union3.apply(
			{ executed += listOf(this, "union3.apply(true, false, false)") },
			{ executed += listOf(this, "union3.apply(false, true, false)") },
			{ executed += listOf(this, "union3.apply(false, false, true)") },
		)
		union3.applyFirst { executed += "union3.applyFirst(true)" }
		union3.applySecond { executed += "union3.applySecond(true)" }
		union3.applyThird { executed += "union3.applyThird(true)" }

		assertThat(executed).containsExactly(
			"first", "union1.apply(true, false, false)", "union1.applyFirst(true)",
			"second", "union2.apply(false, true, false)", "union2.applySecond(true)",
			"third", "union3.apply(false, false, true)", "union3.applyThird(true)",
		)
	}

	@Test
	fun mapTest() {
		assertThat(union1.map({ it.repeat(2) }, { it.repeat(3) }, { it.repeat(4) })).isEqualTo("firstfirst")
		assertThat(union1.mapToFirst({ it.repeat(2) }, { it.repeat(3) })).isEqualTo("first")
		assertThat(union1.mapToSecond({ it.repeat(2) }, { it.repeat(3) })).isEqualTo("firstfirst")
		assertThat(union1.mapToThird({ it.repeat(2) }, { it.repeat(3) })).isEqualTo("firstfirst")

		assertThat(union2.map({ it.repeat(2) }, { it.repeat(3) }, { it.repeat(4) })).isEqualTo("secondsecondsecond")
		assertThat(union2.mapToFirst({ it.repeat(2) }, { it.repeat(3) })).isEqualTo("secondsecond")
		assertThat(union2.mapToSecond({ it.repeat(2) }, { it.repeat(3) })).isEqualTo("second")
		assertThat(union2.mapToThird({ it.repeat(2) }, { it.repeat(3) })).isEqualTo("secondsecondsecond")

		assertThat(union3.map({ it.repeat(2) }, { it.repeat(3) }, { it.repeat(4) })).isEqualTo("thirdthirdthirdthird")
		assertThat(union3.mapToFirst({ it.repeat(2) }, { it.repeat(3) })).isEqualTo("thirdthirdthird")
		assertThat(union3.mapToSecond({ it.repeat(2) }, { it.repeat(3) })).isEqualTo("thirdthirdthird")
		assertThat(union3.mapToThird({ it.repeat(2) }, { it.repeat(3) })).isEqualTo("third")
	}

	@Test
	fun flatMapTest() {
		assertThat(union1.flatMap({ it.repeat(2) }, { it.repeat(3) }, { it.repeat(4) }))
			.isEqualTo(TriDiscriminatedUnion.first<String, String, String>("firstfirst"))
		assertThat(union1.flatMapFirst { it.repeat(2) }).isEqualTo(TriDiscriminatedUnion.first<String, String, String>("firstfirst"))
		assertThat(union1.flatMapSecond { it.repeat(2) }).isEqualTo(union1)
		assertThat(union1.flatMapThird { it.repeat(2) }).isEqualTo(union1)

		assertThat(union2.flatMap({ it.repeat(2) }, { it.repeat(3) }, { it.repeat(4) }))
			.isEqualTo(TriDiscriminatedUnion.second<String, String, String>("secondsecondsecond"))
		assertThat(union2.flatMapFirst { it.repeat(2) }).isEqualTo(union2)
		assertThat(union2.flatMapSecond { it.repeat(2) }).isEqualTo(TriDiscriminatedUnion.second<String, String, String>("secondsecond"))
		assertThat(union2.flatMapThird { it.repeat(2) }).isEqualTo(union2)

		assertThat(union3.flatMap({ it.repeat(2) }, { it.repeat(3) }, { it.repeat(4) }))
			.isEqualTo(TriDiscriminatedUnion.third<String, String, String>("thirdthirdthirdthird"))
		assertThat(union3.flatMapFirst { it.repeat(2) }).isEqualTo(union3)
		assertThat(union3.flatMapSecond { it.repeat(2) }).isEqualTo(union3)
		assertThat(union3.flatMapThird { it.repeat(2) }).isEqualTo(TriDiscriminatedUnion.third<String, String, String>("thirdthird"))
	}

	@Test
	fun anyOfTest() {
		assertThat(listOf(true, false)).`as`("type1").allSatisfy { type1 ->
			assertThat(listOf(true, false)).`as`("type2").allSatisfy { type2 ->
				assertThat(listOf(true, false)).`as`("type3").allSatisfy { type3 ->
					fun Boolean.toPredicate() = if (this) String::isNotBlank else String::isBlank

					assertThat(listOf(union1 to type1, union2 to type2, union3 to type3)).allSatisfy { (it, expected) ->
						assertThat(it.anyOf(type1.toPredicate(), type2.toPredicate(), type3.toPredicate())).isEqualTo(expected)
						assertThat(it.anyOf(type1, type2.toPredicate(), type3.toPredicate())).isEqualTo(expected)
						assertThat(it.anyOf(type1, type2, type3.toPredicate())).isEqualTo(expected)
						assertThat(it.anyOf(type1, type2.toPredicate(), type3)).isEqualTo(expected)
						assertThat(it.anyOf(type1.toPredicate(), type2, type3.toPredicate())).isEqualTo(expected)
						assertThat(it.anyOf(type1.toPredicate(), type2, type3)).isEqualTo(expected)
						assertThat(it.anyOf(type1.toPredicate(), type2.toPredicate(), type3)).isEqualTo(expected)
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
					fun Boolean.toPredicate() = if (this) String::isNotBlank else String::isBlank

					assertThat(union1.noneOf(type1.toPredicate(), type2.toPredicate(), type3.toPredicate())).isEqualTo(!type1)
					assertThat(union2.noneOf(type1.toPredicate(), type2.toPredicate(), type3.toPredicate())).isEqualTo(!type2)
					assertThat(union3.noneOf(type1.toPredicate(), type2.toPredicate(), type3.toPredicate())).isEqualTo(!type3)
				}
			}
		}
	}

	@Test
	fun reverseTest() {
		assertThat(union1.reverse())
			.isEqualTo(TriDiscriminatedUnion.third<String, String, String>("first"))
			.isNotEqualTo(union1)
			.isNotEqualTo(union2)
			.isNotEqualTo(union3)
		assertThat(union1.reverse().reverse()).isEqualTo(union1)
		assertThat(union1.reverseFirstTwo())
			.isEqualTo(TriDiscriminatedUnion.second<String, String, String>("first"))
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
			.isEqualTo(TriDiscriminatedUnion.first<String, String, String>("second"))
			.isNotEqualTo(union1)
			.isNotEqualTo(union2)
			.isNotEqualTo(union3)
		assertThat(union2.reverseFirstTwo().reverseFirstTwo()).isEqualTo(union2)
		assertThat(union2.reverseLastTwo())
			.isEqualTo(TriDiscriminatedUnion.third<String, String, String>("second"))
			.isNotEqualTo(union1)
			.isNotEqualTo(union2)
			.isNotEqualTo(union3)
		assertThat(union2.reverseLastTwo().reverseLastTwo()).isEqualTo(union2)

		assertThat(union3.reverse())
			.isEqualTo(TriDiscriminatedUnion.first<String, String, String>("third"))
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
			.isEqualTo(TriDiscriminatedUnion.second<String, String, String>("third"))
			.isNotEqualTo(union1)
			.isNotEqualTo(union2)
			.isNotEqualTo(union3)
		assertThat(union3.reverseLastTwo().reverseLastTwo()).isEqualTo(union3)
	}
}
