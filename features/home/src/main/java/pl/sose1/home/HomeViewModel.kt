package pl.sose1.home

import androidx.lifecycle.ViewModel
import pl.sose1.base.SingleLiveData
import timber.log.Timber

class HomeViewModel: ViewModel() {
    val events = SingleLiveData<HomeViewEvent>()

    fun showToast() {
        events.value = HomeViewEvent.ShowToast
    }

}