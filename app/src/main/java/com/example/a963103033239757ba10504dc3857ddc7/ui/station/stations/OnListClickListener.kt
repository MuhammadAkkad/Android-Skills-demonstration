package com.example.a963103033239757ba10504dc3857ddc7.ui.station.stations

import com.example.a963103033239757ba10504dc3857ddc7.data.model.StationModel

/**
 * Created by Muhammad AKKAD on 10/5/21.
 */
interface OnListClickListener {
    fun next(position: Int)
    fun previous(position: Int)
    fun addToFav(station: StationModel)
    fun onTravelClick(station: StationModel)
}