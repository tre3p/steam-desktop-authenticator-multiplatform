package com.tre3p.sdamp.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MaFile(
    @SerialName("account_name")
    val accountName: String,
    @SerialName("shared_secret")
    val sharedSecret: String,
)
