package com.example.a963103033239757ba10504dc3857ddc7.util

import com.example.a963103033239757ba10504dc3857ddc7.data.model.Point
import kotlin.math.pow
import kotlin.math.sqrt

/**
 * Created by Muhammad AKKAD on 10/9/21.
 */
object TravelHelper {

    fun distanceCalculater(p1: Point, p2: Point): Int { // for EUS
        return sqrt((p1.x - p2.x).pow(2) + (p1.y - p2.y).pow(2)).toInt()
    }

}