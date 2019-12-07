package secureContainer

import AdventRunnable

class PasswordCriterionAmountCalculator(private val isAnyMultipleDouble: Boolean): AdventRunnable<Int, Int> {
    override fun convertInput(): List<Int> {
        return listOf(353096, 843212)
    }

    override fun execute(input: List<Int>): Int {
        var from = input[0]
        val to = input[1]
        var totalMatchingCriteria = 0
        while (from <= to) {
            if (matchesCriteria(from.toString())) {
                totalMatchingCriteria++
            }
            from++
        }
        return totalMatchingCriteria
    }

    private fun matchesCriteria(from: String): Boolean {
        var hasDouble = false
        var lastInt = -1
        from.forEachIndexed{i, char ->
            val c = char.toString()
            val int = Integer.parseInt(c)
            if (int < lastInt) {
                return false
            } else if (int == lastInt) {
                if (isAnyMultipleDouble || (i + 1 == from.length || from[i+1] != char) && (i-2 < 0 || from[i-2] != char)) {
                    hasDouble = true
                }
            }
            lastInt = int
        }
        return hasDouble
    }
}

fun main() {
    PasswordCriterionAmountCalculator(true).main()
    PasswordCriterionAmountCalculator(false).main()
}


