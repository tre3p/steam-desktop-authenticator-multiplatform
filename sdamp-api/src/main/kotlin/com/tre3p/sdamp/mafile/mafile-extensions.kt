package com.tre3p.sdamp.mafile

import com.tre3p.sdamp.model.MaFile
import com.tre3p.sdamp.steam.SteamTime
import java.util.*

fun MaFile.getTwoFactor(): String {
    val alignedTime = SteamTime.getTimeAlignedWithSteamServer()
    val sharedSecretDecoded = Base64.getDecoder().decode(this.sharedSecret)

    return ""
}