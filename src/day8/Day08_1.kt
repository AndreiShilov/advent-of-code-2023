package day8


import println
import readInput


fun main() {


    fun getMoves(input: List<String>) = input[0].split("").filter { it.isNotEmpty() }

    fun getSpotsMap(input: List<String>) = input.drop(2).associate {
        val (location, leftAndRight) = it.split(" = ")
        val (left, right) = leftAndRight.drop(1).dropLast(1).split(", ")
        location to mapOf("L" to left, "R" to right)
    }

    fun getStepsTillCondition(
        moves: List<String>,
        spots: Map<String, Map<String, String>>,
        startingSpot: String,
        startingMoveIndex: Int,
        exitCondition: (spot: String) -> Boolean
    ): Pair<String, Int> {

        var currentSpot = spots[startingSpot]!![moves[startingMoveIndex % moves.size]]!!
        var index = startingMoveIndex
        while (!(exitCondition(currentSpot))) {
            index++
            val currentIndex = index % moves.size
            val currentMove = moves[currentIndex]
            val tmpSpot = spots[currentSpot]!![currentMove]!!
            currentSpot = tmpSpot
        }

        return currentSpot to index + 1
    }

    fun part1(input: List<String>): Int {
        val moves = getMoves(input)
        println(moves)
        val spotsMap = getSpotsMap(input)
        println(spotsMap)

        val stepsTillCondition = getStepsTillCondition(moves, spotsMap, "AAA", 0) { it == "ZZZ" }

        println(stepsTillCondition)

        return stepsTillCondition.second
    }


    tailrec fun gcd(a: Long, b: Long): Long = if (b == 0L) a else gcd(b, a % b)
    fun lcm(a: Long, b: Long) = a * b / gcd(a, b)

    fun part2(input: List<String>): Long {

        val moves = getMoves(input)
        println(moves)
        val spotsMap = getSpotsMap(input)
        println(spotsMap)

        val initial = spotsMap.filter { it.key.endsWith("A") }.map { it.key }
        println(initial)

        val map = initial.associate {
            getStepsTillCondition(moves, spotsMap, it, 0) { spot -> spot.endsWith("Z") }
        }
        println("First = [$map]")

        val mapZ = map.map { entry ->
            val startingMoveIndex = (entry.value % moves.size)
            getStepsTillCondition(
                moves,
                spotsMap,
                entry.key,
                startingMoveIndex
            ) { spot -> spot.endsWith("Z") }
        }

        println("Second = [$mapZ]")
        val map1 = mapZ.map { it.second.toLong() }
        val multiplication = map1.fold(1L) { acc, value -> acc * value }
        println(multiplication)

        return map1.reduce(::lcm)
    }


    val testInput = readInput("day8/Day08_test")
    val part1 = part1(testInput)
//    part1.println()
//
    val input = readInput("day8/Day08")
//    part1(input).println()

    val testInput2 = readInput("day8/Day08_2_test")
    val part2 = part2(testInput2)
    println(part2)
    println(part2(input))
}

