package board

fun createSquareBoard(width: Int): SquareBoard = object : SquareBoard {

    override val width: Int = width

    private val table: Array<Array<Cell>> = Array(width)
    { row ->
        Array(width)
        { col ->
            Cell(row + 1, col + 1)
        }
    }

    override fun getCellOrNull(i: Int, j: Int): Cell? =
            if (i in 1..width && j in 1..width) table[i - 1][j - 1] else null

    override fun getCell(i: Int, j: Int): Cell =
            getCellOrNull(i, j) ?: throw IllegalArgumentException("Index out of bounds: $i, $j")

    override fun getAllCells(): Collection<Cell> =
            getCells(1..width, 1..width)

    override fun getRow(i: Int, jRange: IntProgression): List<Cell> =
            getCells(i..i, jRange)

    override fun getColumn(iRange: IntProgression, j: Int): List<Cell> =
            getCells(iRange, j..j)

    private fun getCells(iRange: IntProgression, jRange: IntProgression): List<Cell> {
        val list = mutableListOf<Cell>()
        for (i in iRange) {
            for (j in jRange) {
                val cell = getCellOrNull(i, j)
                if (cell != null)
                    list += cell
            }
        }
        return list
    }

    override fun Cell.getNeighbour(direction: Direction): Cell? =
            when (direction) {
                Direction.UP -> getCellOrNull(i - 1, j)
                Direction.DOWN -> getCellOrNull(i + 1, j)
                Direction.RIGHT -> getCellOrNull(i, j + 1)
                Direction.LEFT -> getCellOrNull(i, j - 1)
            }
}

fun <T> createGameBoard(width: Int): GameBoard<T> = object : GameBoard<T> {

    override val width: Int = width

    private val table: SquareBoard = createSquareBoard(width)

    private val values: MutableMap<Cell, T> = mutableMapOf()

    override fun getCellOrNull(i: Int, j: Int): Cell? =
            table.getCellOrNull(i, j)

    override fun getCell(i: Int, j: Int): Cell =
            table.getCell(i, j)

    override fun getAllCells(): Collection<Cell> =
            table.getAllCells()

    override fun getRow(i: Int, jRange: IntProgression): List<Cell> =
            table.getRow(i, jRange)

    override fun getColumn(iRange: IntProgression, j: Int): List<Cell> =
            table.getColumn(iRange, j)

    override fun Cell.getNeighbour(direction: Direction): Cell? =
            getNeighbour(direction)

    override fun get(cell: Cell): T? = values[cell]

    override fun set(cell: Cell, value: T?) {
        if (value == null)
            values.remove(cell)
        else
            values[cell] = value
    }

    override fun filter(predicate: (T?) -> Boolean): Collection<Cell> =
            allValues()
                    .filterValues(predicate)
                    .keys

    override fun find(predicate: (T?) -> Boolean): Cell? =
            filter(predicate).firstOrNull()

    override fun any(predicate: (T?) -> Boolean): Boolean =
            allValues()
                    .any { (_, value) -> predicate(value) }

    override fun all(predicate: (T?) -> Boolean): Boolean =
            allValues()
                    .all { (_, value) -> predicate(value) }

    private fun allValues(): Map<Cell, T?> =
            getAllCells()
                    .associate { cell -> cell to get(cell) }
}
