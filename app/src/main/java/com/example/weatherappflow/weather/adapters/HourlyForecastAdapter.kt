package com.example.weatherappflow.weather.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherappflow.R
import com.example.weatherappflow.utils.extensions.setImageUrl
import com.example.weatherappflow.utils.extensions.toCelsiusString
import com.example.weatherappflow.utils.extensions.toHoursString
import com.example.weatherappflow.utils.extensions.toImageUrl
import com.example.weatherappflow.weather.data.domain.HourlyForecast
import kotlinx.android.synthetic.main.item_hourly_weather.view.*

class HourlyForecastAdapter :
    RecyclerView.Adapter<HourlyForecastAdapter.HourlyForecastViewHolder>() {

    var hourlyForecast: List<HourlyForecast> = mutableListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourlyForecastViewHolder =
        HourlyForecastViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_hourly_weather, parent, false)
        )


    override fun onBindViewHolder(holder: HourlyForecastViewHolder, position: Int) {
        holder.hourlyForecast = hourlyForecast[position]
    }

    override fun getItemCount(): Int = hourlyForecast.size


    inner class HourlyForecastViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var hourlyForecast: HourlyForecast? = null
            set(value) {
                field = value
                if (value != null) {
                    itemView.hour.text = value.dt.toHoursString()
                    itemView.hourlyIcon.setImageUrl(value.icon.toImageUrl())
                    itemView.hourlyTemp.text = value.temperature.toCelsiusString()
                }
            }
    }
}
