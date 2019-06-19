package codingblocks.com.weatherlocate.ui.weather.current

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import codingblocks.com.weatherlocate.data.repository.ForecastRepository



class CurrentWeatherViewModelFactory(
    private val forecastRepository: ForecastRepository

) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CurrentWeatherViewModel(forecastRepository) as T
    }
}