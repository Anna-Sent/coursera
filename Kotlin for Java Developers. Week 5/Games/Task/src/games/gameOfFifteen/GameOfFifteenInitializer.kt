package games.gameOfFifteen

interface GameOfFifteenInitializer {
    /*
     * Even permutation of numbers 1..15
     * used to initialized the first 15 cells on a board.
     * The last cell is empty.
     */
    val initialPermutation: List<Int>
}

class RandomGameInitializer : GameOfFifteenInitializer {
    /*
     * Generate a random permutation from 1 to 15.
     * `shuffled()` function might be helpful.
     * If the permutation is not even, make it even (for instance,
     * by swapping two numbers).
     */
    override val initialPermutation by lazy {
        val list = generateSequence(1) { it + 1 }
                .take(15)
                .toList()
                .shuffled()
        if (isEven(list)) {
            return@lazy list
        }
        val swapped = ArrayList(list)
        val tmp = swapped[0]
        swapped[0] = swapped[1]
        swapped[1] = tmp
        return@lazy swapped
    }
}
