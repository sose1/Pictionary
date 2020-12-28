package pl.sose1.game

import android.graphics.Color
import androidx.lifecycle.observe
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import pl.sose1.base.view.BaseActivity
import pl.sose1.core.model.game.Message
import pl.sose1.core.model.user.User
import pl.sose1.game.adapter.message.MessageAdapter
import pl.sose1.game.databinding.ActivityGameBinding
import pl.sose1.ui.PaintingView
import top.defaults.colorpicker.ColorPickerPopup


class GameActivity : BaseActivity<ActivityGameBinding, GameViewModel>(layoutId = R.layout.activity_game) {

    private lateinit var gameId: String
    private lateinit var paintingView: PaintingView

    private val messageAdapter = MessageAdapter()

    override val viewModel by viewModel<GameViewModel> {
        parametersOf(gameId, paintingView)
    }

    override fun onInitDataBinding() {
        gameId = intent.extras?.getString("GAME_ID") ?: throw NullPointerException("GAME_ID is Null")
        val userName = intent.extras?.getString("USER_NAME") ?: throw NullPointerException("USER_NAME is Null")

        paintingView = binding.paintingView
        paintingView.post {
            paintingView.initialize()
        }

        binding.viewModel = viewModel
        viewModel.events.observe(this, this::onViewEvent)

        binding.messages.setHasFixedSize(true)
        binding.messages.adapter = messageAdapter

        viewModel.connectToGame(userName)
        viewModel.getGameById()

    }

    private fun onViewEvent(event: GameViewEvent) {
        when (event) {
            GameViewEvent.ClearMessageContentText -> clearMessageContentText()
            is GameViewEvent.SetMessage -> setMessages(event.message, event.user)
            is GameViewEvent.SetGameCodeInSubtitle -> binding.gameCode.text = event.code
            GameViewEvent.ChangeBrushColor -> changeBrushColor()
            is GameViewEvent.DrawBitmap -> paintingView.drawBitmap(event.byteArray)
        }
    }

    private fun setMessages(message: Message, user: User) {
        messageAdapter.setMessages(message, user)
        val messagesSize = binding.messages.adapter!!.itemCount

        if (messagesSize != 0)
            binding.messages.smoothScrollToPosition(messagesSize - 1)
    }

    private fun clearMessageContentText() {
        binding.messageContent.text.clear()
    }

    private fun changeBrushColor() {
        ColorPickerPopup.Builder(this)
                .initialColor(Color.WHITE)
                .okTitle("Wybierz")
                .cancelTitle("Anuluj")
                .showIndicator(true)
                .showValue(false)
                .build()
                .show(object : ColorPickerPopup.ColorPickerObserver() {
                    override fun onColorPicked(color: Int) {
                        binding.paintingView.changeBrushColor(color)
                    }
                })
    }
}