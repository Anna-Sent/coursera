package rationals

import java.math.BigInteger

data class Rational(val n: BigInteger, val d: BigInteger) : Comparable<Rational> {

    constructor(n: Int, d: Int) : this(n.toBigInteger(), d.toBigInteger())

    constructor(n: Long, d: Long) : this(n.toBigInteger(), d.toBigInteger())

    init {
        if (d == BigInteger.ZERO) {
            throw IllegalArgumentException("Denominator can't be zero")
        }
    }

    fun normalize(): Rational {
        val divisor = n.gcd(d)
        val nSign = if (n >= BigInteger.ZERO) BigInteger.ONE else BigInteger.valueOf(-1)
        val dSign = if (d >= BigInteger.ZERO) BigInteger.ONE else BigInteger.valueOf(-1)
        val sign = nSign * dSign
        return Rational(sign * (n / divisor).abs(), (d / divisor).abs())
    }

    override fun compareTo(other: Rational): Int {
        TODO()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Rational) return false
        val normalizedThis = normalize()
        val normalizedOther = other.normalize()
        return normalizedThis.n == normalizedOther.n
                && normalizedThis.d == normalizedOther.d
    }

    override fun hashCode(): Int {
        val prime = 31
        var result = 1
        result = prime * result + n.hashCode()
        result = prime * result + d.hashCode()
        return result
    }
}

infix fun Int.divBy(d: Int): Rational = Rational(this, d)
infix fun Long.divBy(d: Long): Rational = Rational(this, d)
infix fun BigInteger.divBy(d: BigInteger): Rational = Rational(this, d)

operator fun Rational.plus(other: Rational): Rational = TODO()
operator fun Rational.minus(other: Rational): Rational = TODO()
operator fun Rational.unaryMinus(): Rational = TODO()
operator fun Rational.div(other: Rational): Rational = TODO()
operator fun Rational.times(other: Rational): Rational = TODO()

fun String.toRational(): Rational = TODO()

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
}