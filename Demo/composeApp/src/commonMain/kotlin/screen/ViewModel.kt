package screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlin.coroutines.CoroutineContext

abstract class ViewModel: CoroutineScope {
    private val job = SupervisorJob()

    override val coroutineContext: CoroutineContext = Dispatchers.Main.immediate + job

    fun clear() {
        job.cancel()
    }
}

@Composable
fun <T: ViewModel> rememberViewModel(factor: () -> T): T {
    val viewModel = remember(factor)

    DisposableEffect(viewModel) {
        onDispose { viewModel.clear() }
    }

    return viewModel
}