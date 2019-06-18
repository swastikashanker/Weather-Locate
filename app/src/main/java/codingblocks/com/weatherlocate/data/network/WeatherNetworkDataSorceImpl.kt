package codingblocks.com.weatherlocate.data.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import codingblocks.com.weatherlocate.data.network.response.CurrentWeatherResponse
import codingblocks.com.weatherlocate.internal.NoConnectivityException

class WeatherNetworkDataSorceImpl (

    private val apixuWeatherApiService:ApixuWeatherApiService)
    : WeatherNetworkDataSorce {

    private val _downloadedCurrentWeather = MutableLiveData<CurrentWeatherResponse>()
    override val downloadedCurrentWeather: LiveData<CurrentWeatherResponse>
        get() = _downloadedCurrentWeather

    override suspend fun fetchCurrentWeather(location: String, languageCode: String) {
     try{

         val fetchedCurrentWeather = apixuWeatherApiService
             .getCurrentWeather(location, languageCode)
             .await()
         _downloadedCurrentWeather.postValue(fetchedCurrentWeather)
     }
     catch (e: NoConnectivityException) {
         Log.e("Connectivity", "No internet connection.", e)

     }
    }
}