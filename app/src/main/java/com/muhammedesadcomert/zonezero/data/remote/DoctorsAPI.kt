package com.muhammedesadcomert.zonezero.data.remote

import com.muhammedesadcomert.zonezero.data.model.Doctors
import retrofit2.Response
import retrofit2.http.GET

interface DoctorsAPI {
    @GET("android/doctors.json")
    suspend fun getDoctors(): Response<Doctors>
}