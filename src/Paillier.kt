import java.math.BigInteger
import java.lang.StringBuilder
import java.util.*

class Paillier {
    private var p: BigInteger? = null
    private var q: BigInteger? = null
    private var lambda: BigInteger? = null
    private var n: BigInteger? = null
    private var nsquare: BigInteger? = null
    private var g: BigInteger? = null
    private var bitLength = 0

    constructor(bitLengthVal: Int, certainty: Int) {
        keyGeneration(bitLengthVal, certainty)
    }

    constructor() {}

    val pQ: Array<BigInteger?>
        get() {
            val res = arrayOfNulls<BigInteger>(4)
            res[0] = BigInteger("" + p)
            res[1] = BigInteger("" + q)
            return res
        }

    fun keyGeneration(bitLengthVal: Int, certainty: Int) {
        bitLength = bitLengthVal
        p = BigInteger(bitLength / 2, certainty, Random())
        q = BigInteger(bitLength / 2, certainty, Random())
        n = p!!.multiply(q)
        nsquare = n?.multiply(n)
        g = BigInteger("2")
        lambda = p!!.subtract(BigInteger.ONE).multiply(q!!.subtract(BigInteger.ONE)).divide(
                p!!.subtract(BigInteger.ONE).gcd(q!!.subtract(BigInteger.ONE)))
        if (g!!.modPow(lambda, nsquare).subtract(BigInteger.ONE).divide(n).gcd(n).toInt() != 1) {
            println("Message" + "g is not good. Choose g again.")
        }
    }

    fun keyGeneration(bitLengthVal: Int, certainty: Int, a: BigInteger?, b: BigInteger?) {
        bitLength = bitLengthVal
        p = a
        q = b

        // n = p*q
        n = p!!.multiply(q)

        //n^2 = n*n
        nsquare = n?.multiply(n)

        // Choose random 'g'
        g = BigInteger("2")

        // lambda = lcm(p-1, q-1)
        // lambda = p-1 * q-1 / gcd(p-1, q-1)
        lambda = p!!.subtract(BigInteger.ONE).multiply(q!!.subtract(BigInteger.ONE)).divide(
                p!!.subtract(BigInteger.ONE).gcd(q!!.subtract(BigInteger.ONE)))

        // If not right, choose another
        if (g!!.modPow(lambda, nsquare).subtract(BigInteger.ONE).divide(n).gcd(n).toInt() != 1) {
            println("Message" + "g is not good. Choose g again.")
        }
    }

    // Random r, 0 < r < n
    private fun encryption(m: BigInteger?, r: BigInteger): BigInteger {

        // ciphertext c = ((g^m) * (r^n)) mod (n*n)
        return g!!.modPow(m, nsquare).multiply(r.modPow(n, nsquare)).mod(nsquare)
    }

    private fun decryption(c: BigInteger): BigInteger {
        val u = g!!.modPow(lambda, nsquare).subtract(BigInteger.ONE).divide(n).modInverse(n)

        // plaintext m = (L * ((c^lambda) mod (n*n)) * mu mod n
        return c.modPow(lambda, nsquare).subtract(BigInteger.ONE).divide(n).multiply(u).mod(n)
    }

    fun encryptString(st: String, r: BigInteger): BigInteger {
        var temp = st[0].code
        println(temp)
        var num = BigInteger(temp.toString())
        for (i in 1 until st.length) {
            temp = st[i].code
            println(temp)
            num = num.multiply(BigInteger.valueOf(1000)).add(BigInteger.valueOf(temp.toLong()))
            println("num:$num")
        }
        return encryption(num, r)
    }

    fun decryptString(num: BigInteger): String {
        val num1 = decryption(num)
        println("SecondBig:$num1")
        val strc = num1.toString().length
        println("strc length:$strc")
        var m = num1.toString()
        println("m string$m")
        if (strc % 3 != 0) {
            m = "0$m"
        }
        val strd = StringBuilder()
        var i = 0
        while (i < m.length) {
            strd.append(m.substring(i, i + 3).toInt().toChar())
            println("Process:$strd")
            i += 3
        }
        return strd.toString()
    }
}