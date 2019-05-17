package games.gameOfFifteen

/*
 * This function should return the parity of the permutation.
 * true - the permutation is even
 * false - the permutation is odd
 * https://en.wikipedia.org/wiki/Parity_of_a_permutation

 * If the game of fifteen is started with the wrong parity, you can't get the correct result
 *   (numbers sorted in the right order, empty cell at last).
 * Thus the initial permutation should be correct.
 */
fun isEven(permutation: List<Int>): Boolean {
    val list = ArrayList(permutation)
    val (_, count) = sort(list)
    return count % 2 == 0
}

fun sort(list: List<Int>): Pair<List<Int>, Int> {
    val sorted = ArrayList(list)
    val size = sorted.size
    var count = 0
    for (i in 0 until size) {
        for (j in 0 until size - i - 1) {
            if (sorted[j] > sorted[j + 1]) {
                val tmp = sorted[j]
                sorted[j] = sorted[j + 1]
                sorted[j + 1] = tmp
                ++count
            }
        }
    }
    return sorted to count
}

fun main() {
    test(listOf(1, 2, 3))
    test(listOf())
    test(listOf(3, 2, 1))
    test(listOf(-1, -2, -3))
    test(listOf(-1, -2, -3, 0, 1, 2, 3))
}

fun test(list: List<Int>) {
    val (sorted, _) = sort(list)
    println(sorted.toString() == list.sorted().toString())
}
