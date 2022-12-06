import java.io.File
import java.io.InputStream

class Day4 {
    companion object {
        private val inputStream: InputStream = File("src/main/resources/day4.txt").inputStream()
        private val input = inputStream.bufferedReader().use { it.readText() }
        private val workSchedulePairs = input.split("\n")

        fun partOne(): Int {
            return workSchedulePairs.sumOf { pair ->
                val pairList = pair.split(",")
                val first = pairList[0]
                val second = pairList[1]

                if (getSectionRange(first).minus(getSectionRange(second)).isEmpty() ||
                    getSectionRange(second).minus(getSectionRange(first)).isEmpty()) {
                    1 as Int
                } else {
                    0 as Int
                }
            }
        }

        fun partTwo(): Int {
            return workSchedulePairs.sumOf { pair ->
                val pairList = pair.split(",")
                val first = pairList[0]
                val second = pairList[1]

                if (getSectionRange(first).minus(getSectionRange(second)).size < rangeSize(first) ||
                    getSectionRange(second).minus(getSectionRange(first)).size < rangeSize(second)) {
                    1 as Int
                } else {
                    0 as Int
                }
            }
        }

        private fun getSectionRange(first: String): IntRange {
            val sectionStartStop = first.split("-")
            return sectionStartStop[0].toInt()..sectionStartStop[1].toInt()
        }

        private fun rangeSize(first: String) = (getSectionRange(first).max() - getSectionRange(first).min() + 1)
    }
}


fun main() {
    println("Part One: " + Day4.partOne())
    println("Part Two: " + Day4.partTwo())
}
