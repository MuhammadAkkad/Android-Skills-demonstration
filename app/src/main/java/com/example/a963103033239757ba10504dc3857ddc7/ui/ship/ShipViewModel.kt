package com.example.a963103033239757ba10504dc3857ddc7.ui.ship

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ShipViewModel : ViewModel() {

    var firstSliderValue = 1
    var secondSliderValue = 1 // default initial values
    var thirdSliderValue = 1
    var sum = MutableLiveData(3)

    fun listenToValue(sliderNumber: Int, value: Int) {
        when (sliderNumber) {
            1 -> firstSliderValue = value
            2 -> secondSliderValue = value
            3 -> thirdSliderValue = value
        }
        sum.value = firstSliderValue + secondSliderValue + thirdSliderValue
    }
}