package com.muhammedesadcomert.zonezero.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Doctor(
    @SerializedName("full_name")
    val fullName: String,
    @SerializedName("user_status")
    val userStatus: String,
    @SerializedName("gender")
    val gender: String,
    @SerializedName("image")
    val image: Image,
) : Serializable