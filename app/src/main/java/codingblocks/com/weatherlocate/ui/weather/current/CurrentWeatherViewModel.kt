package codingblocks.com.weatherlocate.ui.weather.current

import androidx.lifecycle.ViewModel;
import codingblocks.com.weatherlocate.data.provider.UnitProvider
import codingblocks.com.weatherlocate.data.repository.ForecastRepository
import codingblocks.com.weatherlocate.internal.Unit
import codingblocks.com.weatherlocate.internal.lazyDef

class CurrentWeatherViewModel(private val forecastRepository: ForecastRepository,

  unitProvider: UnitProvider) : ViewModel() {

    private val unitsystem =unitProvider.getUnitSystem()



    val isMetric : Boolean
    get() = unitsystem ==Unit.METRIC

    val weather by lazyDef {

        forecastRepository.getCurrentWeather(isMetric)

    }

  val location by lazyDef{
    forecastRepository.getLocation()
  }


}
