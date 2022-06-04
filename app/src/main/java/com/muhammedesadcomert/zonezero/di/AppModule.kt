package com.muhammedesadcomert.zonezero.di

import com.muhammedesadcomert.zonezero.data.remote.DoctorsAPI
import com.muhammedesadcomert.zonezero.data.repository.DoctorsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private const val BASE_URL = "https://www.mobillium.com/"

    @Singleton
    @Provides
    fun provideDoctorsRepository(api: DoctorsAPI) = DoctorsRepository(api)

    @Singleton
    @Provides
    fun provideDoctorAPI(): DoctorsAPI {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(DoctorsAPI::class.java)
    }
}