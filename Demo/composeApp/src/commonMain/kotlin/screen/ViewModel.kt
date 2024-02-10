package screen

import com.nekzabirov.viewmodel.ViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*

abstract class ViewModel<S, E>(initState: S): ViewModel() {

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
}