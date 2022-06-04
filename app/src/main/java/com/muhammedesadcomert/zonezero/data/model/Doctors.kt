package com.muhammedesadcomert.zonezero.data.model

import com.google.gson.annotations.SerializedName

data class Doctors(
    @SerializedName("doctors")
    val doctors: List<Doctor>,
)