import java.io.File
import java.io.InputStream

class Day8 {
    companion object {
        private val inputStream: InputStream = File("src/main/resources/day8.txt").inputStream()
        private val input = inputStream.bufferedReader().use { it.readText() }
        private val lines = input.split("\n") as ArrayList<String>

        fun partOne(): Int {
            val yAxisRange = (lines.indices)
            val xAxisRange = (0 until lines[0].length)
            val countedMap = lines.map { line ->
                line.map { _ -> false } as ArrayList
            } as ArrayList


            return treeColumnCount(xAxisRange, yAxisRange, countedMap) +
            treeColumnCount(xAxisRange, yAxisRange.reversed(), countedMap) +
            treeRowCount(xAxisRange,yAxisRange, countedMap) +
            treeRowCount(xAxisRange.reversed(),yAxisRange, countedMap)
        }

        fun partTwo(): Int {
            val mapOfScores = lines.mapIndexed { lineIndex, line ->
                val x = line.mapIndexed { treeIndex, _ ->
                    var down = treesInViewDown(lineIndex, treeIndex)
                    if (down == 0) down = 1
                    var up = treesInViewYUp(lineIndex, treeIndex)
                    if (up == 0) up = 1
                    var right = treesInViewRight(lineIndex, treeIndex)
                    if (right == 0) right = 1
                    var left = treesInViewXLeft(lineIndex, treeIndex)
                    if (left == 0) left = 1
                    down * up * right * left
                }
                x
            }

            return mapOfScores.maxOf { item ->
                val itemMax = item.max()
                itemMax
            }
        }

        private fun treeColumnCount(xAxisLength: IntProgression, yAxisLength: IntProgression, countedMap: List<java.util.ArrayList<Boolean>>): Int {
            return xAxisLength.sumOf { column ->
                var highestTree = -1
                arrayListOf(yAxisLength.sumOf { row ->
                    val currentTree = lines[row][column]
                    val treeSize = (currentTree.code - '0'.code)
                    if (treeSize > highestTree) {
                        highestTree = treeSize
                        if (!countedMap[row][column]) {
                            countedMap[row][column] = true
                            1.toInt()
                        } else {
                            0
                        }
                    } else {
                        0.toInt()
                    }
                }).sum()
            }
        }

        private fun treeRowCount(xAxisLength: IntProgression, yAxisLength: IntProgression, countedMap: List<java.util.ArrayList<Boolean>>): Int {
            return yAxisLength.sumOf { row ->
                var highestTree = -1
                arrayListOf(xAxisLength.sumOf { column ->
                    val currentTree = lines[row][column]
                    val treeSize = (currentTree.code - '0'.code)
                    if (treeSize > highestTree) {
                        highestTree = treeSize
                        if (!countedMap[row][column]) {
                            countedMap[row][column] = true
                            1.toInt()
                        } else {
                            0
                        }
                    } else {
                        0.toInt()
                    }
                }).sum()
            }
        }

        private fun treesInViewDown(rowStartIndex: Int, columnIndex: Int): Int {
            val treeHouseHeight = lines[rowStartIndex][columnIndex].code - '0'.code
            var highestTree = 0
            var treeBlocking = false
            return lines.subList(rowStartIndex + 1, lines.lastIndex + 1).sumOf { line ->
                val currentTree = line[columnIndex].code - '0'.code
                if (currentTree > highestTree || !treeBlocking) {
                    if (currentTree >= treeHouseHeight) treeBlocking = true
                    highestTree = currentTree
                    1.toInt()
                } else {
                    0.toInt()
                }
            }
        }

        private fun treesInViewYUp(rowStartIndex: Int, columnIndex: Int): Int {
            val treeHouseHeight = lines[rowStartIndex][columnIndex]
            var highestTree = treeHouseHeight
            var treeBlocking = false
            return lines.subList(0, rowStartIndex).reversed().sumOf { line ->
                val currentTree = line[columnIndex]
                if (currentTree > highestTree || !treeBlocking) {
                    if (currentTree >= treeHouseHeight) treeBlocking = true
                    highestTree = currentTree
                    1.toInt()
                } else {
                    0.toInt()
                }
            }
        }

        private fun treesInViewRight(rowIndex: Int, columnStartIndex: Int): Int {
            val treeHouseHeight = lines[rowIndex][columnStartIndex].code - '0'.code
            var highestTree = 0
            var treeBlocking = false
            return lines[rowIndex].substring(columnStartIndex + 1, lines[rowIndex].length).sumOf { currentTree ->
                if (currentTree.code - '0'.code > highestTree || !treeBlocking) {
                    if (currentTree.code - '0'.code >= treeHouseHeight) treeBlocking = true
                    if (currentTree.code - '0'.code > highestTree) highestTree = currentTree.code - '0'.code
                    1.toInt()
                } else {
                    0.toInt()
                }
            }
        }

        private fun treesInViewXLeft(rowIndex: Int, columnStartIndex: Int): Int {
            val treeHouseHeight = lines[rowIndex][columnStartIndex]
            var highestTree = treeHouseHeight
            var treeBlocking = false
            return lines[rowIndex].reversed().substring(lines[rowIndex].length- columnStartIndex).sumOf { currentTree ->
                if (currentTree > highestTree || !treeBlocking) {
                    if (currentTree >= treeHouseHeight) treeBlocking = true
                    highestTree = currentTree
                    1.toInt()
                } else {
                    0.toInt()
                }
            }
        }
    }
}


fun main() {
    println("Part One: " + Day8.partOne())
    println("Part Two: " + Day8.partTwo())
}
