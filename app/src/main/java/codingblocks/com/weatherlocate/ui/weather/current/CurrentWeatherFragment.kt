package codingblocks.com.weatherlocate.ui.weather.current

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel

import codingblocks.com.weatherlocate.R
import codingblocks.com.weatherlocate.data.network.ApixuWeatherApiService
import codingblocks.com.weatherlocate.data.network.ConnectivityInterceptorImpl
import codingblocks.com.weatherlocate.data.network.WeatherNetworkDataSourceImpl
import codingblocks.com.weatherlocate.internal.glide.GlideApp
import codingblocks.com.weatherlocate.ui.base.ScopedFragment
import kotlinx.android.synthetic.main.current_weather_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class CurrentWeatherFragment : ScopedFragment(),KodeinAware {
    override val kodein by closestKodein()
       private val viewModelFactory  : CurrentWeatherViewModelFactory by instance()


    companion object {
        fun newInstance() = CurrentWeatherFragment()
    }

    private lateinit var viewModel: CurrentWeatherViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.current_weather_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this,viewModelFactory)
            .get(CurrentWeatherViewModel::class.java)
        bindUI()

//
//        val apiService = ApixuWeatherApiService(ConnectivityInterceptorImpl(this.context!!))
//
//        val weatherNetworkDataSorce =WeatherNetworkDataSourceImpl(apiService)
//        weatherNetworkDataSorce.downloadedCurrentWeather.observe(this, Observer {
//            tvCurrent.text= it.toString()
//        })
//
//        GlobalScope.launch ( Dispatchers.Main ){
//
//            weatherNetworkDataSorce.fetchCurrentWeather("Patna","en")
//
//
//        }



    }

    private fun bindUI()=launch {
        val currentWeather = viewModel.weather.await()
        currentWeather.observe(this@CurrentWeatherFragment, Observer {

            if(it == null) return@Observer

            group_loading.visibility = View.GONE
            updateLocation("Patna")
            updateDate()
            updateTemperature(it.temperature,it.feelsLikeTemperature)
            updateCondition(it.conditionText)
            updatePrecipitation(it.precipitationVolume)
            updateWind(it.windDirection,it.windSpeed)

            GlideApp.with(this@CurrentWeatherFragment)
                .load("https:${it.conditionIconUrl}")
                .into(ivCondition)

        })
    }

    private fun updateLocation(location:String){
        //update loc

        (activity as? AppCompatActivity)?.supportActionBar?.title = location
    }

    private fun updateDate(){
        (activity as? AppCompatActivity)?.supportActionBar?.subtitle = "Today"

    }


    private fun updateCondition(condition:String){
        tvConditionText.text = "$condition"
    }
    private fun updateTemperature(temperature:Double,tempFeelsLike:Double){
        val unit=if(viewModel.isMetric)"°C" else "°F"
        tvTemperature.text="$temperature $unit"
        tvFeelsLike.text ="$tempFeelsLike $unit"

    }




    private fun updatePrecipitation(precipitation : Double){
        val unit=if(viewModel.isMetric)"mm" else "in"
        tvPrecipitation.text="$precipitation $unit"

    }

    private fun updateWind(windDirection:String,windSpeed:Double){
        val unit=if(viewModel.isMetric)"kph" else "mph"
        tvWind.text="$windDirection,$windSpeed $unit"

    }


}
