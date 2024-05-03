package com.tre3p.sdamp.steam

import com.tre3p.sdamp.model.MaFile
import java.util.*

fun MaFile.getTwoFactor(): String {
    val alignedTime = SteamTime.getTimeAlignedWithSteamServer()
    val sharedSecretDecoded = Base64.getDecoder().decode(this.sharedSecret)

    return ""
}