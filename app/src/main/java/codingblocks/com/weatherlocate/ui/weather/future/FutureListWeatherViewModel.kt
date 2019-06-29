package codingblocks.com.weatherlocate.ui.weather.future


import codingblocks.com.weatherlocate.data.provider.UnitProvider
import codingblocks.com.weatherlocate.data.repository.ForecastRepository
import codingblocks.com.weatherlocate.internal.lazyDef
import codingblocks.com.weatherlocate.ui.base.WeatherViewModel
import org.threeten.bp.LocalDate

class FutureListWeatherViewModel(
    private val forecastRepository: ForecastRepository,
    unitProvider: UnitProvider
) : WeatherViewModel(forecastRepository,unitProvider) {

    val weather by lazyDef{
        forecastRepository.getFutureWeatherList(LocalDate.now(),super.isMetricUnit)
    }


}
