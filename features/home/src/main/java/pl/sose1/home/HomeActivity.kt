package pl.sose1.home

import org.koin.android.viewmodel.ext.android.viewModel
import android.widget.Toast
import androidx.lifecycle.observe
import pl.sose1.base.view.BaseActivity
import pl.sose1.home.databinding.ActivityHomeBinding

class HomeActivity : BaseActivity<ActivityHomeBinding, HomeViewModel>(layoutId = R.layout.activity_home) {

    override val viewModel by viewModel<HomeViewModel>()

    override fun onInitDataBinding() {
        binding.viewModel = viewModel

        viewModel.events.observe(this, this::onViewEvent)
    }

    private fun onViewEvent(event: HomeViewEvent) {
        when(event) {
            HomeViewEvent.ShowToast ->
                Toast.makeText(this, "Show Toast", Toast.LENGTH_LONG).show()
        }
    }
}