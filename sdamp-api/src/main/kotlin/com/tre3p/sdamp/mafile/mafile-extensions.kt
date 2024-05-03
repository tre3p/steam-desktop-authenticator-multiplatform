package com.tre3p.sdamp.mafile

import com.tre3p.sdamp.model.MaFile
import com.tre3p.sdamp.steam.SteamTime
import java.nio.ByteBuffer
import java.util.*
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec
import kotlin.experimental.and

private const val CHARS = "23456789BCDFGHJKMNPQRTVWXY"
private const val HMAC_SHA1_ALGO = "HmacSHA1"

fun MaFile.getTwoFactor(): String {
    val alignedTime = SteamTime.getTimeAlignedWithSteamServer()
    val sharedSecretDecoded = Base64.getDecoder().decode(this.sharedSecret)

    val buffer = ByteBuffer.allocate(8)
    buffer.putInt(4, (alignedTime / 30).toInt())

    val hmac = Mac.getInstance(HMAC_SHA1_ALGO)
    val keySpec = SecretKeySpec(sharedSecretDecoded, HMAC_SHA1_ALGO)
    hmac.init(keySpec)
    hmac.update(buffer.array())

    val sum = hmac.doFinal()
    val start = (sum[19] and 0x0F).toInt()
    var slice = ByteBuffer.wrap(sum, start, 4).int and 0x7FFFFFFF

    val buf = CharArray(5)
    for (i in 0 until 5) {
        buf[i] = CHARS[slice % CHARS.length]
        slice /= CHARS.length
    }

    return String(buf).reversed()
}