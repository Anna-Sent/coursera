package nicestring

fun String.isNice(): Boolean {
    return listOf(
            first(),
            second(),
            third())
            .count { it } >= 2
}

fun String.first(): Boolean {
    return listOf("bu", "ba", "be")
            .count { contains(it) } == 0
}

fun String.second(): Boolean {
    return count { it in setOf('a', 'e', 'i', 'o', 'u') } >= 3
}

fun String.third(): Boolean {
    return zipWithNext()
            .count { it.first == it.second } > 0
}
