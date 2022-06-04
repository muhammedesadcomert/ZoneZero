package com.muhammedesadcomert.zonezero.ui.doctor

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.muhammedesadcomert.zonezero.R
import com.muhammedesadcomert.zonezero.databinding.FragmentDoctorDetailBinding
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DoctorDetailFragment : Fragment() {

    private lateinit var binding: FragmentDoctorDetailBinding
    private val args: DoctorDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentDoctorDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupDoctorDetails()

        binding.appointmentButton.setOnClickListener {
            val action =
                DoctorDetailFragmentDirections.actionDoctorDetailFragmentToAppointmentFragment(args.doctor.userStatus)
            findNavController().navigate(action)
        }
    }

    private fun setupDoctorDetails() {
        binding.doctorName.text = args.doctor.fullName
        Picasso.get().load(args.doctor.image.url).into(binding.doctorImage)
        premiumOrNot()
    }

    private fun premiumOrNot() {
        if (args.doctor.userStatus == "free") {
            binding.premium.visibility = View.GONE
            binding.appointmentText.text = getString(R.string.buy_premium)
        } else {
            binding.appointmentText.text = getString(R.string.make_an_appointment)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        findNavController().navigateUp()
        return super.onOptionsItemSelected(item)
    }
}