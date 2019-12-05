package programAlarm

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class IntcodeTest {

    @Test
    fun exitsWithFirstOpcode99() {
        assertEquals(listOf(1,1,1,2,99,5,6,0,99), Intcode().execute(listOf(1,1,1,3,99,5,6,0,99)))
    }

    @Test
    fun addsWithOpcode1() {
        assert(Intcode().execute(listOf(1,0,0,0,99)) == listOf(2,0,0,0,99))
    }

    @Test
    fun multipliesWithOpcode2() {
        assert(Intcode().execute(listOf(2,3,0,3,99)) == listOf(2,3,0,6,99))
        assert(Intcode().execute(listOf(2,4,4,5,99,0)) == listOf(2,4,4,5,99,9801))
    }

    @Test
    fun canModifyFutureCodes() {
        assertEquals(listOf(30,1,1,4,2,5,6,0,99), Intcode().execute(listOf(1,1,1,4,99,5,6,0,99)))
    }
}