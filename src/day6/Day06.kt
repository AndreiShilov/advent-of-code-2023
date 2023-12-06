package day6


import println
import readInput


fun main() {

    fun part1(input: List<String>): Long {

        val map = input.map {
            it.split(":")[1]
                .split(" ")
                .filter { number -> number.isNotEmpty() }
                .map { number -> number.trim().toInt() }
        }

        val times = map[0]
        val distances = map[1]

        val winStrategiesPerRace = times.mapIndexed { index, time ->
            val distanceToBeat = distances[index]

            val possibleWinStrategies = (0..time)
                .map { chargingTimeAndSpeed ->
                    val drivingTime = time - chargingTimeAndSpeed
                    val distanceInAttempt = drivingTime * chargingTimeAndSpeed

                    distanceInAttempt > distanceToBeat
                }
                .count { it }
            possibleWinStrategies
        }

        return winStrategiesPerRace.fold(1) { acc, i ->
            acc * i
        }
    }

    fun part2(input: List<String>): Int {

        val map = input.map {
            it.filter { c -> c.isDigit() }.toLong()
        }

        val time = map[0]
        val distanceToBeat = map[1]

        return (0..time)
            .map { chargingTimeAndSpeed ->
                val drivingTime = time - chargingTimeAndSpeed
                val distanceInAttempt = drivingTime * chargingTimeAndSpeed

                distanceInAttempt > distanceToBeat
            }
            .count { it }
    }


    val testInput = readInput("day6/Day06_test")
//    val part1 = part1(testInput)
//    part1.println()

    val input = readInput("day6/Day06")
//    part1(input).println()
//
    val part2 = part2(testInput)
    println(part2)
    println(part2(input))
}

