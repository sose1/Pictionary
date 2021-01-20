package pl.sose1.game

import android.view.View
import android.widget.Toast
import com.github.dhaval2404.colorpicker.MaterialColorPickerDialog
import com.github.dhaval2404.colorpicker.model.ColorShape
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import pl.sose1.base.view.BaseActivity
import pl.sose1.core.model.game.Message
import pl.sose1.core.model.user.User
import pl.sose1.game.adapter.message.MessageAdapter
import pl.sose1.game.databinding.ActivityGameBinding
import pl.sose1.ui.painting.PaintingView


class GameActivity : BaseActivity<ActivityGameBinding, GameViewModel>(layoutId = R.layout.activity_game) {

    private lateinit var gameId: String
    private lateinit var paintingView: PaintingView

    private val messageAdapter = MessageAdapter()

    override val viewModel by viewModel<GameViewModel> {
        parametersOf(gameId)
    }

    override fun onInitDataBinding() {
        gameId = intent.extras?.getString("GAME_ID") ?: throw NullPointerException("GAME_ID is Null")
        val userName = intent.extras?.getString("USER_NAME") ?: throw NullPointerException("USER_NAME is Null")

        binding.viewModel = viewModel
        viewModel.events.observe(this, this::onViewEvent)

        paintingView = binding.paintingView
        paintingView.post {
            paintingView.initialize()
        }

        paintingView.drawnListener = viewModel

        binding.messages.setHasFixedSize(true)
        binding.messages.adapter = messageAdapter

        viewModel.connectToGame(userName)
        viewModel.getGameById()
    }

    private fun onViewEvent(event: GameViewEvent) {
        when (event) {
            GameViewEvent.ClearMessageContentText -> clearMessageContentText()
            GameViewEvent.ChangeBrushColor -> changeBrushColor()
            GameViewEvent.ShowTimeoutException -> showTimeoutException()
            GameViewEvent.Guessing -> startGuessing()
            GameViewEvent.ClearImage -> paintingView.clearImage()
            is GameViewEvent.GameStarted -> paintingView.isStarted = event.isStarted
            is GameViewEvent.SetGameCode -> binding.gameCode.text = event.code
            is GameViewEvent.SetMessage -> setMessages(event.message, event.user)
            is GameViewEvent.DrawBitmap -> paintingView.drawBitmap(event.byteArray)
            is GameViewEvent.Painter -> startPainting(event.wordGuess)
        }
    }

    private fun startGuessing() {
        binding.colorButton.visibility = View.INVISIBLE
        binding.clearButton.visibility = View.INVISIBLE
        binding.word.text = ""
        paintingView.canPaint = false
    }

    private fun startPainting(wordGuess: String) {
        binding.colorButton.visibility = View.VISIBLE
        binding.clearButton.visibility = View.VISIBLE
        binding.word.text = wordGuess
        paintingView.canPaint = true
    }

    private fun showTimeoutException() {
        Toast.makeText(this, "Przekroczono czas połączenia.", Toast.LENGTH_LONG).show()
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
        MaterialColorPickerDialog.Builder(this)
            .setTitle("Wybierz kolor")
            .setColors(arrayListOf("#ff0000", "#FFA500", "#FFFF00", "#FF00FF", "#0000FF", "#008000", "#A0522D", "#000000"))
            .setColorShape(ColorShape.CIRCLE)
            .setColorListener { color, _ ->  paintingView.changeBrushColor(color)}
            .show()
    }
}