package id.ac.ui.cs.mobileprogramming.muhammadferilbagusperkasa.cuacajabodetabek.api

class ApiCuacaEntity {
    data class ApiCuacaBody(
            val cnt: Int,
            val list: List<ApiCuacaList>
    )

    data class ApiCuacaList(
        val coord: Coord,
        val sys: Sys,
        val weather: List<Weather>,
        val main: Main,
        val visibility: Long,
        val wind: Wind,
        val clouds: Clouds,
        val dt: Long,
        val id: Long,
        val name: String
    )

    data class Coord(
        val lon: Double,
        val lat: Double
    )

    data class Sys(
        val country: String,
        val timezone: Long,
        val sunrise: Long,
        val sunset: Long
    )

    data class Weather(
        val id: Long,
        val main: String,
        val description: String,
        val icon: String
    )

    data class Main(
        val temp: Double,
        val feel_like: Double,
        val temp_min: Double,
        val temp_max: Double,
        val pressure: Long,
        val humidity: Long
    )

    data class Wind(
        val speed: Double,
        val deg: Long
    )

    data class Clouds(
        val all: Long
    )
}