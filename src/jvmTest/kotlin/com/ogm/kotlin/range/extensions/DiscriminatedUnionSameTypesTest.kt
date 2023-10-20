package com.ogm.kotlin.range.extensions

import com.ogm.kotlin.discriminatedunion.DiscriminatedUnion
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
		}.withMessage("DiscriminatedUnion is of the first type")
		assertThatIllegalStateException().isThrownBy {
			union1.secondOrThrow { "mock error" }
		}.withMessage("mock error")
		assertThat(union1.firstOr("default")).isEqualTo("first")
		assertThat(union1.firstOrGet { "default" }).isEqualTo("first")
		assertThat(union1.secondOr("default")).isEqualTo("default")
		assertThat(union1.secondOrGet { "default" }).isEqualTo("default")

		assertThat(union2.firstOrNull).isNull()
		assertThat(union2.secondOrNull).isEqualTo("second")
		assertThat(union2.isFirstType).isFalse()
		assertThat(union2.isSecondType).isTrue()
		assertThatIllegalStateException().isThrownBy {
			union2.firstOrThrow()
		}.withMessage("DiscriminatedUnion is of the second type")
		assertThatIllegalStateException().isThrownBy {
			union2.firstOrThrow { "mock error" }
		}.withMessage("mock error")
		assertThat(union2.secondOrThrow()).isEqualTo("second")
		assertThat(union2.secondOrThrow { "mock error" }).isEqualTo("second")
		assertThat(union2.firstOr("default")).isEqualTo("default")
		assertThat(union2.firstOrGet { "default" }).isEqualTo("default")
		assertThat(union2.secondOr("default")).isEqualTo("second")
		assertThat(union2.secondOrGet { "default" }).isEqualTo("second")
	}
}
