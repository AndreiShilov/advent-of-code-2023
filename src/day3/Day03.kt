package day3


import println
import readInput


fun main() {
    fun getAllNeighboursIndexes(
        start: Int,
        end: Int,
        yIndex: Int,
        charArrays: List<CharArray>
    ): List<Pair<Int, Int>> {
        val topRow = IntRange(start - 1, end + 1).map { Pair(yIndex - 1, it) }
        val bottomRow = IntRange(start - 1, end + 1).map { Pair(yIndex + 1, it) }
        val left = Pair(yIndex, start - 1)
        val right = Pair(yIndex, end + 1)

        return (topRow + bottomRow + left + right)
            .filter { it.first >= 0 && it.second >= 0 }
            .filter { it.first <= charArrays.size - 1 && it.second <= charArrays.first().size - 1 }
    }

    fun isAdjacent(charArrays: List<CharArray>, yIndex: Int, start: Int, end: Int): Boolean {

        val filter = getAllNeighboursIndexes(start, end, yIndex, charArrays)

        for (coordinate in filter) {
            println("Checking for position $coordinate")
            val c = charArrays[coordinate.first][coordinate.second]
            println("Char [$c]")
            if (c != '.') return true
        }

        return false
    }

    fun isNeighbourTo(charArrays: List<CharArray>, yIndex: Int, start: Int, end: Int): Pair<Int, Int>? {
        val filter = getAllNeighboursIndexes(start, end, yIndex, charArrays)

        for (coordinate in filter) {
            println("Checking for position $coordinate")
            val c = charArrays[coordinate.first][coordinate.second]
            println("Char [$c]")
            if (c == '*') return coordinate
        }

        return null
    }

    fun nearbyPartNumbers(charArrays: List<CharArray>, yIndex: Int, xIndex: Int) {

    }

    fun part1(input: List<String>): Int {

        val charArrays = input.map { it.toCharArray() }

        val numbers = mutableListOf<Int>()

        charArrays.forEachIndexed { yIndex, it ->
            val buffer = mutableListOf<Char>()
            var start = -1
            var end = -1

            it.forEachIndexed { xIndex, char ->
                val isDigit = char.isDigit()
                if (isDigit && buffer.isEmpty()) start = xIndex
                if (isDigit) buffer.add(char)
                if ((!isDigit && buffer.isNotEmpty())
                    || (buffer.isNotEmpty() && xIndex == it.size - 1)
                ) {
                    end = xIndex - 1
                    val number = buffer.joinToString("").toInt()

                    println("Checking for [$number]")
                    if (isAdjacent(charArrays, yIndex, start, end)) {
                        numbers.add(number)
                    }
                    println("============================")
                    buffer.clear()
                }

            }
        }

        return numbers.sum()
    }

    fun part2(input: List<String>): Int {
        val charArrays = input.map { it.toCharArray() }

        val numbers = mutableListOf<Pair<Int, Pair<Int, Int>>>()

        charArrays.forEachIndexed { yIndex, it ->
            val buffer = mutableListOf<Char>()
            var start = -1
            var end = -1

            it.forEachIndexed { xIndex, char ->
                val isDigit = char.isDigit()
                if (isDigit && buffer.isEmpty()) start = xIndex
                if (isDigit) buffer.add(char)
                if ((!isDigit && buffer.isNotEmpty())
                    || (buffer.isNotEmpty() && xIndex == it.size - 1)
                ) {
                    end = xIndex - 1
                    val number = buffer.joinToString("").toInt()

                    println("Checking for [$number]")
                    isNeighbourTo(charArrays, yIndex, start, end)?.let {
                        numbers.add(Pair(number, it))
                    }
                    println("============================")
                    buffer.clear()
                }
            }
        }

        return numbers
            .groupBy { it.second }
            .filter { it.value.size == 2 }
            .map { it.value.first().first * it.value.last().first }
            .sum()
    }


    val testInput = readInput("day3/Day03_test")
    val part1 = part1(testInput)
    println("Test ${part1}")


    val input = readInput("day3/Day03")
    part1(input).println()
    println("Test ${part1}")


    val part2 = part2(testInput)
    println(part2)
    println(part2(input))

}


