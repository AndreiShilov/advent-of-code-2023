package day5


import println
import readInput


data class AlmanacMapping(
    val destinationRangeStart: Long,
    val sourceRangeStart: Long,
    val rangeLength: Long
)

fun main() {

    fun part1(input: List<String>): Long {

        val breaks = input.mapIndexedNotNull { index, line -> if (line.isEmpty()) index else null }.toList()
        // part 1
        val seedInput = input[0].substring(7).split(" ").map { it.toLong() }

        val seedsToSoil = input.subList(3, breaks[1])
        val soilToFertilizer = input.subList(breaks[1] + 2, breaks[2])
        val FertilizerToWater = input.subList(breaks[2] + 2, breaks[3])
        val waterTolight = input.subList(breaks[3] + 2, breaks[4])
        val lightToTemperature = input.subList(breaks[4] + 2, breaks[5])
        val temperatureToHumidity = input.subList(breaks[5] + 2, breaks[6])
        val humidityToLocation = input.subList(breaks[6] + 2, input.size)

        val locations = seedInput.findNext(seedsToSoil.map(::toAlmanacMapping))
            .findNext(soilToFertilizer.map(::toAlmanacMapping))
            .findNext(FertilizerToWater.map(::toAlmanacMapping))
            .findNext(waterTolight.map(::toAlmanacMapping))
            .findNext(lightToTemperature.map(::toAlmanacMapping))
            .findNext(temperatureToHumidity.map(::toAlmanacMapping))
            .findNext(humidityToLocation.map(::toAlmanacMapping))


        println(locations)

        return locations.min()
    }

    fun part2(input: List<String>): Long {
        val breaks = input.mapIndexedNotNull { index, line -> if (line.isEmpty()) index else null }.toList()
        // part 1
        val seedInput = input[0].substring(7)
            .split(" ")
            .map { it.toLong() }
            .chunked(2)
            .map { LongRange(it.first(), it.first() + it.last()) }

        val seedsToSoil = input.subList(3, breaks[1]).map(::toAlmanacMapping)
        val soilToFertilizer = input.subList(breaks[1] + 2, breaks[2]).map(::toAlmanacMapping)
        val FertilizerToWater = input.subList(breaks[2] + 2, breaks[3]).map(::toAlmanacMapping)
        val waterTolight = input.subList(breaks[3] + 2, breaks[4]).map(::toAlmanacMapping)
        val lightToTemperature = input.subList(breaks[4] + 2, breaks[5]).map(::toAlmanacMapping)
        val temperatureToHumidity = input.subList(breaks[5] + 2, breaks[6]).map(::toAlmanacMapping)
        val humidityToLocation = input.subList(breaks[6] + 2, input.size).map(::toAlmanacMapping)


        var min = Long.MAX_VALUE

        for (range in seedInput) {
            var rangeStart = range.first
            val rangeEnd = range.last
            println(range)
            generateSequence(rangeStart) {
                if (it < rangeEnd) it + 1
                else null
            }.forEach {
                val location = it.process(seedsToSoil)
                    .process(soilToFertilizer)
                    .process(FertilizerToWater)
                    .process(waterTolight)
                    .process(lightToTemperature)
                    .process(temperatureToHumidity)
                    .process(humidityToLocation)

                if (location < min) min = location
            }
        }

        return min
    }


    val testInput = readInput("day5/Day05_test")
//    val part1 = part1(testInput)
//    part1.println()

    val input = readInput("day5/Day05")
//    part1(input).println()
//
    val part2 = part2(testInput)
    println(part2)
    println(part2(input))

}


private fun toAlmanacMapping(line: String): AlmanacMapping {
    val (dest, source, range) = line.split(" ").map { it.toLong() }
    return AlmanacMapping(dest, source, range)
}

private fun List<Pair<Long, Long>>.findNextFromRange(mappings: List<AlmanacMapping>): List<Pair<Long, Long>> {

    println("=================")
    val ranges = mappings.flatMap {
        val mappingSourceRange = it.sourceRangeStart to it.sourceRangeStart + it.rangeLength

        this.mapNotNull { range ->
            println("-------------")
            println(range)
            if (range.first > mappingSourceRange.second) return@mapNotNull null
            if (range.second < mappingSourceRange.first) return@mapNotNull null

            if (range.first >= mappingSourceRange.first && range.second > mappingSourceRange.second) {
                return@mapNotNull range.first to mappingSourceRange.second
            }
            if (range.first >= mappingSourceRange.first && range.second <= mappingSourceRange.second) {
                return@mapNotNull range.first to range.second
            }

            if (range.first < mappingSourceRange.first && range.second > mappingSourceRange.second) {
                return@mapNotNull mappingSourceRange.first to mappingSourceRange.second
            }

            if (range.first < mappingSourceRange.first && range.second <= mappingSourceRange.second) {
                return@mapNotNull mappingSourceRange.first to range.second
            }

            null
        }
    }

    println(ranges)
    return ranges
}

private fun List<Long>.findNext(mappings: List<AlmanacMapping>): List<Long> {
    return this.map { inputValue -> inputValue.process(mappings) }
}

private fun Long.process(mappings: List<AlmanacMapping>): Long {
    for (mapping in mappings) {
        val start = mapping.sourceRangeStart
        val range = mapping.rangeLength
        if (this >= start && this <= start + range) {
            return mapping.destinationRangeStart + this - start
        }
    }
    return this
}

