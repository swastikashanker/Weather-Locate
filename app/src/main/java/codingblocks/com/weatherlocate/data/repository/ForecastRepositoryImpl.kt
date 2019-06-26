package codingblocks.com.weatherlocate.data.repository

import androidx.lifecycle.LiveData
import codingblocks.com.weatherlocate.data.db.CurrentDao
import codingblocks.com.weatherlocate.data.db.entity.Location
import codingblocks.com.weatherlocate.data.db.unitlocal.FutureWeatherDao
import codingblocks.com.weatherlocate.data.db.unitlocal.LocationDao
import codingblocks.com.weatherlocate.data.db.unitlocal.current.UnitSpecificCurrentWeatherEntry
import codingblocks.com.weatherlocate.data.db.unitlocal.future.UnitSpecificSimpleFutureWeatherEntry
import codingblocks.com.weatherlocate.data.network.WeatherNetworkDataSource
import codingblocks.com.weatherlocate.data.network.response.CurrentWeatherResponse
import codingblocks.com.weatherlocate.data.network.response.FutureWeatherResponse
import codingblocks.com.weatherlocate.data.provider.LocationProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.threeten.bp.LocalDate
import org.threeten.bp.ZonedDateTime
import java.util.*

class ForecastRepositoryImpl(
    private val currentWeatherDao: CurrentDao,
    private val futureWeatherDao: FutureWeatherDao,
    private val locationDao:LocationDao,
    private val weatherNetworkDataSource: WeatherNetworkDataSource,
    private val locationprovider:LocationProvider
    ):ForecastRepository
{



    init {
        weatherNetworkDataSource.apply {
            downloadedCurrentWeather.observeForever{newCurrentWeather ->
                persistFetchedCurrentWeather(newCurrentWeather)

            }


            downloadedFutureWeather.observeForever{newFutureWeather ->
                persistFetchedFutureWeather(newFutureWeather)

            }

        }
    }


    override suspend fun getFutureWeatherList(
        startDate: LocalDate,
        metric: Boolean
    ): LiveData<out List<UnitSpecificSimpleFutureWeatherEntry>> {
        return withContext(Dispatchers.IO) {
            initWeatherData()
            return@withContext if (metric) futureWeatherDao.getSimpleFutureWeatherMetric(startDate)
            else futureWeatherDao.getSimpleFutureWeatherImperial(startDate)

        }

    }

    override suspend fun getLocation(): LiveData<Location> {
        return withContext(Dispatchers.IO){
            initWeatherData()
            return@withContext locationDao.getLocation()
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

        val lastWeatherLocation=locationDao.getLocationNonLive()



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


    private fun persistFetchedFutureWeather(fetchedWeather: FutureWeatherResponse){



        fun deleteOldForecastData() {
            val today = LocalDate.now()
            futureWeatherDao.deleteOldEntries(today)
        }

        GlobalScope.launch(Dispatchers.IO) {
            deleteOldForecastData()
            val futureWeatherList = fetchedWeather.futureWeatherEntries.entries



            futureWeatherDao.insert(futureWeatherList)
            locationDao.insert(fetchedWeather.location)
        }

    }




    }






