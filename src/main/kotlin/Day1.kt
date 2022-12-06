import java.io.File
import java.io.InputStream

class Elf(val name:String, val arrayOfFoodItems: List<Int>, val sumOfCalories: Int)

fun main() {
    val inputStream: InputStream = File("src/main/resources/day1.txt").inputStream()
    val elfsUnnamed = inputStream.bufferedReader().use { it.readText() }

    var enumeration = 0
    val elves: ArrayList<Elf> = elfsUnnamed
        .split("\n\n")
        .map { listOfFoodItems ->
            val listOfFoodItemsCaloriesInInt: List<Int> = listOfFoodItems
                .split("\n")
                .map(String::toInt)
            enumeration++
            Elf("Elf $enumeration", listOfFoodItemsCaloriesInInt, listOfFoodItemsCaloriesInInt.sum())
        } as ArrayList<Elf>


    val totalForTopThreeElfs = elves.sortedByDescending { elf -> elf.sumOfCalories }
        .take(3)
        .sumOf { it.sumOfCalories }

    println(totalForTopThreeElfs)

}