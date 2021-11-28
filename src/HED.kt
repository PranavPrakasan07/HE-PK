import java.math.BigInteger
import java.util.*

/**
 * Homomorphic Encryption class
 */
object HED {
    // Store the keys, in the file
    private var FILENAME = "src/keyStorage"
    private var paillier: Paillier? = null
    private var fileOps: FileOps? = null
    private var r: BigInteger? = null

    @JvmStatic
    fun encryptHE(input: String?): String {
        val ena = input?.let { r?.let { it1 -> paillier!!.encryptString(it, it1) } }
        return ena.toString()
    }

    @JvmStatic
    fun decryptHE(input: String): String {
        return paillier!!.decryptString(BigInteger(input))
    }

    init {
        fileOps = FileOps()
        paillier = Paillier()

        // check if the file already exists
        if (fileOps!!.check(FILENAME)) {
            val temp = fileOps!!.getKey(FILENAME)
            paillier!!.keyGeneration(512, 62, temp[0], temp[1])
            r = temp[2]
        } else {

            // temp = [p, q, r]
            r = BigInteger(512, Random())
            paillier!!.keyGeneration(512, 62)

            // Returns [p, q]
            val temp: Array<BigInteger?> = paillier!!.pQ
            temp[2] = BigInteger("" + r)
            fileOps!!.fileWrite(temp, FILENAME)
        }
    }
}