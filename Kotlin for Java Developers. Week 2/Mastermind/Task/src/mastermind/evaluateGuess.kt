package mastermind

import java.util.*
import kotlin.math.min

data class Evaluation(val rightPosition: Int, val wrongPosition: Int)

fun evaluateGuess(secret: String, guess: String): Evaluation {

    val rightPositions = secret.zip(guess).count { it.first == it.second }

    val commonLetters = "ABCDEF".sumBy { ch ->

        Math.min(secret.count { it == ch }, guess.count { it == ch })
    }
    return Evaluation(rightPositions, commonLetters - rightPositions)
}

fun evaluateGuess_(secret: String, guess: String): Evaluation {
    var rightPosition = 0
    val matchingIndicies = mutableSetOf<Int>()
    for (i in 0 until min(secret.length, guess.length)) {
        if (secret[i] == guess[i]) {
            ++rightPosition
            matchingIndicies += i
        }
    }

    val secretPairs = calcNotMatchingPairs(secret, matchingIndicies)
    val guessPairs = calcNotMatchingPairs(guess, matchingIndicies)

    var i = 0
    var j = 0
    var wrongPosition = 0
    while (i < secretPairs.size && j < guessPairs.size) {
        when {
            secretPairs[i].first == guessPairs[j].first -> {
                ++wrongPosition
                ++i
                ++j
            }
            secretPairs[i].first < guessPairs[j].first -> {
                ++i
            }
            else -> {
                ++j
            }
        }
    }

    return Evaluation(rightPosition, wrongPosition)
}

fun calcNotMatchingPairs(s: String, matchingIndicies: Set<Int>): List<Pair<Char, Int>> {
    val list = mutableListOf<Pair<Char, Int>>()
    for ((index, ch) in s.withIndex()) {
        if (index !in matchingIndicies)
            list += ch to index
    }
    return list.sortedWith(Comparator { p1, p2 ->
        when {
            p1.first == p2.first -> p1.second.compareTo(p2.second)
            else -> p1.first.compareTo(p2.first)
        }
    })
}
