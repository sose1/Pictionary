package pl.sose1.ui.bindingAdapters

import androidx.databinding.BindingAdapter
import pl.sose1.ui.painting.PaintingView

@BindingAdapter("canPaint")
fun PaintingView.setCanPaint(canPaint: Boolean) {
    this.canPaint = canPaint
}
