package theTyrannyOfTheRocketEquation

import AdventRunnable

class FuelCalculator: AdventRunnable<Int, Int> {
    override fun execute(input: List<Int>): Int {
        return input.sumBy { mass -> mass / 3 - 2  }
    }

    override fun convertInput(): List<Int> {
        return readFileAsLinesUsingBufferedReader("src\\TheTyrannyOfTheRocketEquation\\input.txt")
            .map { s -> Integer.valueOf(s) }
    }
}

fun main() {
    FuelCalculator().main()
}
