package theTyrannyOfTheRocketEquation

import org.junit.jupiter.api.Test

internal class FuelCalculatorTest {

    @Test
    fun run() {
        assert(FuelCalculator().execute(listOf(12)) == 2)
        assert(FuelCalculator().execute(listOf(14)) == 2)
        assert(FuelCalculator().execute(listOf(1969)) == 654)
        assert(FuelCalculator().execute(listOf(100756)) == 33583)
        assert(FuelCalculator().execute(listOf(100756, 12)) == 33583 + 2)
        assert(FuelCalculator().execute(listOf(100756, 12, 1969)) == 33583 + 2 + 654)
    }

}