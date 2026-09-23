package com.example.fuelstation.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fuelstation.adapter.StationAdapter
import com.example.fuelstation.databinding.ActivityMainBinding
import com.example.fuelstation.model.Station
import com.example.fuelstation.model.StationRepository

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val stations = StationRepository.getStations()

        binding.rvStations.layoutManager = LinearLayoutManager(this)
        binding.rvStations.adapter = StationAdapter(stations) { station ->
            openPayment(station)
        }

        binding.btnFuelCard.setOnClickListener {
            startActivity(Intent(this, FuelCardActivity::class.java))
        }
    }

    private fun openPayment(station: Station) {
        val intent = Intent(this, PaymentActivity::class.java)
        intent.putExtra("station_number", station.stationNumber)
        intent.putExtra("station_name", station.name)
        intent.putExtra("price_per_liter", station.pricePerLiterToman)
        startActivity(intent)
    }
}
