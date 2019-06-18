package codingblocks.com.weatherlocate.internal

import kotlinx.coroutines.*

fun <T> lazyDef(block:suspend CoroutineScope.()->T): Lazy<Deferred<T>> {

    return lazy {
        GlobalScope.async(start = CoroutineStart.LAZY) {
            block.invoke(this)

        }
    }

}