package screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import screen.login.LoginEvent
import screen.login.LoginState
import kotlin.coroutines.CoroutineContext

abstract class ViewModel<S, E>(initState: S): CoroutineScope {
    private val job = SupervisorJob()

    override val coroutineContext: CoroutineContext = Dispatchers.Main.immediate + job

    protected val _state = MutableStateFlow<S>(initState)
    val state: StateFlow<S> = _state

    private val _event = Channel<E>(100)

    init {
        _event.receiveAsFlow()
            .onEach {
                process(it)
            }
            .launchIn(this)
    }

    protected abstract fun process(event: E)

    fun sendEvent(event: E) {
        _event.trySend(event)
    }

    fun clear() {
        job.cancel()
    }
}

@Composable
fun <S, E, T: ViewModel<S, E>> rememberViewModel(factor: () -> T): T {
    val viewModel = remember(factor)

    DisposableEffect(viewModel) {
        onDispose { viewModel.clear() }
    }

    return viewModel
}