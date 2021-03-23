package pl.sose1.game

import android.os.Handler
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.core.view.isVisible
import com.github.dhaval2404.colorpicker.MaterialColorPickerDialog
import com.github.dhaval2404.colorpicker.model.ColorShape
import kotlinx.android.synthetic.main.activity_game.*
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import pl.sose1.base.view.BaseActivity
import pl.sose1.core.model.game.Message
import pl.sose1.core.model.user.User
import pl.sose1.game.adapter.message.MessageAdapter
import pl.sose1.game.databinding.ActivityGameBinding
import pl.sose1.ui.extensions.createAnimation
import pl.sose1.ui.extensions.onDone
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

        messageContent.onDone { viewModel.sendMessage() }
    }

    private fun onViewEvent(event: GameViewEvent) {
        when (event) {
            is GameViewEvent.SetMessage -> setMessages(event.message, event.user)
            is GameViewEvent.DrawBitmap -> paintingView.drawBitmap(event.byteArray)
            GameViewEvent.ChangeBrushColor -> changeBrushColor()
            GameViewEvent.ShowTimeoutException -> showTimeoutException()
            GameViewEvent.ShowGuessingInfoAnimation -> showGuessingInfoAnimation()
            GameViewEvent.ShowPaintingInfoAnimation -> showPaintingInfoAnimation()
            GameViewEvent.ClearImage -> paintingView.clearImage()
            GameViewEvent.ClearMessageContentText -> clearMessageContentText()
            GameViewEvent.ShowWordGuessAndPaintingInfoAnimation -> showWordGuessAndPaintingInfoAnimation()
            GameViewEvent.ShowWordGuessAndGuessingInfoAnimation -> showWordGuessAndGuessingInfoAnimation()
        }
    }

    private fun showWordGuessAndGuessingInfoAnimation() {
        val animSheetSlideUp = AnimationUtils.loadAnimation(this, R.anim.bottom_sheet_info_slide_up)
        val animSheetSlideDown = AnimationUtils.loadAnimation(this, R.anim.bottom_sheet_ino_slide_down)

        createAnimation(animSheetSlideUp, animSheetSlideDown,0, binding.infoSheet) {
            Handler().postDelayed({
                showGuessingInfoAnimation()
            }, animSheetSlideUp.duration)
        }
    }

    private fun showWordGuessAndPaintingInfoAnimation() {
        val animSheetSlideUp = AnimationUtils.loadAnimation(this, R.anim.bottom_sheet_info_slide_up)
        val animSheetSlideDown = AnimationUtils.loadAnimation(this, R.anim.bottom_sheet_ino_slide_down)

        createAnimation(animSheetSlideUp, animSheetSlideDown,0, binding.infoSheet) {
            Handler().postDelayed({
                showPaintingInfoAnimation()
            }, animSheetSlideUp.duration)
        }
    }

    private fun showGuessingInfoAnimation() {
        val animInfoSlideUp = AnimationUtils.loadAnimation(this, R.anim.info_slide_up)
        val animInfoSlideDown = AnimationUtils.loadAnimation(this, R.anim.info_slide_down)
        val animBrushToolsSlideRight = AnimationUtils.loadAnimation(this, R.anim.brush_tools_slide_right)
        val animClearImageSlideRight = AnimationUtils.loadAnimation(this, R.anim.clear_image_slide_right)

        binding.info.text = getString(R.string.guess)
        binding.info.visibility = View.VISIBLE

        createAnimation(animInfoSlideUp, animInfoSlideDown,0, binding.info) {
            if (binding.clearImage.isVisible) {
                createAnimation(animClearImageSlideRight, null, 0, binding.clearImage){
                    binding.brushTools.visibility = View.INVISIBLE
                }
            }
            if (binding.brushTools.isVisible) {
                createAnimation(animBrushToolsSlideRight, null, 70, binding.brushTools){
                    binding.clearImage.visibility = View.INVISIBLE
                }
            }
        }
    }

    private fun showPaintingInfoAnimation() {
        val animInfoSlideUp = AnimationUtils.loadAnimation(this, R.anim.info_slide_up)
        val animInfoSlideDown = AnimationUtils.loadAnimation(this, R.anim.info_slide_down)
        val animBrushToolsSlideLeft = AnimationUtils.loadAnimation(this, R.anim.brush_tools_slide_left)
        val animClearImageSlideLeft = AnimationUtils.loadAnimation(this, R.anim.clear_image_slide_left)

        binding.info.text = getString(R.string.paint)
        binding.info.visibility = View.VISIBLE

        createAnimation(animInfoSlideUp, animInfoSlideDown,0, binding.info) {
            binding.brushTools.visibility = View.VISIBLE
            createAnimation(animBrushToolsSlideLeft, null, 0, binding.brushTools) {}

            binding.clearImage.visibility = View.VISIBLE
            createAnimation(animClearImageSlideLeft, null, 70, binding.clearImage) {}
        }
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
                .setColors(
                        arrayListOf("#ff0000", "#FFA500", "#FFFF00", "#FF00FF", "#0000FF", "#008000", "#A0522D", "#000000")
                )
                .setColorShape(ColorShape.CIRCLE)
                .setColorListener { color, _ ->  paintingView.changeBrushColor(color)}
                .show()
    }
}