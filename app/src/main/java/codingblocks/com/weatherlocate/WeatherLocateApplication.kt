package codingblocks.com.weatherlocate

import android.app.Application
import android.content.Context
import android.preference.PreferenceManager
import codingblocks.com.weatherlocate.data.db.ForecastDatabase
import codingblocks.com.weatherlocate.data.network.*
import codingblocks.com.weatherlocate.data.provider.LocationProvider
import codingblocks.com.weatherlocate.data.provider.LocationProviderImpl
import codingblocks.com.weatherlocate.data.provider.UnitProvider
import codingblocks.com.weatherlocate.data.provider.UnitProviderImpl
import codingblocks.com.weatherlocate.data.repository.ForecastRepository
import codingblocks.com.weatherlocate.data.repository.ForecastRepositoryImpl
import codingblocks.com.weatherlocate.ui.weather.current.CurrentWeatherViewModelFactory
import codingblocks.com.weatherlocate.ui.weather.future.details.FutureWeatherViewModelFactory
import com.google.android.gms.location.LocationServices
import com.jakewharton.threetenabp.AndroidThreeTen
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.*
import org.threeten.bp.LocalDate

class WeatherLocateApplication:Application(),KodeinAware {


    override val kodein = Kodein.lazy {
        import(androidXModule(this@WeatherLocateApplication))


        bind() from singleton { ForecastDatabase(instance()) }
        bind() from singleton { instance<ForecastDatabase>().currentWeatherDao() }
        bind() from singleton { instance<ForecastDatabase>().futureWeatherDao() }
        bind() from singleton { instance<ForecastDatabase>().locationDao() }
        bind<ConnectivityInterceptor>() with singleton { ConnectivityInterceptorImpl(instance()) }
        bind() from singleton { ApixuWeatherApiService(instance()) }
        bind<WeatherNetworkDataSource>() with singleton { WeatherNetworkDataSourceImpl(instance()) }
        bind() from provider { LocationServices.getFusedLocationProviderClient(instance<Context>()) }
        bind<LocationProvider>() with singleton { LocationProviderImpl(instance(),instance() )}
        bind<ForecastRepository>() with singleton { ForecastRepositoryImpl(instance(), instance() ,instance(),instance(),instance())}
        bind<UnitProvider>() with singleton { UnitProviderImpl(instance()) }
        bind() from provider { CurrentWeatherViewModelFactory(instance(),instance()) }
        bind() from provider { FutureWeatherViewModelFactory(instance(), instance()) }
//        bind() from factory { detailDate: LocalDate -> FutureDetailWeatherViewModelFactory(detailDate, instance(), instance()) }
    }

    override fun onCreate() {
        super.onCreate()
      AndroidThreeTen.init(this)
//        PreferenceManager.setDefaultValues(this, R.xml.preferences, false)
   }

    }
