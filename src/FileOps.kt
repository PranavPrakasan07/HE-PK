import java.io.File
import java.math.BigInteger
import java.io.PrintWriter
import java.util.Objects
import java.io.BufferedReader
import java.io.FileReader
import java.io.IOException
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
            br = BufferedReader(FileReader(FILENAME))
            var i = 0
            while (br.readLine().also { sCurrentLine = it } != null) {
                temp[i] = BigInteger(sCurrentLine)
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