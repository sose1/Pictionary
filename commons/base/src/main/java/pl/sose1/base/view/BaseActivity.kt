package pl.sose1.base.view

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel

abstract class BaseActivity<B : ViewDataBinding, V : ViewModel>(
    @LayoutRes
    private val layoutId: Int
) : AppCompatActivity() {

    lateinit var binding: B
    abstract val viewModel: V

    abstract fun onInitDataBinding()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, layoutId)
        binding.lifecycleOwner = this

        onInitDataBinding()
    }
}