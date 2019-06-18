package codingblocks.com.weatherlocate.data.network

import androidx.lifecycle.LiveData
import codingblocks.com.weatherlocate.data.network.response.CurrentWeatherResponse

interface WeatherNetworkDataSorce {

    val downloadedCurrentWeather : LiveData<CurrentWeatherResponse>

    suspend fun fetchCurrentWeather
                (
        location: String,
        languageCode: String


    )


}