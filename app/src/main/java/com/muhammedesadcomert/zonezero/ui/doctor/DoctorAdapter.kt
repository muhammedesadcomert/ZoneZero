package com.muhammedesadcomert.zonezero.ui.doctor

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.muhammedesadcomert.zonezero.data.model.Doctor
import com.muhammedesadcomert.zonezero.databinding.DoctorItemBinding
import com.squareup.picasso.Picasso

class DoctorAdapter(private val onItemClicked: (Doctor) -> Unit) :
    ListAdapter<Doctor, DoctorAdapter.DoctorViewHolder>(DIFF_CALLBACK) {

    inner class DoctorViewHolder(private val binding: DoctorItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(doctor: Doctor) {
            binding.apply {
                doctorName.text = doctor.fullName
                Picasso.get().load(doctor.image.url).into(binding.doctorImage)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): DoctorAdapter.DoctorViewHolder {
        return DoctorViewHolder(
            DoctorItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: DoctorAdapter.DoctorViewHolder, position: Int) {
        val doctor = differ.currentList[position]
        holder.bind(doctor)
        holder.itemView.setOnClickListener {
            onItemClicked(doctor)
        }
    }

    val differ = AsyncListDiffer(this, DIFF_CALLBACK)

    override fun getItemCount() = differ.currentList.size

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Doctor>() {
            override fun areItemsTheSame(oldItem: Doctor, newItem: Doctor): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Doctor, newItem: Doctor): Boolean {
                return oldItem == newItem
            }
        }
    }
}