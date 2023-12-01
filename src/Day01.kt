fun main() {
    fun part1(input: List<String>): Int {
        return input
            .map { line -> line.filter { it.isDigit() } }
            .map { line -> "${line.first()}${line.last()}" }
            .sumOf { it.toInt() }
    }

    fun part1_1(input: List<String>): Int {
        return input
            .map(::getNumber)
            .sum()
    }

    fun part2(input: List<String>): Int {
        return input
            .map { line ->
                var cursor = 0
                val numbers = mutableListOf<Int>()
                while (cursor < line.length) {
                    val find = Regex("(\\d|one|two|three|four|five|six|seven|eight|nine)").find(line, cursor)
                    if (find != null) {
                        numbers.add(mapNum(find.value))
                        cursor = find.range.first + 1
                    } else {
                        cursor++
                    }
                }
                numbers
            }
            .map { line -> "${line.first()}${line.last()}" }
            .sumOf { it.toInt() }
    }

//     test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    val part1 = part1(testInput)
    println(part1)
    check(part1 == 142)

    val input = readInput("Day01")
    part1(input).println()

    val testInput2 = readInput("Day01_2_test")
    val part2 = part2(testInput2)
    println(part2)
    check(part2 == 281)
    part2(input).println()
}


private fun getNumber(line: String): Int {
    var startIndex = 0
    var endIndex = line.length - 1

    var firstD: String? = null
    var lastD: String? = null

    println(line)
    while (firstD == null || lastD == null) {
        val head = line[startIndex++]
        if (firstD == null && head.isDigit()) firstD = head.toString()

        val tail = line[endIndex--]
        if (lastD == null && tail.isDigit()) lastD = tail.toString()
    }

    val value = "$firstD$lastD".toInt()
    println(value)
    println("====================================")
    return value
}

fun mapNum(str: String): Int {
    return when (str) {
        "one" -> 1
        "two" -> 2
        "three" -> 3
        "four" -> 4
        "five" -> 5
        "six" -> 6
        "seven" -> 7
        "eight" -> 8
        "nine" -> 9
        else -> str.toInt()
    }
}
