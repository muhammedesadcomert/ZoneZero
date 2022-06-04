package com.muhammedesadcomert.zonezero.ui.doctor

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.muhammedesadcomert.zonezero.R
import com.muhammedesadcomert.zonezero.databinding.FragmentDoctorListBinding
import com.muhammedesadcomert.zonezero.util.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DoctorListFragment : Fragment() {

    private val viewModel: DoctorListViewModel by viewModels()
    private lateinit var binding: FragmentDoctorListBinding
    private lateinit var doctorAdapter: DoctorAdapter
    private var isLoading = false
    private var query: String? = null
    private var gender: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentDoctorListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()
        getDoctors()
        setLineDivider()
        handleCheckBoxState()
        searchDoctor()
    }

    private fun initAdapter() {
        doctorAdapter = DoctorAdapter { doctor ->
            val action =
                DoctorListFragmentDirections.actionDoctorListFragmentToDoctorDetailFragment(doctor)
            findNavController().navigate(action)
        }

        binding.recyclerView.apply {
            adapter = doctorAdapter
            layoutManager = LinearLayoutManager(activity)
            setHasFixedSize(true)
        }
    }

    private fun getDoctors() {
        viewModel.doctors.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    doctorAdapter.differ.submitList(response.data?.doctors)
                }
                is Resource.Error -> {
                    hideProgressBar()
                    Log.e("DoctorListFragment", "Data can't loaded -> ${response.message}")
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        }
    }

    private fun searchDoctor() {
        var job: Job? = null

        binding.search.addTextChangedListener { editText ->
            job?.cancel()
            job = MainScope().launch {
                val searchText = editText.toString()
                query = searchText.ifEmpty {
                    null
                }
                filterDoctors()
            }
        }
    }

    private fun filterDoctors() {
        val results = viewModel.searchAndFilterDoctors(query, gender)
        doctorAdapter.differ.submitList(results)
        if (results!!.isEmpty()) {
            binding.userNotFound.visibility = View.VISIBLE
        } else {
            binding.userNotFound.visibility = View.GONE
        }
    }

    private fun setLineDivider() {
        val divider =
            DividerItemDecoration(binding.recyclerView.context, DividerItemDecoration.VERTICAL)
        divider.setDrawable(resources.getDrawable(R.drawable.layer, null))
        binding.recyclerView.addItemDecoration(divider)
    }

    private fun hideProgressBar() {
        binding.progressBar.visibility = View.GONE
        isLoading = false
    }

    private fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
        isLoading = true
    }

    private fun handleCheckBoxState() {
        val checkBoxFemale = binding.female
        val checkBoxMale = binding.male

        checkBoxFemale.setOnCheckedChangeListener { _, isCheckedFemale ->
            if (isCheckedFemale) {
                checkBoxMale.isChecked = false
                gender = "female"
            } else {
                gender = null
            }
            filterDoctors()
        }

        checkBoxMale.setOnCheckedChangeListener { _, isCheckedMale ->
            if (isCheckedMale) {
                checkBoxFemale.isChecked = false
                gender = "male"
            } else {
                gender = null
            }
            filterDoctors()
        }
    }
}