package pl.sose1.lobby

import androidx.lifecycle.ViewModel
import pl.sose1.base.SingleLiveData

class LobbyViewModel: ViewModel() {
    val events = SingleLiveData<pl.sose1.lobby.LobbyViewEvent>()
}