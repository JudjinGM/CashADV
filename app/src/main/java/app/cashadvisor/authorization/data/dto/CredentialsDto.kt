package app.cashadvisor.authorization.data.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CredentialsDto(
    val accessToken: String,
    val refreshToken: String,
) : Parcelable
