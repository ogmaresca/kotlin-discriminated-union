package com.ogm.kotlin.range.extensions

import com.ogm.kotlin.discriminatedunion.DiscriminatedUnion
import com.ogm.kotlin.discriminatedunion.DiscriminatedUnion.Companion.value
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatIllegalStateException
import org.junit.jupiter.api.Test

class DiscriminatedUnionSameTypesTest {
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
	fun reverseTest() {
		assertThat(union1.reverse()).isEqualTo(DiscriminatedUnion.second<String, String>("first"))
		assertThat(union1.reverse()).isNotEqualTo(union1)
		assertThat(union1.reverse()).isNotEqualTo(union2)
		assertThat(union1.reverse().reverse()).isEqualTo(union1)

		assertThat(union2.reverse()).isEqualTo(DiscriminatedUnion.first<String, String>("second"))
		assertThat(union2.reverse()).isNotEqualTo(union2)
		assertThat(union2.reverse()).isNotEqualTo(union1)
		assertThat(union2.reverse().reverse()).isEqualTo(union2)
	}
}
