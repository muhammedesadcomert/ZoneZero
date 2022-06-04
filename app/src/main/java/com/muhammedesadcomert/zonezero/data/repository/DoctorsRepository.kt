package com.muhammedesadcomert.zonezero.data.repository

import com.muhammedesadcomert.zonezero.data.remote.DoctorsAPI
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class DoctorsRepository @Inject constructor(private val api: DoctorsAPI) {
    suspend fun getDoctors() = api.getDoctors()
}