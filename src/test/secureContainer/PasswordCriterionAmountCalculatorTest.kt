package secureContainer

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class PasswordCriterionAmountCalculatorTest {
    @Test
    fun anyMultiplierIsAlsoADouble() {
        val calculator = PasswordCriterionAmountCalculator(true)
        assertEquals(579, calculator.execute(calculator.convertInput()))
    }

    @Test
    fun triplesAndMoreDoNotCountAsDoubles() {
        val calculator = PasswordCriterionAmountCalculator(false)
        assertEquals(358, calculator.execute(calculator.convertInput()))
    }
}