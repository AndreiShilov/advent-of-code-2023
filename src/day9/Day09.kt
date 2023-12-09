package day9


import println
import readInput

fun main() {


    fun findPredictionValue(history: List<Int>): Pair<Int, Int> {

        val iterations = mutableListOf<List<Int>>()
        var currentIteration = history

        while (!currentIteration.all { it == 0 }) {
            iterations.add(currentIteration)
            currentIteration = currentIteration.foldIndexed(mutableListOf()) { index, acc, value ->
                if (index != 0) {
                    acc.add(value - currentIteration[index - 1])
                }
                acc
            }
        }

        val predictionNext = iterations.fold(0) { acc, ints ->
            acc + ints.last()
        }

        println("=====")
        val predictionsPrevious = iterations.reversed().fold(0) { acc, ints ->
            ints.first() - acc
        }
        println("Previous $predictionsPrevious .... $predictionNext Next")


        return predictionsPrevious to  predictionNext
    }

    fun part1(input: List<String>): Int {

        val histories = input.map { line ->
            line.split(" ").map { stringNum -> stringNum.toInt() }
        }

        return histories.map(::findPredictionValue).sumOf { it.second }
    }

    fun part2(input: List<String>): Int {
        val histories = input.map { line ->
            line.split(" ").map { stringNum -> stringNum.toInt() }
        }

        return histories.map(::findPredictionValue).sumOf { it.first }
    }


    val testInput = readInput("day9/Day09_test")
    val part1 = part1(testInput)
    part1.println()
//
    val input = readInput("day9/Day09")
    part1(input).println()
//
    val part2 = part2(testInput)
    println(part2)
    println(part2(input))
}

