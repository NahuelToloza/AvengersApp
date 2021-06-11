package com.toloza.avengersapp.service.security

import com.toloza.avengersapp.BuildConfig
import com.toloza.avengersapp.service.api.PUBLIC_KEY
import com.toloza.avengersapp.service.api.TIME_STAMP
import okhttp3.internal.and
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException


interface HashKeyBuilder {
    fun build(): String?
}

class AvengersHashKeyBuilder: HashKeyBuilder {
    override fun build(): String? {
        val timeStamp = TIME_STAMP
        val publicKey = PUBLIC_KEY
        val privateKey = BuildConfig.PRIVATE_KEY_AVENGERS_API

        val messageToHash = "$timeStamp$privateKey$publicKey"
        return getMd5(messageToHash)
    }
}

fun getMd5(input: String): String? {
    var md5Key: String? = null
    try {
        val md = MessageDigest.getInstance("MD5")
        val md5Bytes = md.digest(input.toByteArray())
        val md5 = java.lang.StringBuilder()
        for (i in md5Bytes.indices) {
            md5.append(Integer.toHexString(md5Bytes[i] and 0xFF or 0x100).substring(1, 3))
        }
        md5Key = md5.toString()
    } catch (e: NoSuchAlgorithmException) {
        e.printStackTrace()
    }
    return md5Key
}