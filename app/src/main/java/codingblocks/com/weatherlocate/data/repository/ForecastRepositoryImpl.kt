package codingblocks.com.weatherlocate.data.repository

import androidx.lifecycle.LiveData
import codingblocks.com.weatherlocate.data.db.CurrentDao
import codingblocks.com.weatherlocate.data.db.entity.Location
import codingblocks.com.weatherlocate.data.db.unitlocal.LocationDao
import codingblocks.com.weatherlocate.data.db.unitlocal.UnitSpecificCurrentWeatherEntry
import codingblocks.com.weatherlocate.data.network.WeatherNetworkDataSource
import codingblocks.com.weatherlocate.data.network.response.CurrentWeatherResponse
import codingblocks.com.weatherlocate.data.provider.LocationProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.threeten.bp.ZonedDateTime
import java.util.*

class ForecastRepositoryImpl(
    private val currentWeatherDao: CurrentDao,
    private val locationDao:LocationDao,
    private val weatherNetworkDataSource: WeatherNetworkDataSource,
    private val locationprovider:LocationProvider
    ):ForecastRepository
{
    override suspend fun getLocation(): LiveData<Location> {
        return withContext(Dispatchers.IO){
            initWeatherData()
            return@withContext locationDao.getLocation()
        }
    }


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

        val lastWeatherLocation=locationDao.getLocation().value


        if(lastWeatherLocation==null|| locationprovider.hasLocationChanged(lastWeatherLocation)){
            fetchcurrent()
            return
        }

            if (isFetchneeded(lastWeatherLocation.zonedDateTime))
                fetchcurrent()
            return




    }

    private fun isFetchneeded(lastFetchTime:ZonedDateTime):Boolean{
        val thirtymins =ZonedDateTime.now().minusMinutes(30)
        return lastFetchTime.isBefore(thirtymins)
    }

    private suspend fun fetchcurrent(){
        weatherNetworkDataSource.fetchCurrentWeather(locationprovider.getPreferredLocationString(),
            Locale.getDefault().language
        )
    }

    private fun persistFetchedCurrentWeather(fetchedWeather: CurrentWeatherResponse?) {

        GlobalScope.launch(Dispatchers.IO) {
            currentWeatherDao.insert(fetchedWeather!!.current)
            locationDao.insert(fetchedWeather.location)



        }


    }




    }






