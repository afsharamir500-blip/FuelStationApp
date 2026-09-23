package com.example.fuelstation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fuelstation.databinding.ItemStationBinding
import com.example.fuelstation.model.Station

class StationAdapter(
    private val stations: List<Station>,
    private val onPayClick: (Station) -> Unit
) : RecyclerView.Adapter<StationAdapter.StationViewHolder>() {

    inner class StationViewHolder(val binding: ItemStationBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StationViewHolder {
        val binding = ItemStationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StationViewHolder, position: Int) {
        val station = stations[position]
        holder.binding.tvStationNumber.text = "شماره جایگاه: ${station.stationNumber}"
        holder.binding.tvStationName.text = station.name
        holder.binding.tvStationAddress.text = station.address
        holder.binding.tvPrice.text = "قیمت هر لیتر: ${"%,d".format(station.pricePerLiterToman)} تومان (${station.fuelType})"

        holder.binding.btnPay.setOnClickListener { onPayClick(station) }
    }

    override fun getItemCount(): Int = stations.size
}
