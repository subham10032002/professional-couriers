package com.tpcindia.professionalcouriersapp.data.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.nio.charset.StandardCharsets
import java.security.spec.KeySpec
import java.util.Base64
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.DESedeKeySpec


class EncryptionUtils {
    private var cipher: Cipher? = null
    private var key: SecretKey? = null

    init {
        try {
            val encryptionKey = "ThisIsSpartaThisIsSparta"
            val encryptionScheme = DESEDE_ENCRYPTION_SCHEME

            val arrayBytes = encryptionKey.toByteArray(charset(UNICODE_FORMAT))
            val keySpec: KeySpec = DESedeKeySpec(arrayBytes)

            val secretKeyFactory = SecretKeyFactory.getInstance(encryptionScheme)
            cipher = Cipher.getInstance(encryptionScheme)
            key = secretKeyFactory.generateSecret(keySpec)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun encrypt(unencryptedString: String): String? {
        var encryptedString: String? = null
        try {
            cipher!!.init(Cipher.ENCRYPT_MODE, key)
            val plainText = unencryptedString.toByteArray(charset(UNICODE_FORMAT))
            val encryptedText = cipher!!.doFinal(plainText)
            encryptedString = Base64.getEncoder().encodeToString(encryptedText)
        } catch (e: Exception) {
            encryptedString = unencryptedString
            e.printStackTrace()
        }
        return encryptedString
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun decrypt(encryptedString: String?): String? {
        var decryptedText: String? = null
        try {
            cipher!!.init(Cipher.DECRYPT_MODE, key)
            val encryptedText = Base64.getDecoder().decode(encryptedString)
            val plainText = cipher!!.doFinal(encryptedText)
            decryptedText = String(plainText, StandardCharsets.UTF_8)
        } catch (e: Exception) {
            decryptedText = encryptedString
            e.printStackTrace()
        }
        return decryptedText
    }

    companion object {
        private const val UNICODE_FORMAT = "UTF8"
        const val DESEDE_ENCRYPTION_SCHEME: String = "DESede"
    }
}

