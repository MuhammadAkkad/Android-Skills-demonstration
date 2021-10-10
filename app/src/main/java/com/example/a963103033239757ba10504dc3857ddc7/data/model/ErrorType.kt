package com.example.a963103033239757ba10504dc3857ddc7.data.model

/**
 * Created by Muhammad AKKAD on 10/10/21.
 */
enum class ErrorType(val errMsg: String) {
    REMAINING_TIME("Kalan zaman yetmiyor."),
    NO_STOCK_LEFT("Kalan uzay giysi sayısı yetmiyor."),
    DURABILITY("Kalan dayanıklılık süresi yetmiyor."),
    DISTANCE("Kalan mesafe yetmiyor."),
    SUCCESS("OKAY")

}