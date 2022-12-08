import java.io.File
import java.io.InputStream

class Day7 {
    companion object {
        private val inputStream: InputStream = File("src/main/resources/day7.txt").inputStream()
        private val input = inputStream.bufferedReader().use { it.readText() }
        private val lines = input.split("\n")

        fun partOne(): Int {
            val directoryRoot: DirectoryNode? = getDirectoryRoot()
            val flattenedListOfNodes = directoryRoot?.getChildren()?.flattenChildNodes()
            val sum = flattenedListOfNodes?.sumOf { node ->
                if (node.getChildren() != null && node.getNodeSize() <= 100000) {
                    node.getNodeSize()
                } else
                    0
            }
            return sum!!
        }

        fun partTwo(): Int {
            val directoryRoot: DirectoryNode? = getDirectoryRoot()

            val minSize = 30000000 - (70000000 - directoryRoot?.getNodeSize()!!)
            val flattenedListOfNodes = directoryRoot.getChildren()?.flattenChildNodes()

            val smallestDeletion = flattenedListOfNodes?.map {  node ->
                if (node.getChildren() != null && node.getNodeSize() > minSize) {
                    node.getNodeSize()
                } else
                    0
            }?.sorted()?.first { it != 0 }

            return smallestDeletion!!
        }

        private fun getDirectoryRoot(): DirectoryNode? {
            var directoryRoot: DirectoryNode? = null
            var currentDirectory: DirectoryNode? = null
            lines.forEach { line ->
                val command = line.split(" ")
                if (line.contains('/')) {
                    directoryRoot = DirectoryNode(command[2], arrayListOf(), null, 0)
                    currentDirectory = directoryRoot
                } else if (line.startsWith('$')) {
                    val commandAndArgument = command.subList(1, command.size)
                    if (commandAndArgument[0] == "cd" && commandAndArgument[1] == "..") {
                        currentDirectory = currentDirectory?.parent
                    } else if (commandAndArgument[0] == "cd") {
                        currentDirectory = currentDirectory?.getChildren()?.first { directoryNode ->
                            directoryNode.name == commandAndArgument[1]
                        }
                    }
                } else if (line[0].isDigit() || line.startsWith("dir")) {
                    if (line[0].isDigit()) {
                        currentDirectory?.addDirectoryNode(
                            DirectoryNode(command[1], null, currentDirectory, command[0].toInt()
                            )
                        )
                    } else {
                        currentDirectory?.addDirectoryNode(
                            DirectoryNode(command[1], arrayListOf(), currentDirectory, 0
                            )
                        )
                    }
                }
            }
            return directoryRoot
        }

        fun List<DirectoryNode>.flattenChildNodes(): List<DirectoryNode> {
            return this + map { it.getChildren()?.flattenChildNodes() ?: emptyList() }.flatten()
        }



        class DirectoryNode(val name: String, private var directoryNodes: ArrayList<DirectoryNode>?, val parent: DirectoryNode?, var size: Int) {
            fun addDirectoryNode(directoryNode: DirectoryNode) {
                this.directoryNodes?.add(directoryNode)
            }

            fun getChildren(): ArrayList<DirectoryNode>? {
                return directoryNodes
            }

            fun getNodeSize(): Int {
                return if (getChildren() == null) {
                    size
                } else {
                    getChildren()?.sumOf { childNode ->
                        childNode.getNodeSize()
                    }!!
                }
            }
        }

    }
}


fun main() {
    println("Part One: " + Day7.partOne())
    println("Part Two: " + Day7.partTwo())
}
