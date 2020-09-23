package pl.sose1.home

import androidx.lifecycle.ViewModel
import pl.sose1.base.SingleLiveData

class HomeViewModel: ViewModel() {
    val events = SingleLiveData<HomeViewEvent>()

    fun onClickCreateButton() {
        events.value = HomeViewEvent.OnClickCreateButton
    }

}