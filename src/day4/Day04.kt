package day4


import println
import readInput
import kotlin.math.pow


fun main() {


    fun part1(input: List<String>): Int {

        return input.sumOf { line ->

            val (card, numbers) = line.split(": ")

            val (winning, current) = numbers.split(" | ")

            val winningNumbers = winning.split(" ").filter { it.isNotEmpty() }.map { it.toInt() }.toSet()
            val currentNumbers = current.split(" ").filter { it.isNotEmpty() }.map { it.toInt() }.toSet()

            val intersect = winningNumbers.intersect(currentNumbers)

            when (intersect.size) {
                0 -> 0
                else -> 2.0.pow((intersect.size - 1).toDouble()).toInt()
            }

        }
    }

    fun part2(input: List<String>): Int {

        val cardCounter = List(input.size) { index -> index + 1 to 1 }.toMap().toMutableMap()

        input.forEachIndexed { index, line ->
            val currentCard = index + 1
            val copiesNumber = cardCounter[currentCard]!!
            val (card, numbers) = line.split(": ")

            val (winning, current) = numbers.split(" | ")
            val winningNumbers = winning.split(" ").filter { it.isNotEmpty() }.map { it.toInt() }.toSet()
            val currentNumbers = current.split(" ").filter { it.isNotEmpty() }.map { it.toInt() }.toSet()
            val intersect = winningNumbers.intersect(currentNumbers)

            val copiesToPick = intersect.size

            val range = if (copiesToPick > 0) IntRange(1, intersect.size) else null

            range?.forEach {
                cardCounter[currentCard + it] = cardCounter[currentCard + it]!! + (1 * copiesNumber)
            }

        }

        println(cardCounter)
        return cardCounter.map { it.value }.sum()

    }


    val testInput = readInput("day4/Day04_test")
    val part1 = part1(testInput)
    part1.println()

    val input = readInput("day4/Day04")
    part1(input).println()

    val part2 = part2(testInput)
    println(part2)
    println(part2(input))

}


