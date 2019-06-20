package codingblocks.com.weatherlocate.data.provider

import codingblocks.com.weatherlocate.internal.Unit


interface UnitProvider {
    fun getUnitSystem(): Unit
}