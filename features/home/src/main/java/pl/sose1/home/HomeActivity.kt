package pl.sose1.home

import android.app.AlertDialog
import androidx.lifecycle.observe
import org.koin.android.viewmodel.ext.android.viewModel
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
            HomeViewEvent.OnClickCreateButton -> configureAlertDialog()

        }
    }

    private fun configureAlertDialog() {
        AlertDialog.Builder(this)
            .setTitle(R.string.dialog_title_create_room)
            .setPositiveButton(R.string.yes) { _, _ -> }
            .setNegativeButton(android.R.string.cancel) { _, _ -> }
            .show()
    }
}