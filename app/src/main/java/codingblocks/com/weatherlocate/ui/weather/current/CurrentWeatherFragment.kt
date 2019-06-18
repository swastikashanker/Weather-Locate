package codingblocks.com.weatherlocate.ui.weather.current

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer

import codingblocks.com.weatherlocate.R
import codingblocks.com.weatherlocate.data.network.ApixuWeatherApiService
import codingblocks.com.weatherlocate.data.network.ConnectivityInterceptorImpl
import codingblocks.com.weatherlocate.data.network.WeatherNetworkDataSorceImpl
import kotlinx.android.synthetic.main.current_weather_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CurrentWeatherFragment : Fragment() {

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
        viewModel = ViewModelProviders.of(this).get(CurrentWeatherViewModel::class.java)
        // TODO: Use the ViewModel

        val apiService = ApixuWeatherApiService(ConnectivityInterceptorImpl(this.context!!))

        val weatherNetworkDataSorce =WeatherNetworkDataSorceImpl(apiService)
        weatherNetworkDataSorce.downloadedCurrentWeather.observe(this, Observer {
            tvCurrent.text= it.toString()
        })

        GlobalScope.launch ( Dispatchers.Main ){

            weatherNetworkDataSorce.fetchCurrentWeather("Patna","en")


        }
    }

}
