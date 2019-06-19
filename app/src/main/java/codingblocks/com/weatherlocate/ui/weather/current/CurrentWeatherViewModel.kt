package codingblocks.com.weatherlocate.ui.weather.current

import androidx.lifecycle.ViewModel;
import codingblocks.com.weatherlocate.data.repository.ForecastRepository
import codingblocks.com.weatherlocate.internal.Unit
import codingblocks.com.weatherlocate.internal.lazyDef

class CurrentWeatherViewModel(private val forecastRepository: ForecastRepository) : ViewModel() {



    private val unitsystem = Unit.METRIC

    val isMetric : Boolean
    get() = unitsystem ==Unit.METRIC

    val weather by lazyDef {

        forecastRepository.getCurrentWeather(isMetric)

    }


}
