package pl.sose1.home

import android.app.ActivityOptions
import android.app.AlertDialog
import android.content.Intent
import android.widget.Toast
import androidx.lifecycle.observe
import org.koin.android.viewmodel.ext.android.viewModel
import pl.sose1.base.view.BaseActivity
import pl.sose1.game.GameActivity
import pl.sose1.home.databinding.ActivityHomeBinding

class HomeActivity : BaseActivity<ActivityHomeBinding, HomeViewModel>(layoutId = R.layout.activity_home) {
    override val viewModel by viewModel<HomeViewModel>()

    override fun onInitDataBinding() {
        binding.viewModel = viewModel

        viewModel.events.observe(this, this::onViewEvent)
    }

    private fun onViewEvent(event: HomeViewEvent) {
        when (event) {
            HomeViewEvent.OnClickCreateButton -> configureAlertDialog()
            is HomeViewEvent.ShowUserNameError -> showUserNameErrorToast()
            is HomeViewEvent.ShowInputFieldError -> showInputFieldErrorToast()
            is HomeViewEvent.OpenLobby -> openGameActivity(event.gameId, event.userName)
            is HomeViewEvent.ShowNotFoundError -> showNotFoundError()
        }
    }

    private fun showNotFoundError() {
        Toast.makeText(this, R.string.not_found_gry, Toast.LENGTH_SHORT).show()
    }

    private fun showInputFieldErrorToast() {
        Toast.makeText(this, R.string.input_field_is_empty, Toast.LENGTH_SHORT).show()
    }

    private fun showUserNameErrorToast() {
        Toast.makeText(this, R.string.user_name_is_empty, Toast.LENGTH_SHORT).show()
    }

    private fun configureAlertDialog() {
        AlertDialog.Builder(this)
            .setTitle(R.string.dialog_title_create_game)
            .setPositiveButton(R.string.yes) { _, _ -> viewModel.onClickPositiveButton()}
            .setNegativeButton(android.R.string.cancel) { _, _ -> }
            .show()
    }

    private fun openGameActivity(gameId: String, userName: String) {

        val intent = Intent(this, GameActivity::class.java)
        intent.putExtra("GAME_ID", gameId)
        intent.putExtra("USER_NAME", userName)

        startActivity(
            intent,
            ActivityOptions
                .makeSceneTransitionAnimation(this)
                .toBundle())
    }
}