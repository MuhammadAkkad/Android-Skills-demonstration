package com.example.a963103033239757ba10504dc3857ddc7.ui.listeners

import com.example.a963103033239757ba10504dc3857ddc7.data.model.StationModel

/**
 * Created by Muhammad AKKAD on 10/5/21.
 */
interface OnViewClickListener {
    fun next(position: Int)
    fun previous(position: Int)
    fun addToFav(station: StationModel)
    fun onTravelClick(station: StationModel)
}