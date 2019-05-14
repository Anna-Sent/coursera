package nicestring

fun String.isNice(): Boolean {
    return listOf(
            first(),
            second(),
            third())
            .count { it } >= 2
}

fun String.first(): Boolean {
    return setOf("bu", "ba", "be")
            .none { contains(it) }
}

fun String.second(): Boolean {
    return count { it in setOf('a', 'e', 'i', 'o', 'u') } >= 3
}

fun String.third(): Boolean {
    return zipWithNext()
            .any { it.first == it.second }
}
