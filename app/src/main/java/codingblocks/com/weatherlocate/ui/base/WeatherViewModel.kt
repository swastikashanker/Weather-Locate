package codingblocks.com.weatherlocate.ui.base

import androidx.lifecycle.ViewModel
import codingblocks.com.weatherlocate.data.provider.UnitProvider
import codingblocks.com.weatherlocate.data.repository.ForecastRepository
import codingblocks.com.weatherlocate.internal.Unit
import codingblocks.com.weatherlocate.internal.lazyDef


abstract class WeatherViewModel(
    private val forecastRepository: ForecastRepository,
    unitProvider: UnitProvider
) : ViewModel() {

    private val unitSystem = unitProvider.getUnitSystem()

    val isMetricUnit: Boolean
        get() = unitSystem == Unit.METRIC

    val weatherLocation by lazyDef {
        forecastRepository.getLocation()
    }
}