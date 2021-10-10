package com.example.a963103033239757ba10504dc3857ddc7.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.a963103033239757ba10504dc3857ddc7.data.db.AppDatabase

/**
 * Created by Muhammad AKKAD on 10/8/21.
 */
class ViewModelFactory(private val database: AppDatabase) : ViewModelProvider.Factory {


    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
            return FavoriteViewModel(database) as T
        }
        if (modelClass.isAssignableFrom(StationsViewModel::class.java)) {
            return StationsViewModel(database) as T
        }

        if (modelClass.isAssignableFrom(ShipViewModel::class.java)) {
            return ShipViewModel(database) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}