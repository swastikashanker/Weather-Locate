package codingblocks.com.weatherlocate.ui.weather.current

import androidx.lifecycle.ViewModel;
import codingblocks.com.weatherlocate.data.provider.UnitProvider
import codingblocks.com.weatherlocate.data.repository.ForecastRepository
import codingblocks.com.weatherlocate.internal.Unit
import codingblocks.com.weatherlocate.internal.lazyDef
import codingblocks.com.weatherlocate.ui.base.WeatherViewModel

class CurrentWeatherViewModel(private val forecastRepository: ForecastRepository,

  unitProvider: UnitProvider) : WeatherViewModel(forecastRepository,unitProvider) {


    val weather by lazyDef {

        forecastRepository.getCurrentWeather(super.isMetricUnit)

    }




}
