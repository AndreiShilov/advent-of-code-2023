package day7


import day7.CardsHand.Companion.calculateValue
import print
import println
import readInput

// Possible Values
// FIVE=10
// FOUR=9
// FULL HOUSE = 8
// THREE = 7
// TWO PAIRS = 6
// PAIR = 5
// HIGH CARD = 0


data class CardsHand(val hand: String, val value: Int, val bet: Int) : Comparable<CardsHand> {

    override fun compareTo(other: CardsHand): Int {
        if (this.hand == other.hand) return 0

        if (this.value == other.value) {
            this.hand.forEachIndexed { index, c ->
                val otherChar = other.hand[index]

                val compareTo = charToValue(c).compareTo(charToValue(otherChar))
                if (compareTo != 0) return compareTo
            }
        }

        return this.value.compareTo(other.value)
    }

    companion object {
        fun charToValue(c: Char): Int {
            return when (c) {
                'A' -> 14
                'K' -> 13
                'Q' -> 12
//                'J' -> 11
                'T' -> 10
                '9' -> 9
                '8' -> 8
                '7' -> 7
                '6' -> 6
                '5' -> 5
                '4' -> 4
                '3' -> 3
                '2' -> 2
                'J' -> 1
                else -> {
                    throw IllegalStateException("Unknown char $c")
                }
            }

        }

        fun calculateValue(hand: String): Int {
            val charSet = hand.split("")
                .filter { it.isNotEmpty() }
                .groupBy { it }.map { (key, value) ->
                    key to value.count()
                }
                .toMap().toMutableMap()

            charSet["J"]?.let {
                if(it == 5) return@let
                val maxBy = charSet.entries
                    .filter { entry -> entry.key != "J" }
                    .maxBy { entry -> entry.value }
                charSet[maxBy.key] = maxBy.value + it
                charSet.remove("J")
            }
            return when (charSet.size) {
                1 -> 10
                2 -> {
                    if (charSet.values.containsAll(listOf(4, 1))) return 9 //case with 4+1
                    if (charSet.values.containsAll(listOf(3, 2))) return 8 //case  2+3
                    throw IllegalStateException("Boom $charSet")
                }

                3 -> {
                    if (charSet.values.containsAll(listOf(3, 1, 1))) return 7 //case 3+1+1
                    if (charSet.values.containsAll(listOf(2, 2, 1))) return 6 //case with 2+2+1
                    throw IllegalStateException("Boom")
                }

                4 -> 5
                5 -> 0
                else -> {
                    throw IllegalStateException("Should not happen")
                }
            }
        }
    }

}

fun main() {

    fun part1(input: List<String>): Int {

        val sorted = input
            .map {
                val (cards, betString) = it.split(" ")
                CardsHand(cards, calculateValue(cards), betString.toInt())
            }
            .sorted()
            .print()
            .mapIndexed { index, cardsHand ->
                cardsHand.bet * (index + 1)
            }.sum()

        return sorted
    }

    fun part2(input: List<String>): Int {

        return 0
    }


    val testInput = readInput("day7/Day07_test")
    val part1 = part1(testInput)
    part1.println()

    val input = readInput("day7/Day07")
    part1(input).println()
//
//    val part2 = part2(testInput)
//    println(part2)
//    println(part2(input))
}

