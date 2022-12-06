import java.io.File
import java.io.InputStream
import java.util.Stack

class Day5 {
    companion object {
        private val inputStream: InputStream = File("src/main/resources/day5.txt").inputStream()
        private val input = inputStream.bufferedReader().use { it.readText() }
        private val rows = input.split("\n")
        private val divider = rows.indexOf("")
        private val positionMap = rows.take(divider)
        private val positionMapIndexRange = 0..positionMap[positionMap.lastIndex].replace(" ", "").length -1
        private val moveInstructions = rows.subList(divider + 1, rows.lastIndex + 1)

        fun partOne(): String {
            val stacks = buildInitialStacks(positionMap.take(positionMap.lastIndex))
            sortSingleBox(stacks)
            return stacks.joinToString(separator = "") { stack ->
                stack.pop()
            }
        }

        fun partTwo(): String {
            val stacks = buildInitialStacks(positionMap.take(positionMap.lastIndex))
            sortMultipleBoxes(stacks)
            return stacks.joinToString(separator = "") { stack ->
                stack.pop()
            }
        }

        private fun sortSingleBox(stacks: List<Stack<String>>) {
            moveInstructions.map { instructionSet ->
                val instructionSetArray = instructionSet.split(" ")
                val numberToMove = 1..instructionSetArray[1].toInt()
                val moveFrom = instructionSetArray[3].toInt().minus(1)
                val moveTo = instructionSetArray[5].toInt().minus(1)

                repeat(numberToMove.count()) {
                    stacks[moveTo].push(stacks[moveFrom].pop())
                }
            }
        }


        private fun sortMultipleBoxes(stacks: List<Stack<String>>) {
            moveInstructions.map { instructionSet ->
                val instructionSetArray = instructionSet.split(" ")
                val numberToMove = 1..instructionSetArray[1].toInt()
                val moveFrom = instructionSetArray[3].toInt().minus(1)
                val moveTo = instructionSetArray[5].toInt().minus(1)

                val tempStack = Stack<String>()
                repeat(numberToMove.count()) {
                    tempStack.push(stacks[moveFrom].pop())
                }
                repeat(numberToMove.count()) {
                    stacks[moveTo].push(tempStack.pop())
                }
            }
        }

        private fun buildInitialStacks (positionMap: List<String>): List<Stack<String>> {
            val boxes = positionMap.map { row ->
                row.chunked(4) as ArrayList<String>
            }

            return positionMapIndexRange.map { index ->
                val crates = Stack<String>()
                boxes.reversed().map { row ->
                    if (index < row.size && row[index].isNotBlank())
                        crates.push(row[index].substring(1,2))
                }
                crates
            }
        }
    }
}


fun main() {
    println("Part One: " + Day5.partOne())
    println("Part Two: " + Day5.partTwo())
}
