import java.lang.Exception
import java.nio.charset.StandardCharsets
import java.security.MessageDigest
import javax.crypto.spec.SecretKeySpec
import java.security.NoSuchAlgorithmException
import java.util.*
import javax.crypto.Cipher

class AESfor {
    private val secret = "minor2"
    private fun setKey(myKey: String) {
        val sha: MessageDigest
        try {
            key = myKey.toByteArray(StandardCharsets.UTF_8)
            sha = MessageDigest.getInstance("SHA-1")
            key = sha.digest(key)
            key = key.copyOf(16)
            secretKey = SecretKeySpec(key, "AES")
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }
    }

    fun encrypt(strToEncrypt: String): String? {
        try {
            setKey(secret)
            val cipher = Cipher.getInstance("AES/PKCS5Padding")
            cipher.init(Cipher.ENCRYPT_MODE, secretKey)
            return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.toByteArray(StandardCharsets.UTF_8)))
        } catch (e: Exception) {
            println("Error while encrypting:$e")
        }
        return null
    }

    fun decrypt(strToDecrypt: String?): String? {
        try {
            setKey(secret)
            val cipher = Cipher.getInstance("AES/PKCS5PADDING")
            cipher.init(Cipher.DECRYPT_MODE, secretKey)
            return String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)))
        } catch (e: Exception) {
            println("Error while decrypting:$e")
        }
        return null
    }

    companion object {
        private var secretKey: SecretKeySpec? = null
        private lateinit var key: ByteArray
    }
}