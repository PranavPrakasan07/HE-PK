import HED.encryptHE
import HED.decryptHE
import kotlin.jvm.JvmStatic

internal object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        val enteredText = "Trial is this okay"

        val encryptedText = encryptHE(enteredText)
        println("ENA-OBJ:$encryptedText")
        println("DEC-OBJ:" + decryptHE(encryptedText))
    }
}
