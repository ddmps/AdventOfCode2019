package theTyrannyOfTheRocketEquation

import AdventRunnable

class FuelCalculator(private val doesFuelHaveMass: Boolean = true) : AdventRunnable<Int, Int> {

    private fun calculateNeededFuel() = { mass: Int ->
        mass / 3 - 2
    }

    private fun calculateNeededFuelRecursively(moduleMass: Int): Int {
        val fuelNeeded = calculateNeededFuel().invoke(moduleMass)
        if (fuelNeeded <= 0) return 0
        return fuelNeeded + calculateNeededFuelRecursively(fuelNeeded)
    }

    override fun execute(input: List<Int>): Int {
        if (!doesFuelHaveMass) {
            return input.sumBy(calculateNeededFuel())
        }
        return input.sumBy { m -> calculateNeededFuelRecursively(m)}
    }

    override fun convertInput(): List<Int> {
        return readFileAsLinesUsingBufferedReader("src\\TheTyrannyOfTheRocketEquation\\input.txt")
            .map { s -> Integer.valueOf(s) }
    }
}

fun main() {
    FuelCalculator(false).main()
    FuelCalculator(true).main()
}
