import java.io.File
import java.io.InputStream

class Day6 {
    companion object {
        private val inputStream: InputStream = File("src/main/resources/day6.txt").inputStream()
        private val input = inputStream.bufferedReader().use { it.readText() }

        fun solve(patternLength: Int): Int {
            return input.mapIndexed { index, _ ->
                if (index + patternLength < input.length) {
                    val stringToCompare = input.substring(index, index + patternLength)
                    stringToCompare.toSet().size == stringToCompare.length
                } else {
                    false
                }
            }.indexOfFirst { it } + patternLength
        }
    }
}

fun main() {
    println("Part One: " + Day6.solve(4))
    println("Part Two: " + Day6.solve(14))
}
