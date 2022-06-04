package com.muhammedesadcomert.zonezero.ui.doctor

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muhammedesadcomert.zonezero.data.model.Doctor
import com.muhammedesadcomert.zonezero.data.model.Doctors
import com.muhammedesadcomert.zonezero.data.repository.DoctorsRepository
import com.muhammedesadcomert.zonezero.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class DoctorListViewModel @Inject constructor(
    private val doctorsRepository: DoctorsRepository,
) : ViewModel() {

    val doctors: MutableLiveData<Resource<Doctors>> = MutableLiveData()
    private var doctorsResponse: Doctors? = null

    init {
        getDoctors()
    }

    private fun getDoctors() = viewModelScope.launch {
        doctors.postValue(Resource.Loading())
        val response = doctorsRepository.getDoctors()
        doctors.postValue(handleDoctorsResponse(response))
    }

    private fun handleDoctorsResponse(response: Response<Doctors>): Resource<Doctors> {
        if (response.isSuccessful) {
            response.body()?.let {
                if (doctorsResponse == null) {
                    doctorsResponse = it
                }
                return Resource.Success(it)
            }
        }
        return Resource.Error(response.message())
    }

    fun searchAndFilterDoctors(query: String? = null, gender: String? = null): List<Doctor>? {
        val currentDoctorList = doctors.value?.data?.doctors

        val results: List<Doctor>? = if (query.isNullOrEmpty() && gender == null) {
            currentDoctorList
        } else if (query.isNullOrEmpty() && gender != null) {
            currentDoctorList?.filter {
                it.gender == gender
            }
        } else if (!query.isNullOrEmpty() && gender == null) {
            currentDoctorList?.filter {
                it.fullName.contains(query, ignoreCase = true)
            }
        } else {
            currentDoctorList?.filter {
                it.fullName.contains(query!!, ignoreCase = true) && it.gender == gender
            }
        }
        return results
    }
}