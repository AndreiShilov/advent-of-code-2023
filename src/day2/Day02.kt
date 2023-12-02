package day2


import println
import readInput


private val defaultMap = mapOf<String, Int>(
    "blue" to 0,
    "green" to 0,
    "red" to 0,
)

fun main() {
    fun games(input: List<String>) = input.map { line ->
        val (gameInfo, setsInfo) = line.split(": ")

        val sets = setsInfo.split("; ")
        val gameSets = sets.map { set ->
            val map = set.split(", ")
                .associate { cubeInfo ->
                    val (number, color) = cubeInfo.split(" ")
                    color to number.toInt()
                }
            GameSet(defaultMap + map)
        }

        val (_, gameIndex) = gameInfo.split(" ")

        Game(gameIndex.toInt(), gameSets)
    }

    fun part1(input: List<String>): Int {

        val games = games(input)

        return games
            .filter { game -> game.sets.all { set -> set.isPossible() } }
            .sumOf { it.id }
    }

    fun part2(input: List<String>): Int {
        val games = games(input)

        return games.map { game ->
            val blueMax = game.sets.maxOfOrNull { it.cubeCounts["blue"]!! }!!
            val greenMax = game.sets.maxOfOrNull { it.cubeCounts["green"]!! }!!
            val redMax = game.sets.maxOfOrNull { it.cubeCounts["red"]!! }!!

            val power = redMax * blueMax * greenMax
            println("Game ${game.id} Power = [$power]: Blue = [$blueMax], Green = [$greenMax], Red = [$redMax]")

            power
        }.sum()

    }

    val testInput = readInput("day2/Day02_test")
    val part1 = part1(testInput)
    println(part1)


    val input = readInput("day2/Day02")
    part1(input).println()

    val part2 = part2(testInput)
    println(part2)
    println(part2(input))

}

data class Game(
    val id: Int,
    val sets: List<GameSet>
)

data class GameSet(
    val cubeCounts: Map<String, Int>
) {

    fun isPossible(): Boolean {
        return cubeCounts["red"]!! <= 12 && cubeCounts["green"]!! <= 13 && cubeCounts["blue"]!! <= 14
    }

}

