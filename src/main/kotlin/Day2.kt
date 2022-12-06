import Day2.Companion.partOne
import Day2.Companion.partTwo
import java.io.File
import java.io.InputStream

class Day2() {

    companion object {
        private val inputStream: InputStream = File("src/main/resources/day2.txt").inputStream()
        private val strategy = inputStream.bufferedReader().use { it.readText() }

        private val enemyDefeatedByList = mapOf("A" to "Y", "B" to "Z", "C" to "X")
        private val iWantToLoseList = mapOf("A" to "Z", "B" to "X", "C" to "Y")
        private val letterMappingList = mapOf("A" to "X", "B" to "Y", "C" to "Z")
        private val playerPieceScore = mapOf("X" to 1, "Y" to 2, "Z" to 3)
        fun partOne(): Int {
            return strategy
                .split("\n")
                .sumOf { round ->
                    val enemyTurn = round.split(" ")[0]
                    val myTurn = round.split(" ")[1]
                    val winScore = when (myTurn) {
                        enemyDefeatedByList[enemyTurn] -> {
                            6
                        }

                        letterMappingList[enemyTurn] -> {
                            3
                        }

                        else -> {
                            0
                        }
                    }
                    winScore + playerPieceScore[myTurn]!!
                }
        }

        fun partTwo(): Int {
            return strategy
                .split("\n")
                .sumOf { round ->
                    val enemyTurn = round.split(" ")[0]
                    val myTurn = round.split(" ")[1]
                    val newStrategy = mapOf(
                        "X" to iWantToLoseList[enemyTurn],
                        "Y" to letterMappingList[enemyTurn],
                        "Z" to enemyDefeatedByList[enemyTurn]
                    )
                    val newPlayerPiece = newStrategy[myTurn]
                    val winScore = when (newPlayerPiece) {
                        enemyDefeatedByList[enemyTurn] -> {
                            6
                        }

                        letterMappingList[enemyTurn] -> {
                            3
                        }

                        else -> {
                            0
                        }
                    }
                    winScore + playerPieceScore[newPlayerPiece]!!
                }
        }
    }

}

fun main() {
    println("Part One Score: ${partOne()}")
    println("Part Two Score: ${partTwo()}")
}

