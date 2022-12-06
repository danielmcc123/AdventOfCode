import java.io.File
import java.io.InputStream


class Day3 {
    companion object {
        private val inputStream: InputStream = File("src/main/resources/day3.txt").inputStream()
        private val allRucksacksRaw = inputStream.bufferedReader().use { it.readText() }
        private val rucksackContentsList = allRucksacksRaw.split("\n")

        fun partOne(): Int {
            return rucksackContentsList.sumOf { rucksack ->
                val compartmentOne: CharSequence = rucksack.take(rucksack.length / 2)
                val compartmentTwo: CharSequence = rucksack.subSequence(rucksack.length / 2, rucksack.length)
                val duplicateItem = compartmentOne.first { item ->
                    compartmentTwo.contains(item)
                }
                getPriorityScore(duplicateItem)
            }
        }

        fun partTwo(): Int {
            return rucksackContentsList
                .chunked(3)
                .sumOf { rucksackGroup ->
                    val groupItem = rucksackGroup[0].filter { item ->
                        rucksackGroup[1].contains(item)
                    }.first { item ->
                        rucksackGroup[2].contains(item)
                    }
                    getPriorityScore(groupItem)
                }
        }

        private fun getPriorityScore(itemChar: Char): Int {
            val asciiValue = itemChar.code
            val priorityScore: Int = if (asciiValue in 97..122) {
                asciiValue - 96
            } else {
                asciiValue - 38
            }
            return priorityScore
        }
    }
}


fun main() {
    println("Part One: " + Day3.partOne())
    println("Part Two: " + Day3.partTwo())
}
