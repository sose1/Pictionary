package pl.sose1.lobby

import androidx.lifecycle.observe
import org.koin.android.viewmodel.ext.android.viewModel
import pl.sose1.base.view.BaseActivity
import pl.sose1.lobby.databinding.ActivityLobbyBinding


class LobbyActivity : BaseActivity<ActivityLobbyBinding, LobbyViewModel>(layoutId = R.layout.activity_lobby) {

    override val viewModel by viewModel<LobbyViewModel>()

    override fun onInitDataBinding() {
        binding.viewModel = viewModel
        viewModel.events.observe(this, this::onViewEvent)
    }

    private fun onViewEvent(event: LobbyViewEvent){

    }



}