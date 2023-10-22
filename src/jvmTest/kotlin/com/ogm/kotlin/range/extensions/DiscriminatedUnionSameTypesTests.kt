package com.ogm.kotlin.range.extensions

import com.ogm.kotlin.discriminatedunion.DiscriminatedUnion
import com.ogm.kotlin.discriminatedunion.DiscriminatedUnion.Companion.value
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatIllegalStateException
import org.junit.jupiter.api.Test

class DiscriminatedUnionSameTypesTests {
	private val union1 = DiscriminatedUnion.first<String, String>("first")
	private val union2 = DiscriminatedUnion.second<String, String>("second")

	@Test
	fun sameTypesGettersTest() {
		assertThat(union1.firstOrNull).isEqualTo("first")
		assertThat(union1.secondOrNull).isNull()
		assertThat(union1.isFirstType).isTrue()
		assertThat(union1.isSecondType).isFalse()
		assertThat(union1.firstOrThrow()).isEqualTo("first")
		assertThat(union1.firstOrThrow { "mock error" }).isEqualTo("first")
		assertThatIllegalStateException().isThrownBy {
			union1.secondOrThrow()
		}.withMessage("DiscriminatedUnion is type 1")
		assertThatIllegalStateException().isThrownBy {
			union1.secondOrThrow { "mock error" }
		}.withMessage("mock error")
		assertThat(union1.firstOr("default")).isEqualTo("first")
		assertThat(union1.firstOrGet { "default" }).isEqualTo("first")
		assertThat(union1.secondOr("default")).isEqualTo("default")
		assertThat(union1.secondOrGet { "default" }).isEqualTo("default")
		assertThat(union1.toString()).isEqualTo("first")
		assertThat(union1.hashCode()).isEqualTo("first".hashCode())
		assertThat(union1.value).isEqualTo("first")
		@Suppress("AssertBetweenInconvertibleTypes")
		assertThat(union1).isNotEqualTo("first")
		assertThat(union1).isEqualTo(union1)
		assertThat(union1).isEqualTo(DiscriminatedUnion.first<String, String>("first"))
		assertThat(union1).isNotEqualTo(DiscriminatedUnion.second<String, String>("first"))
		assertThat(union1).isNotEqualTo(union2)
		assertThat(union1.position).isEqualTo(1)

		assertThat(union2.firstOrNull).isNull()
		assertThat(union2.secondOrNull).isEqualTo("second")
		assertThat(union2.isFirstType).isFalse()
		assertThat(union2.isSecondType).isTrue()
		assertThatIllegalStateException().isThrownBy {
			union2.firstOrThrow()
		}.withMessage("DiscriminatedUnion is type 2")
		assertThatIllegalStateException().isThrownBy {
			union2.firstOrThrow { "mock error" }
		}.withMessage("mock error")
		assertThat(union2.secondOrThrow()).isEqualTo("second")
		assertThat(union2.secondOrThrow { "mock error" }).isEqualTo("second")
		assertThat(union2.firstOr("default")).isEqualTo("default")
		assertThat(union2.firstOrGet { "default" }).isEqualTo("default")
		assertThat(union2.secondOr("default")).isEqualTo("second")
		assertThat(union2.secondOrGet { "default" }).isEqualTo("second")
		assertThat(union2.toString()).isEqualTo("second")
		assertThat(union2.hashCode()).isEqualTo("second".hashCode())
		assertThat(union2.value).isEqualTo("second")
		@Suppress("AssertBetweenInconvertibleTypes")
		assertThat(union2).isNotEqualTo("second")
		assertThat(union2).isEqualTo(union2)
		assertThat(union2).isNotEqualTo(DiscriminatedUnion.first<String, String>("second"))
		assertThat(union2).isEqualTo(DiscriminatedUnion.second<String, String>("second"))
		assertThat(union2).isNotEqualTo(union1)
		assertThat(union2.position).isEqualTo(2)
	}

	@Test
	fun takeIfTest() {
		assertThat(union1.takeIf(String::isNotBlank, String::isNotBlank)).isEqualTo(union1)
		assertThat(union1.takeIf(String::isNotBlank, String::isNullOrBlank)).isEqualTo(union1)
		assertThat(union1.takeIf(String::isNullOrBlank, String::isNotBlank)).isNull()
		assertThat(union1.takeIf(String::isNullOrBlank, String::isNullOrBlank)).isNull()

		assertThat(union2.takeIf(String::isNotBlank, String::isNotBlank)).isEqualTo(union2)
		assertThat(union2.takeIf(String::isNotBlank, String::isNullOrBlank)).isNull()
		assertThat(union2.takeIf(String::isNullOrBlank, String::isNotBlank)).isEqualTo(union2)
		assertThat(union2.takeIf(String::isNullOrBlank, String::isNullOrBlank)).isNull()

		assertThat(union1.takeIfFirst(String::isNotBlank)).isEqualTo(union1)
		assertThat(union1.takeIfFirst(String::isNullOrBlank)).isNull()
		assertThat(union1.takeIfSecond(String::isNotBlank)).isEqualTo(union1)
		assertThat(union1.takeIfSecond(String::isNullOrBlank)).isEqualTo(union1)

		assertThat(union2.takeIfFirst(String::isNotBlank)).isEqualTo(union2)
		assertThat(union2.takeIfFirst(String::isNullOrBlank)).isEqualTo(union2)
		assertThat(union2.takeIfSecond(String::isNotBlank)).isEqualTo(union2)
		assertThat(union2.takeIfSecond(String::isNullOrBlank)).isNull()
	}

	@Test
	fun takeUnlessTest() {
		assertThat(union1.takeUnless(String::isNotBlank, String::isNotBlank)).isNull()
		assertThat(union1.takeUnless(String::isNotBlank, String::isNullOrBlank)).isNull()
		assertThat(union1.takeUnless(String::isNullOrBlank, String::isNotBlank)).isEqualTo(union1)
		assertThat(union1.takeUnless(String::isNullOrBlank, String::isNullOrBlank)).isEqualTo(union1)

		assertThat(union2.takeUnless(String::isNotBlank, String::isNotBlank)).isNull()
		assertThat(union2.takeUnless(String::isNotBlank, String::isNullOrBlank)).isEqualTo(union2)
		assertThat(union2.takeUnless(String::isNullOrBlank, String::isNotBlank)).isNull()
		assertThat(union2.takeUnless(String::isNullOrBlank, String::isNullOrBlank)).isEqualTo(union2)

		assertThat(union1.takeUnlessFirst(String::isNotBlank)).isNull()
		assertThat(union1.takeUnlessFirst(String::isNullOrBlank)).isEqualTo(union1)
		assertThat(union1.takeUnlessSecond(String::isNotBlank)).isEqualTo(union1)
		assertThat(union1.takeUnlessSecond(String::isNullOrBlank)).isEqualTo(union1)

		assertThat(union2.takeUnlessFirst(String::isNotBlank)).isEqualTo(union2)
		assertThat(union2.takeUnlessFirst(String::isNullOrBlank)).isEqualTo(union2)
		assertThat(union2.takeUnlessSecond(String::isNotBlank)).isNull()
		assertThat(union2.takeUnlessSecond(String::isNullOrBlank)).isEqualTo(union2)
	}

	@Test
	fun alsoTest() {
		val executed = mutableListOf<String>()
		union1.also({ executed += listOf(it, "union1.also(true, false)") }) { executed += listOf(it, "union1.also(false, true)") }
		union1.alsoFirst { executed += "union1.alsoFirst(true)" }
		union1.alsoSecond { executed += "union1.alsoSecond(true)" }

		union2.also({ executed += listOf(it, "union2.also(true, false)") }) { executed += listOf(it, "union2.also(false, true)") }
		union2.alsoFirst { executed += "union2.alsoFirst(true)" }
		union2.alsoSecond { executed += "union2.alsoSecond(true)" }

		assertThat(executed).containsExactly(
			"first",
			"union1.also(true, false)",
			"union1.alsoFirst(true)",
			"second",
			"union2.also(false, true)",
			"union2.alsoSecond(true)",
		)
	}

	@Test
	fun applyTest() {
		val executed = mutableListOf<String>()
		union1.apply({ executed += listOf(this, "union1.apply(true, false)") }) { executed += listOf(this, "union1.apply(false, true)") }
		union1.applyFirst { executed += "union1.applyFirst(true)" }
		union1.applySecond { executed += "union1.applySecond(true)" }

		union2.apply({ executed += listOf(this, "union2.apply(true, false)") }) { executed += listOf(this, "union2.apply(false, true)") }
		union2.applyFirst { executed += "union2.applyFirst(true)" }
		union2.applySecond { executed += "union2.applySecond(true)" }

		assertThat(executed).containsExactly(
			"first",
			"union1.apply(true, false)",
			"union1.applyFirst(true)",
			"second",
			"union2.apply(false, true)",
			"union2.applySecond(true)",
		)
	}

	@Test
	fun mapTest() {
		assertThat(union1.map({ it.repeat(2) }) { it.repeat(3) }).isEqualTo("firstfirst")
		assertThat(union1.mapToFirst { it.repeat(2) }).isEqualTo("first")
		assertThat(union1.mapToSecond { it.repeat(2) }).isEqualTo("firstfirst")

		assertThat(union2.map({ it.repeat(2) }) { it.repeat(3) }).isEqualTo("secondsecondsecond")
		assertThat(union2.mapToFirst { it.repeat(2) }).isEqualTo("secondsecond")
		assertThat(union2.mapToSecond { it.repeat(2) }).isEqualTo("second")
	}

	@Test
	fun flatMapTest() {
		assertThat(union1.flatMap({ it.repeat(2) }) { it.repeat(3) }).isEqualTo(DiscriminatedUnion.first<String, String>("firstfirst"))
		assertThat(union1.flatMapFirst { it.repeat(2) }).isEqualTo(DiscriminatedUnion.first<String, String>("firstfirst"))
		assertThat(union1.flatMapSecond { it.repeat(2) }).isEqualTo(union1)

		assertThat(union2.flatMap({ it.repeat(2) }) { it.repeat(3) }).isEqualTo(DiscriminatedUnion.second<String, String>("secondsecondsecond"))
		assertThat(union2.flatMapFirst { it.repeat(2) }).isEqualTo(union2)
		assertThat(union2.flatMapSecond { it.repeat(2) }).isEqualTo(DiscriminatedUnion.second<String, String>("secondsecond"))
	}

	@Test
	fun anyOfTest() {
		assertThat(union1.anyOf(String::isNotBlank, String::isNotBlank)).isTrue()
		assertThat(union1.anyOf(true, String::isNotBlank)).isTrue()
		assertThat(union1.anyOf(false, String::isNotBlank)).isFalse()
		assertThat(union1.anyOf(String::isNotBlank, true)).isTrue()
		assertThat(union1.anyOf(String::isNotBlank, false)).isTrue()
		assertThat(union1.anyOf(String::isBlank, String::isBlank)).isFalse()
		assertThat(union1.anyOf(true, String::isBlank)).isTrue()
		assertThat(union1.anyOf(false, String::isBlank)).isFalse()
		assertThat(union1.anyOf(String::isBlank, true)).isFalse()
		assertThat(union1.anyOf(String::isBlank, false)).isFalse()
		assertThat(union1.anyOf(String::isNotBlank, String::isBlank)).isTrue()
		assertThat(union1.anyOf(String::isBlank, String::isNotBlank)).isFalse()

		assertThat(union2.anyOf(String::isNotBlank, String::isNotBlank)).isTrue()
		assertThat(union2.anyOf(true, String::isNotBlank)).isTrue()
		assertThat(union2.anyOf(false, String::isNotBlank)).isTrue()
		assertThat(union2.anyOf(String::isNotBlank, true)).isTrue()
		assertThat(union2.anyOf(String::isNotBlank, false)).isFalse()
		assertThat(union2.anyOf(String::isBlank, String::isBlank)).isFalse()
		assertThat(union2.anyOf(true, String::isBlank)).isFalse()
		assertThat(union2.anyOf(false, String::isBlank)).isFalse()
		assertThat(union2.anyOf(String::isBlank, true)).isTrue()
		assertThat(union2.anyOf(String::isBlank, false)).isFalse()
		assertThat(union2.anyOf(String::isNotBlank, String::isBlank)).isFalse()
		assertThat(union2.anyOf(String::isBlank, String::isNotBlank)).isTrue()
	}

	@Test
	fun noneOfTest() {
		assertThat(union1.noneOf(String::isNotBlank, String::isNotBlank)).isFalse()
		assertThat(union1.noneOf(String::isBlank, String::isBlank)).isTrue()
		assertThat(union1.noneOf(String::isNotBlank, String::isBlank)).isFalse()
		assertThat(union1.noneOf(String::isBlank, String::isNotBlank)).isTrue()

		assertThat(union2.noneOf(String::isNotBlank, String::isNotBlank)).isFalse()
		assertThat(union2.noneOf(String::isBlank, String::isBlank)).isTrue()
		assertThat(union2.noneOf(String::isNotBlank, String::isBlank)).isTrue()
		assertThat(union2.noneOf(String::isBlank, String::isNotBlank)).isFalse()
	}

	@Test
	fun reverseTest() {
		assertThat(union1.reverse())
			.isEqualTo(DiscriminatedUnion.second<String, String>("first"))
			.isNotEqualTo(union1)
			.isNotEqualTo(union2)
		assertThat(union1.reverse().reverse()).isEqualTo(union1)

		assertThat(union2.reverse())
			.isEqualTo(DiscriminatedUnion.first<String, String>("second"))
			.isNotEqualTo(union1)
		assertThat(union2.reverse().reverse()).isEqualTo(union2)
	}

	@Test
	fun toPairTest() {
		assertThat(union1.toPair()).isEqualTo("first" to null)
		assertThat(union2.toPair()).isEqualTo(null to "second")
	}

	@Test
	fun orDefaultsTest() {
		assertThat(union1.orDefaults("lorem ipsum", "dolor sit amet")).isEqualTo("first" to "dolor sit amet")
		assertThat(union1.orDefaults("lorem ipsum" to "dolor sit amet")).isEqualTo("first" to "dolor sit amet")
		assertThat(union2.orDefaults("lorem ipsum", "dolor sit amet")).isEqualTo("lorem ipsum" to "second")
		assertThat(union2.orDefaults("lorem ipsum" to "dolor sit amet")).isEqualTo("lorem ipsum" to "second")
	}
}
