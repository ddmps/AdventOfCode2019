package theTyrannyOfTheRocketEquation

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import theTyrannyOfTheRocketEquation.FuelCalculator

internal class FuelCalculatorTest {

    @Test
    fun simpleFuelCalculator() {
        assertEquals(2, FuelCalculator(false).execute(listOf(12)))
        assert(FuelCalculator(false).execute(listOf(14)) == 2)
        assert(FuelCalculator(false).execute(listOf(1969)) == 654)
        assert(FuelCalculator(false).execute(listOf(100756)) == 33583)
        assert(FuelCalculator(false).execute(listOf(100756, 12)) == 33583 + 2)
        assert(FuelCalculator(false).execute(listOf(100756, 12, 1969)) == 33583 + 2 + 654)
    }

    @Test
    fun calculatesOwnFuelConsumption() {
        assert(FuelCalculator().execute(listOf(2)) == 0)
        assert(FuelCalculator().execute(listOf(12)) == 2)
        assert(FuelCalculator().execute(listOf(14)) == 2)
        assert(FuelCalculator().execute(listOf(1969)) == 966)
        assert(FuelCalculator().execute(listOf(100756)) == 50346)
        assert(FuelCalculator().execute(listOf(100756, 12)) == 50346 + 2)
        assert(FuelCalculator().execute(listOf(100756, 12, 1969)) == 50346 + 2 + 966)
    }

}