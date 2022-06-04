package com.muhammedesadcomert.zonezero.ui.appointment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.navArgs
import com.muhammedesadcomert.zonezero.R
import com.muhammedesadcomert.zonezero.databinding.FragmentAppointmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AppointmentFragment : Fragment() {

    private lateinit var binding: FragmentAppointmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentAppointmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args: AppointmentFragmentArgs by navArgs()
        val title: String

        if (args.premiumOrFree == "premium") {
            binding.appointmentScreen.text = getString(R.string.appointment_screen)
            title = getString(R.string.make_an_appointment)
        } else {
            binding.appointmentScreen.text = getString(R.string.payment_screen)
            title = getString(R.string.buy_premium)
        }

        setupActionBarTitle(title)
    }

    private fun setupActionBarTitle(title: String) {
        (activity as AppCompatActivity).supportActionBar?.title = title
    }
}