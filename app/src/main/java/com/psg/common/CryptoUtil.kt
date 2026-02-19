package com.psg.common

import android.util.Base64
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

object CryptoUtil {
    private const val SECRET_KEY = "1234567890123456"
    private const val TRANSFORMATION = "AES/ECB/PKCS5Padding"

    fun encrypt(text: String): String {
        val cipher = Cipher.getInstance(TRANSFORMATION)
        val key = SecretKeySpec(SECRET_KEY.toByteArray(), "AES")
        cipher.init(Cipher.ENCRYPT_MODE, key)
        return Base64.encodeToString(cipher.doFinal(text.toByteArray()), Base64.DEFAULT)
    }

    fun decrypt(text: String): String {
        return try {
            val cipher = Cipher.getInstance(TRANSFORMATION)
            val key = SecretKeySpec(SECRET_KEY.toByteArray(), "AES")
            cipher.init(Cipher.DECRYPT_MODE, key)
            String(cipher.doFinal(Base64.decode(text, Base64.DEFAULT)))
        } catch (e: Exception) {
            "Decryption Failed"
        }
    }
}
