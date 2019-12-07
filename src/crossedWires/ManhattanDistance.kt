package crossedWires

import AdventRunnable
import java.lang.Integer.max
import java.lang.Integer.min
import kotlin.math.absoluteValue
import kotlin.math.sign

fun main() {
    ManhattanDistance(ManhattanDistance.Strategy.CLOSEST_TRAVELLED).main()
    ManhattanDistance(ManhattanDistance.Strategy.CLOSEST_TO_START_POSITION).main()
}

class ManhattanDistance(val strategy: Strategy) : AdventRunnable<List<Pair<Char, Int>>, Int> {

    enum class Strategy() {
        CLOSEST_TO_START_POSITION,
        CLOSEST_TRAVELLED
    }

    override fun convertInput(): List<List<Pair<Char, Int>>> {
        return convertStringToPairs(readFileAsLinesUsingBufferedReader("src\\crossedWires\\input.txt"))
    }

    override fun execute(input: List<List<Pair<Char, Int>>>): Int {
        val wires: List<List<Line>> =
            input.map { wire -> makeDirectionsToCoordinates(wire, strategy == Strategy.CLOSEST_TO_START_POSITION) }
        var closestIntersection = Int.MAX_VALUE
        var totalTraveledFirstWire = 0
        wires[0].forEachIndexed { i, line ->
            run {
                var totalTravelledSecondWire = 0
                for (otherLine in wires[1]) {
                    if (strategy == Strategy.CLOSEST_TO_START_POSITION) {
                        if (line.furthestDistanceFrom0() < otherLine.minimumDistanceFrom0()) break
                        closestIntersection =
                            line.intersectsAt(otherLine)?.let { coordinate ->
                                coordinate.x.absoluteValue + coordinate.y.absoluteValue
                            }?.takeIf { it < closestIntersection }
                                ?: closestIntersection
                    } else if (strategy == Strategy.CLOSEST_TRAVELLED) {
                        if (closestIntersection < totalTraveledFirstWire) return closestIntersection
                        else if (closestIntersection < totalTraveledFirstWire + totalTravelledSecondWire) break
                        closestIntersection =
                            line.intersectsAt(otherLine)?.let { to ->
                                (totalTraveledFirstWire + Line(
                                    line.from,
                                    to
                                ).travelledDistance()
                                +
                                totalTravelledSecondWire + Line(
                                    otherLine.from,
                                    to
                                ).travelledDistance())
                            }?.takeIf { it < closestIntersection }
                                ?: closestIntersection
                        totalTravelledSecondWire += otherLine.travelledDistance()
                    }
                }
                totalTraveledFirstWire += line.travelledDistance()
            }
        }
        return closestIntersection
    }

    private fun makeDirectionsToCoordinates(
        directions: List<Pair<Char, Int>>,
        sortByMinimumDistance: Boolean
    ): List<Line> {
        val lines = mutableListOf<Line>()
        var lastCoordinate = Coordinate(0, 0)
        for (direction in directions) {
            var newX = lastCoordinate.x
            var newY = lastCoordinate.y
            when (direction.first) {
                'R' -> newX += direction.second
                'L' -> newX -= direction.second
                'U' -> newY += direction.second
                'D' -> newY -= direction.second
            }
            val newCoordinate = Coordinate(newX, newY)
            lines.add(Line(lastCoordinate, newCoordinate))
            lastCoordinate = newCoordinate
        }
        return if (sortByMinimumDistance) lines.sortedBy(Line::minimumDistanceFrom0) else lines.toList()
    }

}

fun convertStringToPairs(rawStrings: List<String>): List<List<Pair<Char, Int>>> {
    return rawStrings.map { rawString ->
        rawString.split(",")
            .map { pair -> Pair(pair[0], Integer.valueOf(pair.substring(1))) }
    }
}

class Line(val from: Coordinate, private val to: Coordinate) {
    fun minimumDistanceFrom0(): Int {
        return when {
            from.x.sign != to.x.sign -> from.y.absoluteValue
            from.y.sign != to.y.sign -> from.x.absoluteValue
            else -> min(from.x.absoluteValue + from.y.absoluteValue, to.x.absoluteValue + to.y.absoluteValue)

        }
    }

    fun furthestDistanceFrom0(): Int {
        return max(from.x.absoluteValue + from.y.absoluteValue, to.x.absoluteValue + to.y.absoluteValue)
    }

    fun travelledDistance(): Int {
        return (from.x - to.x).absoluteValue + (from.y - to.y).absoluteValue
    }

    fun intersectsAt(otherLine: Line): Coordinate? {
        if (from.x != to.x && otherLine.from.x == otherLine.to.x) {
            if (from.x < otherLine.from.x && to.x > otherLine.from.x || from.x > otherLine.from.x && to.x < otherLine.from.x) {
                if (from.y > otherLine.from.y && from.y < otherLine.to.y || from.y < otherLine.from.y && from.y > otherLine.to.y)
                    return Coordinate(otherLine.from.x, from.y)
            }
        } else if (from.y != to.y && otherLine.from.y == otherLine.to.y) {
            if (from.y < otherLine.from.y && to.y > otherLine.from.y || from.y > otherLine.from.y && to.y < otherLine.from.y) {
                if (from.x > otherLine.from.x && from.x < otherLine.to.x || from.x < otherLine.from.x && from.x > otherLine.to.x)
                    return Coordinate(from.x, otherLine.from.y)
            }
        }
        return null
    }

}

class Coordinate(val x: Int, val y: Int) {
    override fun equals(other: Any?): Boolean =
        if (other is Coordinate) {
            this.x == other.x && this.y == other.y
        } else {
            false
        }

    override fun hashCode(): Int {
        var result = x
        result = 31 * result + y
        return result
    }
}

