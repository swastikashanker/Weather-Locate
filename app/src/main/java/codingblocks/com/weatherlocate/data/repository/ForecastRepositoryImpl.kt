package codingblocks.com.weatherlocate.data.repository

import androidx.lifecycle.LiveData
import codingblocks.com.weatherlocate.data.db.CurrentDao
import codingblocks.com.weatherlocate.data.db.unitlocal.UnitSpecificCurrentWeatherEntry
import codingblocks.com.weatherlocate.data.network.WeatherNetworkDataSource
import codingblocks.com.weatherlocate.data.network.response.CurrentWeatherResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.threeten.bp.ZonedDateTime
import java.util.*

class ForecastRepositoryImpl(
    private val currentWeatherDao: CurrentDao,
    private val weatherNetworkDataSource: WeatherNetworkDataSource
    ):ForecastRepository
{



    init {
        weatherNetworkDataSource.downloadedCurrentWeather.observeForever { newCurrentWeather ->
            persistFetchedCurrentWeather(newCurrentWeather)
        }
    }



            override suspend fun getCurrentWeather(metric: Boolean): LiveData<out UnitSpecificCurrentWeatherEntry> {

                return withContext(Dispatchers.IO){

                    initWeatherData()

                    return@withContext  if(metric) currentWeatherDao.getWeatherMetric()
                    else currentWeatherDao.getWeatherImperial()
                }


        }




    private suspend fun initWeatherData(){

        if(isFetchneeded(ZonedDateTime.now().minusHours(1)))
            fetchcurrent()



    }

    private fun isFetchneeded(lastFetchTime:ZonedDateTime):Boolean{
        val thirtymins =ZonedDateTime.now().minusMinutes(30)
        return lastFetchTime.isBefore(thirtymins)
    }

    private suspend fun fetchcurrent(){
        weatherNetworkDataSource.fetchCurrentWeather(
            "Patna",
            Locale.getDefault().language
        )
    }

    private fun persistFetchedCurrentWeather(fetchedWeather: CurrentWeatherResponse?) {

        GlobalScope.launch(Dispatchers.IO) {
            currentWeatherDao.insert(fetchedWeather!!.current)




        }


    }




    }






