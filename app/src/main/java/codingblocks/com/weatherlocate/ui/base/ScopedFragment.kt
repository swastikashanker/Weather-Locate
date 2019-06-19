package codingblocks.com.weatherlocate.ui.base

import android.os.Bundle
import androidx.fragment.app.Fragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext


abstract class ScopedFragment : Fragment(), CoroutineScope {
    private lateinit var work: Job

    override val coroutineContext: CoroutineContext
        get() = work + Dispatchers.Main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        work= Job()
    }

    override fun onDestroy() {
        super.onDestroy()
       work.cancel()
    }
}