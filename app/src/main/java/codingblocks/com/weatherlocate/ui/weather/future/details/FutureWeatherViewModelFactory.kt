package codingblocks.com.weatherlocate.ui.weather.future.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import codingblocks.com.weatherlocate.data.provider.UnitProvider
import codingblocks.com.weatherlocate.data.repository.ForecastRepository
import codingblocks.com.weatherlocate.ui.weather.future.FutureListWeatherViewModel


class FutureWeatherViewModelFactory(
    private val forecastRepository: ForecastRepository,
private val unitProvider: UnitProvider

) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return FutureListWeatherViewModel(forecastRepository,unitProvider) as T
    }
}