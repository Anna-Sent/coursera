package games.gameOfFifteen

import board.Direction
import games.game.Game

/*
 * Implement the Game of Fifteen (https://en.wikipedia.org/wiki/15_puzzle).
 * When you finish, you can play the game by executing 'PlayGameOfFifteen'.
 */
fun newGameOfFifteen(initializer: GameOfFifteenInitializer = RandomGameInitializer()): Game =
        object : Game {

            var list: ArrayList<Int?> = initial()
            var empty: Int = 15

            override fun initialize() {
                list = initial()
                empty = 15
            }

            fun initial(): ArrayList<Int?> =
                    ArrayList(initializer.initialPermutation)
                            .apply {
                                add(null)
                            }

            override fun canMove(): Boolean {
                return true
            }

            override fun hasWon(): Boolean {
                for (i in 1..15) {
                    if (i != list[i - 1]) {
                        return false
                    }
                }
                return true
            }

            override fun processMove(direction: Direction) {
                when (direction) {
                    Direction.LEFT -> {
                        if (empty % 4 == 3) return
                        val left = list[empty + 1]
                        list[empty + 1] = null
                        list[empty] = left
                        empty += 1
                    }
                    Direction.RIGHT -> {
                        if (empty % 4 == 0) return
                        val left = list[empty - 1]
                        list[empty - 1] = null
                        list[empty] = left
                        empty -= 1
                    }
                    Direction.DOWN -> {
                        if (empty <= 3) return
                        val left = list[empty - 4]
                        list[empty - 4] = null
                        list[empty] = left
                        empty -= 4
                    }
                    Direction.UP -> {
                        if (empty >= 12) return
                        val left = list[empty + 4]
                        list[empty + 4] = null
                        list[empty] = left
                        empty += 4
                    }
                }
            }

            override fun get(i: Int, j: Int): Int? {
                return list.getOrNull((i - 1) * 4 + (j - 1))
            }
        }
