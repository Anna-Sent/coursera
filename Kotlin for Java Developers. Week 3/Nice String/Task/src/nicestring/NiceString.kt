package nicestring

fun String.isNice(): Boolean =
        listOf(
                first(),
                second(),
                third())
                .count { it } >= 2

fun String.first(): Boolean =
        setOf("bu", "ba", "be")
                .none { contains(it) }

fun String.second(): Boolean =
        count { it in setOf('a', 'e', 'i', 'o', 'u') } >= 3

fun String.third(): Boolean =
        zipWithNext()
                .any { it.first == it.second }
