package crossedWires

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource


internal class ManhattanDistanceTest {

    @Test
    fun linesThatCrossZeroAtEitherAxisShouldHaveOtherAxisAsClosestTo0() {
        val from = Coordinate(-3, -10)
        val to = Coordinate(3, -10)
        val line = Line(from, to)
        assertEquals(10, line.minimumDistanceFrom0())
        assertEquals(30, Line(Coordinate(30, -10), Coordinate(30, 10)).minimumDistanceFrom0())
    }

    @Test
    fun lineThatDoesNotCrossZeroShouldHaveAbsoluteValuesOfBothAxisOfTheClosestCoordinate() {
        val from = Coordinate(3, 10)
        val to = Coordinate(20, 10)
        val line = Line(from, to)
        assertEquals(13, line.minimumDistanceFrom0())
    }

    @Test
    fun furthestDistance() {
        val from = Coordinate(-3, -10)
        val to = Coordinate(5, -10)
        val line = Line(from, to)
        assertEquals(15, line.furthestDistanceFrom0())
    }

    @Test
    fun lineIntersection() {
        val from = Line(Coordinate(3, 0), Coordinate(3, 10))
        val to = Line(Coordinate(-1, 3), Coordinate(4, 3))
        val intersectsAt = from.intersectsAt(to)
        val intersectsAt2 = to.intersectsAt(from)
        assertEquals(intersectsAt, intersectsAt2)
        assertEquals(Coordinate(3, 3), intersectsAt)
    }

    @ParameterizedTest
    @CsvSource("'CLOSEST_TO_START_POSITION', '11'", "'CLOSEST_TRAVELLED', '30'")
    fun singleIntersectionManhattanDistance(strategy: ManhattanDistance.Strategy, expected: Int) {
        assertEquals(
            expected, ManhattanDistance(strategy).execute(
                convertStringToPairs(
                    listOf(
                        "R8,U5,L5,D3",
                        "U7,R6,D4"
                    )
                )
            )
        )
    }

    @ParameterizedTest
    @CsvSource("'CLOSEST_TO_START_POSITION', '6'", "'CLOSEST_TRAVELLED', '30'")
    fun doubleIntersectionPicksFromStrategy(strategy: ManhattanDistance.Strategy, expected: Int) {
        assertEquals(
            expected, ManhattanDistance(strategy).execute(
                convertStringToPairs(
                    listOf(
                        "R8,U5,L5,D3",
                        "U7,R6,D4,L4"
                    )
                )
            )
        )
    }

    @Test
    fun longerDistances() {
        assertEquals(
            159, ManhattanDistance(ManhattanDistance.Strategy.CLOSEST_TO_START_POSITION).execute(
                convertStringToPairs(
                    listOf(
                        "R75,D30,R83,U83,L12,D49,R71,U7,L72",
                        "U62,R66,U55,R34,D71,R55,D58,R83"
                    )
                )
            )
        )
    }

    @Test
    fun longerDistances2() {
        assertEquals(
            135, ManhattanDistance(ManhattanDistance.Strategy.CLOSEST_TO_START_POSITION).execute(
                convertStringToPairs(
                    listOf(
                        "R98,U47,R26,D63,R33,U87,L62,D20,R33,U53,R51",
                        "U98,R91,D20,R16,D67,R40,U7,R15,U6,R7"
                    )
                )
            )
        )
    }

}