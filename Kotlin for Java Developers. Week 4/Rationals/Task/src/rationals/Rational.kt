package rationals

import java.math.BigInteger

@Suppress("DataClassPrivateConstructor")
data class Rational private constructor(val n: BigInteger, val d: BigInteger) : Comparable<Rational> {

    init {
        if (d == BigInteger.ZERO) {
            throw IllegalArgumentException("Denominator can't be zero")
        }
    }

    companion object {
        fun create(n: Int, d: Int) = Rational(n.toBigInteger(), d.toBigInteger()).normalize()
        fun create(n: Long, d: Long) = Rational(n.toBigInteger(), d.toBigInteger()).normalize()
        fun create(n: BigInteger, d: BigInteger) = Rational(n, d).normalize()
    }

    private fun normalize(): Rational {
        val divisor = n.gcd(d)
        val sign = d.signum().toBigInteger()
        return Rational(sign * n / divisor, sign * d / divisor)
    }

    override fun compareTo(other: Rational): Int {
        return (n * other.d).compareTo(d * other.n)
    }

    override fun toString(): String {
        return if (d == BigInteger.ONE) "$n" else "$n/$d"
    }
}

infix fun Int.divBy(d: Int): Rational = Rational.create(this, d)
infix fun Long.divBy(d: Long): Rational = Rational.create(this, d)
infix fun BigInteger.divBy(d: BigInteger): Rational = Rational.create(this, d)

operator fun Rational.plus(other: Rational): Rational {
    val lcm = d * other.d / d.gcd(other.d)
    return Rational.create(n * lcm / d + other.n * lcm / other.d, lcm)
}

operator fun Rational.minus(other: Rational): Rational {
    val lcm = d * other.d / d.gcd(other.d)
    return Rational.create(n * lcm / d - other.n * lcm / other.d, lcm)
}

operator fun Rational.unaryMinus(): Rational = copy(n = -n)

operator fun Rational.div(other: Rational): Rational = Rational.create(n * other.d, d * other.n)

operator fun Rational.times(other: Rational): Rational = Rational.create(n * other.n, d * other.d)

const val delimiter = "/"

fun String.toRational(): Rational {
    fun String.toBigIntegerOrFail() =
            toBigIntegerOrNull() ?: throw java.lang.IllegalArgumentException(
                    "Expecting rational in the form of 'n/d' or 'n', was: '${this@toRational}'")
    if (delimiter in this) {
        val (nStr, dStr) = split(delimiter)
        val n = nStr.toBigIntegerOrFail()
        val d = dStr.toBigIntegerOrFail()
        return Rational.create(n, d)
    }
    val n = toBigIntegerOrFail()
    return Rational.create(n, BigInteger.ONE)
}

fun main() {
    val half = 1 divBy 2
    val third = 1 divBy 3

    val sum: Rational = half + third
    println(5 divBy 6 == sum)

    val difference: Rational = half - third
    println(1 divBy 6 == difference)

    val product: Rational = half * third
    println(1 divBy 6 == product)

    val quotient: Rational = half / third
    println(3 divBy 2 == quotient)

    val negation: Rational = -half
    println(-1 divBy 2 == negation)

    println((2 divBy 1).toString() == "2")
    println((-2 divBy 4).toString() == "-1/2")
    println("117/1098".toRational().toString() == "13/122")

    val twoThirds = 2 divBy 3
    println(half < twoThirds)

    println(half in third..twoThirds)

    println(2000000000L divBy 4000000000L == 1 divBy 2)

    println("912016490186296920119201192141970416029".toBigInteger() divBy
            "1824032980372593840238402384283940832058".toBigInteger() == 1 divBy 2)

    println("1/2/3".toRational())
}