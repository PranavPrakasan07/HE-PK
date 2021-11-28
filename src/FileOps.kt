import java.io.*
import java.math.BigInteger
import java.util.Objects
import java.lang.Exception
import java.nio.charset.StandardCharsets

class FileOps {
    fun check(F: String): Boolean {
        var res = false
        val f = File(F)
        res = f.exists()
        return res
    }

    fun fileWrite(temp: Array<BigInteger?>, FILENAME: String) {
        var f: File? = null
        var bool = false
        try {
            f = File(FILENAME)
            bool = f.createNewFile()
            println("File created: $bool")
            val writer = PrintWriter(FILENAME, StandardCharsets.UTF_8)
            writer.write(temp[0].toString())
            writer.write(Objects.requireNonNull(System.getProperty("line.separator")))
            writer.write(temp[1].toString())
            writer.write(Objects.requireNonNull(System.getProperty("line.separator")))
            writer.write(temp[2].toString())
            writer.write(Objects.requireNonNull(System.getProperty("line.separator")))
            writer.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun getKey(FILENAME: String): Array<BigInteger?> {

        var br: BufferedReader? = null
        var fr: FileReader? = null
        var sCurrentLine: String
        val temp = arrayOfNulls<BigInteger>(3)
        try {
            fr = FileReader(FILENAME)
            br = BufferedReader(fr)
            var i = 0
            fr.readLines().forEach {
                temp[i] = BigInteger(it)
                i++
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            try {
                br?.close()
                fr?.close()
            } catch (ex: IOException) {
                ex.printStackTrace()
            }
        }
        return temp
    }
}