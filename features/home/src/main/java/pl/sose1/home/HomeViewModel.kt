package pl.sose1.home

import androidx.lifecycle.ViewModel
import pl.sose1.base.SingleLiveData
import timber.log.Timber

class HomeViewModel: ViewModel() {
    val events = SingleLiveData<HomeViewEvent>()

    fun onClickCreateButton() {
        events.value = HomeViewEvent.OnClickCreateButton
    }

    fun onClickPositiveButton() {
        Timber.d("createNewGameRoom")
        events.value = HomeViewEvent.OnClickPositiveButton
    }
}