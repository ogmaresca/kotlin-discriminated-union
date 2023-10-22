package com.ogm.kotlin.range.extensions

import com.ogm.kotlin.discriminatedunion.Quadruple
import com.ogm.kotlin.discriminatedunion.Quadruple.Companion.toList
import com.ogm.kotlin.discriminatedunion.Quadruple.Companion.toListOfPairs
import java.time.LocalDate
import java.time.LocalTime
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class QuadrupleTests {
	@Test
	fun constructorTest() {
		val str = "lorem ipsum"
		val int = 1337
		val date = LocalDate.of(2023, 10, 21)
		val time = LocalTime.of(9, 0, 0, 1)

		val quadruple1 = Quadruple(str, int, date, time)
		assertThat(quadruple1.first).isEqualTo(str)
		assertThat(quadruple1.second).isEqualTo(int)
		assertThat(quadruple1.third).isEqualTo(date)
		assertThat(quadruple1.fourth).isEqualTo(time)

		val quadruple2 = Quadruple(str to int, date to time)
		assertThat(quadruple2.first).isEqualTo(str)
		assertThat(quadruple2.second).isEqualTo(int)
		assertThat(quadruple2.third).isEqualTo(date)
		assertThat(quadruple2.fourth).isEqualTo(time)

		val quadruple3 = Quadruple(str to int, date, time)
		assertThat(quadruple3.first).isEqualTo(str)
		assertThat(quadruple3.second).isEqualTo(int)
		assertThat(quadruple3.third).isEqualTo(date)
		assertThat(quadruple3.fourth).isEqualTo(time)

		val quadruple4 = Quadruple(str, int to date, time)
		assertThat(quadruple4.first).isEqualTo(str)
		assertThat(quadruple4.second).isEqualTo(int)
		assertThat(quadruple4.third).isEqualTo(date)
		assertThat(quadruple4.fourth).isEqualTo(time)

		val quadruple5 = Quadruple(str, int, date to time)
		assertThat(quadruple5.first).isEqualTo(str)
		assertThat(quadruple5.second).isEqualTo(int)
		assertThat(quadruple5.third).isEqualTo(date)
		assertThat(quadruple5.fourth).isEqualTo(time)

		val quadruple6 = Quadruple(Triple(str, int, date), time)
		assertThat(quadruple6.first).isEqualTo(str)
		assertThat(quadruple6.second).isEqualTo(int)
		assertThat(quadruple6.third).isEqualTo(date)
		assertThat(quadruple6.fourth).isEqualTo(time)

		val quadruple7 = Quadruple(str, Triple(int, date, time))
		assertThat(quadruple7.first).isEqualTo(str)
		assertThat(quadruple7.second).isEqualTo(int)
		assertThat(quadruple7.third).isEqualTo(date)
		assertThat(quadruple7.fourth).isEqualTo(time)
	}

	@Test
	fun destructureTests() {
		val quadruple = Quadruple("lorem ipsum", 1337, LocalDate.of(2023, 10, 21), LocalTime.of(9, 0, 0, 1))

		val (str, int, date, time) = quadruple
		assertThat(str).isEqualTo("lorem ipsum")
		assertThat(int).isEqualTo(1337)
		assertThat(date).isEqualTo(LocalDate.of(2023, 10, 21))
		assertThat(time).isEqualTo(LocalTime.of(9, 0, 0, 1))
	}

	@Test
	fun toStringTest() {
		val quadruple = Quadruple("lorem ipsum", 1337, LocalDate.of(2023, 10, 21), LocalTime.of(9, 0, 0, 1))

		assertThat(quadruple.toString()).isEqualTo("(lorem ipsum, 1337, 2023-10-21, 09:00:00.000000001)")
	}

	@Test
	fun toPairsTest() {
		val quadruple = Quadruple("lorem ipsum", 1337, LocalDate.of(2023, 10, 21), LocalTime.of(9, 0, 0, 1))

		assertThat(quadruple.toPairs()).isEqualTo(("lorem ipsum" to 1337) to (LocalDate.of(2023, 10, 21) to LocalTime.of(9, 0, 0, 1)))
	}

	@Test
	fun toListTest() {
		val quadruple = Quadruple("lorem ipsum", "dolor", "sit amet", "consectetur adipiscing elit")

		assertThat(quadruple.toList()).containsExactly("lorem ipsum", "dolor", "sit amet", "consectetur adipiscing elit")
	}

	@Test
	fun toListOfPairsTest() {
		val quadruple = Quadruple("lorem ipsum", 1337, "dolor", 9001)

		assertThat(quadruple.toListOfPairs()).containsExactly("lorem ipsum" to 1337, "dolor" to 9001)
		assertThat(quadruple.toListOfPairs().toMap()).containsAllEntriesOf(mapOf("lorem ipsum" to 1337, "dolor" to 9001))
	}
}
