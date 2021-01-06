package pl.sose1.home

import android.app.ActivityOptions
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.observe
import com.google.android.material.snackbar.Snackbar
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
            is HomeViewEvent.ShowNotFoundException -> showNotFoundException()
            HomeViewEvent.onClickInfoAboutCodeButton -> showInfoAboutCode()
            HomeViewEvent.ShowTimeoutException -> showTimeoutException()
        }
    }

    private fun showTimeoutException() {
        closeKeyboard()
        createSnackBarInfo(R.string.timeout_exception)
    }

    private fun showInfoAboutCode() {
        AlertDialog.Builder(this)
                .setTitle(R.string.info_about_code)
                .setPositiveButton(android.R.string.ok) {_, _ -> }
                .show()
    }

    private fun showNotFoundException() {
        closeKeyboard()
        createSnackBarInfo(R.string.not_found_game)
    }

    private fun showInputFieldErrorToast() {
        closeKeyboard()
        createSnackBarInfo(R.string.input_field_is_empty)
    }

    private fun showUserNameErrorToast() {
        closeKeyboard()
        createSnackBarInfo(R.string.user_name_is_empty)
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

    private fun createSnackBarInfo(stringId: Int) {
        Snackbar
            .make(binding.homeActivity, stringId, Snackbar.LENGTH_LONG)
            .show()
    }

    private fun closeKeyboard() {
        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(this.currentFocus?.applicationWindowToken, 0)
    }
}