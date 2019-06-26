package codingblocks.com.weatherlocate.data.network

import androidx.lifecycle.LiveData
import codingblocks.com.weatherlocate.data.network.response.CurrentWeatherResponse
import codingblocks.com.weatherlocate.data.network.response.FutureWeatherResponse

interface WeatherNetworkDataSource {

    val downloadedCurrentWeather : LiveData<CurrentWeatherResponse>
    val downloadedFutureWeather : LiveData<FutureWeatherResponse>


    suspend fun fetchCurrentWeather
                (
        location: String,
        languageCode: String


    )

    suspend fun fetchFutureWeather
                (
        location: String,
        languageCode: String


    )


}