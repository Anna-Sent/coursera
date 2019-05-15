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
        return (n * other.d).compareTo(d * other.n)
    }

    override fun toString(): String {
        val normalized = normalize()
        return if (normalized.d == BigInteger.ONE) "${normalized.n}" else "${normalized.n}/${normalized.d}"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Rational) return false
        val normalized = normalize()
        val normalizedOther = other.normalize()
        return normalized.n == normalizedOther.n
                && normalized.d == normalizedOther.d
    }

    override fun hashCode(): Int {
        val prime = 31
        var result = 1
        val normalized = normalize()
        result = prime * result + normalized.hashCode()
        result = prime * result + normalized.hashCode()
        return result
    }
}

infix fun Int.divBy(d: Int): Rational = Rational(this, d).normalize()
infix fun Long.divBy(d: Long): Rational = Rational(this, d).normalize()
infix fun BigInteger.divBy(d: BigInteger): Rational = Rational(this, d).normalize()

operator fun Rational.plus(other: Rational): Rational {
    val lcm = (d * other.d) / d.gcd(other.d)
    return Rational(n * lcm / d + other.n * lcm / other.d, lcm).normalize()
}

operator fun Rational.minus(other: Rational): Rational {
    val lcm = (d * other.d) / d.gcd(other.d)
    return Rational(n * lcm / d - other.n * lcm / other.d, lcm).normalize()
}

operator fun Rational.unaryMinus(): Rational = copy(n = -n).normalize()

operator fun Rational.div(other: Rational): Rational = Rational(n * other.d, d * other.n).normalize()

operator fun Rational.times(other: Rational): Rational = Rational(n * other.n, d * other.d).normalize()

const val delimiter = "/"

fun String.toRational(): Rational {
    if (delimiter in this) {
        val tokens = split("/")
        val n = tokens[0].toBigInteger()
        val d = tokens[1].toBigInteger()
        return Rational(n, d).normalize()
    }
    val n = toBigInteger()
    return Rational(n, BigInteger.ONE)
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
}